package com.crawler.app.controller;

import com.crawler.app.dto.Node;
import com.crawler.app.service.ICrawlerService;
import com.crawler.app.singleton.Status;
import com.crawler.app.singleton.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api/v1")
public class WebCrawlerController {

    @Autowired
    private ICrawlerService crawlerService;

    @PostMapping("/crawl")
    public String submit(@RequestParam @NotNull(message = "input url cannot be null") String url, @RequestParam @Min(1) int depth) {
        return  crawlerService.execute(url, depth);
    }

    @GetMapping(value = "/crawl",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object result(@RequestParam @NotNull(message = "token cannot be null") String token) {
       if (Storage.getStatus(token).compareTo(Status.Processed)==0){
           System.out.println("Status: "+Storage.getStatus(token));
           Node node = Storage.getNode(token);
           return node;
       }
       return Storage.getStatus(token);
    }

}
