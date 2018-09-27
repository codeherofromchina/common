package com.erui.order.dao;

import com.erui.order.entity.ProjectProfit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface ProjectProfitDao extends JpaRepository<ProjectProfit, Serializable>, JpaSpecificationExecutor<ProjectProfit> {

}
