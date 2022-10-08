package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
//开启服务注册发现
@EnableDiscoveryClient
public class ServiceSpringbootStart {
    public static void main(String[] args){
        SpringApplication.run(ServiceSpringbootStart.class,args);
        System.out.println("启动成功[ServiceSpringbootStart]！！！！！！！！！！！！！");
    }
}
