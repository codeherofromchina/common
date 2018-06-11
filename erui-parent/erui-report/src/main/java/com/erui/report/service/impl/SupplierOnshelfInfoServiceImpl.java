package com.erui.report.service.impl;

import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.dao.SupplierOnshelfInfoMapper;
import com.erui.report.model.SupplierOnshelfInfo;
import com.erui.report.service.SupplierOnshelfInfoService;
import org.apache.commons.collections4.CollectionUtils;
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


}
