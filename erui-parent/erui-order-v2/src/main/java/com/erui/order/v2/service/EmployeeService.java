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
    public Long findIdByUserNo(String userNo);
}
