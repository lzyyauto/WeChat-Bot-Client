package cn.yangself.wechatBotClient.service.impl;

import cn.yangself.wechatBotClient.constant.Constant;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.entity.FriendVo;
import cn.yangself.wechatBotClient.service.ForwardingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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

    @Override
    public boolean saveFriend(FriendVo friendVo) {
        stringRedisTemplate.opsForHash().put(Constant.FRIENDS, friendVo.getName(), friendVo.getWxid());
        return true;
    }

    @Override
    public String getWxidByNick(String nick) {
        return (String) stringRedisTemplate.opsForHash().get(Constant.FRIENDS, nick);
    }

    @Override
    public void cleanFriends() {
        stringRedisTemplate.delete(Constant.FRIENDS);
    }

    @Override
    public Map getFriends() {
        return stringRedisTemplate.opsForHash().entries(Constant.FRIENDS);
    }

    @Override
    public String getSpecialChatroom(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean onChatRoomBot(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
        return true;
    }
}
