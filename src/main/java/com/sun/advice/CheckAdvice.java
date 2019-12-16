package com.sun.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @description: 参数校验异常切面
 * @author: 星际一哥
 * @create: 2019-12-16 12:57
 */
@ControllerAdvice
public class CheckAdvice {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handlerBindException(WebExchangeBindException ex) {
        return new ResponseEntity<>(toStr(ex), HttpStatus.BAD_REQUEST);
    }

    /**
     * 把校验异常装换为字符串
     *
     * @param ex
     * @return
     */
    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream()
                // 如果不操作数据，只是转换数据，使用map
                .map(e -> e.getField() + ":" + e.getDefaultMessage()).reduce("", (s1, s2) -> s1 + "\n" + s2);
    }

}
