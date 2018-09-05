package com.erui.report.service.impl;

import com.erui.report.dao.SalesDataStatisticsMapper;
import com.erui.report.service.SalesDataStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/9/4.
 * 供应商业务实现类
 */
@Service
public class SalesDataStatisticsServiceImpl implements SalesDataStatisticsService {
    private static final String UNKNOW = "未知";
    @Autowired
    private SalesDataStatisticsMapper salesDataStatisticsMapper;

    @Override
    public Map<String, List<Object>> agencySupplierCountryStatisticsData(Map<String, Object> params) {
        Map<String, List<Object>> result = null;
        List<Map<String, Object>> datas = salesDataStatisticsMapper.agencySupplierCountryStatisticsData(params);
        result = _handleSimpleData(result, datas);
        return result;
    }

    @Override
    public Map<String, List<Object>> agencyOrgStatisticsData(Map<String, Object> params) {
        Map<String, List<Object>> result = null;
        List<Map<String, Object>> datas = salesDataStatisticsMapper.agencySupplierOrgStatisticsData(params);
        result = _handleSimpleData(result, datas);
        return result;
    }


    /**
     * 处理简单的名称和总数统计数据结果
     *
     * @param result
     * @param datas
     */
    private Map<String, List<Object>> _handleSimpleData(Map<String, List<Object>> result, List<Map<String, Object>> datas) {
        if (datas != null && datas.size() > 0) {
            result = new HashMap<>();
            List<Object> names = new ArrayList<>();
            List<Object> countNums = new ArrayList<>();
            for (Map<String, Object> data : datas) {
                Object name = data.get("name");
                names.add(name == null ? UNKNOW : name);
                countNums.add(data.get("total"));
            }
            result.put("name", names);
            result.put("counts", countNums);
        }
        return result;
    }
}
