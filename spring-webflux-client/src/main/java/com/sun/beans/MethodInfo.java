package com.sun.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @description: 封装方法信息
 * @author: 星际一哥
 * @create: 2019-12-17 19:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MethodInfo {

    /*请求地址*/
    private String url;

    /*请求方法*/
    private HttpMethod httpMethod;

    /*请求参数*/
    private Map<String, Object> params;

    /*请求body*/
    private Mono<?> body;

    /*请求body的类型*/
    private Class<?> bodyElementType;

    /*返回是Mono还是Flux*/
    private boolean isReturnFlux;

    /*返回值的类型*/
    private Class<?> returnElementType;
}
