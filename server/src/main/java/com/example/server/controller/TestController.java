package com.example.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author urey.liu
 * @description
 * @date 2023/12/9 3:02 下午
 */
@RestController
public class TestController {

    @RequestMapping("/test")
    public String testMethod(){
        System.out.println("get request by other success~");
        return "security response success";
    }
}
