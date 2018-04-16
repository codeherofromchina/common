package com.erui.order.dao;

import com.erui.order.entity.Order;
import com.erui.order.entity.Purch;
import com.erui.order.model.SaleStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2018/4/2.
 */
public interface StatisticsDao extends JpaRepository<Purch, Serializable> {


    /**
     * 查询复购率
     *
     * @return
     */
    @Query(value = "select region,country,count(nn) as total," +
            "sum(case when nn = 2 then 1 else 0 end) as one_re_purch," +
            "sum(case when nn = 3 then 1 else 0 end) as two_re_purch," +
            "sum(case when nn = 4 then 1 else 0 end) as three_re_purch," +
            "sum(case when t1.nn > 4 then 1 else 0 end) as more_re_purch" +
            " from (select region,country,crm_code,count(crm_code) as nn from `order` where `status` >= 3 and crm_code !='' and signing_date >= :startDate and signing_date < :endDate group by crm_code,region,country ) as t1" +
            " group by region,country", nativeQuery = true)
    List<Object> rePurchRate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);



    /**
     * 查询商品纬度的询单信息
     *
     * @return
     */
    @Query(value = "SELECT t1.sku,t2.area_bn,t2.country_bn,count(distinct t2.id),sum(IFNULL(t3.total_quote_price,0) * t1.qty)  " +
            " from erui_rfq.inquiry_item t1,erui_rfq.inquiry t2,erui_rfq.final_quote_item t3 " +
            " where t1.inquiry_id = t2.id and t1.id = t3.inquiry_item_id " +
            " and t1.created_at >= :startDate and t1.created_at < :endDate " +
            " group by t1.sku,t2.area_bn,t2.country_bn", nativeQuery = true)
    List<Object> inquiryStatisGroupBySku(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 订单基本统计信息
     *  执行中的订单
     * @return
     */
    @Query(value = "select " +
            "new com.erui.order.model.SaleStatistics(region,country,count(id) as orderNum,sum(totalPrice) as orderAmount," +
            " sum(case when customerType = 1 then 1 else 0 end) as oilOrderNum," +
            " sum(case when customerType = 1 then totalPrice else 0 end) as oilOrderAmount," +
            " sum(case when customerType != 1 then 1 else 0 end) as nonOilOrderNum," +
            " sum(case when customerType != 1 then totalPrice else 0 end) as nonOilOrderAmount," +
            " sum(case when crmCode <> '' then 1 else 0 end) as crmOrderNum," +
            " sum(case when crmCode <> '' then totalPrice else 0 end)  as crmOrderAmount)" +
            " from Order where status >= 3 and signingDate >= :startDate and signingDate < :endDate" +
            " group by region,country")
    List<SaleStatistics> orderBaseSaleStatisInfo(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 按照地区和国家查询询单的基本统计信息
     * @return
     */
    @Query(value = "select inquiry_area,inquiry_unit,count(1) as totalNum,sum(ifnull(quotation_price,0)) as totalAmount from erui_rfq.v_inquiry_count where rollin_time >= :startDate and rollin_time < :endDate group by inquiry_unit,inquiry_area", nativeQuery = true)
    List<Object> inquiryStatisInfo(@Param("startDate") Date startDate,@Param("endDate") Date endDate);


    @Query(value = "select t1.goods_id,t1.purchase_num,t1.purchase_price,t1.purchase_total_price,t2.purch_no,t2.signing_date," +
            " t2.arrival_date,t2.pay_type,t2.pay_factory_date from purch_goods t1,purch t2 where t1.purch_id = t2.id and t2.`status` = 3 and t1.goods_id in :goodsIds",nativeQuery = true)
    List<Object> findPurchGoods(@Param("goodsIds") List<Integer> goodsIds);



    @Query(value = "select t1.goods_id,t1.inspect_num,(t1.inspect_num - t1.unqualified),t2.inspect_date,t2.supplier_name,t3.check_date,t4.instock_date " +
            " from inspect_apply_goods t1,inspect_apply t2 left outer join inspect_report t3 on t2.id = t3.inspect_apply_id left outer join instock t4 on t4.inspect_report_id = t3.id and t4.`status` = 3  " +
            " where t1.inspect_apply_id = t2.id and t1.goods_id in :goodsIds",nativeQuery = true)
    List<Object> findInspectApplyGoods(@Param("goodsIds") List<Integer> goodsIds);



    @Query(value = "select t1.goods_id,t1.send_num,t2.booking_date,t3.check_date,t3.booking_time,t3.logistics_user_name,t3.leave_factory," +
            "t3.logi_invoice_no,t3.packing_time,t3.sailing_date,t3.arrival_port_time,t3.accomplish_date" +
            " from deliver_consign_goods t1,deliver_consign t2 left outer join deliver_detail t3 on t2.id = t3.deliver_consign_id " +
            "where t1.deliver_consign_id = t2.id and t2.`status` = 3 and t1.goods_id in :goodsIds",nativeQuery = true)
    List<Object> findDeliverConsignGoods(@Param("goodsIds") List<Integer> goodsIds);

    @Query(value = "select order_id,sum(money),min(payment_date) from order_account where del_yn=1 and order_id in :orderIds group by order_id",nativeQuery = true)
    List<Object> findOrderAccount(@Param("orderIds") List<Integer> orderIds);
}
