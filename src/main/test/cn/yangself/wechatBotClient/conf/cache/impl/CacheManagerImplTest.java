package cn.yangself.wechatBotClient.conf.cache.impl;

import cn.yangself.wechatBotClient.conf.cache.CacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootApplication
@Service
class CacheManagerImplTest {
    @Autowired
    private CacheManager cacheManager;

    @Test
    void putHashObject() {
        cacheManager.putHashObject("test","111");
    }

    @Test
    void getHashObject() {
    }

    @Test
    void getHashEntries() {
    }
}