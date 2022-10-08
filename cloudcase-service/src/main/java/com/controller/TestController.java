package com.controller;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TestController {
    int i = 0;
    @GetMapping("/service/test")
    public String selectById(){
        System.out.println(" 请求==== "+(i++));
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int i = RandomUtils.nextInt(1, 100000000);
        return i+"";
    }
}
