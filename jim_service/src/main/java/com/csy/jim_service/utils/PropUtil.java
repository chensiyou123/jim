package com.csy.jim_service.utils;

import com.jfinal.kit.Prop;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author WChao
 * @date 2019-06-15
 * 获取文件属性工具类
 */
public class PropUtil {
    private static Prop prop = null;
    private static final ConcurrentHashMap<String, Prop> map = new ConcurrentHashMap();

    private PropUtil() {
    }


    public static String get(String key) {
        return getProp().get(key);
    }
    public static Prop getProp() {
        if (prop == null) {
            throw new IllegalStateException("Load propties file by invoking PropKit.use(String fileName) method first.");
        } else {
            return prop;
        }
    }

}