package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.InspectApply;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface InspectApplyService {
    /**
     * 根据id查询报检信息
     * @param id
     * @return
     */
    InspectApply findById(Integer id);
}
