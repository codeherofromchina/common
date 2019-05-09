package com.erui.order.service;

import com.erui.order.entity.Attachment;

import java.util.List;
import java.util.Map;

/**
 * Created by wangxiaodan on 2017/12/20.
 */
public interface AttachmentService {


    /**
     * 处理附件信息
     *
     * @param existAttachments
     * @param paramsAttachments
     * @param userId
     * @param userName
     * @return
     */
    List<Attachment> handleParamAttachment(List<Attachment> existAttachments, List<Attachment> paramsAttachments, Integer userId, String userName);

    void updateAttachments(List<Attachment> attachmentList, Map<Integer, Attachment> dbAttahmentsMap, Integer relObjId, String category) throws Exception;

    void addAttachments(List<Attachment> attachmentList, Integer id, String category);

    List<Attachment> queryAttachs(Integer id, String category);
}
