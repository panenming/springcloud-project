package com.aoyang.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.aoyang.gateway.entity.FilterEntity;
import com.aoyang.gateway.entity.PredicateEntity;
import com.aoyang.gateway.entity.RouteEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @Description: 动态路由
 * @Author: panenming
 * @CreateDate: 2019/12/6 上午10:30
 */
@Component
public class DynamicRoutingConfig implements ApplicationEventPublisherAware {
    private final Logger logger = LoggerFactory.getLogger(DynamicRoutingConfig.class);

    @Value("${gateway.data-id}")
    private String DATA_ID = "";

    @Value("${gateway.group}")
    private String GROUP = "";

    @Value("${gateway.server-addr}")
    private String SERVERADDR = "";


    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void refreshRouting() throws NacosException {
        ConfigService configService = NacosFactory.createConfigService(SERVERADDR);
        // 初始化
        String content = configService.getConfig(DATA_ID, GROUP, 5000);
        JSONObject config = JSONObject.parseObject(content);
        initRoute(config);
        configService.addListener(DATA_ID, GROUP, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {
                JSONObject config = JSONObject.parseObject(configInfo);
                boolean refreshGatewayRoute = config.getBoolean("refreshGatewayRoute");
                // 如果是其他需要更新的数据也可以放在这里更新
                if (refreshGatewayRoute) {
                    initRoute(config);
                } else {
                    logger.info("路由未发生变更");
                }
            }
        });
    }

    private void initRoute(JSONObject config) {
        logger.info("route change config : {}", config);
        List<RouteEntity> list = JSON.parseArray(config.getString("routeList")).toJavaList(RouteEntity.class);
        for (RouteEntity route : list) {
            update(assembleRouteDefinition(route));
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    /**
     * 路由更新
     *
     * @param routeDefinition
     * @return
     */
    public void update(RouteDefinition routeDefinition) {
        try {
            this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId()));
            routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
            this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this));
            logger.info("路由更新成功");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public RouteDefinition assembleRouteDefinition(RouteEntity routeEntity) {

        RouteDefinition definition = new RouteDefinition();

        // ID
        definition.setId(routeEntity.getId());

        // Predicates
        List<PredicateDefinition> pdList = new ArrayList<>();
        for (PredicateEntity predicateEntity : routeEntity.getPredicates()) {
            PredicateDefinition predicateDefinition = new PredicateDefinition();
            predicateDefinition.setArgs(predicateEntity.getArgs());
            predicateDefinition.setName(predicateEntity.getName());
            pdList.add(predicateDefinition);
        }
        definition.setPredicates(pdList);

        // Filters
        List<FilterDefinition> fdList = new ArrayList<>();
        for (FilterEntity filterEntity : routeEntity.getFilters()) {
            FilterDefinition filterDefinition = new FilterDefinition();
            filterDefinition.setArgs(filterEntity.getArgs());
            filterDefinition.setName(filterEntity.getName());
            fdList.add(filterDefinition);
        }
        definition.setFilters(fdList);

        // URI
        URI uri = UriComponentsBuilder.fromUriString(routeEntity.getUri()).build().toUri();
        definition.setUri(uri);

        return definition;
    }
}
