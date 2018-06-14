package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SupplierOnshelfInfoMapper;
import com.erui.report.model.SupplierOnshelfInfo;
import com.erui.report.service.SupplierOnshelfInfoService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SupplierOnshelfInfoServiceImpl  extends BaseService<SupplierOnshelfInfoMapper> implements SupplierOnshelfInfoService{


    @Override
    public void insertSupplierOnshelfInfoList(String startTime,List<HashMap> onshelfInfoList) throws Exception{

        Date date = DateUtil.parseStringToDate(startTime, DateUtil.FULL_FORMAT_STR);
        if(CollectionUtils.isNotEmpty(onshelfInfoList)) {

            for (Map<String, Object> m : onshelfInfoList) {

                SupplierOnshelfInfo sInfo=new SupplierOnshelfInfo();
                int spu=0;
                int sku=0;
                if(m.get("name")!=null) {
                    sInfo.setSupplier( m.get("name").toString());
                }
                if(m.get("spu_count")!=null){
                    spu=(int) m.get("spu_count");
                }
                if(m.get("sku_count")!=null){
                    sku=(int) m.get("sku_count");
                }
                if(spu<=0&&sku<=0) {
                    continue;
                }
                sInfo.setCreateAt(date);
                sInfo.setOnshelfSpuNum(spu);
                sInfo.setOnshelfSkuNum(sku);
                writeMapper.insertSelective(sInfo);
            }
        }

    }

    @Override
    public List<Map<String, Object>> selectOnshelfDetailGroupBySupplier(Map<String,String> params) {

        return readMapper.selectOnshelfDetailGroupBySupplier(params);
    }

    @Override
    public XSSFWorkbook exportSupplierOnshelfDetail(Date startTime,Date endTime,List<Map<String, Object>> dataList) {
        //声明工作簿
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//设置字体居中

        //生成一个表格
        XSSFSheet sheet = wb.createSheet("上架供应商明细表");
        sheet.setDefaultColumnWidth(10);
        sheet.setColumnWidth(0, 8192);
        sheet.setColumnWidth(1, 8192);
        sheet.setColumnWidth(2, 4521);
        sheet.setColumnWidth(3, 4521);
        //产生标题行
        XSSFRow row = sheet.createRow(0);
        String[] headers = new String[]{"时间", "供应商名称", "上架SPU", "上架SKU"};
        for (int i = 0; i < headers.length; i++) {
            XSSFCell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
        }

        //拼接日期
        String start = DateUtil.formatDateToString(startTime, DateUtil.SHORT_SLASH_FORMAT_STR);
        String end = DateUtil.formatDateToString(endTime, DateUtil.SHORT_SLASH_FORMAT_STR);
        String datetime=start+"-"+end;
        //添加数据
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (int i = 0; i < dataList.size(); i++) {
                Map<String, Object> m = dataList.get(i);
                XSSFRow row1 = sheet.createRow(i + 1);
                XSSFCell cell0 = row1.createCell(0);
                cell0.setCellValue(datetime);
                XSSFCell cell1 = row1.createCell(1);
                cell1.setCellValue(m.get("supplier").toString());
                XSSFCell cell2 = row1.createCell(2);
                cell2.setCellValue(m.get("onshelfSPU").toString());
                XSSFCell cell3 = row1.createCell(3);
                cell3.setCellValue(m.get("onshelfSKU").toString());
            }
        }
        return wb;
    }


}
