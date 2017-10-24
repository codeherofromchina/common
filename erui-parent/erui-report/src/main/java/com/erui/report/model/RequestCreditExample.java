package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RequestCreditExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public RequestCreditExample() {
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

        public Criteria andCreditSerialNumIsNull() {
            addCriterion("credit_serial_num is null");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumIsNotNull() {
            addCriterion("credit_serial_num is not null");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumEqualTo(String value) {
            addCriterion("credit_serial_num =", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumNotEqualTo(String value) {
            addCriterion("credit_serial_num <>", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumGreaterThan(String value) {
            addCriterion("credit_serial_num >", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumGreaterThanOrEqualTo(String value) {
            addCriterion("credit_serial_num >=", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumLessThan(String value) {
            addCriterion("credit_serial_num <", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumLessThanOrEqualTo(String value) {
            addCriterion("credit_serial_num <=", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumLike(String value) {
            addCriterion("credit_serial_num like", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumNotLike(String value) {
            addCriterion("credit_serial_num not like", value, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumIn(List<String> values) {
            addCriterion("credit_serial_num in", values, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumNotIn(List<String> values) {
            addCriterion("credit_serial_num not in", values, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumBetween(String value1, String value2) {
            addCriterion("credit_serial_num between", value1, value2, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andCreditSerialNumNotBetween(String value1, String value2) {
            addCriterion("credit_serial_num not between", value1, value2, "creditSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNull() {
            addCriterion("order_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderNumIsNotNull() {
            addCriterion("order_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderNumEqualTo(String value) {
            addCriterion("order_num =", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotEqualTo(String value) {
            addCriterion("order_num <>", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThan(String value) {
            addCriterion("order_num >", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumGreaterThanOrEqualTo(String value) {
            addCriterion("order_num >=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThan(String value) {
            addCriterion("order_num <", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLessThanOrEqualTo(String value) {
            addCriterion("order_num <=", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumLike(String value) {
            addCriterion("order_num like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotLike(String value) {
            addCriterion("order_num not like", value, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumIn(List<String> values) {
            addCriterion("order_num in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotIn(List<String> values) {
            addCriterion("order_num not in", values, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumBetween(String value1, String value2) {
            addCriterion("order_num between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andOrderNumNotBetween(String value1, String value2) {
            addCriterion("order_num not between", value1, value2, "orderNum");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyIsNull() {
            addCriterion("sales_main_company is null");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyIsNotNull() {
            addCriterion("sales_main_company is not null");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyEqualTo(String value) {
            addCriterion("sales_main_company =", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyNotEqualTo(String value) {
            addCriterion("sales_main_company <>", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyGreaterThan(String value) {
            addCriterion("sales_main_company >", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("sales_main_company >=", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyLessThan(String value) {
            addCriterion("sales_main_company <", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyLessThanOrEqualTo(String value) {
            addCriterion("sales_main_company <=", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyLike(String value) {
            addCriterion("sales_main_company like", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyNotLike(String value) {
            addCriterion("sales_main_company not like", value, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyIn(List<String> values) {
            addCriterion("sales_main_company in", values, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyNotIn(List<String> values) {
            addCriterion("sales_main_company not in", values, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyBetween(String value1, String value2) {
            addCriterion("sales_main_company between", value1, value2, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesMainCompanyNotBetween(String value1, String value2) {
            addCriterion("sales_main_company not between", value1, value2, "salesMainCompany");
            return (Criteria) this;
        }

        public Criteria andSalesAreaIsNull() {
            addCriterion("sales_area is null");
            return (Criteria) this;
        }

        public Criteria andSalesAreaIsNotNull() {
            addCriterion("sales_area is not null");
            return (Criteria) this;
        }

        public Criteria andSalesAreaEqualTo(String value) {
            addCriterion("sales_area =", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaNotEqualTo(String value) {
            addCriterion("sales_area <>", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaGreaterThan(String value) {
            addCriterion("sales_area >", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaGreaterThanOrEqualTo(String value) {
            addCriterion("sales_area >=", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaLessThan(String value) {
            addCriterion("sales_area <", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaLessThanOrEqualTo(String value) {
            addCriterion("sales_area <=", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaLike(String value) {
            addCriterion("sales_area like", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaNotLike(String value) {
            addCriterion("sales_area not like", value, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaIn(List<String> values) {
            addCriterion("sales_area in", values, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaNotIn(List<String> values) {
            addCriterion("sales_area not in", values, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaBetween(String value1, String value2) {
            addCriterion("sales_area between", value1, value2, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesAreaNotBetween(String value1, String value2) {
            addCriterion("sales_area not between", value1, value2, "salesArea");
            return (Criteria) this;
        }

        public Criteria andSalesCountryIsNull() {
            addCriterion("sales_country is null");
            return (Criteria) this;
        }

        public Criteria andSalesCountryIsNotNull() {
            addCriterion("sales_country is not null");
            return (Criteria) this;
        }

        public Criteria andSalesCountryEqualTo(String value) {
            addCriterion("sales_country =", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryNotEqualTo(String value) {
            addCriterion("sales_country <>", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryGreaterThan(String value) {
            addCriterion("sales_country >", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryGreaterThanOrEqualTo(String value) {
            addCriterion("sales_country >=", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryLessThan(String value) {
            addCriterion("sales_country <", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryLessThanOrEqualTo(String value) {
            addCriterion("sales_country <=", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryLike(String value) {
            addCriterion("sales_country like", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryNotLike(String value) {
            addCriterion("sales_country not like", value, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryIn(List<String> values) {
            addCriterion("sales_country in", values, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryNotIn(List<String> values) {
            addCriterion("sales_country not in", values, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryBetween(String value1, String value2) {
            addCriterion("sales_country between", value1, value2, "salesCountry");
            return (Criteria) this;
        }

        public Criteria andSalesCountryNotBetween(String value1, String value2) {
            addCriterion("sales_country not between", value1, value2, "salesCountry");
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

        public Criteria andCustomerCodeIsNull() {
            addCriterion("customer_code is null");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeIsNotNull() {
            addCriterion("customer_code is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeEqualTo(String value) {
            addCriterion("customer_code =", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeNotEqualTo(String value) {
            addCriterion("customer_code <>", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeGreaterThan(String value) {
            addCriterion("customer_code >", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeGreaterThanOrEqualTo(String value) {
            addCriterion("customer_code >=", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeLessThan(String value) {
            addCriterion("customer_code <", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeLessThanOrEqualTo(String value) {
            addCriterion("customer_code <=", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeLike(String value) {
            addCriterion("customer_code like", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeNotLike(String value) {
            addCriterion("customer_code not like", value, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeIn(List<String> values) {
            addCriterion("customer_code in", values, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeNotIn(List<String> values) {
            addCriterion("customer_code not in", values, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeBetween(String value1, String value2) {
            addCriterion("customer_code between", value1, value2, "customerCode");
            return (Criteria) this;
        }

        public Criteria andCustomerCodeNotBetween(String value1, String value2) {
            addCriterion("customer_code not between", value1, value2, "customerCode");
            return (Criteria) this;
        }

        public Criteria andExportProNameIsNull() {
            addCriterion("export_pro_name is null");
            return (Criteria) this;
        }

        public Criteria andExportProNameIsNotNull() {
            addCriterion("export_pro_name is not null");
            return (Criteria) this;
        }

        public Criteria andExportProNameEqualTo(String value) {
            addCriterion("export_pro_name =", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameNotEqualTo(String value) {
            addCriterion("export_pro_name <>", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameGreaterThan(String value) {
            addCriterion("export_pro_name >", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameGreaterThanOrEqualTo(String value) {
            addCriterion("export_pro_name >=", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameLessThan(String value) {
            addCriterion("export_pro_name <", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameLessThanOrEqualTo(String value) {
            addCriterion("export_pro_name <=", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameLike(String value) {
            addCriterion("export_pro_name like", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameNotLike(String value) {
            addCriterion("export_pro_name not like", value, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameIn(List<String> values) {
            addCriterion("export_pro_name in", values, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameNotIn(List<String> values) {
            addCriterion("export_pro_name not in", values, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameBetween(String value1, String value2) {
            addCriterion("export_pro_name between", value1, value2, "exportProName");
            return (Criteria) this;
        }

        public Criteria andExportProNameNotBetween(String value1, String value2) {
            addCriterion("export_pro_name not between", value1, value2, "exportProName");
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

        public Criteria andOrderAmountIsNull() {
            addCriterion("order_amount is null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIsNotNull() {
            addCriterion("order_amount is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAmountEqualTo(BigDecimal value) {
            addCriterion("order_amount =", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotEqualTo(BigDecimal value) {
            addCriterion("order_amount <>", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThan(BigDecimal value) {
            addCriterion("order_amount >", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount >=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThan(BigDecimal value) {
            addCriterion("order_amount <", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("order_amount <=", value, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountIn(List<BigDecimal> values) {
            addCriterion("order_amount in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotIn(List<BigDecimal> values) {
            addCriterion("order_amount not in", values, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andOrderAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("order_amount not between", value1, value2, "orderAmount");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyIsNull() {
            addCriterion("credit_currency is null");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyIsNotNull() {
            addCriterion("credit_currency is not null");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyEqualTo(String value) {
            addCriterion("credit_currency =", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyNotEqualTo(String value) {
            addCriterion("credit_currency <>", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyGreaterThan(String value) {
            addCriterion("credit_currency >", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyGreaterThanOrEqualTo(String value) {
            addCriterion("credit_currency >=", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyLessThan(String value) {
            addCriterion("credit_currency <", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyLessThanOrEqualTo(String value) {
            addCriterion("credit_currency <=", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyLike(String value) {
            addCriterion("credit_currency like", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyNotLike(String value) {
            addCriterion("credit_currency not like", value, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyIn(List<String> values) {
            addCriterion("credit_currency in", values, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyNotIn(List<String> values) {
            addCriterion("credit_currency not in", values, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyBetween(String value1, String value2) {
            addCriterion("credit_currency between", value1, value2, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andCreditCurrencyNotBetween(String value1, String value2) {
            addCriterion("credit_currency not between", value1, value2, "creditCurrency");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIsNull() {
            addCriterion("receive_amount is null");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIsNotNull() {
            addCriterion("receive_amount is not null");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountEqualTo(BigDecimal value) {
            addCriterion("receive_amount =", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotEqualTo(BigDecimal value) {
            addCriterion("receive_amount <>", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountGreaterThan(BigDecimal value) {
            addCriterion("receive_amount >", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_amount >=", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountLessThan(BigDecimal value) {
            addCriterion("receive_amount <", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receive_amount <=", value, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountIn(List<BigDecimal> values) {
            addCriterion("receive_amount in", values, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotIn(List<BigDecimal> values) {
            addCriterion("receive_amount not in", values, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_amount between", value1, value2, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andReceiveAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receive_amount not between", value1, value2, "receiveAmount");
            return (Criteria) this;
        }

        public Criteria andWarnStatusIsNull() {
            addCriterion("warn_status is null");
            return (Criteria) this;
        }

        public Criteria andWarnStatusIsNotNull() {
            addCriterion("warn_status is not null");
            return (Criteria) this;
        }

        public Criteria andWarnStatusEqualTo(String value) {
            addCriterion("warn_status =", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusNotEqualTo(String value) {
            addCriterion("warn_status <>", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusGreaterThan(String value) {
            addCriterion("warn_status >", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusGreaterThanOrEqualTo(String value) {
            addCriterion("warn_status >=", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusLessThan(String value) {
            addCriterion("warn_status <", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusLessThanOrEqualTo(String value) {
            addCriterion("warn_status <=", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusLike(String value) {
            addCriterion("warn_status like", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusNotLike(String value) {
            addCriterion("warn_status not like", value, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusIn(List<String> values) {
            addCriterion("warn_status in", values, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusNotIn(List<String> values) {
            addCriterion("warn_status not in", values, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusBetween(String value1, String value2) {
            addCriterion("warn_status between", value1, value2, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andWarnStatusNotBetween(String value1, String value2) {
            addCriterion("warn_status not between", value1, value2, "warnStatus");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonIsNull() {
            addCriterion("back_response_person is null");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonIsNotNull() {
            addCriterion("back_response_person is not null");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonEqualTo(String value) {
            addCriterion("back_response_person =", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonNotEqualTo(String value) {
            addCriterion("back_response_person <>", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonGreaterThan(String value) {
            addCriterion("back_response_person >", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonGreaterThanOrEqualTo(String value) {
            addCriterion("back_response_person >=", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonLessThan(String value) {
            addCriterion("back_response_person <", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonLessThanOrEqualTo(String value) {
            addCriterion("back_response_person <=", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonLike(String value) {
            addCriterion("back_response_person like", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonNotLike(String value) {
            addCriterion("back_response_person not like", value, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonIn(List<String> values) {
            addCriterion("back_response_person in", values, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonNotIn(List<String> values) {
            addCriterion("back_response_person not in", values, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonBetween(String value1, String value2) {
            addCriterion("back_response_person between", value1, value2, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackResponsePersonNotBetween(String value1, String value2) {
            addCriterion("back_response_person not between", value1, value2, "backResponsePerson");
            return (Criteria) this;
        }

        public Criteria andBackDateIsNull() {
            addCriterion("back_date is null");
            return (Criteria) this;
        }

        public Criteria andBackDateIsNotNull() {
            addCriterion("back_date is not null");
            return (Criteria) this;
        }

        public Criteria andBackDateEqualTo(Date value) {
            addCriterion("back_date =", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateNotEqualTo(Date value) {
            addCriterion("back_date <>", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateGreaterThan(Date value) {
            addCriterion("back_date >", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateGreaterThanOrEqualTo(Date value) {
            addCriterion("back_date >=", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateLessThan(Date value) {
            addCriterion("back_date <", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateLessThanOrEqualTo(Date value) {
            addCriterion("back_date <=", value, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateIn(List<Date> values) {
            addCriterion("back_date in", values, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateNotIn(List<Date> values) {
            addCriterion("back_date not in", values, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateBetween(Date value1, Date value2) {
            addCriterion("back_date between", value1, value2, "backDate");
            return (Criteria) this;
        }

        public Criteria andBackDateNotBetween(Date value1, Date value2) {
            addCriterion("back_date not between", value1, value2, "backDate");
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