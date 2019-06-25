package com.erui.order.v2.service;

import com.erui.order.v2.model.Attachment;

import java.util.List;

/**
 * @Auther 王晓丹
 * @Date 2019/6/12 下午7:01
 */
public interface AttachmentService {
    Integer insert(Attachment attachment) throws Exception;

    void update(Attachment attachment);

    void deleteById(Integer attachmentId);

    /**
     * 根据实体类别,分类，和关联ID
     * @param category
     * @param group
     * @param relObjId
     * @return
     */
    List<Attachment> selectAttachmentByCategoryAndGroupAndRelObjId(String category, String group, Integer relObjId);
}
