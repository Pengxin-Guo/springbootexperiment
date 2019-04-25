package com.example.springbootexperiment06.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class User {
    @Size(min = 4, max = 6,
            message = "您输入的用户名为${validatedValue}，长度必须大于{min}小于{max}")
    private String userName;
    @Size(min = 6, message = "密码长度必须大于{min}")
    private String password;
    @Min(value = 10, message = "您的年龄必须大于{min}")
    @Max(value = 60, message = "您的年龄必须小于{max}")
    private int age;
    @Email(message = "Email地址不合法")
    private String email;
}
