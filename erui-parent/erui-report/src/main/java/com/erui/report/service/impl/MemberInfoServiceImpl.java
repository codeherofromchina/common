package com.erui.report.service.impl;

import com.erui.report.dao.CommonMapper;
import com.erui.report.dao.MemberInfoStatisticsMapper;
import com.erui.report.service.CommonService;
import com.erui.report.service.MemberInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/12.
 */
@Service
public class MemberInfoServiceImpl implements MemberInfoService {

    @Autowired
    private MemberInfoStatisticsMapper memberInfoStatisticsMapper;

    /**
     * 按照地区统计会员等级
     * @param params
     * @return
     */
    @Override
    public Map<String, List<Object>> membershipGradeByArea(Map<String, Object> params) {
        List<Map<String,Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByArea(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipGradeByCountry(Map<String, Object> params) {
        List<Map<String,Object>> gradeList = memberInfoStatisticsMapper.membershipGradeByCountry(params);
        Map<String, List<Object>> result = _handleMemberGradeData(gradeList);
        return result;
    }

    @Override
    public Map<String, List<Object>> visitStatisticsByArea(Map<String, Object> params) {
        List<Map<String,Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByArea(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
        return result;
    }

    @Override
    public Map<String, List<Object>> visitStatisticsByCountry(Map<String, Object> params) {
        List<Map<String,Object>> visitStatisticsData = memberInfoStatisticsMapper.visitStatisticsByCountry(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(visitStatisticsData);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipByArea(Map<String, Object> params) {
        List<Map<String,Object>> membershipNumList = memberInfoStatisticsMapper.membershipByArea(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }

    @Override
    public Map<String, List<Object>> membershipByCountry(Map<String, Object> params) {
        List<Map<String,Object>> membershipNumList = memberInfoStatisticsMapper.membershipByCountry(params);
        Map<String, List<Object>> result = _handleVisitStatisticsData(membershipNumList);
        return result;
    }


    /**
     * 处理会员等级数据
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleMemberGradeData(List<Map<String,Object>> gradeList) {
        Map<String,List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> normalGradeList = new ArrayList<>();
        List<Object> topGradeList = new ArrayList<>();
        for (Map<String,Object> map :gradeList) {
            Object name = map.get("name");
            Object normalGrade = map.get("normalGrade");
            Object topGrade = map.get("topGrade");
            nameList.add(name);
            normalGradeList.add(normalGrade);
            topGradeList.add(topGrade);
        }
        result.put("nameList",nameList);
        result.put("normalGradeList",normalGradeList);
        result.put("topGradeList",topGradeList);
        return result;
    }


    /**
     * 处理会员等级数据
     * @param gradeList
     * @return
     */
    private Map<String, List<Object>> _handleVisitStatisticsData(List<Map<String,Object>> gradeList) {
        Map<String,List<Object>> result = new HashMap<>();
        List<Object> nameList = new ArrayList<>();
        List<Object> numList = new ArrayList<>();
        for (Map<String,Object> map :gradeList) {
            Object name = map.get("name");
            Object num = map.get("num");
            nameList.add(name);
            numList.add(num);
        }
        result.put("nameList",nameList);
        result.put("numList",numList);
        return result;
    }
}
