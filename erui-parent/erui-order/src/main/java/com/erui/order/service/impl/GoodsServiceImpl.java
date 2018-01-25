package com.erui.order.service.impl;

import com.erui.order.dao.AreaDao;
import com.erui.order.dao.GoodsDao;
import com.erui.order.entity.Area;
import com.erui.order.entity.Goods;
import com.erui.order.service.AreaService;
import com.erui.order.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsDao goodsDao;

    @Override
    @Transactional(readOnly = true)
    public Goods findById(Integer id) {
        return goodsDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Goods> findByProjectIds(List<Integer> projectIds) {
        List<Goods> goodsList = null;
        if (projectIds != null && projectIds.size() > 0) {
            goodsList = goodsDao.findByProjectIdIn(projectIds);
        }
        if (goodsList == null) {
            goodsList = new ArrayList<>();
        }
        return goodsList;
    }
}
