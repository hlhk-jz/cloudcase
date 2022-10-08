package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//开启服务注册发现
@EnableDiscoveryClient
@SpringBootApplication
public class SpringBootGetwayStart {
    public static void main(String[] args){
        SpringApplication.run(SpringBootGetwayStart.class,args);
        System.out.print("=========>>>>>> getway 网关服务启动完成 <<<<<<<<=========");
    }
}
