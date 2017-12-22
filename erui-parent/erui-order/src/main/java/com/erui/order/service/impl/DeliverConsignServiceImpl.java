package com.erui.order.service.impl;

import com.erui.order.dao.DeliverConsignDao;
import com.erui.order.entity.*;
import com.erui.order.service.DeliverConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class DeliverConsignServiceImpl implements DeliverConsignService {

    @Autowired
    private DeliverConsignDao deliverConsignDao;

    @Override
    public DeliverConsign findById(Integer id) {
        return deliverConsignDao.findOne(id);
    }


    /**
     * 根据出口发货通知单 查询信息
     *
     * @param deliverNoticeNos 看货通知单号  数组
     * @return
     */
    @Override
    @Transactional
    public List<DeliverConsign> querExitInformMessage(Integer[] deliverNoticeNos) {
        List<DeliverConsign> page = deliverConsignDao.findByIdInAndStatus(deliverNoticeNos, "3");

        for (DeliverConsign deliverConsign : page){
            deliverConsign.getOrder().getId();
            deliverConsign.getAttachmentSet().size();
            Set<DeliverConsignGoods> deliverConsignGoodsSet = deliverConsign.getDeliverConsignGoodsSet();
            for (DeliverConsignGoods deliverConsignGoods : deliverConsignGoodsSet){
                deliverConsignGoods.getGoods().getId();
            }

        }

        return page;
    }



    /**
     * 看货通知管理   查询出口发货通知单
     * @return
     */
    @Override
    public List<DeliverConsign> queryExitAdvice() {
        List<DeliverConsign> lsit=deliverConsignDao.findByStatusAndDeliverYn(3,2);
        return lsit;
    }


}
