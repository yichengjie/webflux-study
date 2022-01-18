package com.yicj.func.model;

import com.yicj.func.service.SayHello;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String username ;
    private Integer age ;

    public String say(SayHello sayHello) {
        return sayHello.sayHello(this.username) ;
    }

}
