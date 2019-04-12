/**
 * 作者：chao
 * 时间：2019/4/10 14:42
 * 描述：
 */
package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {


    public List<User> getUser();

    public void insert(User user);

}
