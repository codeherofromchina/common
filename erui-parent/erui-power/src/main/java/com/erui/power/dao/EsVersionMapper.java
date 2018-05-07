package com.erui.power.dao;

import com.erui.power.model.EsVersion;
import com.erui.power.model.EsVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EsVersionMapper {
    int countByExample(EsVersionExample example);

    int deleteByExample(EsVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EsVersion record);

    int insertSelective(EsVersion record);

    List<EsVersion> selectByExample(EsVersionExample example);

    EsVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EsVersion record, @Param("example") EsVersionExample example);

    int updateByExample(@Param("record") EsVersion record, @Param("example") EsVersionExample example);

    int updateByPrimaryKeySelective(EsVersion record);

    int updateByPrimaryKey(EsVersion record);
}