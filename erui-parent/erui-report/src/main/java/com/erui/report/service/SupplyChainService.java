package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.*;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.SupplyPlanVo;

/**
 * 供应链、供应商业务类
 */
public interface SupplyChainService {
    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:52 2017/11/14
     * @modified By
     */
    Date selectStart();

    /**
     * @Author:SHIGS
     * @Description
     * @Date:16:52 2017/11/14
     * @modified By
     */
    Date selectEnd();

    /**
     * @Author:SHIGS 查询spu sku 供应商完成量
     * @Description
     * @Date:15:58 2017/10/21
     * @modified By
     */
    Map<String, Object> selectFinishByDate(Date startTime, Date endTime, String type);

    /**
     * 导入供应链数据到数据库
     *
     * @param datas
     * @param testOnly true:只检测数据  false:插入正式库
     * @return
     */
    ImportDataResponse importData(List<String[]> datas, boolean testOnly);

    /*
        根据时间查询列表
    */
    List<SupplyChain> queryListByDate(Date startTime, Date endTime);

    /**
     * 事业部供应链明细列表
     *
     * @return
     */
    List<SuppliyChainOrgVo> selectOrgSuppliyChain(Date startTime, Date endTime);

    /**
     * 品类供应链明细列表
     *
     * @return
     */
    List<SuppliyChainItemClassVo> selectItemCalssSuppliyChain(Date startTime, Date endTime);

    /**
     * 根据品类查询供应链明细
     *
     * @return
     */
    SuppliyChainItemClassVo selectSuppliyChainByItemClass(Date startTime, Date endTime, String itemClass);

    /**
     * 品类部供应链明细列表
     *
     * @return
     */
    List<SuppliyChainCateVo> selectCateSuppliyChain(Date startTime, Date endTime);

    /**
     * 供应链趋势图
     *
     * @param startTime
     * @param endTime
     * @param type
     * @return
     */
    SupplyTrendVo supplyTrend(Date startTime, Date endTime, String type);

    /**
     * 品类部列表
     *
     * @return
     */
    List<String> selectCategoryList();

    /**
     * 事业部列表
     *
     * @return
     */
    List<String> selectOrgList();

    /**
     * 根据时间查询计划数 sku spu supplier
     *
     * @return
     */
    SupplyPlanVo getPlanNum(Date startTime, Date endTime);

    /**
     * 查询供应商的报价数量信息
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {"names":['供应商1','供应商2',...],"values":[38,32,...]}
     */
    Map<String, List<Object>> selectSupplyQuoteCount(String startTime, String endTime);

    /**
     * 查询供应商的报价金额信息
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return {"names":['供应商1','供应商2',...],"values":[161.58,161.58,...]}
     */
    Map<String, List<Object>> selectSupplyQuoteAmount(String startTime, String endTime);
}