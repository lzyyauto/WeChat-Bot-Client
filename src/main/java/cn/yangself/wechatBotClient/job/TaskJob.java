package cn.yangself.wechatBotClient.job;

import cn.yangself.wechatBotClient.service.WXServerListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TaskJob {
    @Autowired
    private WXServerListener wxServerListener;

    /**
     * 按照标准时间来算，每隔 1s 执行一次
     */
    @Scheduled(cron = "0/1 * * * * ?")
    public void syncFriend() throws InterruptedException {
        log.info("开始同步好友");
        wxServerListener.getContactList();
    }

    /**
     * 从启动时间开始，延迟 5s 后间隔 4s 执行
     * 固定等待时间
     */
//    @Scheduled(fixedDelay = 4000, initialDelay = 5000)
//    public void job3() throws InterruptedException {
//        Random r = new Random();
//        int i = r.nextInt();
//        log.info(i + "【job3】开始执行：{}", DateUtil.formatDateTime(new Date()));
//        Thread.sleep(5000);
//        log.info(i + "【job3】结束执行：{}", DateUtil.formatDateTime(new Date()));
//    }
}
