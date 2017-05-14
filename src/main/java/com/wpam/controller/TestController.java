package com.wpam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController(value = "/test")
public class TestController {
    private RestTemplate template;

    @Autowired
    public TestController(RestTemplate template) {
        this.template = template;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String testa() {
        return "test OK!";
    }

    //    @Scheduled(fixedRate = 10000)
    public void test() {
        final ResponseEntity<String> result = template.getForEntity("https://localhost:9090/test", String.class);

        System.out.println(result.getBody());
    }
}
