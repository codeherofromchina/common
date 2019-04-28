package com.erui.order.v2.dao;

import com.erui.order.v2.model.Uploaded;
import com.erui.order.v2.model.UploadedExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UploadedMapper {
    int countByExample(UploadedExample example);

    int deleteByExample(UploadedExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Uploaded record);

    int insertSelective(Uploaded record);

    List<Uploaded> selectByExample(UploadedExample example);

    Uploaded selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Uploaded record, @Param("example") UploadedExample example);

    int updateByExample(@Param("record") Uploaded record, @Param("example") UploadedExample example);

    int updateByPrimaryKeySelective(Uploaded record);

    int updateByPrimaryKey(Uploaded record);
}