package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverConsign;
import com.erui.order.v2.model.DeliverConsignExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverConsignMapper {
    int countByExample(DeliverConsignExample example);

    int deleteByExample(DeliverConsignExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverConsign record);

    int insertSelective(DeliverConsign record);

    List<DeliverConsign> selectByExampleWithBLOBs(DeliverConsignExample example);

    List<DeliverConsign> selectByExample(DeliverConsignExample example);

    DeliverConsign selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverConsign record, @Param("example") DeliverConsignExample example);

    int updateByExampleWithBLOBs(@Param("record") DeliverConsign record, @Param("example") DeliverConsignExample example);

    int updateByExample(@Param("record") DeliverConsign record, @Param("example") DeliverConsignExample example);

    int updateByPrimaryKeySelective(DeliverConsign record);

    int updateByPrimaryKeyWithBLOBs(DeliverConsign record);

    int updateByPrimaryKey(DeliverConsign record);
}