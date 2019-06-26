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

        public Criteria andDeliverConsignIdIsNull() {
            addCriterion("deliver_consign_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdIsNotNull() {
            addCriterion("deliver_consign_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdEqualTo(Integer value) {
            addCriterion("deliver_consign_id =", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotEqualTo(Integer value) {
            addCriterion("deliver_consign_id <>", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdGreaterThan(Integer value) {
            addCriterion("deliver_consign_id >", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_id >=", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdLessThan(Integer value) {
            addCriterion("deliver_consign_id <", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_id <=", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdIn(List<Integer> values) {
            addCriterion("deliver_consign_id in", values, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotIn(List<Integer> values) {
            addCriterion("deliver_consign_id not in", values, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_id between", value1, value2, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_id not between", value1, value2, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIsNull() {
            addCriterion("deliver_consign_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIsNotNull() {
            addCriterion("deliver_consign_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoEqualTo(String value) {
            addCriterion("deliver_consign_no =", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotEqualTo(String value) {
            addCriterion("deliver_consign_no <>", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThan(String value) {
            addCriterion("deliver_consign_no >", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no >=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThan(String value) {
            addCriterion("deliver_consign_no <", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no <=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLike(String value) {
            addCriterion("deliver_consign_no like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotLike(String value) {
            addCriterion("deliver_consign_no not like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIn(List<String> values) {
            addCriterion("deliver_consign_no in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotIn(List<String> values) {
            addCriterion("deliver_consign_no not in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoBetween(String value1, String value2) {
            addCriterion("deliver_consign_no between", value1, value2, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotBetween(String value1, String value2) {
            addCriterion("deliver_consign_no not between", value1, value2, "deliverConsignNo");
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

        public Criteria andProjectNoIsNull() {
            addCriterion("project_no is null");
            return (Criteria) this;
        }

        public Criteria andProjectNoIsNotNull() {
            addCriterion("project_no is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNoEqualTo(String value) {
            addCriterion("project_no =", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotEqualTo(String value) {
            addCriterion("project_no <>", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThan(String value) {
            addCriterion("project_no >", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoGreaterThanOrEqualTo(String value) {
            addCriterion("project_no >=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThan(String value) {
            addCriterion("project_no <", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLessThanOrEqualTo(String value) {
            addCriterion("project_no <=", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoLike(String value) {
            addCriterion("project_no like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotLike(String value) {
            addCriterion("project_no not like", value, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoIn(List<String> values) {
            addCriterion("project_no in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotIn(List<String> values) {
            addCriterion("project_no not in", values, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoBetween(String value1, String value2) {
            addCriterion("project_no between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andProjectNoNotBetween(String value1, String value2) {
            addCriterion("project_no not between", value1, value2, "projectNo");
            return (Criteria) this;
        }

        public Criteria andContractNoIsNull() {
            addCriterion("contract_no is null");
            return (Criteria) this;
        }

        public Criteria andContractNoIsNotNull() {
            addCriterion("contract_no is not null");
            return (Criteria) this;
        }

        public Criteria andContractNoEqualTo(String value) {
            addCriterion("contract_no =", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotEqualTo(String value) {
            addCriterion("contract_no <>", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThan(String value) {
            addCriterion("contract_no >", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoGreaterThanOrEqualTo(String value) {
            addCriterion("contract_no >=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThan(String value) {
            addCriterion("contract_no <", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLessThanOrEqualTo(String value) {
            addCriterion("contract_no <=", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoLike(String value) {
            addCriterion("contract_no like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotLike(String value) {
            addCriterion("contract_no not like", value, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoIn(List<String> values) {
            addCriterion("contract_no in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotIn(List<String> values) {
            addCriterion("contract_no not in", values, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoBetween(String value1, String value2) {
            addCriterion("contract_no between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andContractNoNotBetween(String value1, String value2) {
            addCriterion("contract_no not between", value1, value2, "contractNo");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIsNull() {
            addCriterion("operation_specialist_id is null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIsNotNull() {
            addCriterion("operation_specialist_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdEqualTo(Integer value) {
            addCriterion("operation_specialist_id =", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotEqualTo(Integer value) {
            addCriterion("operation_specialist_id <>", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdGreaterThan(Integer value) {
            addCriterion("operation_specialist_id >", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("operation_specialist_id >=", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdLessThan(Integer value) {
            addCriterion("operation_specialist_id <", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdLessThanOrEqualTo(Integer value) {
            addCriterion("operation_specialist_id <=", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIn(List<Integer> values) {
            addCriterion("operation_specialist_id in", values, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotIn(List<Integer> values) {
            addCriterion("operation_specialist_id not in", values, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdBetween(Integer value1, Integer value2) {
            addCriterion("operation_specialist_id between", value1, value2, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotBetween(Integer value1, Integer value2) {
            addCriterion("operation_specialist_id not between", value1, value2, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIsNull() {
            addCriterion("operation_specialist is null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIsNotNull() {
            addCriterion("operation_specialist is not null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistEqualTo(String value) {
            addCriterion("operation_specialist =", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotEqualTo(String value) {
            addCriterion("operation_specialist <>", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistGreaterThan(String value) {
            addCriterion("operation_specialist >", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistGreaterThanOrEqualTo(String value) {
            addCriterion("operation_specialist >=", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLessThan(String value) {
            addCriterion("operation_specialist <", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLessThanOrEqualTo(String value) {
            addCriterion("operation_specialist <=", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLike(String value) {
            addCriterion("operation_specialist like", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotLike(String value) {
            addCriterion("operation_specialist not like", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIn(List<String> values) {
            addCriterion("operation_specialist in", values, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotIn(List<String> values) {
            addCriterion("operation_specialist not in", values, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistBetween(String value1, String value2) {
            addCriterion("operation_specialist between", value1, value2, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotBetween(String value1, String value2) {
            addCriterion("operation_specialist not between", value1, value2, "operationSpecialist");
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

        public Criteria andBusinessSketchIsNull() {
            addCriterion("business_sketch is null");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchIsNotNull() {
            addCriterion("business_sketch is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchEqualTo(String value) {
            addCriterion("business_sketch =", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotEqualTo(String value) {
            addCriterion("business_sketch <>", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchGreaterThan(String value) {
            addCriterion("business_sketch >", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchGreaterThanOrEqualTo(String value) {
            addCriterion("business_sketch >=", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLessThan(String value) {
            addCriterion("business_sketch <", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLessThanOrEqualTo(String value) {
            addCriterion("business_sketch <=", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLike(String value) {
            addCriterion("business_sketch like", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotLike(String value) {
            addCriterion("business_sketch not like", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchIn(List<String> values) {
            addCriterion("business_sketch in", values, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotIn(List<String> values) {
            addCriterion("business_sketch not in", values, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchBetween(String value1, String value2) {
            addCriterion("business_sketch between", value1, value2, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotBetween(String value1, String value2) {
            addCriterion("business_sketch not between", value1, value2, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIsNull() {
            addCriterion("goods_deposit_place is null");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIsNotNull() {
            addCriterion("goods_deposit_place is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceEqualTo(String value) {
            addCriterion("goods_deposit_place =", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotEqualTo(String value) {
            addCriterion("goods_deposit_place <>", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceGreaterThan(String value) {
            addCriterion("goods_deposit_place >", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("goods_deposit_place >=", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLessThan(String value) {
            addCriterion("goods_deposit_place <", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLessThanOrEqualTo(String value) {
            addCriterion("goods_deposit_place <=", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLike(String value) {
            addCriterion("goods_deposit_place like", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotLike(String value) {
            addCriterion("goods_deposit_place not like", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIn(List<String> values) {
            addCriterion("goods_deposit_place in", values, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotIn(List<String> values) {
            addCriterion("goods_deposit_place not in", values, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceBetween(String value1, String value2) {
            addCriterion("goods_deposit_place between", value1, value2, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotBetween(String value1, String value2) {
            addCriterion("goods_deposit_place not between", value1, value2, "goodsDepositPlace");
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

        public Criteria andNumbersIsNull() {
            addCriterion("numbers is null");
            return (Criteria) this;
        }

        public Criteria andNumbersIsNotNull() {
            addCriterion("numbers is not null");
            return (Criteria) this;
        }

        public Criteria andNumbersEqualTo(Integer value) {
            addCriterion("numbers =", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersNotEqualTo(Integer value) {
            addCriterion("numbers <>", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersGreaterThan(Integer value) {
            addCriterion("numbers >", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersGreaterThanOrEqualTo(Integer value) {
            addCriterion("numbers >=", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersLessThan(Integer value) {
            addCriterion("numbers <", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersLessThanOrEqualTo(Integer value) {
            addCriterion("numbers <=", value, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersIn(List<Integer> values) {
            addCriterion("numbers in", values, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersNotIn(List<Integer> values) {
            addCriterion("numbers not in", values, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersBetween(Integer value1, Integer value2) {
            addCriterion("numbers between", value1, value2, "numbers");
            return (Criteria) this;
        }

        public Criteria andNumbersNotBetween(Integer value1, Integer value2) {
            addCriterion("numbers not between", value1, value2, "numbers");
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

        public Criteria andTransportTypeIsNull() {
            addCriterion("transport_type is null");
            return (Criteria) this;
        }

        public Criteria andTransportTypeIsNotNull() {
            addCriterion("transport_type is not null");
            return (Criteria) this;
        }

        public Criteria andTransportTypeEqualTo(String value) {
            addCriterion("transport_type =", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeNotEqualTo(String value) {
            addCriterion("transport_type <>", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeGreaterThan(String value) {
            addCriterion("transport_type >", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeGreaterThanOrEqualTo(String value) {
            addCriterion("transport_type >=", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeLessThan(String value) {
            addCriterion("transport_type <", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeLessThanOrEqualTo(String value) {
            addCriterion("transport_type <=", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeLike(String value) {
            addCriterion("transport_type like", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeNotLike(String value) {
            addCriterion("transport_type not like", value, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeIn(List<String> values) {
            addCriterion("transport_type in", values, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeNotIn(List<String> values) {
            addCriterion("transport_type not in", values, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeBetween(String value1, String value2) {
            addCriterion("transport_type between", value1, value2, "transportType");
            return (Criteria) this;
        }

        public Criteria andTransportTypeNotBetween(String value1, String value2) {
            addCriterion("transport_type not between", value1, value2, "transportType");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNull() {
            addCriterion("arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNotNull() {
            addCriterion("arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date =", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date <>", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThan(Date value) {
            addCriterionForJDBCDate("arrival_date >", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date >=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThan(Date value) {
            addCriterionForJDBCDate("arrival_date <", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date <=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIn(List<Date> values) {
            addCriterionForJDBCDate("arrival_date in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrival_date not in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrival_date between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrival_date not between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyIsNull() {
            addCriterion("order_emergency is null");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyIsNotNull() {
            addCriterion("order_emergency is not null");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyEqualTo(Integer value) {
            addCriterion("order_emergency =", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyNotEqualTo(Integer value) {
            addCriterion("order_emergency <>", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyGreaterThan(Integer value) {
            addCriterion("order_emergency >", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_emergency >=", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyLessThan(Integer value) {
            addCriterion("order_emergency <", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyLessThanOrEqualTo(Integer value) {
            addCriterion("order_emergency <=", value, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyIn(List<Integer> values) {
            addCriterion("order_emergency in", values, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyNotIn(List<Integer> values) {
            addCriterion("order_emergency not in", values, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyBetween(Integer value1, Integer value2) {
            addCriterion("order_emergency between", value1, value2, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andOrderEmergencyNotBetween(Integer value1, Integer value2) {
            addCriterion("order_emergency not between", value1, value2, "orderEmergency");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameIsNull() {
            addCriterion("crm_code_or_name is null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameIsNotNull() {
            addCriterion("crm_code_or_name is not null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameEqualTo(String value) {
            addCriterion("crm_code_or_name =", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotEqualTo(String value) {
            addCriterion("crm_code_or_name <>", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameGreaterThan(String value) {
            addCriterion("crm_code_or_name >", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameGreaterThanOrEqualTo(String value) {
            addCriterion("crm_code_or_name >=", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLessThan(String value) {
            addCriterion("crm_code_or_name <", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLessThanOrEqualTo(String value) {
            addCriterion("crm_code_or_name <=", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLike(String value) {
            addCriterion("crm_code_or_name like", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotLike(String value) {
            addCriterion("crm_code_or_name not like", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameIn(List<String> values) {
            addCriterion("crm_code_or_name in", values, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotIn(List<String> values) {
            addCriterion("crm_code_or_name not in", values, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameBetween(String value1, String value2) {
            addCriterion("crm_code_or_name between", value1, value2, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotBetween(String value1, String value2) {
            addCriterion("crm_code_or_name not between", value1, value2, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
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

        public Criteria andHandleStatusIsNull() {
            addCriterion("handle_status is null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIsNotNull() {
            addCriterion("handle_status is not null");
            return (Criteria) this;
        }

        public Criteria andHandleStatusEqualTo(Integer value) {
            addCriterion("handle_status =", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotEqualTo(Integer value) {
            addCriterion("handle_status <>", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThan(Integer value) {
            addCriterion("handle_status >", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("handle_status >=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThan(Integer value) {
            addCriterion("handle_status <", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusLessThanOrEqualTo(Integer value) {
            addCriterion("handle_status <=", value, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusIn(List<Integer> values) {
            addCriterion("handle_status in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotIn(List<Integer> values) {
            addCriterion("handle_status not in", values, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusBetween(Integer value1, Integer value2) {
            addCriterion("handle_status between", value1, value2, "handleStatus");
            return (Criteria) this;
        }

        public Criteria andHandleStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("handle_status not between", value1, value2, "handleStatus");
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

        public Criteria andTenantIsNull() {
            addCriterion("tenant is null");
            return (Criteria) this;
        }

        public Criteria andTenantIsNotNull() {
            addCriterion("tenant is not null");
            return (Criteria) this;
        }

        public Criteria andTenantEqualTo(String value) {
            addCriterion("tenant =", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantNotEqualTo(String value) {
            addCriterion("tenant <>", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantGreaterThan(String value) {
            addCriterion("tenant >", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantGreaterThanOrEqualTo(String value) {
            addCriterion("tenant >=", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantLessThan(String value) {
            addCriterion("tenant <", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantLessThanOrEqualTo(String value) {
            addCriterion("tenant <=", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantLike(String value) {
            addCriterion("tenant like", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantNotLike(String value) {
            addCriterion("tenant not like", value, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantIn(List<String> values) {
            addCriterion("tenant in", values, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantNotIn(List<String> values) {
            addCriterion("tenant not in", values, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantBetween(String value1, String value2) {
            addCriterion("tenant between", value1, value2, "tenant");
            return (Criteria) this;
        }

        public Criteria andTenantNotBetween(String value1, String value2) {
            addCriterion("tenant not between", value1, value2, "tenant");
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