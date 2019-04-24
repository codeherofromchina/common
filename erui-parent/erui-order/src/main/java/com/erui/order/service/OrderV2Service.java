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
     * 根据条件分页查询订单列表
     *
     * @param condition
     * @return
     */
    Page<Order> findByPage(OrderV2ListRequest condition);

    /**
     * 查询所有订单的审核节点
     * @return
     * @throws Exception
     */
    List findAllAuditProcess() throws Exception;

    /**
     * 更新审核进度完成
     * @param processInstanceId
     * @param taskDefinitionKey
     */
    void updateAuditProcessDone(String processInstanceId, String taskDefinitionKey);

    /**
     * 更新审核进度进行中
     * @param processInstanceId
     * @param taskDefinitionKey
     */
    void updateAuditProcessDoing(String processInstanceId, String taskDefinitionKey, String taskId);
}
