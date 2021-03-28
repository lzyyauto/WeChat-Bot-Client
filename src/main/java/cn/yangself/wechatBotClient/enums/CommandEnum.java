package cn.yangself.wechatBotClient.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommandEnum {
    BINDING(0, "绑定", "binding"),
    UNBUNDLING(1, "解绑", "unbundling"),
    FRILIST(2, "好友列表", "frilist"),

    //指令来源
    COMMANDCHAT(0, "群聊指令", "commandchat"),
    COMMANDADMIN(1, "私聊指令", "commandadmin");

    private int code;
    private String label;
    private String value;


    public static CommandEnum getByValue(String s) {
        for (CommandEnum enums : CommandEnum.values()) {
            if (enums.getValue().equals(s)) {
                return enums;
            }
        }
        return null;
    }
}
