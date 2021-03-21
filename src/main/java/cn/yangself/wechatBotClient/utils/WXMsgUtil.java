package cn.yangself.wechatBotClient.utils;

import cn.yangself.wechatBotClient.constant.CommandString;
import cn.yangself.wechatBotClient.domain.WXMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WXMsgUtil {

    private static String botNick;

    @Value("${config.weChat.botnick}")
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
