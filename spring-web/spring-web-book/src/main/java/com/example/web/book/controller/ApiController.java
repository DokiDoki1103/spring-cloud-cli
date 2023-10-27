package com.example.web.book.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
public class ApiController {



    @GetMapping("/hello")
    public String hello() {
        return "Hello, book-service!";
    }

    @RequestMapping("/call/{name}")
    public String call(@PathVariable String name) {
        return LocalTime.now() + "——图书服务：" + name;
    }



    @GetMapping(value = "/delay", produces = "application/json")
    public Map<String, Object> delay() throws InterruptedException {
        int delay = new Random().nextInt(3000);
        // 模拟慢调用
        Thread.sleep(new Random().nextInt(delay));

        Map<String, Object> res = new HashMap<>();
        res.put("code", 200);
        res.put("msg", "success");
        res.put("delay", delay);
        return res;
    }
}
