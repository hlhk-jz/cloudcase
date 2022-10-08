/*
package com.controller;

import com.mapper.TestMapper;
import com.pojo.Stu;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

*/
/**
 * 多线程Callable使用invokeAll处理返回数据
 * 案例：多次查询表整合多次的返回列表,并返回
 *//*

@RestController
@AllArgsConstructor
public class ThreadTest {
    private TestMapper testMapper;

    @GetMapping("/thread/test")
    public Object show() throws Exception{
        //创建次数,查询 stu 表 3次分别查询记录 0,1；2,3；4,5  在整合返回
        List<Map<String, Object>> list = getParam();
        // 创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待。
        ExecutorService service = Executors.newFixedThreadPool(list.size());
        // 接收结果
        ArrayList<Callable<List<Stu>>> stus = new ArrayList<Callable<List<Stu>>>();
        for (int i = 0; i < list.size(); i++) {
            final int s = i;
            stus.add(new Callable<List<Stu>>() {
                @Override
                public List<Stu> call() throws Exception {
                    Map<String, Object> map = list.get(s);
                    return testMapper.selectStu(map);
                }
            });
        }

        // 处理返回结果 invokeALL等线程执行结束后统一返回
        List<Future<List<Stu>>> futureList = service.invokeAll(stus);
        Future<List<Stu>> future;
        List<Stu> fulist = new ArrayList<Stu>();
        for (int i = 0; i < futureList.size(); i++) {
            future = futureList.get(i);
            System.out.println("list = " + future);
            fulist.addAll(future.get());
        }
        return fulist;
    }

    private List<Map<String, Object>> getParam() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map1 = new HashMap<>();
        map1.put("spage", 0);
        map1.put("epage", 2);
        list.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("spage", 2);
        map2.put("epage", 2);
        list.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("spage", 4);
        map3.put("epage", 2);
        list.add(map3);
        return list;
    }



    @GetMapping("/thread/test1")
    public Object show2(){
        Map<String, Object> map1 = new HashMap<>();
        map1.put("spage", 0);
        map1.put("epage", 2);
        return testMapper.selectStu(map1);
    }
}
*/
