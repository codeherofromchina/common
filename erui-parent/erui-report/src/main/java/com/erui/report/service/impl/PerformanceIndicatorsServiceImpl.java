package com.erui.report.service.impl;

import com.erui.report.dao.PerformanceIndicatorsMapper;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.model.PerformanceIndicatorsExample;
import com.erui.report.service.PerformanceIndicatorsService;
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
public class PerformanceIndicatorsServiceImpl extends BaseService<PerformanceIndicatorsMapper> implements PerformanceIndicatorsService {
    @Autowired
    private PerformanceIndicatorsMapper performanceIndicatorsMapper;

    @Override
    public int add(PerformanceIndicators performanceIndicators) {
        int insert = performanceIndicatorsMapper.insert(performanceIndicators);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public int del(List<Integer> ids) {
        PerformanceIndicatorsExample example = new PerformanceIndicatorsExample();
        example.createCriteria().andIdIn(ids);
        int i = performanceIndicatorsMapper.deleteByExample(example);
        if (i > 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int update(PerformanceIndicators performanceIndicators) {
        int insert = performanceIndicatorsMapper.updateByPrimaryKey(performanceIndicators);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public PageInfo<PerformanceIndicators> list(Map<String, Object> params) {
        PageHelper.startPage(params);
        PerformanceIndicatorsExample example = new PerformanceIndicatorsExample();
        PerformanceIndicatorsExample.Criteria criteria = example.createCriteria();
        String prescription = (String)params.get("prescription"); // 指标时效
        String countryName = (String)params.get("countryName"); // 国家名称
        String orgName = (String)params.get("orgName"); // 事业部名称
        if (StringUtils.isNotBlank(prescription) ) {
            criteria.andPrescriptionEqualTo(prescription);
        }
        if(StringUtils.isNotBlank(countryName)){
            criteria.andCountryNameLike("%" + prescription + "%");
        }
        if(StringUtils.isNotBlank(orgName)){
            criteria.andOrgNameLike("%" + orgName + "%");
        }

        List<PerformanceIndicators> purchasingPowerList = performanceIndicatorsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }

    @Override
    public List<PerformanceIndicators> findByPrescription(List<String> prescriptionList) {
        PerformanceIndicatorsExample example = new PerformanceIndicatorsExample();
        example.createCriteria().andPrescriptionIn(prescriptionList);
        List<PerformanceIndicators> performanceIndicatorsList = performanceIndicatorsMapper.selectByExample(example);
        return performanceIndicatorsList;
    }
}
