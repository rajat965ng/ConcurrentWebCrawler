# Web Crawler

## Environment Settings
#### JDK 11
#### Maven 3.6


## Libraries
#### Spring Boot
#### JSoup
#### JUnit

## Test Data
      |     URL                 |         Depth          |
      {"https://jsoup.org/",                2            },
      {"https://eventuate.io/",             2            },
      {"https://time.com/best-inventions/", 3            },
      {"https://www.ndtv.com/",             2            }


## Command to execute test cases

    mvn clean test
    
## Command to execute application 

    mvn clean spring-boot:run
    
## Curl command request/response examples
    
### Submit the request. Inputs:  url and depth. Output: token    
    curl --location --request POST 'localhost:8080/api/v1/crawl?url=https://jsoup.org/&depth=2'         

### Check the status of response using token obtained in above response
    curl localhost:8080/api/v1/crawl?token=c3373867-275c-4e66-8cea-86c5155d67c9