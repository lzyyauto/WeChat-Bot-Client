package cn.yangself.wechatBotClient.mapper;

import cn.yangself.wechatBotClient.WechatBotClientTestApplication;
import cn.yangself.wechatBotClient.domain.WXMsg;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
class WXMsgMapperTest extends WechatBotClientTestApplication {
    @Autowired
    private WXMsgMapper wxMsgMapper;

    @Test
    void testSave() {
        WXMsg wxMsg = new WXMsg();
        wxMsg.setId("123999");
        System.out.println(wxMsgMapper.saveWXMsg(wxMsg));
    }

}