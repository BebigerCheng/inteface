package com.siy.protal.service.serviceImpl;

import com.siy.protal.persistence.UserMapper;
import com.siy.protal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public List getUserList() {
        return userMapper.getUserList();
    }
}
