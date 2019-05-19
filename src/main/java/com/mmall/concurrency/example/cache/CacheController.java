package com.mmall.concurrency.example.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 这个项目tomcat的端口是8889
 * 验证redis的
 */
@Controller
@RequestMapping("/cache")
public class CacheController {

    //注入刚才写的RedisClient
    @Autowired
    private RedisClient redisClient;

    //http://localhost:8889/cache/set
    //http://localhost:8889/cache/set?k=test1&v=1
    @RequestMapping("/set")
    @ResponseBody
    public String set(@RequestParam("k") String k, @RequestParam("v") String v)
            throws Exception {
        redisClient.set(k, v);
        return "SUCCESS";
    }

    //http://localhost:8889/cache/get
    //http://localhost:8889/cache/get?k=test1
    @RequestMapping("/get")
    @ResponseBody
    public String get(@RequestParam("k") String k) throws Exception {
        return redisClient.get(k);
    }
}
