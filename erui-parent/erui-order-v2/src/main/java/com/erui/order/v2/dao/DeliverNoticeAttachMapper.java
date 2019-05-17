package com.erui.order.v2.dao;

import com.erui.order.v2.model.DeliverNoticeAttach;
import com.erui.order.v2.model.DeliverNoticeAttachExample;
import com.erui.order.v2.model.DeliverNoticeAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DeliverNoticeAttachMapper {
    int countByExample(DeliverNoticeAttachExample example);

    int deleteByExample(DeliverNoticeAttachExample example);

    int deleteByPrimaryKey(DeliverNoticeAttachKey key);

    int insert(DeliverNoticeAttach record);

    int insertSelective(DeliverNoticeAttach record);

    List<DeliverNoticeAttach> selectByExample(DeliverNoticeAttachExample example);

    DeliverNoticeAttach selectByPrimaryKey(DeliverNoticeAttachKey key);

    int updateByExampleSelective(@Param("record") DeliverNoticeAttach record, @Param("example") DeliverNoticeAttachExample example);

    int updateByExample(@Param("record") DeliverNoticeAttach record, @Param("example") DeliverNoticeAttachExample example);

    int updateByPrimaryKeySelective(DeliverNoticeAttach record);

    int updateByPrimaryKey(DeliverNoticeAttach record);
}