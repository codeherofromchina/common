package com.erui.report.service.impl;

import com.erui.report.dao.InquirySkuMapper;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquirySkuExample;
import com.erui.report.service.InquirySKUService;
import com.erui.report.util.IsOilVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InquirySKUServiceImpl extends  BaseService<InquirySkuMapper> implements InquirySKUService {

    @Override
    public Integer selectSKUCountByTime(Date startDate, Date endDate) {
        InquirySkuExample e = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = e.createCriteria();
        if(startDate!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if(endDate!=null){
            criteria.andRollinTimeLessThan(endDate);
        }
        return readMapper.selectSKUCountByTime(e);
    }

    @Override
    public List<IsOilVo> selectCountGroupByIsOil(Date startDate, Date endDate) {
        InquirySkuExample e = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = e.createCriteria();
        if(startDate!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if(endDate!=null){
            criteria.andRollinTimeLessThan(endDate);
        }
        return readMapper.selectCountGroupByIsOil(e);
    }



    @Override
    public List<Map<String, Object>> selectProTop3(Map<String, Object> params) {
        List<Map<String, Object>> list = readMapper.selectProTop3(params);
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public List<CateDetailVo> selectSKUDetailByCategory(Date startTime,Date endTime) {
        InquirySkuExample example = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        criteria.andProCategoryIsNotNull();
        return readMapper. selectSKUDetailByCategory(example);
    }
}
