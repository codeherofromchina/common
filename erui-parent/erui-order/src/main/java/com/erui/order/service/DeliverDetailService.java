package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.DeliverDetail;
import com.erui.order.requestVo.DeliverDetailVo;
import org.springframework.data.domain.Page;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverDetailService {
    /**
     * 根据id查询出库详情信息
     * @param id
     * @return
     */
    DeliverDetail findById(Integer id);

    /**
     * 分页查询物流-出库单详情列表
     * @param condition
     * @return
     */
    Page<DeliverDetail> listByPage(DeliverDetailVo condition);

    /**
     * 查询物流-出库单详情
     * @param id
     * @return
     */
    DeliverDetail findDetailById(Integer id);

    /**
     * 保存物流-出库单详情
     * @param deliverDetailVo
     * @return
     */
    boolean save(DeliverDetailVo deliverDetailVo) throws Exception;
}
