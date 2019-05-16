package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeliverNoticeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeliverNoticeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoIsNull() {
            addCriterion("deliver_notice_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoIsNotNull() {
            addCriterion("deliver_notice_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoEqualTo(String value) {
            addCriterion("deliver_notice_no =", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoNotEqualTo(String value) {
            addCriterion("deliver_notice_no <>", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoGreaterThan(String value) {
            addCriterion("deliver_notice_no >", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_notice_no >=", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoLessThan(String value) {
            addCriterion("deliver_notice_no <", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoLessThanOrEqualTo(String value) {
            addCriterion("deliver_notice_no <=", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoLike(String value) {
            addCriterion("deliver_notice_no like", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoNotLike(String value) {
            addCriterion("deliver_notice_no not like", value, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoIn(List<String> values) {
            addCriterion("deliver_notice_no in", values, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoNotIn(List<String> values) {
            addCriterion("deliver_notice_no not in", values, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoBetween(String value1, String value2) {
            addCriterion("deliver_notice_no between", value1, value2, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeNoNotBetween(String value1, String value2) {
            addCriterion("deliver_notice_no not between", value1, value2, "deliverNoticeNo");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNull() {
            addCriterion("sender_id is null");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNotNull() {
            addCriterion("sender_id is not null");
            return (Criteria) this;
        }

        public Criteria andSenderIdEqualTo(Integer value) {
            addCriterion("sender_id =", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotEqualTo(Integer value) {
            addCriterion("sender_id <>", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThan(Integer value) {
            addCriterion("sender_id >", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_id >=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThan(Integer value) {
            addCriterion("sender_id <", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThanOrEqualTo(Integer value) {
            addCriterion("sender_id <=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdIn(List<Integer> values) {
            addCriterion("sender_id in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotIn(List<Integer> values) {
            addCriterion("sender_id not in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdBetween(Integer value1, Integer value2) {
            addCriterion("sender_id between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_id not between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNull() {
            addCriterion("sender_name is null");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNotNull() {
            addCriterion("sender_name is not null");
            return (Criteria) this;
        }

        public Criteria andSenderNameEqualTo(String value) {
            addCriterion("sender_name =", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotEqualTo(String value) {
            addCriterion("sender_name <>", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThan(String value) {
            addCriterion("sender_name >", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThanOrEqualTo(String value) {
            addCriterion("sender_name >=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThan(String value) {
            addCriterion("sender_name <", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThanOrEqualTo(String value) {
            addCriterion("sender_name <=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLike(String value) {
            addCriterion("sender_name like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotLike(String value) {
            addCriterion("sender_name not like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameIn(List<String> values) {
            addCriterion("sender_name in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotIn(List<String> values) {
            addCriterion("sender_name not in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameBetween(String value1, String value2) {
            addCriterion("sender_name between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotBetween(String value1, String value2) {
            addCriterion("sender_name not between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("send_date is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("send_date is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterionForJDBCDate("send_date =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("send_date <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterionForJDBCDate("send_date >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("send_date >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterionForJDBCDate("send_date <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("send_date <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterionForJDBCDate("send_date in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("send_date not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("send_date between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("send_date not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andTradeTermsIsNull() {
            addCriterion("trade_terms is null");
            return (Criteria) this;
        }

        public Criteria andTradeTermsIsNotNull() {
            addCriterion("trade_terms is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTermsEqualTo(String value) {
            addCriterion("trade_terms =", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsNotEqualTo(String value) {
            addCriterion("trade_terms <>", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsGreaterThan(String value) {
            addCriterion("trade_terms >", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsGreaterThanOrEqualTo(String value) {
            addCriterion("trade_terms >=", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsLessThan(String value) {
            addCriterion("trade_terms <", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsLessThanOrEqualTo(String value) {
            addCriterion("trade_terms <=", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsLike(String value) {
            addCriterion("trade_terms like", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsNotLike(String value) {
            addCriterion("trade_terms not like", value, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsIn(List<String> values) {
            addCriterion("trade_terms in", values, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsNotIn(List<String> values) {
            addCriterion("trade_terms not in", values, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsBetween(String value1, String value2) {
            addCriterion("trade_terms between", value1, value2, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andTradeTermsNotBetween(String value1, String value2) {
            addCriterion("trade_terms not between", value1, value2, "tradeTerms");
            return (Criteria) this;
        }

        public Criteria andToPlaceIsNull() {
            addCriterion("to_place is null");
            return (Criteria) this;
        }

        public Criteria andToPlaceIsNotNull() {
            addCriterion("to_place is not null");
            return (Criteria) this;
        }

        public Criteria andToPlaceEqualTo(String value) {
            addCriterion("to_place =", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotEqualTo(String value) {
            addCriterion("to_place <>", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceGreaterThan(String value) {
            addCriterion("to_place >", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("to_place >=", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLessThan(String value) {
            addCriterion("to_place <", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLessThanOrEqualTo(String value) {
            addCriterion("to_place <=", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLike(String value) {
            addCriterion("to_place like", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotLike(String value) {
            addCriterion("to_place not like", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceIn(List<String> values) {
            addCriterion("to_place in", values, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotIn(List<String> values) {
            addCriterion("to_place not in", values, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceBetween(String value1, String value2) {
            addCriterion("to_place between", value1, value2, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotBetween(String value1, String value2) {
            addCriterion("to_place not between", value1, value2, "toPlace");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnIsNull() {
            addCriterion("transport_type_bn is null");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnIsNotNull() {
            addCriterion("transport_type_bn is not null");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnEqualTo(String value) {
            addCriterion("transport_type_bn =", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnNotEqualTo(String value) {
            addCriterion("transport_type_bn <>", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnGreaterThan(String value) {
            addCriterion("transport_type_bn >", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnGreaterThanOrEqualTo(String value) {
            addCriterion("transport_type_bn >=", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnLessThan(String value) {
            addCriterion("transport_type_bn <", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnLessThanOrEqualTo(String value) {
            addCriterion("transport_type_bn <=", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnLike(String value) {
            addCriterion("transport_type_bn like", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnNotLike(String value) {
            addCriterion("transport_type_bn not like", value, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnIn(List<String> values) {
            addCriterion("transport_type_bn in", values, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnNotIn(List<String> values) {
            addCriterion("transport_type_bn not in", values, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnBetween(String value1, String value2) {
            addCriterion("transport_type_bn between", value1, value2, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBnNotBetween(String value1, String value2) {
            addCriterion("transport_type_bn not between", value1, value2, "transportTypeBn");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidIsNull() {
            addCriterion("technical_uid is null");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidIsNotNull() {
            addCriterion("technical_uid is not null");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidEqualTo(Integer value) {
            addCriterion("technical_uid =", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidNotEqualTo(Integer value) {
            addCriterion("technical_uid <>", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidGreaterThan(Integer value) {
            addCriterion("technical_uid >", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("technical_uid >=", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidLessThan(Integer value) {
            addCriterion("technical_uid <", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidLessThanOrEqualTo(Integer value) {
            addCriterion("technical_uid <=", value, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidIn(List<Integer> values) {
            addCriterion("technical_uid in", values, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidNotIn(List<Integer> values) {
            addCriterion("technical_uid not in", values, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidBetween(Integer value1, Integer value2) {
            addCriterion("technical_uid between", value1, value2, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andTechnicalUidNotBetween(Integer value1, Integer value2) {
            addCriterion("technical_uid not between", value1, value2, "technicalUid");
            return (Criteria) this;
        }

        public Criteria andUrgencyIsNull() {
            addCriterion("urgency is null");
            return (Criteria) this;
        }

        public Criteria andUrgencyIsNotNull() {
            addCriterion("urgency is not null");
            return (Criteria) this;
        }

        public Criteria andUrgencyEqualTo(String value) {
            addCriterion("urgency =", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotEqualTo(String value) {
            addCriterion("urgency <>", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyGreaterThan(String value) {
            addCriterion("urgency >", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyGreaterThanOrEqualTo(String value) {
            addCriterion("urgency >=", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLessThan(String value) {
            addCriterion("urgency <", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLessThanOrEqualTo(String value) {
            addCriterion("urgency <=", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyLike(String value) {
            addCriterion("urgency like", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotLike(String value) {
            addCriterion("urgency not like", value, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyIn(List<String> values) {
            addCriterion("urgency in", values, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotIn(List<String> values) {
            addCriterion("urgency not in", values, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyBetween(String value1, String value2) {
            addCriterion("urgency between", value1, value2, "urgency");
            return (Criteria) this;
        }

        public Criteria andUrgencyNotBetween(String value1, String value2) {
            addCriterion("urgency not between", value1, value2, "urgency");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNull() {
            addCriterion("delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNotNull() {
            addCriterion("delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(Date value) {
            addCriterionForJDBCDate("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("delivery_date not between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andNumersIsNull() {
            addCriterion("numers is null");
            return (Criteria) this;
        }

        public Criteria andNumersIsNotNull() {
            addCriterion("numers is not null");
            return (Criteria) this;
        }

        public Criteria andNumersEqualTo(Integer value) {
            addCriterion("numers =", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersNotEqualTo(Integer value) {
            addCriterion("numers <>", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersGreaterThan(Integer value) {
            addCriterion("numers >", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersGreaterThanOrEqualTo(Integer value) {
            addCriterion("numers >=", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersLessThan(Integer value) {
            addCriterion("numers <", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersLessThanOrEqualTo(Integer value) {
            addCriterion("numers <=", value, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersIn(List<Integer> values) {
            addCriterion("numers in", values, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersNotIn(List<Integer> values) {
            addCriterion("numers not in", values, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersBetween(Integer value1, Integer value2) {
            addCriterion("numers between", value1, value2, "numers");
            return (Criteria) this;
        }

        public Criteria andNumersNotBetween(Integer value1, Integer value2) {
            addCriterion("numers not between", value1, value2, "numers");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}