package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.OrderMapper;
import com.erui.order.v2.model.Order;
import com.erui.order.v2.model.OrderExample;
import com.erui.order.v2.model.OrderWithBLOBs;
import com.erui.order.v2.service.UserService;
import com.erui.order.v2.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新的订单业务类
 * 订单的新业务写到此业务类中
 *
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:17
 */
@Service("orderServiceImplV2")
@Transactional(timeout = 500)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserService userService;

    /**
     * 根据业务流的实例ID查找订单信息
     *
     * @param processId
     * @return
     */
    @Transactional(readOnly = true)
    public Order findOrderByProcessId(String processId) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<Order> orders = orderMapper.selectByExample(example);
        if (orders != null && orders.size() > 0) {
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
        String auditingProcess2 = order.getAuditingProcess();
        String auditingUserId = order.getAuditingUserId();
        String audiRemark = order.getAudiRemark();

        if (StringUtils.isNotBlank(auditingProcess2)) {
            List<String> auditingProcessList = new ArrayList<>(Arrays.asList(auditingProcess2.split(",")));
            if (auditingProcessList.size() == 1) {
                auditingProcess2 = "";
                auditingUserId = "";
            } else {
                String[] auditingUserIdArr = null;
                if (StringUtils.isNotBlank(auditingUserId)) {
                    auditingUserIdArr = StringUtils.splitPreserveAllTokens(auditingUserId, ",");
                } else {
                    auditingUserIdArr = new String[auditingProcessList.size()];
                }

                String[] auditingUserIdArr02 = new String[auditingProcessList.size() - 1];
                Iterator<String> iterator = auditingProcessList.iterator();
                int i = 0;
                int n = 0;
                boolean removed = false;
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    if (StringUtils.equals(next, auditingProcess)) {
                        iterator.remove();
                        removed = true;
                    } else {
                        auditingUserIdArr02[n] = auditingUserIdArr[i];
                        ++n;
                    }
                    ++i;
                }

                auditingProcess2 = StringUtils.join(auditingProcessList, ",");
                if (removed) {
                    auditingUserId = StringUtils.join(auditingUserIdArr02, ",");
                } else {
                    auditingUserId = StringUtils.join(auditingUserIdArr, ",");
                }
            }
        }
        // 设置审核人
        // 通过工号查找用户ID
        Long userId = userService.findIdByUserNo(assignee);
        if (StringUtils.isNotBlank(audiRemark)) {
            if (userId != null && !audiRemark.contains("," + userId + ",")) {
                audiRemark += "," + userId + ",";
            }
        } else if (userId != null) {
            audiRemark = "," + userId + ",";
        }

        // 更新修正后的状态
        OrderWithBLOBs orderSelective = new OrderWithBLOBs();
        orderSelective.setId(order.getId());
        orderSelective.setAuditingProcess(auditingProcess2);
        orderSelective.setAuditingUserId(auditingUserId);
        orderSelective.setAudiRemark(audiRemark);
        orderMapper.updateByPrimaryKeySelective(orderSelective);
    }

    @Override
    public void updateAuditProcessDoing(Long orderId, String auditingProcess, String taskId, boolean rejected) {
        // 查询订单
        Order order = orderMapper.selectByPrimaryKey(Math.toIntExact(orderId));
        if (order == null) {
            return;
        }
        // 处理订单的审核状态和审核进度
        int auditingStatus = rejected ? Order.AuditingStatusEnum.REJECT.getStatus() : Order.AuditingStatusEnum.PROCESSING.getStatus();
        String auditingProcess2 = order.getAuditingProcess();
        String auditingUserId = order.getAuditingUserId();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            Set<String> set = new HashSet<>(Arrays.asList(auditingProcess2.split(",")));
            if (!set.contains(auditingProcess)) {
                auditingProcess2 = auditingProcess2 + "," + auditingProcess;
                auditingUserId += ",";
            }
        } else {
            auditingProcess2 = auditingProcess;
            auditingUserId = "";
        }
        // 更新订单的审核状态、审核进度和当前任务
        // 更新修正后的状态
        OrderWithBLOBs orderSelective = new OrderWithBLOBs();
        orderSelective.setId(order.getId());
        orderSelective.setAuditingStatus(auditingStatus);
        orderSelective.setAuditingProcess(auditingProcess2);
        orderSelective.setAuditingUserId(auditingUserId);
        orderSelective.setTaskId(taskId);
        orderMapper.updateByPrimaryKeySelective(orderSelective);
    }

    @Override
    public Order findOrderById(Integer orderId) {
        OrderWithBLOBs orderWithBLOBs = orderMapper.selectByPrimaryKey(orderId);
        return orderWithBLOBs;
    }


    @Override
    public void updateById(Integer id, Order order) {
        order.setId(id);
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public Map<Integer, String> findContractsByOrderIds(List<Integer> orderIds) {
        OrderExample example = new OrderExample();
        OrderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(orderIds);
        List<Order> orders = orderMapper.selectByExample(example);

        Map<Integer, String> result = null;
        if (orders != null && orders.size() > 0) {
            result = orders.stream().collect(Collectors.toMap(vo -> vo.getId(), vo -> vo.getContractNo()));
        }
        if (result == null) {
            result = new HashMap<>();
        }

        return result;
    }

    @Override
    public void updateAuditUser(Long orderId, Long userId, String userName, String actId) {
        OrderWithBLOBs order = orderMapper.selectByPrimaryKey(orderId.intValue());
        // 获取原来的审核进度和相应审核人
        String auditingProcess = order.getAuditingProcess();
        String auditingUserId = order.getAuditingUserId();
        if (StringUtils.isBlank(auditingProcess)) {
            return;
        }
        // 处理审核人到审核进度的相应索引上
        String[] split = auditingProcess.split(",");
        String[] userIds ;
        if (StringUtils.isNotBlank(auditingUserId)) {
            userIds = StringUtils.splitPreserveAllTokens(auditingUserId, ",");
        } else {
            userIds = new String[split.length];
        }
        for (int i=0; i< split.length; ++i) {
            if (StringUtils.equals(split[i], actId) && userIds.length > i) {
                userIds[i] = String.valueOf(userId);
                break;
            }
        }

        // 更新
        OrderWithBLOBs selectiveOrder = new OrderWithBLOBs();
        selectiveOrder.setId(order.getId());
        selectiveOrder.setAuditingUserId(StringUtils.join(userIds, ","));
        orderMapper.updateByPrimaryKeySelective(selectiveOrder);
    }


    @Override
    public void deleteAuditUser(Long orderId, String actId) {
        OrderWithBLOBs order = orderMapper.selectByPrimaryKey(orderId.intValue());
        // 获取原来的审核进度和相应审核人
        String auditingProcess = order.getAuditingProcess();
        String auditingUserId = order.getAuditingUserId();
        if (StringUtils.isBlank(auditingProcess)) {
            return;
        }
        // 处理审核人到审核进度的相应索引上
        String[] split = auditingProcess.split(",");
        String[] userIds = StringUtils.splitPreserveAllTokens(auditingUserId, ",");
        if (split.length != userIds.length) {
            // 数据正常的情况下是一一对应的，所以大小是相等的
            return;
        }
        for (int i=0; i< split.length; ++i) {
            if (StringUtils.equals(split[i], actId)) {
                userIds[i] = "";
                break;
            }
        }
        // 更新
        OrderWithBLOBs selectiveOrder = new OrderWithBLOBs();
        selectiveOrder.setId(order.getId());
        selectiveOrder.setAuditingUserId(StringUtils.join(userIds, ","));
        orderMapper.updateByPrimaryKeySelective(selectiveOrder);
    }

    @Override
    public void updateProcessCompleted(String processInstanceId) {
        // 修改审核状态
        // 查询订单
        Order order = findOrderByProcessId(processInstanceId);
        if (order == null) {
            return;
        }
        OrderWithBLOBs orderSelective = new OrderWithBLOBs();
        orderSelective.setId(order.getId());
        orderSelective.setAuditingStatus(4); // 2:审核中  4：审核完成
        orderSelective.setAuditingProcess("999");  // 999为审核进度审核完成
        orderMapper.updateByPrimaryKeySelective(orderSelective);
    }
}
