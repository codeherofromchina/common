package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverDetail;
import com.erui.order.v2.model.DeliverDetailExample;
import com.erui.order.v2.model.DeliverDetailWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverDetailMapper {
    int countByExample(DeliverDetailExample example);

    int deleteByExample(DeliverDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverDetailWithBLOBs record);

    int insertSelective(DeliverDetailWithBLOBs record);

    List<DeliverDetailWithBLOBs> selectByExampleWithBLOBs(DeliverDetailExample example);

    List<DeliverDetail> selectByExample(DeliverDetailExample example);

    DeliverDetailWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverDetailWithBLOBs record, @Param("example") DeliverDetailExample example);

    int updateByExampleWithBLOBs(@Param("record") DeliverDetailWithBLOBs record, @Param("example") DeliverDetailExample example);

    int updateByExample(@Param("record") DeliverDetail record, @Param("example") DeliverDetailExample example);

    int updateByPrimaryKeySelective(DeliverDetailWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DeliverDetailWithBLOBs record);

    int updateByPrimaryKey(DeliverDetail record);
}