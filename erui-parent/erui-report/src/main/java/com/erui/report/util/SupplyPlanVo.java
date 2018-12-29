package com.erui.report.util;

import java.math.BigDecimal;

public class SupplyPlanVo {
    private int planSupplierNum;
    private int planSKUNum;
    private int planSPUNum;

    public SupplyPlanVo(int planSupplierNum, int planSKUNum, int planSPUNum) {
        this.planSupplierNum = planSupplierNum;
        this.planSKUNum = planSKUNum;
        this.planSPUNum = planSPUNum;
    }
    public SupplyPlanVo(BigDecimal planSupplierNum, BigDecimal planSKUNum, BigDecimal planSPUNum) {
        this.planSupplierNum = planSupplierNum.intValue();
        this.planSKUNum = planSKUNum.intValue();
        this.planSPUNum = planSPUNum.intValue();
    }
    public int getPlanSupplierNum() {
        return planSupplierNum;
    }

    public void setPlanSupplierNum(int planSupplierNum) {
        this.planSupplierNum = planSupplierNum;
    }

    public int getPlanSKUNum() {
        return planSKUNum;
    }

    public void setPlanSKUNum(int planSKUNum) {
        this.planSKUNum = planSKUNum;
    }

    public int getPlanSPUNum() {
        return planSPUNum;
    }

    public void setPlanSPUNum(int planSPUNum) {
        this.planSPUNum = planSPUNum;
    }
}
