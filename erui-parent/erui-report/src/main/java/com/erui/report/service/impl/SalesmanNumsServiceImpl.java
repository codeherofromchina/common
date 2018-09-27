package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SalesmanNumsMapper;
import com.erui.report.model.SalesmanNums;
import com.erui.report.model.SalesmanNumsExample;
import com.erui.report.service.CommonService;
import com.erui.report.service.SalesmanNumsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class SalesmanNumsServiceImpl extends BaseService<SalesmanNumsMapper> implements SalesmanNumsService {
    @Autowired
    private SalesmanNumsMapper salesmanNumsMapper;
    @Autowired
    private CommonService commonService;

    @Override
    public int add(List<SalesmanNums> salesmanNumsList) throws Exception {
        Date now = new Date();
        for (SalesmanNums vo : salesmanNumsList) {
            vo.setId(null);
            vo.setCreateTime(now);
            String countryBn = vo.getCountryBn();
            Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(countryBn);
            vo.setCountryName((String) countryInfoMap.get("countryName"));
            int insert = salesmanNumsMapper.insert(vo);
            if (insert < 1) {
                throw new Exception("数据新增数据库错误");
            }
        }
        return 0;
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
    public int update(SalesmanNums salesmanNums) {
        String countryBn = salesmanNums.getCountryBn();
        Map<String, Object> countryInfoMap = commonService.findCountryInfoByBn(countryBn);
        salesmanNums.setCountryName((String) countryInfoMap.get("countryName"));
        salesmanNums.setCreateTime(new Date());
        int insert = salesmanNumsMapper.updateByPrimaryKey(salesmanNums);
        return insert > 0 ? 0 : 1;
    }

    @Override
    public PageInfo<SalesmanNums> list(Map<String, Object> params) {
        PageHelper.startPage((Integer) params.get("pageNum"), (Integer) params.get("pageSize"));
        SalesmanNumsExample example = new SalesmanNumsExample();
        SalesmanNumsExample.Criteria criteria = example.createCriteria();
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
        String countryBn = (String) params.get("countryBn"); // 国家编码
        if (StringUtils.isNotBlank(countryBn)) {
            criteria.andCountryBnEqualTo(countryBn);
        }
        String createUserName = (String) params.get("createUserName"); // 创建
        if (StringUtils.isNotBlank(createUserName)) {
            criteria.andCreateUserNameEqualTo("%" + createUserName + "%");
        }
        example.setOrderByClause("create_time desc");
        List<SalesmanNums> purchasingPowerList = salesmanNumsMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(purchasingPowerList);
        return pageInfo;
    }

    @Override
    public Map<String, Integer> manTotalNumByCountry(Map<String, Object> params) {
        Map<String, Integer> result = new HashMap<>();
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
        List<Map<String, Object>> numsList = salesmanNumsMapper.selectNumsWhereTime(params);

        for (Map<String, Object> nums : numsList) {
            Date startPrescription = (Date) nums.get("start_prescription");
            Date endPrescription = (Date) nums.get("end_prescription");
            Integer num = (Integer) nums.get("num");
            String countryName = (String) nums.get("country_name");
            countryName = StringUtils.isBlank(countryName) ? "未知" : countryName;

            int dayNum = DateUtil.inRangeDateDayNum(startPrescription, endPrescription, startDate, endDate);
            if (num != null && num > 0 && dayNum > 0) {
                int diffNum = DateUtil.diffDayNum(startPrescription, endPrescription);
                if (diffNum <= 0) {
                    continue;
                }
                int n = (int) ((double) dayNum / diffNum * num);

                Integer integer = result.get(countryName);
                if (integer == null) {
                    result.put(countryName, n);
                } else {
                    result.put(countryName, n + integer);
                }
            }
        }
        return result;
    }

    @Override
    public Map<String, Integer> manTotalNumByArea(Map<String, Object> params) {
        Map<String, Integer> result = new HashMap<>();
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
        List<Map<String, Object>> numsList = salesmanNumsMapper.selectNumsWhereTime(params);

        for (Map<String, Object> nums : numsList) {
            Date startPrescription = (Date) nums.get("start_prescription");
            Date endPrescription = (Date) nums.get("end_prescription");
            Integer num = (Integer) nums.get("num");
            String areaName = (String) nums.get("area_name");
            areaName = StringUtils.isBlank(areaName) ? "未知" : areaName;

            int dayNum = DateUtil.inRangeDateDayNum(startPrescription, endPrescription, startDate, endDate);
            if (num != null && num > 0 && dayNum > 0) {
                int diffNum = DateUtil.diffDayNum(startPrescription, endPrescription);
                if (diffNum <= 0) {
                    continue;
                }
                int n = (int) ((double) dayNum / diffNum * num);

                Integer integer = result.get(areaName);
                if (integer == null) {
                    result.put(areaName, n);
                } else {
                    result.put(areaName, n + integer);
                }
            }
        }
        return result;
    }
}
