package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchProjectExample;
import com.erui.order.v2.model.PurchProjectKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchProjectMapper {
    int countByExample(PurchProjectExample example);

    int deleteByExample(PurchProjectExample example);

    int deleteByPrimaryKey(PurchProjectKey key);

    int insert(PurchProjectKey record);

    int insertSelective(PurchProjectKey record);

    List<PurchProjectKey> selectByExample(PurchProjectExample example);

    int updateByExampleSelective(@Param("record") PurchProjectKey record, @Param("example") PurchProjectExample example);

    int updateByExample(@Param("record") PurchProjectKey record, @Param("example") PurchProjectExample example);
}