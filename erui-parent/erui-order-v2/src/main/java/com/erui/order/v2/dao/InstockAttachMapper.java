package com.erui.order.v2.dao;

import com.erui.order.v2.model.InstockAttach;
import com.erui.order.v2.model.InstockAttachExample;
import com.erui.order.v2.model.InstockAttachKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstockAttachMapper {
    int countByExample(InstockAttachExample example);

    int deleteByExample(InstockAttachExample example);

    int deleteByPrimaryKey(InstockAttachKey key);

    int insert(InstockAttach record);

    int insertSelective(InstockAttach record);

    List<InstockAttach> selectByExample(InstockAttachExample example);

    InstockAttach selectByPrimaryKey(InstockAttachKey key);

    int updateByExampleSelective(@Param("record") InstockAttach record, @Param("example") InstockAttachExample example);

    int updateByExample(@Param("record") InstockAttach record, @Param("example") InstockAttachExample example);

    int updateByPrimaryKeySelective(InstockAttach record);

    int updateByPrimaryKey(InstockAttach record);
}