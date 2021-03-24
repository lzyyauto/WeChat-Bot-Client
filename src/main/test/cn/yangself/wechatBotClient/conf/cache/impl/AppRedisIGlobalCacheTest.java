package cn.yangself.wechatBotClient.conf.cache.impl;

import cn.yangself.wechatBotClient.conf.cache.IGlobalCache;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class AppRedisIGlobalCacheTest {
    @Autowired
    private IGlobalCache IGlobalCache;

    @Test
    void get() {
        System.out.println(IGlobalCache.get("test"));
    }

    @Test
    void set() {
        IGlobalCache.set("test", "111");
    }
}