package com.erui.order.service;

import com.erui.order.entity.Area;
import com.erui.order.entity.Dept;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
public interface DeptService {
    /**
     * 根据id查询分销部信息
     * @param id
     * @return
     */
    Dept findById(Integer id);


    /**
     * 根据name查询分销部信息
     * @param name
     * @return
     */
    Dept findTop1ByName(String name) ;


    /**
     * 根据enName查询分销部信息
     * @param enName
     * @return
     */
    Dept findTop1ByEnName(String enName) ;
}
