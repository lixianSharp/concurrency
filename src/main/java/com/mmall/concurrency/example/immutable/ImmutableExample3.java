package com.mmall.concurrency.example.immutable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mmall.concurrency.annoations.ThreadSafe;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 10:23 2019/4/28
 * @Description: 不可变对象Guava中的：ImmutableXXX：Collection、List、Set、Map。。。
 */
@ThreadSafe
public class ImmutableExample3 {
    private final static ImmutableList<Integer> list = ImmutableList.of(1,2,3);

    private final static ImmutableSet set = ImmutableSet.copyOf(list);

    private final static ImmutableMap<Integer,Integer> map = ImmutableMap.of(1,1,2,2,3,3);
    private final static ImmutableMap<Integer,Integer> map2 = ImmutableMap.<Integer,Integer>builder()
                                        .put(1,2).put(3,4).put(5,6).build();

    public static void main(String[] args) {
//        list.add(4);//java.lang.UnsupportedOperationException
//        set.add(5);//java.lang.UnsupportedOperationException

//        map.put(1,4);//java.lang.UnsupportedOperationException 不允许被修改
//        map2.put(1,3);//java.lang.UnsupportedOperationException
    }
}
