package com.example.web.gateway.mapper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book",path = "/app-service/api")
public interface RemoteProviderService {

    @GetMapping("/call/{name}")
    String Message(@PathVariable("name") String msg);
}
