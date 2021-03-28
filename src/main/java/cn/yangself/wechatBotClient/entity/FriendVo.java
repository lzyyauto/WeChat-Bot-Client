package cn.yangself.wechatBotClient.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendVo implements Serializable {
    /**
     * 名称(昵称)
     * *有可能获取不到
     */
    private String name;
    /**
     * 唯一标识
     */
    private String wxid;
    /**
     * 种类
     * 1.普通好友
     * 2.群聊
     * 3.公众号
     */
    private int type;
}
