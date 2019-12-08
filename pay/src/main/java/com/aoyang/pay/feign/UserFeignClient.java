package com.aoyang.pay.feign;

import com.aoyang.pay.feign.fallback.UserFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user", fallback = UserFeignClientFallback.class)
public interface UserFeignClient {
    @GetMapping("/selectUser")
    String getUser(@RequestParam("condition") String condition);
}