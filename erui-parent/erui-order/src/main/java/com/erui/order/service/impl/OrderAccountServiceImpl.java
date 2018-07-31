package com.erui.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.erui.comm.NewDateUtil;
import com.erui.comm.ThreadLocalUtil;
import com.erui.comm.util.CookiesUtil;
import com.erui.comm.util.constant.Constant;
import com.erui.comm.util.data.string.StringUtil;
import com.erui.comm.util.http.HttpRequest;
import com.erui.order.dao.*;
import com.erui.order.entity.Order;
import com.erui.order.entity.*;
import com.erui.order.requestVo.OrderAcciuntAdd;
import com.erui.order.requestVo.OrderListCondition;
import com.erui.order.service.DeliverConsignService;
import com.erui.order.service.OrderAccountService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.math.BigInteger;
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

    @Autowired
    private StatisticsDao statisticsDao;

    @Autowired
    private DeliverConsignService deliverConsignService;

    @Value("#{orderProp[CREDIT_EXTENSION]}")
    private String creditExtension;  //授信服务器地址


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
    public void delGatheringRecord(ServletRequest request, Integer id) throws Exception {


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


        /**
         *更新订单中已收款总金额
         */
        orderAccountsDispose(id1);


        /**
         * 修改授信额度  删除新增的授信额度
         * 接口会处理金额，以及记录
         */
        try {
            Integer creditLogId = orderAccounts.getCreditLogId();
            if(creditLogId != null){
                delCreditPayment(creditLogId);
            }
        }catch (Exception e){
            throw new Exception(e);
        }


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
        Integer id = orderAccount.getOrder().getId();   //订单id
        Order order = orderDao.findOne(id);
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
            BigDecimal discount = orderAccount.getDiscount() == null ? BigDecimal.valueOf(0) : orderAccount.getDiscount();   //其他扣款金额
            BigDecimal add = orderAccount.getMoney().add(discount);
            orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getMsg()) + "  " + numberFormat1.format(add) + " " + order.getCurrencyBn());
            orderLog.setEnoperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getEnMsg()) + "  " + numberFormat1.format(add) + " " + order.getCurrencyBn());
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

        /**
         *更新订单中预收金额
         */
        Order order1 = null;
        try {
            order1 = orderAccountsDispose(id);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

        /**
         * 修改授信额度
         */
        try {
            JSONObject jsonObject = disposeLineOfCredit(orderAccount1, order1);
            if(jsonObject != null) {
                Integer log_id = jsonObject.getInteger("log_id");
                if(log_id > 0 ){
                    orderAccount1.setCreditLogId(log_id);   //添加授信记录id
                    orderAccountDao.save(orderAccount1);
                }
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }


    /**
     * 编辑收款记录
     *
     * @param orderAccount
     * @return
     */
    @Override
    @Transactional
    public void updateGatheringRecord(ServletRequest request, OrderAcciuntAdd orderAccount) throws Exception {
        OrderAccount orderAccounts = orderAccountDao.findOne(orderAccount.getId()); //查询收款

        BigDecimal money = orderAccounts.getMoney() == null ? BigDecimal.valueOf(0) : orderAccounts.getMoney();    //旧回款金额
        BigDecimal discount1 = orderAccounts.getDiscount() == null ? BigDecimal.valueOf(0) : orderAccounts.getDiscount(); //旧其他扣款金额
        BigDecimal formerSumMoney = money.add(discount1);   //旧本笔收款总金额

        /**
         * 修改 log日志 订单执行跟踪  订单执行跟踪 回款时间
         */
        OrderLog orderLog = orderLogDao.findByOrderAccountId(orderAccount.getId()); //查询日志


        Order order = orderAccounts.getOrder(); //订单
        String currencyBn = order.getCurrencyBn();   //金额类型

        NumberFormat numberFormat1 =  new   DecimalFormat("###,##0.00");
        if (orderAccount.getMoney() != null) {
            //获取回款时间
            if (orderAccount.getPaymentDate() != null) {
                orderLog.setBusinessDate(orderAccount.getPaymentDate());
            }BigDecimal discount = orderAccount.getDiscount() == null ? BigDecimal.valueOf(0) : orderAccount.getDiscount();   //其他扣款金额
            BigDecimal add = orderAccount.getMoney().add(discount);
            orderLog.setOperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getMsg()) + "  " + numberFormat1.format(add) + " " + currencyBn);
            orderLog.setEnoperation(StringUtils.defaultIfBlank(null, OrderLog.LogTypeEnum.ADVANCE.getEnMsg()) + "  " + numberFormat1.format(add) + " " + currencyBn);
            orderLogDao.save(orderLog);
        }

        orderAccounts.setDesc(orderAccount.getDesc());
        orderAccounts.setMoney(orderAccount.getMoney());
        orderAccounts.setDiscount(orderAccount.getDiscount());
        orderAccounts.setPaymentDate(orderAccount.getPaymentDate());
        orderAccounts.setGoodsPrice(orderAccount.getGoodsPrice());
        orderAccounts.setDeliverDate(orderAccount.getDeliverDate());


        orderAccounts.setUpdateTime(new Date());
        OrderAccount orderAccount1 = orderAccountDao.saveAndFlush(orderAccounts);


        /**
         *更新订单中已收款总金额
         */
        Order order1 = orderAccountsDispose(order.getId());


        /**
         *  根据修改的收款信息      修改授信额度
         */
        try {

            Integer creditLogId = orderAccount1.getCreditLogId();       //授信记录id

            if(creditLogId != null){
                BigDecimal newMoney = orderAccount1.getMoney() == null ? BigDecimal.valueOf(0) : orderAccount1.getMoney();    //旧回款金额
                BigDecimal newDiscount1 = orderAccount1.getDiscount() == null ? BigDecimal.valueOf(0) : orderAccount1.getDiscount(); //旧其他扣款金额
                BigDecimal newSumMoney = newMoney.add(newDiscount1);   //旧本笔收款总金额

                if(newSumMoney.compareTo(formerSumMoney) != 0){

                    try {
                        disposeCreditPayment(order1,newSumMoney,formerSumMoney,creditLogId);
                    }catch (Exception e){
                        throw new Exception(e);
                    }

                }
            }


        }catch (Exception e){
            throw new Exception(e);
        }

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

        /*Order orderMoney = moneyDispose(byOrderIdAndDelYn, byOrderId);  //金额处理
        BigDecimal shipmentsMoney = order.getShipmentsMoney();//已发货总金额
        BigDecimal alreadyGatheringMoney = order.getAlreadyGatheringMoney(); //已收款总金额
        order.setReceivableAccountRemaining(shipmentsMoney.subtract(alreadyGatheringMoney));//应收账款余额*/


        // 已收款总金额（USD）   已收款总金额=已收款总金额*汇率          如果收款方式为美元（USD）的话，不用计算汇率
        BigDecimal exchangeRate = order.getExchangeRate();//汇率
        String currencyBn = order.getCurrencyBn();//订单结算币种
        BigDecimal alreadyGatheringMoney = order.getAlreadyGatheringMoney()== null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoney();  //已收款总金额
        if(currencyBn != "USD"){
            order.setAlreadyGatheringMoneyUSD(alreadyGatheringMoney.multiply(exchangeRate));
        }else {
            order.setAlreadyGatheringMoneyUSD(alreadyGatheringMoney);
        }
        order.setAlreadyGatheringMoneyUSD(order.getAlreadyGatheringMoneyUSD() == null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoneyUSD());

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

                //根据所属地区
                if(StringUtil.isNotBlank(condition.getRegion())){
                    list.add(cb.equal(root.get("region").as(String.class), condition.getRegion()));
                }
                //根据国家
                /*if(StringUtil.isNotBlank(condition.getCountry())){
                    list.add(cb.equal(root.get("country").as(String.class), condition.getCountry()));
                }*/

                //根据应收账款余额
                if(condition.getReceivableAccountRemaining() != null){
                    Integer receivableAccountRemaining = condition.getReceivableAccountRemaining();

                    if(receivableAccountRemaining == 1){
                        list.add(cb.lessThan(root.get("receivableAccountRemaining").as(BigDecimal.class), BigDecimal.valueOf(0)));
                    }else if(receivableAccountRemaining == 2){
                        list.add(cb.equal(root.get("receivableAccountRemaining").as(BigDecimal.class), BigDecimal.valueOf(0)));
                    }else{
                        list.add(cb.greaterThan(root.get("receivableAccountRemaining").as(BigDecimal.class), BigDecimal.valueOf(0)));
                    }

                   /* Set<Order> receivableAccountRemainingSet = findReceivableAccountRemaining(condition.getReceivableAccountRemaining());
                    CriteriaBuilder.In<Object> idIn = cb.in(root.get("id"));
                    if (receivableAccountRemainingSet != null && receivableAccountRemainingSet.size() > 0) {
                        for (Order o : receivableAccountRemainingSet) {
                            idIn.value(o.getId());
                        }
                    } else {
                        // 查找失败
                        idIn.value(-1);
                    }
                    list.add(idIn);*/
                }

                //根据订单
               /* list.add(cb.greaterThan(root.get("status").as(Integer.class), 1));  //订单保存时，不查询保存的*/
                list.add(cb.equal(root.get("deleteFlag").as(Integer.class), 0));

                //根据项目
                Join<Order, Project> projectRoot = root.join("project");
                String[] projectStatus = {"EXECUTING","DONE","DELAYED_EXECUTION","DELAYED_COMPLETE","UNSHIPPED","DELAYED_UNSHIPPED","PAUSE"};
                list.add(projectRoot.get("projectStatus").in(projectStatus));

                Predicate[] predicates = new Predicate[list.size()];
                predicates = list.toArray(predicates);
                return cb.and(predicates);
            }
        }, request);
        if(pageOrder.hasContent()){

            // 查询 地区中英文对应列表
            Map<String, String> bnMapZhRegion = this.findBnMapZhRegion();
            // 查询 国家中英文对应列表
            Map<String, String> bnMapZhCountry = this.findBnMapZhCountry();

            for (Order vo : pageOrder.getContent()){
                NumberFormat numberFormat1 =  new   DecimalFormat("###,##0.00");
                BigDecimal shipmentsMoney = vo.getShipmentsMoney() == null ? BigDecimal.valueOf(0) :vo.getShipmentsMoney() ; //已发货总金额
                vo.setCurrencyBnShipmentsMoney(vo.getCurrencyBn()+" "+numberFormat1.format(shipmentsMoney));//已发货总金额
                BigDecimal alreadyGatheringMoney = vo.getAlreadyGatheringMoney()== null ? BigDecimal.valueOf(0) : vo.getAlreadyGatheringMoney();   //已收款总金额
                vo.setCurrencyBnAlreadyGatheringMoney(vo.getCurrencyBn()+" "+numberFormat1.format(alreadyGatheringMoney));  //已收款总金额
                BigDecimal receivableAccountRemaining = vo.getReceivableAccountRemaining()== null ? BigDecimal.valueOf(0) : vo.getReceivableAccountRemaining();  //应收账款余额
                vo.setCurrencyBnReceivableAccountRemaining(vo.getCurrencyBn()+" "+numberFormat1.format(receivableAccountRemaining));//应收账款余额
                vo.setRegion(bnMapZhRegion.get(vo.getRegion())); //所属地区
                vo.setCountry(bnMapZhCountry.get(vo.getCountry()));   // 国家
            }
        }

        return pageOrder;
    }


    /**
     * 发货信息查询   (根据订单)
     *
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    public List<OrderAccountDeliver> queryOrderAccountDeliver(Integer id) {
        return orderAccountDeliverDao.findByOrderIdAndDelYn(id, 1);
    }


    /**
     * 发货信息查询id  逻辑删除
     * @param request
     * @param id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delOrderAccountDeliver(ServletRequest request, Integer id) throws Exception {

        /**
         ** 逻辑删除收款记录
         */
        OrderAccountDeliver orderAccountDeliver = orderAccountDeliverDao.findOne(id);   //查询收款记录
        orderAccountDeliver.setDelYn(0);
        OrderAccountDeliver save = orderAccountDeliverDao.save(orderAccountDeliver);//收款记录  逻辑删除


        Order order;
        /**
         * 更新订单中已发货总金额
         */
        try {
            order  = orderAccountDeliverDispose(save.getOrder().getId());
        } catch (Exception e) {
            throw new Exception(String.format("发货信息删除失败"));
        }


        try {
            if(order != null){
                disposeAdvanceMoney(order);  //处理预收金额
            }
        } catch (Exception e) {
            throw new Exception(String.format("预收金额更新失败"));
        }

    }

    /**
     *  添加一条发货信息
     * @param orderAccountDeliver  收款信息
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrderAccountDeliver(OrderAccountDeliver orderAccountDeliver, ServletRequest request) throws Exception {

        Order order;
        try {
            OrderAccountDeliver orderAccountDeliverAdd = orderAccountDeliverDao.save(orderAccountDeliver);
            /**
             * 更新订单中已发货总金额
             */
            order = orderAccountDeliverDispose(orderAccountDeliverAdd.getOrder().getId());
        } catch (Exception e) {
            throw new Exception(String.format("发货信息添加失败"));
        }

        try {
            if(order != null){
             disposeAdvanceMoney(order);  //处理预收金额
            }
        } catch (Exception e) {
            throw new Exception(String.format("预收金额更新失败"));
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
    @Transactional(rollbackFor = Exception.class)
    public void updateOrderAccountDeliver(ServletRequest request, OrderAcciuntAdd orderAccount) throws Exception {
        OrderAccountDeliver one = orderAccountDeliverDao.findOne(orderAccount.getId());//查询发货信息

        one.setDesc(orderAccount.getDesc());
        one.setGoodsPrice(orderAccount.getGoodsPrice());
        one.setDeliverDate(orderAccount.getDeliverDate());

        orderAccountDeliverDao.saveAndFlush(one);

        Order order;
        /**
         * 更新订单中已发货总金额
         */
        try {
            order = orderAccountDeliverDispose(one.getOrder().getId());
        } catch (Exception e) {
            throw new Exception(String.format("发货信息添加失败"));
        }


        try {
            if(order != null){
                disposeAdvanceMoney(order);  //处理预收金额
            }
        } catch (Exception e) {
            throw new Exception(String.format("预收金额更新失败"));
        }

    }

    /**
     * 财务金额处理
     *
     */
   /* public Order moneyDispose(List<OrderAccountDeliver> orderAccountDelivers ,List<OrderAccount> orderAccounts){

        Order order = new Order();

        //已发货总金额
        if(orderAccountDelivers.size() != 0){
            //发运信息
            BigDecimal shipmentsMoneySum = BigDecimal.valueOf(0);  //已发货总金额
            for (OrderAccountDeliver orderAccountDeliver : orderAccountDelivers){
                if(orderAccountDeliver.getGoodsPrice() != null && orderAccountDeliver.getDelYn() == 1){
                    shipmentsMoneySum= shipmentsMoneySum.add(orderAccountDeliver.getGoodsPrice());   // 发货金额
                }
            }
            order.setShipmentsMoney(shipmentsMoneySum); //已发货总金额        已发货总金额=发货金额的总和

        }

        //已收款总金额
        if(orderAccounts.size() != 0){
            //收款信息
            BigDecimal sumMoneySum = BigDecimal.valueOf(0);     //回款金额
            BigDecimal sumDiscountSum = BigDecimal.valueOf(0);      //其他扣款金额
            for(OrderAccount orderAccount : orderAccounts){

                Integer delYn = orderAccount.getDelYn();    //删除标识   0：删除   1：存在
                if (orderAccount.getMoney() != null && delYn == 1) {
                    sumMoneySum = sumMoneySum.add(orderAccount.getMoney());       //回款金额
                }
                if (orderAccount.getDiscount() != null && delYn == 1) {
                    sumDiscountSum = sumDiscountSum.add(orderAccount.getDiscount());      //其他扣款金额
                }
            }

            if(sumDiscountSum != null ){
                order.setAlreadyGatheringMoney(sumMoneySum.add(sumDiscountSum));     //已收款总金额       已收款总额=回款金额总额+其他扣款金额总和
            }else{
                order.setAlreadyGatheringMoney(sumMoneySum);     //已收款总金额
            }
        }

        //应收账款余额    应收账款余额=已发货总金额-已收款总金额
        order.setShipmentsMoney(order.getShipmentsMoney() == null ? BigDecimal.valueOf(0) : order.getShipmentsMoney());  //已发货总金额
        order.setAlreadyGatheringMoney(order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoney());//已收款总金额
        order.setReceivableAccountRemaining(order.getShipmentsMoney().subtract(order.getAlreadyGatheringMoney()));

        return  order;

    }*/

    /**
     * 根据应收账款余额
     * @param receivableAccountRemaining    1  <0  ,  2 =0  ,  3 >0
     * @return
     */
    @Transactional(readOnly = true)
    public Set<Order> findReceivableAccountRemaining(Integer receivableAccountRemaining){
        Set<Order> result = new HashSet<>();
        List<Order> list = orderDao.findAll();

        if(list.size() != 0){
            for (Order vo : list){


                //应收账款余额    应收账款余额=已发货总金额-已收款总金额
                BigDecimal shipmentsMoney = vo.getShipmentsMoney() == null ? BigDecimal.valueOf(0) : vo.getShipmentsMoney();//已发货总金额
                BigDecimal alreadyGatheringMoney = vo.getAlreadyGatheringMoney() ==  null ? BigDecimal.valueOf(0) : vo.getAlreadyGatheringMoney();//已收款总金额
                BigDecimal receivableAccountRemainingNew = shipmentsMoney.subtract(alreadyGatheringMoney);//应收账款余额

                // -1 小于0     0 等于0   1 大于0
                int i=receivableAccountRemainingNew.compareTo(BigDecimal.ZERO);

                if(receivableAccountRemaining == 1 && i == -1){
                    result.add(vo);
                }else if(receivableAccountRemaining == 2 && i == 0 ){
                    result.add(vo);
                }else if (receivableAccountRemaining == 3 && i == 1){
                    result.add(vo);
                }
            }
        }

        return result;

    }


    //查询地区的中英文对应列表
    public Map<String, String> findBnMapZhRegion() {
        Map<String, String> result = new HashMap<>();
        List<Object> regions = statisticsDao.findBnMapZhRegion();
        if (regions != null && regions.size() > 0) {
            for (Object vo : regions) {
                Object[] strArr = (Object[]) vo;
                String s0 = (String) strArr[0];
                String s1 = (String) strArr[1];
                if (StringUtils.isNotBlank(s1)) {
                    result.put(s0, s1);
                }
            }
        }
        return result;
    }

    // 查询国家中英文对应列表
    public Map<String, String> findBnMapZhCountry() {
        Map<String, String> result = new HashMap<>();
        List<Object> countrys = statisticsDao.findBnMapZhCountry();
        if (countrys != null && countrys.size() > 0) {
            for (Object vo : countrys) {
                Object[] strArr = (Object[]) vo;
                String s0 = (String) strArr[0];
                String s1 = (String) strArr[1];
                if (StringUtils.isNotBlank(s1)) {
                    result.put(s0, s1);
                }
            }
        }
        return result;
    }

    //更新订单中已发货总金额
    @Transactional(rollbackFor = Exception.class)
    public Order orderAccountDeliverDispose(Integer orderId){

        //获取发货信息
        List<OrderAccountDeliver> byOrderIdAndDelYn = orderAccountDeliverDao.findByOrderIdAndDelYn(orderId, 1);

        BigDecimal shipmentsMoneySum = BigDecimal.valueOf(0);  //已发货总金额
        if(byOrderIdAndDelYn.size() != 0){
            //发运信息
            for (OrderAccountDeliver orderAccountDeliver : byOrderIdAndDelYn){
                if(orderAccountDeliver.getGoodsPrice() != null && orderAccountDeliver.getDelYn() == 1){
                    shipmentsMoneySum= shipmentsMoneySum.add(orderAccountDeliver.getGoodsPrice());   // 发货金额
                }
            }
        }
        Order one = orderDao.findOne(orderId);
        one.setShipmentsMoney(shipmentsMoneySum); //已发货总金额        已发货总金额=发货金额的总和
        BigDecimal alreadyGatheringMoney = one.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0) : one.getAlreadyGatheringMoney();  //已收款总金额
        one.setReceivableAccountRemaining(shipmentsMoneySum.subtract(alreadyGatheringMoney));// 应收账款余额
        Order save = orderDao.save(one);
        if(save != null){
            return save;
        }

        return  null;
    }



    //更新订单中已收款总金额
    @Transactional(rollbackFor = Exception.class)
    public Order orderAccountsDispose(Integer orderId){

        //获取发货信息
        List<OrderAccount> byOrderIdAndDelYn = orderAccountDao.findByOrderIdAndDelYn(orderId, 1);

        BigDecimal sumMoneySum = BigDecimal.valueOf(0);     //回款金额
        BigDecimal sumDiscountSum = BigDecimal.valueOf(0);      //其他扣款金额

        if(byOrderIdAndDelYn.size() != 0){
            //收款信息
            for(OrderAccount orderAccount : byOrderIdAndDelYn){

                Integer delYn = orderAccount.getDelYn();    //删除标识   0：删除   1：存在
                if (orderAccount.getMoney() != null && delYn == 1) {
                    sumMoneySum = sumMoneySum.add(orderAccount.getMoney());       //回款金额
                }
                if (orderAccount.getDiscount() != null && delYn == 1) {
                    sumDiscountSum = sumDiscountSum.add(orderAccount.getDiscount());      //其他扣款金额
                }
            }

        }


        Order one = orderDao.findOne(orderId);
        if(sumDiscountSum != null ){
            one.setAlreadyGatheringMoney(sumMoneySum.add(sumDiscountSum));     //已收款总金额       已收款总额=回款金额总额+其他扣款金额总和
        }else{
            one.setAlreadyGatheringMoney(sumMoneySum);     //已收款总金额
        }

        BigDecimal shipmentsMoney = one.getShipmentsMoney()== null ? BigDecimal.valueOf(0) :one.getShipmentsMoney();//已发货总金额

        one.setReceivableAccountRemaining(shipmentsMoney.subtract(one.getAlreadyGatheringMoney()));// 应收账款余额

        Order save = orderDao.save(one);

        return save;
    }


    public  JSONObject disposeLineOfCredit(OrderAccount orderAccount, Order order) throws Exception {
        BigDecimal money = orderAccount.getMoney() == null ? BigDecimal.valueOf(0) : orderAccount.getMoney();//回款金额
        BigDecimal discount = orderAccount.getDiscount()  == null ? BigDecimal.valueOf(0) : orderAccount.getDiscount();//其他扣款金额
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();//订单中利率
        BigDecimal moneySum = money.add(discount);   //  本次回款总金额


        //  查看可用授信额度
        DeliverConsign deliverConsign1 = deliverConsignService.queryCreditData(order);
        if(deliverConsign1 != null && deliverConsign1.getLineOfCredit() != null && deliverConsign1.getLineOfCredit().compareTo(BigDecimal.valueOf(0)) == 1){

            BigDecimal flag = null;

            BigDecimal creditAvailable = deliverConsign1.getCreditAvailable().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);// 可用授信额度
            BigDecimal lineOfCredit = deliverConsign1.getLineOfCredit().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);    //授信额度
            BigDecimal subtract = lineOfCredit.subtract(creditAvailable);   //所欠授信额度

            if (subtract.compareTo(BigDecimal.valueOf(0)) == 1 ){    //判断是否有欠款   所欠大于0

                //判断本次回款总金额 是否 能够还所欠授信额度
                BigDecimal subtract2 = subtract.subtract(moneySum);    //所欠授信额度   -   本次回款总金额
                if(subtract2.compareTo(BigDecimal.valueOf(0)) == 1 || subtract2.compareTo(BigDecimal.valueOf(0)) == 0){ //大于  或者  等于
                    try {
                        //如果金额正好的话     调用授信接口，修改授信额度
                        JSONObject jsonObject = deliverConsignService.buyerCreditPaymentByOrder(order , 2 ,moneySum.multiply(exchangeRate));
                        JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                        if(data == null){  //查询数据正确返回 1
                            throw new Exception("同步授信额度失败");
                        }else {
                            BigDecimal subtract1 = moneySum.subtract(subtract); //本次回款总金额   -   所欠授信额度  //判断还差多少需要还的
                            flag = subtract1;
                            return data;
                        }
                    }catch (Exception e){
                        throw new Exception(e.getMessage());
                    }

                }else {
                    try {

                        BigDecimal subtract1 = moneySum.subtract(subtract); //本次回款总金额   -   所欠授信额度  //判断多出了多少
                        flag = subtract1;  //获取正值
                        // 如果还款金额  大于  所欠授信额度的话  直接还给所欠钱数
                        JSONObject jsonObject = deliverConsignService.buyerCreditPaymentByOrder(order , 2 ,subtract.multiply(exchangeRate));
                        JSONObject data = jsonObject.getJSONObject("data");//获取查询数据
                        if(data == null){  //查询数据正确返回 1
                            throw new Exception("同步授信额度失败");
                        }else {
                            return data;
                        }
                    }catch (Exception e){
                        throw new Exception(e.getMessage());
                    }
                }
            }

            BigDecimal currencyBnShipmentsMoney =  order.getShipmentsMoney() == null ? BigDecimal.valueOf(0.00) : order.getShipmentsMoney();  //已发货总金额 （财务管理
            BigDecimal currencyBnAlreadyGatheringMoney = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0.00) : order.getAlreadyGatheringMoney();//已收款总金额

            //收款总金额  -  发货总金额
            BigDecimal subtract1 = currencyBnAlreadyGatheringMoney.subtract(currencyBnShipmentsMoney);
            if (subtract1.compareTo(BigDecimal.valueOf(0)) == 1 ){ // 如果大于发货金额， 说明有多出的钱
                BigDecimal advanceMoney = order.getAdvanceMoney() == null ? BigDecimal.valueOf(0) : order.getAdvanceMoney();
                BigDecimal add = advanceMoney.add(flag);    //查看是负数还是正数
                if(add.compareTo(BigDecimal.valueOf(0)) == 1){
                    order.setAdvanceMoney(add);
                }else {
                    order.setAdvanceMoney(BigDecimal.valueOf(0));
                }
            }

            orderDao.save(order);

        }
        return null;
    }


    /**
     * 修改授信额度  删除新增的授信额度
     * @param creditLogId
     */
    public void delCreditPayment(Integer creditLogId) throws Exception {

        //拿取局部返回信息
        String returnMassage;
        try {
            //拼接查询授信路径
            String url = creditExtension + "V2/Buyercredit/deleteBuyerCreditOrderLogByOrderInfo";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"log_id\":\""+creditLogId+"\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            logger.info("人员详情返回信息：" + returnMassage);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if(code != 1){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }

        }catch (Exception ex){
            throw new Exception(String.format("查询授信信息失败"));
        }


    }




    /**
     * 根据修改的收款信息      计算额度
     * @param order
     *  @param newSumMoney     //修改后的收款总金额
     * @param formerSumMoney    //修改前的收款总金额
     * @param reditLogId //
     */
    public void disposeCreditPayment(Order order, BigDecimal newSumMoney, BigDecimal formerSumMoney, Integer reditLogId) throws Exception {

        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();//订单中利率
        String crmCode = order.getCrmCode();//crm客户码

        //获取授信信息
        DeliverConsign deliverConsign = deliverConsignService.queryCreditData(order);
        if(deliverConsign.getLineOfCredit().compareTo(BigDecimal.valueOf(0)) == 1){

            BigDecimal lineOfCredit = deliverConsign.getLineOfCredit().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);//授信额度
            if(lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1){        //必须有授信额度
                BigDecimal creditAvailable = deliverConsign.getCreditAvailable().divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);    // 可用授信额度
                BigDecimal subtract = lineOfCredit.subtract(creditAvailable);   //应还授信额度

                if(subtract.compareTo(BigDecimal.valueOf(0)) == 1){ //有应还授信额度再处理，  如果没有可还的不用处理

                    /**
                     * 处理授信金额
                     */

                    BigDecimal advanceMoney = order.getAdvanceMoney();//预收金额

                    BigDecimal add = null; //需要同步到授信的可用授信额度

                    BigDecimal retMoney = null; //授信记录需要修改的额度

                    BigDecimal subtract1 = newSumMoney.subtract(formerSumMoney);//修改后   -   修改前   =  相差的收款金额

                    if(subtract1.compareTo(BigDecimal.valueOf(0)) == -1){ //修改前大，  从授信中减去
                        add = creditAvailable.add(subtract1);      // 负值     可用授信额度  +  修改后相差的收款金额
                        try {
                            updateCreditPayment(crmCode,add.multiply(exchangeRate));  // 修改授信额度
                            retMoney = newSumMoney;
                        }catch (Exception e){
                            throw new Exception(e);
                        }
                    }else if(subtract1.compareTo(BigDecimal.valueOf(0)) == 1){    //修改后大 ，  添加授信
                        BigDecimal add1 = advanceMoney.add(subtract1);  //正值  预收金额  +   相差的值

                        //判断修改后多出的值，是否能还完所欠的授信额度      应还授信额度 >   多出的收款金额
                        Integer i = subtract.compareTo(subtract1);
                        if(i == 1 || i == 0){ //如果大于或等于   直接新增
                            add = creditAvailable.add(subtract1);      // 可用授信额度  +  修改后相差的正值
                            order.setAdvanceMoney(BigDecimal.valueOf(0));
                            try {
                                updateCreditPayment(crmCode,add.multiply(exchangeRate));  // 修改授信额度
                                retMoney = newSumMoney;
                            }catch (Exception e){
                                throw new Exception(e);
                            }

                        }else { //如果小于，说明收款金额大，只还所欠的授信额度
                            BigDecimal subtract2 = subtract1.subtract(subtract);    //获取多出的值   修改后相差的正值 - 应还授信额度  =  多出的钱是属于预收的
                            order.setAdvanceMoney(subtract2);
                            add = lineOfCredit;

                            retMoney = newSumMoney.subtract(subtract2);//修改后金额  -  多出的钱是属于预收的   = 需要修改授信记录的额度
                            try {
                                updateCreditPayment(crmCode,lineOfCredit.multiply(exchangeRate));  // 修改授信额度  授信额度直接还清
                            }catch (Exception e){
                                throw new Exception(e);
                            }
                        }
                    }

                    /**
                     * 处理授信记录
                     */
                    try {
                        if(add.compareTo(BigDecimal.valueOf(0)) == 1){
                            updateCreditPaymentLog(reditLogId, "+"+retMoney.multiply(exchangeRate) , add.multiply(exchangeRate));
                        }
                    }catch (Exception e){
                        throw new Exception(e);
                    }

                }else { //如果授信中没有可还的   优先处理预收的
                    BigDecimal advanceMoney = order.getAdvanceMoney();//预收金额
                    BigDecimal add = null; //需要同步到授信的可用授信额度
                    BigDecimal subtract1 = newSumMoney.subtract(formerSumMoney);//修改后   -   修改前   =  相差的收款金额            可能是正负值
                    if(subtract1.compareTo(BigDecimal.valueOf(0)) == -1){ //修改前大，  从授信中减去
                        BigDecimal add1 = advanceMoney.add(subtract1);  // 预收金额  +   相差的负值
                        if(add1.compareTo(BigDecimal.valueOf(0)) == 1){ //如果是大于，直接修改预收
                            order.setAdvanceMoney(add1);
                        }else {                           //如果是负值  从授信中去减
                            try {
                                add = creditAvailable.add(add1);      //   可用授信额度  +  （预收金额  +   相差的负值   ） =负值的时候
                                updateCreditPayment(crmCode,add.multiply(exchangeRate));  // 修改授信额度

                                /**
                                 * 处理授信记录
                                 */
                                if(add.compareTo(BigDecimal.valueOf(0)) == 1){
                                    updateCreditPaymentLog(reditLogId, "+"+newSumMoney , add.multiply(exchangeRate));
                                }

                            }catch (Exception e){
                                throw new Exception(e);
                            }
                        }
                    }else if(subtract1.compareTo(BigDecimal.valueOf(0)) == 1){    //修改后大 ，  添加授信
                        BigDecimal add1 = advanceMoney.add(subtract1);  // 预收金额  +   相差的正值
                            order.setAdvanceMoney(add1);
                    }
                }
            }
            orderDao.save(order);
        }


    }

    /**
     * 根据修改的收款信息      修改授信额度
     * @param crmCode   //crm客户码
     * @param creditAvailable    //可用授信额度
     */
    public void updateCreditPayment(String crmCode, BigDecimal creditAvailable) throws Exception {
        //拿取局部返回信息
        String returnMassage;
        try {
            //拼接查询授信路径
            String url = creditExtension + "V2/Buyercredit/updateBuyerCreditByOrderInfo";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"crm_code\":\""+crmCode+"\",\"credit_available\":\""+creditAvailable+"\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            logger.info("人员详情返回信息：" + returnMassage);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if(code != 1){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }

        }catch (Exception ex){
            throw new Exception(String.format("查询授信信息失败"));
        }

    }

    /**
     * 根据修改的收款信息       处理授信记录
     * @param log_id   //日志ID
     * @param use_credit_granted    //使用额度,带正负号
     * @param credit_available    //可用额度
     */
    public void updateCreditPaymentLog(Integer log_id, String use_credit_granted, BigDecimal credit_available) throws Exception {
        //拿取局部返回信息
        String returnMassage;
        try {
            //拼接查询授信路径
            String url = creditExtension + "/V2/Buyercredit/updateBuyerCreditOrderLogByOrderInfo";
            //获取token
            String eruiToken = (String) ThreadLocalUtil.getObject();

            // 根据id获取人员信息
            String jsonParam = "{\"log_id\":\""+log_id+"\",\"use_credit_granted\":\""+use_credit_granted+"\",\"credit_available\":\""+credit_available+"\"}";
            Map<String, String> header = new HashMap<>();
            header.put(CookiesUtil.TOKEN_NAME, eruiToken);
            header.put("Content-Type", "application/json");
            header.put("accept", "*/*");
            returnMassage = HttpRequest.sendPost(url, jsonParam, header);
            logger.info("人员详情返回信息：" + returnMassage);

            JSONObject jsonObject = JSONObject.parseObject(returnMassage);
            Integer code = jsonObject.getInteger("code");   //获取查询状态
            if(code != 1){  //查询数据正确返回 1
                String message = jsonObject.getString("message");
                throw new Exception(message);
            }

        }catch (Exception ex){
            throw new Exception(String.format("同步授信记录失败"));
        }

    }

    /**
     * 处理预收金额
     * @param order
     */
    public  void  disposeAdvanceMoney(Order order) throws Exception {
        BigDecimal exchangeRate = order.getExchangeRate() == null ? BigDecimal.valueOf(1) : order.getExchangeRate();//订单中利率

        BigDecimal shipmentsMoneyUSD = order.getShipmentsMoney() == null ? BigDecimal.valueOf(0) : order.getShipmentsMoney();//已发货总金额
        BigDecimal shipmentsMoney = shipmentsMoneyUSD.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);

        BigDecimal alreadyGatheringMoneyUSD = order.getAlreadyGatheringMoney() == null ? BigDecimal.valueOf(0) : order.getAlreadyGatheringMoney();// 已收款总金额
        BigDecimal alreadyGatheringMoney = alreadyGatheringMoneyUSD.divide(exchangeRate, 2, BigDecimal.ROUND_HALF_DOWN);

        BigDecimal subtract = alreadyGatheringMoney.subtract(shipmentsMoney);   //多出的 收款 金额
        if(subtract.compareTo(BigDecimal.valueOf(0)) == 1){

            //查询授信信息
            DeliverConsign deliverConsign = deliverConsignService.queryCreditData(order);

            BigDecimal lineOfCredit = deliverConsign.getLineOfCredit();//授信额度
            if(lineOfCredit.compareTo(BigDecimal.valueOf(0)) == 1){ //必须有额度
                //可用授信额度
                BigDecimal creditAvailable = deliverConsign.getCreditAvailable();
                //授信额度  -  可用授信额度   判断是否有额度
                BigDecimal subtract2 = lineOfCredit.subtract(creditAvailable);  //欠授信额度
                if(subtract2.compareTo(BigDecimal.valueOf(0)) == 1){      //如果有所欠的
                    BigDecimal subtract1 = subtract2.subtract(subtract);    // 欠授信额度  -  多出的 收款 金额
                    //如果欠授信额度大   或者正好    取正值。  预收金额为0
                    if (subtract1.compareTo(BigDecimal.valueOf(0)) == 1 || subtract1.compareTo(BigDecimal.valueOf(0)) == 0){
                        order.setAdvanceMoney(subtract);
                    }else { //取多出的负值
                        BigDecimal abs = subtract1.abs(); //取正值
                        order.setAdvanceMoney(abs);
                    }

                }else { //如果没有直接处理到  预收金额
                    order.setAdvanceMoney(subtract);
                }

            }else { //如果没有多出的直接处理到  预收金额
                order.setAdvanceMoney(subtract);
            }



        }else {
            order.setAdvanceMoney(BigDecimal.valueOf(0));
        }

        orderDao.save(order);

    }

}
