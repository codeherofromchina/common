package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class OrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderExample() {
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

        public Criteria andFrameworkNoIsNull() {
            addCriterion("framework_no is null");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoIsNotNull() {
            addCriterion("framework_no is not null");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoEqualTo(String value) {
            addCriterion("framework_no =", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoNotEqualTo(String value) {
            addCriterion("framework_no <>", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoGreaterThan(String value) {
            addCriterion("framework_no >", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoGreaterThanOrEqualTo(String value) {
            addCriterion("framework_no >=", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoLessThan(String value) {
            addCriterion("framework_no <", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoLessThanOrEqualTo(String value) {
            addCriterion("framework_no <=", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoLike(String value) {
            addCriterion("framework_no like", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoNotLike(String value) {
            addCriterion("framework_no not like", value, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoIn(List<String> values) {
            addCriterion("framework_no in", values, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoNotIn(List<String> values) {
            addCriterion("framework_no not in", values, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoBetween(String value1, String value2) {
            addCriterion("framework_no between", value1, value2, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andFrameworkNoNotBetween(String value1, String value2) {
            addCriterion("framework_no not between", value1, value2, "frameworkNo");
            return (Criteria) this;
        }

        public Criteria andContractNoOsIsNull() {
            addCriterion("contract_no_os is null");
            return (Criteria) this;
        }

        public Criteria andContractNoOsIsNotNull() {
            addCriterion("contract_no_os is not null");
            return (Criteria) this;
        }

        public Criteria andContractNoOsEqualTo(String value) {
            addCriterion("contract_no_os =", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsNotEqualTo(String value) {
            addCriterion("contract_no_os <>", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsGreaterThan(String value) {
            addCriterion("contract_no_os >", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsGreaterThanOrEqualTo(String value) {
            addCriterion("contract_no_os >=", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsLessThan(String value) {
            addCriterion("contract_no_os <", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsLessThanOrEqualTo(String value) {
            addCriterion("contract_no_os <=", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsLike(String value) {
            addCriterion("contract_no_os like", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsNotLike(String value) {
            addCriterion("contract_no_os not like", value, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsIn(List<String> values) {
            addCriterion("contract_no_os in", values, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsNotIn(List<String> values) {
            addCriterion("contract_no_os not in", values, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsBetween(String value1, String value2) {
            addCriterion("contract_no_os between", value1, value2, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andContractNoOsNotBetween(String value1, String value2) {
            addCriterion("contract_no_os not between", value1, value2, "contractNoOs");
            return (Criteria) this;
        }

        public Criteria andPoNoIsNull() {
            addCriterion("po_no is null");
            return (Criteria) this;
        }

        public Criteria andPoNoIsNotNull() {
            addCriterion("po_no is not null");
            return (Criteria) this;
        }

        public Criteria andPoNoEqualTo(String value) {
            addCriterion("po_no =", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotEqualTo(String value) {
            addCriterion("po_no <>", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThan(String value) {
            addCriterion("po_no >", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoGreaterThanOrEqualTo(String value) {
            addCriterion("po_no >=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThan(String value) {
            addCriterion("po_no <", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLessThanOrEqualTo(String value) {
            addCriterion("po_no <=", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoLike(String value) {
            addCriterion("po_no like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotLike(String value) {
            addCriterion("po_no not like", value, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoIn(List<String> values) {
            addCriterion("po_no in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotIn(List<String> values) {
            addCriterion("po_no not in", values, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoBetween(String value1, String value2) {
            addCriterion("po_no between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andPoNoNotBetween(String value1, String value2) {
            addCriterion("po_no not between", value1, value2, "poNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoIsNull() {
            addCriterion("logi_quote_no is null");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoIsNotNull() {
            addCriterion("logi_quote_no is not null");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoEqualTo(String value) {
            addCriterion("logi_quote_no =", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoNotEqualTo(String value) {
            addCriterion("logi_quote_no <>", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoGreaterThan(String value) {
            addCriterion("logi_quote_no >", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoGreaterThanOrEqualTo(String value) {
            addCriterion("logi_quote_no >=", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoLessThan(String value) {
            addCriterion("logi_quote_no <", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoLessThanOrEqualTo(String value) {
            addCriterion("logi_quote_no <=", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoLike(String value) {
            addCriterion("logi_quote_no like", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoNotLike(String value) {
            addCriterion("logi_quote_no not like", value, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoIn(List<String> values) {
            addCriterion("logi_quote_no in", values, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoNotIn(List<String> values) {
            addCriterion("logi_quote_no not in", values, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoBetween(String value1, String value2) {
            addCriterion("logi_quote_no between", value1, value2, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andLogiQuoteNoNotBetween(String value1, String value2) {
            addCriterion("logi_quote_no not between", value1, value2, "logiQuoteNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoIsNull() {
            addCriterion("inquiry_no is null");
            return (Criteria) this;
        }

        public Criteria andInquiryNoIsNotNull() {
            addCriterion("inquiry_no is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryNoEqualTo(String value) {
            addCriterion("inquiry_no =", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoNotEqualTo(String value) {
            addCriterion("inquiry_no <>", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoGreaterThan(String value) {
            addCriterion("inquiry_no >", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_no >=", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoLessThan(String value) {
            addCriterion("inquiry_no <", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoLessThanOrEqualTo(String value) {
            addCriterion("inquiry_no <=", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoLike(String value) {
            addCriterion("inquiry_no like", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoNotLike(String value) {
            addCriterion("inquiry_no not like", value, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoIn(List<String> values) {
            addCriterion("inquiry_no in", values, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoNotIn(List<String> values) {
            addCriterion("inquiry_no not in", values, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoBetween(String value1, String value2) {
            addCriterion("inquiry_no between", value1, value2, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andInquiryNoNotBetween(String value1, String value2) {
            addCriterion("inquiry_no not between", value1, value2, "inquiryNo");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNull() {
            addCriterion("order_type is null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIsNotNull() {
            addCriterion("order_type is not null");
            return (Criteria) this;
        }

        public Criteria andOrderTypeEqualTo(Integer value) {
            addCriterion("order_type =", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotEqualTo(Integer value) {
            addCriterion("order_type <>", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThan(Integer value) {
            addCriterion("order_type >", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_type >=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThan(Integer value) {
            addCriterion("order_type <", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeLessThanOrEqualTo(Integer value) {
            addCriterion("order_type <=", value, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeIn(List<Integer> values) {
            addCriterion("order_type in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotIn(List<Integer> values) {
            addCriterion("order_type not in", values, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeBetween(Integer value1, Integer value2) {
            addCriterion("order_type between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("order_type not between", value1, value2, "orderType");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIsNull() {
            addCriterion("order_source is null");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIsNotNull() {
            addCriterion("order_source is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSourceEqualTo(Integer value) {
            addCriterion("order_source =", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotEqualTo(Integer value) {
            addCriterion("order_source <>", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceGreaterThan(Integer value) {
            addCriterion("order_source >", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_source >=", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceLessThan(Integer value) {
            addCriterion("order_source <", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceLessThanOrEqualTo(Integer value) {
            addCriterion("order_source <=", value, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceIn(List<Integer> values) {
            addCriterion("order_source in", values, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotIn(List<Integer> values) {
            addCriterion("order_source not in", values, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceBetween(Integer value1, Integer value2) {
            addCriterion("order_source between", value1, value2, "orderSource");
            return (Criteria) this;
        }

        public Criteria andOrderSourceNotBetween(Integer value1, Integer value2) {
            addCriterion("order_source not between", value1, value2, "orderSource");
            return (Criteria) this;
        }

        public Criteria andSigningDateIsNull() {
            addCriterion("signing_date is null");
            return (Criteria) this;
        }

        public Criteria andSigningDateIsNotNull() {
            addCriterion("signing_date is not null");
            return (Criteria) this;
        }

        public Criteria andSigningDateEqualTo(Date value) {
            addCriterionForJDBCDate("signing_date =", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("signing_date <>", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateGreaterThan(Date value) {
            addCriterionForJDBCDate("signing_date >", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("signing_date >=", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateLessThan(Date value) {
            addCriterionForJDBCDate("signing_date <", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("signing_date <=", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateIn(List<Date> values) {
            addCriterionForJDBCDate("signing_date in", values, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("signing_date not in", values, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("signing_date between", value1, value2, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("signing_date not between", value1, value2, "signingDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNull() {
            addCriterion("delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNotNull() {
            addCriterion("delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualTo(String value) {
            addCriterion("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(String value) {
            addCriterion("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(String value) {
            addCriterion("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(String value) {
            addCriterion("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(String value) {
            addCriterion("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLike(String value) {
            addCriterion("delivery_date like", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotLike(String value) {
            addCriterion("delivery_date not like", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<String> values) {
            addCriterion("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<String> values) {
            addCriterion("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(String value1, String value2) {
            addCriterion("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(String value1, String value2) {
            addCriterion("delivery_date not between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andSigningCoIsNull() {
            addCriterion("signing_co is null");
            return (Criteria) this;
        }

        public Criteria andSigningCoIsNotNull() {
            addCriterion("signing_co is not null");
            return (Criteria) this;
        }

        public Criteria andSigningCoEqualTo(String value) {
            addCriterion("signing_co =", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoNotEqualTo(String value) {
            addCriterion("signing_co <>", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoGreaterThan(String value) {
            addCriterion("signing_co >", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoGreaterThanOrEqualTo(String value) {
            addCriterion("signing_co >=", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoLessThan(String value) {
            addCriterion("signing_co <", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoLessThanOrEqualTo(String value) {
            addCriterion("signing_co <=", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoLike(String value) {
            addCriterion("signing_co like", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoNotLike(String value) {
            addCriterion("signing_co not like", value, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoIn(List<String> values) {
            addCriterion("signing_co in", values, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoNotIn(List<String> values) {
            addCriterion("signing_co not in", values, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoBetween(String value1, String value2) {
            addCriterion("signing_co between", value1, value2, "signingCo");
            return (Criteria) this;
        }

        public Criteria andSigningCoNotBetween(String value1, String value2) {
            addCriterion("signing_co not between", value1, value2, "signingCo");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("agent_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(Integer value) {
            addCriterion("agent_id =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(Integer value) {
            addCriterion("agent_id <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(Integer value) {
            addCriterion("agent_id >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_id >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(Integer value) {
            addCriterion("agent_id <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_id <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<Integer> values) {
            addCriterion("agent_id in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<Integer> values) {
            addCriterion("agent_id not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_id between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_id not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNull() {
            addCriterion("agent_name is null");
            return (Criteria) this;
        }

        public Criteria andAgentNameIsNotNull() {
            addCriterion("agent_name is not null");
            return (Criteria) this;
        }

        public Criteria andAgentNameEqualTo(String value) {
            addCriterion("agent_name =", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotEqualTo(String value) {
            addCriterion("agent_name <>", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThan(String value) {
            addCriterion("agent_name >", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameGreaterThanOrEqualTo(String value) {
            addCriterion("agent_name >=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThan(String value) {
            addCriterion("agent_name <", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLessThanOrEqualTo(String value) {
            addCriterion("agent_name <=", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameLike(String value) {
            addCriterion("agent_name like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotLike(String value) {
            addCriterion("agent_name not like", value, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameIn(List<String> values) {
            addCriterion("agent_name in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotIn(List<String> values) {
            addCriterion("agent_name not in", values, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameBetween(String value1, String value2) {
            addCriterion("agent_name between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andAgentNameNotBetween(String value1, String value2) {
            addCriterion("agent_name not between", value1, value2, "agentName");
            return (Criteria) this;
        }

        public Criteria andExecCoIdIsNull() {
            addCriterion("exec_co_id is null");
            return (Criteria) this;
        }

        public Criteria andExecCoIdIsNotNull() {
            addCriterion("exec_co_id is not null");
            return (Criteria) this;
        }

        public Criteria andExecCoIdEqualTo(Integer value) {
            addCriterion("exec_co_id =", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdNotEqualTo(Integer value) {
            addCriterion("exec_co_id <>", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdGreaterThan(Integer value) {
            addCriterion("exec_co_id >", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("exec_co_id >=", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdLessThan(Integer value) {
            addCriterion("exec_co_id <", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdLessThanOrEqualTo(Integer value) {
            addCriterion("exec_co_id <=", value, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdIn(List<Integer> values) {
            addCriterion("exec_co_id in", values, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdNotIn(List<Integer> values) {
            addCriterion("exec_co_id not in", values, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdBetween(Integer value1, Integer value2) {
            addCriterion("exec_co_id between", value1, value2, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("exec_co_id not between", value1, value2, "execCoId");
            return (Criteria) this;
        }

        public Criteria andExecCoNameIsNull() {
            addCriterion("exec_co_name is null");
            return (Criteria) this;
        }

        public Criteria andExecCoNameIsNotNull() {
            addCriterion("exec_co_name is not null");
            return (Criteria) this;
        }

        public Criteria andExecCoNameEqualTo(String value) {
            addCriterion("exec_co_name =", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameNotEqualTo(String value) {
            addCriterion("exec_co_name <>", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameGreaterThan(String value) {
            addCriterion("exec_co_name >", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameGreaterThanOrEqualTo(String value) {
            addCriterion("exec_co_name >=", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameLessThan(String value) {
            addCriterion("exec_co_name <", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameLessThanOrEqualTo(String value) {
            addCriterion("exec_co_name <=", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameLike(String value) {
            addCriterion("exec_co_name like", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameNotLike(String value) {
            addCriterion("exec_co_name not like", value, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameIn(List<String> values) {
            addCriterion("exec_co_name in", values, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameNotIn(List<String> values) {
            addCriterion("exec_co_name not in", values, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameBetween(String value1, String value2) {
            addCriterion("exec_co_name between", value1, value2, "execCoName");
            return (Criteria) this;
        }

        public Criteria andExecCoNameNotBetween(String value1, String value2) {
            addCriterion("exec_co_name not between", value1, value2, "execCoName");
            return (Criteria) this;
        }

        public Criteria andRegionIsNull() {
            addCriterion("region is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            addCriterion("region is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(String value) {
            addCriterion("region =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(String value) {
            addCriterion("region <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThan(String value) {
            addCriterion("region >", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThanOrEqualTo(String value) {
            addCriterion("region >=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThan(String value) {
            addCriterion("region <", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThanOrEqualTo(String value) {
            addCriterion("region <=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLike(String value) {
            addCriterion("region like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotLike(String value) {
            addCriterion("region not like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(List<String> values) {
            addCriterion("region in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(List<String> values) {
            addCriterion("region not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(String value1, String value2) {
            addCriterion("region between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(String value1, String value2) {
            addCriterion("region not between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameIsNull() {
            addCriterion("distribution_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameIsNotNull() {
            addCriterion("distribution_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameEqualTo(String value) {
            addCriterion("distribution_dept_name =", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameNotEqualTo(String value) {
            addCriterion("distribution_dept_name <>", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameGreaterThan(String value) {
            addCriterion("distribution_dept_name >", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("distribution_dept_name >=", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameLessThan(String value) {
            addCriterion("distribution_dept_name <", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameLessThanOrEqualTo(String value) {
            addCriterion("distribution_dept_name <=", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameLike(String value) {
            addCriterion("distribution_dept_name like", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameNotLike(String value) {
            addCriterion("distribution_dept_name not like", value, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameIn(List<String> values) {
            addCriterion("distribution_dept_name in", values, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameNotIn(List<String> values) {
            addCriterion("distribution_dept_name not in", values, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameBetween(String value1, String value2) {
            addCriterion("distribution_dept_name between", value1, value2, "distributionDeptName");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptNameNotBetween(String value1, String value2) {
            addCriterion("distribution_dept_name not between", value1, value2, "distributionDeptName");
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

        public Criteria andCrmCodeIsNull() {
            addCriterion("crm_code is null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeIsNotNull() {
            addCriterion("crm_code is not null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeEqualTo(String value) {
            addCriterion("crm_code =", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeNotEqualTo(String value) {
            addCriterion("crm_code <>", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeGreaterThan(String value) {
            addCriterion("crm_code >", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeGreaterThanOrEqualTo(String value) {
            addCriterion("crm_code >=", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeLessThan(String value) {
            addCriterion("crm_code <", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeLessThanOrEqualTo(String value) {
            addCriterion("crm_code <=", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeLike(String value) {
            addCriterion("crm_code like", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeNotLike(String value) {
            addCriterion("crm_code not like", value, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeIn(List<String> values) {
            addCriterion("crm_code in", values, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeNotIn(List<String> values) {
            addCriterion("crm_code not in", values, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeBetween(String value1, String value2) {
            addCriterion("crm_code between", value1, value2, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCrmCodeNotBetween(String value1, String value2) {
            addCriterion("crm_code not between", value1, value2, "crmCode");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeIsNull() {
            addCriterion("customer_type is null");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeIsNotNull() {
            addCriterion("customer_type is not null");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeEqualTo(Integer value) {
            addCriterion("customer_type =", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotEqualTo(Integer value) {
            addCriterion("customer_type <>", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeGreaterThan(Integer value) {
            addCriterion("customer_type >", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("customer_type >=", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeLessThan(Integer value) {
            addCriterion("customer_type <", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeLessThanOrEqualTo(Integer value) {
            addCriterion("customer_type <=", value, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeIn(List<Integer> values) {
            addCriterion("customer_type in", values, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotIn(List<Integer> values) {
            addCriterion("customer_type not in", values, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeBetween(Integer value1, Integer value2) {
            addCriterion("customer_type between", value1, value2, "customerType");
            return (Criteria) this;
        }

        public Criteria andCustomerTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("customer_type not between", value1, value2, "customerType");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIsNull() {
            addCriterion("per_liable_repay is null");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIsNotNull() {
            addCriterion("per_liable_repay is not null");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayEqualTo(String value) {
            addCriterion("per_liable_repay =", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayNotEqualTo(String value) {
            addCriterion("per_liable_repay <>", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayGreaterThan(String value) {
            addCriterion("per_liable_repay >", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayGreaterThanOrEqualTo(String value) {
            addCriterion("per_liable_repay >=", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayLessThan(String value) {
            addCriterion("per_liable_repay <", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayLessThanOrEqualTo(String value) {
            addCriterion("per_liable_repay <=", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayLike(String value) {
            addCriterion("per_liable_repay like", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayNotLike(String value) {
            addCriterion("per_liable_repay not like", value, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIn(List<String> values) {
            addCriterion("per_liable_repay in", values, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayNotIn(List<String> values) {
            addCriterion("per_liable_repay not in", values, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayBetween(String value1, String value2) {
            addCriterion("per_liable_repay between", value1, value2, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayNotBetween(String value1, String value2) {
            addCriterion("per_liable_repay not between", value1, value2, "perLiableRepay");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdIsNull() {
            addCriterion("business_unit_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdIsNotNull() {
            addCriterion("business_unit_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdEqualTo(Integer value) {
            addCriterion("business_unit_id =", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdNotEqualTo(Integer value) {
            addCriterion("business_unit_id <>", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdGreaterThan(Integer value) {
            addCriterion("business_unit_id >", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("business_unit_id >=", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdLessThan(Integer value) {
            addCriterion("business_unit_id <", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdLessThanOrEqualTo(Integer value) {
            addCriterion("business_unit_id <=", value, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdIn(List<Integer> values) {
            addCriterion("business_unit_id in", values, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdNotIn(List<Integer> values) {
            addCriterion("business_unit_id not in", values, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdBetween(Integer value1, Integer value2) {
            addCriterion("business_unit_id between", value1, value2, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitIdNotBetween(Integer value1, Integer value2) {
            addCriterion("business_unit_id not between", value1, value2, "businessUnitId");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameIsNull() {
            addCriterion("business_unit_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameIsNotNull() {
            addCriterion("business_unit_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameEqualTo(String value) {
            addCriterion("business_unit_name =", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameNotEqualTo(String value) {
            addCriterion("business_unit_name <>", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameGreaterThan(String value) {
            addCriterion("business_unit_name >", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_unit_name >=", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameLessThan(String value) {
            addCriterion("business_unit_name <", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameLessThanOrEqualTo(String value) {
            addCriterion("business_unit_name <=", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameLike(String value) {
            addCriterion("business_unit_name like", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameNotLike(String value) {
            addCriterion("business_unit_name not like", value, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameIn(List<String> values) {
            addCriterion("business_unit_name in", values, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameNotIn(List<String> values) {
            addCriterion("business_unit_name not in", values, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameBetween(String value1, String value2) {
            addCriterion("business_unit_name between", value1, value2, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andBusinessUnitNameNotBetween(String value1, String value2) {
            addCriterion("business_unit_name not between", value1, value2, "businessUnitName");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdIsNull() {
            addCriterion("technical_id is null");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdIsNotNull() {
            addCriterion("technical_id is not null");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdEqualTo(Integer value) {
            addCriterion("technical_id =", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdNotEqualTo(Integer value) {
            addCriterion("technical_id <>", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdGreaterThan(Integer value) {
            addCriterion("technical_id >", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("technical_id >=", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdLessThan(Integer value) {
            addCriterion("technical_id <", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdLessThanOrEqualTo(Integer value) {
            addCriterion("technical_id <=", value, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdIn(List<Integer> values) {
            addCriterion("technical_id in", values, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdNotIn(List<Integer> values) {
            addCriterion("technical_id not in", values, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdBetween(Integer value1, Integer value2) {
            addCriterion("technical_id between", value1, value2, "technicalId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdNotBetween(Integer value1, Integer value2) {
            addCriterion("technical_id not between", value1, value2, "technicalId");
            return (Criteria) this;
        }

        public Criteria andGrantTypeIsNull() {
            addCriterion("grant_type is null");
            return (Criteria) this;
        }

        public Criteria andGrantTypeIsNotNull() {
            addCriterion("grant_type is not null");
            return (Criteria) this;
        }

        public Criteria andGrantTypeEqualTo(String value) {
            addCriterion("grant_type =", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotEqualTo(String value) {
            addCriterion("grant_type <>", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeGreaterThan(String value) {
            addCriterion("grant_type >", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeGreaterThanOrEqualTo(String value) {
            addCriterion("grant_type >=", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLessThan(String value) {
            addCriterion("grant_type <", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLessThanOrEqualTo(String value) {
            addCriterion("grant_type <=", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeLike(String value) {
            addCriterion("grant_type like", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotLike(String value) {
            addCriterion("grant_type not like", value, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeIn(List<String> values) {
            addCriterion("grant_type in", values, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotIn(List<String> values) {
            addCriterion("grant_type not in", values, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeBetween(String value1, String value2) {
            addCriterion("grant_type between", value1, value2, "grantType");
            return (Criteria) this;
        }

        public Criteria andGrantTypeNotBetween(String value1, String value2) {
            addCriterion("grant_type not between", value1, value2, "grantType");
            return (Criteria) this;
        }

        public Criteria andPreinvestIsNull() {
            addCriterion("preinvest is null");
            return (Criteria) this;
        }

        public Criteria andPreinvestIsNotNull() {
            addCriterion("preinvest is not null");
            return (Criteria) this;
        }

        public Criteria andPreinvestEqualTo(Integer value) {
            addCriterion("preinvest =", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestNotEqualTo(Integer value) {
            addCriterion("preinvest <>", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestGreaterThan(Integer value) {
            addCriterion("preinvest >", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestGreaterThanOrEqualTo(Integer value) {
            addCriterion("preinvest >=", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestLessThan(Integer value) {
            addCriterion("preinvest <", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestLessThanOrEqualTo(Integer value) {
            addCriterion("preinvest <=", value, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestIn(List<Integer> values) {
            addCriterion("preinvest in", values, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestNotIn(List<Integer> values) {
            addCriterion("preinvest not in", values, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestBetween(Integer value1, Integer value2) {
            addCriterion("preinvest between", value1, value2, "preinvest");
            return (Criteria) this;
        }

        public Criteria andPreinvestNotBetween(Integer value1, Integer value2) {
            addCriterion("preinvest not between", value1, value2, "preinvest");
            return (Criteria) this;
        }

        public Criteria andFinancingIsNull() {
            addCriterion("financing is null");
            return (Criteria) this;
        }

        public Criteria andFinancingIsNotNull() {
            addCriterion("financing is not null");
            return (Criteria) this;
        }

        public Criteria andFinancingEqualTo(Integer value) {
            addCriterion("financing =", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingNotEqualTo(Integer value) {
            addCriterion("financing <>", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingGreaterThan(Integer value) {
            addCriterion("financing >", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingGreaterThanOrEqualTo(Integer value) {
            addCriterion("financing >=", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingLessThan(Integer value) {
            addCriterion("financing <", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingLessThanOrEqualTo(Integer value) {
            addCriterion("financing <=", value, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingIn(List<Integer> values) {
            addCriterion("financing in", values, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingNotIn(List<Integer> values) {
            addCriterion("financing not in", values, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingBetween(Integer value1, Integer value2) {
            addCriterion("financing between", value1, value2, "financing");
            return (Criteria) this;
        }

        public Criteria andFinancingNotBetween(Integer value1, Integer value2) {
            addCriterion("financing not between", value1, value2, "financing");
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

        public Criteria andFromCountryIsNull() {
            addCriterion("from_country is null");
            return (Criteria) this;
        }

        public Criteria andFromCountryIsNotNull() {
            addCriterion("from_country is not null");
            return (Criteria) this;
        }

        public Criteria andFromCountryEqualTo(String value) {
            addCriterion("from_country =", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryNotEqualTo(String value) {
            addCriterion("from_country <>", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryGreaterThan(String value) {
            addCriterion("from_country >", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryGreaterThanOrEqualTo(String value) {
            addCriterion("from_country >=", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryLessThan(String value) {
            addCriterion("from_country <", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryLessThanOrEqualTo(String value) {
            addCriterion("from_country <=", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryLike(String value) {
            addCriterion("from_country like", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryNotLike(String value) {
            addCriterion("from_country not like", value, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryIn(List<String> values) {
            addCriterion("from_country in", values, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryNotIn(List<String> values) {
            addCriterion("from_country not in", values, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryBetween(String value1, String value2) {
            addCriterion("from_country between", value1, value2, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromCountryNotBetween(String value1, String value2) {
            addCriterion("from_country not between", value1, value2, "fromCountry");
            return (Criteria) this;
        }

        public Criteria andFromPortIsNull() {
            addCriterion("from_port is null");
            return (Criteria) this;
        }

        public Criteria andFromPortIsNotNull() {
            addCriterion("from_port is not null");
            return (Criteria) this;
        }

        public Criteria andFromPortEqualTo(String value) {
            addCriterion("from_port =", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotEqualTo(String value) {
            addCriterion("from_port <>", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortGreaterThan(String value) {
            addCriterion("from_port >", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortGreaterThanOrEqualTo(String value) {
            addCriterion("from_port >=", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLessThan(String value) {
            addCriterion("from_port <", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLessThanOrEqualTo(String value) {
            addCriterion("from_port <=", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortLike(String value) {
            addCriterion("from_port like", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotLike(String value) {
            addCriterion("from_port not like", value, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortIn(List<String> values) {
            addCriterion("from_port in", values, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotIn(List<String> values) {
            addCriterion("from_port not in", values, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortBetween(String value1, String value2) {
            addCriterion("from_port between", value1, value2, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPortNotBetween(String value1, String value2) {
            addCriterion("from_port not between", value1, value2, "fromPort");
            return (Criteria) this;
        }

        public Criteria andFromPlaceIsNull() {
            addCriterion("from_place is null");
            return (Criteria) this;
        }

        public Criteria andFromPlaceIsNotNull() {
            addCriterion("from_place is not null");
            return (Criteria) this;
        }

        public Criteria andFromPlaceEqualTo(String value) {
            addCriterion("from_place =", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceNotEqualTo(String value) {
            addCriterion("from_place <>", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceGreaterThan(String value) {
            addCriterion("from_place >", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("from_place >=", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceLessThan(String value) {
            addCriterion("from_place <", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceLessThanOrEqualTo(String value) {
            addCriterion("from_place <=", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceLike(String value) {
            addCriterion("from_place like", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceNotLike(String value) {
            addCriterion("from_place not like", value, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceIn(List<String> values) {
            addCriterion("from_place in", values, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceNotIn(List<String> values) {
            addCriterion("from_place not in", values, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceBetween(String value1, String value2) {
            addCriterion("from_place between", value1, value2, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andFromPlaceNotBetween(String value1, String value2) {
            addCriterion("from_place not between", value1, value2, "fromPlace");
            return (Criteria) this;
        }

        public Criteria andToCountryIsNull() {
            addCriterion("to_country is null");
            return (Criteria) this;
        }

        public Criteria andToCountryIsNotNull() {
            addCriterion("to_country is not null");
            return (Criteria) this;
        }

        public Criteria andToCountryEqualTo(String value) {
            addCriterion("to_country =", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryNotEqualTo(String value) {
            addCriterion("to_country <>", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryGreaterThan(String value) {
            addCriterion("to_country >", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryGreaterThanOrEqualTo(String value) {
            addCriterion("to_country >=", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryLessThan(String value) {
            addCriterion("to_country <", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryLessThanOrEqualTo(String value) {
            addCriterion("to_country <=", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryLike(String value) {
            addCriterion("to_country like", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryNotLike(String value) {
            addCriterion("to_country not like", value, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryIn(List<String> values) {
            addCriterion("to_country in", values, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryNotIn(List<String> values) {
            addCriterion("to_country not in", values, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryBetween(String value1, String value2) {
            addCriterion("to_country between", value1, value2, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToCountryNotBetween(String value1, String value2) {
            addCriterion("to_country not between", value1, value2, "toCountry");
            return (Criteria) this;
        }

        public Criteria andToPortIsNull() {
            addCriterion("to_port is null");
            return (Criteria) this;
        }

        public Criteria andToPortIsNotNull() {
            addCriterion("to_port is not null");
            return (Criteria) this;
        }

        public Criteria andToPortEqualTo(String value) {
            addCriterion("to_port =", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotEqualTo(String value) {
            addCriterion("to_port <>", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortGreaterThan(String value) {
            addCriterion("to_port >", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortGreaterThanOrEqualTo(String value) {
            addCriterion("to_port >=", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLessThan(String value) {
            addCriterion("to_port <", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLessThanOrEqualTo(String value) {
            addCriterion("to_port <=", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortLike(String value) {
            addCriterion("to_port like", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotLike(String value) {
            addCriterion("to_port not like", value, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortIn(List<String> values) {
            addCriterion("to_port in", values, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotIn(List<String> values) {
            addCriterion("to_port not in", values, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortBetween(String value1, String value2) {
            addCriterion("to_port between", value1, value2, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPortNotBetween(String value1, String value2) {
            addCriterion("to_port not between", value1, value2, "toPort");
            return (Criteria) this;
        }

        public Criteria andToPlaceIsNull() {
            addCriterion("to_place is null");
            return (Criteria) this;
        }

        public Criteria andToPlaceIsNotNull() {
            addCriterion("to_place is not null");
            return (Criteria) this;
        }

        public Criteria andToPlaceEqualTo(String value) {
            addCriterion("to_place =", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotEqualTo(String value) {
            addCriterion("to_place <>", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceGreaterThan(String value) {
            addCriterion("to_place >", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("to_place >=", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLessThan(String value) {
            addCriterion("to_place <", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLessThanOrEqualTo(String value) {
            addCriterion("to_place <=", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceLike(String value) {
            addCriterion("to_place like", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotLike(String value) {
            addCriterion("to_place not like", value, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceIn(List<String> values) {
            addCriterion("to_place in", values, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotIn(List<String> values) {
            addCriterion("to_place not in", values, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceBetween(String value1, String value2) {
            addCriterion("to_place between", value1, value2, "toPlace");
            return (Criteria) this;
        }

        public Criteria andToPlaceNotBetween(String value1, String value2) {
            addCriterion("to_place not between", value1, value2, "toPlace");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIsNull() {
            addCriterion("total_price is null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIsNotNull() {
            addCriterion("total_price is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceEqualTo(BigDecimal value) {
            addCriterion("total_price =", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("total_price <>", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("total_price >", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price >=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThan(BigDecimal value) {
            addCriterion("total_price <", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price <=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIn(List<BigDecimal> values) {
            addCriterion("total_price in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("total_price not in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price between", value1, value2, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price not between", value1, value2, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdIsNull() {
            addCriterion("distribution_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdIsNotNull() {
            addCriterion("distribution_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdEqualTo(Integer value) {
            addCriterion("distribution_dept_id =", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdNotEqualTo(Integer value) {
            addCriterion("distribution_dept_id <>", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdGreaterThan(Integer value) {
            addCriterion("distribution_dept_id >", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("distribution_dept_id >=", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdLessThan(Integer value) {
            addCriterion("distribution_dept_id <", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("distribution_dept_id <=", value, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdIn(List<Integer> values) {
            addCriterion("distribution_dept_id in", values, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdNotIn(List<Integer> values) {
            addCriterion("distribution_dept_id not in", values, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("distribution_dept_id between", value1, value2, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andDistributionDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("distribution_dept_id not between", value1, value2, "distributionDeptId");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptIsNull() {
            addCriterion("technical_id_dept is null");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptIsNotNull() {
            addCriterion("technical_id_dept is not null");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptEqualTo(String value) {
            addCriterion("technical_id_dept =", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptNotEqualTo(String value) {
            addCriterion("technical_id_dept <>", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptGreaterThan(String value) {
            addCriterion("technical_id_dept >", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptGreaterThanOrEqualTo(String value) {
            addCriterion("technical_id_dept >=", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptLessThan(String value) {
            addCriterion("technical_id_dept <", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptLessThanOrEqualTo(String value) {
            addCriterion("technical_id_dept <=", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptLike(String value) {
            addCriterion("technical_id_dept like", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptNotLike(String value) {
            addCriterion("technical_id_dept not like", value, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptIn(List<String> values) {
            addCriterion("technical_id_dept in", values, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptNotIn(List<String> values) {
            addCriterion("technical_id_dept not in", values, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptBetween(String value1, String value2) {
            addCriterion("technical_id_dept between", value1, value2, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andTechnicalIdDeptNotBetween(String value1, String value2) {
            addCriterion("technical_id_dept not between", value1, value2, "technicalIdDept");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnIsNull() {
            addCriterion("currency_bn is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnIsNotNull() {
            addCriterion("currency_bn is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnEqualTo(String value) {
            addCriterion("currency_bn =", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnNotEqualTo(String value) {
            addCriterion("currency_bn <>", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnGreaterThan(String value) {
            addCriterion("currency_bn >", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnGreaterThanOrEqualTo(String value) {
            addCriterion("currency_bn >=", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnLessThan(String value) {
            addCriterion("currency_bn <", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnLessThanOrEqualTo(String value) {
            addCriterion("currency_bn <=", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnLike(String value) {
            addCriterion("currency_bn like", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnNotLike(String value) {
            addCriterion("currency_bn not like", value, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnIn(List<String> values) {
            addCriterion("currency_bn in", values, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnNotIn(List<String> values) {
            addCriterion("currency_bn not in", values, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnBetween(String value1, String value2) {
            addCriterion("currency_bn between", value1, value2, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andCurrencyBnNotBetween(String value1, String value2) {
            addCriterion("currency_bn not between", value1, value2, "currencyBn");
            return (Criteria) this;
        }

        public Criteria andTaxBearingIsNull() {
            addCriterion("tax_bearing is null");
            return (Criteria) this;
        }

        public Criteria andTaxBearingIsNotNull() {
            addCriterion("tax_bearing is not null");
            return (Criteria) this;
        }

        public Criteria andTaxBearingEqualTo(Integer value) {
            addCriterion("tax_bearing =", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingNotEqualTo(Integer value) {
            addCriterion("tax_bearing <>", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingGreaterThan(Integer value) {
            addCriterion("tax_bearing >", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingGreaterThanOrEqualTo(Integer value) {
            addCriterion("tax_bearing >=", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingLessThan(Integer value) {
            addCriterion("tax_bearing <", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingLessThanOrEqualTo(Integer value) {
            addCriterion("tax_bearing <=", value, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingIn(List<Integer> values) {
            addCriterion("tax_bearing in", values, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingNotIn(List<Integer> values) {
            addCriterion("tax_bearing not in", values, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingBetween(Integer value1, Integer value2) {
            addCriterion("tax_bearing between", value1, value2, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andTaxBearingNotBetween(Integer value1, Integer value2) {
            addCriterion("tax_bearing not between", value1, value2, "taxBearing");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnIsNull() {
            addCriterion("payment_mode_bn is null");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnIsNotNull() {
            addCriterion("payment_mode_bn is not null");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnEqualTo(String value) {
            addCriterion("payment_mode_bn =", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnNotEqualTo(String value) {
            addCriterion("payment_mode_bn <>", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnGreaterThan(String value) {
            addCriterion("payment_mode_bn >", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnGreaterThanOrEqualTo(String value) {
            addCriterion("payment_mode_bn >=", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnLessThan(String value) {
            addCriterion("payment_mode_bn <", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnLessThanOrEqualTo(String value) {
            addCriterion("payment_mode_bn <=", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnLike(String value) {
            addCriterion("payment_mode_bn like", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnNotLike(String value) {
            addCriterion("payment_mode_bn not like", value, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnIn(List<String> values) {
            addCriterion("payment_mode_bn in", values, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnNotIn(List<String> values) {
            addCriterion("payment_mode_bn not in", values, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnBetween(String value1, String value2) {
            addCriterion("payment_mode_bn between", value1, value2, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andPaymentModeBnNotBetween(String value1, String value2) {
            addCriterion("payment_mode_bn not between", value1, value2, "paymentModeBn");
            return (Criteria) this;
        }

        public Criteria andQualityFundsIsNull() {
            addCriterion("quality_funds is null");
            return (Criteria) this;
        }

        public Criteria andQualityFundsIsNotNull() {
            addCriterion("quality_funds is not null");
            return (Criteria) this;
        }

        public Criteria andQualityFundsEqualTo(BigDecimal value) {
            addCriterion("quality_funds =", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsNotEqualTo(BigDecimal value) {
            addCriterion("quality_funds <>", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsGreaterThan(BigDecimal value) {
            addCriterion("quality_funds >", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quality_funds >=", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsLessThan(BigDecimal value) {
            addCriterion("quality_funds <", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quality_funds <=", value, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsIn(List<BigDecimal> values) {
            addCriterion("quality_funds in", values, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsNotIn(List<BigDecimal> values) {
            addCriterion("quality_funds not in", values, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quality_funds between", value1, value2, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andQualityFundsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quality_funds not between", value1, value2, "qualityFunds");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNull() {
            addCriterion("pay_status is null");
            return (Criteria) this;
        }

        public Criteria andPayStatusIsNotNull() {
            addCriterion("pay_status is not null");
            return (Criteria) this;
        }

        public Criteria andPayStatusEqualTo(Integer value) {
            addCriterion("pay_status =", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotEqualTo(Integer value) {
            addCriterion("pay_status <>", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThan(Integer value) {
            addCriterion("pay_status >", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_status >=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThan(Integer value) {
            addCriterion("pay_status <", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pay_status <=", value, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusIn(List<Integer> values) {
            addCriterion("pay_status in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotIn(List<Integer> values) {
            addCriterion("pay_status not in", values, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusBetween(Integer value1, Integer value2) {
            addCriterion("pay_status between", value1, value2, "payStatus");
            return (Criteria) this;
        }

        public Criteria andPayStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_status not between", value1, value2, "payStatus");
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

        public Criteria andDeliverConsignCIsNull() {
            addCriterion("deliver_consign_c is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCIsNotNull() {
            addCriterion("deliver_consign_c is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCEqualTo(Boolean value) {
            addCriterion("deliver_consign_c =", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCNotEqualTo(Boolean value) {
            addCriterion("deliver_consign_c <>", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCGreaterThan(Boolean value) {
            addCriterion("deliver_consign_c >", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCGreaterThanOrEqualTo(Boolean value) {
            addCriterion("deliver_consign_c >=", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCLessThan(Boolean value) {
            addCriterion("deliver_consign_c <", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCLessThanOrEqualTo(Boolean value) {
            addCriterion("deliver_consign_c <=", value, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCIn(List<Boolean> values) {
            addCriterion("deliver_consign_c in", values, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCNotIn(List<Boolean> values) {
            addCriterion("deliver_consign_c not in", values, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCBetween(Boolean value1, Boolean value2) {
            addCriterion("deliver_consign_c between", value1, value2, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignCNotBetween(Boolean value1, Boolean value2) {
            addCriterion("deliver_consign_c not between", value1, value2, "deliverConsignC");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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

        public Criteria andDeleteFlagIsNull() {
            addCriterion("delete_flag is null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIsNotNull() {
            addCriterion("delete_flag is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagEqualTo(Boolean value) {
            addCriterion("delete_flag =", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotEqualTo(Boolean value) {
            addCriterion("delete_flag <>", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThan(Boolean value) {
            addCriterion("delete_flag >", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagGreaterThanOrEqualTo(Boolean value) {
            addCriterion("delete_flag >=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThan(Boolean value) {
            addCriterion("delete_flag <", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagLessThanOrEqualTo(Boolean value) {
            addCriterion("delete_flag <=", value, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagIn(List<Boolean> values) {
            addCriterion("delete_flag in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotIn(List<Boolean> values) {
            addCriterion("delete_flag not in", values, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagBetween(Boolean value1, Boolean value2) {
            addCriterion("delete_flag between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteFlagNotBetween(Boolean value1, Boolean value2) {
            addCriterion("delete_flag not between", value1, value2, "deleteFlag");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNull() {
            addCriterion("delete_time is null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNotNull() {
            addCriterion("delete_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeEqualTo(Date value) {
            addCriterion("delete_time =", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotEqualTo(Date value) {
            addCriterion("delete_time <>", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThan(Date value) {
            addCriterion("delete_time >", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delete_time >=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThan(Date value) {
            addCriterion("delete_time <", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("delete_time <=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIn(List<Date> values) {
            addCriterion("delete_time in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotIn(List<Date> values) {
            addCriterion("delete_time not in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeBetween(Date value1, Date value2) {
            addCriterion("delete_time between", value1, value2, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("delete_time not between", value1, value2, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingIsNull() {
            addCriterion("receivable_account_remaining is null");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingIsNotNull() {
            addCriterion("receivable_account_remaining is not null");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingEqualTo(BigDecimal value) {
            addCriterion("receivable_account_remaining =", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingNotEqualTo(BigDecimal value) {
            addCriterion("receivable_account_remaining <>", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingGreaterThan(BigDecimal value) {
            addCriterion("receivable_account_remaining >", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_account_remaining >=", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingLessThan(BigDecimal value) {
            addCriterion("receivable_account_remaining <", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingLessThanOrEqualTo(BigDecimal value) {
            addCriterion("receivable_account_remaining <=", value, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingIn(List<BigDecimal> values) {
            addCriterion("receivable_account_remaining in", values, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingNotIn(List<BigDecimal> values) {
            addCriterion("receivable_account_remaining not in", values, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_account_remaining between", value1, value2, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andReceivableAccountRemainingNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("receivable_account_remaining not between", value1, value2, "receivableAccountRemaining");
            return (Criteria) this;
        }

        public Criteria andAcquireIdIsNull() {
            addCriterion("acquire_id is null");
            return (Criteria) this;
        }

        public Criteria andAcquireIdIsNotNull() {
            addCriterion("acquire_id is not null");
            return (Criteria) this;
        }

        public Criteria andAcquireIdEqualTo(Integer value) {
            addCriterion("acquire_id =", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdNotEqualTo(Integer value) {
            addCriterion("acquire_id <>", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdGreaterThan(Integer value) {
            addCriterion("acquire_id >", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("acquire_id >=", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdLessThan(Integer value) {
            addCriterion("acquire_id <", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdLessThanOrEqualTo(Integer value) {
            addCriterion("acquire_id <=", value, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdIn(List<Integer> values) {
            addCriterion("acquire_id in", values, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdNotIn(List<Integer> values) {
            addCriterion("acquire_id not in", values, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdBetween(Integer value1, Integer value2) {
            addCriterion("acquire_id between", value1, value2, "acquireId");
            return (Criteria) this;
        }

        public Criteria andAcquireIdNotBetween(Integer value1, Integer value2) {
            addCriterion("acquire_id not between", value1, value2, "acquireId");
            return (Criteria) this;
        }

        public Criteria andExeChgDateIsNull() {
            addCriterion("exe_chg_date is null");
            return (Criteria) this;
        }

        public Criteria andExeChgDateIsNotNull() {
            addCriterion("exe_chg_date is not null");
            return (Criteria) this;
        }

        public Criteria andExeChgDateEqualTo(Date value) {
            addCriterionForJDBCDate("exe_chg_date =", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("exe_chg_date <>", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateGreaterThan(Date value) {
            addCriterionForJDBCDate("exe_chg_date >", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("exe_chg_date >=", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateLessThan(Date value) {
            addCriterionForJDBCDate("exe_chg_date <", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("exe_chg_date <=", value, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateIn(List<Date> values) {
            addCriterionForJDBCDate("exe_chg_date in", values, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("exe_chg_date not in", values, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("exe_chg_date between", value1, value2, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andExeChgDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("exe_chg_date not between", value1, value2, "exeChgDate");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNull() {
            addCriterion("buyer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIsNotNull() {
            addCriterion("buyer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerIdEqualTo(Integer value) {
            addCriterion("buyer_id =", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotEqualTo(Integer value) {
            addCriterion("buyer_id <>", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThan(Integer value) {
            addCriterion("buyer_id >", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("buyer_id >=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThan(Integer value) {
            addCriterion("buyer_id <", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdLessThanOrEqualTo(Integer value) {
            addCriterion("buyer_id <=", value, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdIn(List<Integer> values) {
            addCriterion("buyer_id in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotIn(List<Integer> values) {
            addCriterion("buyer_id not in", values, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdBetween(Integer value1, Integer value2) {
            addCriterion("buyer_id between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andBuyerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("buyer_id not between", value1, value2, "buyerId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdIsNull() {
            addCriterion("inquiry_id is null");
            return (Criteria) this;
        }

        public Criteria andInquiryIdIsNotNull() {
            addCriterion("inquiry_id is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryIdEqualTo(String value) {
            addCriterion("inquiry_id =", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdNotEqualTo(String value) {
            addCriterion("inquiry_id <>", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdGreaterThan(String value) {
            addCriterion("inquiry_id >", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_id >=", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdLessThan(String value) {
            addCriterion("inquiry_id <", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdLessThanOrEqualTo(String value) {
            addCriterion("inquiry_id <=", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdLike(String value) {
            addCriterion("inquiry_id like", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdNotLike(String value) {
            addCriterion("inquiry_id not like", value, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdIn(List<String> values) {
            addCriterion("inquiry_id in", values, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdNotIn(List<String> values) {
            addCriterion("inquiry_id not in", values, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdBetween(String value1, String value2) {
            addCriterion("inquiry_id between", value1, value2, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andInquiryIdNotBetween(String value1, String value2) {
            addCriterion("inquiry_id not between", value1, value2, "inquiryId");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNull() {
            addCriterion("business_name is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIsNotNull() {
            addCriterion("business_name is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNameEqualTo(String value) {
            addCriterion("business_name =", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotEqualTo(String value) {
            addCriterion("business_name <>", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThan(String value) {
            addCriterion("business_name >", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameGreaterThanOrEqualTo(String value) {
            addCriterion("business_name >=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThan(String value) {
            addCriterion("business_name <", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLessThanOrEqualTo(String value) {
            addCriterion("business_name <=", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameLike(String value) {
            addCriterion("business_name like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotLike(String value) {
            addCriterion("business_name not like", value, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameIn(List<String> values) {
            addCriterion("business_name in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotIn(List<String> values) {
            addCriterion("business_name not in", values, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameBetween(String value1, String value2) {
            addCriterion("business_name between", value1, value2, "businessName");
            return (Criteria) this;
        }

        public Criteria andBusinessNameNotBetween(String value1, String value2) {
            addCriterion("business_name not between", value1, value2, "businessName");
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

        public Criteria andTotalPriceUsdIsNull() {
            addCriterion("total_price_usd is null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdIsNotNull() {
            addCriterion("total_price_usd is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdEqualTo(BigDecimal value) {
            addCriterion("total_price_usd =", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdNotEqualTo(BigDecimal value) {
            addCriterion("total_price_usd <>", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdGreaterThan(BigDecimal value) {
            addCriterion("total_price_usd >", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price_usd >=", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdLessThan(BigDecimal value) {
            addCriterion("total_price_usd <", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price_usd <=", value, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdIn(List<BigDecimal> values) {
            addCriterion("total_price_usd in", values, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdNotIn(List<BigDecimal> values) {
            addCriterion("total_price_usd not in", values, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price_usd between", value1, value2, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andTotalPriceUsdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price_usd not between", value1, value2, "totalPriceUsd");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNull() {
            addCriterion("exchange_rate is null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNotNull() {
            addCriterion("exchange_rate is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateEqualTo(BigDecimal value) {
            addCriterion("exchange_rate =", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <>", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThan(BigDecimal value) {
            addCriterion("exchange_rate >", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate >=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThan(BigDecimal value) {
            addCriterion("exchange_rate <", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIn(List<BigDecimal> values) {
            addCriterion("exchange_rate in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotIn(List<BigDecimal> values) {
            addCriterion("exchange_rate not in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate not between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andProcessProgressIsNull() {
            addCriterion("process_progress is null");
            return (Criteria) this;
        }

        public Criteria andProcessProgressIsNotNull() {
            addCriterion("process_progress is not null");
            return (Criteria) this;
        }

        public Criteria andProcessProgressEqualTo(String value) {
            addCriterion("process_progress =", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressNotEqualTo(String value) {
            addCriterion("process_progress <>", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressGreaterThan(String value) {
            addCriterion("process_progress >", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressGreaterThanOrEqualTo(String value) {
            addCriterion("process_progress >=", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressLessThan(String value) {
            addCriterion("process_progress <", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressLessThanOrEqualTo(String value) {
            addCriterion("process_progress <=", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressLike(String value) {
            addCriterion("process_progress like", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressNotLike(String value) {
            addCriterion("process_progress not like", value, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressIn(List<String> values) {
            addCriterion("process_progress in", values, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressNotIn(List<String> values) {
            addCriterion("process_progress not in", values, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressBetween(String value1, String value2) {
            addCriterion("process_progress between", value1, value2, "processProgress");
            return (Criteria) this;
        }

        public Criteria andProcessProgressNotBetween(String value1, String value2) {
            addCriterion("process_progress not between", value1, value2, "processProgress");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasIsNull() {
            addCriterion("deliver_consign_has is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasIsNotNull() {
            addCriterion("deliver_consign_has is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasEqualTo(Integer value) {
            addCriterion("deliver_consign_has =", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasNotEqualTo(Integer value) {
            addCriterion("deliver_consign_has <>", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasGreaterThan(Integer value) {
            addCriterion("deliver_consign_has >", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_has >=", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasLessThan(Integer value) {
            addCriterion("deliver_consign_has <", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_has <=", value, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasIn(List<Integer> values) {
            addCriterion("deliver_consign_has in", values, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasNotIn(List<Integer> values) {
            addCriterion("deliver_consign_has not in", values, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_has between", value1, value2, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignHasNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_has not between", value1, value2, "deliverConsignHas");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsIsNull() {
            addCriterion("order_belongs is null");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsIsNotNull() {
            addCriterion("order_belongs is not null");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsEqualTo(Integer value) {
            addCriterion("order_belongs =", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsNotEqualTo(Integer value) {
            addCriterion("order_belongs <>", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsGreaterThan(Integer value) {
            addCriterion("order_belongs >", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_belongs >=", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsLessThan(Integer value) {
            addCriterion("order_belongs <", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsLessThanOrEqualTo(Integer value) {
            addCriterion("order_belongs <=", value, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsIn(List<Integer> values) {
            addCriterion("order_belongs in", values, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsNotIn(List<Integer> values) {
            addCriterion("order_belongs not in", values, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsBetween(Integer value1, Integer value2) {
            addCriterion("order_belongs between", value1, value2, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderBelongsNotBetween(Integer value1, Integer value2) {
            addCriterion("order_belongs not between", value1, value2, "orderBelongs");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIsNull() {
            addCriterion("order_category is null");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIsNotNull() {
            addCriterion("order_category is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryEqualTo(Integer value) {
            addCriterion("order_category =", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryNotEqualTo(Integer value) {
            addCriterion("order_category <>", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryGreaterThan(Integer value) {
            addCriterion("order_category >", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_category >=", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryLessThan(Integer value) {
            addCriterion("order_category <", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryLessThanOrEqualTo(Integer value) {
            addCriterion("order_category <=", value, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryIn(List<Integer> values) {
            addCriterion("order_category in", values, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryNotIn(List<Integer> values) {
            addCriterion("order_category not in", values, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryBetween(Integer value1, Integer value2) {
            addCriterion("order_category between", value1, value2, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCategoryNotBetween(Integer value1, Integer value2) {
            addCriterion("order_category not between", value1, value2, "orderCategory");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesIsNull() {
            addCriterion("overseas_sales is null");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesIsNotNull() {
            addCriterion("overseas_sales is not null");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesEqualTo(Integer value) {
            addCriterion("overseas_sales =", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesNotEqualTo(Integer value) {
            addCriterion("overseas_sales <>", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesGreaterThan(Integer value) {
            addCriterion("overseas_sales >", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesGreaterThanOrEqualTo(Integer value) {
            addCriterion("overseas_sales >=", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesLessThan(Integer value) {
            addCriterion("overseas_sales <", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesLessThanOrEqualTo(Integer value) {
            addCriterion("overseas_sales <=", value, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesIn(List<Integer> values) {
            addCriterion("overseas_sales in", values, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesNotIn(List<Integer> values) {
            addCriterion("overseas_sales not in", values, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesBetween(Integer value1, Integer value2) {
            addCriterion("overseas_sales between", value1, value2, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andOverseasSalesNotBetween(Integer value1, Integer value2) {
            addCriterion("overseas_sales not between", value1, value2, "overseasSales");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyIsNull() {
            addCriterion("shipments_money is null");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyIsNotNull() {
            addCriterion("shipments_money is not null");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyEqualTo(BigDecimal value) {
            addCriterion("shipments_money =", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyNotEqualTo(BigDecimal value) {
            addCriterion("shipments_money <>", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyGreaterThan(BigDecimal value) {
            addCriterion("shipments_money >", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("shipments_money >=", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyLessThan(BigDecimal value) {
            addCriterion("shipments_money <", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("shipments_money <=", value, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyIn(List<BigDecimal> values) {
            addCriterion("shipments_money in", values, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyNotIn(List<BigDecimal> values) {
            addCriterion("shipments_money not in", values, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipments_money between", value1, value2, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andShipmentsMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("shipments_money not between", value1, value2, "shipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyIsNull() {
            addCriterion("already_gathering_money is null");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyIsNotNull() {
            addCriterion("already_gathering_money is not null");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyEqualTo(BigDecimal value) {
            addCriterion("already_gathering_money =", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyNotEqualTo(BigDecimal value) {
            addCriterion("already_gathering_money <>", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyGreaterThan(BigDecimal value) {
            addCriterion("already_gathering_money >", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("already_gathering_money >=", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyLessThan(BigDecimal value) {
            addCriterion("already_gathering_money <", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("already_gathering_money <=", value, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyIn(List<BigDecimal> values) {
            addCriterion("already_gathering_money in", values, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyNotIn(List<BigDecimal> values) {
            addCriterion("already_gathering_money not in", values, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("already_gathering_money between", value1, value2, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andAlreadyGatheringMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("already_gathering_money not between", value1, value2, "alreadyGatheringMoney");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateIsNull() {
            addCriterion("purch_req_create is null");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateIsNotNull() {
            addCriterion("purch_req_create is not null");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateEqualTo(Integer value) {
            addCriterion("purch_req_create =", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateNotEqualTo(Integer value) {
            addCriterion("purch_req_create <>", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateGreaterThan(Integer value) {
            addCriterion("purch_req_create >", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_req_create >=", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateLessThan(Integer value) {
            addCriterion("purch_req_create <", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateLessThanOrEqualTo(Integer value) {
            addCriterion("purch_req_create <=", value, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateIn(List<Integer> values) {
            addCriterion("purch_req_create in", values, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateNotIn(List<Integer> values) {
            addCriterion("purch_req_create not in", values, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateBetween(Integer value1, Integer value2) {
            addCriterion("purch_req_create between", value1, value2, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andPurchReqCreateNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_req_create not between", value1, value2, "purchReqCreate");
            return (Criteria) this;
        }

        public Criteria andInspectNIsNull() {
            addCriterion("inspect_n is null");
            return (Criteria) this;
        }

        public Criteria andInspectNIsNotNull() {
            addCriterion("inspect_n is not null");
            return (Criteria) this;
        }

        public Criteria andInspectNEqualTo(Integer value) {
            addCriterion("inspect_n =", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNNotEqualTo(Integer value) {
            addCriterion("inspect_n <>", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNGreaterThan(Integer value) {
            addCriterion("inspect_n >", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_n >=", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNLessThan(Integer value) {
            addCriterion("inspect_n <", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_n <=", value, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNIn(List<Integer> values) {
            addCriterion("inspect_n in", values, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNNotIn(List<Integer> values) {
            addCriterion("inspect_n not in", values, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNBetween(Integer value1, Integer value2) {
            addCriterion("inspect_n between", value1, value2, "inspectN");
            return (Criteria) this;
        }

        public Criteria andInspectNNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_n not between", value1, value2, "inspectN");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyIsNull() {
            addCriterion("advance_money is null");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyIsNotNull() {
            addCriterion("advance_money is not null");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyEqualTo(BigDecimal value) {
            addCriterion("advance_money =", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyNotEqualTo(BigDecimal value) {
            addCriterion("advance_money <>", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyGreaterThan(BigDecimal value) {
            addCriterion("advance_money >", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_money >=", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyLessThan(BigDecimal value) {
            addCriterion("advance_money <", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("advance_money <=", value, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyIn(List<BigDecimal> values) {
            addCriterion("advance_money in", values, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyNotIn(List<BigDecimal> values) {
            addCriterion("advance_money not in", values, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_money between", value1, value2, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAdvanceMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("advance_money not between", value1, value2, "advanceMoney");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIsNull() {
            addCriterion("auditing_status is null");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIsNotNull() {
            addCriterion("auditing_status is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusEqualTo(Integer value) {
            addCriterion("auditing_status =", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotEqualTo(Integer value) {
            addCriterion("auditing_status <>", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusGreaterThan(Integer value) {
            addCriterion("auditing_status >", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("auditing_status >=", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusLessThan(Integer value) {
            addCriterion("auditing_status <", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusLessThanOrEqualTo(Integer value) {
            addCriterion("auditing_status <=", value, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusIn(List<Integer> values) {
            addCriterion("auditing_status in", values, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotIn(List<Integer> values) {
            addCriterion("auditing_status not in", values, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusBetween(Integer value1, Integer value2) {
            addCriterion("auditing_status between", value1, value2, "auditingStatus");
            return (Criteria) this;
        }

        public Criteria andAuditingStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("auditing_status not between", value1, value2, "auditingStatus");
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

        public Criteria andAuditingUserIdEqualTo(String value) {
            addCriterion("auditing_user_id =", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotEqualTo(String value) {
            addCriterion("auditing_user_id <>", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThan(String value) {
            addCriterion("auditing_user_id >", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_user_id >=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThan(String value) {
            addCriterion("auditing_user_id <", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLessThanOrEqualTo(String value) {
            addCriterion("auditing_user_id <=", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdLike(String value) {
            addCriterion("auditing_user_id like", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotLike(String value) {
            addCriterion("auditing_user_id not like", value, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdIn(List<String> values) {
            addCriterion("auditing_user_id in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotIn(List<String> values) {
            addCriterion("auditing_user_id not in", values, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdBetween(String value1, String value2) {
            addCriterion("auditing_user_id between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIdNotBetween(String value1, String value2) {
            addCriterion("auditing_user_id not between", value1, value2, "auditingUserId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdIsNull() {
            addCriterion("country_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdIsNotNull() {
            addCriterion("country_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdEqualTo(Integer value) {
            addCriterion("country_leader_id =", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdNotEqualTo(Integer value) {
            addCriterion("country_leader_id <>", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdGreaterThan(Integer value) {
            addCriterion("country_leader_id >", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("country_leader_id >=", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdLessThan(Integer value) {
            addCriterion("country_leader_id <", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("country_leader_id <=", value, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdIn(List<Integer> values) {
            addCriterion("country_leader_id in", values, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdNotIn(List<Integer> values) {
            addCriterion("country_leader_id not in", values, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("country_leader_id between", value1, value2, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("country_leader_id not between", value1, value2, "countryLeaderId");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIsNull() {
            addCriterion("country_leader is null");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIsNotNull() {
            addCriterion("country_leader is not null");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderEqualTo(String value) {
            addCriterion("country_leader =", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderNotEqualTo(String value) {
            addCriterion("country_leader <>", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderGreaterThan(String value) {
            addCriterion("country_leader >", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("country_leader >=", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderLessThan(String value) {
            addCriterion("country_leader <", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderLessThanOrEqualTo(String value) {
            addCriterion("country_leader <=", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderLike(String value) {
            addCriterion("country_leader like", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderNotLike(String value) {
            addCriterion("country_leader not like", value, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderIn(List<String> values) {
            addCriterion("country_leader in", values, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderNotIn(List<String> values) {
            addCriterion("country_leader not in", values, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderBetween(String value1, String value2) {
            addCriterion("country_leader between", value1, value2, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andCountryLeaderNotBetween(String value1, String value2) {
            addCriterion("country_leader not between", value1, value2, "countryLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdIsNull() {
            addCriterion("area_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdIsNotNull() {
            addCriterion("area_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdEqualTo(Integer value) {
            addCriterion("area_leader_id =", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdNotEqualTo(Integer value) {
            addCriterion("area_leader_id <>", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdGreaterThan(Integer value) {
            addCriterion("area_leader_id >", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_leader_id >=", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdLessThan(Integer value) {
            addCriterion("area_leader_id <", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("area_leader_id <=", value, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdIn(List<Integer> values) {
            addCriterion("area_leader_id in", values, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdNotIn(List<Integer> values) {
            addCriterion("area_leader_id not in", values, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("area_leader_id between", value1, value2, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("area_leader_id not between", value1, value2, "areaLeaderId");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIsNull() {
            addCriterion("area_leader is null");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIsNotNull() {
            addCriterion("area_leader is not null");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderEqualTo(String value) {
            addCriterion("area_leader =", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderNotEqualTo(String value) {
            addCriterion("area_leader <>", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderGreaterThan(String value) {
            addCriterion("area_leader >", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("area_leader >=", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderLessThan(String value) {
            addCriterion("area_leader <", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderLessThanOrEqualTo(String value) {
            addCriterion("area_leader <=", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderLike(String value) {
            addCriterion("area_leader like", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderNotLike(String value) {
            addCriterion("area_leader not like", value, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderIn(List<String> values) {
            addCriterion("area_leader in", values, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderNotIn(List<String> values) {
            addCriterion("area_leader not in", values, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderBetween(String value1, String value2) {
            addCriterion("area_leader between", value1, value2, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaLeaderNotBetween(String value1, String value2) {
            addCriterion("area_leader not between", value1, value2, "areaLeader");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdIsNull() {
            addCriterion("area_vp_id is null");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdIsNotNull() {
            addCriterion("area_vp_id is not null");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdEqualTo(Integer value) {
            addCriterion("area_vp_id =", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdNotEqualTo(Integer value) {
            addCriterion("area_vp_id <>", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdGreaterThan(Integer value) {
            addCriterion("area_vp_id >", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("area_vp_id >=", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdLessThan(Integer value) {
            addCriterion("area_vp_id <", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdLessThanOrEqualTo(Integer value) {
            addCriterion("area_vp_id <=", value, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdIn(List<Integer> values) {
            addCriterion("area_vp_id in", values, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdNotIn(List<Integer> values) {
            addCriterion("area_vp_id not in", values, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdBetween(Integer value1, Integer value2) {
            addCriterion("area_vp_id between", value1, value2, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIdNotBetween(Integer value1, Integer value2) {
            addCriterion("area_vp_id not between", value1, value2, "areaVpId");
            return (Criteria) this;
        }

        public Criteria andAreaVpIsNull() {
            addCriterion("area_vp is null");
            return (Criteria) this;
        }

        public Criteria andAreaVpIsNotNull() {
            addCriterion("area_vp is not null");
            return (Criteria) this;
        }

        public Criteria andAreaVpEqualTo(String value) {
            addCriterion("area_vp =", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpNotEqualTo(String value) {
            addCriterion("area_vp <>", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpGreaterThan(String value) {
            addCriterion("area_vp >", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpGreaterThanOrEqualTo(String value) {
            addCriterion("area_vp >=", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpLessThan(String value) {
            addCriterion("area_vp <", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpLessThanOrEqualTo(String value) {
            addCriterion("area_vp <=", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpLike(String value) {
            addCriterion("area_vp like", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpNotLike(String value) {
            addCriterion("area_vp not like", value, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpIn(List<String> values) {
            addCriterion("area_vp in", values, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpNotIn(List<String> values) {
            addCriterion("area_vp not in", values, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpBetween(String value1, String value2) {
            addCriterion("area_vp between", value1, value2, "areaVp");
            return (Criteria) this;
        }

        public Criteria andAreaVpNotBetween(String value1, String value2) {
            addCriterion("area_vp not between", value1, value2, "areaVp");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdIsNull() {
            addCriterion("per_liable_repay_id is null");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdIsNotNull() {
            addCriterion("per_liable_repay_id is not null");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdEqualTo(Integer value) {
            addCriterion("per_liable_repay_id =", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdNotEqualTo(Integer value) {
            addCriterion("per_liable_repay_id <>", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdGreaterThan(Integer value) {
            addCriterion("per_liable_repay_id >", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("per_liable_repay_id >=", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdLessThan(Integer value) {
            addCriterion("per_liable_repay_id <", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdLessThanOrEqualTo(Integer value) {
            addCriterion("per_liable_repay_id <=", value, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdIn(List<Integer> values) {
            addCriterion("per_liable_repay_id in", values, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdNotIn(List<Integer> values) {
            addCriterion("per_liable_repay_id not in", values, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdBetween(Integer value1, Integer value2) {
            addCriterion("per_liable_repay_id between", value1, value2, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andPerLiableRepayIdNotBetween(Integer value1, Integer value2) {
            addCriterion("per_liable_repay_id not between", value1, value2, "perLiableRepayId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdIsNull() {
            addCriterion("financing_commissioner_id is null");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdIsNotNull() {
            addCriterion("financing_commissioner_id is not null");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdEqualTo(Integer value) {
            addCriterion("financing_commissioner_id =", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdNotEqualTo(Integer value) {
            addCriterion("financing_commissioner_id <>", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdGreaterThan(Integer value) {
            addCriterion("financing_commissioner_id >", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("financing_commissioner_id >=", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdLessThan(Integer value) {
            addCriterion("financing_commissioner_id <", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdLessThanOrEqualTo(Integer value) {
            addCriterion("financing_commissioner_id <=", value, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdIn(List<Integer> values) {
            addCriterion("financing_commissioner_id in", values, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdNotIn(List<Integer> values) {
            addCriterion("financing_commissioner_id not in", values, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdBetween(Integer value1, Integer value2) {
            addCriterion("financing_commissioner_id between", value1, value2, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("financing_commissioner_id not between", value1, value2, "financingCommissionerId");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkIsNull() {
            addCriterion("audi_remark is null");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkIsNotNull() {
            addCriterion("audi_remark is not null");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkEqualTo(String value) {
            addCriterion("audi_remark =", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkNotEqualTo(String value) {
            addCriterion("audi_remark <>", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkGreaterThan(String value) {
            addCriterion("audi_remark >", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("audi_remark >=", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkLessThan(String value) {
            addCriterion("audi_remark <", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkLessThanOrEqualTo(String value) {
            addCriterion("audi_remark <=", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkLike(String value) {
            addCriterion("audi_remark like", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkNotLike(String value) {
            addCriterion("audi_remark not like", value, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkIn(List<String> values) {
            addCriterion("audi_remark in", values, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkNotIn(List<String> values) {
            addCriterion("audi_remark not in", values, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkBetween(String value1, String value2) {
            addCriterion("audi_remark between", value1, value2, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andAudiRemarkNotBetween(String value1, String value2) {
            addCriterion("audi_remark not between", value1, value2, "audiRemark");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIsNull() {
            addCriterion("cancel_reason is null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIsNotNull() {
            addCriterion("cancel_reason is not null");
            return (Criteria) this;
        }

        public Criteria andCancelReasonEqualTo(String value) {
            addCriterion("cancel_reason =", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotEqualTo(String value) {
            addCriterion("cancel_reason <>", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonGreaterThan(String value) {
            addCriterion("cancel_reason >", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonGreaterThanOrEqualTo(String value) {
            addCriterion("cancel_reason >=", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonLessThan(String value) {
            addCriterion("cancel_reason <", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonLessThanOrEqualTo(String value) {
            addCriterion("cancel_reason <=", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonLike(String value) {
            addCriterion("cancel_reason like", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotLike(String value) {
            addCriterion("cancel_reason not like", value, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonIn(List<String> values) {
            addCriterion("cancel_reason in", values, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotIn(List<String> values) {
            addCriterion("cancel_reason not in", values, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonBetween(String value1, String value2) {
            addCriterion("cancel_reason between", value1, value2, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andCancelReasonNotBetween(String value1, String value2) {
            addCriterion("cancel_reason not between", value1, value2, "cancelReason");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdIsNull() {
            addCriterion("settlement_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdIsNotNull() {
            addCriterion("settlement_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdEqualTo(Integer value) {
            addCriterion("settlement_leader_id =", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdNotEqualTo(Integer value) {
            addCriterion("settlement_leader_id <>", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdGreaterThan(Integer value) {
            addCriterion("settlement_leader_id >", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("settlement_leader_id >=", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdLessThan(Integer value) {
            addCriterion("settlement_leader_id <", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("settlement_leader_id <=", value, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdIn(List<Integer> values) {
            addCriterion("settlement_leader_id in", values, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdNotIn(List<Integer> values) {
            addCriterion("settlement_leader_id not in", values, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("settlement_leader_id between", value1, value2, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("settlement_leader_id not between", value1, value2, "settlementLeaderId");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIsNull() {
            addCriterion("settlement_leader is null");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIsNotNull() {
            addCriterion("settlement_leader is not null");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderEqualTo(String value) {
            addCriterion("settlement_leader =", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderNotEqualTo(String value) {
            addCriterion("settlement_leader <>", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderGreaterThan(String value) {
            addCriterion("settlement_leader >", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("settlement_leader >=", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderLessThan(String value) {
            addCriterion("settlement_leader <", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderLessThanOrEqualTo(String value) {
            addCriterion("settlement_leader <=", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderLike(String value) {
            addCriterion("settlement_leader like", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderNotLike(String value) {
            addCriterion("settlement_leader not like", value, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderIn(List<String> values) {
            addCriterion("settlement_leader in", values, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderNotIn(List<String> values) {
            addCriterion("settlement_leader not in", values, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderBetween(String value1, String value2) {
            addCriterion("settlement_leader between", value1, value2, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andSettlementLeaderNotBetween(String value1, String value2) {
            addCriterion("settlement_leader not between", value1, value2, "settlementLeader");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdIsNull() {
            addCriterion("legal_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdIsNotNull() {
            addCriterion("legal_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdEqualTo(Integer value) {
            addCriterion("legal_auditer_id =", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdNotEqualTo(Integer value) {
            addCriterion("legal_auditer_id <>", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdGreaterThan(Integer value) {
            addCriterion("legal_auditer_id >", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("legal_auditer_id >=", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdLessThan(Integer value) {
            addCriterion("legal_auditer_id <", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("legal_auditer_id <=", value, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdIn(List<Integer> values) {
            addCriterion("legal_auditer_id in", values, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdNotIn(List<Integer> values) {
            addCriterion("legal_auditer_id not in", values, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("legal_auditer_id between", value1, value2, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("legal_auditer_id not between", value1, value2, "legalAuditerId");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIsNull() {
            addCriterion("legal_auditer is null");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIsNotNull() {
            addCriterion("legal_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerEqualTo(String value) {
            addCriterion("legal_auditer =", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerNotEqualTo(String value) {
            addCriterion("legal_auditer <>", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerGreaterThan(String value) {
            addCriterion("legal_auditer >", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("legal_auditer >=", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerLessThan(String value) {
            addCriterion("legal_auditer <", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerLessThanOrEqualTo(String value) {
            addCriterion("legal_auditer <=", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerLike(String value) {
            addCriterion("legal_auditer like", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerNotLike(String value) {
            addCriterion("legal_auditer not like", value, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerIn(List<String> values) {
            addCriterion("legal_auditer in", values, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerNotIn(List<String> values) {
            addCriterion("legal_auditer not in", values, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerBetween(String value1, String value2) {
            addCriterion("legal_auditer between", value1, value2, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andLegalAuditerNotBetween(String value1, String value2) {
            addCriterion("legal_auditer not between", value1, value2, "legalAuditer");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIsNull() {
            addCriterion("financing_commissioner is null");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIsNotNull() {
            addCriterion("financing_commissioner is not null");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerEqualTo(String value) {
            addCriterion("financing_commissioner =", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerNotEqualTo(String value) {
            addCriterion("financing_commissioner <>", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerGreaterThan(String value) {
            addCriterion("financing_commissioner >", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerGreaterThanOrEqualTo(String value) {
            addCriterion("financing_commissioner >=", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerLessThan(String value) {
            addCriterion("financing_commissioner <", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerLessThanOrEqualTo(String value) {
            addCriterion("financing_commissioner <=", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerLike(String value) {
            addCriterion("financing_commissioner like", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerNotLike(String value) {
            addCriterion("financing_commissioner not like", value, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerIn(List<String> values) {
            addCriterion("financing_commissioner in", values, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerNotIn(List<String> values) {
            addCriterion("financing_commissioner not in", values, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerBetween(String value1, String value2) {
            addCriterion("financing_commissioner between", value1, value2, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andFinancingCommissionerNotBetween(String value1, String value2) {
            addCriterion("financing_commissioner not between", value1, value2, "financingCommissioner");
            return (Criteria) this;
        }

        public Criteria andProcessIdIsNull() {
            addCriterion("process_id is null");
            return (Criteria) this;
        }

        public Criteria andProcessIdIsNotNull() {
            addCriterion("process_id is not null");
            return (Criteria) this;
        }

        public Criteria andProcessIdEqualTo(String value) {
            addCriterion("process_id =", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotEqualTo(String value) {
            addCriterion("process_id <>", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThan(String value) {
            addCriterion("process_id >", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdGreaterThanOrEqualTo(String value) {
            addCriterion("process_id >=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThan(String value) {
            addCriterion("process_id <", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLessThanOrEqualTo(String value) {
            addCriterion("process_id <=", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdLike(String value) {
            addCriterion("process_id like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotLike(String value) {
            addCriterion("process_id not like", value, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdIn(List<String> values) {
            addCriterion("process_id in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotIn(List<String> values) {
            addCriterion("process_id not in", values, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdBetween(String value1, String value2) {
            addCriterion("process_id between", value1, value2, "processId");
            return (Criteria) this;
        }

        public Criteria andProcessIdNotBetween(String value1, String value2) {
            addCriterion("process_id not between", value1, value2, "processId");
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

        public Criteria andTaskIdIsNull() {
            addCriterion("task_id is null");
            return (Criteria) this;
        }

        public Criteria andTaskIdIsNotNull() {
            addCriterion("task_id is not null");
            return (Criteria) this;
        }

        public Criteria andTaskIdEqualTo(String value) {
            addCriterion("task_id =", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotEqualTo(String value) {
            addCriterion("task_id <>", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThan(String value) {
            addCriterion("task_id >", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdGreaterThanOrEqualTo(String value) {
            addCriterion("task_id >=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThan(String value) {
            addCriterion("task_id <", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLessThanOrEqualTo(String value) {
            addCriterion("task_id <=", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdLike(String value) {
            addCriterion("task_id like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotLike(String value) {
            addCriterion("task_id not like", value, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdIn(List<String> values) {
            addCriterion("task_id in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotIn(List<String> values) {
            addCriterion("task_id not in", values, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdBetween(String value1, String value2) {
            addCriterion("task_id between", value1, value2, "taskId");
            return (Criteria) this;
        }

        public Criteria andTaskIdNotBetween(String value1, String value2) {
            addCriterion("task_id not between", value1, value2, "taskId");
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