package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.PerformanceIndicatorsMapper;
import com.erui.report.model.PerformanceIndicators;
import com.erui.report.model.PerformanceIndicatorsExample;
import com.erui.report.service.CommonService;
import com.erui.report.service.PerformanceIndicatorsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class PerformanceIndicatorsServiceImpl extends BaseService<PerformanceIndicatorsMapper> implements PerformanceIndicatorsService {
    @Autowired
    private PerformanceIndicatorsMapper performanceIndicatorsMapper;
    @Autowired
    private CommonService commonService;

    @Override
    public int add(PerformanceIndicators performanceIndicators) {
        // 完善指标类型的其余信息
        Integer ptype = performanceIndicators.getPtype();
        if (1== ptype) {
            // 完善事业部名称信息
            Map<String,Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if(2 == ptype){
            // 完善国家信息
            Map<String,Object> countryInfoMap = commonService.findCountryInfoByBn(performanceIndicators.getCountryBn());
            performanceIndicators.setCountryName((String) countryInfoMap.get("countryName"));
            performanceIndicators.setAreaBn((String) countryInfoMap.get("areaBn"));
            performanceIndicators.setAreaName((String) countryInfoMap.get("areaName"));
        }
        performanceIndicators.setCreateTime(new Date());
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
        Integer ptype = performanceIndicators.getPtype();
        // 更新指标类型的其余信息
        if (1== ptype) {
            // 完善事业部名称信息
            Map<String,Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if(2 == ptype){
            // 完善国家信息
            Map<String,Object> countryInfoMap = commonService.findCountryInfoByBn(performanceIndicators.getCountryBn());
            performanceIndicators.setCountryName((String) countryInfoMap.get("countryName"));
            performanceIndicators.setAreaBn((String) countryInfoMap.get("areaBn"));
            performanceIndicators.setAreaName((String) countryInfoMap.get("areaName"));
        }
        performanceIndicators.setCreateTime(new Date());
        int insert = performanceIndicatorsMapper.updateByPrimaryKey(performanceIndicators);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public PageInfo<PerformanceIndicators> list(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"),(Integer) params.get("pageSize"));
        PerformanceIndicatorsExample example = new PerformanceIndicatorsExample();
        PerformanceIndicatorsExample.Criteria criteria = example.createCriteria();
        String startPrescription = (String) params.get("startPrescription");
        String endPrescription = (String) params.get("endPrescription");
        Date startDate = DateUtil.parseString2DateNoException(startPrescription,DateUtil.SHORT_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(endPrescription,DateUtil.SHORT_FORMAT_STR);
        if (startDate != null) {
            criteria.andStartPrescriptionGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andEndPrescriptionLessThanOrEqualTo(endDate);
        }
        String ptype = String.valueOf(params.get("ptype")); // 指标类型
        if (StringUtils.isNotBlank(ptype) && StringUtils.isNumeric(ptype)) {
            criteria.andPtypeEqualTo(Integer.parseInt(ptype));
        }
        String countryBn = (String)params.get("countryBn"); // 国家名称
        if (StringUtils.isNotBlank(countryBn)) {
            criteria.andCountryBnEqualTo(countryBn);
        }
        String orgId = String.valueOf(params.get("orgId")); // 事业部ID
        if(StringUtils.isNotBlank(orgId) && StringUtils.isNumeric(orgId)) {
            criteria.andOrgIdEqualTo(Integer.parseInt(orgId));
        }
        List<PerformanceIndicators> purchasingPowerList = performanceIndicatorsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }

}
