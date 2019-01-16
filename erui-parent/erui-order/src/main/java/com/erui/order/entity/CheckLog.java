package com.erui.order.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "check_log")
public class CheckLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 订单或项目id
     */
    @Column(name = "order_id")
    private Integer orderId;
    /**
     * 采购订单id
     */
    @Column(name = "purch_id")
    private Integer purchId;

    @Transient
    private String processName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 审核进度
     */
    @Column(name = "auditing_process")
    private Integer auditingProcess;

    /**
     * 当前审核人ID
     */
    @Column(name = "auditing_user_id")
    private Integer auditingUserId;

    /**
     * 当前审核人姓名
     */
    @Column(name = "auditing_user_name")
    private String auditingUserName;

    /**
     * 下一个审核进度名称/下一节点
     */
    @Column(name = "next_auditing_process")
    private String nextAuditingProcess;

    /**
     * 下一个审id
     */
    @Column(name = "next_auditing_user_id")
    private String nextAuditingUserId;

    /**
     * 审核意见/操作说明
     */
    @Column(name = "auditing_msg")
    private String auditingMsg;

    /**
     * 1：立项   2：通过  -1：驳回
     */
    private String operation;

    /**
     * -- 1:订单  2：项目
     */
    private Integer type;

    public Integer getPurchId() {
        return purchId;
    }

    public void setPurchId(Integer purchId) {
        this.purchId = purchId;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取订单id
     *
     * @return order_id - 订单id
     */
    public Integer getOrderId() {
        return orderId;
    }

    /**
     * 设置订单id
     *
     * @param orderId 订单id
     */
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取审核进度
     *
     * @return auditing_process - 审核进度
     */
    public Integer getAuditingProcess() {
        return auditingProcess;
    }

    /**
     * 设置审核进度
     *
     * @param auditingProcess 审核进度
     */
    public void setAuditingProcess(Integer auditingProcess) {
        this.auditingProcess = auditingProcess;
    }

    /**
     * 获取当前审核人ID
     *
     * @return auditing_user_id - 当前审核人ID
     */
    public Integer getAuditingUserId() {
        return auditingUserId;
    }

    /**
     * 设置当前审核人ID
     *
     * @param auditingUserId 当前审核人ID
     */
    public void setAuditingUserId(Integer auditingUserId) {
        this.auditingUserId = auditingUserId;
    }

    /**
     * 获取当前审核人姓名
     *
     * @return auditing_user_name - 当前审核人姓名
     */
    public String getAuditingUserName() {
        return auditingUserName;
    }

    /**
     * 设置当前审核人姓名
     *
     * @param auditingUserName 当前审核人姓名
     */
    public void setAuditingUserName(String auditingUserName) {
        this.auditingUserName = auditingUserName == null ? null : auditingUserName.trim();
    }

    /**
     * 获取下一个审核进度名称/下一节点
     *
     * @return next_auditing_process - 下一个审核进度名称/下一节点
     */
    public String getNextAuditingProcess() {
        return nextAuditingProcess;
    }

    public void setNextAuditingProcess(String nextAuditingProcess) {
        this.nextAuditingProcess = nextAuditingProcess;
    }

    public String getNextAuditingUserId() {
        return nextAuditingUserId;
    }

    public void setNextAuditingUserId(String nextAuditingUserId) {
        this.nextAuditingUserId = nextAuditingUserId;
    }

    /**
     * 获取审核意见/操作说明
     *
     * @return auditing_msg - 审核意见/操作说明
     */
    public String getAuditingMsg() {
        return auditingMsg;
    }

    /**
     * 设置审核意见/操作说明
     *
     * @param auditingMsg 审核意见/操作说明
     */
    public void setAuditingMsg(String auditingMsg) {
        this.auditingMsg = auditingMsg == null ? null : auditingMsg.trim();
    }

    /**
     * 获取 1：立项   2：通过  -1：驳回
     *
     * @return operation -  1：立项   2：通过  -1：驳回
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置 1：立项   2：通过  -1：驳回
     *
     * @param operation 1：立项   2：通过  -1：驳回
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * 获取-- 1:订单  2：项目
     *
     * @return type - -- 1:订单  2：项目
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置-- 1:订单  2：项目
     *
     * @param type -- 1:订单  2：项目 3. 采购
     */
    public void setType(Integer type) {
        this.type = type;
    }


    public String getProcessName() {
        AuditProcessingEnum anEnum = AuditProcessingEnum.findEnum(this.getType(), this.getAuditingProcess());
        if (anEnum == null) {
            return "--";
        }
        return anEnum.getName();
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }


    public enum AuditProcessingEnum {
        // 0.订单提交立项 1.回款责任人审核、2.国家负责人审核、3.区域负责人审核、4.区域VP审核、5.融资专员审核
        //  1.事业部利润核算、2.法务审核、3.财务审核、4.结算审核、5.物流审核、6.事业部审核、7.事业部VP审核、8.总裁审核、9.董事长审核
        A(1, 0, "市场经办人"), B(1, 1, "回款责任人审核"), C(1, 2, "国家负责人审核"), H(1, 8, "法务审核"), D(1, 3, "区域负责人审核"), E(1, 4, "区域VP审核"), F(1, 5, "融资专员审核"), F2(1, 6, "商务技术经办人"), G(1, 7, "事业部利润核算"),
        I(2, 13, "财务审核"), J(2, 14, "结算审核"), K(2, 15, "物流审核"), L(2, 16, "事业部总监审核"), M(2, 17, "事业部VP审核"), N(2, 18, "总裁审核"), O(2, 19, "董事长审核"),
        P1(3, 20, "董事长审核"),P2(3, 21, "采购负责人审核"),P3(3, 22, "商务技术审核"),P4(3, 23, "法务审核"),P5(3, 24, "财务审核"),P6(3, 25, "事业部vp审核"),P7(3, 26, "总裁审核");
        int type;
        int process;
        String name;

        AuditProcessingEnum(int type, int process, String name) {
            this.type = type;
            this.process = process;
            this.name = name;
        }

        public String getName() {
            return name;
        }


        public static AuditProcessingEnum findEnum(Integer type, Integer process) {
            if (type == null || process == null) {
                return null;
            }
            int iType = type.intValue();
            int iProcess = process.intValue();
            AuditProcessingEnum[] values = AuditProcessingEnum.values();
            for (AuditProcessingEnum value : values) {
                if (iType == value.type && iProcess == value.process) {
                    return value;
                }
            }
            return null;
        }
    }
}