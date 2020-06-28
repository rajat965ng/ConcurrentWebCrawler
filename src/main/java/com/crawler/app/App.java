package com.crawler.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    //JDK 11 specific setting for Jsoup.
    {
        System.setProperty("https.protocols","TLSv1,TLSv1.1,TLSv1.2");
    }

    public static void main(String args[]) {
        SpringApplication.run(App.class,args);
    }

}
