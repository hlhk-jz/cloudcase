package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
@SpringBootApplication
//开启服务注册发现
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.api")
public class SpringBootNacosStart {
    public static void main(String[] args){
        SpringApplication.run(SpringBootNacosStart.class,args);
        System.out.print("启动成功[SpringBootNacosStart]！！！！！！！！！！！！！");
    }
}
