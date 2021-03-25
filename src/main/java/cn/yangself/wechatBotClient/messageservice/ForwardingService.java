package cn.yangself.wechatBotClient.messageservice;

import cn.yangself.wechatBotClient.domain.WXMsg;

public interface ForwardingService {
    boolean bindingPerson(WXMsg message);

    boolean bindingChatroom(WXMsg message);

    boolean unbundlingPerson(WXMsg message);

    boolean unbundlingChatroom(WXMsg message);
}
