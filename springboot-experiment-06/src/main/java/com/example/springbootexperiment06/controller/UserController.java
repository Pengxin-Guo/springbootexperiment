package com.example.springbootexperiment06.controller;

import com.example.springbootexperiment06.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
@Validated  // 必须在controller组件声明启动方法级校验
public class UserController {
    @PostMapping("/users")
    public Map postUser(@Validated @RequestBody User user) {
        return Map.of("user", user);
    }

    @GetMapping("/users/{username}")
    public void getViolationException(  // 声明方法参数校验规则
            @Size(min = 2, max = 6, message = "用户参数信息错误")
            @PathVariable String username){

    }
}
