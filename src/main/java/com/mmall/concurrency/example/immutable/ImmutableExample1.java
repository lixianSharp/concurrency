package com.mmall.concurrency.example.immutable;


import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.NotThreadSafe;

import java.util.HashMap;
import java.util.Map;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:34 2019/4/28
 * @Description:
 */
@NotThreadSafe
public class ImmutableExample1 {
    private final static Integer a = 1;
    private final static String b = "2";
    private final static Map<Integer, Integer> map = new HashMap<Integer, Integer>();//HashMap是线程不安全的

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
    }

    public static void main(String[] args) {
//        a = 2;
//        b = "3";
        //使用final修饰的引用类型，不能指向另外一个对象，但是里面的值是可以修改的。也就是引用的类型不允许被修改。
//        map = new HashMap<>();

        map.put(1, 3);
        LoggerUtil.logger.info("{}", map.get(1));
    }

    /**
     * final修饰方法传入的变量，如果这个变量是基本数据类型，那么它也是不允许被修改的
     *
     * @param a
     */
    private void test(final int a) {
//        a = 1;
    }
}
