package com.erui.order.v2.dao;

import com.erui.order.v2.model.Instock;
import com.erui.order.v2.model.InstockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface InstockMapper {
    int countByExample(InstockExample example);

    int deleteByExample(InstockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Instock record);

    int insertSelective(Instock record);

    List<Instock> selectByExampleWithBLOBs(InstockExample example);

    List<Instock> selectByExample(InstockExample example);

    Instock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Instock record, @Param("example") InstockExample example);

    int updateByExampleWithBLOBs(@Param("record") Instock record, @Param("example") InstockExample example);

    int updateByExample(@Param("record") Instock record, @Param("example") InstockExample example);

    int updateByPrimaryKeySelective(Instock record);

    int updateByPrimaryKeyWithBLOBs(Instock record);

    int updateByPrimaryKey(Instock record);
}