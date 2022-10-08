package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpStart {
    public static void main(String[] args){
        SpringApplication.run(HttpStart.class,args);
        System.out.println("启动完成。。。");
    }
}
