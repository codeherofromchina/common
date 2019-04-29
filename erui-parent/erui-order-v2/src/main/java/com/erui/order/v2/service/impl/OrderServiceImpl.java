package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.OrderMapper;
import com.erui.order.v2.model.Order;
import com.erui.order.v2.model.OrderExample;
import com.erui.order.v2.model.OrderWithBLOBs;
import com.erui.order.v2.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 新的订单业务类
 * 订单的新业务写到此业务类中
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:17
 */
@Service
@Transactional(timeout = 500)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    /**
     * 根据业务流的实例ID查找订单信息
     * @param processId
     * @return
     */
    @Transactional(readOnly = true)
    public Order findOrderByProcessId (String processId) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<Order> orders = orderMapper.selectByExample(example);
        if (orders != null || orders.size() > 0) {
            return orders.get(0);
        }
        return null;
    }

    @Override
    public void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee) {
        // 查询订单
        Order order = findOrderByProcessId(processInstanceId);
        if (order == null) {
            return;
        }
        // 更新审核进度，如果审核进度为空，则更新审核状态为通过
        Integer auditingStatus = order.getAuditingStatus(); // 2:审核中  4：审核完成
        String auditingProcess2 = order.getAuditingProcess();
        String audiRemark = order.getAudiRemark();

        if (StringUtils.isNotBlank(auditingProcess2)) {
            if (auditingProcess2.equals(auditingProcess)) {
                auditingStatus = 4;
            } else {
                auditingProcess2 = auditingProcess2.replace(auditingProcess, "");
                while (auditingProcess2.indexOf(",,") != -1) {
                    auditingProcess2 = auditingProcess2.replace(",,", ",");
                }
                auditingProcess2 = StringUtils.strip(auditingProcess2, ",");
            }
        } else {
            auditingStatus = 4;
        }
        // 设置审核人
        if (StringUtils.isNotBlank(audiRemark)) {
            if (StringUtils.isNotBlank(assignee)) {
                audiRemark += "," + assignee + ",";
            }
        } else if (StringUtils.isNotBlank(assignee)) {
            audiRemark = "," + assignee + ",";
        }

        // 更新修正后的状态
        OrderWithBLOBs orderSelective = new OrderWithBLOBs();
        orderSelective.setId(order.getId());
        orderSelective.setAuditingStatus(auditingStatus);
        orderSelective.setAuditingProcess(auditingProcess2);
        orderSelective.setAudiRemark(audiRemark);
        orderMapper.updateByPrimaryKeySelective(orderSelective);
    }

    @Override
    public void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId) {
        // 查询订单
        Order order = findOrderByProcessId(processInstanceId);
        if (order == null) {
            return;
        }
        // 处理订单的审核状态和审核进度
        Integer auditingStatus = 2; // 2:审核中
        String auditingProcess2 = order.getAuditingProcess();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            auditingProcess2 = auditingProcess2 + "," + auditingProcess;
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新订单的审核状态、审核进度和当前任务
        // 更新修正后的状态
        OrderWithBLOBs orderSelective = new OrderWithBLOBs();
        orderSelective.setId(order.getId());
        orderSelective.setAuditingStatus(auditingStatus);
        orderSelective.setAuditingProcess(auditingProcess2);
        orderSelective.setTaskId(taskId);
        orderMapper.updateByPrimaryKeySelective(orderSelective);
    }
}
