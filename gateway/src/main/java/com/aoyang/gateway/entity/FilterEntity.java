package com.aoyang.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class FilterEntity {

    /**
     * 过滤器对应的Name
     */
    private String name;

    /**
     * 路由规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}