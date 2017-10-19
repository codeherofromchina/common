package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderOutboundCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderOutboundCountExample() {
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

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIsNull() {
            addCriterion("outbound_num is null");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIsNotNull() {
            addCriterion("outbound_num is not null");
            return (Criteria) this;
        }

        public Criteria andOutboundNumEqualTo(String value) {
            addCriterion("outbound_num =", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotEqualTo(String value) {
            addCriterion("outbound_num <>", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumGreaterThan(String value) {
            addCriterion("outbound_num >", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumGreaterThanOrEqualTo(String value) {
            addCriterion("outbound_num >=", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumLessThan(String value) {
            addCriterion("outbound_num <", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumLessThanOrEqualTo(String value) {
            addCriterion("outbound_num <=", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumLike(String value) {
            addCriterion("outbound_num like", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotLike(String value) {
            addCriterion("outbound_num not like", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIn(List<String> values) {
            addCriterion("outbound_num in", values, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotIn(List<String> values) {
            addCriterion("outbound_num not in", values, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumBetween(String value1, String value2) {
            addCriterion("outbound_num between", value1, value2, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotBetween(String value1, String value2) {
            addCriterion("outbound_num not between", value1, value2, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumIsNull() {
            addCriterion("dispatch_num is null");
            return (Criteria) this;
        }

        public Criteria andDispatchNumIsNotNull() {
            addCriterion("dispatch_num is not null");
            return (Criteria) this;
        }

        public Criteria andDispatchNumEqualTo(String value) {
            addCriterion("dispatch_num =", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumNotEqualTo(String value) {
            addCriterion("dispatch_num <>", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumGreaterThan(String value) {
            addCriterion("dispatch_num >", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumGreaterThanOrEqualTo(String value) {
            addCriterion("dispatch_num >=", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumLessThan(String value) {
            addCriterion("dispatch_num <", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumLessThanOrEqualTo(String value) {
            addCriterion("dispatch_num <=", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumLike(String value) {
            addCriterion("dispatch_num like", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumNotLike(String value) {
            addCriterion("dispatch_num not like", value, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumIn(List<String> values) {
            addCriterion("dispatch_num in", values, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumNotIn(List<String> values) {
            addCriterion("dispatch_num not in", values, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumBetween(String value1, String value2) {
            addCriterion("dispatch_num between", value1, value2, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andDispatchNumNotBetween(String value1, String value2) {
            addCriterion("dispatch_num not between", value1, value2, "dispatchNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNull() {
            addCriterion("contract_num is null");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNotNull() {
            addCriterion("contract_num is not null");
            return (Criteria) this;
        }

        public Criteria andContractNumEqualTo(String value) {
            addCriterion("contract_num =", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotEqualTo(String value) {
            addCriterion("contract_num <>", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThan(String value) {
            addCriterion("contract_num >", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThanOrEqualTo(String value) {
            addCriterion("contract_num >=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThan(String value) {
            addCriterion("contract_num <", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThanOrEqualTo(String value) {
            addCriterion("contract_num <=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLike(String value) {
            addCriterion("contract_num like", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotLike(String value) {
            addCriterion("contract_num not like", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIn(List<String> values) {
            addCriterion("contract_num in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotIn(List<String> values) {
            addCriterion("contract_num not in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumBetween(String value1, String value2) {
            addCriterion("contract_num between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotBetween(String value1, String value2) {
            addCriterion("contract_num not between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNull() {
            addCriterion("execute_num is null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNotNull() {
            addCriterion("execute_num is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumEqualTo(String value) {
            addCriterion("execute_num =", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotEqualTo(String value) {
            addCriterion("execute_num <>", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThan(String value) {
            addCriterion("execute_num >", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThanOrEqualTo(String value) {
            addCriterion("execute_num >=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThan(String value) {
            addCriterion("execute_num <", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThanOrEqualTo(String value) {
            addCriterion("execute_num <=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLike(String value) {
            addCriterion("execute_num like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotLike(String value) {
            addCriterion("execute_num not like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIn(List<String> values) {
            addCriterion("execute_num in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotIn(List<String> values) {
            addCriterion("execute_num not in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumBetween(String value1, String value2) {
            addCriterion("execute_num between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotBetween(String value1, String value2) {
            addCriterion("execute_num not between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andPackCountIsNull() {
            addCriterion("pack_count is null");
            return (Criteria) this;
        }

        public Criteria andPackCountIsNotNull() {
            addCriterion("pack_count is not null");
            return (Criteria) this;
        }

        public Criteria andPackCountEqualTo(Integer value) {
            addCriterion("pack_count =", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotEqualTo(Integer value) {
            addCriterion("pack_count <>", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountGreaterThan(Integer value) {
            addCriterion("pack_count >", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pack_count >=", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountLessThan(Integer value) {
            addCriterion("pack_count <", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountLessThanOrEqualTo(Integer value) {
            addCriterion("pack_count <=", value, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountIn(List<Integer> values) {
            addCriterion("pack_count in", values, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotIn(List<Integer> values) {
            addCriterion("pack_count not in", values, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountBetween(Integer value1, Integer value2) {
            addCriterion("pack_count between", value1, value2, "packCount");
            return (Criteria) this;
        }

        public Criteria andPackCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pack_count not between", value1, value2, "packCount");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNull() {
            addCriterion("destination is null");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNotNull() {
            addCriterion("destination is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationEqualTo(String value) {
            addCriterion("destination =", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotEqualTo(String value) {
            addCriterion("destination <>", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThan(String value) {
            addCriterion("destination >", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("destination >=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThan(String value) {
            addCriterion("destination <", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThanOrEqualTo(String value) {
            addCriterion("destination <=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLike(String value) {
            addCriterion("destination like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotLike(String value) {
            addCriterion("destination not like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationIn(List<String> values) {
            addCriterion("destination in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotIn(List<String> values) {
            addCriterion("destination not in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationBetween(String value1, String value2) {
            addCriterion("destination between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotBetween(String value1, String value2) {
            addCriterion("destination not between", value1, value2, "destination");
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