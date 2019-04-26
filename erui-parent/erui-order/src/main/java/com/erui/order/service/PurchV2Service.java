package com.erui.order.service;

import com.erui.order.entity.Purch;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchV2Service {

    Purch findDetailInfo(Integer id);

    boolean update(Purch purch) throws Exception;

    boolean insert(Purch purch) throws Exception;


}
