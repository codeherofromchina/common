package com.erui.power.service.impl;

import com.erui.power.dao.SystemMapper;
import com.erui.power.model.System;
import com.erui.power.model.SystemExample;
import com.erui.power.service.SystemService;
import com.erui.power.vo.SystemVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by wangxiaodan on 2018/5/8.
 */
@Service
public class SystemServiceImpl extends BaseService<SystemMapper> implements SystemService {


    public PageInfo<System> findByPage(SystemVo systemVo) {
        // 设置分页信息
        //PageHelper.startPage(systemVo.getPageNum(),systemVo.getPageSize());
        PageHelper.startPage(systemVo);
        SystemExample example = new SystemExample();
        SystemExample.Criteria criteria = example.createCriteria();
        // 设置系统名称模糊查询
        String name = systemVo.getName();
        if (StringUtils.isNotBlank(name)) {
            criteria.andNameLike(name);
        }
        // 设置系统编码模糊查询
        String bn = systemVo.getBn();
        if (StringUtils.isNotBlank(bn)) {
            criteria.andBnLike(bn);
        }
        // 根据系统是否启用查询
        if (systemVo.getEnable() != null) {
            criteria.andEnableEqualTo(systemVo.getEnable());
        }
        example.setOrderByClause("id asc");
        List<System> list = readMapper.selectByExample(example);
        return new PageInfo<>(list);
    }
}
