package com.yicj.mysql.config;

import com.yicj.mysql.handler.UserHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Autowired
    private UserHandler userHandler ;

    @Bean
    public RouterFunction<ServerResponse> userRouter() {
        RouterFunction<ServerResponse> router = RouterFunctions
                .route(POST("/router/user/findList")
                                .and(accept(APPLICATION_JSON)),
                        userHandler::findList) ;

        return router;
    }
}
