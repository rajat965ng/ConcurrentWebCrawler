package com.crawler.app.dto;

import com.crawler.app.singleton.Status;
import com.crawler.app.singleton.Storage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

public class Node {

    private int totalLinks;
    private int totalImages;
    private String pageTitle;
    private String pageLink;
    private int imageCount;
    private int level;

    private String token;

    private Queue<Node> details;

    public Node(String pageLink, int level) {
        this.pageLink = pageLink;
        this.level = level;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getLevel() {
        return level;
    }

    public int getTotalLinks() {
        return totalLinks;
    }

    public int getTotalImages() {
        return totalImages;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public String getPageLink() {
        return pageLink;
    }

    public int getImageCount() {
        return imageCount;
    }

    public Queue<Node> getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return "Node{" +
                "totalLinks=" + totalLinks +
                ", totalImages=" + totalImages +
                ", pageTitle='" + pageTitle + '\'' +
                ", pageLink='" + pageLink + '\'' +
                ", imageCount=" + imageCount +
                ", level=" + level +
                ", details=" + details +
                '}';
    }

    public Node build() {
        try {
            Storage.setStatusMap(token, Status.InProcess);
            Document document = Jsoup.connect(pageLink).get();
            Set<String> links = document.select("a[href]").eachAttr("href").parallelStream().filter(s -> s.contains("http")).collect(Collectors.toSet());
            Set<String> images = new HashSet<>(document.select("img[src~=(?i)\\.(png|jpe?g|gif)]").eachAttr("src"));

            this.totalLinks = links.size();
            this.totalImages = links.size();
            this.pageTitle = document.title();
            this.imageCount = images.size();

            if (level>1) {
                details = new LinkedList<>();
                level = level-1;
                links.parallelStream().limit(20).map(link -> {
                   Node node = new Node(link,level);
                   node.setToken(token);
                   return node;
                }).forEach(details::add);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            Storage.setStatusMap(token, Status.Failed);
        }
        return this;
    }
}
