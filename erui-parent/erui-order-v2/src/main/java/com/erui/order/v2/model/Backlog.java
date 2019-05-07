package com.erui.order.v2.model;

import java.util.Date;

public class Backlog {
    private Integer id;

    private String createDate;

    private String placeSystem;

    private String functionExplainName;

    private Integer functionExplainId;

    private String returnNo;

    private String informTheContent;

    private Integer hostId;

    private Integer followId;

    private Integer uid;

    private Integer delYn;

    private Date deleteTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPlaceSystem() {
        return placeSystem;
    }

    public void setPlaceSystem(String placeSystem) {
        this.placeSystem = placeSystem;
    }

    public String getFunctionExplainName() {
        return functionExplainName;
    }

    public void setFunctionExplainName(String functionExplainName) {
        this.functionExplainName = functionExplainName;
    }

    public Integer getFunctionExplainId() {
        return functionExplainId;
    }

    public void setFunctionExplainId(Integer functionExplainId) {
        this.functionExplainId = functionExplainId;
    }

    public String getReturnNo() {
        return returnNo;
    }

    public void setReturnNo(String returnNo) {
        this.returnNo = returnNo;
    }

    public String getInformTheContent() {
        return informTheContent;
    }

    public void setInformTheContent(String informTheContent) {
        this.informTheContent = informTheContent;
    }

    public Integer getHostId() {
        return hostId;
    }

    public void setHostId(Integer hostId) {
        this.hostId = hostId;
    }

    public Integer getFollowId() {
        return followId;
    }

    public void setFollowId(Integer followId) {
        this.followId = followId;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getDelYn() {
        return delYn;
    }

    public void setDelYn(Integer delYn) {
        this.delYn = delYn;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public enum ProjectStatusEnum {

        TRANSACTIONORDER("办理项目", 1), REJECTORDER("驳回订单", 0), EXECUTEPROJECT("执行项目", 2), REJECTPROJRCT("驳回项目", 2),
        PURCHREQUISITION("办理采购申请", 3), PURCHORDER("办理采购订单", 4), INSPECTAPPLY("办理报检单", 5), INSPECTREPORT("办理入库质检", 6),
        INSTOCKSUBMENU("办理入库分单", 7), TRANSACTINSTOCK("办理入库", 7),
        INSTOCKSUBMENUDELIVER("办理出库分单", 8), TRANSACTDELIVER("办理出库", 8), DELIVERDETAIL("办理出库质检", 9), NOTARIZEDELIVER("确认出库", 8),
        LOGISTICS("办理物流分单", 10), LOGISTICSDATA("办理物流", 11), ORDER_AUDIT("订单审核", 12), ORDER_REJECT("订单驳回", 13), PROJECT_AUDIT("项目审核", 14),
        PROJECT_REJECT("项目驳回", 15), PURCH_AUDIT("采购订单审核", 16), PURCH_REJECT("采购订单驳回", 17), ORDER_AUDIT2("订单审核", 18), ORDER_REJECT2("订单驳回", 19),
        PROJECT_REJECT2("项目驳回", 20), ORDER_REJECT3("项目驳回", 18),
        DELIVERCONSIGN_REJECT("出口通知单驳回", 21), DELIVERCONSIGN_AUDIT("出口通知单审核", 22);
        /**
         * 12: 正常订单审核地址
         * 13：订单驳回，正常审核地址
         * 19：订单被驳回到市场经办人，可以编辑订单信息后提交地址
         * 18：是跳转到商务技术审核人处理订单信息的地址
         * 14：正常项目审核地址
         * 15：项目驳回后，正常项目审核地址
         * 20：项目被驳回到商务技术，项目能编辑完后提交
         * 16：采购订单正常审核地址
         * 17：采购订单被驳回后，需要编辑内容，然后提交审核
         */
        private String msg;

        private Integer num;

        ProjectStatusEnum(String msg, Integer num) {
            this.msg = msg;
            this.num = num;
        }

        @Override
        public String toString() {
            return "<" + this.num + "-" + this.msg + ">";
        }

        public String getMsg() {
            return msg;
        }

        public Integer getNum() {
            return num;
        }

    }
}