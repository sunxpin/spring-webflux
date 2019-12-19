package com.sun.web;

import com.sun.beans.User;
import com.sun.repository.UserRepository;
import com.sun.utils.CheckUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @Description 用户CRUD
 * @Author 星际一哥
 * @Date 2019/12/15 16:58
 * @Version 1.0
 */
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @GetMapping("/")
    public Flux<User> getAll() {
        return this.userRepository.findAll();
    }

    /**
     * 查询所有用户，并以流的方式向客户端返回数据（SSE）
     *
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return this.userRepository.findAll();
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @PostMapping("/")
    public Mono<User> addUser(@Valid @RequestBody User user) {
        // spring data jpa 里面，新增和修改都是save，有id是修改，id为空是新增
        user.setId(null);
        CheckUtil.checkUser(user.getName());
        return this.userRepository.save(user);
    }


    /**
     * 根据id删除用户，不存在则返回404，存在则删除返回成功
     *
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        // deleteById 没有返回值，不能判断是否存在
        return this.userRepository
                // 1 判断是否存在
                .findById(id)
                // 当你要操作数据，并返回一个Mono 这个时候使用flatMap
                // 如果不操作数据，只是转换数据，使用map
                .flatMap(user -> this.userRepository.delete(user).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK))))
                // 如果不存在则返回404
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id更新用户，如果不存在则返回404，存在则更新并返回用户
     *
     * @param id
     * @param user
     * @return
     */
    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id,
                                                 @Valid @RequestBody User user) {
        CheckUtil.checkUser(user.getName());
        return this.userRepository.findById(id)
                // flatMap 操作数据
                .flatMap(u -> {
                    u.setAge(user.getAge());
                    u.setName(user.getName());
                    return this.userRepository.save(u);
                })
                // map 转换数据 把user 进行包装
                .map(u -> new ResponseEntity<>(u, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据id查找用户，不存在则返回404
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id) {
        return this.userRepository.findById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * 根据年龄查找用户
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping("/age/{start}/{end}")
    public Flux<User> findByAge(@PathVariable("start") int start, @PathVariable("end") int end) {
        return this.userRepository.ageBetween(start, end);

    }

    /**
     * 根据年龄查找用户,返回流
     *
     * @param start
     * @param end
     * @return
     */
    @GetMapping(value = "/stream/age/{start}/{end}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findByAgeStream(@PathVariable("start") int start, @PathVariable("end") int end) {
        return this.userRepository.ageBetween(start, end);

    }

    /**
     * 根据年龄查找用户
     *
     * @return
     */
    @GetMapping("/old")
    public Flux<User> findOld() {
        return this.userRepository.findOld();
    }

    /**
     * 根据年龄查找用户
     *
     * @return
     */
    @GetMapping(value = "/stream/old", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> findOldStream() {
        return this.userRepository.findOld();
    }


}
