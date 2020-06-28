package com.crawler.app.service;

import com.crawler.app.dto.Node;
import com.crawler.app.singleton.Status;
import com.crawler.app.singleton.Storage;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Executors;

@Service
public class CrawlerService implements ICrawlerService {



    @Override
    public String execute(String url, int depth) {
        UUID uuid =  UUID.randomUUID();
        String token = uuid.toString();
        Storage.setStatusMap(token, Status.Submitted);
        Executors.newCachedThreadPool().execute(() -> scrap(url,depth,token));
        return uuid.toString();
    }

    @Override
    public void scrap(String url,int depth,String token) {
        Node node = new Node(url,depth);
        node.setToken(token);
        crawl(node.build());
        Storage.setStatusMap(token, Status.Processed);
        Storage.setNodeMap(token,node);
    }

    @Override
    public void crawl(Node node) {
        node.build();
        if (node.getDetails()!=null && !node.getDetails().isEmpty()){
            node.getDetails().parallelStream().forEach(this::crawl);
        }
    }

}


