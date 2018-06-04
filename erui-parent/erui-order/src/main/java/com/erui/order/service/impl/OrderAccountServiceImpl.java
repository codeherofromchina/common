package com.erui.order.service.impl;

import com.erui.comm.NewDateUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.order.dao.OrderAccountDao;
import com.erui.order.dao.OrderAccountDeliverDao;
import com.erui.order.dao.OrderDao;
import com.erui.order.dao.OrderLogDao;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderAccount;
import com.erui.order.entity.OrderAccountDeliver;
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
import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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


    @Autowired
    OrderAccountDeliverDao orderAccountDeliverDao;



  /*  @Value("#{orderProp[CRM_URL]}")
    private String crmUrl;  //CRM接口地址

*/
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
    public void delGatheringRecord(ServletRequest request, Integer id) {


        /**
         ** 逻辑删除收款记录
         */

        OrderAccount orderAccounts = orderAccountDao.findOne(id);   //查询收款记录
        orderAccounts.setDelYn(0);
        orderAccountDao.save(orderAccounts);        //收款记录  逻辑删除


        Order order = orderAccounts.getOrder();

        /**
         * 判断是否是收款中状态
         */
        Integer id1 = order.getId();//拿到订单id
        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id1, 1);
        //无收款记录  改变收款状态为  1:未付款      （ 1:未付款 2:部分付款 3:收款完成'）
        if (byOrderId.size() == 0) {
            Order one = orderDao.findOne(id1);
            one.setPayStatus(1);
            orderDao.saveAndFlush(one);
        }

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
    public void addGatheringRecord(OrderAccount orderAccount, ServletRequest request) throws Exception {
      /*orderAccount.setPaymentDate(new Date());*/   //测试放开
        orderAccount.setCreateTime(new Date());
        OrderAccount orderAccount1;
        try {
            orderAccount1 = orderAccountDao.save(orderAccount);
        } catch (Exception e) {
            throw new Exception(String.format("%s%s%s","订单收款记录添加失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Order receipt record add failure"));
        }

        Order order = orderDao.findOne(orderAccount.getOrder().getId());
        if (order == null) {
            throw new Exception(String.format("%s%s%s","无订单id：" + orderAccount.getOrder().getId() + "  关联关系", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Non order id:" + orderAccount.getOrder().getId() + " Association"));
        }
        order.setPayStatus(2);
        orderDao.saveAndFlush(order);

        /**
         *  推送 Log日志 收到预付款
         */
        OrderLog orderLog = new OrderLog();
        try {
            orderLog.setOrder(orderDao.findOne(order.getId()));
            orderLog.setLogType(OrderLog.LogTypeEnum.ADVANCE.getCode());
            NumberFormat numberFormat1 =  new   DecimalFormat("###,##0.00");
            orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getMsg()) + "  " + numberFormat1.format(orderAccount.getMoney()) + " " + order.getCurrencyBn());
            orderLog.setEnoperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getEnMsg()) + "  " + numberFormat1.format(orderAccount.getMoney()) + " " + order.getCurrencyBn());
            orderLog.setCreateTime(new Date());
            orderLog.setBusinessDate(orderAccount.getPaymentDate()); //获取回款时间
            orderLog.setOrdersGoodsId(null);
            orderLog.setOrderAccountId(orderAccount1.getId());
            orderLogDao.save(orderLog);
        } catch (Exception ex) {

            logger.error("日志记录失败 {}", orderLog.toString());
            logger.error("错误", ex);
            ex.printStackTrace();
            throw new Exception(String.format("%s%s%s","订单收款记录日志添加失败", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Order receipts log log add failure"));
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
    public void updateGatheringRecord(ServletRequest request, OrderAcciuntAdd orderAccount) {
        OrderAccount orderAccounts = orderAccountDao.findOne(orderAccount.getId()); //查询收款


        /**
         * 修改 log日志 订单执行跟踪  订单执行跟踪 回款时间
         */
        OrderLog orderLog = orderLogDao.findByOrderAccountId(orderAccount.getId()); //查询日志
        String currencyBn = orderAccounts.getOrder().getCurrencyBn();   //金额类型

        NumberFormat numberFormat1 =  new   DecimalFormat("###,##0.00");
        if (orderAccount.getMoney() != null) {
            //获取回款时间
            if (orderAccount.getPaymentDate() != null) {
                orderLog.setBusinessDate(orderAccount.getPaymentDate());
            }
            orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getMsg()) + "  " + numberFormat1.format(orderAccount.getMoney()) + " " + currencyBn);
            orderLog.setEnoperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getEnMsg()) + "  " + numberFormat1.format(orderAccount.getMoney()) + " " + currencyBn);
            orderLogDao.save(orderLog);
        }

        orderAccounts.setDesc(orderAccount.getDesc());
        orderAccounts.setMoney(orderAccount.getMoney());
        orderAccounts.setDiscount(orderAccount.getDiscount());
        orderAccounts.setPaymentDate(orderAccount.getPaymentDate());
        orderAccounts.setGoodsPrice(orderAccount.getGoodsPrice());
        orderAccounts.setDeliverDate(orderAccount.getDeliverDate());




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
        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id, 1);
        if (byOrderId.size() == 0) {
            throw new Exception(String.format("%s%s%s","无收款记录", Constant.ZH_EN_EXCEPTION_SPLIT_SYMBOL,"Non receivable record"));
        }
        Order order = orderDao.findOne(id);
        order.setPayStatus(3);
        orderDao.saveAndFlush(order);

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

        List<OrderAccount> byOrderId = orderAccountDao.findByOrderIdAndDelYn(id, 1);
        List<OrderAccountDeliver> byOrderIdAndDelYn = orderAccountDeliverDao.findByOrderIdAndDelYn(id, 1);

        //发运信息
        BigDecimal shipmentsMoneySum = BigDecimal.valueOf(0);  //已发货总金额
        for (OrderAccountDeliver orderAccountDeliver : byOrderIdAndDelYn){
            if(orderAccountDeliver.getGoodsPrice() != null){
                shipmentsMoneySum= shipmentsMoneySum.add(orderAccountDeliver.getGoodsPrice());   // 发货金额
            }
        }
        order.setShipmentsMoney(shipmentsMoneySum); //已发货总金额        已发货总金额=发货金额的总和


        //收款信息
        BigDecimal sumMoneySum = BigDecimal.valueOf(0);     //回款金额
        BigDecimal sumDiscountSum = BigDecimal.valueOf(0);      //其他扣款金额
        for(OrderAccount orderAccount : byOrderId){
            if (orderAccount.getMoney() != null) {
                sumMoneySum = sumMoneySum.add(orderAccount.getMoney());       //回款金额
            }
            if (orderAccount.getDiscount() != null) {
                sumDiscountSum = sumDiscountSum.add(orderAccount.getDiscount());      //其他扣款金额
            }
        }

        if(sumDiscountSum != null ){
            order.setAlreadyGatheringMoney(sumMoneySum.add(sumDiscountSum));     //已收款总金额       已收款总额=回款金额总额+其他扣款金额总和
        }else{
            order.setAlreadyGatheringMoney(sumMoneySum);     //已收款总金额
        }

        //应收账款余额    应收账款余额=已发货总金额-已收款总金额
        order.setReceivableAccountRemaining(order.getShipmentsMoney().subtract(order.getAlreadyGatheringMoney()));


        // 已收款总金额（USD）   已收款总金额=已收款总金额*汇率          如果收款方式为美元（USD）的话，不用计算汇率
        BigDecimal exchangeRate = order.getExchangeRate();//汇率
        String currencyBn = order.getCurrencyBn();//订单结算币种
        if(currencyBn != "USD"){
            order.setAlreadyGatheringMoneyUSD(order.getAlreadyGatheringMoney().multiply(exchangeRate));
        }else {
            order.setAlreadyGatheringMoneyUSD(order.getAlreadyGatheringMoney());
        }


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
        PageRequest request = new PageRequest(condition.getPage() - 1, condition.getRows(), Sort.Direction.DESC, "createTime");
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
                list.add(cb.greaterThan(root.get("status").as(Integer.class), 1));  //订单保存时，不查询保存的
                list.add(cb.equal(root.get("deleteFlag").as(Integer.class), 0));
                //

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);

        return pageOrder;
    }


    /**
     * 发货信息查询   (根据订单)
     *
     * @param id
     * @return
     */
    @Override
    public List<OrderAccountDeliver> queryOrderAccountDeliver(Integer id) {
        return orderAccountDeliverDao.findByOrderIdAndDelYn(id, 1);
    }


    /**
     * 发货信息查询id  逻辑删除
     * @param request
     * @param id
     */
    @Override
    public void delOrderAccountDeliver(ServletRequest request, Integer id) {

        /**
         ** 逻辑删除收款记录
         */
        OrderAccountDeliver orderAccountDeliver = orderAccountDeliverDao.findOne(id);   //查询收款记录
        orderAccountDeliver.setDelYn(0);
        orderAccountDeliverDao.save(orderAccountDeliver);        //收款记录  逻辑删除

    }

    /**
     *  添加一条发货信息
     * @param orderAccountDeliver  收款信息
     * @return
     */
    @Override
    public void addOrderAccountDeliver(OrderAccountDeliver orderAccountDeliver, ServletRequest request) throws Exception {
        try {
            orderAccountDeliverDao.save(orderAccountDeliver);
        } catch (Exception e) {
            throw new Exception(String.format("发货信息添加失败"));
        }
    }


    /**
     *
     * 编辑发货信息
     *
     * @param request
     * @param orderAccount
     */
    @Override
    public void updateOrderAccountDeliver(ServletRequest request, OrderAcciuntAdd orderAccount) {
        OrderAccountDeliver one = orderAccountDeliverDao.findOne(orderAccount.getId());//查询发货信息

        one.setDesc(orderAccount.getDesc());
        one.setGoodsPrice(orderAccount.getGoodsPrice());
        one.setDeliverDate(orderAccount.getDeliverDate());

        orderAccountDeliverDao.saveAndFlush(one);
    }

}
