package com.erui.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 配置信息
 * Created by wangxiaodan on 2019/1/22.
 */
@Component
public class OrderConf implements InitializingBean {
    @Value("#{orderProp[CRM_URL]}")
    private String crmUrl; // CRM接口调用地址

    @Value("#{orderProp[MEMBER_INFORMATION]}")
    private String memberInformation; // 查询人员信息调用接口

    @Value("#{orderProp[SEND_SMS]}")
    private String sendSms; // 发短信接口

    @Value("#{orderProp[DING_SEND_SMS]}")
    private String dingSendSms; // 发钉钉通知接口

    @Value("#{orderProp[MEMBER_LIST]}")
    private String memberList; // 用户列表

    @Value("#{orderProp[SSO_USER]}")
    private String ssoUser;  // 从SSO获取登录用户

    @Value("#{orderProp[CREDIT_EXTENSION]}")
    private String creditExtension; // 授信服务器地址

    @Value("#{orderProp[MEMBER_POINTS]}")
    private String memberPoints; // 会员积分服务器地址

    @Value("#{orderProp[ACTIVITI_URL]}")
    private String activitiUrl; // 业务流系统地址

    @Value("#{orderProp[BPM_KEY]}")
    private String bpmKey;

    public String getCrmUrl() {
        return crmUrl;
    }

    public void setCrmUrl(String crmUrl) {
        this.crmUrl = crmUrl;
    }

    public String getMemberInformation() {
        return memberInformation;
    }

    public void setMemberInformation(String memberInformation) {
        this.memberInformation = memberInformation;
    }

    public String getSendSms() {
        return sendSms;
    }

    public void setSendSms(String sendSms) {
        this.sendSms = sendSms;
    }

    public String getDingSendSms() {
        return dingSendSms;
    }

    public void setDingSendSms(String dingSendSms) {
        this.dingSendSms = dingSendSms;
    }

    public String getMemberList() {
        return memberList;
    }

    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }

    public String getSsoUser() {
        return ssoUser;
    }

    public void setSsoUser(String ssoUser) {
        this.ssoUser = ssoUser;
    }

    public String getCreditExtension() {
        return creditExtension;
    }

    public void setCreditExtension(String creditExtension) {
        this.creditExtension = creditExtension;
    }

    public String getMemberPoints() {
        return memberPoints;
    }

    public void setMemberPoints(String memberPoints) {
        this.memberPoints = memberPoints;
    }

    public String getActivitiUrl() {
        return activitiUrl;
    }

    public void setActivitiUrl(String activitiUrl) {
        this.activitiUrl = activitiUrl;
    }

    public String getBpmKey() {
        return bpmKey;
    }

    public void setBpmKey(String bpmKey) {
        this.bpmKey = bpmKey;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        orderConf = this;
    }

    public static OrderConf getInstance() {
        return orderConf;
    }
    private static OrderConf orderConf;
}
