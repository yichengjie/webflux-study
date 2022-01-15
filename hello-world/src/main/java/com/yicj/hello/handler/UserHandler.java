package com.yicj.hello.handler;

import com.yicj.hello.dao.UserRepository;
import com.yicj.hello.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    @Autowired
    private UserRepository userRepository ;


    public Mono<ServerResponse> saveUser(ServerRequest request){

        Mono<UserInfo> mono = request.bodyToMono(UserInfo.class);
        //Mono<UserInfo> resp = userRepository.saveUser(mono);

        //return ServerResponse.ok().build(resp) ;
        return null ;
    }


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