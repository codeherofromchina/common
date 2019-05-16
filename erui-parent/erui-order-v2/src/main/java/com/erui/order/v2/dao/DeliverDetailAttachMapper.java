package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverDetailAttachExample;
import com.erui.order.v2.model.DeliverDetailAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverDetailAttachMapper {
    int countByExample(DeliverDetailAttachExample example);

    int deleteByExample(DeliverDetailAttachExample example);

    int deleteByPrimaryKey(DeliverDetailAttachKey key);

    int insert(DeliverDetailAttachKey record);

    int insertSelective(DeliverDetailAttachKey record);

    List<DeliverDetailAttachKey> selectByExample(DeliverDetailAttachExample example);

    int updateByExampleSelective(@Param("record") DeliverDetailAttachKey record, @Param("example") DeliverDetailAttachExample example);

    int updateByExample(@Param("record") DeliverDetailAttachKey record, @Param("example") DeliverDetailAttachExample example);
}