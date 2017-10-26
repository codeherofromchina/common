package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreditExtensionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CreditExtensionExample() {
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

        public Criteria andCreditDateIsNull() {
            addCriterion("credit_date is null");
            return (Criteria) this;
        }

        public Criteria andCreditDateIsNotNull() {
            addCriterion("credit_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreditDateEqualTo(Date value) {
            addCriterion("credit_date =", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateNotEqualTo(Date value) {
            addCriterion("credit_date <>", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateGreaterThan(Date value) {
            addCriterion("credit_date >", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateGreaterThanOrEqualTo(Date value) {
            addCriterion("credit_date >=", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateLessThan(Date value) {
            addCriterion("credit_date <", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateLessThanOrEqualTo(Date value) {
            addCriterion("credit_date <=", value, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateIn(List<Date> values) {
            addCriterion("credit_date in", values, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateNotIn(List<Date> values) {
            addCriterion("credit_date not in", values, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateBetween(Date value1, Date value2) {
            addCriterion("credit_date between", value1, value2, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditDateNotBetween(Date value1, Date value2) {
            addCriterion("credit_date not between", value1, value2, "creditDate");
            return (Criteria) this;
        }

        public Criteria andCreditAreaIsNull() {
            addCriterion("credit_area is null");
            return (Criteria) this;
        }

        public Criteria andCreditAreaIsNotNull() {
            addCriterion("credit_area is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAreaEqualTo(String value) {
            addCriterion("credit_area =", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaNotEqualTo(String value) {
            addCriterion("credit_area <>", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaGreaterThan(String value) {
            addCriterion("credit_area >", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaGreaterThanOrEqualTo(String value) {
            addCriterion("credit_area >=", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaLessThan(String value) {
            addCriterion("credit_area <", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaLessThanOrEqualTo(String value) {
            addCriterion("credit_area <=", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaLike(String value) {
            addCriterion("credit_area like", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaNotLike(String value) {
            addCriterion("credit_area not like", value, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaIn(List<String> values) {
            addCriterion("credit_area in", values, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaNotIn(List<String> values) {
            addCriterion("credit_area not in", values, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaBetween(String value1, String value2) {
            addCriterion("credit_area between", value1, value2, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditAreaNotBetween(String value1, String value2) {
            addCriterion("credit_area not between", value1, value2, "creditArea");
            return (Criteria) this;
        }

        public Criteria andCreditCountryIsNull() {
            addCriterion("credit_country is null");
            return (Criteria) this;
        }

        public Criteria andCreditCountryIsNotNull() {
            addCriterion("credit_country is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCountryEqualTo(String value) {
            addCriterion("credit_country =", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryNotEqualTo(String value) {
            addCriterion("credit_country <>", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryGreaterThan(String value) {
            addCriterion("credit_country >", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryGreaterThanOrEqualTo(String value) {
            addCriterion("credit_country >=", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryLessThan(String value) {
            addCriterion("credit_country <", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryLessThanOrEqualTo(String value) {
            addCriterion("credit_country <=", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryLike(String value) {
            addCriterion("credit_country like", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryNotLike(String value) {
            addCriterion("credit_country not like", value, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryIn(List<String> values) {
            addCriterion("credit_country in", values, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryNotIn(List<String> values) {
            addCriterion("credit_country not in", values, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryBetween(String value1, String value2) {
            addCriterion("credit_country between", value1, value2, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andCreditCountryNotBetween(String value1, String value2) {
            addCriterion("credit_country not between", value1, value2, "creditCountry");
            return (Criteria) this;
        }

        public Criteria andMailCodeIsNull() {
            addCriterion("mail_code is null");
            return (Criteria) this;
        }

        public Criteria andMailCodeIsNotNull() {
            addCriterion("mail_code is not null");
            return (Criteria) this;
        }

        public Criteria andMailCodeEqualTo(String value) {
            addCriterion("mail_code =", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeNotEqualTo(String value) {
            addCriterion("mail_code <>", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeGreaterThan(String value) {
            addCriterion("mail_code >", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeGreaterThanOrEqualTo(String value) {
            addCriterion("mail_code >=", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeLessThan(String value) {
            addCriterion("mail_code <", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeLessThanOrEqualTo(String value) {
            addCriterion("mail_code <=", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeLike(String value) {
            addCriterion("mail_code like", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeNotLike(String value) {
            addCriterion("mail_code not like", value, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeIn(List<String> values) {
            addCriterion("mail_code in", values, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeNotIn(List<String> values) {
            addCriterion("mail_code not in", values, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeBetween(String value1, String value2) {
            addCriterion("mail_code between", value1, value2, "mailCode");
            return (Criteria) this;
        }

        public Criteria andMailCodeNotBetween(String value1, String value2) {
            addCriterion("mail_code not between", value1, value2, "mailCode");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNull() {
            addCriterion("customer_name is null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIsNotNull() {
            addCriterion("customer_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerNameEqualTo(String value) {
            addCriterion("customer_name =", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotEqualTo(String value) {
            addCriterion("customer_name <>", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThan(String value) {
            addCriterion("customer_name >", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameGreaterThanOrEqualTo(String value) {
            addCriterion("customer_name >=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThan(String value) {
            addCriterion("customer_name <", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLessThanOrEqualTo(String value) {
            addCriterion("customer_name <=", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameLike(String value) {
            addCriterion("customer_name like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotLike(String value) {
            addCriterion("customer_name not like", value, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameIn(List<String> values) {
            addCriterion("customer_name in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotIn(List<String> values) {
            addCriterion("customer_name not in", values, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameBetween(String value1, String value2) {
            addCriterion("customer_name between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andCustomerNameNotBetween(String value1, String value2) {
            addCriterion("customer_name not between", value1, value2, "customerName");
            return (Criteria) this;
        }

        public Criteria andRelyCreditIsNull() {
            addCriterion("rely_credit is null");
            return (Criteria) this;
        }

        public Criteria andRelyCreditIsNotNull() {
            addCriterion("rely_credit is not null");
            return (Criteria) this;
        }

        public Criteria andRelyCreditEqualTo(BigDecimal value) {
            addCriterion("rely_credit =", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditNotEqualTo(BigDecimal value) {
            addCriterion("rely_credit <>", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditGreaterThan(BigDecimal value) {
            addCriterion("rely_credit >", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rely_credit >=", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditLessThan(BigDecimal value) {
            addCriterion("rely_credit <", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rely_credit <=", value, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditIn(List<BigDecimal> values) {
            addCriterion("rely_credit in", values, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditNotIn(List<BigDecimal> values) {
            addCriterion("rely_credit not in", values, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rely_credit between", value1, value2, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andRelyCreditNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rely_credit not between", value1, value2, "relyCredit");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateIsNull() {
            addCriterion("effective_date is null");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateIsNotNull() {
            addCriterion("effective_date is not null");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateEqualTo(Date value) {
            addCriterion("effective_date =", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateNotEqualTo(Date value) {
            addCriterion("effective_date <>", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateGreaterThan(Date value) {
            addCriterion("effective_date >", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("effective_date >=", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateLessThan(Date value) {
            addCriterion("effective_date <", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateLessThanOrEqualTo(Date value) {
            addCriterion("effective_date <=", value, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateIn(List<Date> values) {
            addCriterion("effective_date in", values, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateNotIn(List<Date> values) {
            addCriterion("effective_date not in", values, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateBetween(Date value1, Date value2) {
            addCriterion("effective_date between", value1, value2, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andEffectiveDateNotBetween(Date value1, Date value2) {
            addCriterion("effective_date not between", value1, value2, "effectiveDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNull() {
            addCriterion("expiry_date is null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNotNull() {
            addCriterion("expiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateEqualTo(Date value) {
            addCriterion("expiry_date =", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotEqualTo(Date value) {
            addCriterion("expiry_date <>", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThan(Date value) {
            addCriterion("expiry_date >", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("expiry_date >=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThan(Date value) {
            addCriterion("expiry_date <", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThanOrEqualTo(Date value) {
            addCriterion("expiry_date <=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIn(List<Date> values) {
            addCriterion("expiry_date in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotIn(List<Date> values) {
            addCriterion("expiry_date not in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateBetween(Date value1, Date value2) {
            addCriterion("expiry_date between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotBetween(Date value1, Date value2) {
            addCriterion("expiry_date not between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andUsedAmountIsNull() {
            addCriterion("used_amount is null");
            return (Criteria) this;
        }

        public Criteria andUsedAmountIsNotNull() {
            addCriterion("used_amount is not null");
            return (Criteria) this;
        }

        public Criteria andUsedAmountEqualTo(BigDecimal value) {
            addCriterion("used_amount =", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountNotEqualTo(BigDecimal value) {
            addCriterion("used_amount <>", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountGreaterThan(BigDecimal value) {
            addCriterion("used_amount >", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("used_amount >=", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountLessThan(BigDecimal value) {
            addCriterion("used_amount <", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("used_amount <=", value, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountIn(List<BigDecimal> values) {
            addCriterion("used_amount in", values, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountNotIn(List<BigDecimal> values) {
            addCriterion("used_amount not in", values, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("used_amount between", value1, value2, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andUsedAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("used_amount not between", value1, value2, "usedAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIsNull() {
            addCriterion("available_amount is null");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIsNotNull() {
            addCriterion("available_amount is not null");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountEqualTo(BigDecimal value) {
            addCriterion("available_amount =", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotEqualTo(BigDecimal value) {
            addCriterion("available_amount <>", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountGreaterThan(BigDecimal value) {
            addCriterion("available_amount >", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("available_amount >=", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountLessThan(BigDecimal value) {
            addCriterion("available_amount <", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("available_amount <=", value, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountIn(List<BigDecimal> values) {
            addCriterion("available_amount in", values, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotIn(List<BigDecimal> values) {
            addCriterion("available_amount not in", values, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_amount between", value1, value2, "availableAmount");
            return (Criteria) this;
        }

        public Criteria andAvailableAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("available_amount not between", value1, value2, "availableAmount");
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