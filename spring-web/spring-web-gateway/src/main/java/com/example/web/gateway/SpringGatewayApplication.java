package com.example.web.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class SpringGatewayApplication {

    public static void main(String[] args) {

        SpringApplication.run(SpringGatewayApplication.class,args);
    }
}