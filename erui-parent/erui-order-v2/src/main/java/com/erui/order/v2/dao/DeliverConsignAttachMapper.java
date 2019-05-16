package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverConsignAttach;
import com.erui.order.v2.model.DeliverConsignAttachExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverConsignAttachMapper {
    int countByExample(DeliverConsignAttachExample example);

    int deleteByExample(DeliverConsignAttachExample example);

    int insert(DeliverConsignAttach record);

    int insertSelective(DeliverConsignAttach record);

    List<DeliverConsignAttach> selectByExample(DeliverConsignAttachExample example);

    int updateByExampleSelective(@Param("record") DeliverConsignAttach record, @Param("example") DeliverConsignAttachExample example);

    int updateByExample(@Param("record") DeliverConsignAttach record, @Param("example") DeliverConsignAttachExample example);
}