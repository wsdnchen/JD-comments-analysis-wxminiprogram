package com.scullincw.wechatserverside.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/wechat")
@RestController
public class HelloWorldController {
    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }
}