package com.config;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
/**
 *  添加或删除 redis 中的路由 就不用重启网关了
 *  参考 https://blog.csdn.net/zhuyu19911016520/article/details/86557165
 */
@Component
public class RouterController {
    @Autowired
    private RedisRouteDefinitionRepository redisRouteDefinitionRepository;

    /**当数据库添加路由后，调用该接口同时添加到redis中就不用从起网关了*/
    @PostMapping("/save")
    public void saveRouter(@RequestBody JSONObject jsonObject){
        //redisRouteDefinitionRepository.save();
    }

    /**删除 redis 中的路由信息，不用重启项目*/
    @DeleteMapping("/delet")
    public void delRouter(@RequestBody JSONObject jsonObject){
       // redisRouteDefinitionRepository.delete();
    }
}
