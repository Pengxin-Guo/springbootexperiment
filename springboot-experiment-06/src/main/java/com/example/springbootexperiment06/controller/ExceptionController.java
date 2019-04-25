package com.example.springbootexperiment06.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestControllerAdvice  // 声明一个全局异常信息处理组件
public class ExceptionController {
    /**
     * 属性校验失败异常
     * @param exception
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)  // 声明此方法处理的异常类
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 声明返回的状态码
    public Map handleValidException(MethodArgumentNotValidException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        exception.getBindingResult()  // 获取绑定结果对象
                .getFieldErrors()     // 获取属性校验失败信息
                .forEach(e -> {
                    stringBuilder.append(e.getField());   // 获取校验失败名称
                    stringBuilder.append(": ");
                    stringBuilder.append(e.getDefaultMessage());  // 获取失败信息描述
                    stringBuilder.append("; ");
                });
        return Map.of("message", stringBuilder.toString());  // 返回拼接结果
    }

    /**
     * jackson json类型转换异常
     * @param exception
     * @return
     */
    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleInvalidFormatException(InvalidFormatException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        exception.getPath()
                .forEach(p -> {
                    stringBuilder.append("属性");
                    stringBuilder.append(p.getFieldName());
                    stringBuilder.append(", 您输入的值：" + exception.getValue());
                    stringBuilder.append(", 类型错误！");
                });
        return Map.of("message", stringBuilder.toString());
    }

    /**
     * 方法级参数校验失败异常
     * @param exception
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handleConstraintViolationException(ConstraintViolationException exception) {
        StringBuilder stringBuilder = new StringBuilder();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(v -> {
            stringBuilder.append(v.getMessage() + "; ");
        });
        return Map.of("message", stringBuilder.toString());
    }
}
