package com.erui.report.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.TargetMapper;
import com.erui.report.model.TargetExample;
import com.erui.report.service.TargetService;
import org.springframework.stereotype.Service;

@Service
public class TargetServiceImpl extends  BaseService<TargetMapper> implements TargetService {


    @Override
    public Double selectTargetAmountByCondition(Integer leastMoth, Integer maxMoth, String area, String org) {
        TargetExample example = new TargetExample();
        TargetExample.Criteria criteria = example.createCriteria();
        if(leastMoth!=null){
            criteria.andTargetMothGreaterThanOrEqualTo(leastMoth);
        }
        if(maxMoth!=null){
            criteria.andTargetMothLessThanOrEqualTo(maxMoth);
        }
        if(StringUtil.isNotBlank(area)){
            criteria.andAreaEqualTo(area);
        }
        if(StringUtil.isNotBlank(org)){
            criteria.andOrgnizationEqualTo(org);
        }
        return readMapper.selectTargetAmountByCondition(example);
    }
}
