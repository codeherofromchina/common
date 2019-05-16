package com.erui.order.v2.service;

/**
 * Created by wangxiaodan on 2018/5/7.
 */
public interface EmployeeService {
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
}
