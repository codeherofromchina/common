package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MemberExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MemberExample() {
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

        public Criteria andInputDateIsNull() {
            addCriterion("input_date is null");
            return (Criteria) this;
        }

        public Criteria andInputDateIsNotNull() {
            addCriterion("input_date is not null");
            return (Criteria) this;
        }

        public Criteria andInputDateEqualTo(Date value) {
            addCriterion("input_date =", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateNotEqualTo(Date value) {
            addCriterion("input_date <>", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateGreaterThan(Date value) {
            addCriterion("input_date >", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateGreaterThanOrEqualTo(Date value) {
            addCriterion("input_date >=", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateLessThan(Date value) {
            addCriterion("input_date <", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateLessThanOrEqualTo(Date value) {
            addCriterion("input_date <=", value, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateIn(List<Date> values) {
            addCriterion("input_date in", values, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateNotIn(List<Date> values) {
            addCriterion("input_date not in", values, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateBetween(Date value1, Date value2) {
            addCriterion("input_date between", value1, value2, "inputDate");
            return (Criteria) this;
        }

        public Criteria andInputDateNotBetween(Date value1, Date value2) {
            addCriterion("input_date not between", value1, value2, "inputDate");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountIsNull() {
            addCriterion("general_member_count is null");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountIsNotNull() {
            addCriterion("general_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountEqualTo(Integer value) {
            addCriterion("general_member_count =", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountNotEqualTo(Integer value) {
            addCriterion("general_member_count <>", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountGreaterThan(Integer value) {
            addCriterion("general_member_count >", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("general_member_count >=", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountLessThan(Integer value) {
            addCriterion("general_member_count <", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("general_member_count <=", value, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountIn(List<Integer> values) {
            addCriterion("general_member_count in", values, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountNotIn(List<Integer> values) {
            addCriterion("general_member_count not in", values, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("general_member_count between", value1, value2, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("general_member_count not between", value1, value2, "generalMemberCount");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyIsNull() {
            addCriterion("general_member_rebuy is null");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyIsNotNull() {
            addCriterion("general_member_rebuy is not null");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyEqualTo(Integer value) {
            addCriterion("general_member_rebuy =", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyNotEqualTo(Integer value) {
            addCriterion("general_member_rebuy <>", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyGreaterThan(Integer value) {
            addCriterion("general_member_rebuy >", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyGreaterThanOrEqualTo(Integer value) {
            addCriterion("general_member_rebuy >=", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyLessThan(Integer value) {
            addCriterion("general_member_rebuy <", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyLessThanOrEqualTo(Integer value) {
            addCriterion("general_member_rebuy <=", value, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyIn(List<Integer> values) {
            addCriterion("general_member_rebuy in", values, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyNotIn(List<Integer> values) {
            addCriterion("general_member_rebuy not in", values, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyBetween(Integer value1, Integer value2) {
            addCriterion("general_member_rebuy between", value1, value2, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGeneralMemberRebuyNotBetween(Integer value1, Integer value2) {
            addCriterion("general_member_rebuy not between", value1, value2, "generalMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountIsNull() {
            addCriterion("senior_member_count is null");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountIsNotNull() {
            addCriterion("senior_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountEqualTo(Integer value) {
            addCriterion("senior_member_count =", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountNotEqualTo(Integer value) {
            addCriterion("senior_member_count <>", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountGreaterThan(Integer value) {
            addCriterion("senior_member_count >", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("senior_member_count >=", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountLessThan(Integer value) {
            addCriterion("senior_member_count <", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("senior_member_count <=", value, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountIn(List<Integer> values) {
            addCriterion("senior_member_count in", values, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountNotIn(List<Integer> values) {
            addCriterion("senior_member_count not in", values, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("senior_member_count between", value1, value2, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("senior_member_count not between", value1, value2, "seniorMemberCount");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyIsNull() {
            addCriterion("senior_member_rebuy is null");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyIsNotNull() {
            addCriterion("senior_member_rebuy is not null");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyEqualTo(Integer value) {
            addCriterion("senior_member_rebuy =", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyNotEqualTo(Integer value) {
            addCriterion("senior_member_rebuy <>", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyGreaterThan(Integer value) {
            addCriterion("senior_member_rebuy >", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyGreaterThanOrEqualTo(Integer value) {
            addCriterion("senior_member_rebuy >=", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyLessThan(Integer value) {
            addCriterion("senior_member_rebuy <", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyLessThanOrEqualTo(Integer value) {
            addCriterion("senior_member_rebuy <=", value, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyIn(List<Integer> values) {
            addCriterion("senior_member_rebuy in", values, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyNotIn(List<Integer> values) {
            addCriterion("senior_member_rebuy not in", values, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyBetween(Integer value1, Integer value2) {
            addCriterion("senior_member_rebuy between", value1, value2, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andSeniorMemberRebuyNotBetween(Integer value1, Integer value2) {
            addCriterion("senior_member_rebuy not between", value1, value2, "seniorMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountIsNull() {
            addCriterion("gold_member_count is null");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountIsNotNull() {
            addCriterion("gold_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountEqualTo(Integer value) {
            addCriterion("gold_member_count =", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountNotEqualTo(Integer value) {
            addCriterion("gold_member_count <>", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountGreaterThan(Integer value) {
            addCriterion("gold_member_count >", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("gold_member_count >=", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountLessThan(Integer value) {
            addCriterion("gold_member_count <", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("gold_member_count <=", value, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountIn(List<Integer> values) {
            addCriterion("gold_member_count in", values, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountNotIn(List<Integer> values) {
            addCriterion("gold_member_count not in", values, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("gold_member_count between", value1, value2, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("gold_member_count not between", value1, value2, "goldMemberCount");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyIsNull() {
            addCriterion("gold_member_rebuy is null");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyIsNotNull() {
            addCriterion("gold_member_rebuy is not null");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyEqualTo(Integer value) {
            addCriterion("gold_member_rebuy =", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyNotEqualTo(Integer value) {
            addCriterion("gold_member_rebuy <>", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyGreaterThan(Integer value) {
            addCriterion("gold_member_rebuy >", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyGreaterThanOrEqualTo(Integer value) {
            addCriterion("gold_member_rebuy >=", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyLessThan(Integer value) {
            addCriterion("gold_member_rebuy <", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyLessThanOrEqualTo(Integer value) {
            addCriterion("gold_member_rebuy <=", value, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyIn(List<Integer> values) {
            addCriterion("gold_member_rebuy in", values, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyNotIn(List<Integer> values) {
            addCriterion("gold_member_rebuy not in", values, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyBetween(Integer value1, Integer value2) {
            addCriterion("gold_member_rebuy between", value1, value2, "goldMemberRebuy");
            return (Criteria) this;
        }

        public Criteria andGoldMemberRebuyNotBetween(Integer value1, Integer value2) {
            addCriterion("gold_member_rebuy not between", value1, value2, "goldMemberRebuy");
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