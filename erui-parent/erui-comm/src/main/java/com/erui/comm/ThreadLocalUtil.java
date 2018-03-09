package com.erui.comm;

/**
 * 线程存储工具类
 * Created by wangxiaodan on 2018/3/9.
 */
public class ThreadLocalUtil {
    private static ThreadLocal<Object> threadLocal = new ThreadLocal<>();


    public static void setObject(Object obj) {
        threadLocal.set(obj);
    }

    public static Object getObject() {
        return threadLocal.get();
    }
}
