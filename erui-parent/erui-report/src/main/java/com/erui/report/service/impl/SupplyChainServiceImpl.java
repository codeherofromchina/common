package com.erui.report.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.erui.comm.RateUtil;
import com.erui.comm.util.data.date.DateUtil;
import com.erui.report.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.erui.report.dao.SupplyChainMapper;
import com.erui.report.service.SupplyChainService;
import com.erui.report.util.ExcelUploadTypeEnum;
import com.erui.report.util.ImportDataResponse;

@Service
public class SupplyChainServiceImpl extends BaseService<SupplyChainMapper> implements SupplyChainService {
	private final static Logger logger = LoggerFactory.getLogger(RequestCreditServiceImpl.class);

	/**
	 * @Author:SHIGS
	 * @Description 查询 spu sku 供应商完成量
	 * @Date:16:02 2017/10/21
	 * @modified By
	 */
	@Override
	public List<Map> selectFinishByDate(Date startDate, Date endDate) {
		SupplyChainExample supplyChainExample = new SupplyChainExample();
		supplyChainExample.createCriteria().andCreateAtBetween(startDate, endDate);
		return this.readMapper.selectFinishByDate(supplyChainExample);
	}

	@Override
	public ImportDataResponse importData(List<String[]> datas, boolean testOnly) {
		ImportDataResponse response = new ImportDataResponse();
		int size = datas.size();
		SupplyChain sc = null;
		for (int index = 0; index < size; index++) {
			String[] strArr = datas.get(index);
			if (ExcelUploadTypeEnum.verifyData(strArr, ExcelUploadTypeEnum.REQUEST_CREDIT, response, index + 1)) {
				continue;
			}
			sc = new SupplyChain();

			try {
				sc.setCreateAt(DateUtil.parseString2Date(strArr[0], "yyyy/M/d", "yyyy/M/d hh:mm:ss",
						DateUtil.FULL_FORMAT_STR, DateUtil.SHORT_FORMAT_STR));
			} catch (Exception e) {
				logger.error(e.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "日期字段格式错误");
				continue;
			}
			sc.setOrganization(strArr[1]);
			sc.setCategory(strArr[2]);
			sc.setItemClass(strArr[3]);

			try {
				sc.setPlanSkuNum(new BigDecimal(strArr[4]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划SKU字段非数字");
				continue;
			}
			try {
				sc.setFinishSkuNum(new BigDecimal(strArr[5]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "SKU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSpuNum(new BigDecimal(strArr[6]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划SPU字段非数字");
				continue;
			}
			try {
				sc.setFinishSpuNum(new BigDecimal(strArr[7]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "SPU实际完成字段非数字");
				continue;
			}
			try {
				sc.setPlanSuppliNum(new BigDecimal(strArr[8]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "计划供应商数量非数字");
				continue;
			}
			try {
				sc.setFinishSuppliNum(new BigDecimal(strArr[9]).intValue());
			} catch (Exception ex) {
				logger.error(ex.getMessage());
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, "供应商数量实际开发字段非数字");
				continue;
			}

			try {
				if (!testOnly) {
					writeMapper.insertSelective(sc);
					response.incrSuccess();
				}
			} catch (Exception e) {
				response.incrFail();
				response.pushFailItem(ExcelUploadTypeEnum.REQUEST_CREDIT.getTable(), index + 1, e.getMessage());
			}

		}
		response.setDone(true);

		return response;
	}

	@Override
	public List<SupplyChain> queryListByDate(Date startTime, Date endTime) {
        SupplyChainExample example = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = example.createCriteria();
        if(startTime!=null&&!startTime.equals("")&&startTime!=null&&!startTime.equals("")){
            criteria.andCreateAtBetween(startTime,endTime);
		}
		return readMapper.selectByExample(example);
	}
    //事业部-供应链明细列表
    @Override
    public List<SuppliyChainOrgVo> selectOrgSuppliyChain() {
        List<SuppliyChainOrgVo> list=readMapper.selectOrgSuppliyChain();
        return list;
    }

    @Override
    public List<SuppliyChainItemClassVo> selectItemCalssSuppliyChain(Date startTime,Date endTime) {
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
            criteria.andCreateAtBetween(startTime, endTime);
        return readMapper.selectItemCalssSuppliyChainByExample(supplyChainExample);
    }

    @Override
    public SuppliyChainItemClassVo selectSuppliyChainByItemClass(Date startTime, Date endTime, String itemClass) {
        SupplyChainExample supplyChainExample = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = supplyChainExample.createCriteria();
        criteria.andCreateAtBetween(startTime, endTime);
        criteria.andItemClassEqualTo(itemClass);
        return readMapper.selectSuppliyChainByItemClassByExample(supplyChainExample);
    }

    @Override
    public List<SuppliyChainCateVo> selectCateSuppliyChain() {
        return  readMapper.selectCateSuppliyChain();
    }

    //供应链趋势图
    @Override
    public SupplyTrendVo supplyTrend(int days, int type) {
        SupplyChainExample example = new SupplyChainExample();
        SupplyChainExample.Criteria criteria = example.createCriteria();
        if(days>0){
            criteria.andCreateAtBetween(DateUtil.recedeTime(days),new Date());
        }
        List<SupplyChain>  list=readMapper.selectByExample(example);
        String[] datetime = new String[list.size()];
        Integer[] suppliyFinishCount = null;
        Integer[] SPUFinishCount =null;
        Integer[] SKUFinishCount = null;
        if(type==0){
            suppliyFinishCount = new Integer[list.size()];
        }else if(type==1){
            SPUFinishCount = new Integer[list.size()];
        }else if(type==2){
            SKUFinishCount= new Integer[list.size()];
        }
        if(list!=null&&list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                datetime[i]=DateUtil.formatDate2String(list.get(i).getCreateAt(),"M月d日");
                if(type==0){
                    suppliyFinishCount[i]=list.get(i).getFinishSuppliNum();
                }else if(type==1){
                    SPUFinishCount[i]=list.get(i).getFinishSpuNum();
                }else if(type==2){
                    SKUFinishCount[i]=list.get(i).getFinishSkuNum();
                }
        }
        }
        SupplyTrendVo trend = new SupplyTrendVo(datetime,suppliyFinishCount,SPUFinishCount,SKUFinishCount);

        return trend;
    }
}