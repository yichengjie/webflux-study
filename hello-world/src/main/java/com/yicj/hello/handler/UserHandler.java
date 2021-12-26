package com.yicj.hello.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    /**
     *  获取用户id
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getUserId(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just("14455662442"),String.class);
    }

    /**
     *  获取用户姓名
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> getUserName(ServerRequest serverRequest){

        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just("crabman"),String.class);
    }

}