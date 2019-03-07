package com.erui.order.service.impl;

import com.erui.order.dao.AttachmentDao;
import com.erui.order.entity.Attachment;
import com.erui.order.service.AttachmentService;
import com.erui.order.util.exception.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
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
        if (existAttachments != null && existAttachments.size() > 0) {
            attachmentMap = existAttachments.parallelStream().collect(Collectors.toMap(Attachment::getId, vo -> vo));
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

    @Transactional(rollbackFor = Exception.class)
    public void updateAttachments(List<Attachment> attachmentList, Map<Integer, Attachment> dbAttahmentsMap, Integer relObjId, String category) throws Exception {
        Attachment attachment = null;
        List<Attachment> addAttachments = new ArrayList<>();
        for (Attachment attachment1 : attachmentList) {
            if (attachment1.getId() == null) {
                attachment = new Attachment();
            } else {
                attachment = dbAttahmentsMap.remove(attachment1.getId());
                if (attachment == null) {
                    throw new MyException("不存在的附件标识&&Non-existent attachment identifier");
                }
            }
            attachment1.copyBaseInfoTo(attachment);
            //设置附件关联id 和附件类型
            attachment.setRelObjId(relObjId);
            attachment.setCategory(category);
            addAttachments.add(attachment);
        }
        attachmentDao.save(addAttachments);
        attachmentDao.delete(dbAttahmentsMap.values());

    }

    @Override
    public void addAttachments(List<Attachment> attachmentList, Integer id, String category) {
        List<Attachment> attachments = new ArrayList<>();
        for (Attachment attachment : attachmentList) {
            attachment.setRelObjId(id);
            attachment.setCategory(category);
            attachments.add(attachment);
        }
        attachmentDao.save(attachments);
    }

    @Override
    public List<Attachment> queryAttachs(Integer id, String category) {
        List<Attachment> attachments = new ArrayList<>();
        if (id != null && category != null) {
            attachments = attachmentDao.findByRelObjIdAndCategory(id, category);
        }
        if (attachments != null && attachments.size() > 0) {
            return attachments;
        }
        return null;
    }
}
