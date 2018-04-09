package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Iogistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface IogisticsDao extends JpaRepository<Iogistics, Serializable> ,JpaSpecificationExecutor<Iogistics> {

    Iogistics findById(Integer id);
}
