package com.erui.order.service;

import com.erui.order.entity.DeliverConsign;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeliverConsignService  {

    /**
     * 查找出口发货通知单
     * @param id
     * @return
     */
    public DeliverConsign findById(Integer id);
}
