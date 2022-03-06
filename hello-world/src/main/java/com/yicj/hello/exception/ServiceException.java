package com.yicj.hello.exception;// ServiceException.java

import com.yicj.hello.constants.ServiceExceptionEnum;
import lombok.Data;

@Data
public final class ServiceException extends RuntimeException {

    /**
     * 错误码
     */
    private final Integer code;

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum) {
        // 使用父类的 message 字段
        super(serviceExceptionEnum.getMessage());
        // 设置错误码
        this.code = serviceExceptionEnum.getCode();
    }

    // ... 省略 getting 方法

}