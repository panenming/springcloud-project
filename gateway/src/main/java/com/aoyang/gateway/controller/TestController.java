package com.aoyang.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试controller
 */
@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello() {
        return "panenming.com";
    }

}