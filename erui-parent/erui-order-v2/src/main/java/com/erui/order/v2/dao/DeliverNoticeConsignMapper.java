package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverNoticeConsignExample;
import com.erui.order.v2.model.DeliverNoticeConsignKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverNoticeConsignMapper {
    int countByExample(DeliverNoticeConsignExample example);

    int deleteByExample(DeliverNoticeConsignExample example);

    int deleteByPrimaryKey(DeliverNoticeConsignKey key);

    int insert(DeliverNoticeConsignKey record);

    int insertSelective(DeliverNoticeConsignKey record);

    List<DeliverNoticeConsignKey> selectByExample(DeliverNoticeConsignExample example);

    int updateByExampleSelective(@Param("record") DeliverNoticeConsignKey record, @Param("example") DeliverNoticeConsignExample example);

    int updateByExample(@Param("record") DeliverNoticeConsignKey record, @Param("example") DeliverNoticeConsignExample example);
}