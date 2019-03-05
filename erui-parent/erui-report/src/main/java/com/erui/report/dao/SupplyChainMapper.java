package com.erui.report.dao;

import com.erui.report.model.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.erui.report.util.SupplyPlanVo;
import org.apache.ibatis.annotations.Param;

public interface SupplyChainMapper {
    Date selectStart();
    Date selectEnd();
    List<Map> selectFinishByDate(SupplyChainExample supplyChainExample);
    int countByExample(SupplyChainExample example);

    int deleteByExample(SupplyChainExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SupplyChain record);

    int insertSelective(SupplyChain record);

    List<SupplyChain> selectByExample(SupplyChainExample example);

    SupplyChain selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SupplyChain record, @Param("example") SupplyChainExample example);

    int updateByExample(@Param("record") SupplyChain record, @Param("example") SupplyChainExample example);

    int updateByPrimaryKeySelective(SupplyChain record);

    int updateByPrimaryKey(SupplyChain record);

    List<SuppliyChainOrgVo> selectOrgSuppliyChainByExample(SupplyChainExample example);
    List<SuppliyChainItemClassVo> selectItemCalssSuppliyChainByExample(SupplyChainExample example);
    SuppliyChainItemClassVo  selectSuppliyChainByItemClassByExample(SupplyChainExample example);
    List<SuppliyChainCateVo> selectCateSuppliyChainByExample(SupplyChainExample example);
    //品类部列表
    List<String> selectCategoryList();
    //事业部列表
    List<String> selectOrgList();
    
    void truncateTable();

    SupplyPlanVo  selectPlanCount(SupplyChainExample example);

    /**
     * 查询供应商的报价数量信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectSupplyQuoteCount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 查询供应商的报价金额信息
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> selectSupplyQuoteAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

}