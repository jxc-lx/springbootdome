/**
 * 作者：chao
 * 时间：2019/4/10 14:46
 * 描述：
 */
package com.example.service.impl;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("UserService")
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    public List<User> getUser() {
        List <User> userlist = new ArrayList<User>();
        userlist =  userMapper.selectAll();
        return userlist;
    }


    public void insert(User user) {
        userMapper.insert(user);

    }
}
