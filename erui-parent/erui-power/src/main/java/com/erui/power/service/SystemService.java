package com.erui.power.service;

import com.erui.power.model.System;
import com.erui.power.vo.SystemVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

/**
 * Created by wangxiaodan on 2018/5/8.
 */
public interface SystemService {

    /**
     * 分页查询系统列表信息
     *
     * @param systemVo

     * @return
     */
    public PageInfo<System> findByPage(SystemVo systemVo);
}
