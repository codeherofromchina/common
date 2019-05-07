package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.DeliverDetailGoodsMapper;
import com.erui.order.v2.dao.DeliverDetailMapper;
import com.erui.order.v2.model.DeliverDetail;
import com.erui.order.v2.model.DeliverDetailExample;
import com.erui.order.v2.model.DeliverDetailGoodsKey;
import com.erui.order.v2.model.DeliverDetailWithBLOBs;
import com.erui.order.v2.service.DeliverdetailService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/5/6 下午7:34
 */
@Service("deliverdetailServiceImplV2")
public class DeliverdetailServiceImpl implements DeliverdetailService {
    @Autowired
    private DeliverDetailMapper deliverDetailMapper;
    @Autowired
    private DeliverDetailGoodsMapper deliverDetailGoodsMapper;

    @Override
    public String findNewestDeliverDetailNo() {
        PageHelper.offsetPage(0, 1, false);
        DeliverDetailExample example = new DeliverDetailExample();
        example.setOrderByClause("order by deliver_detail_no desc");

        List<DeliverDetail> deliverDetails = deliverDetailMapper.selectByExample(example);
        if (deliverDetails != null && deliverDetails.size() > 0) {
            DeliverDetail deliverDetail = deliverDetails.get(0);
            return deliverDetail.getDeliverDetailNo();
        }
        return null;
    }

    @Override
    public void insert(DeliverDetailWithBLOBs deliverDetail) {
        deliverDetailMapper.insert(deliverDetail);
    }

    @Override
    public void insertDeliverGoodsRelation(Integer deliverDetailId, List<Integer> deliverConsignGoodsIds) {
        for (Integer deliverConsignGoodsId : deliverConsignGoodsIds) {
            DeliverDetailGoodsKey deliverDetailGoodsKey = new DeliverDetailGoodsKey();
            deliverDetailGoodsKey.setDeliverDetailId(deliverDetailId);
            deliverDetailGoodsKey.setDeliverConsignGoodsId(deliverConsignGoodsId);
            deliverDetailGoodsMapper.insert(deliverDetailGoodsKey);
        }
    }
}
