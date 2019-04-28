package com.erui.order.v2.dao;

import com.erui.order.v2.model.InstockAttachExample;
import com.erui.order.v2.model.InstockAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstockAttachMapper {
    int countByExample(InstockAttachExample example);

    int deleteByExample(InstockAttachExample example);

    int deleteByPrimaryKey(InstockAttachKey key);

    int insert(InstockAttachKey record);

    int insertSelective(InstockAttachKey record);

    List<InstockAttachKey> selectByExample(InstockAttachExample example);

    int updateByExampleSelective(@Param("record") InstockAttachKey record, @Param("example") InstockAttachExample example);

    int updateByExample(@Param("record") InstockAttachKey record, @Param("example") InstockAttachExample example);
}