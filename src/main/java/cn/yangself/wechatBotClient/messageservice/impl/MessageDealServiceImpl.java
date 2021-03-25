package cn.yangself.wechatBotClient.messageservice.impl;

import cn.yangself.wechatBotClient.constant.CommandString;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.enums.CommandEnum;
import cn.yangself.wechatBotClient.messageservice.ForwardingService;
import cn.yangself.wechatBotClient.messageservice.MessageDealService;
import cn.yangself.wechatBotClient.service.WXServerListener;
import cn.yangself.wechatBotClient.utils.WXMsgUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageDealServiceImpl implements MessageDealService {

    @Value("${wechat.botwxid}")
    private String botWxId;

    @Autowired
    private WXServerListener wxServerListener;

    @Autowired
    private ForwardingService forwardingService;

    @Override
    public boolean dealTextMessage(String s) {
        log.info(s);
//        JSONObject message = JSON.parseObject(s);
        WXMsg message = JSON.parseObject(s, WXMsg.class);
        message = WXMsgUtil.formatWXMsg(message);
        //判断类型
        switch (message.getType()) {
            case 5005:
//                log.info("系统消息:" + message.getContent());
                break;
            case 1:
                String resultURL = WXMsgUtil.checkURL(message.getContent());
                if (message.getRoomId() != null) {
                    log.info("群聊消息:" + message.getNick() + ":" + message.getContent());
                    if (message.getIsAt()) {
                        //圈我
                        //判断指令
                        if (message.getContent().contains(CommandString.FUNCTION)) {
                            dosomething(message, CommandEnum.COMMANDCHAT.getCode());
                        } else {
                            //TODO 接入机器人
                            wxServerListener.sendTextMsg(message.getRoomId() + CommandString.CHATROOM, "圈我作甚?");
                        }
                    }
                } else {
                    //判断自己
                    if (message.getNick().equals(botWxId)) {
                        log.info("我自己发的消息:" + message.getContent());
                    } else {
                        //判断管理员
                        if (WXMsgUtil.isAdmin(message.getNick())) {
                            //判断指令
                            if (message.getContent().contains(CommandString.FUNCTION)) {
                                dosomething(message, CommandEnum.COMMANDADMIN.getCode());
//                                forwardingService.bindingChatroom(message);
                            }
                        }
                        log.info("单聊消息:" + message.getNick() + ":" + message.getContent());
                    }

                }
                break;
            default:
                log.info("未知类型消息:" + s);
        }

        return true;
    }

    private void dosomething(WXMsg message, int type) {
        //function:command:param
        String[] func = message.getContent().split(":");
        if (func.length < 2) {
            //错误指令
            return;
        }

        CommandEnum ce = CommandEnum.getByValue(func[1]);

        if (ce != null) {
            switch (ce) {
                case BINDING:
                    //绑定一定有参数.不用判断为空
                    message.setContent(func[2]);
                    if (type == CommandEnum.COMMANDCHAT.getCode()) {
                        forwardingService.bindingPerson(message);
                    } else {
                        forwardingService.bindingChatroom(message);
                    }
                    break;
                case UNBUNDLING:
                    if (type == CommandEnum.COMMANDCHAT.getCode()) {
                        forwardingService.unbundlingPerson(message);
                    } else {
                        forwardingService.unbundlingChatroom(message);
                    }
                    break;
                default:
                    log.info("nothing happen!");
            }
        }
        log.info("command absence!");

    }
}
