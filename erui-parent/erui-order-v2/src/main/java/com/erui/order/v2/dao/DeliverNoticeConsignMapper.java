package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverNoticeConsign;
import com.erui.order.v2.model.DeliverNoticeConsignExample;
import com.erui.order.v2.model.DeliverNoticeConsignKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverNoticeConsignMapper {
    int countByExample(DeliverNoticeConsignExample example);

    int deleteByExample(DeliverNoticeConsignExample example);

    int deleteByPrimaryKey(DeliverNoticeConsignKey key);

    int insert(DeliverNoticeConsign record);

    int insertSelective(DeliverNoticeConsign record);

    List<DeliverNoticeConsign> selectByExample(DeliverNoticeConsignExample example);

    DeliverNoticeConsign selectByPrimaryKey(DeliverNoticeConsignKey key);

    int updateByExampleSelective(@Param("record") DeliverNoticeConsign record, @Param("example") DeliverNoticeConsignExample example);

    int updateByExample(@Param("record") DeliverNoticeConsign record, @Param("example") DeliverNoticeConsignExample example);

    int updateByPrimaryKeySelective(DeliverNoticeConsign record);

    int updateByPrimaryKey(DeliverNoticeConsign record);
}