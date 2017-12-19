package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverNotice;
import org.springframework.data.domain.Page;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverNoticeService {
    /**
     * 根据id查询看货通知单信息
     * @param id
     * @return
     */
    DeliverNotice findById(Integer id);

    /**
     * 根据条件分页查询看货通知单
     * @param condition
     * @return
     */
    Page<DeliverNotice> listByPage(DeliverNotice condition);
}
