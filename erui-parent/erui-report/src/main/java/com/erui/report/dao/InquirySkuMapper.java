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

    /**
     * 查询询单商品次数
     * @param params  startTime endTime
     * @return
     */
     int selectInqGoodsCountByTime(Map<String,Object> params);
    /**
     * 查询平台和非平台的询价商品次数
     * @param params  startTime endTime
     * @return
     */
    Map<String,Object> selectPlatInfoByTime(Map<String,Object> params);
    /**
     * 查询油气和非油气的询价商品次数
     * @param params  startTime endTime
     * @return
     */
    Map<String,Object> selectIsOilInfoByCondition(Map<String,Object> params);

    /**
     * 查询分类 的 询价商品次数top3
     * @param params  startTime endTime
     * @return  proCategory ：分类  proCount ： 次数
     */
    List<Map<String,Object>> selectProTop3(Map<String,Object> params);
    /**
     * 查询油气和非油气商品次数
     * @param params  startTime endTime
     * @return
     */
    List<IsOilVo> selectCountGroupByIsOil(Map<String,Object> params);

    List<Map<String, Object>> selectCountGroupByIsPlat(InquirySkuExample example);
    /**
     * 查询各分类的询单商品次数和金额
     * @param params  startTime endTime
     * @return
     */
    List<CateDetailVo> selectSKUDetailByCategory(Map<String,Object> params);

     void  deleteByQuotetionNum(String quotetionNum);
     //获取sku区域数据汇总
    CustomerNumSummaryVO selectNumSummaryByExcample(InquirySkuExample example);
    //批量插入sku
    void insertSKUList(List<InquirySku> skuList);
}