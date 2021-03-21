package cn.yangself.wechatBotClient.messageservice.impl;

import cn.yangself.wechatBotClient.constant.CommandString;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.messageservice.MessageDealService;
import cn.yangself.wechatBotClient.service.WXServerListener;
import cn.yangself.wechatBotClient.utils.WXMsgUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageDealServiceImpl implements MessageDealService {
    @Autowired
    private WXServerListener wxServerListener;

    @Override
    public boolean dealTextMessage(String s) {
//        log.info(s);
//        JSONObject message = JSON.parseObject(s);
        WXMsg message = JSON.parseObject(s, WXMsg.class);
        message = WXMsgUtil.formatWXMsg(message);
        //判断类型
        switch (message.getType()) {
            case 5005:
//                log.info("系统消息:" + message.getContent());
                break;
            case 1:
                if (message.getRoomId() != null) {
                    log.info("群聊消息:" + message.getNick() + ":" + message.getContent());
                    //判断URL
                    if (WXMsgUtil.checkURL(message.getContent()) != null) {
                        //TODO 判断并且转链
                        //是,转发
                    } else {
                        //否
                        if (message.getIsAt()) {
                            //圈我
                            //判断指令
                            if (message.getContent().contains(CommandString.FUNCTION)) {

                            } else {
                                //TODO 接入机器人
                                wxServerListener.sendTextMsg(message.getRoomId() + CommandString.CHATROOM, "圈我作甚?");
                            }
                        }
                    }
                } else {
                    //判断自己.(重启是否会变)
                    if (message.getNick().equals("wxid_5301003010211")) {
                        log.info("我自己发的消息:" + message.getContent());
                    } else {
                        //判断管理员?

                        //判断指令
                        if (message.getContent().contains(CommandString.FUNCTION)) {
                            log.info("功能产生");
                        } else {

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
}
