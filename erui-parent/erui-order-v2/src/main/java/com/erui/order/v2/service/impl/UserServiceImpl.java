package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.UserMapper;
import com.erui.order.v2.model.User;
import com.erui.order.v2.model.UserExample;
import com.erui.order.v2.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaodan on 2018/5/7.
 */
@Service("userServiceImplV2")
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper UserMapper;


    @Override
    public Long findIdByUserNo(String userNo) {
        if (StringUtils.isBlank(userNo)) {
            return null;
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNoEqualTo(userNo).andDeletedFlagEqualTo("N")
                .andStatusEqualTo("NORMAL");

        List<User> Users = UserMapper.selectByExample(example);
        if (Users != null && Users.size() > 0) {
            return Users.get(0).getId();
        }
        return null;
    }


    @Override
    public String findUserNoById(Long id) {
        if (id != null) {
            User User = UserMapper.selectByPrimaryKey(id);
            if (User != null) {
                return User.getUserNo();
            }
        }
        return null;
    }

    @Override
    public List<String> findUserNosByIds(List<Long> ids) {
        List<String> result = new ArrayList<>();
        if (ids != null && ids.size() > 0) {
            UserExample example = new UserExample();
            example.createCriteria().andIdIn(ids);
            List<User> users = UserMapper.selectByExample(example);
            if (users != null && users.size() > 0) {
                users.stream().filter(vo -> StringUtils.isNotBlank(vo.getUserNo())).forEach(
                        vo -> result.add(vo.getUserNo())
                );
            }
        }
        return result;
    }

    @Override
    public User findUserNoByUserNo(String userNo) {
        if (StringUtils.isBlank(userNo)) {
            return null;
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUserNoEqualTo(userNo).andDeletedFlagEqualTo("N")
                .andStatusEqualTo("NORMAL");

        List<User> Users = UserMapper.selectByExample(example);
        if (Users != null && Users.size() > 0) {
            return Users.get(0);
        }
        return null;
    }
}
