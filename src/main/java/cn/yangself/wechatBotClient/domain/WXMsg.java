package cn.yangself.wechatBotClient.domain;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WXMsg implements Serializable {
    private static final long serialVersionUID = 2892248514883451461L;
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
