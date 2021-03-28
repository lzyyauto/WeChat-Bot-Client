package cn.yangself.wechatBotClient.service.impl;

import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.service.ForwardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ForwardingServiceImpl implements ForwardingService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean binding(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.opsForValue().set(value, key);
        return true;
    }

    @Override
    public boolean unbundling(String key) {
        String mapID = stringRedisTemplate.opsForValue().get(key);
        stringRedisTemplate.delete(key);
        stringRedisTemplate.delete(mapID);
        return true;
    }

    @Override
    public boolean bindingPerson(WXMsg message) {
        stringRedisTemplate.opsForValue().set(message.getRoomId(), message.getContent());
        return true;
    }

    @Override
    public boolean bindingChatroom(WXMsg message) {
        stringRedisTemplate.opsForValue().set(message.getWxid(), message.getContent());
        return true;
    }

    @Override
    public boolean unbundlingPerson(WXMsg message) {
        stringRedisTemplate.delete(message.getRoomId());
        return true;
    }

    @Override
    public boolean unbundlingChatroom(WXMsg message) {
        stringRedisTemplate.delete(message.getWxid());
        return true;
    }

    @Override
    public String getBindingID(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }
}
