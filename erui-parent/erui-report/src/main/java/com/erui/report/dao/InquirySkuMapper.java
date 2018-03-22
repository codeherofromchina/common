package com.erui.report.dao;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.model.InquirySku;
import com.erui.report.model.InquirySkuExample;
import com.erui.report.util.CustomerNumSummaryVO;
import com.erui.report.util.IsOilVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface InquirySkuMapper {
    int countByExample(InquirySkuExample example);

    int deleteByExample(InquirySkuExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InquirySku record);

    int insertSelective(InquirySku record);

    List<InquirySku> selectByExample(InquirySkuExample example);

    InquirySku selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InquirySku record, @Param("example") InquirySkuExample example);

    int updateByExample(@Param("record") InquirySku record, @Param("example") InquirySkuExample example);

    int updateByPrimaryKeySelective(InquirySku record);

    int updateByPrimaryKey(InquirySku record);

    Integer selectSKUCountByTime(InquirySkuExample example);
    List<IsOilVo> selectCountGroupByIsOil(InquirySkuExample example);

    //查询产品Top3
    List<Map<String,Object>> selectProTop3(InquirySkuExample example);
    List<Map<String, Object>> selectCountGroupByIsPlat(InquirySkuExample example);

    List<CateDetailVo> selectSKUDetailByCategory(InquirySkuExample example);

     void  deleteByQuotetionNum(String quotetionNum);
     //获取sku区域数据汇总
    CustomerNumSummaryVO selectNumSummaryByExcample(InquirySkuExample example);
    //批量插入sku
    void insertSKUList(List<InquirySku> skuList);
}