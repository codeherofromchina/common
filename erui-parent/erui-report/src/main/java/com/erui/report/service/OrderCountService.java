package com.erui.report.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.report.model.CateDetailVo;
import com.erui.report.model.OrderCount;
import com.erui.report.model.OrderCountExample;
import com.erui.report.util.ImportDataResponse;
import com.erui.report.util.CustomerNumSummaryVO;

public interface OrderCountService {
    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:05 2017/11/14
     * @modified By
     */
    Date selectStart();

    /**
     * @Author:SHIGS
     * @Description
     * @Date:17:05 2017/11/14
     * @modified By
     */
    Date selectEnd();

    /**
     * 导入客户中心-订单数据
     *
     * @param datas
     * @param testOnly true:只检测数据  false:插入正式库
     * @return
     */
    public ImportDataResponse importData(List<String[]> datas, boolean testOnly);

    /*
     * 订单数
     * */
    public int orderCountByTime(Date startTime, Date endTime, String projectStatus, String org, String area);

    /*
     * 订单金额
     * */
    public Double orderAmountByTime(Date startTime, Date endTime, String area);

    /*
     * 订单产品类别数量Top3
     * */
    public List<Map<String, Object>> selectOrderProTop3(Map<String, Object> params);

    public Double selectProfitRate(Date startTime, Date endTime);

    /*
     * 根据时间查询订单列表
     * */
    List<OrderCount> selectListByTime(Date startTime, Date endTime);

    /**
     * 获取数据汇总
     *
     * @param area
     * @param country
     * @return
     */
    public CustomerNumSummaryVO numSummary(Date startTime, Date endTime, String area, String country);

    //订单品类明细
    List<CateDetailVo> selecOrdDetailByCategory(Date startTime, Date endTime);

    int selectProCountByExample();

    /**
     * 按照项目开始区间统计事业部的订单数量和金额
     *
     * @param params
     * @return {"totalAmount":'总订单金额--BigDecimal',"totalNum":'总订单数量--Long',"organization":'事业部--String'}
     */
    List<Map<String, Object>> findCountAndAmountByRangProjectStartGroupOrigation(Map<String,Object> params);

    /**
     * 按照项目开始区间统计区域的订单数量和金额
     *
     * @param startTime
     * @param endTime
     * @return {"totalAmount":'总订单金额--BigDecimal',"totalNum":'总订单数量--Long',"area":'区域--String'}
     */
    List<Map<String, Object>> findCountAndAmountByRangProjectStartGroupArea(Date startTime, Date endTime);

    /**
     * 查询客户复购数据明细
     * @param startTime
     * @param endTime
     * @return {"custName":'客户名称',"buyCount":'购买次数'}
     */
    List<Map<String, Object>> selectRePurchaseDetail(Date startTime, Date endTime,Object area,Object isOil);
    /**
     * 查询油气非油气的复购客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"oil":'油气非油气',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByCustCategory(Date startTime, Date endTime);

    /**
     * 查询油气非油气的客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"oil":'油气非油气',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByCustCategory(Date startTime, Date endTime);

    /**
     * 查询各地区的复购客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"area":'地区',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByArea(Date startTime, Date endTime);

    /**
     * 查询各地区的客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"area":'地区',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByArea(Date startTime, Date endTime);

    /**
     * 查询各事业部的复购客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"org":'事业部',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectRePurchaseCustGroupByOrg(Date startTime, Date endTime);

    /**
     * 查询各事业部的客户数量
     *
     * @param startTime
     * @param endTime
     * @return {"org":'事业部',"custCount":'客户数量'}
     */
    List<Map<String, Object>> selectCustCountGroupByOrg(Date startTime, Date endTime);

    /**
     * 查询各地区的订单数量和金额
     *
     * @param startTime
     * @param endTime
     * @return {"area":'地区',"ordCount":'订单数量',"ordAmmount":'订单金额'}
     */
    List<Map<String, Object>> selectDataGroupByArea(Date startTime, Date endTime);

    /**
     * 查询各事业部的订单数量和金额
     *
     * @param startTime
     * @param endTime
     * @return {"org":'事业部',"ordCount":'订单数量',"ordAmmount":'订单金额'}
     */
    List<Map<String, Object>> selectDataGroupByOrg(Date startTime, Date endTime);
    /**
     * 查查询订单品类明细的数据
     * @param startTime
     * @param endTime
     * @return {"itemClass":'事业部',"ordCount":'订单数量',"ordAmmount":'订单金额',"profit":'初步利润率'}
     */
    List<Map<String, Object>> selecOrdDetailGroupByCategory(Date startTime, Date endTime);
}