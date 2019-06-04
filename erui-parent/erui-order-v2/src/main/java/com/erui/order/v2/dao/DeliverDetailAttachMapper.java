package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverDetailAttach;
import com.erui.order.v2.model.DeliverDetailAttachExample;
import com.erui.order.v2.model.DeliverDetailAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverDetailAttachMapper {
    int countByExample(DeliverDetailAttachExample example);

    int deleteByExample(DeliverDetailAttachExample example);

    int deleteByPrimaryKey(DeliverDetailAttachKey key);

    int insert(DeliverDetailAttach record);

    int insertSelective(DeliverDetailAttach record);

    List<DeliverDetailAttach> selectByExample(DeliverDetailAttachExample example);

    DeliverDetailAttach selectByPrimaryKey(DeliverDetailAttachKey key);

    int updateByExampleSelective(@Param("record") DeliverDetailAttach record, @Param("example") DeliverDetailAttachExample example);

    int updateByExample(@Param("record") DeliverDetailAttach record, @Param("example") DeliverDetailAttachExample example);

    int updateByPrimaryKeySelective(DeliverDetailAttach record);

    int updateByPrimaryKey(DeliverDetailAttach record);
}