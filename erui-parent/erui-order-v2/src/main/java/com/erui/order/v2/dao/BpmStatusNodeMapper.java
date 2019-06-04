package com.erui.order.v2.dao;

import com.erui.order.v2.model.BpmStatusNode;
import com.erui.order.v2.model.BpmStatusNodeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BpmStatusNodeMapper {
    int countByExample(BpmStatusNodeExample example);

    int deleteByExample(BpmStatusNodeExample example);

    int deleteByPrimaryKey(Long id);

    int insert(BpmStatusNode record);

    int insertSelective(BpmStatusNode record);

    List<BpmStatusNode> selectByExample(BpmStatusNodeExample example);

    BpmStatusNode selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") BpmStatusNode record, @Param("example") BpmStatusNodeExample example);

    int updateByExample(@Param("record") BpmStatusNode record, @Param("example") BpmStatusNodeExample example);

    int updateByPrimaryKeySelective(BpmStatusNode record);

    int updateByPrimaryKey(BpmStatusNode record);
}