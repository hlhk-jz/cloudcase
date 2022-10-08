package com.controller;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.api.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 服务提供者
 */
@RestController
@Slf4j
public class TestController {
    @Autowired
    private TestService service;

    @GetMapping("/test")
    public String selectById(){
        return service.selectById();
    }

    @GetMapping("/test2")
    public Object selectByIds()throws Exception{
        String s = HttpUtil.get("http://localhost:9002/service/test");

        ExecutorService executorService = Executors.newFixedThreadPool(10);


        //创建请求信息
        List<Callable<String>> callables = new ArrayList<>();
        for (int i=0;i<100;i++){
            //使用匿名函数创建 callable ，无参返回String
            callables.add(()->{
                return HttpUtil.get("http://localhost:9002/service/test");
            });
        }

        /**
         * 批量发送，这里会阻塞，直到 封装的请求信
         * 息列表 都发送完毕，才返回，发送的时候是
         * 根据创建的线程池去发送的，比如上面设置
         * 了 10，那么一次就会同时发送 10 个请求
         */
        List<Future<String>> futures = executorService.invokeAll(callables);

        //处理返回结果
        List<String> repList = new ArrayList<>();
        Future<String> future;
        for (int i = 0; i < futures.size(); i++) {
            future = futures.get(i);
            repList.add(future.get());
        }

        System.out.println("==================执行了=====================");
        return repList;
    }


}
