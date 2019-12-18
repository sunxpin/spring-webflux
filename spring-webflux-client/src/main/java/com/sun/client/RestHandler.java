package com.sun.client;

import com.sun.beans.MethodInfo;
import com.sun.beans.ServerInfo;

/**
 * @description: Rest请求处理类
 * @author: 星际一哥
 * @create: 2019-12-17 19:59
 */
public interface RestHandler {

    /**
     * 初始化服务器信息
     *
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);


    Object invokeRest(MethodInfo methodInfo);
}

