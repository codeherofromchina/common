package com.example.dao;

import com.example.model.UserInfo;
import org.springframework.data.repository.query.Param;

/**
 * @Author:SHIGS
 * @Description
 * @Date Created in 20:33 2018/5/29
 * @modified By
 */
public interface UserInfoMapper {
    UserInfo selectById(@Param("id") Integer id);
}
