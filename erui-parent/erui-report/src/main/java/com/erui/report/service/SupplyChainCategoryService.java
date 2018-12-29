package com.erui.report.service;

import com.erui.report.model.SupplyChainCategory;
import com.erui.report.util.SupplyCateDetailVo;

import java.util.Date;
import java.util.List;

public interface SupplyChainCategoryService {
 /**
 * 根据时间查询分类列表
* */
    List<SupplyCateDetailVo> selectCateListByTime(Date startTime, Date endTime);
}