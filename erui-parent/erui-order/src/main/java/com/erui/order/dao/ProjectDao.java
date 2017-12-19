package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface ProjectDao extends JpaRepository<Project, Serializable>,JpaSpecificationExecutor {
    List<Project> findByPurchReqCreateAndPurchDone(Integer purchReqCreate, Boolean b);
}
