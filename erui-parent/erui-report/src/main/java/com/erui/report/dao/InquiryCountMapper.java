package com.erui.report.dao;

import com.erui.report.model.InquiryCount;
import com.erui.report.model.InquiryCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

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

    Double selectTotalAmountByExample(@Param("example") InquiryCountExample example);
}