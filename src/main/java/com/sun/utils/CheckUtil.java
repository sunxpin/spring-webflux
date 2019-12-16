package com.sun.utils;

import com.sun.exception.CheckException;

import java.util.Arrays;

/**
 * @description: TODO
 * @author: 星际一哥
 * @create: 2019-12-16 19:00
 */
public class CheckUtil {

    public static final String[] INVALIDE_NAMES = {"admin", "guanliyuan"};

    public static void checkUser(String name) {
        Arrays.stream(INVALIDE_NAMES).filter(value -> value.equalsIgnoreCase(name)).findAny().ifPresent(val -> {
            throw new CheckException("name", val);
        });

    }
}
