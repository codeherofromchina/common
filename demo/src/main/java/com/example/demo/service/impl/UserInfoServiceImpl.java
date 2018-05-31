package com.example.service.impl;

import com.example.dao.UserInfoMapper;
import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 21:09 2018/5/29
 * @modified By
 */
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;
    @Override
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }
}
