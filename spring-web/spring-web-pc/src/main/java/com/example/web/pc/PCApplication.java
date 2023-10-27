package com.example.web.pc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients({"com.example.openfeign.mapper"})
@SpringBootApplication
public class PCApplication {
    public static void main(String[] args) {
        SpringApplication.run(PCApplication.class, args);
    }
}