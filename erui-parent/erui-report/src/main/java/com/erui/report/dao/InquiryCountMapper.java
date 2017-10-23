package com.erui.report.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.InquiryCountVo;
import org.apache.ibatis.annotations.Param;

import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryCountExample;

public interface InquiryCountMapper {
    int countByExample(InquiryCountExample example);

    int deleteByExample(InquiryCountExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InquiryCount record);

    int insertSelective(InquiryCount record);

    List<InquiryCount> selectByExample(InquiryCountExample example);

    InquiryCount selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InquiryCount record, @Param("example") InquiryCountExample example);

    int updateByExample(@Param("record") InquiryCount record, @Param("example") InquiryCountExample example);

    int updateByPrimaryKeySelective(InquiryCount record);

    int updateByPrimaryKey(InquiryCount record);

    //查询总金额
    Double selectTotalAmountByExample(InquiryCountExample example);
    //查询油气类产品数量
    int     selectProCountByIsOil(InquiryCountExample example);
    //查询产品Top3
    List<Map<String,Object>> selectProTop3(Map<String,Object>params);


}