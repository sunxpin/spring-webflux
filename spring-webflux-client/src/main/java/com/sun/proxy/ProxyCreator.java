package com.sun.proxy;

/**
 * 创建代理类
 */

public interface ProxyCreator {

    Object createProxy(Class<?> type);
}
