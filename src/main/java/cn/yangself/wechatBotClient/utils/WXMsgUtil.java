package cn.yangself.wechatBotClient.utils;

import cn.yangself.wechatBotClient.constant.Constant;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.service.WXServerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WXMsgUtil {

    private static String botNick;

    @Value("${wechat.botnick}")
    public void setBotNick(String botNick) {
        WXMsgUtil.botNick = botNick;
    }

    private static String admin;

    @Value("${wechat.admin}")
    public void setAdmin(String admin) {
        WXMsgUtil.admin = admin;
    }

    public static WXMsg formatWXMsg(WXMsg wxMsg) {
        if (wxMsg.getType() != WXServerListener.RECV_TXT_MSG) {
            //系统消息不做转换
            return wxMsg;
        }
        if (wxMsg.getId2().contains(Constant.CHATROOM)) {
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
        //TODO 判断并且转链
        return null;
    }

    public static boolean isAdmin(String wxid) {
        return wxid.equals(admin);
    }
}
