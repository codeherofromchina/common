package com.erui.power.service.impl;

import com.erui.power.dao.SystemMapper;
import com.erui.power.model.System;
import com.erui.power.model.SystemExample;
import com.erui.power.service.SystemService;
import com.erui.power.vo.SystemVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by wangxiaodan on 2018/5/8.
 */
@Service
public class SystemServiceImpl extends BaseService<SystemMapper> implements SystemService {

    private static final SimpleDateFormat seqDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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

    @Override
    public boolean delete(Integer id) {

        if (readMapper.selectByPrimaryKey(Long.valueOf(id)) != null){
            writeMapper.deleteByPrimaryKey(Long.valueOf(id));

            return true;
        }
        return false;
    }

    @Override
    public boolean add(System system) {

        if (!bnCodeExists(system.getBn())){

            system.setCreatedTime(new Date());
            writeMapper.insert(system);
            return true;
        }

        return false;
    }

    //判断bn是否存在
    private boolean bnCodeExists(String bn) {
        SystemExample example = new SystemExample();
        SystemExample.Criteria criteria = example.createCriteria();

        criteria.andBnEqualTo(bn);
        return readMapper.countByExample(example) > 0;
    }

    @Override
    public boolean update(System system) {

        system.setCreatedTime(new Date());
        writeMapper.updateByPrimaryKey(system);
        return true;
    }
}
