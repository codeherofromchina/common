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

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
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
        if (1 == ptype) {
            // 完善事业部名称信息
            Map<String, Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if (2 == ptype) {
            // 完善国家信息
            Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(performanceIndicators.getCountryBn());
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
        if (1 == ptype) {
            // 完善事业部名称信息
            Map<String, Object> orgInfoMap = commonService.findOrgInfoById(performanceIndicators.getOrgId());
            performanceIndicators.setOrgName((String) orgInfoMap.get("orgName"));
        } else if (2 == ptype) {
            // 完善国家信息
            Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(performanceIndicators.getCountryBn());
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
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        PerformanceIndicatorsExample example = new PerformanceIndicatorsExample();
        PerformanceIndicatorsExample.Criteria criteria = example.createCriteria();
        String startPrescription = (String) params.get("startPrescription");
        String endPrescription = (String) params.get("endPrescription");
        Date startDate = DateUtil.parseString2DateNoException(startPrescription, DateUtil.SHORT_FORMAT_STR);
        Date endDate = DateUtil.parseString2DateNoException(endPrescription, DateUtil.SHORT_FORMAT_STR);
        if (startDate != null) {
            criteria.andEndPrescriptionGreaterThanOrEqualTo(startDate);
        }
        if (endDate != null) {
            criteria.andStartPrescriptionLessThanOrEqualTo(endDate);
        }
        String ptype = String.valueOf(params.get("ptype")); // 指标类型
        if (StringUtils.isNotBlank(ptype) && StringUtils.isNumeric(ptype)) {
            criteria.andPtypeEqualTo(Integer.parseInt(ptype));
        }
        String countryBn = (String) params.get("countryBn"); // 国家名称
        if (StringUtils.isNotBlank(countryBn)) {
            criteria.andCountryBnEqualTo(countryBn);
        }
        String orgId = String.valueOf(params.get("orgId")); // 事业部ID
        if (StringUtils.isNotBlank(orgId) && StringUtils.isNumeric(orgId)) {
            criteria.andOrgIdEqualTo(Integer.parseInt(orgId));
        }
        example.setOrderByClause("create_time desc");
        List<PerformanceIndicators> purchasingPowerList = performanceIndicatorsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }


    /**
     * 查找时间段内的事业部业绩指标信息
     *
     * @param params
     * @return {orgId:{name:'',totalPrice:10000,dayNum:n}}
     */
    @Override
    public Map<Integer, Map<String, Object>> performanceIndicatorsWhereTimeByOrg(Map<String, Object> params) {
        Map<Integer, Map<String, Object>> result = new HashMap<>();
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR);
            endDate = DateUtil.parseStringToDate(endTime, DateUtil.FULL_FORMAT_STR);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return result;
        }
        List<Map<String, Object>> performanceIndicatorsList = performanceIndicatorsMapper.selectPerformanceIndicatorsWhereTimeByOrg(params);
        for (Map<String, Object> performanceIndicatorInfo : performanceIndicatorsList) {
            Date startPrescription = (Date) performanceIndicatorInfo.get("startPrescription");
            Date endPrescription = (Date) performanceIndicatorInfo.get("endPrescription");
            BigDecimal quota = (BigDecimal) performanceIndicatorInfo.get("quota");
            Integer orgId = (Integer) performanceIndicatorInfo.get("org_id");
            String name = (String) performanceIndicatorInfo.get("name");
            name = StringUtils.isBlank(name) ? "其他" : name;

            int dayNum = DateUtil.inRangeDateDayNum(startPrescription, endPrescription, startDate, endDate);
            if (quota != null && quota.compareTo(BigDecimal.ZERO) > 0 && dayNum > 0) {
                int diffNum = DateUtil.diffDayNum(startPrescription, endPrescription);
                if (diffNum <= 0) {
                    continue;
                }
                BigDecimal totalPrice = quota.multiply(new BigDecimal(dayNum)).divide(new BigDecimal(diffNum),2,BigDecimal.ROUND_DOWN);

                Map<String, Object> map = result.get(orgId);
                if (map == null) {
                    map = new HashMap<>();
                    map.put("name", name);
                    map.put("dayNum", dayNum);
                    map.put("totalPrice", totalPrice);
                } else {
                    BigDecimal totalPrice02 = (BigDecimal) map.get("totalPrice");
                    map.put("totalPrice", totalPrice02.add(totalPrice));
                    Integer day = (Integer)map.get("dayNum");
                    map.put("dayNum", day + dayNum);
                }
                result.put(orgId, map);
            }
        }
        return result;
    }


    /**
     * 查找时间段内的地区业绩指标信息
     *
     * @param params
     * @return {area_bn:{name:'',totalPrice:10000,dayNum:n}}
     */
    @Override
    public Map<String, Map<String, Object>> performanceIndicatorsWhereTimeByArea(Map<String, Object> params) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR);
            endDate = DateUtil.parseStringToDate(endTime, DateUtil.FULL_FORMAT_STR);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return result;
        }
        List<Map<String, Object>> performanceIndicatorsList = performanceIndicatorsMapper.selectPerformanceIndicatorsWhereTimeByArea(params);
        for (Map<String, Object> performanceIndicatorInfo : performanceIndicatorsList) {
            Date startPrescription = (Date) performanceIndicatorInfo.get("startPrescription");
            Date endPrescription = (Date) performanceIndicatorInfo.get("endPrescription");
            BigDecimal quota = (BigDecimal) performanceIndicatorInfo.get("quota");
            String country_bn = (String) performanceIndicatorInfo.get("country_bn");
            String name = (String) performanceIndicatorInfo.get("name");
            name = StringUtils.isBlank(name) ? "其他" : name;

            int dayNum = DateUtil.inRangeDateDayNum(startPrescription, endPrescription, startDate, endDate);
            if (quota != null && quota.compareTo(BigDecimal.ZERO) > 0 && dayNum > 0) {
                int diffNum = DateUtil.diffDayNum(startPrescription, endPrescription);
                if (diffNum <= 0) {
                    continue;
                }
                BigDecimal totalPrice = quota.multiply(new BigDecimal(dayNum)).divide(new BigDecimal(diffNum),2,BigDecimal.ROUND_DOWN);

                Map<String, Object> map = result.get(country_bn);
                if (map == null) {
                    map = new HashMap<>();
                    map.put("name", name);
                    map.put("dayNum", dayNum);
                    map.put("totalPrice", totalPrice);
                } else {
                    BigDecimal totalPrice02 = (BigDecimal) map.get("totalPrice");
                    map.put("totalPrice", totalPrice02.add(totalPrice));
                    Integer day = (Integer)map.get("dayNum");
                    map.put("dayNum", day + dayNum);
                }
                result.put(country_bn, map);
            }
        }
        return result;
    }


    /**
     * 查找时间段内的国家业绩指标信息
     *
     * @param params
     * @return {country_bn:{name:'',totalPrice:10000,dayNum:n}}
     */
    @Override
    public Map<String, Map<String, Object>> performanceIndicatorsWhereTimeByCountry(Map<String, Object> params) {
        Map<String, Map<String, Object>> result = new HashMap<>();
        String startTime = (String) params.get("startTime");
        String endTime = (String) params.get("endTime");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR);
            endDate = DateUtil.parseStringToDate(endTime, DateUtil.FULL_FORMAT_STR);
        } catch (ParseException ex) {
            ex.printStackTrace();
            return result;
        }
        List<Map<String, Object>> performanceIndicatorsList = performanceIndicatorsMapper.selectPerformanceIndicatorsWhereTimeByCountry(params);
        for (Map<String, Object> performanceIndicatorInfo : performanceIndicatorsList) {
            Date startPrescription = (Date) performanceIndicatorInfo.get("startPrescription");
            Date endPrescription = (Date) performanceIndicatorInfo.get("endPrescription");
            BigDecimal quota = (BigDecimal) performanceIndicatorInfo.get("quota");
            String area_bn = (String) performanceIndicatorInfo.get("area_bn");
            String name = (String) performanceIndicatorInfo.get("name");
            name = StringUtils.isBlank(name) ? "其他" : name;

            int dayNum = DateUtil.inRangeDateDayNum(startPrescription, endPrescription, startDate, endDate);
            if (quota != null && quota.compareTo(BigDecimal.ZERO) > 0 && dayNum > 0) {
                int diffNum = DateUtil.diffDayNum(startPrescription, endPrescription);
                if (diffNum <= 0) {
                    continue;
                }
                BigDecimal totalPrice = quota.multiply(new BigDecimal(dayNum)).divide(new BigDecimal(diffNum),2,BigDecimal.ROUND_DOWN);
                Map<String, Object> map = result.get(area_bn);
                if (map == null) {
                    map = new HashMap<>();
                    map.put("name", name);
                    map.put("dayNum", dayNum);
                    map.put("totalPrice", totalPrice);
                } else {
                    BigDecimal totalPrice02 = (BigDecimal) map.get("totalPrice");
                    map.put("totalPrice", totalPrice02.add(totalPrice));
                    Integer day = (Integer)map.get("dayNum");
                    map.put("dayNum", day + dayNum);
                }
                result.put(area_bn, map);
            }
        }
        return result;
    }
}
