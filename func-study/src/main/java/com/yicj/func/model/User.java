package com.yicj.func.model;

import com.yicj.func.service.SayHello;
import lombok.Data;

@Data
public class User {
    private String username ;

    public String say(SayHello sayHello) {
        return sayHello.sayHello(this.username) ;
    }

}
