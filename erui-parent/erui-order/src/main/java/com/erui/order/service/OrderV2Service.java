package com.erui.order.service;


import com.erui.order.entity.CheckLog;
import com.erui.order.entity.ComplexOrder;
import com.erui.order.entity.Order;
import com.erui.order.entity.OrderLog;
import com.erui.order.requestVo.*;
import com.erui.order.util.excel.ImportDataResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 订单业务类
 * Created by wangxiaodan on 2017/12/11.
 */
public interface OrderV2Service {
    /**
     * 根据id查询订单信息
     *
     * @param id
     * @return
     */
    Order findById(Integer id);

    /**
     * 根据id查询订单信息
     *
     * @param id
     * @return
     */
    Order findByIdLang(Integer id, String lang);



    /**
     * 修改订单
     *
     * @param addOrderVo
     * @return
     */
    Integer updateOrder(Integer orderId, AddOrderV2Vo addOrderVo) throws Exception;

    /**
     * 添加订单
     *
     * @param addOrderVo
     * @return
     */
    Integer addOrder(AddOrderV2Vo addOrderVo) throws Exception;




    /**
     * 审核订单
     * @param auditOrderRequestVo
     * @param userId
     * @param userName
     */
    void audit(AuditOrderRequestVo auditOrderRequestVo, Integer userId, String userName) throws Exception;
}
