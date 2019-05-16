package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchContractStandard;
import com.erui.order.v2.model.PurchContractStandardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchContractStandardMapper {
    int countByExample(PurchContractStandardExample example);

    int deleteByExample(PurchContractStandardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchContractStandard record);

    int insertSelective(PurchContractStandard record);

    List<PurchContractStandard> selectByExample(PurchContractStandardExample example);

    PurchContractStandard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchContractStandard record, @Param("example") PurchContractStandardExample example);

    int updateByExample(@Param("record") PurchContractStandard record, @Param("example") PurchContractStandardExample example);

    int updateByPrimaryKeySelective(PurchContractStandard record);

    int updateByPrimaryKey(PurchContractStandard record);
}