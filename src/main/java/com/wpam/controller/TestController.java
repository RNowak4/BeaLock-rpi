package com.wpam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestController {
    private RestTemplate template;

    @Autowired
    public TestController(RestTemplate template) {
        this.template = template;
    }

    @Scheduled(fixedRate = 500)
    public void test() {
        final ResponseEntity<String> result = template.getForEntity("https://localhost:9090/test", String.class);

        System.out.println(result.getBody());
    }
}
