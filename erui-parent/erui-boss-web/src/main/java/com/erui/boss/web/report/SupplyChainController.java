package com.erui.boss.web.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import java.util.stream.Collectors;
import com.erui.boss.web.util.Result;
import com.erui.comm.DateUtil;
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
    @RequestMapping(value = "/supplyGeneral",method = RequestMethod.POST)
    public Object supplyGeneral(@RequestParam(name = "days",required = true) int days){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        int finishSupplyCount=0;
        int planSupplyCount=0;
        int finishSPU=0;
        int finishSKU=0;
        int planSPU=0;
        int planSKU=0;
        Date startTime = DateUtil.recedeTime(days);
          List<SupplyChain> list = supplyChainService.queryListByDate(startTime, new Date());
        if(list!=null&&list.size()>0){
            for (SupplyChain supply:list) {
                finishSupplyCount+=supply.getFinishSuppliNum();
                finishSKU+=supply.getFinishSkuNum();
                finishSPU+=supply.getFinishSpuNum();
                planSupplyCount+=supply.getPlanSuppliNum();
                planSKU+=supply.getPlanSkuNum();
                planSPU+=supply.getPlanSpuNum();
            }
        }
        double supplierFinishRate=0.0;
        double spuFinishRate=0.0;
        double skuFinishRate=0.0;
        if(planSupplyCount>0){
            supplierFinishRate= RateUtil.intChainRate(finishSupplyCount,planSupplyCount);
        }
        if(planSPU>0){
            spuFinishRate= RateUtil.intChainRate(finishSPU,planSPU);
        }
        if(planSKU>0){
            skuFinishRate= RateUtil.intChainRate(finishSKU,planSKU);
        }

        Map<String, Object> supplier = new HashMap<>();
        supplier.put("planCount",planSupplyCount);
        supplier.put("finishCount",finishSupplyCount);
        supplier.put("finishRate",supplierFinishRate);
        Map<String, Object> planSPUCount = new HashMap<>();
        planSPUCount.put("planCount",planSPU);
        planSPUCount.put("finishCount",finishSPU);
        planSPUCount.put("finishRate",spuFinishRate);
        Map<String, Object> planSKUCount = new HashMap<>();
        planSKUCount.put("planCount",planSKU);
        planSKUCount.put("finishCount",finishSKU);
        planSKUCount.put("finishRate",skuFinishRate);


        data.put("supplier",supplier);
        data.put("planSPU",planSPUCount);
        data.put("planSKU",planSKUCount);
        result.put("code",200);
        result.put("data",data);
        return result;
    }

    //趋势图
    @ResponseBody
    @RequestMapping(value = "/supplyTrend",method = RequestMethod.POST)
    public Object supplyTrend(@RequestParam(name="days",required = true)int days,@RequestParam(name="type",required = true)Integer type){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> dataMap = new HashMap<>();
        String[] legend=new String[1];
       SupplyTrendVo data=  this.supplyChainService.supplyTrend(days,type);
        if(data!=null){

            if(type==0){
                legend[0]="供应商完成量";
                dataMap.put("legend",legend);
                dataMap.put("xAxis",data.getDatetime());
                dataMap.put("yAxis",data.getSuppliyFinishCount());
            }else  if(type==1){
                legend[0]="SPU完成量";
                dataMap.put("legend",legend);
                dataMap.put("xAxis",data.getDatetime());
                dataMap.put("yAxis",data.getSPUFinishCount());
            }else  if(type==2){
                legend[0]="SKU完成量";
                dataMap.put("legend",legend);
                dataMap.put("xAxis",data.getDatetime());
                dataMap.put("yAxis",data.getSKUFinishCount());
            }

            result.put("code",200);
            result.put("data",dataMap);
        }
        return result;
    }

    //事业部明细
    @ResponseBody
    @RequestMapping("/organizationDetail")
    public Object organizationDetail(@RequestParam(name = "org",required = true) String org){
        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();


        List<SuppliyChainOrgVo> list=this.supplyChainService.selectOrgSuppliyChain();
        SuppliyChainOrgVo suppliOrgVo=null;
        for (SuppliyChainOrgVo orgVo:list) {
            if(org.equals(orgVo.getOrg())){
                suppliOrgVo=orgVo;
                break;
            }
        }
        HashMap<String, Object> planSPU = new HashMap<>();
        HashMap<String, Object> planSKU = new HashMap<>();
        HashMap<String, Object> planSuppliy= new HashMap<>();
        if(suppliOrgVo!=null) {
            planSPU.put("planSPU", suppliOrgVo.getPlanSPU());
            planSPU.put("noFinishSPU", suppliOrgVo.getPlanSPU() - suppliOrgVo.getFinishSPU());
            planSKU.put("planSKU", suppliOrgVo.getPlanSKU());
            planSKU.put("noFinishSKU", suppliOrgVo.getPlanSKU() - suppliOrgVo.getFinishSKU());
            planSuppliy.put("planSuppliy", suppliOrgVo.getPlanSuppliy());
            planSuppliy.put("noFinishSuppliy", suppliOrgVo.getPlanSuppliy() - suppliOrgVo.getFinishSuppliy());
        }
        data.put("planSPU",planSPU);
        data.put("planSKU",planSKU);
        data.put("planSuppliy",planSuppliy);

        result.put("code",200);
        result.put("data",data);
        return  result;
    }

    //品类部明细
    @ResponseBody
    @RequestMapping("/categoryDetail")
    public Object categoryDetail(@RequestParam(name = "category",required = true) String category){

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();


        List<SuppliyChainCateVo> list=this.supplyChainService.selectCateSuppliyChain();
        SuppliyChainCateVo suppliCateVo=null;
        for (SuppliyChainCateVo cateVo:list) {
            if(category.equals(cateVo.getCategory())){
                suppliCateVo=cateVo;
                break;
            }
        }
        HashMap<String, Object> planSPU = new HashMap<>();
        HashMap<String, Object> planSKU = new HashMap<>();
        HashMap<String, Object> planSuppliy= new HashMap<>();
        if(suppliCateVo!=null) {
            planSPU.put("planSPU", suppliCateVo.getPlanSPU());
            planSPU.put("noFinishSPU", suppliCateVo.getPlanSPU() - suppliCateVo.getFinishSPU());
            planSKU.put("planSKU", suppliCateVo.getPlanSKU());
            planSKU.put("noFinishSKU", suppliCateVo.getPlanSKU() - suppliCateVo.getFinishSKU());
            planSuppliy.put("planSuppliy", suppliCateVo.getPlanSuppliy());
            planSuppliy.put("noFinishSuppliy", suppliCateVo.getPlanSuppliy() - suppliCateVo.getFinishSuppliy());
        }
        data.put("planSPU",planSPU);
        data.put("planSKU",planSKU);
        data.put("planSuppliy",planSuppliy);

        result.put("code",200);
        result.put("data",data);
        return  result;
    }

    //品类部列表
    @ResponseBody
    @RequestMapping("/categoryList")
    public Object categoryDetail(){

        List<String> categoryList=supplyChainService.selectCategoryList();
        return new Result<List<String>>().setData(categoryList);
    }

    // 品类明细
    @ResponseBody
    @RequestMapping("/catesDetail")
    public Object catesDetail() {
        Date startTime = DateUtil.recedeTime(7);
        Date chainTime = DateUtil.recedeTime(14);
        List<SuppliyChainItemClassVo> list = this.supplyChainService.selectItemCalssSuppliyChain(startTime, new Date());
        List<SuppliyChainItemClassVo> weekAgolist = supplyChainService.selectItemCalssSuppliyChain(chainTime,
                startTime);
        final Map<String, SuppliyChainItemClassVo> helpMap;
        if (weekAgolist != null && list != null) {
            helpMap = weekAgolist.parallelStream()
                    .collect(Collectors.toMap(SuppliyChainItemClassVo::getItemClass, vo -> vo));
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
