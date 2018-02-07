package com.erui.report.util;
/*
* 采购品类明细vo
* */
public class ProcurCateDetailVo {
    private String itemClass;//品类
    private int  procurementCount;//采购申请单数量
    private  Double  signingContractAmount;//签约合同金额
    private Double signingContractCount;//签约合同数量
    private Double  procurementChainRate;//采购申请单环比率
    private Double signingContractChainRate;//签约合同数量环比
    private Double signingContractAmountChainRate;//签约合同金额环比

    public String getItemClass() {
        return itemClass;
    }

    public void setItemClass(String itemClass) {
        this.itemClass = itemClass;
    }

    public int getProcurementCount() {
        return procurementCount;
    }

    public void setProcurementCount(int procurementCount) {
        this.procurementCount = procurementCount;
    }

    public Double getSigningContractAmount() {
        return signingContractAmount;
    }

    public void setSigningContractAmount(Double signingContractAmount) {
        this.signingContractAmount = signingContractAmount;
    }

    public Double getSigningContractCount() {
        return signingContractCount;
    }

    public void setSigningContractCount(Double signingContractCount) {
        this.signingContractCount = signingContractCount;
    }

    public Double getProcurementChainRate() {
        return procurementChainRate;
    }

    public void setProcurementChainRate(Double procurementChainRate) {
        this.procurementChainRate = procurementChainRate;
    }

    public Double getSigningContractChainRate() {
        return signingContractChainRate;
    }

    public void setSigningContractChainRate(Double signingContractChainRate) {
        this.signingContractChainRate = signingContractChainRate;
    }

    public Double getSigningContractAmountChainRate() {
        return signingContractAmountChainRate;
    }

    public void setSigningContractAmountChainRate(Double signingContractAmountChainRate) {
        this.signingContractAmountChainRate = signingContractAmountChainRate;
    }
}
