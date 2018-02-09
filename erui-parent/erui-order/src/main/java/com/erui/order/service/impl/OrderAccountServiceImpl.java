package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderAccountDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.OrderLogDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.OrderAccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private static Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderAccountDao orderAccountDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderServiceImpl orderService;


    @Autowired
    OrderLogDao orderLogDao;

    /**
     * 根据id 查询订单收款信息(单条)
     *
     * @param id 收款信息id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public OrderAccount findById(Integer id) {
        return orderAccountDao.findOne(id);
    }


    /**
     * 收款记录查询   (根据订单，查询全部订单记录)
     *
     * @param id 订单id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderAccount> queryGatheringRecordAll(Integer id) {

        return orderAccountDao.findByOrderIdAndDelYn(id, 1);
    }


    /**
     * 根据收款信息id 逻辑删除
     *
     * @param id 收款信息id
     */
    @Override
    @Transactional
    public void delGatheringRecord(Integer id) {


        /**
         ** 逻辑删除收款记录
         */

        OrderAccount orderAccounts = orderAccountDao.findOne(id);   //查询收款记录
        orderAccounts.setDelYn(0);
        orderAccountDao.save(orderAccounts);        //收款记录  逻辑删除

        /**
         * 判断是否是收款中状态
         */
        Integer id1 = orderAccounts.getOrder().getId();//拿到订单id
        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id1,1);
        //无收款记录  改变收款状态为  1:未付款      （ 1:未付款 2:部分付款 3:收款完成'）
        if(byOrderId.size() == 0){
            Order one = orderDao.findOne(id1);
            one.setPayStatus(1);
            orderDao.saveAndFlush(one);
        }

        /**
         *  更正应收账款余额
         */
        /*//TODO
        Order order = orderDao.findOne(id1);  //查询订单信息
        List<OrderAccount> byOrderId2 = orderAccountDao.findByOrderIdAndDelYn(id,1);  //查询订单收款记录
        BigDecimal sumGoodsPrice = BigDecimal.valueOf(0);  //发货金额
        BigDecimal sumMoney = BigDecimal.valueOf(0);     //回款金额
        BigDecimal sumDiscount = BigDecimal.valueOf(0);      //其他扣款金额
        int size = byOrderId2.size();   //收款记录条数
        for (int i = 0; i < size; i++) {
            if (byOrderId2.get(i).getGoodsPrice() != null) {
                sumGoodsPrice = sumGoodsPrice.add(byOrderId2.get(i).getGoodsPrice());    //发货金额
            }
            if (byOrderId2.get(i).getMoney() != null) {
                sumMoney = sumMoney.add(byOrderId2.get(i).getMoney());       //回款金额
            }
            if (byOrderId2.get(i).getDiscount() != null) {
                sumDiscount = sumDiscount.add(byOrderId2.get(i).getDiscount());      //其他扣款金额
            }
        }
        BigDecimal subtract = sumGoodsPrice.subtract(sumMoney).subtract(sumDiscount);    // 应收账款余额=发货金额-回款金额-其他扣款金额
        if(subtract.compareTo(BigDecimal.ZERO) == 1){
            order.setReceivableAccountRemaining(subtract);    //应收账款余额
        }else{
            order.setReceivableAccountRemaining(BigDecimal.ZERO);
        }
        orderDao.saveAndFlush(order);
*/

        /**
         * //日志记录表
         */
        OrderLog byOrderAccountId = orderLogDao.findByOrderAccountId(id);
        orderLogDao.delete(byOrderAccountId.getId());
    }


    /**
     * 添加一条收款记录
     *
     * @param orderAccount 收款信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGatheringRecord(OrderAccount orderAccount) throws Exception {
      /*orderAccount.setPaymentDate(new Date());*/   //测试放开
        orderAccount.setCreateTime(new Date());
        OrderAccount orderAccount1;
        try {
            orderAccount1 =orderAccountDao.save(orderAccount);
        }catch (Exception e){
            throw new Exception("订单收款记录添加失败");
        }

        Order order = orderDao.findOne(orderAccount.getOrder().getId());
        if( order== null){
            throw new Exception("无订单id："+orderAccount.getOrder().getId()+"  关联关系");
        }
        order.setPayStatus(2);
        orderDao.saveAndFlush(order);

        //推送收到预付款
        OrderLog orderLog = new OrderLog();
        try {
            orderLog.setOrder(orderDao.findOne(order.getId()));
            orderLog.setLogType(OrderLog.LogTypeEnum.ADVANCE.getCode());
            orderLog.setOperation(StringUtils.defaultIfBlank(orderAccount.getDesc(), OrderLog.LogTypeEnum.ADVANCE.getMsg()) +"  "+orderAccount.getMoney() +" "+order.getCurrencyBn());
            orderLog.setCreateTime(new Date());
            orderLog.setOrdersGoodsId(null);
            orderLog.setOrderAccountId(orderAccount1.getId());
            orderLogDao.save(orderLog);
        } catch (Exception ex) {
            logger.error("日志记录失败 {}", orderLog.toString());
            logger.error("错误", ex);
            ex.printStackTrace();
            throw new Exception("订单收款记录日志添加失败");
        }

    }


    /**
     * 编辑收款订单
     *
     * @param orderAccount
     * @return
     */
    @Override
    @Transactional
    public void updateGatheringRecord(OrderAcciuntAdd orderAccount) {
        OrderAccount orderAccounts = orderAccountDao.findOne(orderAccount.getId()); //查询收款

        OrderLog orderLog = orderLogDao.findByOrderAccountId(orderAccount.getId()); //查询日志

        String currencyBn = orderAccounts.getOrder().getCurrencyBn();   //金额类型
        if(StringUtil.isNotBlank(orderAccount.getDesc()) && orderAccount.getMoney() != null  ){
            orderLog.setOperation(StringUtils.defaultIfBlank(orderAccount.getDesc(), OrderLog.LogTypeEnum.ADVANCE.getMsg()) +"  "+orderAccount.getMoney() +" "+currencyBn);
            orderLogDao.save(orderLog);
        }else if(StringUtil.isNotBlank(orderAccount.getDesc()) || orderAccount.getMoney() != null){
            if(StringUtil.isNotBlank(orderAccount.getDesc())){
                orderLog.setOperation(StringUtils.defaultIfBlank(orderAccount.getDesc(), OrderLog.LogTypeEnum.ADVANCE.getMsg()) +"  "+orderAccounts.getMoney() +" "+currencyBn);
                orderLogDao.save(orderLog);
            }else if(orderAccount.getMoney() != null){
                orderLog.setOperation(StringUtils.defaultIfBlank(orderAccounts.getDesc(), OrderLog.LogTypeEnum.ADVANCE.getMsg()) +"  "+orderAccount.getMoney() +" "+currencyBn);
                orderLogDao.save(orderLog);
            }
        }


        if (orderAccount.getDesc() != null) {
            orderAccounts.setDesc(orderAccount.getDesc());
        }
        if (orderAccount.getMoney() != null) {
            orderAccounts.setMoney(orderAccount.getMoney());
        }
        /*if (orderAccount.getDiscount() != null) {*/
            orderAccounts.setDiscount(orderAccount.getDiscount());
       /*}*/
        if (orderAccount.getPaymentDate() != null) {
            orderAccounts.setPaymentDate(orderAccount.getPaymentDate());
        }
        if (orderAccount.getGoodsPrice() != null) {
            orderAccounts.setGoodsPrice(orderAccount.getGoodsPrice());
        }
        if (orderAccount.getDeliverDate() != null) {
            orderAccounts.setDeliverDate(orderAccount.getDeliverDate());
        }
        orderAccounts.setUpdateTime(new Date());
        orderAccountDao.saveAndFlush(orderAccounts);
    }


    /**
     * 确认全部收款完成
     *
     * @return
     */
    @Override
    @Transactional
    public void endGatheringRecord(Integer id) throws Exception {

        /**
         * 查看当前订单是否有收款记录
         */
        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id,1);
        if(byOrderId.size() == 0){
            throw new Exception("无收款记录");
        }
        Order order = orderDao.findOne(id);
        order.setPayStatus(3);
        orderDao.saveAndFlush(order);

        /*orderService.addLog(OrderLog.LogTypeEnum.DELIVERYDONE, order.getId(), null, null);    //推送全部交收完成*/
    }


    /**
     * 收款信息
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Order gatheringMessage(Integer id) {
        Order order = orderDao.findOne(id);  //查询订单信息

        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id,1);

        BigDecimal sumGoodsPrice = BigDecimal.valueOf(0);  //发货金额
        BigDecimal sumMoney = BigDecimal.valueOf(0);     //回款金额
        BigDecimal sumDiscount = BigDecimal.valueOf(0);      //其他扣款金额

        int size = byOrderId.size();
        for (int i = 0; i < size; i++) {
            if (byOrderId.get(i).getGoodsPrice() != null) {
                sumGoodsPrice = sumGoodsPrice.add(byOrderId.get(i).getGoodsPrice());    //发货金额
            }
            if (byOrderId.get(i).getMoney() != null) {
                sumMoney = sumMoney.add(byOrderId.get(i).getMoney());       //回款金额
            }
            if (byOrderId.get(i).getDiscount() != null) {
                sumDiscount = sumDiscount.add(byOrderId.get(i).getDiscount());      //其他扣款金额
            }
        }

        BigDecimal subtract = sumGoodsPrice.subtract(sumMoney).subtract(sumDiscount);

       // 应收账款余额=发货金额-回款金额-其他扣款金额
        order.setReceivableAccountRemaining(subtract);    //应收账款余额
        return order;
    }


    /**
     * 收款管理
     *
     * @param condition
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Order> gatheringManage(OrderListCondition condition) {
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC,"createTime");
        Page<Order> pageOrder = orderDao.findAll(new Specification<Order>() {
            @Override
            public Predicate toPredicate(Root<Order> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                // 根据采购合同号模糊查询
                if (StringUtil.isNotBlank(condition.getContractNo())) {
                    list.add(cb.like(root.get("contractNo").as(String.class), "%" + condition.getContractNo() + "%"));
                }
                //根据订单状态查询
                if (condition.getStatus() != null) {
                    list.add(cb.equal(root.get("status").as(Integer.class), condition.getStatus()));
                }
                //根据订单签约日期
                if (condition.getSigningDate() != null) {
                    list.add(cb.equal(root.get("signingDate").as(Date.class), NewDateUtil.getDate(condition.getSigningDate())));
                }
                //根据po号
                if (StringUtil.isNotBlank(condition.getPoNo())) {
                    list.add(cb.like(root.get("poNo").as(String.class), "%" + condition.getPoNo() + "%"));
                }
                //根据收款状态
                if (condition.getPayStatus() != null) {
                    list.add(cb.equal(root.get("payStatus").as(Integer.class), condition.getPayStatus()));
                }
                //根据CRM客户代码
                if (StringUtil.isNotBlank(condition.getCrmCode())) {
                    list.add(cb.equal(root.get("crmCode").as(String.class), condition.getCrmCode()));
                }
                list.add(cb.equal(root.get("deleteFlag").as(Integer.class), 0));
                //

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);


        return pageOrder;
    }


}
