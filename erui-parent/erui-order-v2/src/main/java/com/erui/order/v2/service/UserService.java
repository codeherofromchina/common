package com.erui.order.v2.service;

import com.erui.order.v2.model.User;

import java.util.List;

/**
 * Created by wangxiaodan on 2018/5/7.
 */
public interface UserService {
    /**
     * 通过员工号查找员工ID
     * @param userNo
     * @return
     */
    Long findIdByUserNo(String userNo);
    /**
     * 通过员工ID查找员工号
     * @param id
     * @return
     */
    String findUserNoById(Long id);

    /**
     * 通过员工ID查找员工号
     * @param ids
     * @return
     */
    List<String> findUserNosByIds(List<Long> ids);

    /**
     * 通过工号查询用户
     * @param userNo
     * @return
     */
    User findUserNoByUserNo(String userNo);

    /**
     * 通过主键查找用户信息
     * @param userId
     * @return
     */
    User findUserById(Long userId);
}
