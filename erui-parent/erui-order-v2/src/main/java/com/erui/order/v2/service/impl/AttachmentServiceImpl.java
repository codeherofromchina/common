package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.AttachmentMapper;
import com.erui.order.v2.model.Attachment;
import com.erui.order.v2.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther 王晓丹
 * @Date 2019/6/12 下午7:01
 */
@Service("attachmentServiceImplV2")
public class AttachmentServiceImpl implements AttachmentService {
    @Autowired
    private AttachmentMapper attachmentMapper ;
    @Override
    public Integer insert(Attachment attachment) throws Exception {

        int record = attachmentMapper.insertSelective(attachment);
        if (record <=0 ) {
            throw new Exception("附件新增失败");
        }
        return attachment.getId();
    }


    @Override
    public void deleteById(Integer attachmentId) {
        attachmentMapper.deleteByPrimaryKey(attachmentId);
    }
}
