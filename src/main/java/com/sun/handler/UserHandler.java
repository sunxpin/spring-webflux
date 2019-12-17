package com.sun.handler;

import com.sun.entity.User;
import com.sun.repository.UserRepository;
import com.sun.utils.CheckUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @description: RouterFunction 用户处理类
 * @author: 星际一哥
 * @create: 2019-12-16 19:26
 */
@Component
public
class UserHandler {

    private UserRepository userRepository;

    private UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 查询所有用户
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(this.userRepository.findAll(), User.class);
    }


    /**
     * 新增用户
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        // 获取用户提交数据
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        return userMono.flatMap(user -> {
            // 校验用户名字合法性
            CheckUtil.checkUser(user.getName());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8).body(this.userRepository.save(user), User.class);
        });

    }

    /**
     * 根据id删除用户
     *
     * @param serverRequest
     * @return
     */
    public Mono<ServerResponse> deleteUser(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return  // 先查出来
                this.userRepository.findById(id)
                        // flatMap 对查出来的对象进行删除操作，删除成功返回200
                        .flatMap(user -> this.userRepository.delete(user).then(ServerResponse.ok().build()))
                        // 不存在则返回404
                        .switchIfEmpty(ServerResponse.notFound().build());
    }
}
