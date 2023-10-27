package com.example.web.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class Listener  implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    HttpRequest httpRequest;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        for (int i = 0; i < 10; i++) {
            httpRequest.send();
        }
    }
}
