package com.erui.boss.web.report;

import com.erui.comm.DateUtil;
import com.erui.comm.RateUtil;
import com.erui.report.model.SuppliyChainCateVo;
import com.erui.report.model.SuppliyChainItemClassVo;
import com.erui.report.model.SuppliyChainOrgVo;
import com.erui.report.model.SupplyChain;
import com.erui.report.service.SupplyChainService;
import org.aspectj.apache.bcel.generic.NEW;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lirb on 2017/10/24.
 * 报表系统-供应链
 */
@Controller
@RequestMapping("/report-supplyChain")
public class SupplyChainController {


    @Autowired
    private SupplyChainService supplyChainService;

    @ResponseBody
    @RequestMapping(value = "/supplyGeneral",method = RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object supplyGeneral(@RequestBody(required = true) Map<String, Object> reqMap){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();

        int finishSupplyCount=0;
        int planSupplyCount=0;
        int finishSPU=0;
        int finishSKU=0;
        int planSPU=0;
        int planSKU=0;
        int days = (int) reqMap.get("days");
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
    @RequestMapping(value = "/supplyTrend",method = RequestMethod.POST,produces = "application/json;charset=utf8")
    public Object supplyTrend(@RequestBody(required = true) Map<String, Object> reqMap){

        Map<String, Object> result = new HashMap<>();
        Map<String, Object> data = new HashMap<>();
        int days= (int) reqMap.get("days");
        String type= (String) reqMap.get("type");

        String[] dates=new String[days];
        if(days>0&&days<=30){
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年M月d日");
                ArrayList<Date> dateList = new ArrayList<>();
                for (int i = 0; i < dates.length; i++) {
                    Date datetime = DateUtil.recedeTime(days - (i+1) );
                    dateList.add(datetime);
                    String date = dateFormat.format(datetime);
                    dates[i]=date;
                }
            Date sTime = DateUtil.recedeTime(days);
            for (int i = 0; i < dateList.size(); i++) {
                if(i==0){

                }else {

                }

            }











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
    //品类明细
    @ResponseBody
    @RequestMapping("/catesDetail")
    public Object catesDetail(){

        HashMap<String, Object> result = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        Date startTime = DateUtil.recedeTime(7);
        Date chainTime = DateUtil.recedeTime(14);
        List<SuppliyChainItemClassVo> list=this.supplyChainService.selectItemCalssSuppliyChain(startTime,new Date());
        if(list!=null&&list.size()>0) {
            for (SuppliyChainItemClassVo itemClassVo : list) {
                Map<String, Object> category = new HashMap<>();
                category.put("plainSPU",itemClassVo.getPlanSPU());
                category.put("finishSPU",itemClassVo.getFinishSPU());
                SuppliyChainItemClassVo SDF=supplyChainService.selectSuppliyChainByItemClass(startTime,new Date(), itemClassVo.getItemClass());
                System.out.println(SDF.getFinishSKU());
            }
        }
        return  null;
    }

}
