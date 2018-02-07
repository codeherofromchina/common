package com.erui.report.model;

import java.util.ArrayList;
import java.util.List;

public class TargetExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public TargetExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTargetStatusIsNull() {
            addCriterion("target_status is null");
            return (Criteria) this;
        }

        public Criteria andTargetStatusIsNotNull() {
            addCriterion("target_status is not null");
            return (Criteria) this;
        }

        public Criteria andTargetStatusEqualTo(String value) {
            addCriterion("target_status =", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusNotEqualTo(String value) {
            addCriterion("target_status <>", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusGreaterThan(String value) {
            addCriterion("target_status >", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusGreaterThanOrEqualTo(String value) {
            addCriterion("target_status >=", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusLessThan(String value) {
            addCriterion("target_status <", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusLessThanOrEqualTo(String value) {
            addCriterion("target_status <=", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusLike(String value) {
            addCriterion("target_status like", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusNotLike(String value) {
            addCriterion("target_status not like", value, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusIn(List<String> values) {
            addCriterion("target_status in", values, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusNotIn(List<String> values) {
            addCriterion("target_status not in", values, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusBetween(String value1, String value2) {
            addCriterion("target_status between", value1, value2, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andTargetStatusNotBetween(String value1, String value2) {
            addCriterion("target_status not between", value1, value2, "targetStatus");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andOrgnizationIsNull() {
            addCriterion("orgnization is null");
            return (Criteria) this;
        }

        public Criteria andOrgnizationIsNotNull() {
            addCriterion("orgnization is not null");
            return (Criteria) this;
        }

        public Criteria andOrgnizationEqualTo(String value) {
            addCriterion("orgnization =", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationNotEqualTo(String value) {
            addCriterion("orgnization <>", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationGreaterThan(String value) {
            addCriterion("orgnization >", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationGreaterThanOrEqualTo(String value) {
            addCriterion("orgnization >=", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationLessThan(String value) {
            addCriterion("orgnization <", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationLessThanOrEqualTo(String value) {
            addCriterion("orgnization <=", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationLike(String value) {
            addCriterion("orgnization like", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationNotLike(String value) {
            addCriterion("orgnization not like", value, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationIn(List<String> values) {
            addCriterion("orgnization in", values, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationNotIn(List<String> values) {
            addCriterion("orgnization not in", values, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationBetween(String value1, String value2) {
            addCriterion("orgnization between", value1, value2, "orgnization");
            return (Criteria) this;
        }

        public Criteria andOrgnizationNotBetween(String value1, String value2) {
            addCriterion("orgnization not between", value1, value2, "orgnization");
            return (Criteria) this;
        }

        public Criteria andTargetMothIsNull() {
            addCriterion("target_moth is null");
            return (Criteria) this;
        }

        public Criteria andTargetMothIsNotNull() {
            addCriterion("target_moth is not null");
            return (Criteria) this;
        }

        public Criteria andTargetMothEqualTo(Integer value) {
            addCriterion("target_moth =", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothNotEqualTo(Integer value) {
            addCriterion("target_moth <>", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothGreaterThan(Integer value) {
            addCriterion("target_moth >", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothGreaterThanOrEqualTo(Integer value) {
            addCriterion("target_moth >=", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothLessThan(Integer value) {
            addCriterion("target_moth <", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothLessThanOrEqualTo(Integer value) {
            addCriterion("target_moth <=", value, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothIn(List<Integer> values) {
            addCriterion("target_moth in", values, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothNotIn(List<Integer> values) {
            addCriterion("target_moth not in", values, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothBetween(Integer value1, Integer value2) {
            addCriterion("target_moth between", value1, value2, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetMothNotBetween(Integer value1, Integer value2) {
            addCriterion("target_moth not between", value1, value2, "targetMoth");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountIsNull() {
            addCriterion("target_ammount is null");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountIsNotNull() {
            addCriterion("target_ammount is not null");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountEqualTo(Long value) {
            addCriterion("target_ammount =", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountNotEqualTo(Long value) {
            addCriterion("target_ammount <>", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountGreaterThan(Long value) {
            addCriterion("target_ammount >", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountGreaterThanOrEqualTo(Long value) {
            addCriterion("target_ammount >=", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountLessThan(Long value) {
            addCriterion("target_ammount <", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountLessThanOrEqualTo(Long value) {
            addCriterion("target_ammount <=", value, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountIn(List<Long> values) {
            addCriterion("target_ammount in", values, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountNotIn(List<Long> values) {
            addCriterion("target_ammount not in", values, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountBetween(Long value1, Long value2) {
            addCriterion("target_ammount between", value1, value2, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetAmmountNotBetween(Long value1, Long value2) {
            addCriterion("target_ammount not between", value1, value2, "targetAmmount");
            return (Criteria) this;
        }

        public Criteria andTargetCountIsNull() {
            addCriterion("target_count is null");
            return (Criteria) this;
        }

        public Criteria andTargetCountIsNotNull() {
            addCriterion("target_count is not null");
            return (Criteria) this;
        }

        public Criteria andTargetCountEqualTo(Integer value) {
            addCriterion("target_count =", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountNotEqualTo(Integer value) {
            addCriterion("target_count <>", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountGreaterThan(Integer value) {
            addCriterion("target_count >", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("target_count >=", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountLessThan(Integer value) {
            addCriterion("target_count <", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountLessThanOrEqualTo(Integer value) {
            addCriterion("target_count <=", value, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountIn(List<Integer> values) {
            addCriterion("target_count in", values, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountNotIn(List<Integer> values) {
            addCriterion("target_count not in", values, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountBetween(Integer value1, Integer value2) {
            addCriterion("target_count between", value1, value2, "targetCount");
            return (Criteria) this;
        }

        public Criteria andTargetCountNotBetween(Integer value1, Integer value2) {
            addCriterion("target_count not between", value1, value2, "targetCount");
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