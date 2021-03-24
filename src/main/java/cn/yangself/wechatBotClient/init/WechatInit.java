package cn.yangself.wechatBotClient.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j

public class WechatInit implements CommandLineRunner {
//    @Autowired
//    private WXServerListener wxServerListener;

    @Override
    public void run(String... args) throws Exception {
        log.info("初始化.获取wxid");
        //TODO 获取wxid并引用
        //没想好怎么做.暂时使用配置填入

    }

}
