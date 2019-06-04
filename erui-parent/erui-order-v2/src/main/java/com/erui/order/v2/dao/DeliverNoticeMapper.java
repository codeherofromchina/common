package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverNotice;
import com.erui.order.v2.model.DeliverNoticeExample;
import com.erui.order.v2.model.DeliverNoticeWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverNoticeMapper {
    int countByExample(DeliverNoticeExample example);

    int deleteByExample(DeliverNoticeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DeliverNoticeWithBLOBs record);

    int insertSelective(DeliverNoticeWithBLOBs record);

    List<DeliverNoticeWithBLOBs> selectByExampleWithBLOBs(DeliverNoticeExample example);

    List<DeliverNotice> selectByExample(DeliverNoticeExample example);

    DeliverNoticeWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DeliverNoticeWithBLOBs record, @Param("example") DeliverNoticeExample example);

    int updateByExampleWithBLOBs(@Param("record") DeliverNoticeWithBLOBs record, @Param("example") DeliverNoticeExample example);

    int updateByExample(@Param("record") DeliverNotice record, @Param("example") DeliverNoticeExample example);

    int updateByPrimaryKeySelective(DeliverNoticeWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(DeliverNoticeWithBLOBs record);

    int updateByPrimaryKey(DeliverNotice record);
}