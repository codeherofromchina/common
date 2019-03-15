package com.erui.report.service.impl;

import com.erui.report.dao.BuyerStatisticsMapper;
import com.erui.report.service.BuyerStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2019/3/15.
 */
@Service
public class BuyerStatisticsServiceImpl extends BaseService<BuyerStatisticsMapper> implements BuyerStatisticsService {
    @Autowired
    private BuyerStatisticsMapper buyerStatisticsMapper;

    @Override
    public PageInfo<Map<String, Object>> buyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }
}
