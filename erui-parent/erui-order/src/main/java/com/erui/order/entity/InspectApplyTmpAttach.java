package com.erui.order.entity;

import javax.persistence.*;

/**
 * 报检单-临时附件表（用于重新报检时临时保存附件）
 * Created by wangxiaodan on 2018/2/6.
 */
@Entity
@Table(name = "inspect_apply_tmp_attach")
public class InspectApplyTmpAttach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "inspect_apply_id")
    private Integer inspectApplyId;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "attach_id")
    private Attachment attachment;

    public Integer getId() {
        return id;
    }

    public Integer getInspectApplyId() {
        return inspectApplyId;
    }

    public void setInspectApplyId(Integer inspectApplyId) {
        this.inspectApplyId = inspectApplyId;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }
}
