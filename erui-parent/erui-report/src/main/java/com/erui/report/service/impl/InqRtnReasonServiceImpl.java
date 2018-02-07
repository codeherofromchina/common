package com.erui.report.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.InqRtnReasonMapper;
import com.erui.report.model.InqRtnReasonExample;
import com.erui.report.service.InqRtnReasonService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class InqRtnReasonServiceImpl extends  BaseService<InqRtnReasonMapper> implements InqRtnReasonService {

    @Override
    public List<Map<String, Object>> selectCountGroupByRtnSeason(Date startTime, Date endTime,Object area,Object org) {
        InqRtnReasonExample example = new InqRtnReasonExample();
        InqRtnReasonExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andRollinTimeLessThan(endTime);
        }
        if(area!=null&& StringUtil.isNotBlank(area.toString())){
            criteria.andInquiryAreaEqualTo(area.toString());
        }
        if(org!=null&&StringUtil.isNotBlank(org.toString())){
            criteria.andOrganizationEqualTo(org.toString());
        }
        return readMapper.selectCountGroupByRtnSeason(example);
    }

    @Override
    public List<Map<String, Object>> selectCountGroupByRtnSeasonAndArea(Date startTime, Date endTime) {
        InqRtnReasonExample example = new InqRtnReasonExample();
        InqRtnReasonExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andRollinTimeLessThan(endTime);
        }
        return readMapper.selectCountGroupByRtnSeasonAndArea(example);
    }

    @Override
    public List<Map<String, Object>> selectCountGroupByRtnSeasonAndOrg(Date startTime, Date endTime) {
        InqRtnReasonExample example = new InqRtnReasonExample();
        InqRtnReasonExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andRollinTimeLessThan(endTime);
        }
        return  readMapper.selectCountGroupByRtnSeasonAndOrg(example);
    }
}
