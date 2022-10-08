package com.config;
import com.mapper.GatewayRouteMapper;
import com.pojo.GatewayRoute;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 项目初始化的时候将路由信息添加到内存
 */
@Slf4j
@Component
public class GatewayServiceHandler  implements ApplicationEventPublisherAware, CommandLineRunner {
    @Autowired
    private RedisRouteDefinitionRepository routeDefinitionWriter;
    private ApplicationEventPublisher publisher;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
    /**自己的获取数据dao*/
    @Autowired
    private GatewayRouteMapper gatewayRouteMapper;

    /**接口是在容器启动成功后的最后一步回调（类似开机自启动）*/
    @Override
    public void run(String... args){
        this.loadRouteConfig();
    }

    public String loadRouteConfig() {
        log.info("====开始加载=====网关配置信息=========");
        //删除redis里面的路由配置信息
        redisTemplate.delete(RedisRouteDefinitionRepository.GATEWAY_ROUTES);

        //从数据库拿到基本路由配置
        List<GatewayRoute> gatewayRouteList = gatewayRouteMapper.queryAllRoutes();
        gatewayRouteList.forEach(gatewayRoute -> {
            RouteDefinition definition=handleData(gatewayRoute);
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        });
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("=======网关配置信息===加载完成======路由配置数量：{}",gatewayRouteList.size());
        return "success";
    }

    /**
     * 路由数据转换公共方法
     * @param gatewayRoute
     * @return
     */
    private RouteDefinition handleData(GatewayRoute gatewayRoute){
        RouteDefinition definition = new RouteDefinition();
        Map<String, String> predicateParams = new HashMap<>(8);
        PredicateDefinition predicate = new PredicateDefinition();
        FilterDefinition filterDefinition = new FilterDefinition();
        Map<String, String> filterParams = new HashMap<>(8);

        URI uri = null;
        if(gatewayRoute.getUrl().startsWith("http")){
            //http地址
            uri = UriComponentsBuilder.fromHttpUrl(gatewayRoute.getUrl()).build().toUri();
        }else{
            //注册中心
            uri = UriComponentsBuilder.fromUriString("lb://"+gatewayRoute.getUrl()).build().toUri();
        }

        definition.setId(gatewayRoute.getServiceId());
        // 名称是固定的，spring gateway会根据名称找对应的PredicateFactory
        predicate.setName("Path");
        predicateParams.put("pattern",gatewayRoute.getPredicates());
        predicate.setArgs(predicateParams);

        // 名称是固定的, 路径去前缀
        filterDefinition.setName("StripPrefix");
        filterParams.put("_genkey_0", gatewayRoute.getFilters().toString());
        filterDefinition.setArgs(filterParams);
        definition.setFilters(Arrays.asList(filterDefinition));
        definition.setPredicates(Arrays.asList(predicate));
        definition.setUri(uri);
        return definition;
    }
}
