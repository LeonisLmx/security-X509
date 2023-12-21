package com.example.client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @author urey.liu
 * @description
 * @date 2023/12/9 6:11 下午
 */
@RestController
public class TestController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String testMethod(){
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://127.0.0.1:443/", String.class);
        System.out.println(forEntity.getBody());
        return "securityRequest success!";
    }
}
