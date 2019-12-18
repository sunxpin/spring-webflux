package com.sun.beans;

import lombok.Builder;
import lombok.Data;

/**
 * @description: 封装服务器信息
 * @author: 星际一哥
 * @create: 2019-12-17 19:46
 */
@Data
@Builder
public class ServerInfo {

    private String url;
}
