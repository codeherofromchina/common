package com.erui.report.model;

import java.math.BigDecimal;

/**
 * Created by lirb on 2017/10/24.
 */
public class SuppliyChainOrgVo {
    private int      planSKU;
    private int      finishSKU;
    private int      planSPU;
    private int      finishSPU;
    private int      planSuppliy;
    private int      finishSuppliy;
    private String   org;


    public SuppliyChainOrgVo(int planSKU, int finishSKU, int planSPU, int finishSPU, int planSuppliy, int finishSuppliy, String org) {

        this.planSKU = planSKU;
        this.finishSKU = finishSKU;
        this.planSPU = planSPU;
        this.finishSPU = finishSPU;
        this.planSuppliy = planSuppliy;
        this.finishSuppliy = finishSuppliy;
        this.org = org;
    }
    public SuppliyChainOrgVo(BigDecimal planSKU, BigDecimal finishSKU, BigDecimal planSPU, BigDecimal finishSPU, BigDecimal planSuppliy, BigDecimal finishSuppliy, String org) {

        this.planSKU = planSKU.intValue();
        this.finishSKU = finishSKU.intValue();
        this.planSPU = planSPU.intValue();
        this.finishSPU = finishSPU.intValue();
        this.planSuppliy = planSuppliy.intValue();
        this.finishSuppliy = finishSuppliy.intValue();
        this.org = org;
    }
    public int getPlanSKU() {
        return planSKU;
    }

    public void setPlanSKU(int planSKU) {
        this.planSKU = planSKU;
    }

    public int getFinishSKU() {
        return finishSKU;
    }

    public void setFinishSKU(int finishSKU) {
        this.finishSKU = finishSKU;
    }

    public int getPlanSPU() {
        return planSPU;
    }

    public void setPlanSPU(int planSPU) {
        this.planSPU = planSPU;
    }

    public int getFinishSPU() {
        return finishSPU;
    }

    public void setFinishSPU(int finishSPU) {
        this.finishSPU = finishSPU;
    }

    public int getPlanSuppliy() {
        return planSuppliy;
    }

    public void setPlanSuppliy(int planSuppliy) {
        this.planSuppliy = planSuppliy;
    }

    public int getFinishSuppliy() {
        return finishSuppliy;
    }

    public void setFinishSuppliy(int finishSuppliy) {
        this.finishSuppliy = finishSuppliy;
    }

    public String getOrg() {
        return org;
    }

    public void setOrg(String org) {
        this.org = org;
    }




    @Override
    public String toString() {
        return "SuppliyChainOrgVo{" +
                "planSKU=" + planSKU +
                ", finishSKU=" + finishSKU +
                ", planSPU=" + planSPU +
                ", finishSPU=" + finishSPU +
                ", planSuppliy=" + planSuppliy +
                ", finishSuppliy=" + finishSuppliy +
                ", org='" + org + '\'' +
                '}';
    }
}
