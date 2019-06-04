package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CheckLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CheckLogExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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

        public Criteria andAuditingProcessIsNull() {
            addCriterion("auditing_process is null");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessIsNotNull() {
            addCriterion("auditing_process is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessEqualTo(String value) {
            addCriterion("auditing_process =", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessNotEqualTo(String value) {
            addCriterion("auditing_process <>", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessGreaterThan(String value) {
            addCriterion("auditing_process >", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_process >=", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessLessThan(String value) {
            addCriterion("auditing_process <", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessLessThanOrEqualTo(String value) {
            addCriterion("auditing_process <=", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessLike(String value) {
            addCriterion("auditing_process like", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessNotLike(String value) {
            addCriterion("auditing_process not like", value, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessIn(List<String> values) {
            addCriterion("auditing_process in", values, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessNotIn(List<String> values) {
            addCriterion("auditing_process not in", values, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessBetween(String value1, String value2) {
            addCriterion("auditing_process between", value1, value2, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingProcessNotBetween(String value1, String value2) {
            addCriterion("auditing_process not between", value1, value2, "auditingProcess");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIsNull() {
            addCriterion("auditing_user_id is null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIsNotNull() {
            addCriterion("auditing_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdEqualTo(Integer value) {
            addCriterion("auditing_user_id =", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotEqualTo(Integer value) {
            addCriterion("auditing_user_id <>", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThan(Integer value) {
            addCriterion("auditing_user_id >", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("auditing_user_id >=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThan(Integer value) {
            addCriterion("auditing_user_id <", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("auditing_user_id <=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIn(List<Integer> values) {
            addCriterion("auditing_user_id in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotIn(List<Integer> values) {
            addCriterion("auditing_user_id not in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdBetween(Integer value1, Integer value2) {
            addCriterion("auditing_user_id between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("auditing_user_id not between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameIsNull() {
            addCriterion("auditing_user_name is null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameIsNotNull() {
            addCriterion("auditing_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameEqualTo(String value) {
            addCriterion("auditing_user_name =", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameNotEqualTo(String value) {
            addCriterion("auditing_user_name <>", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameGreaterThan(String value) {
            addCriterion("auditing_user_name >", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_user_name >=", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameLessThan(String value) {
            addCriterion("auditing_user_name <", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameLessThanOrEqualTo(String value) {
            addCriterion("auditing_user_name <=", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameLike(String value) {
            addCriterion("auditing_user_name like", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameNotLike(String value) {
            addCriterion("auditing_user_name not like", value, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameIn(List<String> values) {
            addCriterion("auditing_user_name in", values, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameNotIn(List<String> values) {
            addCriterion("auditing_user_name not in", values, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameBetween(String value1, String value2) {
            addCriterion("auditing_user_name between", value1, value2, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNameNotBetween(String value1, String value2) {
            addCriterion("auditing_user_name not between", value1, value2, "auditingUserName");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessIsNull() {
            addCriterion("next_auditing_process is null");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessIsNotNull() {
            addCriterion("next_auditing_process is not null");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessEqualTo(String value) {
            addCriterion("next_auditing_process =", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessNotEqualTo(String value) {
            addCriterion("next_auditing_process <>", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessGreaterThan(String value) {
            addCriterion("next_auditing_process >", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessGreaterThanOrEqualTo(String value) {
            addCriterion("next_auditing_process >=", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessLessThan(String value) {
            addCriterion("next_auditing_process <", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessLessThanOrEqualTo(String value) {
            addCriterion("next_auditing_process <=", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessLike(String value) {
            addCriterion("next_auditing_process like", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessNotLike(String value) {
            addCriterion("next_auditing_process not like", value, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessIn(List<String> values) {
            addCriterion("next_auditing_process in", values, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessNotIn(List<String> values) {
            addCriterion("next_auditing_process not in", values, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessBetween(String value1, String value2) {
            addCriterion("next_auditing_process between", value1, value2, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingProcessNotBetween(String value1, String value2) {
            addCriterion("next_auditing_process not between", value1, value2, "nextAuditingProcess");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdIsNull() {
            addCriterion("next_auditing_user_id is null");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdIsNotNull() {
            addCriterion("next_auditing_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdEqualTo(String value) {
            addCriterion("next_auditing_user_id =", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdNotEqualTo(String value) {
            addCriterion("next_auditing_user_id <>", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdGreaterThan(String value) {
            addCriterion("next_auditing_user_id >", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("next_auditing_user_id >=", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdLessThan(String value) {
            addCriterion("next_auditing_user_id <", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdLessThanOrEqualTo(String value) {
            addCriterion("next_auditing_user_id <=", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdLike(String value) {
            addCriterion("next_auditing_user_id like", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdNotLike(String value) {
            addCriterion("next_auditing_user_id not like", value, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdIn(List<String> values) {
            addCriterion("next_auditing_user_id in", values, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdNotIn(List<String> values) {
            addCriterion("next_auditing_user_id not in", values, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdBetween(String value1, String value2) {
            addCriterion("next_auditing_user_id between", value1, value2, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andNextAuditingUserIdNotBetween(String value1, String value2) {
            addCriterion("next_auditing_user_id not between", value1, value2, "nextAuditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgIsNull() {
            addCriterion("auditing_msg is null");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgIsNotNull() {
            addCriterion("auditing_msg is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgEqualTo(String value) {
            addCriterion("auditing_msg =", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgNotEqualTo(String value) {
            addCriterion("auditing_msg <>", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgGreaterThan(String value) {
            addCriterion("auditing_msg >", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_msg >=", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgLessThan(String value) {
            addCriterion("auditing_msg <", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgLessThanOrEqualTo(String value) {
            addCriterion("auditing_msg <=", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgLike(String value) {
            addCriterion("auditing_msg like", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgNotLike(String value) {
            addCriterion("auditing_msg not like", value, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgIn(List<String> values) {
            addCriterion("auditing_msg in", values, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgNotIn(List<String> values) {
            addCriterion("auditing_msg not in", values, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgBetween(String value1, String value2) {
            addCriterion("auditing_msg between", value1, value2, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andAuditingMsgNotBetween(String value1, String value2) {
            addCriterion("auditing_msg not between", value1, value2, "auditingMsg");
            return (Criteria) this;
        }

        public Criteria andOperationIsNull() {
            addCriterion("operation is null");
            return (Criteria) this;
        }

        public Criteria andOperationIsNotNull() {
            addCriterion("operation is not null");
            return (Criteria) this;
        }

        public Criteria andOperationEqualTo(String value) {
            addCriterion("operation =", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotEqualTo(String value) {
            addCriterion("operation <>", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThan(String value) {
            addCriterion("operation >", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThanOrEqualTo(String value) {
            addCriterion("operation >=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThan(String value) {
            addCriterion("operation <", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThanOrEqualTo(String value) {
            addCriterion("operation <=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLike(String value) {
            addCriterion("operation like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotLike(String value) {
            addCriterion("operation not like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationIn(List<String> values) {
            addCriterion("operation in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotIn(List<String> values) {
            addCriterion("operation not in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationBetween(String value1, String value2) {
            addCriterion("operation between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotBetween(String value1, String value2) {
            addCriterion("operation not between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Integer value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Integer value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Integer value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Integer value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Integer value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Integer> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Integer> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Integer value1, Integer value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andJoinIdIsNull() {
            addCriterion("join_id is null");
            return (Criteria) this;
        }

        public Criteria andJoinIdIsNotNull() {
            addCriterion("join_id is not null");
            return (Criteria) this;
        }

        public Criteria andJoinIdEqualTo(Integer value) {
            addCriterion("join_id =", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdNotEqualTo(Integer value) {
            addCriterion("join_id <>", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdGreaterThan(Integer value) {
            addCriterion("join_id >", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("join_id >=", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdLessThan(Integer value) {
            addCriterion("join_id <", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdLessThanOrEqualTo(Integer value) {
            addCriterion("join_id <=", value, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdIn(List<Integer> values) {
            addCriterion("join_id in", values, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdNotIn(List<Integer> values) {
            addCriterion("join_id not in", values, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdBetween(Integer value1, Integer value2) {
            addCriterion("join_id between", value1, value2, "joinId");
            return (Criteria) this;
        }

        public Criteria andJoinIdNotBetween(Integer value1, Integer value2) {
            addCriterion("join_id not between", value1, value2, "joinId");
            return (Criteria) this;
        }

        public Criteria andAuditSeqIsNull() {
            addCriterion("audit_seq is null");
            return (Criteria) this;
        }

        public Criteria andAuditSeqIsNotNull() {
            addCriterion("audit_seq is not null");
            return (Criteria) this;
        }

        public Criteria andAuditSeqEqualTo(BigDecimal value) {
            addCriterion("audit_seq =", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqNotEqualTo(BigDecimal value) {
            addCriterion("audit_seq <>", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqGreaterThan(BigDecimal value) {
            addCriterion("audit_seq >", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("audit_seq >=", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqLessThan(BigDecimal value) {
            addCriterion("audit_seq <", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqLessThanOrEqualTo(BigDecimal value) {
            addCriterion("audit_seq <=", value, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqIn(List<BigDecimal> values) {
            addCriterion("audit_seq in", values, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqNotIn(List<BigDecimal> values) {
            addCriterion("audit_seq not in", values, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("audit_seq between", value1, value2, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andAuditSeqNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("audit_seq not between", value1, value2, "auditSeq");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
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