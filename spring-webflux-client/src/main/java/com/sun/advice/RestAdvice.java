package com.sun.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * @description: 捕捉RestClient 404 错误
 * @author: 星际一哥
 * @create: 2019-12-19 18:17
 */
@ControllerAdvice
public class RestAdvice {

    @ExceptionHandler(WebClientResponseException.NotFound.class)
    public ResponseEntity<String> handlerBindException(WebClientResponseException.NotFound ex) {
        return new ResponseEntity<>("数据不存在!", HttpStatus.NOT_FOUND);
    }


}
