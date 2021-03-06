package cn.yangself.wechatBotClient.service;

import cn.yangself.wechatBotClient.domain.WXMsg;
import cn.yangself.wechatBotClient.entity.FriendVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
public class ForwardingServiceTest {

    @Autowired
    private ForwardingService forwardingService;

    @Test
    void bindingPerson() {
        WXMsg mess = new WXMsg();
        mess.setWxid("111");
        mess.setContent("test");
        forwardingService.bindingPerson(mess);
    }

    @Test
    void saveFriend() {
        FriendVo friendVo = new FriendVo();
        friendVo.setName("\uD83C\uDF8F北京333");
        friendVo.setWxid("1231231222323");
        forwardingService.saveFriend(friendVo);
    }

    @Test
    void cleanFriend() {
        forwardingService.cleanFriends();
    }

    @Test
    void getWxidByNick() {
        log.info(forwardingService.getWxidByNick("\uD83C\uDF8F北京333"));
    }
}