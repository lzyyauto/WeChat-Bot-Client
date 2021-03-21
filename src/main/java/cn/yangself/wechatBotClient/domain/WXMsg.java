package cn.yangself.wechatBotClient.domain;

import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WXMsg {
    private String id;
    private String wxid;
    private String content;
    private String roomId;
    private int type;
    private String nick;
    private String id1;
    private String id2;
    private String srvid;
    private String time;
    private Boolean isAt = false;


    public String toJson() {
        return JSON.toJSONString(this);
    }
}
