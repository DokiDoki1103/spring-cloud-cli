package com.example.web.gateway.controller;

import com.example.web.gateway.mapper.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/app-service/api")
public class ApiController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello, app-service!";
    }

    @Autowired
    private RemoteProviderService remoteProviderService;

    @GetMapping("/consumer")
    public String consumer() {
        String hello = remoteProviderService.Message("我是从app过来的");
        return hello;
    }
    @RequestMapping("/call/{name}")
    public String call(@PathVariable String name) {
        return LocalTime.now() + "——服务提供者：" + name;
    }

    @GetMapping(value = "/delay",produces = "application/json")
    public Map<String,Object> delay() throws InterruptedException {
        int delay = new Random().nextInt(3000);
        // 模拟慢调用
        Thread.sleep(new Random().nextInt(delay));

        Map<String, Object> res = new HashMap<>();
        res.put("code",200);
        res.put("msg","success");
        res.put("delay",delay);
        return res;
    }
}
