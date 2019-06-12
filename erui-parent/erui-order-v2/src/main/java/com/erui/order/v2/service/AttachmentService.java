package com.erui.order.v2.service;

import com.erui.order.v2.model.Attachment;

/**
 * @Auther 王晓丹
 * @Date 2019/6/12 下午7:01
 */
public interface AttachmentService {
    Integer insert(Attachment attachment) throws Exception;

    void deleteById(Integer attachmentId);
}
