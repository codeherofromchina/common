package com.erui.report.service.impl;

import com.erui.report.dao.CommonMapper;
import com.erui.report.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private CommonMapper commonMapper;


    @Override
    public List<Map<String, Object>> orgList() {
        List<Map<String, Object>> orgList = commonMapper.orgList();
        return orgList;
    }

    @Override
    public List<Map<String, Object>> areaList() {
        List<Map<String, Object>> areaList = commonMapper.areaList();
        return areaList;
    }

    @Override
    public List<Map<String, Object>> countryList(Map<String,Object> params) {
        List<Map<String, Object>> countryList = commonMapper.countryList(params);
        return countryList;
    }
}
