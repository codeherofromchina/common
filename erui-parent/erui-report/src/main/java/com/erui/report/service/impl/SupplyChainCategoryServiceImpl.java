package com.erui.report.service.impl;

import com.erui.report.dao.SupplyChainCategoryMapper;
import com.erui.report.model.SupplyChainCategory;
import com.erui.report.model.SupplyChainCategoryExample;
import com.erui.report.service.SupplyChainCategoryService;
import com.erui.report.util.SupplyCateDetailVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SupplyChainCategoryServiceImpl extends  BaseService<SupplyChainCategoryMapper> implements SupplyChainCategoryService {


    @Override
    public List<SupplyCateDetailVo> selectCateListByTime(Date startTime, Date endTime) {
        SupplyChainCategoryExample example = new SupplyChainCategoryExample();
        SupplyChainCategoryExample.Criteria criteria = example.createCriteria();
        if(startTime!=null){
            criteria.andCreateAtGreaterThanOrEqualTo(startTime);
        }
        if(endTime!=null){
            criteria.andCreateAtLessThan(endTime);
        }
        return this.readMapper.selectCateListByTime(example);
    }
}
