package com.example.springbootexperiment02;

import com.example.springbootexperiment02.entity.Address;
import com.example.springbootexperiment02.entity.User;
import com.example.springbootexperiment02.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    /**
     * 编写单元测试用例，测试方法实现的结果是否正确
     */

    @Test
    public void addUserTest() {
        User user = userRepository.addUser(new User("BO"));
        log.debug("{}", user.getInsertTime());
    }

    @Test
    public void addAddressTest() {
        Address address = userRepository.addAddress(new Address("888"), 4);
        log.debug("{}", address.getDetail());
    }

    @Test
    public void updateUserTest() {
        User user = userRepository.updateUser(1, "GPX");
        log.debug("{}", user.getName());
    }

    @Test
    public void updateAddressTest() {
        Address address = userRepository.updateAddress(2, 2);
        log.debug("{}", address.getUser().getId());
    }

    @Test
    public void listAddressesTest() {
        List<Address> list = userRepository.listAddresses(1);
        log.debug("{}", list);
    }

    @Test
    public void removeAddressTest() {
        userRepository.removeAddress(1);
    }

    @Test
    public void remaveUserTest() {
        userRepository.remaveUser(2);
    }
}
