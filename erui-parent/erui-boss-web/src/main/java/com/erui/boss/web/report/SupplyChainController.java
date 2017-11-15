package com.erui.boss.web.report;

import com.erui.comm.util.data.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;

import com.erui.boss.web.util.Result;
import com.erui.comm.RateUtil;
import com.erui.report.model.SuppliyChainCateVo;
import com.erui.report.model.SuppliyChainItemClassVo;
import com.erui.report.model.SuppliyChainOrgVo;
import com.erui.report.model.SupplyChain;
import com.erui.report.model.SupplyTrendVo;
import com.erui.report.service.SupplyChainService;


/**
 * Created by lirb on 2017/10/24. 报表系统-供应链
 */
@Controller
@RequestMapping("/report/supplyChain")
public class SupplyChainController {


    @Autowired
    private SupplyChainService supplyChainService;

    @ResponseBody
    @RequestMapping(value = "/supplyGeneral", method = RequestMethod.POST)
    public Object supplyGeneral(@RequestParam(name = "days", required = true) int days) {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        int finishSupplyCount = 0;
        int planSupplyCount = 0;
        int finishSPU = 0;
        int finishSKU = 0;
        int planSPU = 0;
        int planSKU = 0;
        Date startTime;
        if (days > 0) {
            startTime = DateUtil.recedeTime(days);
        } else {
            startTime = null;
        }

        List<SupplyChain> list = supplyChainService.queryListByDate(startTime, new Date());
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
        double supplierFinishRate = 0.00;
        double spuFinishRate = 0.00;
        double skuFinishRate = 0.00;
        if (planSupplyCount > 0) {
            supplierFinishRate = RateUtil.intChainRate(finishSupplyCount, planSupplyCount);
        }
        if (planSPU > 0) {
            spuFinishRate = RateUtil.intChainRate(finishSPU, planSPU);
        }
        if (planSKU > 0) {
            skuFinishRate = RateUtil.intChainRate(finishSKU, planSKU);
        }

        Map<String, Object> supplier = new HashMap<>();
        supplier.put("planCount", planSupplyCount);
        supplier.put("finishCount", finishSupplyCount);
        supplier.put("finishRate", supplierFinishRate);
        Map<String, Object> planSPUCount = new HashMap<>();
        planSPUCount.put("planCount", planSPU);
        planSPUCount.put("finishCount", finishSPU);
        planSPUCount.put("finishRate", spuFinishRate);
        Map<String, Object> planSKUCount = new HashMap<>();
        planSKUCount.put("planCount", planSKU);
        planSKUCount.put("finishCount", finishSKU);
        planSKUCount.put("finishRate", skuFinishRate);


        data.put("supplier", supplier);
        data.put("planSPU", planSPUCount);
        data.put("planSKU", planSKUCount);
        result.put("code", 200);
        result.put("data", data);
        return result;
    }

    //趋势图
    @ResponseBody
    @RequestMapping(value = "/supplyTrend", method = RequestMethod.POST)
    public Object supplyTrend(@RequestParam(name = "days", required = true) int days, @RequestParam(name = "type", required = true) Integer type) {

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String[] legend = new String[1];
        SupplyTrendVo data = this.supplyChainService.supplyTrend(days, type);
        if (data != null) {

            if (type == 0) {
                legend[0] = "供应商完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSuppliyFinishCount());
            } else if (type == 1) {
                legend[0] = "SPU完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSPUFinishCount());
            } else if (type == 2) {
                legend[0] = "SKU完成量";
                dataMap.put("legend", legend);
                dataMap.put("xAxis", data.getDatetime());
                dataMap.put("yAxis", data.getSKUFinishCount());
            }

            result.put("code", 200);
            result.put("data", dataMap);
        }
        return result;
    }

    //事业部明细
    @ResponseBody
    @RequestMapping("/organizationDetail")
    public Object organizationDetail(@RequestParam(name = "org", required = true) String org) {
        HashMap<String, Object> result = new HashMap<>();
        Date startTime = DateUtil.recedeTime(7);
        List<SuppliyChainOrgVo> list = this.supplyChainService.selectOrgSuppliyChain(startTime, new Date());
        SuppliyChainOrgVo suppliOrgVo = null;
        for (SuppliyChainOrgVo orgVo : list) {
            if (org.equals(orgVo.getOrg())) {
                suppliOrgVo = orgVo;
                break;
            }
        }
        if (suppliOrgVo != null) {
            Map<String, Object> data = returnDetailData(suppliOrgVo);
            result.put("code", 200);
            result.put("data", data);
        }
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
    @RequestMapping("/categoryDetail")
    public Object categoryDetail(@RequestParam(name = "category", required = true) String category) {

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        Date startTime = DateUtil.recedeTime(7);
        List<SuppliyChainCateVo> list = this.supplyChainService.selectCateSuppliyChain(startTime, new Date());
        SuppliyChainCateVo suppliCateVo = null;
        for (SuppliyChainCateVo cateVo : list) {
            if (category.equals(cateVo.getCategory())) {
                suppliCateVo = cateVo;
                break;
            }
        }
        HashMap<String, Object> planSPU = new HashMap<>();
        HashMap<String, Object> planSKU = new HashMap<>();
        HashMap<String, Object> planSuppliy = new HashMap<>();
        if (suppliCateVo != null) {
            int noFinishSPU = suppliCateVo.getPlanSPU() - suppliCateVo.getFinishSPU();
            int noFinishSKU = suppliCateVo.getPlanSKU() - suppliCateVo.getFinishSKU();
            int noFinishSuppliy = suppliCateVo.getPlanSuppliy() - suppliCateVo.getFinishSuppliy();
            if (noFinishSPU < 0) {
                noFinishSPU = 0;
            }
            if (noFinishSKU < 0) {
                noFinishSKU = 0;
            }
            if (noFinishSuppliy < 0) {
                noFinishSuppliy = 0;
            }
            planSPU.put("finishedSPU", suppliCateVo.getFinishSPU());
            planSPU.put("noFinishSPU", noFinishSPU);
            planSKU.put("finishedSKU", suppliCateVo.getFinishSKU());
            planSKU.put("noFinishSKU", noFinishSKU);
            planSuppliy.put("finishedSuppliy", suppliCateVo.getFinishSuppliy());
            planSuppliy.put("noFinishSuppliy", noFinishSuppliy);
        }
        data.put("planSPU", planSPU);
        data.put("planSKU", planSKU);
        data.put("planSuppliy", planSuppliy);

        result.put("code", 200);
        result.put("data", data);
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
    @RequestMapping("/catesDetail")
    public Object catesDetail() {
        Date startTime = DateUtil.recedeTime(7);
        Date chainTime = DateUtil.recedeTime(14);
        List<SuppliyChainItemClassVo> list = supplyChainService.selectItemCalssSuppliyChain(startTime, new Date());
        List<SuppliyChainItemClassVo> weekAgolist = supplyChainService.selectItemCalssSuppliyChain(chainTime,
                startTime);
        final Map<String, SuppliyChainItemClassVo> helpMap;
        if (weekAgolist != null && list != null) {
            helpMap = weekAgolist.parallelStream().collect(Collectors.toMap(SuppliyChainItemClassVo::getItemClass, vo -> vo));
        } else {
            helpMap = new HashMap<>();
        }

        if (list != null) {
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

        return new Result<Object>().setData(list);
    }


}
