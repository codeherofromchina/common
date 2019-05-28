package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.GoodsMapper;
import com.erui.order.v2.dao.PurchContractGoodsMapper;
import com.erui.order.v2.dao.PurchGoodsMapper;
import com.erui.order.v2.dao.PurchMapper;
import com.erui.order.v2.model.*;
import com.erui.order.v2.service.UserService;
import com.erui.order.v2.service.ProjectService;
import com.erui.order.v2.service.PurchService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 新的项目业务类
 *
 * @Auther 王晓丹
 * @Date 2019/4/28 下午2:17
 */
@Service("purchServiceImplV2")
@Transactional
public class PurchServiceImpl implements PurchService {
    @Autowired
    private PurchMapper purchMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;
    @Autowired
    private PurchGoodsMapper purchGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private PurchContractGoodsMapper purchContractGoodsMapper;

    /**
     * 根据业务流的实例ID查找订单信息
     *
     * @param processId
     * @return
     */
    public Purch findPurchByProcessId(String processId) {
        PurchExample example = new PurchExample();
        PurchExample.Criteria criteria = example.createCriteria();
        criteria.andProcessIdEqualTo(processId);
        List<Purch> purchs = purchMapper.selectByExample(example);
        if (purchs != null || purchs.size() > 0) {
            return purchs.get(0);
        }
        return null;
    }

