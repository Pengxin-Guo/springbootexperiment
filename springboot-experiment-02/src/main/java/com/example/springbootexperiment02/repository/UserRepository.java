package com.example.springbootexperiment02.repository;

import com.example.springbootexperiment02.entity.Address;
import com.example.springbootexperiment02.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserRepository {
    @PersistenceContext
    private EntityManager em;

    /**
     * 添加用户，并返回包括数据库时间戳的用户对象
     */
    public User addUser(User user) {
        em.persist(user);
        em.refresh(user);
        return user;
    }

    /**
     * 添加地址，并指定地址对应的用户
     */
    public Address addAddress(Address address, int uid) {
        User user = em.find(User.class, uid);
        address.setUser(user);
        em.persist(address);
        return address;
    }

    /**
     * 更新指定ID用户的姓名
     */
    public User updateUser(int uid, String newName) {
        User user = new User();
        user.setId(uid);
        User newUser = em.merge(user);
        em.refresh(newUser);
        newUser.setName(newName);
        return newUser;
    }

    /**
     * 尝试使用merge()，以及find()2种方法分别实现
     * 更新指定地址为指定用户
     */
    public Address updateAddress(int aid, int uid) {
        /*
        // 第一种方法 merge实现
        Address address = new Address();
        address.setId(aid);
        Address newAddress = em.merge(address);
        em.refresh(newAddress);
        User user = new User();
        user.setId(uid);
        User newUser = em.merge(user);
        em.refresh(newUser);
        newAddress.setUser(newUser);
        return newAddress;
        */
        // 第二种方法 find实现
        Address address = em.find(Address.class, aid);
        User user = em.find(User.class, uid);
        address.setUser(user); // 事务结束自动更新
        return address;
    }

    /**
     * 返回指定用户的全部地址，没有返回空集合，而非null
     */
    public List<Address> listAddresses(int uid) {
        User user = em.find(User.class, uid);
        List<Address> list = user.getAddresses();
        List.of(list);
        return list;
    }

    public void removeAddress(int aid) {
        Address address = em.find(Address.class, aid);
        em.remove(address);
        return ;
    }

    /**
     * 删除用户，设置级联操作或手动删除相关地址
     */
    public void remaveUser(int uid) {
        User user = em.find(User.class, uid);
        em.remove(user);
        return ;
    }
}
