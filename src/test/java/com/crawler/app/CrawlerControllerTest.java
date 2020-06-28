package com.crawler.app;

import com.crawler.app.singleton.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppTest.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CrawlerControllerTest {

    @LocalServerPort
    private int port;
    private String host = "http://localhost";

    @Autowired
    private TestRestTemplate restTemplate;

    private String crawlerUrl;


    @Before
    public void setup(){
        restTemplate = new TestRestTemplate();
        String uri = host+":"+port+"/api/v1";
        crawlerUrl = uri+"/crawl";
    }

    public static Object[][] data() {
        return new Object[][]{
                {"https://jsoup.org/",2},
                {"https://eventuate.io/",2},
                {"https://time.com/best-inventions/",3},
                {"https://www.ndtv.com/",2}
        };
    }

    @Test
    public void test_CrawlerApi()  {
        Arrays.asList(data()).forEach(objects -> {
            System.out.println("********************************************************");
                System.out.println("***URL: "+objects[0]+", depth: "+objects[1]+" ****");
                String postUrl = crawlerUrl+"?url="+objects[0]+"&depth="+objects[1]+"";
                String token = restTemplate.postForObject(postUrl,null,String.class);
                System.out.println("token: "+token);
                String getUrl = crawlerUrl+"?token="+token+"";
                Object output = restTemplate.getForObject(getUrl, Object.class);
                System.out.println("Output: " + output);
                boolean flag = true;
                while (flag) {
                    System.out.println("waiting for 5 sec...");
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    output = restTemplate.getForObject(getUrl, Object.class);
                    flag = (output instanceof Status);
                    System.out.println("Output: " + output);
                }
            System.out.println("********************************************************");
        });
    }

}
