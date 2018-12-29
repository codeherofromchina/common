package com.erui.report.dao;


import com.erui.report.model.SupplierOnshelfInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SupplierOnshelfInfoMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(SupplierOnshelfInfo record);

    int insertSelective(SupplierOnshelfInfo record);

    SupplierOnshelfInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SupplierOnshelfInfo record);

    int updateByPrimaryKey(SupplierOnshelfInfo record);

    /**
     * 根据日期查询各供应商的已上架数据
     * @param params
     * @return
     */
    List<Map<String, Object>> selectOnshelfDetailGroupBySupplier(Map<String,String> params);
}