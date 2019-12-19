package com.sun.handler;

import com.sun.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @description: routerFunction 异常处理类
 * @author: 星际一哥
 * @create: 2019-12-17 12:56
 */
@Component
@Order(-2) //优先级 越小越先执行，最少得调到-2
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        String result = toStr(ex);

        DataBuffer db = response.bufferFactory().wrap(result.getBytes());
        return response.writeWith(Mono.just(db));
    }

    private String toStr(Throwable ex) {
        //已知异常
        if (ex instanceof CheckException) {
            CheckException checkException = (CheckException) ex;
            return checkException.getFieldName() + ": Invalided " + checkException.getFieldValue();
        }
        //未知异常
        else {
            ex.printStackTrace();
            return ex.toString();
        }
    }
}
