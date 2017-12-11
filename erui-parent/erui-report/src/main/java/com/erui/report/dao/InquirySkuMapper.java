package com.erui.report.dao;

import com.erui.report.model.InquirySku;
import com.erui.report.model.InquirySkuExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int selectSKUCountByTime(InquirySkuExample example);
}