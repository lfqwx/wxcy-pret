package com.post.ibaties.secondary.xxx.user.service;

import com.post.ibaties.common.core.BaseService;
import com.post.ibaties.secondary.xxx.user.entity.User;
import com.post.ibaties.secondary.xxx.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends BaseService<UserMapper, User> {
    @Autowired
    UserMapper userMapper;

    public User findUserById(String id) {
        return userMapper.selectById(id);
    }
}
