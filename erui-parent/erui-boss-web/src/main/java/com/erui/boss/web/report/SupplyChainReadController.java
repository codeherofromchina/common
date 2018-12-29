package com.erui.boss.web.report;

import com.erui.boss.web.util.Result;
import com.erui.boss.web.util.ResultStatusEnum;
import com.erui.comm.NewDateUtil;
import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.model.SupplyTrendVo;
import com.erui.report.service.SupplierOnshelfInfoService;
import com.erui.report.service.SupplyChainCategoryService;
import com.erui.report.service.SupplyChainReadService;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ParamsUtils;
import com.erui.report.util.SupplyCateDetailVo;
import com.erui.report.util.SupplyPlanVo;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/report/supplyChainRead")
public class SupplyChainReadController {

   private static final Logger logger= LoggerFactory.getLogger(SupplyChainReadController.class);
    @Autowired
    private SupplyChainService supplyChainService;
    @Autowired
    private SupplyChainReadService supplyChainReadService;
    @Autowired
    private SupplyChainCategoryService supplyChainCategoryService;
    @Autowired
    private SupplierOnshelfInfoService onshelfInfoService;
    //供应链总览
    @ResponseBody
    @RequestMapping(value = "/supplyGeneral", method = RequestMethod.POST, produces = {"application/json;charset=utf-8"})
    public Object supplyGeneral(@RequestBody Map<String, String> map) throws Exception {

        Date startTime = DateUtil.parseString2DateNoException(map.get("startTime"), "yyyy/MM/dd");
        Date end = DateUtil.parseString2DateNoException(map.get("endTime"), "yyyy/MM/dd");
        if (startTime == null || end == null || startTime.after(end)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        Date endTime = DateUtil.getOperationTime(end, 23, 59, 59);
        //环比开始
        int days = DateUtil.getDayBetween(startTime, endTime);
        Date chainEnd = DateUtil.sometimeCalendar(startTime, days);
        SupplyChainRead supplyRead = supplyChainReadService.getSupplyChainReadDataByTime(startTime, endTime);//当前数据
        SupplyChainRead supplchainRead = supplyChainReadService.getSupplyChainReadDataByTime(chainEnd, startTime);//当前环比数据
        //查询供应链计划数
        SupplyPlanVo planVo = null;
        //开始时间必须是周六，结束时间必须是周五
        if (DateUtil.weekDays[6].equals(DateUtil.getWeekOfDate(startTime)) && DateUtil.weekDays[5].equals(DateUtil.getWeekOfDate(endTime))) {
            planVo = supplyChainService.getPlanNum(startTime, endTime);//当前计划数
        }

        Map<String, Object> data=null;
        if (supplyRead != null) {
            //供应商总览
            Map<String, Object> supplierDatas = new HashMap<>();
            Double suppliComletionRate = null; //供应商完成率
            Double supplierCountLink = 0.00d;//供应商完成数环比
            Double brandCountLink = 0.00d;//供应商品牌数量环比
            Double auditSupplierRate = 0.00d;//待审核供应商数量占比
            Double auditSupplierLink = 0.00d;//待审核供应商数量环比
            Double passSupplierRate = 0.00d;//已通过供应商数量占比
            Double passSupplierLink = 0.00d;//已通过供应商数量环比
            Double rejectSupplierRate = 0.00d;//已驳回供应商数量占比
            Double rejectSupplierLink = 0.00d;//已通过供应商数量环比
            int planSupplierCount = 0; //计划供应商数量
            supplierDatas.put("supplierCount", supplyRead.getSuppliNum());//已开发供应商数量
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
                    rejectSupplierLink = RateUtil.intChainRate(supplyRead.getRejectSuppliNum() - supplchainRead.getRejectSuppliNum(), supplchainRead.getRejectSuppliNum());
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

            //spu总览
            Map<String, Object> spuDatas = new HashMap<>();
            if(planVo!=null){
                spuDatas.put("planSPU", planVo.getPlanSPUNum());
            }else {
                spuDatas.put("planSPU",0);
            }
            spuDatas.put("onshelfSPUCount",supplyRead.getOnshelfSpuNum());
            spuDatas.put("passSPUCount",supplyRead.getPassSpuNum());
            if(supplyRead.getSpuNum()>0){
                spuDatas.put("passSPUProportion",RateUtil.intChainRate(supplyRead.getPassSpuNum(), supplyRead.getSpuNum()));
            }else {
                spuDatas.put("passSPUProportion",0d);
            }
            double passSPULink=0d;
             if(supplchainRead.getPassSpuNum()>0){
                 passSPULink=RateUtil.intChainRate(supplyRead.getPassSpuNum() - supplchainRead.getPassSpuNum(), supplchainRead.getPassSpuNum());
             }
             spuDatas.put("passSPULink",passSPULink);
            //sku总览
            Map<String, Object> skuDatas = new HashMap<>();
            if(planVo!=null){
                skuDatas.put("planSKU", planVo.getPlanSKUNum());
            }else {
                skuDatas.put("planSKU",0);
            }
            skuDatas.put("onshelfSKUCount",supplyRead.getOnshelfSkuNum());
            skuDatas.put("passSKUCount",supplyRead.getPassSkuNum());
            if(supplyRead.getSkuNum()>0){
                skuDatas.put("passSKUProportion",RateUtil.intChainRate(supplyRead.getPassSkuNum(), supplyRead.getSkuNum()));
            }else {
                skuDatas.put("passSKUProportion",0d);
            }
            double passSKULink=0d;
            if(supplchainRead.getPassSkuNum()>0){
                passSKULink=RateUtil.intChainRate(supplyRead.getPassSkuNum() - supplchainRead.getPassSkuNum(), supplchainRead.getPassSkuNum());
            }
            skuDatas.put("passSKULink",passSKULink);
            //封装数据
            data = new HashMap<>();
            data.put("supplierDatas", supplierDatas);
            data.put("skuDatas", skuDatas);
            data.put("spuDatas", spuDatas);
        }
        return new Result<>(data);
    }


    /**
     * 导出供应商已上架spu、sku明细
     * @param startTime
     * @param endTime
     * @param response
     * @return
     */
    @RequestMapping(value = "/exportSupplierOnshelfDetail")
    public Object exportSupplierOnshelfDetail(Date startTime, Date endTime, HttpServletResponse response) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return new Result<>(ResultStatusEnum.PARAM_ERROR);
        }
        endTime = DateUtil.getOperationTime(endTime, 23, 59, 59);
        String fullStartTime = DateUtil.formatDateToString(startTime, "yyyy/MM/dd HH:mm:ss");
        String fullEndTime = DateUtil.formatDateToString(endTime, DateUtil.FULL_FORMAT_STR2);
        Map<String, String> params = new HashMap<>();
        params.put("startTime", fullStartTime);
        params.put("endTime", fullEndTime);
        List<Map<String,Object>> dataList =onshelfInfoService.selectOnshelfDetailGroupBySupplier(params);
        XSSFWorkbook wb = onshelfInfoService.exportSupplierOnshelfDetail(startTime,endTime,dataList);
        //excel文件名
        String fileName = "上架供应商明细" + System.currentTimeMillis() + ".xlsx";
        try {
            RequestCreditController.setResponseHeader(response, fileName);
            wb.write(response.getOutputStream());
            return null;
        } catch (Exception e) {
            logger.debug("异常:" + e.getMessage(), e);
            return null;
        }
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
        return result.setStatus(ResultStatusEnum.DATA_NULL);
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
        List<SupplyCateDetailVo> list = this.supplyChainCategoryService.selectCateListByTime(startTime, endTime);
        int totalSKU=0;
        int totalSPU=0;
        int totalSupplier=0;
        if (list != null && list.size() > 0) {
            for (SupplyCateDetailVo vo:list ) {
                totalSKU+=vo.getSkuCount();
                totalSPU+=vo.getSpuCount();
                totalSupplier+=vo.getSupplierCount();
            }
            for (SupplyCateDetailVo vo:list ) {
                if(totalSKU>0){
                    vo.setSkuCompletionRate(RateUtil.intChainRate(vo.getSkuCount(), totalSKU));
                }
                if(totalSPU>0){
                    vo.setSpuCompletionRate(RateUtil.intChainRate(vo.getSpuCount(), totalSPU));
                }
                if(totalSupplier>0){
                    vo.setSupplierCompletionRate(RateUtil.intChainRate(vo.getSupplierCount(), totalSupplier));
                }
            }
            //按照spu数量排序
            list.sort((v1, v2) -> v2.getSpuCount() - v1.getSpuCount());

            result.setData(list);
            return result;
        }
        return result.setStatus(ResultStatusEnum.DATA_NULL);
    }

}
