package com.erui.order.v2.dao;

import com.erui.order.v2.model.Purch;
import com.erui.order.v2.model.PurchExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchMapper {
    int countByExample(PurchExample example);

    int deleteByExample(PurchExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Purch record);

    int insertSelective(Purch record);

    List<Purch> selectByExampleWithBLOBs(PurchExample example);

    List<Purch> selectByExample(PurchExample example);

    Purch selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Purch record, @Param("example") PurchExample example);

    int updateByExampleWithBLOBs(@Param("record") Purch record, @Param("example") PurchExample example);

    int updateByExample(@Param("record") Purch record, @Param("example") PurchExample example);

    int updateByPrimaryKeySelective(Purch record);

    int updateByPrimaryKeyWithBLOBs(Purch record);

    int updateByPrimaryKey(Purch record);
}