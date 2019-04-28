package com.erui.order.v2.dao;

import com.erui.order.v2.model.Attachment;
import com.erui.order.v2.model.AttachmentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttachmentMapper {
    int countByExample(AttachmentExample example);

    int deleteByExample(AttachmentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Attachment record);

    int insertSelective(Attachment record);

    List<Attachment> selectByExample(AttachmentExample example);

    Attachment selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Attachment record, @Param("example") AttachmentExample example);

    int updateByExample(@Param("record") Attachment record, @Param("example") AttachmentExample example);

    int updateByPrimaryKeySelective(Attachment record);

    int updateByPrimaryKey(Attachment record);
}