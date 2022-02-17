package com.yicj.study.hello.model.response;

import lombok.Data;

@Data
public class BaseWebResponse {

    private String code ;
    private String msg ;
    private Object data ;

    public static BaseWebResponse successNoData() {
        BaseWebResponse response = new BaseWebResponse();
        response.setCode("200");
        response.setMsg("success");
        response.setData(null);
        return  response;
    }
}
