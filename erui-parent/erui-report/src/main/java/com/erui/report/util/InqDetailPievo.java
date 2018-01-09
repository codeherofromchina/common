package com.erui.report.util;

import java.math.BigDecimal;

/*
* 客户中心-询单详细分析-询单明细饼图vo
* */
public class InqDetailPievo {
    private String quoteStatus;//询单状态
    private String[] rtnDescrList;//退回说明列表
    private  BigDecimal[] rtnDecrCountList;//退回说明数量列表
    private String[] finishQuoteList;//已完成状态列表
    private  BigDecimal[] finishQuoteCountList;//已完成状态数量列表
    private String[] quotingQuoteList;//说明列表
    private  BigDecimal[] quotingQuoteCountList;//说明数量列表
    private  String[] orgList;//事业部列表
    private  BigDecimal[] orgCountList;//事业部数量列表
    private  String[] areaList;//地区列表
    private  BigDecimal[] areaCountList;//地区数量列表

    public String getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(String quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public String[] getRtnDescrList() {
        return rtnDescrList;
    }

    public void setRtnDescrList(String[] rtnDescrList) {
        this.rtnDescrList = rtnDescrList;
    }

    public BigDecimal[] getRtnDecrCountList() {
        return rtnDecrCountList;
    }

    public void setRtnDecrCountList(BigDecimal[] rtnDecrCountList) {
        this.rtnDecrCountList = rtnDecrCountList;
    }

    public String[] getFinishQuoteList() {
        return finishQuoteList;
    }

    public void setFinishQuoteList(String[] finishQuoteList) {
        this.finishQuoteList = finishQuoteList;
    }

    public BigDecimal[] getFinishQuoteCountList() {
        return finishQuoteCountList;
    }

    public void setFinishQuoteCountList(BigDecimal[] finishQuoteCountList) {
        this.finishQuoteCountList = finishQuoteCountList;
    }

    public String[] getQuotingQuoteList() {
        return quotingQuoteList;
    }

    public void setQuotingQuoteList(String[] quotingQuoteList) {
        this.quotingQuoteList = quotingQuoteList;
    }

    public BigDecimal[] getQuotingQuoteCountList() {
        return quotingQuoteCountList;
    }

    public void setQuotingQuoteCountList(BigDecimal[] quotingQuoteCountList) {
        this.quotingQuoteCountList = quotingQuoteCountList;
    }

    public String[] getOrgList() {
        return orgList;
    }

    public void setOrgList(String[] orgList) {
        this.orgList = orgList;
    }

    public BigDecimal[] getOrgCountList() {
        return orgCountList;
    }

    public void setOrgCountList(BigDecimal[] orgCountList) {
        this.orgCountList = orgCountList;
    }

    public String[] getAreaList() {
        return areaList;
    }

    public void setAreaList(String[] areaList) {
        this.areaList = areaList;
    }

    public BigDecimal[] getAreaCountList() {
        return areaCountList;
    }

    public void setAreaCountList(BigDecimal[] areaCountList) {
        this.areaCountList = areaCountList;
    }
}
