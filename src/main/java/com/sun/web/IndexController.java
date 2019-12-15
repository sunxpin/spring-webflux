package com.sun.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@RestController
@Slf4j
public class IndexController {

    @GetMapping("1")
    public String sayHi() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("1");
        String result = createStr();
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return result;
    }

    private String createStr() {
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "success";
    }

    @GetMapping("2")
    public Mono<String> sayHell() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("2");
        Mono<String> stringMono = Mono.fromSupplier(this::createStr);
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
        return stringMono;
    }

    /**
     * SSE （ Server-sent Events ）是 WebSocket 的一种轻量代替方案，使用 HTTP 协议。仅支持 UTF-8 格式的编码
     * <p>
     * 服务器向客户端声明接下来要发送流信息时，客户端就会保持连接打开，SSE 使用的就是这种原理。
     * <p>
     * SSE 是单向通道，只能服务器向客户端发送消息，如果客户端需要向服务器发送消息，则需要一个新的 HTTP 请求
     * <p>
     * SSE如何保证数据完整性？
     * 客户端在每次接收到消息时，会把消息的 id 字段作为内部属性 Last-Event-ID 储存起来。
     * SSE 默认支持断线重连机制，在连接断开时会 触发 EventSource 的 error 事件，同时自动重连。
     * 再次连接成功时 EventSource 会把 Last-Event-ID 属性作为请求头发送给服务器，
     * 这样服务器就可以根据这个 Last-Event-ID 作出相应的处理。
     *
     * @return
     */
    @GetMapping(value = "3", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> flux() {
        Flux<String> stringFlux = Flux.fromStream(IntStream.range(0, 5).mapToObj(i -> {
                    try {
                        TimeUnit.SECONDS.sleep(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "flux--data" + i;
                }
        ));

        return stringFlux;
    }
}
