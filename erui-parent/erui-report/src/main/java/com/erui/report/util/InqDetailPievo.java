package com.erui.report.util;

import java.math.BigDecimal;

/*
* 客户中心-询单详细分析-询单明细饼图vo
* */
public class InqDetailPievo {
    private String quoteStatus;//询单状态
    private String[] rtnDescrList;//退回说明列表
    private  Integer[] rtnDecrCountList;//退回说明数量列表
    private String[] finishQuoteList;//已完成状态列表
    private  Integer[] finishQuoteCountList;//已完成状态数量列表
    private String[] quotingQuoteList;//说明列表
    private  Integer[] quotingQuoteCountList;//说明数量列表
    private  String[] orgList;//事业部列表
    private  Integer[] orgCountList;//事业部数量列表
    private  Double[] orgProportionList;//事业部数量列表
    private  String[] areaList;//地区列表
    private  Integer[] areaCountList;//地区数量列表
    private  Double[] areaProportionList;//地区数量列表

    public String getQuoteStatus() {
        return quoteStatus;
    }

    public void setQuoteStatus(String quoteStatus) {
        this.quoteStatus = quoteStatus;
    }

    public Double[] getOrgProportionList() {
        return orgProportionList;
    }

    public void setOrgProportionList(Double[] orgProportionList) {
        this.orgProportionList = orgProportionList;
    }

    public Double[] getAreaProportionList() {
        return areaProportionList;
    }

    public void setAreaProportionList(Double[] areaProportionList) {
        this.areaProportionList = areaProportionList;
    }

    public String[] getRtnDescrList() {
        return rtnDescrList;
    }

    public void setRtnDescrList(String[] rtnDescrList) {
        this.rtnDescrList = rtnDescrList;
    }

    public Integer[] getRtnDecrCountList() {
        return rtnDecrCountList;
    }

    public void setRtnDecrCountList(Integer[] rtnDecrCountList) {
        this.rtnDecrCountList = rtnDecrCountList;
    }

    public String[] getFinishQuoteList() {
        return finishQuoteList;
    }

    public void setFinishQuoteList(String[] finishQuoteList) {
        this.finishQuoteList = finishQuoteList;
    }

    public Integer[] getFinishQuoteCountList() {
        return finishQuoteCountList;
    }

    public void setFinishQuoteCountList(Integer[] finishQuoteCountList) {
        this.finishQuoteCountList = finishQuoteCountList;
    }

    public String[] getQuotingQuoteList() {
        return quotingQuoteList;
    }

    public void setQuotingQuoteList(String[] quotingQuoteList) {
        this.quotingQuoteList = quotingQuoteList;
    }

    public Integer[] getQuotingQuoteCountList() {
        return quotingQuoteCountList;
    }

    public void setQuotingQuoteCountList(Integer[] quotingQuoteCountList) {
        this.quotingQuoteCountList = quotingQuoteCountList;
    }

    public String[] getOrgList() {
        return orgList;
    }

    public void setOrgList(String[] orgList) {
        this.orgList = orgList;
    }

    public Integer[] getOrgCountList() {
        return orgCountList;
    }

    public void setOrgCountList(Integer[] orgCountList) {
        this.orgCountList = orgCountList;
    }

    public String[] getAreaList() {
        return areaList;
    }

    public void setAreaList(String[] areaList) {
        this.areaList = areaList;
    }

    public Integer[] getAreaCountList() {
        return areaCountList;
    }

    public void setAreaCountList(Integer[] areaCountList) {
        this.areaCountList = areaCountList;
    }
}
