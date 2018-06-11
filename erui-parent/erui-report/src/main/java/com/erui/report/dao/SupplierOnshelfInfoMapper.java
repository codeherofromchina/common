package com.erui.report.dao;


import com.erui.report.model.SupplierOnshelfInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SupplierOnshelfInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SupplierOnshelfInfo record);

    int insertSelective(SupplierOnshelfInfo record);

    SupplierOnshelfInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupplierOnshelfInfo record);

    int updateByPrimaryKey(SupplierOnshelfInfo record);
}