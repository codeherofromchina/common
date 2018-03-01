package com.erui.quartz.executor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.comm.util.encrypt.MD5;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainRead;
import com.erui.report.service.InquiryCountService;
import com.erui.report.service.SupplyChainReadService;
import com.erui.report.util.GetDataEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component("reportQuartz")
public class ReportQuartz {

    @Autowired
    private SupplyChainReadService supplyChainReadService;
    @Autowired
    private InquiryCountService inquiryService;

    private static final String goodUrl = "http://api.erui.com/v2/Report/getGoodsCount";//获取sku数据请求路径
    private static final String productUrl = "http://api.erui.com/v2/Report/getProductCount";//获取spu数据请求路径
    private static final String supplierUrl = "http://api.erui.com/v2/Report/getSupplierCount";//获取供应商数据请求路径
    private static final String cateUrl = "http://api.erui.com/v2/Report/getCatProductCount";//获取供应链分类数据请求路径

    public final String inquiryUrl = "http://api.erui.com/v2/report/getTimeIntervalData";//获取询单数据请求路径

    private static final String key = "9b2a37b7b606c14d43db538487a148c7";
    private static ObjectMapper om = new ObjectMapper();

    public void getSupplyChainRead() throws Exception {
        //获取前一天的两个时间点
        System.out.println("执行一次调度");
        Date date = DateUtil.sometimeCalendar(new Date(), 1);
        String startTime = DateUtil.getStartTime(date, DateUtil.FULL_FORMAT_STR);
        String endTime = DateUtil.getEndTime(date, DateUtil.FULL_FORMAT_STR);

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut goodPutMethod = getPutMethod(1, goodUrl, startTime, endTime);
        HttpPut productPutMethod = getPutMethod(1, productUrl, startTime, endTime);
        HttpPut supplierPutMethod = getPutMethod(1, supplierUrl, startTime, endTime);
        HttpPut catePutMethod = getPutMethod(1, cateUrl, startTime, endTime);
        CloseableHttpResponse skuResult = client.execute(goodPutMethod);
        CloseableHttpResponse spuResult = client.execute(productPutMethod);
        //处理结果
        SupplyChainRead skuRead = this.handleResult(skuResult, GetDataEnum.SKU_DATA.getCode());
        SupplyChainRead spuRead = this.handleResult(spuResult, GetDataEnum.SPU_DATA.getCode());
        CloseableHttpResponse supplierResult = client.execute(supplierPutMethod);
        CloseableHttpResponse cateResult = client.execute(catePutMethod);
        SupplyChainRead supplierRead = this.handleResult(supplierResult, GetDataEnum.SUPPLIER_DATA.getCode());
        SupplyChainRead supplyChainRead = null;
        if (skuRead != null || spuRead != null || supplierRead != null) {
            supplyChainRead = mergeResult(skuRead, spuRead, supplierRead);
        }

        //处理分类的数据
        JSONObject json = new JSONObject();
        String cateData = EntityUtils.toString(cateResult.getEntity());
        JSONObject cateJson = json.parseObject(cateData);
        int cateCode = (int) cateJson.get("code");
        List<HashMap> chainCateVoList = null;
        if (cateCode == 1) {
            String cates = cateJson.get("data").toString();
            chainCateVoList = JSON.parseArray(cates, HashMap.class);
        }
        supplyChainReadService.supplyChainReadData(startTime, supplyChainRead, chainCateVoList);

        //处理询单数据
        HttpPut putMethod = getPutMethod(2, inquiryUrl, startTime, endTime);
        CloseableHttpResponse inquiryResult = client.execute(putMethod);
        String inquiryData = EntityUtils.toString(inquiryResult.getEntity());
        JSONObject inquiryObject = json.parseObject(inquiryData);
        Object inquiryCode = inquiryObject.get("code");
        List<HashMap> list = null;
        if (inquiryCode != null && Integer.parseInt(inquiryCode.toString()) == 1) {//成功了
            Object data = inquiryObject.get("data");
            if (data != null) {
                String dataJson = data.toString();
                list = JSON.parseArray(dataJson, HashMap.class);
            }
        }

        inquiryService.inquiryData(list);
    }
    /**
     * 刷新历史数据
     */
    public void totalData() throws Exception {
        List<Map<String, String>> list = getTimes();
        if(CollectionUtils.isNotEmpty(list)) {
            for (Map<String, String> map : list) {
                String startTime = map.get("startTime");
                String endTime = map.get("endTime");
                CloseableHttpClient client = HttpClients.createDefault();
                HttpPut goodPutMethod = getPutMethod(1, goodUrl, startTime, endTime);
                HttpPut productPutMethod = getPutMethod(1, productUrl, startTime, endTime);
                HttpPut supplierPutMethod = getPutMethod(1, supplierUrl, startTime, endTime);
                HttpPut catePutMethod = getPutMethod(1, cateUrl, startTime, endTime);
                CloseableHttpResponse skuResult = client.execute(goodPutMethod);
                CloseableHttpResponse spuResult = client.execute(productPutMethod);
                //处理结果
                SupplyChainRead skuRead = this.handleResult(skuResult, GetDataEnum.SKU_DATA.getCode());
                SupplyChainRead spuRead = this.handleResult(spuResult, GetDataEnum.SPU_DATA.getCode());
                CloseableHttpResponse supplierResult = client.execute(supplierPutMethod);
                CloseableHttpResponse cateResult = client.execute(catePutMethod);
                SupplyChainRead supplierRead = this.handleResult(supplierResult, GetDataEnum.SUPPLIER_DATA.getCode());
                SupplyChainRead supplyChainRead = null;
                if (skuRead != null || spuRead != null || supplierRead != null) {
                    supplyChainRead = mergeResult(skuRead, spuRead, supplierRead);
                }

                //处理分类的数据
                JSONObject json = new JSONObject();
                String cateData = EntityUtils.toString(cateResult.getEntity());
                JSONObject cateJson = json.parseObject(cateData);
                int cateCode = (int) cateJson.get("code");
                List<HashMap> chainCateVoList = null;
                if (cateCode == 1) {
                    String cates = cateJson.get("data").toString();
                    chainCateVoList = JSON.parseArray(cates, HashMap.class);
                }
                supplyChainReadService.supplyChainReadData(startTime, supplyChainRead, chainCateVoList);

                //处理询单数据
                HttpPut putMethod = getPutMethod(2, inquiryUrl, startTime, endTime);
                CloseableHttpResponse inquiryResult = client.execute(putMethod);
                String inquiryData = EntityUtils.toString(inquiryResult.getEntity());
                JSONObject inquiryObject = json.parseObject(inquiryData);
                Object inquiryCode = inquiryObject.get("code");
                List<HashMap> dlist = null;
                if (inquiryCode != null && Integer.parseInt(inquiryCode.toString()) == 1) {//成功了
                    Object data = inquiryObject.get("data");
                    if (data != null) {
                        String dataJson = data.toString();
                        dlist = JSON.parseArray(dataJson, HashMap.class);
                    }
                }

                inquiryService.inquiryData(dlist);
            }
        }
    }

