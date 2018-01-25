package com.erui.order.service.impl;

import com.erui.order.dao.AttachmentDao;
import com.erui.order.entity.Attachment;
import com.erui.order.service.AttachmentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by wangxiaodan on 2017/12/20.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private AttachmentDao attachmentDao;

    // 处理附件信息
    @Transactional(rollbackFor = Exception.class)
    public List<Attachment> handleParamAttachment(List<Attachment> existAttachments, List<Attachment> paramsAttachments, Integer userId, String userName) {

        Map<Integer, Attachment> attachmentMap = new HashMap<>();
        if (existAttachments != null){
            attachmentMap = existAttachments.parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
        } else {
            attachmentMap = new HashMap<>();
        }
        Map<Integer, Attachment> attachmentMap2 = attachmentMap;
        List<Attachment> result = paramsAttachments.parallelStream().filter(vo -> {
            Integer id = vo.getId();
            return id == null || attachmentMap2.containsKey(id);
        }).map(attachment -> {
            Integer attId = attachment.getId();
            if (attId == null) {
                attachment.setCreateTime(new Date());
                attachment.setDeleteFlag(false);
                if (userId != null) {
                    attachment.setUserId(userId);
                }
                if (StringUtils.isNotBlank(userName)) {
                    attachment.setUserName(userName);
                }
            } else {
                Attachment attachment1 = attachmentMap2.remove(attId);
                attachment.setCreateTime(attachment1.getCreateTime());
                attachment.setDeleteFlag(attachment1.getDeleteFlag());
                attachment.setUserId(attachment1.getUserId());
                attachment.setUserName(attachment1.getUserName());
            }
            return attachment;
        }).collect(Collectors.toList());
        // 标志删除的附件为删除状态
        if (attachmentMap.size() > 0) {
            List<Attachment> delAttachmentList = attachmentMap.values().parallelStream().map(vo -> {
                vo.setDeleteFlag(true);
                vo.setDeleteTime(new Date());
                return vo;
            }).collect(Collectors.toList());
            attachmentDao.save(delAttachmentList);
        }
        return result;
    }
}
