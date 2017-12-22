package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderAccountDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.service.OrderAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by wangxiaodan on 2017/12/11.
 */
@Service
public class OrderAccountServiceImpl implements OrderAccountService {

    @Autowired
    private OrderAccountDao orderAccountDao;

    @Autowired
    private OrderDao orderDao;

    /**
     * 根据id 查询订单收款信息(单条)
     * @param id    收款信息id
     *
     * @return
     */
    @Override
    public OrderAccount findById(Integer id) {
        return orderAccountDao.findOne(id);
    }


    /**
     * 收款记录查询   (根据订单，查询全部订单记录)
     *
     * @param id    订单id
     * @return
     */
    @Override
    public List<OrderAccount> queryGatheringRecordAll(Integer id) {

        return orderAccountDao.findByOrderId(id);
    }


    /**
     *  根据收款信息id 逻辑删除
     *
     * @param id       收款信息id
     */
    @Override
    public void delGatheringRecord(Integer id) {
        OrderAccount orderAccounts=orderAccountDao.findOne(id);
        orderAccounts.setDelYn(0);
        orderAccountDao.save(orderAccounts);
    }


    /**
     *  添加一条收款记录
     *
     * @param orderAccount  收款信息
     * @return
     */
    @Override
    public void addGatheringRecord(OrderAccount orderAccount) {
      /*orderAccount.setPaymentDate(new Date());*/   //测试放开
        orderAccount.setCreateTime(new Date());
        orderAccountDao.save(orderAccount);

        Order order = orderDao.findOne(orderAccount.getOrder().getId());
        order.setPayStatus(1);
        orderDao.saveAndFlush(order);
    }


    /**
     * 编辑收款订单
     *
     * @param orderAccount
     * @return
     */
    @Override
    public void updateGatheringRecord(OrderAccount orderAccount) {
        OrderAccount orderAccounts=orderAccountDao.findOne(orderAccount.getId());
        if (orderAccount.getDesc()!=null){
            orderAccounts.setDesc(orderAccount.getDesc());
        }
        if (orderAccount.getMoney()!=null){
            orderAccounts.setMoney(orderAccount.getMoney());
        }
        if (orderAccount.getDiscount()!=null){
            orderAccounts.setDiscount(orderAccount.getDiscount());
        }
        if (orderAccount.getPaymentDate()!=null){
            orderAccounts.setPaymentDate(orderAccount.getPaymentDate());
        }
        if (orderAccount.getGoodsPrice()!=null){
            orderAccounts.setGoodsPrice(orderAccount.getGoodsPrice());
        }
        if (orderAccount.getDeliverDate()!=null){
            orderAccounts.setDeliverDate(orderAccount.getDeliverDate());
        }
        orderAccounts.setUpdateTime(new Date());
        orderAccountDao.saveAndFlush(orderAccounts);
    }


    /**
     * 确认全部收款完成
     * @return
     */
    @Override
    public void endGatheringRecord(Integer id) {
        Order order = orderDao.findOne(id);
        order.setPayStatus(2);
        orderDao.saveAndFlush(order);
    }


    /**
     * 收款信息
     *
     * @param id
     * @return
     */
      @Override
    public Order gatheringMessage( Integer id) {
        Order order =orderDao.findOne(id);  //查询订单信息

        List<OrderAccount> byOrderId = orderAccountDao.findByOrderId(id);

        BigDecimal sumGoodsPrice = BigDecimal.valueOf(0);  //发货金额
        BigDecimal sumMoney = BigDecimal.valueOf(0); ;       //汇款金额
        BigDecimal sumDiscount = BigDecimal.valueOf(0);      //其他扣款金额
        int size = byOrderId.size();
        for (int i=0;i<size;i++){
            if(byOrderId.get(i).getGoodsPrice() != null){
                sumGoodsPrice= sumGoodsPrice.add( byOrderId.get(i).getGoodsPrice());
            }
            if(byOrderId.get(i).getMoney()!=null){
                sumMoney = sumMoney.add(byOrderId.get(i).getMoney());
            }
            if(byOrderId.get(i).getDiscount()!= null){
                sumDiscount = sumDiscount.add(byOrderId.get(i).getDiscount());
            }
        }
        order.setReceivableAccountRemaining(sumGoodsPrice.subtract(sumMoney).subtract(sumDiscount));    //应收账款余额
        order.setAttachmentSet(null);
        order.setGoodsList(null);
        order.setOrderPayments(null);
        return order;
    }


    /**
     * 收款管理
     *
     * @param order
     * @return
     */
 @Override
    public Page<Order> gatheringManage(Order order) {
        PageRequest request = new PageRequest(order.getPage(), order.getRows(), null);
        Page<Order> pageOrder = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(order.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + order.getContractNo() + "%"));
                }
                //根据订单状态查询
                if(order.getStatus()!= null){
                    list.add(cb.equal(root.get("status").as(Integer.class),order.getStatus()));
                }
                //根据订单签约日期
                if(order.getSigningDate()!=null){
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(order.getSigningDate())));
                }
                //根据po号
                if(StringUtil.isNotBlank(order.getPoNo())){
                    list.add(cb.like(root.get("poNo").as(String.class),"%"+ order.getPoNo() + "%"));
                }
                //根据收款状态
                if(order.getPayStatus()!= null){
                    list.add(cb.equal(root.get("payStatus").as(Integer.class),order.getPayStatus()));
                }
                //根据CRM客户代码
                if(StringUtil.isNotBlank(order.getCrmCode())){
                    list.add(cb.equal(root.get("crmCode").as(String.class),order.getCrmCode()));
                }
                //


                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        },request);


     return pageOrder;
    }


}
