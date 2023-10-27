package com.example.openfeign.mapper;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book",path = "/book-service/api")
public interface RemoteProviderService {

    @GetMapping("/call/{name}")
    String Message(@PathVariable("name") String msg);
}
