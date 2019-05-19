package com.mmall.concurrency.example.threadLocal;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 17:02 2019/5/4
 * @Description:
 */
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder = new ThreadLocal<>();

    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    public static void remove(){
        requestHolder.remove();
    }
}
