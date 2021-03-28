package cn.yangself.wechatBotClient.messageservice.impl;

import cn.yangself.wechatBotClient.constant.Constant;
import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.entity.FriendVo;
import cn.yangself.wechatBotClient.enums.CommandEnum;
import cn.yangself.wechatBotClient.messageservice.MessageDealService;
import cn.yangself.wechatBotClient.service.ForwardingService;
import cn.yangself.wechatBotClient.service.WXServerListener;
import cn.yangself.wechatBotClient.utils.WXMsgUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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
//        log.info(s);
//        JSONObject message = JSON.parseObject(s);
        WXMsg message = JSON.parseObject(s, WXMsg.class);
        message = WXMsgUtil.formatWXMsg(message);
        //判断类型
        switch (message.getType()) {
            case WXServerListener.HEART_BEAT:
//                log.info("系统消息:" + message.getContent());
                break;
            case WXServerListener.RECV_TXT_MSG:
                String resultURL = WXMsgUtil.checkURL(message.getContent());
                String bindID = getBingingID(message.getWxid());
                if (message.getRoomId() != null) {
                    log.info("群聊消息:" + message.getNick() + ":" + message.getContent());
                    if (message.getIsAt()) {
                        //圈我
                        //判断指令
                        if (message.getContent().contains(Constant.FUNCTION)) {
                            dosomething(message, CommandEnum.COMMANDCHAT.getCode());
                            //防止指令随业务下发
                            bindID = null;
                        } else {
                            //TODO 接入机器人
                            wxServerListener.sendTextMsg(message.getRoomId() + Constant.CHATROOM, "圈我作甚?");
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
                            if (message.getContent().contains(Constant.FUNCTION)) {
                                dosomething(message, CommandEnum.COMMANDADMIN.getCode());
                                //防止指令随业务下发
                                bindID = null;
                            }
                        }
                        log.info("单聊消息:" + message.getNick() + ":" + message.getContent());
                    }

                }

                if (bindID != null) {
                    //转发去bindID
                    wxServerListener.sendTextMsg(bindID, message.getContent());
                }
                break;
            case WXServerListener.USER_LIST:
                //清理原有列表
                forwardingService.cleanFriends();
                //获取新的列表
                List<FriendVo> friendVoList = JSONObject.parseArray(message.getContent(), FriendVo.class);
                log.info("更新好友列表:size " + friendVoList.size());
                friendVoList.forEach(friendVo -> {
                    forwardingService.saveFriend(friendVo);
                });
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
            Map friends = new HashMap();
            switch (ce) {
                case BINDING:
                    //绑定一定有参数.不用判断为空
                    message.setContent(func[2]);
                    forwardingService.binding(message.getWxid(), message.getContent());
                    break;
                case UNBUNDLING:
                    forwardingService.unbundling(message.getWxid());
                    break;
                case FRILIST:
                    if (func.length < 3) {
                        friends = forwardingService.getFriends();
                    } else {
                        friends.put(func[2], forwardingService.getWxidByNick(func[2]));
                    }
                    break;
                default:
                    log.info("nothing happen!");
            }
            if (friends.size() > 0) {
                AtomicReference<String> result = new AtomicReference<>("");
                AtomicInteger num = new AtomicInteger(0);
                friends.forEach((k, v) -> {
                    result.set(result.get() + k + "/" + v + "\n");
                    num.set(num.getAndIncrement() + 1);
                    if (num.get() > 10) {
                        wxServerListener.sendTextMsg(message.getWxid(), result.get());
                        result.set("");
                        num.set(0);
                    }

                });
                if (!result.get().equals("")) {
                    wxServerListener.sendTextMsg(message.getWxid(), result.get());
                }
            } else {
                wxServerListener.sendTextMsg(message.getWxid(), ce.getLabel() + " success");
            }
        } else {
            log.info("command absence!");
        }

    }

    private String getBingingID(String key) {
        return forwardingService.getBindingID(key);
    }
}
