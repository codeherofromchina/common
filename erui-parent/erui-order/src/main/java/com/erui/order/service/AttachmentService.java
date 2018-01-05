package com.erui.order.service;

import com.erui.order.entity.Attachment;

import java.util.List;

/**
 * Created by wangxiaodan on 2017/12/20.
 */
public interface AttachmentService {


    /**
     * 处理附件信息
     * @param existAttachments
     * @param paramsAttachments
     * @param userId
     * @param userName
     * @return
     */
    public List<Attachment> handleParamAttachment(List<Attachment> existAttachments, List<Attachment> paramsAttachments, Integer userId, String userName);
}
