package com.erui.boss.web.report;


import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.ProcurementCount;
import com.erui.report.service.ProcurementCountService;
import com.erui.report.util.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 采购 Created by lirb on 2018/1/9.
 */
@Controller
@RequestMapping("/report/procurement")
public class ProcurementCotroller {


    @Autowired
    private ProcurementCountService procurementService;

    /**
     * @Author:SHIGS
     * @Description 查询销售区域
     * @Date:19:39 2017/10/30
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "queryArea", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object queryArea() {
        Map<String, Object> areaMap = procurementService.selectArea();
        Result<Map<String, Object>> result = new Result<>(areaMap);
        return result;
    }

    /**
     * @Author:SHIGS
     * @Description 根据销售区域查询国家
     * @Date:19:40 2017/10/30
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "queryCoutry", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object queryCoutry(@RequestBody Map<String, Object> map) throws Exception {
        if (!map.containsKey("area")) {
            throw new MissingServletRequestParameterException("area", "String");
        }
        Map<String, Object> areaCountry = procurementService.selectCountry(map.get("area").toString());
        Result<Map<String, Object>> result = new Result<>(areaCountry);
        return result;
    }


    /**
     * 采购总览
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/procurementPadent", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object inqDetailPie(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);

        //查询采购数据
        List<Map<String, Object>> dataList = procurementService.selectProcurPandent(startTime, endTime);
        if (dataList != null && dataList.size() > 0) {
            return result.setData(dataList.get(0));
        }
        return result.setStatus(ResultStatusEnum.DATA_NULL);

    }

    /**
     * 采购趋势图
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/procurementTrend", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object procurementTrend(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("queryType")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> trendData = this.procurementService.procurementTrend(startTime, endTime, map.get("queryType").toString());
        return result.setData(trendData);

    }

    /**
     * @Author:Lirb
     * @Description 查询区域列表和国家列表
     * @Date:19:40 2017/12/14
     * @modified By
     */
    @ResponseBody
    @RequestMapping(value = "areaAndCounrryList", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object areaAndCounrryList(@RequestBody Map<String, String> map) {
        String areaName = map.get("areaName");
        Result<Object> result = new Result<>();
        List<InquiryAreaVO> list = this.procurementService.selectAllAreaAndCountryList();

        if (StringUtils.isNotBlank(areaName)) {
            List<InquiryAreaVO> areaList = list.parallelStream().filter(vo -> vo.getAreaName().equals(areaName))
                    .collect(Collectors.toList());
            if (areaList.size() > 0) {
                result.setData(areaList.get(0).getCountries());
            } else {
                return result.setStatus(ResultStatusEnum.COMPANY_NOT_EXIST);
            }
        } else {
            List<String> countrys = list.parallelStream().map(InquiryAreaVO::getAreaName).collect(Collectors.toList());
            result.setData(countrys);
        }
        return result;
    }

    /**
     * 采购区域明细
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/areaDetail", method = RequestMethod.POST, produces = "application/json;charset=utf8")
    @ResponseBody
    public Object areaDetail(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("area")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        Map<String, Object> dataMap = this.procurementService.selectAreaDetailData(startTime, endTime, map.get("area").toString(), map.get("country"));
        return result.setData(dataMap);

    }

    /**
     * 采购分类明细
     *
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/cateDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object cateDetail(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();
        Map<String, Object> data = new HashMap<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        int days = DateUtil.getDayBetween(startTime, endTime);

        List<ProcurementCount> datas = procurementService.selectCategoryDetail(startTime, endTime);
       //获取总的计划单数、总的签约合同数、总的合同金额
        int totalPlanCount=0,totalExeCount=0;
        double totalAmount=0d;

        List<Map<String, Object>> cates = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            for (ProcurementCount pro:datas) {
                totalPlanCount+=Integer.parseInt(pro.getPlanNum());
                totalExeCount+=Integer.parseInt(pro.getExecuteNum());
                totalAmount+=Double.parseDouble(pro.getAmmount().toString());
            }
            for (ProcurementCount procureCout : datas) {
                Map<String, Object> cate = new HashMap<>();
                cate.put("itemClass", procureCout.getCategory());
                cate.put("planCount", procureCout.getPlanNum());
                cate.put("executeCount", procureCout.getExecuteNum());
                cate.put("ammout", procureCout.getAmmount());
                //占比率
               if(totalPlanCount>0){
                   cate.put("planChainRate", RateUtil.intChainRate(Integer.parseInt(procureCout.getPlanNum()),totalPlanCount));
               }else {
                   cate.put("planChainRate",0d);
               }
               if(totalExeCount>0){
                   cate.put("executeChainRate", RateUtil.intChainRate(Integer.parseInt(procureCout.getExecuteNum()),totalExeCount));
               }else {
                   cate.put("executeChainRate",0d);
               }
               if(totalAmount>0){
                   cate.put("ammountChainRate", RateUtil.doubleChainRate(Double.parseDouble(procureCout.getAmmount().toString()),totalAmount));
               }else {
                   cate.put("ammountChainRate",0d);
               }

                cates.add(cate);
            }
        }
        return result.setData(cates);
    }


}
