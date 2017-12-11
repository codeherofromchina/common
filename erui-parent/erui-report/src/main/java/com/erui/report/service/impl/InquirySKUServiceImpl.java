package com.erui.report.service.impl;

import com.erui.report.dao.InquirySkuMapper;
import com.erui.report.dao.SupplyChainCategoryMapper;
import com.erui.report.model.InquirySkuExample;
import com.erui.report.model.SupplyChainCategoryExample;
import com.erui.report.service.InquirySKUService;
import com.erui.report.service.SupplyChainCategoryService;
import com.erui.report.util.SupplyCateDetailVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InquirySKUServiceImpl extends  BaseService<InquirySkuMapper> implements InquirySKUService {

    @Override
    public int selectSKUCountByTime(Date startDate, Date endDate) {
        InquirySkuExample e = new InquirySkuExample();
        InquirySkuExample.Criteria criteria = e.createCriteria();
        if(startDate!=null){
            criteria.andRollinTimeGreaterThanOrEqualTo(startDate);
        }
        if(endDate!=null){
            criteria.andRollinTimeLessThan(endDate);
        }
        return readMapper.selectSKUCountByTime(e);
    }
}