    /**
     * 获取所有时间点列表
     */
    public List<Map<String, String>> getTimes() throws Exception {

        List<Map<String, String>> list = new ArrayList<>();
        Date day = DateUtil.parseStringToDate("2017-08-01 00:00:00", DateUtil.FULL_FORMAT_STR);
        int days = DateUtil.getDayBetween(day, new Date());
        String dateTime = DateUtil.getStartTime(new Date(), DateUtil.FULL_FORMAT_STR);
        //获取当前时间的其实时间，和结束时间
        String startTime = DateUtil.getStartTime(day,DateUtil.FULL_FORMAT_STR);
        String endTime = DateUtil.getEndTime(day,DateUtil.FULL_FORMAT_STR);
        for (int i =0; i <days-1; i++) {
            if(startTime.trim().equals(dateTime.trim()))  break;
            HashMap<String, String> dateMap = new HashMap<>();
            dateMap.put("startTime", startTime);
            dateMap.put("endTime", endTime);
            list.add(dateMap);
            day=DateUtil.sometimeCalendar(day,-1);
            startTime = DateUtil.getStartTime(day,DateUtil.FULL_FORMAT_STR);
            endTime = DateUtil.getEndTime(day,DateUtil.FULL_FORMAT_STR);
        }
        return list;
    }

    /**
     * 获取PutMethod
     */
    HttpPut getPutMethod(int type, String url, String startTime, String endTime) throws Exception {
        HttpPut method = new HttpPut(url);
        // method.getParams().setParameter("http.socket.timeout", 3000);
        //组装请求json
        JSONObject jsonObject = new JSONObject();
        Map<String, Object> input = new HashMap<>();
        input.put("lang", "zh");
        if (type == 1) {
            input.put("created_at_start", startTime);
            input.put("created_at_end", endTime);
        } else if (type == 2) {
            input.put("creat_at_start", startTime);
            input.put("creat_at_end", endTime);
            input.put("updat_at_start", startTime);
            input.put("update_at_end", endTime);
        }
        String inputStr = om.writeValueAsString(input);
        System.out.println("=================" + inputStr);
        String sign = MD5.encode(key + inputStr);
        System.out.println(sign + "=====");
        jsonObject.put("sign", sign);
        jsonObject.put("input", inputStr);
        StringEntity entity = new StringEntity(jsonObject.toString(), "utf-8");
        method.setEntity(entity);
        return method;
    }

