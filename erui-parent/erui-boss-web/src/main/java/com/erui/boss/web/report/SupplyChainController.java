package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.*;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.AnalyzeTypeEnum;
import com.erui.report.util.ParamsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * Created by lirb on 2017/10/24. 报表系统-供应链
 */
@Controller
@RequestMapping("/report/supplyChain")
public class SupplyChainController {


    @Autowired
    private SupplyChainService supplyChainService;

    @ResponseBody
    @RequestMapping(value = "/supplyGeneral", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object supplyGeneral(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();
        Map<String, Object> data = new HashMap<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        //环比开始
        int days = DateUtil.getDayBetween(startTime, endTime);
        Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
        int finishSupplyCount = 0;
        int finishSPU = 0;
        int finishSKU = 0;
        int finishSupplyChain = 0;
        int finishSPUChain = 0;
        int finishSKUChain = 0;
        int planSupplyCount = 0;
        int planSPU = 0;
        int planSKU = 0;

        List<SupplyChain> list = supplyChainService.queryListByDate(startTime, endTime);
        List<SupplyChain> chainList = supplyChainService.queryListByDate(chainEnd, startTime);
        if (list != null && list.size() > 0) {
            for (SupplyChain supply : list) {
                finishSupplyCount += supply.getFinishSuppliNum();
                finishSKU += supply.getFinishSkuNum();
                finishSPU += supply.getFinishSpuNum();
                planSupplyCount += supply.getPlanSuppliNum();
                planSKU += supply.getPlanSkuNum();
                planSPU += supply.getPlanSpuNum();
            }
        }
        if (list != null && list.size() > 0) {
            for (SupplyChain supplyChain : chainList) {
                finishSupplyChain += supplyChain.getFinishSuppliNum();
                finishSPUChain += supplyChain.getFinishSpuNum();
                finishSKUChain += supplyChain.getFinishSkuNum();
            }
        }

        double supplierFinishRate = 0.00;
        double spuFinishRate = 0.00;
        double skuFinishRate = 0.00;
        double supplierChainRate = 0.00;
        double spuChainRate = 0.00;
        double skuChainRate = 0.00;
        if (planSupplyCount > 0) {
            supplierFinishRate = RateUtil.intChainRate(finishSupplyCount, planSupplyCount);
        }
        if (planSPU > 0) {
            spuFinishRate = RateUtil.intChainRate(finishSPU, planSPU);
        }
        if (planSKU > 0) {
            skuFinishRate = RateUtil.intChainRate(finishSKU, planSKU);
        }
        if (finishSupplyChain > 0) {
            supplierChainRate = RateUtil.intChainRate(finishSupplyCount - finishSupplyChain, finishSupplyChain);
        }
        if (finishSPUChain > 0) {
            spuChainRate = RateUtil.intChainRate(finishSPU - finishSPUChain, finishSPUChain);
        }
        if (finishSKUChain > 0) {
            skuChainRate = RateUtil.intChainRate(finishSKU - finishSKUChain, finishSKUChain);
        }
        Map<String, Object> supplier = new HashMap<>();
        supplier.put("planCount", planSupplyCount);
        supplier.put("finishCount", finishSupplyCount);
        supplier.put("finishRate", supplierFinishRate);
        supplier.put("supplierChainRate", supplierChainRate);
        Map<String, Object> planSPUCount = new HashMap<>();
        planSPUCount.put("planCount", planSPU);
        planSPUCount.put("finishCount", finishSPU);
        planSPUCount.put("finishRate", spuFinishRate);
        planSPUCount.put("spuChainRate", spuChainRate);
        Map<String, Object> planSKUCount = new HashMap<>();
        planSKUCount.put("planCount", planSKU);
        planSKUCount.put("finishCount", finishSKU);
        planSKUCount.put("finishRate", skuFinishRate);
        planSKUCount.put("skuChainRate", skuChainRate);

        data.put("supplier", supplier);
        data.put("planSPU", planSPUCount);
        data.put("planSKU", planSKUCount);
        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    //趋势图
    @ResponseBody
    @RequestMapping(value = "/supplyTrend", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object supplyTrend(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();
        Map<String, Object> dataMap = new HashMap<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("type")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        String type = map.get("type").toString();
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        String[] legend = new String[1];
        SupplyTrendVo data = this.supplyChainService.supplyTrend(startTime, endTime, type);
        if (data != null) {
            if (type.equals("supplier")) {
                legend[0] = "供应商完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSuppliyFinishCount());
            } else if (type.equals("spu")) {
                legend[0] = "SPU完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSPUFinishCount());
            } else if (type.equals("sku")) {
                legend[0] = "SKU完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSKUFinishCount());
            }
            result.setStatus(ResultStatusEnum.SUCCESS);
            result.setData(dataMap);
            return result;
        }
        result.setStatus(ResultStatusEnum.DATA_NULL);
        return result;
    }

    //事业部明细
    @ResponseBody
    @RequestMapping(value = "/organizationDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object organizationDetail(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();

        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("org")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        String org = map.get("org").toString();
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        List<SuppliyChainOrgVo> list = this.supplyChainService.selectOrgSuppliyChain(startTime, endTime);
        SuppliyChainOrgVo suppliOrgVo = null;
        for (SuppliyChainOrgVo orgVo : list) {
            if (org.equals(orgVo.getOrg())) {
                suppliOrgVo = orgVo;
                break;
            }
        }
        Map<String, Object> data = null;
        if (suppliOrgVo != null) {
            data = returnDetailData(suppliOrgVo);
        } else {
            Map<String, Object> planSPU = new HashMap<>();
            Map<String, Object> planSKU = new HashMap<>();
            Map<String, Object> planSuppliy = new HashMap<>();
            planSPU.put("finishedSPU", 0);
            planSPU.put("noFinishSPU", 0);
            planSKU.put("finishedSKU", 0);
            planSKU.put("noFinishSKU", 0);
            planSuppliy.put("finishedSuppliy", 0);
            planSuppliy.put("noFinishSuppliy", 0);
            data = new HashMap<>();
            data.put("planSPU", planSPU);
            data.put("planSKU", planSKU);
            data.put("planSuppliy", planSuppliy);
        }
        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(data);
        return result;
    }

    //事业部明细封装
    public Map<String, Object> returnDetailData(SuppliyChainOrgVo suppliOrgVo) {
        Map<String, Object> planSPU = new HashMap<>();
        Map<String, Object> planSKU = new HashMap<>();
        Map<String, Object> planSuppliy = new HashMap<>();
        int noFinishSPU = suppliOrgVo.getPlanSPU() - suppliOrgVo.getFinishSPU();
        int noFinishSKU = suppliOrgVo.getPlanSKU() - suppliOrgVo.getFinishSKU();
        int noFinishSuppliy = suppliOrgVo.getPlanSuppliy() - suppliOrgVo.getFinishSuppliy();
        if (noFinishSPU < 0) {
            noFinishSPU = 0;
        }
        if (noFinishSKU < 0) {
            noFinishSKU = 0;
        }
        if (noFinishSuppliy < 0) {
            noFinishSuppliy = 0;
        }
        planSPU.put("finishedSPU", suppliOrgVo.getFinishSPU());
        planSPU.put("noFinishSPU", noFinishSPU);
        planSKU.put("finishedSKU", suppliOrgVo.getFinishSKU());
        planSKU.put("noFinishSKU", noFinishSKU);
        planSuppliy.put("finishedSuppliy", suppliOrgVo.getFinishSuppliy());
        planSuppliy.put("noFinishSuppliy", noFinishSuppliy);
        Map<String, Object> data = new HashMap<>();
        data.put("planSPU", planSPU);
        data.put("planSKU", planSKU);
        data.put("planSuppliy", planSuppliy);
        return data;
    }

    //品类部明细
    @ResponseBody
    @RequestMapping(value = "/categoryDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object categoryDetail(@RequestBody Map<String, Object> map) throws Exception {

        Result<Object> result = new Result<>();
        HashMap<String, Object> data = new HashMap<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime") || !map.containsKey("category")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        String category = map.get("category").toString();
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        List<SuppliyChainCateVo> list = this.supplyChainService.selectCateSuppliyChain(startTime, endTime);
        SuppliyChainCateVo suppliCateVo = null;
        if(list!=null&&list.size()>0) {
            for (SuppliyChainCateVo cateVo : list) {
                if (category.equals(cateVo.getCategory())) {
                    suppliCateVo = cateVo;
                    break;
                }
            }
        }
        HashMap<String, Object> planSPU = new HashMap<>();
        HashMap<String, Object> planSKU = new HashMap<>();
        HashMap<String, Object> planSuppliy = new HashMap<>();
        if (suppliCateVo != null) {
//            int noFinishSPU = suppliCateVo.getPlanSPU() - suppliCateVo.getFinishSPU();
//            int noFinishSKU = suppliCateVo.getPlanSKU() - suppliCateVo.getFinishSKU();
//            int noFinishSuppliy = suppliCateVo.getPlanSuppliy() - suppliCateVo.getFinishSuppliy();
//            if (noFinishSPU < 0) {
//                noFinishSPU = 0;
//            }
//            if (noFinishSKU < 0) {
//                noFinishSKU = 0;
//            }
//            if (noFinishSuppliy < 0) {
//                noFinishSuppliy = 0;
//            }
            planSPU.put("finishedSPU", suppliCateVo.getFinishSPU());
            planSPU.put("planSPU", suppliCateVo.getPlanSPU());
            planSKU.put("finishedSKU", suppliCateVo.getFinishSKU());
            planSKU.put("planSKU", suppliCateVo.getPlanSKU());
            planSuppliy.put("finishedSuppliy", suppliCateVo.getFinishSuppliy());
            planSuppliy.put("planSuppliy", suppliCateVo.getPlanSuppliy());
        } else {
            planSPU.put("finishedSPU", 0);
            planSPU.put("planSPU", 0);
            planSKU.put("finishedSKU", 0);
            planSKU.put("planSKU", 0);
            planSuppliy.put("finishedSuppliy", 0);
            planSuppliy.put("planSuppliy", 0);
        }
        data.put("planSPU", planSPU);
        data.put("planSKU", planSKU);
        data.put("planSuppliy", planSuppliy);
        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(data);
        return result;

    }

    //品类部列表
    @ResponseBody
    @RequestMapping("/categoryList")
    public Object categoryList() {

        List<String> categoryList = supplyChainService.selectCategoryList();
        return new Result<List<String>>().setData(categoryList);
    }

    //事业部列表
    @ResponseBody
    @RequestMapping("/orgList")
    public Object orgList() {

        List<String> orgList = supplyChainService.selectOrgList();
        return new Result<List<String>>().setData(orgList);
    }

    // 品类明细
    @ResponseBody
    @RequestMapping(value = "/catesDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object catesDetail(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();
        if (!map.containsKey("startTime") || !map.containsKey("endTime")) {
            result.setStatus(ResultStatusEnum.PARAM_TYPE_ERROR);
            result.setData("参数输入有误");
            return result;
        }
        //开始时间
        Date startTime = DateUtil.parseStringToDate(map.get("startTime").toString(), "yyyy/MM/dd");
        //截止时间
        Date end = DateUtil.parseStringToDate(map.get("endTime").toString(), "yyyy/MM/dd");
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        //环比开始
        int days = DateUtil.getDayBetween(startTime, endTime);
        Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
        List<SuppliyChainItemClassVo> list = supplyChainService.selectItemCalssSuppliyChain(startTime, endTime);
        List<SuppliyChainItemClassVo> weekAgolist = supplyChainService.selectItemCalssSuppliyChain(chainEnd,
                startTime);
        final Map<String, SuppliyChainItemClassVo> helpMap;
        if (weekAgolist != null && weekAgolist.size()>0) {
            helpMap = weekAgolist.parallelStream().collect(Collectors.toMap(SuppliyChainItemClassVo::getItemClass, vo -> vo));
        } else {
            helpMap = new HashMap<>();
        }

        if (list != null&&list.size()>0) {
            // 生成环比百分数
            for (SuppliyChainItemClassVo itemClassVo : list) {
                String itemClass = itemClassVo.getItemClass();
                SuppliyChainItemClassVo suppliyChainItemClassVo = helpMap.get(itemClass);
                if (suppliyChainItemClassVo != null) {
                    if (suppliyChainItemClassVo.getFinishSPU() > 0) {
                        itemClassVo.setSpuChainRate(RateUtil.intChainRate(
                                itemClassVo.getFinishSPU() - suppliyChainItemClassVo.getFinishSPU(),
                                suppliyChainItemClassVo.getFinishSPU()));
                    }
                    if (suppliyChainItemClassVo != null && suppliyChainItemClassVo.getFinishSKU() > 0) {
                        itemClassVo.setSkuChainRate(RateUtil.intChainRate(
                                itemClassVo.getFinishSKU() - suppliyChainItemClassVo.getFinishSKU(),
                                suppliyChainItemClassVo.getFinishSKU()));
                    }
                    if (suppliyChainItemClassVo != null && suppliyChainItemClassVo.getFinishSuppliy() > 0) {
                        itemClassVo.setSupplierChainRate(RateUtil.intChainRate(
                                itemClassVo.getFinishSuppliy() - suppliyChainItemClassVo.getFinishSuppliy(),
                                suppliyChainItemClassVo.getFinishSuppliy()));
                    }
                }
            }
        }
        result.setStatus(ResultStatusEnum.SUCCESS);
        result.setData(list);
        return result;
    }



    /**
     * 供应商统计：根据供应商统计报价金额、报价数量
     *  查询报价信息
     * @param params
     * @return
     */
    @RequestMapping(value = "/quoteInfo", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Result<Object> selectCategoryNumByArea(@RequestBody(required = true) Map<String, Object> params) {
        //处理参数
        params = ParamsUtils.verifyParam(params, DateUtil.SHORT_FORMAT_STR, null);
        if (params == null) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        Map<String, List<Object>> data = null;
        String analyzeType = String.valueOf(params.get("type"));
        String startTime = String.valueOf(params.get("startTime"));
        String endTime = String.valueOf(params.get("endTime"));
        if (AnalyzeTypeEnum.QUOTE_COUNT.getTypeName().equalsIgnoreCase(analyzeType)) {  // 查询供应商的报价数量
            data = supplyChainService.selectSupplyQuoteCount(startTime, endTime);
        } else if (AnalyzeTypeEnum.QUOTE_AMOUNT.getTypeName().equalsIgnoreCase(analyzeType)) { // 查询供应商的报价金额
            data = supplyChainService.selectSupplyQuoteAmount(startTime, endTime);
        }

        if (data == null || data.size() == 0 || ((List) data.get("names")).size() == 0) {
            return new Result<>(ResultStatusEnum.DATA_NULL);
        }
        return new Result<>(data);
    }

}
