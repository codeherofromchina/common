package com.erui.report.service.impl;

import com.erui.report.dao.SalesmanNumsMapper;
import com.erui.report.model.SalesmanNums;
import com.erui.report.model.SalesmanNumsExample;
import com.erui.report.service.SalesmanNumsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class SalesmanNumsServiceImpl extends BaseService<SalesmanNums> implements SalesmanNumsService {
    @Autowired
    private SalesmanNumsMapper salesmanNumsMapper;

    @Override
    public int add(SalesmanNums SalesmanNums) {
        int insert = salesmanNumsMapper.insert(SalesmanNums);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public int del(List<Integer> ids) {
        SalesmanNumsExample example = new SalesmanNumsExample();
        example.createCriteria().andIdIn(ids);
        int i = salesmanNumsMapper.deleteByExample(example);
        if (i > 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int update(SalesmanNums SalesmanNums) {
        int insert = salesmanNumsMapper.updateByPrimaryKey(SalesmanNums);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public PageInfo<SalesmanNums> list(Map<String, Object> params) {
        PageHelper.startPage(params);
        SalesmanNumsExample example = new SalesmanNumsExample();
        SalesmanNumsExample.Criteria criteria = example.createCriteria();
        String prescription = (String)params.get("prescription"); // 指标时效
        String countryName = (String)params.get("countryName"); // 国家名称
        if (StringUtils.isNotBlank(prescription) ) {
            criteria.andPrescriptionEqualTo(prescription);
        }
        if(StringUtils.isNotBlank(countryName)){
            criteria.andCountryNameLike("%" + prescription + "%");
        }

        List<SalesmanNums> purchasingPowerList = salesmanNumsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }
}
