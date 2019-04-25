package com.example.springbootexperiment07.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Component  // 创建可复用的序列化组件
public class EncryptorComponent {
    @Value("${my.secretkey}")
    private String secretKey;
    @Value("${my.salt}")
    private String salt;
    @Autowired  // 自动注入容器中的jacksonz组件
    private ObjectMapper mapper;
    // 加密
    public String encrypt(Map payload) {
        try {
            String json = mapper.writeValueAsString(payload); // 将Map对象序列化为json字符串
            return Encryptors.text(secretKey, salt).encrypt(json); // 加密
        } catch (JsonProcessingException e) {
        }
        return null;
    }

    // 解密
    public Map<String, Object> decrypt(String encryptString) {
        try {
            String json = Encryptors.text(secretKey, salt).decrypt(encryptString);  // 解密
            return mapper.readValue(json, Map.class);  // 将json字符串反序列为Map对象
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录");
        }
    }
}
