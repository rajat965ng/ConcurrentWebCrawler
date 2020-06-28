package com.crawler.app.singleton;

import com.crawler.app.dto.Node;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Storage {

    private static final Map<String, Node> nodeMap = new ConcurrentHashMap<>();
    private static final Map<String, Status> statusMap = new ConcurrentHashMap<>();


    private Storage() {
    }

    public static void setStatusMap(String token,Status status) {
        synchronized (statusMap){
            statusMap.put(token,status);
        }
    }

    public static synchronized void setNodeMap(String token, Node node) {
        synchronized (nodeMap){
            nodeMap.put(token,node);
        }
    }

    public static Node getNode(String token) {
        return nodeMap.get(token);
    }

    public static Status getStatus(String token) {
        return statusMap.get(token);
    }

}
