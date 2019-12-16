package com.sun.exception;

import lombok.Data;

/**
 * @description: 校验用户名字，不合格抛出异常
 * @author: 星际一哥
 * @create: 2019-12-16 19:01
 */
@Data
public class CheckException extends RuntimeException {
    private String fieldName;
    private String fieldValue;

    public CheckException() {
    }

    public CheckException(String message) {
        super(message);
    }

    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    public CheckException(Throwable cause) {
        super(cause);
    }

    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public CheckException(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
