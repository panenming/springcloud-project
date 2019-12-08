package com.aoyang.pay.controller;

import com.aoyang.pay.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: controller
 * @Author: panenming
 * @CreateDate: 2019/12/4 下午9:30
 */
@RestController
@RequestMapping
public class TestController {
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/testFeignSelectUser")
    public String testFeign(@RequestParam("condition") String condition) {
        return userFeignClient.getUser(condition);
    }
}
