package cn.yangself.wechatBotClient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandEnum {
    BINDING(1, "绑定", "binding"),
    UNBUNDLING(2, "解绑", "unbundling");

    private int code;
    private String label;
    private String value;

}
