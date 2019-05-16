package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverConsignBookingSpace;
import com.erui.order.v2.model.DeliverConsignBookingSpaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverConsignBookingSpaceMapper {
    int countByExample(DeliverConsignBookingSpaceExample example);

    int deleteByExample(DeliverConsignBookingSpaceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverConsignBookingSpace record);

    int insertSelective(DeliverConsignBookingSpace record);

    List<DeliverConsignBookingSpace> selectByExample(DeliverConsignBookingSpaceExample example);

    DeliverConsignBookingSpace selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverConsignBookingSpace record, @Param("example") DeliverConsignBookingSpaceExample example);

    int updateByExample(@Param("record") DeliverConsignBookingSpace record, @Param("example") DeliverConsignBookingSpaceExample example);

    int updateByPrimaryKeySelective(DeliverConsignBookingSpace record);

    int updateByPrimaryKey(DeliverConsignBookingSpace record);
}