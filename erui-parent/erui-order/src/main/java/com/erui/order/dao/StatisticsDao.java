package com.erui.order.dao;

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
            "sum(case when nn = 2 then 1 else 0 end) as two_re_purch," +
            "sum(case when nn = 3 then 1 else 0 end) as three_re_purch," +
            "sum(case when t1.nn > 3 then 1 else 0 end) as more_re_purch" +
            " from (select region,country,crm_code,count(crm_code) as nn from `order` where crm_code !='' and signing_date >= :startDate and signing_date < :endDate group by crm_code,region,country ) as t1" +
            " group by region,country", nativeQuery = true)
    List<Object> rePurchRate(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 订单基本统计信息
     *
     * @return
     */
    @Query(value = "select " +
            "new com.erui.order.model.SaleStatistics(region,country,count(id) as orderNum,sum(totalPrice) as orderAmount," +
            " sum(case when customerType = 1 then 1 else 0 end) as oilOrderNum," +
            " sum(case when customerType = 1 then totalPrice else 0 end) as oilOrderAmount," +
            " sum(case when customerType != 1 then 1 else 0 end) as nonOilOrderNum," +
            " sum(case when customerType != 1 then totalPrice else 0 end) as nonOilOrderAmount)" +
            " from Order where signingDate >= :startDate and signingDate < :endDate" +
            " group by region,country")
    List<SaleStatistics> orderBaseSaleStatisInfo(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 按照地区和国家查询询单的基本统计信息
     * @return
     */
    @Query(value = "select inquiry_area,inquiry_unit,count(1) as totalNum,sum(ifnull(quotation_price,0)) as totalAmount from erui_rfq.v_inquiry_count where rollin_time >= :startDate and rollin_time < :endDate group by inquiry_unit,inquiry_area", nativeQuery = true)
    List<Object> inquiryStatisInfo(@Param("startDate") Date startDate,@Param("endDate") Date endDate);
}
