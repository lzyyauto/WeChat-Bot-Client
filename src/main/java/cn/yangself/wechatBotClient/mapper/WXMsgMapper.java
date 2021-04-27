package cn.yangself.wechatBotClient.mapper;

import cn.yangself.wechatBotClient.domain.WXMsg;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;


@Mapper
@Component
public interface WXMsgMapper {

    @Select("SELECT * FROM w_message")
    List<WXMsg> selectAllMessage();


    int saveWXMsg(@Param("message") WXMsg message);
}
