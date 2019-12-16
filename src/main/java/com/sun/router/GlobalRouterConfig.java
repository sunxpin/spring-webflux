package com.sun.router;

import com.sun.handler.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @description: 全局路由配置类
 * @author: 星际一哥
 * @create: 2019-12-16 20:06
 */

@Configuration
public class GlobalRouterConfig {

    @Bean
    RouterFunction<ServerResponse> userRouter(UserHandler userHandler) {
        return
                // 统一添加user前缀，相当于@RequestMapping("user")
                RouterFunctions.nest(RequestPredicates.path("/router"),
                        // 查询所有用户
                        RouterFunctions.route(RequestPredicates.GET("/"), userHandler::findAll)
                                // 新增用户
                                .andRoute(RequestPredicates.POST("/").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), userHandler::createUser)
                                // 根据id删除用户
                                .andRoute(RequestPredicates.DELETE("/{id}"), userHandler::deleteUser));
    }
}
