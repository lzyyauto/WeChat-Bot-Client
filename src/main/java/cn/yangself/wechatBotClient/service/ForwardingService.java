package cn.yangself.wechatBotClient.service;

import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.entity.FriendVo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ForwardingService {
    boolean binding(String key, String value);

    boolean unbundling(String key);

    boolean bindingPerson(WXMsg message);

    boolean bindingChatroom(WXMsg message);

    boolean unbundlingPerson(WXMsg message);

    boolean unbundlingChatroom(WXMsg message);

    String getBindingID(String key);

    boolean saveFriend(FriendVo friendVo);

    String getWxidByNick(String nick);

    void cleanFriends();

    Map getFriends();
}
