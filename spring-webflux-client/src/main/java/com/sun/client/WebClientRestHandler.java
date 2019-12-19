package com.sun.client;

import com.sun.beans.MethodInfo;
import com.sun.beans.ServerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @description: WebClient Rest请求客户端 https://docs.spring.io/spring/docs/current/spring-framework-reference/web-reactive.html#webflux-client
 * @author: 星际一哥
 * @create: 2019-12-18 08:48
 */
@Slf4j
public class WebClientRestHandler implements RestHandler {

    private WebClient webClient;

    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());

    }

    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        WebClient.RequestBodyUriSpec method =
                this.webClient
                        // 请求方法
                        .method(methodInfo.getHttpMethod());

        // 是否带有参数
        if (methodInfo.getParams() != null) {
            method.uri(methodInfo.getUrl(), methodInfo.getParams());
        } else {
            method.uri(methodInfo.getUrl());
        }

        // 是否有body
        if (methodInfo.getBody() != null) {
            method.body(methodInfo.getBody(), methodInfo.getBodyElementType());
        }

        WebClient.ResponseSpec retrieve =
                // 接收json
                method.accept(MediaType.APPLICATION_JSON)
                        // 发出请求
                        .retrieve();

        // 异常处理
//        retrieve.onStatus()
//                .onStatus(HttpStatus::is4xxClientError, response -> Mono.error(WebClientResponseException.create(response.rawStatusCode(), "找不到服务", null, null, null)))
//                .onStatus(HttpStatus::is5xxServerError, response -> Mono.error(WebClientResponseException.create(response.rawStatusCode(), "内部错误", null, null, null))).toBodilessEntity();


        // 处理返回body ,如果是Flux 则转换为Flux ，否则转换为Mono
        return methodInfo.isReturnFlux() ? retrieve.bodyToFlux(methodInfo.getReturnElementType()) : retrieve.bodyToMono(methodInfo.getReturnElementType()).doOnError(w -> {
            System.out.println("这是我啊");
        });

    }
}
