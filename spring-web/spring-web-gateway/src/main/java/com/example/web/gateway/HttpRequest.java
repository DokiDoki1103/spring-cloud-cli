package com.example.web.gateway;

import org.jsoup.Jsoup;
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

        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/app-service/api/consumer?msg=1");
        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/app-service/api/helllo?msg=1");
        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/app-service/api/delay?msg=1");

        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/pc-service/api/helllo?msg=1");
        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/pc-service/api/delay?msg=1");



        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/book-service/api/helllo?msg=1");
        strings.add("http://8080.grd42ecd.0hpzibea.106.15.152.33.ip.goodrain.net/book-service/api/delay?msg=1");


        while (true){
            try {
                Jsoup.connect(strings.get((int) (Math.random()*strings.size()))).ignoreContentType(true).get();
                Thread.sleep(100);

            } catch (Exception ignored) {

            }
        }
    }
}
