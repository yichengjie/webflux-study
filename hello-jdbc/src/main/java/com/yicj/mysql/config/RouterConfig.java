package com.yicj.mysql.config;

import com.yicj.mysql.handler.UserHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Slf4j
@Configuration
public class RouterConfig {

    @Autowired
    private UserHandler userHandler ;

    @Bean
    public RouterFunction<ServerResponse> userRouter() {
        RouterFunction<ServerResponse> router = RouterFunctions.route()
                .POST("/router/user/findList", userHandler::findList)
                .GET("/hello/{name}", serverRequest -> {
                    log.info("handler hello ...");
                    String name = serverRequest.pathVariable("name");
                    return ServerResponse.ok().bodyValue(name);
                })
                .build() ;
        return router;
    }
}
