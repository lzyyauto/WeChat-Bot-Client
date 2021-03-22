package cn.yangself.wechatBotClient.utils;

import cn.yangself.wechatBotClient.constant.CommandString;
import cn.yangself.wechatBotClient.domain.WXMsg;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WXMsgUtil {

    private static String botNick;

    @Value("${wechat.botnick}")
    public void setBotNick(String botNick) {
        WXMsgUtil.botNick = botNick;
    }


    public static WXMsg formatWXMsg(WXMsg wxMsg) {
        if (wxMsg.getType() != 1) {
            //系统消息不做转换
            return wxMsg;
        }
        if (wxMsg.getId2().contains(CommandString.CHATROOM)) {
            String[] ids = wxMsg.getId2().split("@");
            wxMsg.setRoomId(ids[0]);

            wxMsg.setNick(wxMsg.getId1());
        } else {
            wxMsg.setNick(wxMsg.getId2());
        }

        if (wxMsg.getContent().contains("@" + botNick)) {
            wxMsg.setIsAt(true);
        }

        return wxMsg;
    }

    public static String checkURL(String content) {
        return null;
    }
}
