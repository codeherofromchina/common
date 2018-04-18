package com.erui.report.service.impl;

import com.erui.comm.RateUtil;
import com.erui.report.dao.InquirySkuMapper;
import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquirySkuExample;
import com.erui.report.service.InquirySKUService;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.IsOilVo;
import com.erui.report.util.ParamsUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InquirySKUServiceImpl extends  BaseService<InquirySkuMapper> implements InquirySKUService {

    @Override
    public Integer selectSKUCountByTime(Date startDate, Date endDate,List<String> inquiryNums) {
        InquirySkuExample e = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = e.createCriteria();
        if(startDate!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if(endDate!=null){
            criteria.andRollinTimeLessThan(endDate);
        }
        if(inquiryNums!=null&&inquiryNums.size()>0){
            criteria.andQuotationNumIn(inquiryNums);
        }
        return readMapper.selectSKUCountByTime(e);
    }

    @Override
    public List<IsOilVo> selectCountGroupByIsOil(Map<String,Object> map) {

        return readMapper.selectCountGroupByIsOil(map);
    }



    @Override
    public List<Map<String, Object>> selectProTop3(Map<String,Object> params) {
       //获取分类 询价商品次数top3
        List<Map<String, Object>> list = readMapper.selectProTop3(params);
        //查询 总询价商品次数
        int skuCount = readMapper.selectInqGoodsCountByTime(params);
        if (CollectionUtils.isNotEmpty(list)) {
            list.parallelStream().forEach(m -> {
                BigDecimal s = new BigDecimal(String.valueOf(m.get("proCount")));
                if (skuCount > 0) {
                    m.put("proProportionl", RateUtil.intChainRate(s.intValue(), skuCount));
                } else {
                    m.put("proProportionl", 0d);
                }
            });
            return list;
        }
        return new ArrayList<>();
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
        return readMapper. selectSKUDetailByCategory(example);
    }

    @Override
    public List<Map<String, Object>> selectCountGroupByIsPlat(Date startTime, Date endTime) {
        InquirySkuExample example = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = example.createCriteria();
        if (startTime != null) {
            criteria.andRollinTimeGreaterThanOrEqualTo(startTime);
        }
        if (endTime != null) {
            criteria.andRollinTimeLessThan(endTime);
        }
        return readMapper. selectCountGroupByIsPlat(example);
    }

    @Override
    public Map<String, Object> selectInqGoodsInfoByCondition(Map<String, Object> params) {
        //查询询单商品 次数
        int goodsCount = readMapper.selectInqGoodsCountByTime(params);
        //获取环比商品次数
        Map<String, Object> chainParams = ParamsUtils.getChainParams(params);
        int chainGoodsCount = readMapper.selectInqGoodsCountByTime(chainParams);
        Map<String,Object> goodsMap=new HashMap<>();
        goodsMap.put("goodsCount",goodsCount);
        goodsMap.put("goodsChainAdd",goodsCount-chainGoodsCount);
        if(chainGoodsCount>0){
            goodsMap.put("goodsChainRate", RateUtil.intChainRate(goodsCount-chainGoodsCount,chainGoodsCount));
        }else {
            goodsMap.put("goodsChainRate",0d);
        }
        return goodsMap;
    }

    @Override
    public Map<String, Object> selectPlatInfoByCondition(Map<String, Object> params) {
        //查询 平台、非平台数量
        Map<String,Object> data=readMapper.selectPlatInfoByTime(params);
        //查询环比 平台、非平台数量
        Map<String, Object> chainParams = ParamsUtils.getChainParams(params);
        Map<String,Object> chainData=readMapper.selectPlatInfoByTime(chainParams);
        int platCount=Integer.parseInt(data.get("platCount").toString());
        int notPlatCount=Integer.parseInt(data.get("notPlatCount").toString());
        if(platCount+notPlatCount>0){
            data.put("platProportion",RateUtil.intChainRate(platCount,platCount+notPlatCount));
        }else {
            data.put("platProportion",0d);
        }
        int chainPlatCount=Integer.parseInt(chainData.get("platCount").toString());
        data.put("platChainAdd",platCount-chainPlatCount);
        if(chainPlatCount>0) {
            data.put("planChainRate", RateUtil.intChainRate(platCount - chainPlatCount, chainPlatCount));
        }else {
            data.put("planChainRate",0d);
        }
        return data;
    }

    @Override
    public Map<String, Object> selectIsOilInfoByCondition(Map<String, Object> params) {
        //查询油气 、非油气 商品次数
        Map<String,Object> data=readMapper.selectIsOilInfoByCondition(params);
        //查询环比 油气 、非油气 商品次数
       params= ParamsUtils.getChainParams(params);
        Map<String,Object> chainData=readMapper.selectIsOilInfoByCondition(params);
        //添加占比和环比
        int oil=Integer.parseInt(data.get("oil").toString());
        int notOil=Integer.parseInt(data.get("notOil").toString());
        if(oil+notOil>0){
            data.put("oiProportionl",RateUtil.intChainRate(oil,oil+notOil));
        }else {
            data.put("oiProportionl",0d);
        }
        int chainOil=Integer.parseInt(chainData.get("oil").toString());
        data.put("oilChainAdd",oil-chainOil);
        if(chainOil>0) {
            data.put("chainRate", RateUtil.intChainRate(oil - chainOil, chainOil));
        }else {
            data.put("chainRate",0d);
        }
        return data;
    }

}
