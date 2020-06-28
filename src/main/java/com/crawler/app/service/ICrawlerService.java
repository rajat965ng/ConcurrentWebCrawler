package com.crawler.app.service;

import com.crawler.app.dto.Node;

public interface ICrawlerService {
    public String execute(String url,int depth);
    public void scrap(String url, int depth,String token);
    public void crawl(Node node);
}
