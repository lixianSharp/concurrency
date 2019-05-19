package com.mmall.concurrency.example.immutable;


import com.google.common.collect.Maps;
import com.mmall.concurrency.LoggerUtil;
import com.mmall.concurrency.annoations.ThreadSafe;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 9:34 2019/4/28
 * @Description:
 */
@ThreadSafe
public class ImmutableExample2 {
    private final static Integer a = 1;
    private final static String b = "2";
    private static Map<Integer, Integer> map = Maps.newHashMap();

    static {
        map.put(1, 2);
        map.put(3, 4);
        map.put(5, 6);
        map = Collections.unmodifiableMap(map);
        //通过Collectors.unmodifiableMap处理过之后的map是不允许被修改的
    }

    public static void main(String[] args) {
        map.put(1, 3);//这行会抛出错误,不可被修改 java.lang.UnsupportedOperationException
        LoggerUtil.logger.info("{}", map.get(1));
    }


}
