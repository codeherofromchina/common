package com.erui.power.service;

import com.erui.power.model.FuncPerm;

import java.util.List;

public interface FuncPermService {

    /**
     * 通过员工ID查找员工所有的权限
     * @param userId
     * @return
     */
    public List<FuncPerm> findByEmployee(Integer userId);


    /**
     * 查询系统中所有权限资源
     * @return
     */
    public List<FuncPerm> findAll();
}
