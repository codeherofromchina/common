package com.erui.order.v2.dao;

import com.erui.order.v2.model.PurchContractSignatories;
import com.erui.order.v2.model.PurchContractSignatoriesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PurchContractSignatoriesMapper {
    int countByExample(PurchContractSignatoriesExample example);

    int deleteByExample(PurchContractSignatoriesExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PurchContractSignatories record);

    int insertSelective(PurchContractSignatories record);

    List<PurchContractSignatories> selectByExample(PurchContractSignatoriesExample example);

    PurchContractSignatories selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PurchContractSignatories record, @Param("example") PurchContractSignatoriesExample example);

    int updateByExample(@Param("record") PurchContractSignatories record, @Param("example") PurchContractSignatoriesExample example);

    int updateByPrimaryKeySelective(PurchContractSignatories record);

    int updateByPrimaryKey(PurchContractSignatories record);
}