    @Override
    public void updateAuditProcessDone(String processInstanceId, String auditingProcess, String assignee) {
        // 查询采购
        Purch purch = findPurchByProcessId(processInstanceId);
        if (purch == null) {
            return;
        }
        // 更新审核进度，如果审核进度为空，则更新审核状态为通过
        String auditingProcess2 = purch.getAuditingProcess();
        String auditingUserId = purch.getAuditingUserId();
        String auditingUserName = purch.getAuditingUser();
        String audiRemark = purch.getAudiRemark();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            List<String> auditingProcessList = Arrays.asList(auditingProcess2.split(","));
            String[] auditingUserIdArr = null;
            String[] auditingUserNameArr = null;
            if (StringUtils.isNotBlank(auditingUserId)) {
                auditingUserIdArr = auditingUserId.split(",");
            } else {
                auditingUserIdArr = new String[auditingProcessList.size()];
            }
            if (StringUtils.isNotBlank(auditingUserName)) {
                auditingUserNameArr = auditingUserName.split(",");
            } else {
                auditingUserNameArr = new String[auditingProcessList.size()];
            }

            String[] auditingUserIdArr02 = new String[auditingProcessList.size() -1];
            String[] auditingUserNameArr02 = new String[auditingProcessList.size() -1];
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
                    auditingUserNameArr02[n] = auditingUserNameArr[i];
                    ++n;
                }
                ++i;
            }

            auditingProcess2 = StringUtils.join(auditingProcessList, ",");
            if (removed) {
                auditingUserId = StringUtils.join(auditingUserIdArr02, ",");
                auditingUserName = StringUtils.join(auditingUserNameArr02, ",");
            } else {
                auditingUserId = StringUtils.join(auditingUserIdArr, ",");
                auditingUserName = StringUtils.join(auditingUserNameArr02, ",");
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
        Purch purchSelective = new Purch();
        purchSelective.setId(purch.getId());
        purchSelective.setAuditingProcess(auditingProcess2);
        purchSelective.setAuditingUserId(auditingUserId);
        purchSelective.setAuditingUser(auditingUserName);
        purchSelective.setAudiRemark(audiRemark);
        purchMapper.updateByPrimaryKeySelective(purchSelective);
    }

    @Override
    public void updateAuditProcessDoing(String processInstanceId, String auditingProcess, String taskId) {
        // 查询采购
        Purch purch = findPurchByProcessId(processInstanceId);
        if (purch == null) {
            return;
        }
        // 处理采购的审核状态和审核进度
        Integer auditingStatus = 2; // 2:审核中
        String auditingProcess2 = purch.getAuditingProcess();
        String auditingUserName = purch.getAuditingUser();
        String auditingUserId = purch.getAuditingUserId();
        if (StringUtils.isNotBlank(auditingProcess2)) {
            Set<String> set = new HashSet<>(Arrays.asList(auditingProcess2.split(",")));
            if (!set.contains(auditingProcess)) {
                auditingProcess2 = auditingProcess2 + "," + auditingProcess;
                auditingUserName += ",";
                auditingUserId += ",";
            }
        } else {
            auditingProcess2 = auditingProcess;
        }
        // 更新采购的审核状态、审核进度和当前任务
        // 更新修正后的状态
        Purch purchSelective = new Purch();
        purchSelective.setId(purch.getId());
        purchSelective.setAuditingStatus(auditingStatus);
        purchSelective.setAuditingProcess(auditingProcess2);
        purchSelective.setAuditingUser(auditingUserName);
        purchSelective.setAuditingUserId(auditingUserId);
        purchSelective.setTaskId(taskId);
        purchMapper.updateByPrimaryKeySelective(purchSelective);
    }

    @Override
    public void updateProcessCompleted(String processInstanceId) {
        // 查询采购
        Purch purch = findPurchByProcessId(processInstanceId);
        if (purch == null) {
            return;
        }

        Purch purchSelective = new Purch();
        purchSelective.setId(purch.getId());
        List<Project> projects = projectService.findProjectsByPurchId(purch.getId());
        if (projects.size() > 0) {
            Integer orderCategory = projects.get(0).getOrderCategory();
            Integer status = purch.getStatus();
            if (orderCategory != null && orderCategory == 6 && status != null && status > 1) {
                purchSelective.setStatus(Purch.StatusEnum.DONE.getCode());
            }
        }
        purchSelective.setAuditingStatus(4); // 设置为审核完成 4：审核完成
        purchSelective.setAuditingProcess("999"); //
        purchMapper.updateByPrimaryKeySelective(purchSelective);
    }


    @Override
    public Purch selectById(Integer id) {
        Purch purch = purchMapper.selectByPrimaryKey(id);
        return purch;
    }

    @Override
    public Map<Integer, String> findPurchNoByPurchIds(List<Integer> purchIds) {
        PurchExample example = new PurchExample();
        PurchExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(purchIds);
        List<Purch> purches = purchMapper.selectByExample(example);
        Map<Integer, String> result = null;
        if (purches != null && purches.size() > 0) {
            result = purches.stream().collect(Collectors.toMap(vo -> vo.getId(), vo -> vo.getPurchNo()));
        }
        if (result == null) {
            result = new HashMap<>();
        }
        return result;
    }

    /**
     * 驳回采购订单返还已采购数量
     *
     * @param id
     */
    @Override
    public void rejectPurch(Integer id) {
        // 查询采购
        Purch purch = purchMapper.selectByPrimaryKey(id);
        if (purch == null) {
            return;
        }
        Purch purchSelective = new Purch();
        purchSelective.setId(purch.getId());
        purchSelective.setAuditingStatus(3); // 设置为审核驳回 3：驳回
        purchSelective.setQualityInspectStatus(0); // 如果是驳回则重置质检部重新评估风险等级状态 0：还未重新评估 1：已重新评估
        purchMapper.updateByPrimaryKeySelective(purchSelective);
    }

    @Override
    public void updateAuditUser(Long purchId, Long userId, String userName, String actId) {
        Purch purch = purchMapper.selectByPrimaryKey(purchId.intValue());
        // 获取原来的审核进度和相应审核人
        String auditingProcess = purch.getAuditingProcess();
        String auditingUserId = purch.getAuditingUserId();
        String auditingUser = purch.getAuditingUser();
        if (StringUtils.isBlank(auditingProcess)) {
            return;
        }
        // 处理审核人到审核进度的相应索引上
        String[] split = auditingProcess.split(",");
        String[] userIds ;
        String[] userNames ;
        if (StringUtils.isNotBlank(auditingUserId)) {
            userIds = auditingUserId.split(",");
            userNames = auditingUser.split(",");
        } else {
            userIds = new String[split.length];
            userNames = new String[split.length];
        }
        for (int i=0; i< split.length; ++i) {
            if (StringUtils.equals(split[i], actId) && userIds.length > i) {
                userIds[i] = String.valueOf(userId);
                if (userNames.length > i) {
                    userNames[i] = userName;
                }
                break;
            }
        }

        // 更新
        Purch selectivePurch = new Purch();
        selectivePurch.setId(purch.getId());
        selectivePurch.setAuditingUserId(StringUtils.join(userIds));
        selectivePurch.setAuditingUser(StringUtils.join(userNames));
        purchMapper.updateByPrimaryKeySelective(selectivePurch);
    }
}
