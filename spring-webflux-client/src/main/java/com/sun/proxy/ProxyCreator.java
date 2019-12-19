package com.sun.proxy;

/**
 * 代理类接口
 */

public interface ProxyCreator {

    Object createProxy(Class<?> type);
}
