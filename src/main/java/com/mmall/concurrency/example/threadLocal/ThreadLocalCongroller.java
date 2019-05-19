package com.mmall.concurrency.example.threadLocal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Aauthor xianyuan_li@qq.com
 * @Date: Create in 17:15 2019/5/4
 * @Description:
 */
@Controller
@RequestMapping("/threadLocal")
public class ThreadLocalCongroller {

    //在浏览器中输入 http://localhost:8080/threadLocal/test
    @RequestMapping("/test")
    @ResponseBody
    public Long test() {
        return RequestHolder.getId();
    }
}
