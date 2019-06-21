package com.erui.order.v2.service.impl;

import com.erui.order.v2.dao.AttachmentMapper;
import com.erui.order.v2.model.Attachment;
import com.erui.order.v2.model.AttachmentExample;
import com.erui.order.v2.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void update(Attachment attachment) {
        if (attachment.getId() != null) {
            attachmentMapper.updateByPrimaryKey(attachment);
        }
    }

    @Override
    public void deleteById(Integer attachmentId) {
        attachmentMapper.deleteByPrimaryKey(attachmentId);
    }


    @Override
    public List<Attachment> selectAttachmentByCategoryAndGroupAndRelObjId(String category, String group, Integer relObjId) {
        AttachmentExample example = new AttachmentExample();
        example.createCriteria().andCategoryEqualTo(category).andGroupEqualTo(group).andRelObjIdEqualTo(relObjId);
        List<Attachment> attachments = attachmentMapper.selectByExample(example);
        return attachments;
    }
}
