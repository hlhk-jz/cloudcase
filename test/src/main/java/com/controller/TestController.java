package com.controller;

import cn.hutool.json.JSONObject;
import com.alibaba.fastjson.JSON;
import com.config.EncryptionDecryptionUtils;
import com.config.SecretKeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/test2")
    public Map getOne()throws Exception{
        Map map = new HashMap();
        Thread.sleep(20000002);
        map.put("a","OK");
        return map;
    }

    @PostMapping("/test1")
    public JSONObject test1(@RequestBody JSONObject object){
        log.info("入参："+object );
        JSONObject object1 = new JSONObject();
        object1.set("name","张三" );
        object1.set("age","22" );
        PrivateKey privateKey = SecretKeyUtils.loadPrivateKeyFromFile("pri.key");
        String s = EncryptionDecryptionUtils.encryptByAsymmetric(JSON.toJSONString(object1), privateKey);
        object1.set("sign",s );
        PublicKey publicKey = SecretKeyUtils.loadPublicKeyFromFile("pub1.key");
        String s1 = EncryptionDecryptionUtils.encryptByAsymmetric(JSON.toJSONString(object1), publicKey);
        JSONObject object2 = new JSONObject();
        object2.set("json",s1 );
        return object2;
    }

}
