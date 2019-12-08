package com.aoyang.gateway.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Setter
@Getter
public class PredicateEntity {

    /**
     * 断言对应的Name
     */
    private String name;

    /**
     * 断言规则
     */
    private Map<String, String> args = new LinkedHashMap<>();
}