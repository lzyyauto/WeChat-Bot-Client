package cn.yangself.wechatBotClient.mapper;

import cn.yangself.wechatBotClient.WechatBotClientTestApplication;
import cn.yangself.wechatBotClient.domain.WXMsg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
class WXMsgMapperTest extends WechatBotClientTestApplication {
    @Autowired
    private WXMsgMapper wxMsgMapper;

    @Test
    void testSave() {
        WXMsg wxMsg = new WXMsg();
        wxMsg.setId("123999");
        wxMsg.setWxid("asdasdasd");
        wxMsg.setContent("adasdsa");
        wxMsg.setNickname("adasdsa");
        wxMsg.setId1("adasdsa");
        wxMsg.setId2("adasdsa");
        wxMsg.setSrvid("adasdsa");
        wxMsg.setIsAt(true);
        wxMsg.setType(1);
        wxMsg.setTime("2021-05-01 00:00:00");
        System.out.println(wxMsgMapper.saveWXMsg(wxMsg));
    }

    @Test
    void testGet() {
        List<WXMsg> wxMsgList = wxMsgMapper.selectAllMessage();
        wxMsgList.forEach(wxMsg -> {
            System.out.println(wxMsg.getId());
        });
    }

}