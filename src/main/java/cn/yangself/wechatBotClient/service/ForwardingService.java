package cn.yangself.wechatBotClient.service;

import cn.yangself.wechatBotClient.domain.WXMsg;

public interface ForwardingService {
    boolean binding(String key, String value);

    boolean unbundling(String key);

    boolean bindingPerson(WXMsg message);

    boolean bindingChatroom(WXMsg message);

    boolean unbundlingPerson(WXMsg message);

    boolean unbundlingChatroom(WXMsg message);

    String getBindingID(String key);
}
