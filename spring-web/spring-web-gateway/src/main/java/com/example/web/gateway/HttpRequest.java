package com.example.web.gateway;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

@Component
public class HttpRequest {
    @Async
    public void send(){
        List<String> strings = new ArrayList<>();

        strings.add("http://gateway:8080/app-service/api/consumer");
        strings.add("http://gateway:8080/app-service/api/helllo");
        strings.add("http://gateway:8080/app-service/api/delay");

        strings.add("http://gateway:8080/pc-service/api/helllo");
        strings.add("http://gateway:8080/pc-service/api/delay");
        strings.add("http://gateway:8080/pc-service/api/consumer");



        strings.add("http://gateway:8080/book-service/api/helllo");
        strings.add("http://gateway:8080/book-service/api/delay");


        while (true){
            try {
                Document document = Jsoup.connect(strings.get((int) (Math.random() * strings.size()))).ignoreContentType(true).get();
                System.out.println(document.body());
                Thread.sleep(50);

            } catch (Exception ignored) {

            }
        }
    }
}
