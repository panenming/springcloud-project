package com.aoyang.user.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: mybatis plus config
 * @Author: panenming
 * @CreateDate: 2019/12/5 下午6:40
 */
@Configuration
@MapperScan("com.aoyang.user.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件，自动识别数据库类型
     *
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
