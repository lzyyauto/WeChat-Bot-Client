package cn.yangself.wechatBotClient.init;

import cn.yangself.wechatBotClient.service.WXServerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j

public class WechatInit implements CommandLineRunner {
    @Autowired
    private WXServerListener wxServerListener;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化.获取wxid");
        wxServerListener.getContactList();
    }

}
