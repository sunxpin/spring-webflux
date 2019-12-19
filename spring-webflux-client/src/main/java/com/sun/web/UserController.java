package com.sun.web;

import com.sun.entity.User;
import com.sun.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @description: 测试类
 * @author: 星际一哥
 * @create: 2019-12-17 19:14
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * 测试信息提取 ，不订阅就不会实际发出请求，但是会进入我们的代理类
     */
    @GetMapping("/test")
    public void test() {
        userService.findAll();
        userService.findById("222222222");
        userService.deleteUser("21");
        userService.createUser(Mono.just(User.builder().name("foo").age(22).build()));
    }

    @GetMapping("/")
    public Flux<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping("/")
    public Mono<User> createUser(@RequestBody User user) {
        return userService.createUser(Mono.just(user));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteUser(@PathVariable("id") String id) {
        return userService.deleteUser(id);
    }

}
