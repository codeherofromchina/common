package com.erui.order.dao;

import com.erui.order.entity.Area;
import com.erui.order.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeptDao extends JpaRepository<Dept, Serializable> {
    Dept findTop1ByEnName(String enName);

    Dept findTop1ByName(String name);
}
