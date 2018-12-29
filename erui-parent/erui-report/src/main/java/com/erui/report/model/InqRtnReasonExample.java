package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InqRtnReasonExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InqRtnReasonExample() {
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

        public Criteria andQuotationNumIsNull() {
            addCriterion("quotation_num is null");
            return (Criteria) this;
        }

        public Criteria andQuotationNumIsNotNull() {
            addCriterion("quotation_num is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationNumEqualTo(String value) {
            addCriterion("quotation_num =", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotEqualTo(String value) {
            addCriterion("quotation_num <>", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumGreaterThan(String value) {
            addCriterion("quotation_num >", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumGreaterThanOrEqualTo(String value) {
            addCriterion("quotation_num >=", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLessThan(String value) {
            addCriterion("quotation_num <", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLessThanOrEqualTo(String value) {
            addCriterion("quotation_num <=", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLike(String value) {
            addCriterion("quotation_num like", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotLike(String value) {
            addCriterion("quotation_num not like", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumIn(List<String> values) {
            addCriterion("quotation_num in", values, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotIn(List<String> values) {
            addCriterion("quotation_num not in", values, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumBetween(String value1, String value2) {
            addCriterion("quotation_num between", value1, value2, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotBetween(String value1, String value2) {
            addCriterion("quotation_num not between", value1, value2, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIsNull() {
            addCriterion("rollin_time is null");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIsNotNull() {
            addCriterion("rollin_time is not null");
            return (Criteria) this;
        }

        public Criteria andRollinTimeEqualTo(Date value) {
            addCriterion("rollin_time =", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotEqualTo(Date value) {
            addCriterion("rollin_time <>", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeGreaterThan(Date value) {
            addCriterion("rollin_time >", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rollin_time >=", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeLessThan(Date value) {
            addCriterion("rollin_time <", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeLessThanOrEqualTo(Date value) {
            addCriterion("rollin_time <=", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIn(List<Date> values) {
            addCriterion("rollin_time in", values, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotIn(List<Date> values) {
            addCriterion("rollin_time not in", values, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeBetween(Date value1, Date value2) {
            addCriterion("rollin_time between", value1, value2, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotBetween(Date value1, Date value2) {
            addCriterion("rollin_time not between", value1, value2, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIsNull() {
            addCriterion("inquiry_unit is null");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIsNotNull() {
            addCriterion("inquiry_unit is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitEqualTo(String value) {
            addCriterion("inquiry_unit =", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotEqualTo(String value) {
            addCriterion("inquiry_unit <>", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitGreaterThan(String value) {
            addCriterion("inquiry_unit >", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_unit >=", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLessThan(String value) {
            addCriterion("inquiry_unit <", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLessThanOrEqualTo(String value) {
            addCriterion("inquiry_unit <=", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLike(String value) {
            addCriterion("inquiry_unit like", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotLike(String value) {
            addCriterion("inquiry_unit not like", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIn(List<String> values) {
            addCriterion("inquiry_unit in", values, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotIn(List<String> values) {
            addCriterion("inquiry_unit not in", values, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitBetween(String value1, String value2) {
            addCriterion("inquiry_unit between", value1, value2, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotBetween(String value1, String value2) {
            addCriterion("inquiry_unit not between", value1, value2, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIsNull() {
            addCriterion("inquiry_area is null");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIsNotNull() {
            addCriterion("inquiry_area is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaEqualTo(String value) {
            addCriterion("inquiry_area =", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotEqualTo(String value) {
            addCriterion("inquiry_area <>", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaGreaterThan(String value) {
            addCriterion("inquiry_area >", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_area >=", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLessThan(String value) {
            addCriterion("inquiry_area <", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLessThanOrEqualTo(String value) {
            addCriterion("inquiry_area <=", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLike(String value) {
            addCriterion("inquiry_area like", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotLike(String value) {
            addCriterion("inquiry_area not like", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIn(List<String> values) {
            addCriterion("inquiry_area in", values, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotIn(List<String> values) {
            addCriterion("inquiry_area not in", values, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaBetween(String value1, String value2) {
            addCriterion("inquiry_area between", value1, value2, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotBetween(String value1, String value2) {
            addCriterion("inquiry_area not between", value1, value2, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNull() {
            addCriterion("organization is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNotNull() {
            addCriterion("organization is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationEqualTo(String value) {
            addCriterion("organization =", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotEqualTo(String value) {
            addCriterion("organization <>", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThan(String value) {
            addCriterion("organization >", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThanOrEqualTo(String value) {
            addCriterion("organization >=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThan(String value) {
            addCriterion("organization <", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThanOrEqualTo(String value) {
            addCriterion("organization <=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLike(String value) {
            addCriterion("organization like", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotLike(String value) {
            addCriterion("organization not like", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationIn(List<String> values) {
            addCriterion("organization in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotIn(List<String> values) {
            addCriterion("organization not in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationBetween(String value1, String value2) {
            addCriterion("organization between", value1, value2, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotBetween(String value1, String value2) {
            addCriterion("organization not between", value1, value2, "organization");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonIsNull() {
            addCriterion("return_season is null");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonIsNotNull() {
            addCriterion("return_season is not null");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonEqualTo(String value) {
            addCriterion("return_season =", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonNotEqualTo(String value) {
            addCriterion("return_season <>", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonGreaterThan(String value) {
            addCriterion("return_season >", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonGreaterThanOrEqualTo(String value) {
            addCriterion("return_season >=", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonLessThan(String value) {
            addCriterion("return_season <", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonLessThanOrEqualTo(String value) {
            addCriterion("return_season <=", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonLike(String value) {
            addCriterion("return_season like", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonNotLike(String value) {
            addCriterion("return_season not like", value, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonIn(List<String> values) {
            addCriterion("return_season in", values, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonNotIn(List<String> values) {
            addCriterion("return_season not in", values, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonBetween(String value1, String value2) {
            addCriterion("return_season between", value1, value2, "returnSeason");
            return (Criteria) this;
        }

        public Criteria andReturnSeasonNotBetween(String value1, String value2) {
            addCriterion("return_season not between", value1, value2, "returnSeason");
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