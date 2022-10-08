package com.api;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
@Service
@FeignClient(value = "nacos-service")
public interface TestService {
    @GetMapping("/service/test")
    String selectById();
}
