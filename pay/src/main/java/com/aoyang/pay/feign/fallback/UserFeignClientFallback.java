package com.aoyang.pay.feign.fallback;

import com.aoyang.pay.feign.UserFeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户中心远程访问client
 */
@Component
public class UserFeignClientFallback implements UserFeignClient {

    @Override
    public String getUser(@RequestParam("condition") String condition) {
        return "fallback";
    }

}