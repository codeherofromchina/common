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
    public PageInfo<Map<String, Object>> registerBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryRegisterBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }

    @Override
    public PageInfo<Map<String, Object>> membershipBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryMembershipBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }


    @Override
    public PageInfo<Map<String, Object>> applyBuyerList(int pageNum, int pageSize, Map<String, String> params) {
        PageHelper.startPage(pageNum, pageSize);

        List<Map<String, Object>> buyerList = buyerStatisticsMapper.findCountryApplyBuyerList(params);

        PageInfo pageInfo = new PageInfo(buyerList);
        return pageInfo;
    }
}
