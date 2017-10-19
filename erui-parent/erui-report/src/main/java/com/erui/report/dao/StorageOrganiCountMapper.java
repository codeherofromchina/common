package com.erui.report.dao;

import com.erui.report.model.StorageOrganiCount;
import com.erui.report.model.StorageOrganiCountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StorageOrganiCountMapper {
    int countByExample(StorageOrganiCountExample example);

    int deleteByExample(StorageOrganiCountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(StorageOrganiCount record);

    int insertSelective(StorageOrganiCount record);

    List<StorageOrganiCount> selectByExample(StorageOrganiCountExample example);

    StorageOrganiCount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") StorageOrganiCount record, @Param("example") StorageOrganiCountExample example);

    int updateByExample(@Param("record") StorageOrganiCount record, @Param("example") StorageOrganiCountExample example);

    int updateByPrimaryKeySelective(StorageOrganiCount record);

    int updateByPrimaryKey(StorageOrganiCount record);
}