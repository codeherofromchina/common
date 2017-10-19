package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarketerCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MarketerCountExample() {
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

        public Criteria andMarketerIsNull() {
            addCriterion("marketer is null");
            return (Criteria) this;
        }

        public Criteria andMarketerIsNotNull() {
            addCriterion("marketer is not null");
            return (Criteria) this;
        }

        public Criteria andMarketerEqualTo(String value) {
            addCriterion("marketer =", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerNotEqualTo(String value) {
            addCriterion("marketer <>", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerGreaterThan(String value) {
            addCriterion("marketer >", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerGreaterThanOrEqualTo(String value) {
            addCriterion("marketer >=", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerLessThan(String value) {
            addCriterion("marketer <", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerLessThanOrEqualTo(String value) {
            addCriterion("marketer <=", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerLike(String value) {
            addCriterion("marketer like", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerNotLike(String value) {
            addCriterion("marketer not like", value, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerIn(List<String> values) {
            addCriterion("marketer in", values, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerNotIn(List<String> values) {
            addCriterion("marketer not in", values, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerBetween(String value1, String value2) {
            addCriterion("marketer between", value1, value2, "marketer");
            return (Criteria) this;
        }

        public Criteria andMarketerNotBetween(String value1, String value2) {
            addCriterion("marketer not between", value1, value2, "marketer");
            return (Criteria) this;
        }

        public Criteria andInquiryCountIsNull() {
            addCriterion("inquiry_count is null");
            return (Criteria) this;
        }

        public Criteria andInquiryCountIsNotNull() {
            addCriterion("inquiry_count is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryCountEqualTo(Integer value) {
            addCriterion("inquiry_count =", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountNotEqualTo(Integer value) {
            addCriterion("inquiry_count <>", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountGreaterThan(Integer value) {
            addCriterion("inquiry_count >", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("inquiry_count >=", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountLessThan(Integer value) {
            addCriterion("inquiry_count <", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountLessThanOrEqualTo(Integer value) {
            addCriterion("inquiry_count <=", value, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountIn(List<Integer> values) {
            addCriterion("inquiry_count in", values, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountNotIn(List<Integer> values) {
            addCriterion("inquiry_count not in", values, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountBetween(Integer value1, Integer value2) {
            addCriterion("inquiry_count between", value1, value2, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andInquiryCountNotBetween(Integer value1, Integer value2) {
            addCriterion("inquiry_count not between", value1, value2, "inquiryCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountIsNull() {
            addCriterion("quote_count is null");
            return (Criteria) this;
        }

        public Criteria andQuoteCountIsNotNull() {
            addCriterion("quote_count is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteCountEqualTo(Integer value) {
            addCriterion("quote_count =", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountNotEqualTo(Integer value) {
            addCriterion("quote_count <>", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountGreaterThan(Integer value) {
            addCriterion("quote_count >", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("quote_count >=", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountLessThan(Integer value) {
            addCriterion("quote_count <", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountLessThanOrEqualTo(Integer value) {
            addCriterion("quote_count <=", value, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountIn(List<Integer> values) {
            addCriterion("quote_count in", values, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountNotIn(List<Integer> values) {
            addCriterion("quote_count not in", values, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountBetween(Integer value1, Integer value2) {
            addCriterion("quote_count between", value1, value2, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andQuoteCountNotBetween(Integer value1, Integer value2) {
            addCriterion("quote_count not between", value1, value2, "quoteCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountIsNull() {
            addCriterion("bargain_count is null");
            return (Criteria) this;
        }

        public Criteria andBargainCountIsNotNull() {
            addCriterion("bargain_count is not null");
            return (Criteria) this;
        }

        public Criteria andBargainCountEqualTo(Integer value) {
            addCriterion("bargain_count =", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountNotEqualTo(Integer value) {
            addCriterion("bargain_count <>", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountGreaterThan(Integer value) {
            addCriterion("bargain_count >", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("bargain_count >=", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountLessThan(Integer value) {
            addCriterion("bargain_count <", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountLessThanOrEqualTo(Integer value) {
            addCriterion("bargain_count <=", value, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountIn(List<Integer> values) {
            addCriterion("bargain_count in", values, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountNotIn(List<Integer> values) {
            addCriterion("bargain_count not in", values, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountBetween(Integer value1, Integer value2) {
            addCriterion("bargain_count between", value1, value2, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainCountNotBetween(Integer value1, Integer value2) {
            addCriterion("bargain_count not between", value1, value2, "bargainCount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountIsNull() {
            addCriterion("bargain_amount is null");
            return (Criteria) this;
        }

        public Criteria andBargainAmountIsNotNull() {
            addCriterion("bargain_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBargainAmountEqualTo(BigDecimal value) {
            addCriterion("bargain_amount =", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountNotEqualTo(BigDecimal value) {
            addCriterion("bargain_amount <>", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountGreaterThan(BigDecimal value) {
            addCriterion("bargain_amount >", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bargain_amount >=", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountLessThan(BigDecimal value) {
            addCriterion("bargain_amount <", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bargain_amount <=", value, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountIn(List<BigDecimal> values) {
            addCriterion("bargain_amount in", values, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountNotIn(List<BigDecimal> values) {
            addCriterion("bargain_amount not in", values, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bargain_amount between", value1, value2, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andBargainAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bargain_amount not between", value1, value2, "bargainAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountIsNull() {
            addCriterion("inquiry_amount is null");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountIsNotNull() {
            addCriterion("inquiry_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountEqualTo(BigDecimal value) {
            addCriterion("inquiry_amount =", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountNotEqualTo(BigDecimal value) {
            addCriterion("inquiry_amount <>", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountGreaterThan(BigDecimal value) {
            addCriterion("inquiry_amount >", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("inquiry_amount >=", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountLessThan(BigDecimal value) {
            addCriterion("inquiry_amount <", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("inquiry_amount <=", value, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountIn(List<BigDecimal> values) {
            addCriterion("inquiry_amount in", values, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountNotIn(List<BigDecimal> values) {
            addCriterion("inquiry_amount not in", values, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("inquiry_amount between", value1, value2, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andInquiryAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("inquiry_amount not between", value1, value2, "inquiryAmount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountIsNull() {
            addCriterion("new_member_count is null");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountIsNotNull() {
            addCriterion("new_member_count is not null");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountEqualTo(Integer value) {
            addCriterion("new_member_count =", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountNotEqualTo(Integer value) {
            addCriterion("new_member_count <>", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountGreaterThan(Integer value) {
            addCriterion("new_member_count >", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_member_count >=", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountLessThan(Integer value) {
            addCriterion("new_member_count <", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountLessThanOrEqualTo(Integer value) {
            addCriterion("new_member_count <=", value, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountIn(List<Integer> values) {
            addCriterion("new_member_count in", values, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountNotIn(List<Integer> values) {
            addCriterion("new_member_count not in", values, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountBetween(Integer value1, Integer value2) {
            addCriterion("new_member_count between", value1, value2, "newMemberCount");
            return (Criteria) this;
        }

        public Criteria andNewMemberCountNotBetween(Integer value1, Integer value2) {
            addCriterion("new_member_count not between", value1, value2, "newMemberCount");
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