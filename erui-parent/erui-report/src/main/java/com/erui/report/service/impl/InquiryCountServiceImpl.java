package com.erui.report.service.impl;

import com.erui.comm.util.data.string.StringUtil;
import com.erui.report.dao.InquiryCountMapper;
import com.erui.report.model.InquiryCountExample;
import com.erui.report.service.InquiryCountService;



import com.erui.report.util.ImportDataResponse;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Service;


/*
* 客户中心-询单统计  服务实现类
* */
@Service
public class InquiryCountServiceImpl extends BaseService<InquiryCountMapper> implements InquiryCountService {






//    根据时间统计询单单数
    @Override
    public int inquiryCountByTime(Date startTime, Date endTime,String quotedStatus,double leastQuoteTime,double maxQuoteTime) {
        InquiryCountExample inquiryExample = new InquiryCountExample();
        InquiryCountExample.Criteria criteria = inquiryExample.createCriteria();
        if(startTime!=null&&!"".equals(startTime)&&endTime!=null&&!"".equals(endTime)){
            criteria.andRollinTimeBetween(startTime,endTime);
        }

        if(quotedStatus!=null&&!"".equals(quotedStatus)){
            criteria.andQuotedStatusEqualTo(quotedStatus);
        }
        if(leastQuoteTime>0&&maxQuoteTime>0){
            BigDecimal ldecimal = new BigDecimal(leastQuoteTime);
            BigDecimal mdecimal = new BigDecimal(maxQuoteTime);
            criteria.andQuoteNeedTimeBetween(ldecimal,mdecimal);
        }
        int count = readMapper.countByExample(inquiryExample);
        return count;
    }

	@Override
	public ImportDataResponse importData(List<String[]> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	//根据时间和产品类别查询产品数量
	@Override
	public int selectProCountByIsOil(Date startTime, Date endTime,String isOil) {
        InquiryCountExample example = new InquiryCountExample();
        InquiryCountExample.Criteria criteria = example.createCriteria();
        criteria.andRollinTimeBetween(startTime,endTime);
        if(isOil!=null&&!isOil.equals("")){
            criteria.andIsOilGasEqualTo(isOil);
        }
        int proCount = readMapper.selectProCountByIsOil(example);
        return proCount;
	}

	//查询产品Top3
    @Override
    public List<Map<String,Object>> selectProTop3(Map<String,Object>params) {
        List<Map<String,Object>> list = readMapper.selectProTop3(params);
        return list;
    }
    //查询事业部列表
    @Override
    public List<String> selectOrgList() {
        return readMapper.selectOrgList();
    }


    // //     根据时间统计询单金额
	public Double inquiryAmountByTime(Date startTime, Date endTime) {
		InquiryCountExample example = new InquiryCountExample();
		example.createCriteria().andRollinTimeBetween(startTime, endTime);
		Double amount = readMapper.selectTotalAmountByExample(example);
		return amount;
	}

}