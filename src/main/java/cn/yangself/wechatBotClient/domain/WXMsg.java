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
    private String roomid;
    private int type;
    private String nickname;
    private String id1;
    private String id2;
    private String srvid;
    private String time;
    private Boolean isAt = false;
    private String ext;


    public String toJson() {
        return JSON.toJSONString(this);
    }
}
