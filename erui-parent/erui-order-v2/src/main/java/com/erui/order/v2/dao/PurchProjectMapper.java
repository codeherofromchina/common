package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchProject;
import com.erui.order.v2.model.PurchProjectExample;
import com.erui.order.v2.model.PurchProjectKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchProjectMapper {
    int countByExample(PurchProjectExample example);

    int deleteByExample(PurchProjectExample example);

    int deleteByPrimaryKey(PurchProjectKey key);

    int insert(PurchProject record);

    int insertSelective(PurchProject record);

    List<PurchProject> selectByExample(PurchProjectExample example);

    PurchProject selectByPrimaryKey(PurchProjectKey key);

    int updateByExampleSelective(@Param("record") PurchProject record, @Param("example") PurchProjectExample example);

    int updateByExample(@Param("record") PurchProject record, @Param("example") PurchProjectExample example);

    int updateByPrimaryKeySelective(PurchProject record);

    int updateByPrimaryKey(PurchProject record);
}