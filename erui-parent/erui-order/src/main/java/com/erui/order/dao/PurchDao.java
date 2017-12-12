package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Purch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface PurchDao extends JpaRepository<Purch, Serializable>, JpaSpecificationExecutor<Purch> {
}
