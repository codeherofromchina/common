package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.DeliverConsignMapper;
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.DeliverConsignService;
import com.erui.order.v2.service.EmployeeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 新的出口通知单业务类
 *
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:17
 */
@Service("deliverConsignServiceImplV2")
@Transactional(timeout = 500)
public class DeliverConsignServiceImpl implements DeliverConsignService {
    @Autowired
    private DeliverConsignMapper deliverConsignMapper;
    @Autowired
    private EmployeeService employeeService;

    /**
     * 根据业务流的实例ID查找出口通知单信息
     *
     * @param processId
     * @return
     */
    public DeliverConsign findDeliverConsignByProcessId(String processId) {
        DeliverConsignExample example = new DeliverConsignExample();
        DeliverConsignExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<DeliverConsign> deliverConsigns = deliverConsignMapper.selectByExample(example);
        if (deliverConsigns != null || deliverConsigns.size() > 0) {
            return deliverConsigns.get(0);
        }
        return null;
    }

    @Override
    public void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee) {
        // 查询出口通知单
        DeliverConsign deliverConsign = findDeliverConsignByProcessId(processInstanceId);
        if (deliverConsign == null) {
            return;
        }
        // 更新审核进度，如果审核进度为空，则更新审核状态为通过
        Integer auditingStatus = deliverConsign.getAuditingStatus(); // 2:审核中  4：审核完成
        String auditingProcess2 = deliverConsign.getAuditingProcess();
        String audiRemark = deliverConsign.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            if (auditingProcess2.equals(auditingProcess)) {
                auditingStatus = 4;
                auditingProcess2 = "";
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
        // 通过工号查找用户ID
        Long userId = employeeService.findIdByUserNo(assignee);
        if (StringUtils.isNotBlank(audiRemark)) {
            if (userId != null && !audiRemark.contains("," + userId + ",")) {
                audiRemark += "," + userId + ",";
            }
        } else if (userId != null) {
            audiRemark = "," + userId + ",";
        }
        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());
        deliverConsignSelective.setAuditingStatus(auditingStatus);
        deliverConsignSelective.setAuditingProcess(auditingProcess2);
        deliverConsignSelective.setAudiRemark(audiRemark);
        deliverConsignMapper.updateByPrimaryKeySelective(deliverConsignSelective);
    }

    @Override
    public void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId) {
        // 查询出口通知单
        DeliverConsign deliverConsign = findDeliverConsignByProcessId(processInstanceId);
        if (deliverConsign == null) {
            return;
        }
        // 处理出口通知单的审核状态和审核进度
        Integer auditingStatus = 2; // 2:审核中
        String auditingProcess2 = deliverConsign.getAuditingProcess();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            auditingProcess2 = auditingProcess2 + "," + auditingProcess;
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新修正后的状态
        DeliverConsign deliverConsignSelective = new DeliverConsign();
        deliverConsignSelective.setId(deliverConsign.getId());
        deliverConsignSelective.setAuditingStatus(auditingStatus);
        deliverConsignSelective.setAuditingProcess(auditingProcess2);
        deliverConsignSelective.setTaskId(taskId);
        deliverConsignMapper.updateByPrimaryKeySelective(deliverConsignSelective);
    }
}
