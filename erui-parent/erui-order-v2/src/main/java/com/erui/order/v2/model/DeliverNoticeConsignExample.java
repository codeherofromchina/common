package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.List;

public class DeliverNoticeConsignExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeliverNoticeConsignExample() {
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

        public Criteria andDeliverNoticeIdIsNull() {
            addCriterion("deliver_notice_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdIsNotNull() {
            addCriterion("deliver_notice_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdEqualTo(Integer value) {
            addCriterion("deliver_notice_id =", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotEqualTo(Integer value) {
            addCriterion("deliver_notice_id <>", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdGreaterThan(Integer value) {
            addCriterion("deliver_notice_id >", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_notice_id >=", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdLessThan(Integer value) {
            addCriterion("deliver_notice_id <", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_notice_id <=", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdIn(List<Integer> values) {
            addCriterion("deliver_notice_id in", values, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotIn(List<Integer> values) {
            addCriterion("deliver_notice_id not in", values, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdBetween(Integer value1, Integer value2) {
            addCriterion("deliver_notice_id between", value1, value2, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_notice_id not between", value1, value2, "deliverNoticeId");
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