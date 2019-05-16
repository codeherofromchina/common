package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverNoticeAttachExample;
import com.erui.order.v2.model.DeliverNoticeAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverNoticeAttachMapper {
    int countByExample(DeliverNoticeAttachExample example);

    int deleteByExample(DeliverNoticeAttachExample example);

    int deleteByPrimaryKey(DeliverNoticeAttachKey key);

    int insert(DeliverNoticeAttachKey record);

    int insertSelective(DeliverNoticeAttachKey record);

    List<DeliverNoticeAttachKey> selectByExample(DeliverNoticeAttachExample example);

    int updateByExampleSelective(@Param("record") DeliverNoticeAttachKey record, @Param("example") DeliverNoticeAttachExample example);

    int updateByExample(@Param("record") DeliverNoticeAttachKey record, @Param("example") DeliverNoticeAttachExample example);
}