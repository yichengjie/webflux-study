package com.yicj.study.web.message.request;// UserJoinNoticeRequest.java


import com.yicj.study.web.message.Message;
import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)
public class UserJoinNoticeRequest implements Message {
    public static final String TYPE = "USER_JOIN_NOTICE_REQUEST";
    /**
     * 昵称
     */
    private String nickname;
    // ... 省略 set/get 方法
}