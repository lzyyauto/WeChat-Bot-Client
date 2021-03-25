package cn.yangself.wechatBotClient.messageservice.impl;

import cn.yangself.wechatBotClient.conf.cache.IGlobalCache;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.messageservice.ForwardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ForwardingServiceImpl implements ForwardingService {

    @Autowired
    private IGlobalCache IGlobalCache;

    /**
     * 群聊里绑定转发单人
     *
     * @param message
     * @return
     */
    @Override
    public boolean bindingPerson(WXMsg message) {
        String key = message.getRoomId();
        //TODO 根据昵称查询wxid
        String value = message.getContent();
        IGlobalCache.set(key, value);
        return true;
    }

    /**
     * 私聊绑定某群
     *
     * @param message
     * @return
     */
    @Override
    public boolean bindingChatroom(WXMsg message) {
        String key = message.getWxid();
        //TODO 根据群名查询群wxid@chatromm
        String value = message.getContent();
        IGlobalCache.set(key, value);
        return true;
    }

    /**
     * 群里解绑掉某人
     *
     * @param message
     * @return
     */
    @Override
    public boolean unbundlingPerson(WXMsg message) {
        String key = message.getRoomId();
        IGlobalCache.del(key);
        return true;
    }

    /**
     * 私聊解绑某个群聊
     *
     * @param message
     * @return
     */
    @Override
    public boolean unbundlingChatroom(WXMsg message) {
        String key = message.getWxid();
        IGlobalCache.del(key);
        return true;
    }
}
