package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyTrendVo;
import com.erui.report.service.SupplyChainCategoryService;
import com.erui.report.service.SupplyChainReadService;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.SupplyCateDetailVo;
import com.erui.report.util.SupplyPlanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
@Controller
@RequestMapping("/report/supplyChainRead")
public class SupplyChainReadController {

    @Autowired
    private SupplyChainService supplyChainService;
    @Autowired
    private SupplyChainReadService supplyChainReadService;
    @Autowired
    private SupplyChainCategoryService supplyChainCategoryService;

    //供应链总览
    @ResponseBody
    @RequestMapping(value = "/supplyGeneral", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object supplyGeneral(@RequestBody Map<String, Object> map) throws Exception {
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
        SupplyChainRead supplyRead = supplyChainReadService.getSupplyChainReadDataByTime(startTime, endTime);//当前数据
        SupplyChainRead supplchainRead = supplyChainReadService.getSupplyChainReadDataByTime(chainEnd, startTime);//当前环比数据
        //查询供应链计划数
        SupplyPlanVo planVo = supplyChainService.getPlanNum(startTime, endTime);//当前计划数
        //封装数据
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> supplierDatas = new HashMap<>();
        Map<String, Object> skuDatas = new HashMap<>();
        Map<String, Object> spuDatas = new HashMap<>();
        if (supplyRead != null) {

            supplierDatas.put("supplierCount", supplyRead.getSuppliNum());
            Double suppliComletionRate = null;
            Double supplierCountLink = null;
            Double brandCountLink = null;
            Double auditSupplierRate = null;
            Double auditSupplierLink = null;
            Double passSupplierRate = null;
            Double passSupplierLink = null;
            Double rejectSupplierRate = null;
            Double rejectSupplierLink = null;
            int planSupplierCount = 0;
            if (planVo != null) {
                if (planVo.getPlanSupplierNum() > 0) {
                    suppliComletionRate = RateUtil.intChainRate(supplyRead.getSuppliNum(), planVo.getPlanSupplierNum());
                }
                planSupplierCount = planVo.getPlanSupplierNum();
            }
            if (supplyRead.getSuppliNum() > 0) {
                auditSupplierRate = RateUtil.intChainRate(supplyRead.getAuditSuppliNum(), supplyRead.getSuppliNum());
                passSupplierRate = RateUtil.intChainRate(supplyRead.getPassSuppliNum(), supplyRead.getSuppliNum());
                rejectSupplierRate = RateUtil.intChainRate(supplyRead.getRejectSuppliNum(), supplyRead.getSuppliNum());
            }
            if (supplchainRead != null) {
                if (supplchainRead.getSuppliNum() > 0) {
                    supplierCountLink = RateUtil.intChainRate(supplyRead.getSuppliNum() - supplchainRead.getSuppliNum(), supplchainRead.getSuppliNum());
                }
                if (supplchainRead.getBrandNum() > 0) {
                    brandCountLink = RateUtil.intChainRate(supplyRead.getBrandNum() - supplchainRead.getBrandNum(), supplchainRead.getBrandNum());
                }
                if (supplchainRead.getAuditSuppliNum() > 0) {
                    auditSupplierLink = RateUtil.intChainRate(supplyRead.getAuditSuppliNum() - supplchainRead.getAuditSuppliNum(), supplchainRead.getAuditSuppliNum());
                }
                if (supplchainRead.getPassSuppliNum() > 0) {
                    passSupplierLink = RateUtil.intChainRate(supplyRead.getPassSuppliNum() - supplchainRead.getPassSuppliNum(), supplchainRead.getPassSuppliNum());
                }
                if (supplchainRead.getRejectSuppliNum() > 0) {
                    rejectSupplierRate = RateUtil.intChainRate(supplyRead.getRejectSuppliNum() - supplchainRead.getRejectSuppliNum(), supplchainRead.getRejectSuppliNum());
                }
            }
            supplierDatas.put("planSupplier", planSupplierCount);
            supplierDatas.put("completionRate", suppliComletionRate);
            supplierDatas.put("supplierCountLink", supplierCountLink);
            supplierDatas.put("brandCount", supplyRead.getBrandNum());
            supplierDatas.put("brandCountLink", brandCountLink);
            supplierDatas.put("auditSupplierCount", supplyRead.getAuditSuppliNum());
            supplierDatas.put("auditSupplierRate", auditSupplierRate);
            supplierDatas.put("auditSupplierLink", auditSupplierLink);
            supplierDatas.put("passSupplierCount", supplyRead.getPassSuppliNum());
            supplierDatas.put("passSupplierRate", passSupplierRate);
            supplierDatas.put("passSupplierLink", passSupplierLink);
            supplierDatas.put("rejectSupplierCount", supplyRead.getRejectSuppliNum());
            supplierDatas.put("rejectSupplierRate", rejectSupplierRate);
            supplierDatas.put("rejectSupplierLink", rejectSupplierLink);
            Double skuCompletionRate = null;
            Double skuCountLink = null;
            Double tempoSKURate = null;//za
            Double tempoSKULink = null;
            Double auditSKURate = null;
            Double auditSKULink = null;
            Double passSKURate = null;
            Double passSKULink = null;
            Double rejectSKURate = null;
            Double rejectSKULink = null;
            int planSKUCount = 0;
            if (planVo != null) {
                if (planVo.getPlanSKUNum() > 0) {
                    skuCompletionRate = RateUtil.intChainRate(supplyRead.getSkuNum(), planVo.getPlanSKUNum());
                }
                planSKUCount = planVo.getPlanSKUNum();
            }
            if (supplchainRead != null) {
                if (supplchainRead.getSkuNum() > 0) {
                    skuCountLink = RateUtil.intChainRate(supplyRead.getSkuNum() - supplchainRead.getSkuNum(), supplchainRead.getSkuNum());
                }
                if (supplchainRead.getTempoSkuNum() > 0) {
                    tempoSKULink = RateUtil.intChainRate(supplyRead.getTempoSkuNum() - supplchainRead.getTempoSkuNum(), supplchainRead.getTempoSkuNum());
                }
                if (supplchainRead.getAuditSkuNum() > 0) {
                    auditSKULink = RateUtil.intChainRate(supplyRead.getAuditSkuNum() - supplchainRead.getAuditSkuNum(), supplchainRead.getAuditSkuNum());
                }
                if (supplchainRead.getPassSkuNum() > 0) {
                    passSKULink = RateUtil.intChainRate(supplyRead.getPassSkuNum() - supplchainRead.getPassSkuNum(), supplchainRead.getPassSkuNum());
                }
                if (supplchainRead.getRejectSkuNum() > 0) {
                    rejectSKULink = RateUtil.intChainRate(supplyRead.getRejectSkuNum() - supplchainRead.getRejectSkuNum(), supplchainRead.getRejectSkuNum());
                }
            }
            if (supplyRead.getSkuNum() > 0) {
                tempoSKURate = RateUtil.intChainRate(supplyRead.getTempoSkuNum(), supplyRead.getSkuNum());
                auditSKURate = RateUtil.intChainRate(supplyRead.getAuditSkuNum(), supplyRead.getSkuNum());
                passSKURate = RateUtil.intChainRate(supplyRead.getPassSkuNum(), supplyRead.getSkuNum());
                rejectSKURate = RateUtil.intChainRate(supplyRead.getRejectSkuNum(), supplyRead.getSkuNum());
            }
            skuDatas.put("planSKU", planSKUCount);
            skuDatas.put("skuCount", supplyRead.getSkuNum());
            skuDatas.put("completionRate", skuCompletionRate);//sku完成率
            skuDatas.put("skuCountLink", skuCountLink);
            skuDatas.put("tempoSKUCount", supplyRead.getTempoSkuNum());//暂存sku
            skuDatas.put("tempoSKURate", tempoSKURate);//
            skuDatas.put("tempoSKULink", tempoSKULink);//
            skuDatas.put("auditSKUCount", supplyRead.getAuditSkuNum());//审核中sku
            skuDatas.put("auditSKURate", auditSKURate);
            skuDatas.put("auditSKULink", auditSKULink);
            skuDatas.put("passSKUCount", supplyRead.getPassSkuNum());//已通过sku
            skuDatas.put("passSKURate", passSKURate);
            skuDatas.put("passSKULink", passSKULink);
            skuDatas.put("rejectSKUCount", supplyRead.getRejectSkuNum());//已驳回sku
            skuDatas.put("rejectSKURate", rejectSKURate);
            skuDatas.put("rejectSKULink", rejectSKULink);
            Double spuCompletionRate = null;
            Double spuCountLink = null;
            Double tempoSPURate = null;//za
            Double tempoSPULink = null;
            Double auditSPURate = null;
            Double auditSPULink = null;
            Double passSPURate = null;
            Double passSPULink = null;
            Double rejectSPURate = null;
            Double rejectSPULink = null;
            int planSPUCount = 0;
            if (planVo != null) {
                if (planVo.getPlanSPUNum() > 0) {
                    spuCompletionRate = RateUtil.intChainRate(supplyRead.getSpuNum(), planVo.getPlanSPUNum());
                    planSPUCount = planVo.getPlanSPUNum();
                }
            }
            if (supplchainRead != null) {
                if (supplchainRead.getSpuNum() > 0) {
                    spuCountLink = RateUtil.intChainRate(supplyRead.getSpuNum() - supplchainRead.getSpuNum(), supplchainRead.getSpuNum());
                }
                if (supplchainRead.getTempoSpuNum() > 0) {
                    tempoSPULink = RateUtil.intChainRate(supplyRead.getTempoSpuNum() - supplchainRead.getTempoSpuNum(), supplchainRead.getTempoSpuNum());
                }
                if (supplchainRead.getAuditSpuNum() > 0) {
                    auditSPULink = RateUtil.intChainRate(supplyRead.getAuditSpuNum() - supplchainRead.getAuditSpuNum(), supplchainRead.getAuditSpuNum());
                }
                if (supplchainRead.getPassSpuNum() > 0) {
                    passSPULink = RateUtil.intChainRate(supplyRead.getPassSpuNum() - supplchainRead.getPassSpuNum(), supplchainRead.getPassSpuNum());
                }
                if (supplchainRead.getRejectSpuNum() > 0) {
                    rejectSPULink = RateUtil.intChainRate(supplyRead.getRejectSpuNum() - supplchainRead.getRejectSpuNum(), supplchainRead.getRejectSpuNum());
                }
            }
            if (supplyRead.getSpuNum() > 0) {
                tempoSPURate = RateUtil.intChainRate(supplyRead.getTempoSpuNum(), supplyRead.getSpuNum());
                auditSPURate = RateUtil.intChainRate(supplyRead.getAuditSpuNum(), supplyRead.getSpuNum());
                passSPURate = RateUtil.intChainRate(supplyRead.getPassSpuNum(), supplyRead.getSpuNum());
                rejectSPURate = RateUtil.intChainRate(supplyRead.getRejectSpuNum(), supplyRead.getSpuNum());
            }
            spuDatas.put("planSPU", planSPUCount);
            spuDatas.put("spuCount", supplyRead.getSpuNum());
            spuDatas.put("completionRate", spuCompletionRate);//sku完成率
            spuDatas.put("spuCountLink", spuCountLink);
            spuDatas.put("tempoSPUCount", supplyRead.getTempoSpuNum());//暂存sku
            spuDatas.put("tempoSPURate", tempoSPURate);//
            spuDatas.put("tempoSPULink", tempoSPULink);//
            spuDatas.put("auditSPUCount", supplyRead.getAuditSpuNum());//审核中sku
            spuDatas.put("auditSPURate", auditSPURate);
            spuDatas.put("auditSPULink", auditSPULink);
            spuDatas.put("passSPUCount", supplyRead.getPassSpuNum());//已通过sku
            spuDatas.put("passSPURate", passSPURate);
            spuDatas.put("passSPULink", passSPULink);
            spuDatas.put("rejectSPUCount", supplyRead.getRejectSpuNum());//已驳回sku
            spuDatas.put("rejectSPURate", rejectSPURate);
            spuDatas.put("rejectSPULink", rejectSPULink);
            data.put("supplierDatas", supplierDatas);
            data.put("skuDatas", skuDatas);
            data.put("spuDatas", spuDatas);
            result.setStatus(ResultStatusEnum.SUCCESS);
            return result.setData(data);
        }
        return result.setStatus(ResultStatusEnum.DATA_NULL);
    }

    //趋势图
    @ResponseBody
    @RequestMapping(value = "/supplyTrend", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object supplyTrend(@RequestBody Map<String, Object> map) throws Exception {

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
        SupplyTrendVo data = this.supplyChainReadService.supplyTrend(startTime, endTime);
        result.setData(data);
        return result;
    }

    //计划与完成量
    @ResponseBody
    @RequestMapping(value = "/planAndFinish", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object planAndFinishCount(@RequestBody Map<String, Object> map) throws Exception {
        Result<Object> result = new Result<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> sku = new HashMap<>();
        Map<String, Object> spu = new HashMap<>();
        Map<String, Object> supplier = new HashMap<>();
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
        SupplyChainRead supplyRead = supplyChainReadService.getSupplyChainReadDataByTime(startTime, endTime);//当前数据
        SupplyPlanVo planNum = supplyChainService.getPlanNum(startTime, endTime);
        Integer spuNum = null;
        Integer skuNum = null;
        Integer suppliNum = null;
        Integer planSKUNum = null;
        Integer planSPUNum = null;
        Integer planSupplierNum = null;
        Double skuCompletionRate = null;
        Double spuCompletionRate = null;
        Double supplierCompletionRate = null;
        if (supplyRead != null) {
            spuNum = supplyRead.getSpuNum();
            skuNum = supplyRead.getSkuNum();
            suppliNum = supplyRead.getSuppliNum();

            if (planNum != null) {
                planSKUNum = planNum.getPlanSKUNum();
                planSPUNum = planNum.getPlanSPUNum();
                planSupplierNum = planNum.getPlanSupplierNum();

                if (planSKUNum != null) {
                    if (planSKUNum > 0) {
                        skuCompletionRate = RateUtil.intChainRate(skuNum, planSKUNum);
                    }
                }
                if (planSPUNum != null) {
                    if (planSPUNum > 0) {
                        spuCompletionRate = RateUtil.intChainRate(spuNum, planSPUNum);
                    }
                }
                if (planSupplierNum != null) {
                    if (planSupplierNum > 0) {
                        supplierCompletionRate = RateUtil.intChainRate(suppliNum, planSupplierNum);
                    }
                }
            }

        sku.put("skuNum", skuNum);
        sku.put("planSKUNum", planSKUNum);
        sku.put("skuCompletionRate", skuCompletionRate);
        spu.put("spuNum", spuNum);
        spu.put("planSPUNum", planSPUNum);
        spu.put("spuCompletionRate", spuCompletionRate);
        supplier.put("suppliNum", suppliNum);
        supplier.put("planSupplierNum", planSupplierNum);
        supplier.put("supplierCompletionRate", supplierCompletionRate);
        data.put("sku", sku);
        data.put("spu", spu);
        data.put("supplier", supplier);
        result.setData(data);
        return result;
        }
        return  result.setStatus(ResultStatusEnum.DATA_NULL);
    }
    //分类明细
    @ResponseBody
    @RequestMapping(value = "/cateDetail", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object cateDetail(@RequestBody Map<String, Object> map) throws Exception {
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
        List<SupplyCateDetailVo> list=this.supplyChainCategoryService.selectCateListByTime(startTime,endTime);
        if(list!=null &&list.size()>0){
         list.parallelStream().forEach(vo->{
         int total=vo.getSkuCount()+vo.getSpuCount()+vo.getSupplierCount();
         if(total>0){
             vo.setSkuCompletionRate(RateUtil.intChainRate(vo.getSkuCount(),total));
             vo.setSpuCompletionRate(RateUtil.intChainRate(vo.getSpuCount(),total));
             vo.setSupplierCompletionRate(RateUtil.intChainRate(vo.getSupplierCount(),total));
         }
         });
         result.setData(list);
            return result;
        }
        return result.setStatus(ResultStatusEnum.DATA_NULL);
    }

}
