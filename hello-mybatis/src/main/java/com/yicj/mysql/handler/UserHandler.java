package com.yicj.mysql.handler;

import com.yicj.mysql.entity.UserInfo;
import com.yicj.mysql.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.List;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class UserHandler {

    @Autowired
    private UserService userService ;

    public Mono<ServerResponse> findList(ServerRequest serverRequest) {
        log.info("handler findList start");
        Mono<UserInfo> queryMono = serverRequest.bodyToMono(UserInfo.class);
        Flux<UserInfo> userInfoFlux = queryMono.flatMapMany(userInfo -> {
            log.info("准备查询数据库....");
            List<UserInfo> list = userService.findList(userInfo);
            return Flux.fromIterable(list);
        });
        log.info("handler findList end");
        return ok().contentType(MediaType.APPLICATION_JSON)
                .body(userInfoFlux, UserInfo.class);
    }


}
