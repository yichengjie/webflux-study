package com.yicj.study.web.message.request;// AuthRequest.java

import com.yicj.study.web.message.Message;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthRequest implements Message {
    public static final String TYPE = "AUTH_REQUEST";
    /**
     * 认证 Token
     */
    private String accessToken;
    // ... 省略 set/get 方法
}