package com.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 全局过滤器，可以定义多个，如果有多个根据 getOrder 方法排优先级
 * 返回数越小，优先级越高
 */
@Component
@Slf4j
public class BaseFilter  implements GlobalFilter, Ordered {
    /**
     * 返回数越小，优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("***********全局过滤器***********"+new Date());
        String uname = exchange.getRequest().getQueryParams().getFirst("uname");
        if (false){
            log.info("******用户名为null,非法用户*****");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }
}