    /**
     * 处理获取的数据结果
     */
    SupplyChainRead handleResult(CloseableHttpResponse response, int getDataCode) throws IOException {
        SupplyChainRead supplyChainRead = new SupplyChainRead();
        JSONObject json = new JSONObject();
        String rsultData = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = json.parseObject(rsultData);
        int resultCode = (int) jsonObject.get("code");
        if (resultCode == 1) {//成功
            int draft_count = 0;
            int brand_count = 0;
            int count = Integer.parseInt(jsonObject.get("count").toString());
            int checking_count = Integer.parseInt(jsonObject.get("checking_count").toString());
            int valid_count = Integer.parseInt(jsonObject.get("valid_count").toString());
            int invalid_count = Integer.parseInt(jsonObject.get("invalid_count").toString());
            if (getDataCode != GetDataEnum.SUPPLIER_DATA.getCode()) {
                draft_count = Integer.parseInt(jsonObject.get("draft_count").toString());
            } else {
                brand_count = Integer.parseInt(jsonObject.get("brand_count").toString());
            }
            System.out.println(count + "====" + draft_count + "====" + checking_count
                    + "===" + valid_count + "==" + invalid_count);
            if (getDataCode == GetDataEnum.SKU_DATA.getCode()) {
                supplyChainRead.setSkuNum(count);//'开发SKU数'
                supplyChainRead.setAuditSkuNum(checking_count);//'审核中SKU数'
                supplyChainRead.setRejectSkuNum(invalid_count);//'已驳回SKU数'
                supplyChainRead.setPassSkuNum(valid_count);//'已通过SKU数'
                supplyChainRead.setTempoSkuNum(draft_count);//'暂存SKU数'
                return supplyChainRead;
            } else if (getDataCode == GetDataEnum.SPU_DATA.getCode()) {
                supplyChainRead.setSpuNum(count);//'开发SKU数'
                supplyChainRead.setAuditSpuNum(checking_count);//'审核中SPU数'
                supplyChainRead.setRejectSpuNum(invalid_count);//'已驳回SPU数'
                supplyChainRead.setPassSpuNum(valid_count);//'已通过SPU数'
                supplyChainRead.setTempoSpuNum(draft_count);//'暂存SPU数'
                return supplyChainRead;
            } else if (getDataCode == GetDataEnum.SUPPLIER_DATA.getCode()) {
                supplyChainRead.setSuppliNum(count);//'开发SKU数'
                supplyChainRead.setAuditSuppliNum(checking_count);//'审核中供应商数'
                supplyChainRead.setRejectSuppliNum(invalid_count);//'已驳回供应商数'
                supplyChainRead.setPassSuppliNum(valid_count);//'已通过供应商数'
                supplyChainRead.setBrandNum(brand_count);
                return supplyChainRead;
            }
        }
        return null;
    }

    /**
     * 合并结果
     */
    SupplyChainRead mergeResult(SupplyChainRead... supplyChainRead) {
        SupplyChainRead chainRead = new SupplyChainRead();
        if (supplyChainRead != null && supplyChainRead.length > 0) {
            List<SupplyChainRead> reads = Arrays.asList(supplyChainRead);
            for (SupplyChainRead read : reads) {
                if (read.getSkuNum() != null) {
                    chainRead.setSkuNum(read.getSkuNum());//'开发SKU数'
                    chainRead.setAuditSkuNum(read.getAuditSkuNum());//'审核中SKU数'
                    chainRead.setRejectSkuNum(read.getRejectSkuNum());//'已驳回SKU数'
                    chainRead.setPassSkuNum(read.getPassSkuNum());//'已通过SKU数'
                    chainRead.setTempoSkuNum(read.getTempoSkuNum());//'暂存SKU数'
                } else if (read.getSpuNum() != null) {
                    chainRead.setSpuNum(read.getSpuNum());//'开发SKU数'
                    chainRead.setAuditSpuNum(read.getAuditSpuNum());//'审核中SPU数'
                    chainRead.setRejectSpuNum(read.getRejectSpuNum());//'已驳回SPU数'
                    chainRead.setPassSpuNum(read.getPassSpuNum());//'已通过SPU数'
                    chainRead.setTempoSpuNum(read.getTempoSpuNum());//'暂存SPU数'
                } else if (read.getSuppliNum() != null) {
                    chainRead.setSuppliNum(read.getSuppliNum());//'开发SKU数'
                    chainRead.setAuditSuppliNum(read.getAuditSuppliNum());//'审核中供应商数'
                    chainRead.setRejectSuppliNum(read.getRejectSuppliNum());//'已驳回供应商数'
                    chainRead.setPassSuppliNum(read.getPassSuppliNum());//'已通过供应商数'
                    chainRead.setBrandNum(read.getBrandNum());//供应商品牌数量
                }
            }
        }
        return chainRead;
    }
}
