package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PurchExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchExample() {
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

        public Criteria andPurchNoIsNull() {
            addCriterion("purch_no is null");
            return (Criteria) this;
        }

        public Criteria andPurchNoIsNotNull() {
            addCriterion("purch_no is not null");
            return (Criteria) this;
        }

        public Criteria andPurchNoEqualTo(String value) {
            addCriterion("purch_no =", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoNotEqualTo(String value) {
            addCriterion("purch_no <>", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoGreaterThan(String value) {
            addCriterion("purch_no >", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoGreaterThanOrEqualTo(String value) {
            addCriterion("purch_no >=", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoLessThan(String value) {
            addCriterion("purch_no <", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoLessThanOrEqualTo(String value) {
            addCriterion("purch_no <=", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoLike(String value) {
            addCriterion("purch_no like", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoNotLike(String value) {
            addCriterion("purch_no not like", value, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoIn(List<String> values) {
            addCriterion("purch_no in", values, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoNotIn(List<String> values) {
            addCriterion("purch_no not in", values, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoBetween(String value1, String value2) {
            addCriterion("purch_no between", value1, value2, "purchNo");
            return (Criteria) this;
        }

        public Criteria andPurchNoNotBetween(String value1, String value2) {
            addCriterion("purch_no not between", value1, value2, "purchNo");
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

        public Criteria andDepartmentIsNull() {
            addCriterion("department is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNotNull() {
            addCriterion("department is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentEqualTo(String value) {
            addCriterion("department =", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotEqualTo(String value) {
            addCriterion("department <>", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThan(String value) {
            addCriterion("department >", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThanOrEqualTo(String value) {
            addCriterion("department >=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThan(String value) {
            addCriterion("department <", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThanOrEqualTo(String value) {
            addCriterion("department <=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLike(String value) {
            addCriterion("department like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotLike(String value) {
            addCriterion("department not like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentIn(List<String> values) {
            addCriterion("department in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotIn(List<String> values) {
            addCriterion("department not in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentBetween(String value1, String value2) {
            addCriterion("department between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotBetween(String value1, String value2) {
            addCriterion("department not between", value1, value2, "department");
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

        public Criteria andArrivalDateIsNull() {
            addCriterion("arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNotNull() {
            addCriterion("arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date =", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date <>", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThan(Date value) {
            addCriterionForJDBCDate("arrival_date >", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date >=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThan(Date value) {
            addCriterionForJDBCDate("arrival_date <", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("arrival_date <=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIn(List<Date> values) {
            addCriterionForJDBCDate("arrival_date in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("arrival_date not in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrival_date between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("arrival_date not between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateIsNull() {
            addCriterion("pur_chg_date is null");
            return (Criteria) this;
        }

        public Criteria andPurChgDateIsNotNull() {
            addCriterion("pur_chg_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurChgDateEqualTo(Date value) {
            addCriterionForJDBCDate("pur_chg_date =", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("pur_chg_date <>", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateGreaterThan(Date value) {
            addCriterionForJDBCDate("pur_chg_date >", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pur_chg_date >=", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateLessThan(Date value) {
            addCriterionForJDBCDate("pur_chg_date <", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pur_chg_date <=", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateIn(List<Date> values) {
            addCriterionForJDBCDate("pur_chg_date in", values, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("pur_chg_date not in", values, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pur_chg_date between", value1, value2, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pur_chg_date not between", value1, value2, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("supplier_id is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("supplier_id is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(Integer value) {
            addCriterion("supplier_id =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(Integer value) {
            addCriterion("supplier_id <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(Integer value) {
            addCriterion("supplier_id >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("supplier_id >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(Integer value) {
            addCriterion("supplier_id <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(Integer value) {
            addCriterion("supplier_id <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<Integer> values) {
            addCriterion("supplier_id in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<Integer> values) {
            addCriterion("supplier_id not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(Integer value1, Integer value2) {
            addCriterion("supplier_id between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(Integer value1, Integer value2) {
            addCriterion("supplier_id not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNull() {
            addCriterion("supplier_name is null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIsNotNull() {
            addCriterion("supplier_name is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierNameEqualTo(String value) {
            addCriterion("supplier_name =", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotEqualTo(String value) {
            addCriterion("supplier_name <>", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThan(String value) {
            addCriterion("supplier_name >", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameGreaterThanOrEqualTo(String value) {
            addCriterion("supplier_name >=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThan(String value) {
            addCriterion("supplier_name <", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLessThanOrEqualTo(String value) {
            addCriterion("supplier_name <=", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameLike(String value) {
            addCriterion("supplier_name like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotLike(String value) {
            addCriterion("supplier_name not like", value, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameIn(List<String> values) {
            addCriterion("supplier_name in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotIn(List<String> values) {
            addCriterion("supplier_name not in", values, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameBetween(String value1, String value2) {
            addCriterion("supplier_name between", value1, value2, "supplierName");
            return (Criteria) this;
        }

        public Criteria andSupplierNameNotBetween(String value1, String value2) {
            addCriterion("supplier_name not between", value1, value2, "supplierName");
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

        public Criteria andPayTypeIsNull() {
            addCriterion("pay_type is null");
            return (Criteria) this;
        }

        public Criteria andPayTypeIsNotNull() {
            addCriterion("pay_type is not null");
            return (Criteria) this;
        }

        public Criteria andPayTypeEqualTo(Integer value) {
            addCriterion("pay_type =", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotEqualTo(Integer value) {
            addCriterion("pay_type <>", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThan(Integer value) {
            addCriterion("pay_type >", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_type >=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThan(Integer value) {
            addCriterion("pay_type <", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeLessThanOrEqualTo(Integer value) {
            addCriterion("pay_type <=", value, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeIn(List<Integer> values) {
            addCriterion("pay_type in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotIn(List<Integer> values) {
            addCriterion("pay_type not in", values, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeBetween(Integer value1, Integer value2) {
            addCriterion("pay_type between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andPayTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_type not between", value1, value2, "payType");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgIsNull() {
            addCriterion("other_pay_type_msg is null");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgIsNotNull() {
            addCriterion("other_pay_type_msg is not null");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgEqualTo(String value) {
            addCriterion("other_pay_type_msg =", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgNotEqualTo(String value) {
            addCriterion("other_pay_type_msg <>", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgGreaterThan(String value) {
            addCriterion("other_pay_type_msg >", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgGreaterThanOrEqualTo(String value) {
            addCriterion("other_pay_type_msg >=", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgLessThan(String value) {
            addCriterion("other_pay_type_msg <", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgLessThanOrEqualTo(String value) {
            addCriterion("other_pay_type_msg <=", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgLike(String value) {
            addCriterion("other_pay_type_msg like", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgNotLike(String value) {
            addCriterion("other_pay_type_msg not like", value, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgIn(List<String> values) {
            addCriterion("other_pay_type_msg in", values, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgNotIn(List<String> values) {
            addCriterion("other_pay_type_msg not in", values, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgBetween(String value1, String value2) {
            addCriterion("other_pay_type_msg between", value1, value2, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andOtherPayTypeMsgNotBetween(String value1, String value2) {
            addCriterion("other_pay_type_msg not between", value1, value2, "otherPayTypeMsg");
            return (Criteria) this;
        }

        public Criteria andProductedDateIsNull() {
            addCriterion("producted_date is null");
            return (Criteria) this;
        }

        public Criteria andProductedDateIsNotNull() {
            addCriterion("producted_date is not null");
            return (Criteria) this;
        }

        public Criteria andProductedDateEqualTo(Date value) {
            addCriterionForJDBCDate("producted_date =", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("producted_date <>", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateGreaterThan(Date value) {
            addCriterionForJDBCDate("producted_date >", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("producted_date >=", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateLessThan(Date value) {
            addCriterionForJDBCDate("producted_date <", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("producted_date <=", value, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateIn(List<Date> values) {
            addCriterionForJDBCDate("producted_date in", values, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("producted_date not in", values, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("producted_date between", value1, value2, "productedDate");
            return (Criteria) this;
        }

        public Criteria andProductedDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("producted_date not between", value1, value2, "productedDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateIsNull() {
            addCriterion("pay_factory_date is null");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateIsNotNull() {
            addCriterion("pay_factory_date is not null");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateEqualTo(Date value) {
            addCriterionForJDBCDate("pay_factory_date =", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_factory_date <>", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_factory_date >", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_factory_date >=", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateLessThan(Date value) {
            addCriterionForJDBCDate("pay_factory_date <", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_factory_date <=", value, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateIn(List<Date> values) {
            addCriterionForJDBCDate("pay_factory_date in", values, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_factory_date not in", values, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_factory_date between", value1, value2, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayFactoryDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_factory_date not between", value1, value2, "payFactoryDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateIsNull() {
            addCriterion("pay_deposit_date is null");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateIsNotNull() {
            addCriterion("pay_deposit_date is not null");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_date =", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_date <>", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_deposit_date >", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_date >=", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateLessThan(Date value) {
            addCriterionForJDBCDate("pay_deposit_date <", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_date <=", value, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateIn(List<Date> values) {
            addCriterionForJDBCDate("pay_deposit_date in", values, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_deposit_date not in", values, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_deposit_date between", value1, value2, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_deposit_date not between", value1, value2, "payDepositDate");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredIsNull() {
            addCriterion("pay_deposit_expired is null");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredIsNotNull() {
            addCriterion("pay_deposit_expired is not null");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired =", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredNotEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired <>", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredGreaterThan(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired >", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired >=", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredLessThan(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired <", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pay_deposit_expired <=", value, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredIn(List<Date> values) {
            addCriterionForJDBCDate("pay_deposit_expired in", values, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredNotIn(List<Date> values) {
            addCriterionForJDBCDate("pay_deposit_expired not in", values, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_deposit_expired between", value1, value2, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andPayDepositExpiredNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pay_deposit_expired not between", value1, value2, "payDepositExpired");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNull() {
            addCriterion("invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIsNotNull() {
            addCriterion("invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoEqualTo(String value) {
            addCriterion("invoice_no =", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotEqualTo(String value) {
            addCriterion("invoice_no <>", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThan(String value) {
            addCriterion("invoice_no >", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_no >=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThan(String value) {
            addCriterion("invoice_no <", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("invoice_no <=", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoLike(String value) {
            addCriterion("invoice_no like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotLike(String value) {
            addCriterion("invoice_no not like", value, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoIn(List<String> values) {
            addCriterion("invoice_no in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotIn(List<String> values) {
            addCriterion("invoice_no not in", values, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoBetween(String value1, String value2) {
            addCriterion("invoice_no between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("invoice_no not between", value1, value2, "invoiceNo");
            return (Criteria) this;
        }

        public Criteria andAccountDateIsNull() {
            addCriterion("account_date is null");
            return (Criteria) this;
        }

        public Criteria andAccountDateIsNotNull() {
            addCriterion("account_date is not null");
            return (Criteria) this;
        }

        public Criteria andAccountDateEqualTo(Date value) {
            addCriterionForJDBCDate("account_date =", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("account_date <>", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateGreaterThan(Date value) {
            addCriterionForJDBCDate("account_date >", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("account_date >=", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateLessThan(Date value) {
            addCriterionForJDBCDate("account_date <", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("account_date <=", value, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateIn(List<Date> values) {
            addCriterionForJDBCDate("account_date in", values, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("account_date not in", values, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("account_date between", value1, value2, "accountDate");
            return (Criteria) this;
        }

        public Criteria andAccountDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("account_date not between", value1, value2, "accountDate");
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

        public Criteria andInspectedIsNull() {
            addCriterion("inspected is null");
            return (Criteria) this;
        }

        public Criteria andInspectedIsNotNull() {
            addCriterion("inspected is not null");
            return (Criteria) this;
        }

        public Criteria andInspectedEqualTo(Boolean value) {
            addCriterion("inspected =", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedNotEqualTo(Boolean value) {
            addCriterion("inspected <>", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedGreaterThan(Boolean value) {
            addCriterion("inspected >", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("inspected >=", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedLessThan(Boolean value) {
            addCriterion("inspected <", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedLessThanOrEqualTo(Boolean value) {
            addCriterion("inspected <=", value, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedIn(List<Boolean> values) {
            addCriterion("inspected in", values, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedNotIn(List<Boolean> values) {
            addCriterion("inspected not in", values, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedBetween(Boolean value1, Boolean value2) {
            addCriterion("inspected between", value1, value2, "inspected");
            return (Criteria) this;
        }

        public Criteria andInspectedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("inspected not between", value1, value2, "inspected");
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

        public Criteria andAuditingUserIsNull() {
            addCriterion("auditing_user is null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIsNotNull() {
            addCriterion("auditing_user is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingUserEqualTo(String value) {
            addCriterion("auditing_user =", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNotEqualTo(String value) {
            addCriterion("auditing_user <>", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserGreaterThan(String value) {
            addCriterion("auditing_user >", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserGreaterThanOrEqualTo(String value) {
            addCriterion("auditing_user >=", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserLessThan(String value) {
            addCriterion("auditing_user <", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserLessThanOrEqualTo(String value) {
            addCriterion("auditing_user <=", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserLike(String value) {
            addCriterion("auditing_user like", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNotLike(String value) {
            addCriterion("auditing_user not like", value, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserIn(List<String> values) {
            addCriterion("auditing_user in", values, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNotIn(List<String> values) {
            addCriterion("auditing_user not in", values, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserBetween(String value1, String value2) {
            addCriterion("auditing_user between", value1, value2, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andAuditingUserNotBetween(String value1, String value2) {
            addCriterion("auditing_user not between", value1, value2, "auditingUser");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdIsNull() {
            addCriterion("purch_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdIsNotNull() {
            addCriterion("purch_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdEqualTo(Integer value) {
            addCriterion("purch_auditer_id =", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdNotEqualTo(Integer value) {
            addCriterion("purch_auditer_id <>", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdGreaterThan(Integer value) {
            addCriterion("purch_auditer_id >", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_auditer_id >=", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdLessThan(Integer value) {
            addCriterion("purch_auditer_id <", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("purch_auditer_id <=", value, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdIn(List<Integer> values) {
            addCriterion("purch_auditer_id in", values, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdNotIn(List<Integer> values) {
            addCriterion("purch_auditer_id not in", values, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("purch_auditer_id between", value1, value2, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_auditer_id not between", value1, value2, "purchAuditerId");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIsNull() {
            addCriterion("purch_auditer is null");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIsNotNull() {
            addCriterion("purch_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerEqualTo(String value) {
            addCriterion("purch_auditer =", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerNotEqualTo(String value) {
            addCriterion("purch_auditer <>", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerGreaterThan(String value) {
            addCriterion("purch_auditer >", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("purch_auditer >=", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerLessThan(String value) {
            addCriterion("purch_auditer <", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerLessThanOrEqualTo(String value) {
            addCriterion("purch_auditer <=", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerLike(String value) {
            addCriterion("purch_auditer like", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerNotLike(String value) {
            addCriterion("purch_auditer not like", value, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerIn(List<String> values) {
            addCriterion("purch_auditer in", values, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerNotIn(List<String> values) {
            addCriterion("purch_auditer not in", values, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerBetween(String value1, String value2) {
            addCriterion("purch_auditer between", value1, value2, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andPurchAuditerNotBetween(String value1, String value2) {
            addCriterion("purch_auditer not between", value1, value2, "purchAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdIsNull() {
            addCriterion("business_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdIsNotNull() {
            addCriterion("business_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdEqualTo(Integer value) {
            addCriterion("business_auditer_id =", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdNotEqualTo(Integer value) {
            addCriterion("business_auditer_id <>", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdGreaterThan(Integer value) {
            addCriterion("business_auditer_id >", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("business_auditer_id >=", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdLessThan(Integer value) {
            addCriterion("business_auditer_id <", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("business_auditer_id <=", value, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdIn(List<Integer> values) {
            addCriterion("business_auditer_id in", values, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdNotIn(List<Integer> values) {
            addCriterion("business_auditer_id not in", values, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("business_auditer_id between", value1, value2, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("business_auditer_id not between", value1, value2, "businessAuditerId");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIsNull() {
            addCriterion("business_auditer is null");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIsNotNull() {
            addCriterion("business_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerEqualTo(String value) {
            addCriterion("business_auditer =", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerNotEqualTo(String value) {
            addCriterion("business_auditer <>", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerGreaterThan(String value) {
            addCriterion("business_auditer >", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("business_auditer >=", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerLessThan(String value) {
            addCriterion("business_auditer <", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerLessThanOrEqualTo(String value) {
            addCriterion("business_auditer <=", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerLike(String value) {
            addCriterion("business_auditer like", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerNotLike(String value) {
            addCriterion("business_auditer not like", value, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerIn(List<String> values) {
            addCriterion("business_auditer in", values, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerNotIn(List<String> values) {
            addCriterion("business_auditer not in", values, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerBetween(String value1, String value2) {
            addCriterion("business_auditer between", value1, value2, "businessAuditer");
            return (Criteria) this;
        }

        public Criteria andBusinessAuditerNotBetween(String value1, String value2) {
            addCriterion("business_auditer not between", value1, value2, "businessAuditer");
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

        public Criteria andFinanceAuditerIdIsNull() {
            addCriterion("finance_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdIsNotNull() {
            addCriterion("finance_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdEqualTo(Integer value) {
            addCriterion("finance_auditer_id =", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdNotEqualTo(Integer value) {
            addCriterion("finance_auditer_id <>", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdGreaterThan(Integer value) {
            addCriterion("finance_auditer_id >", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("finance_auditer_id >=", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdLessThan(Integer value) {
            addCriterion("finance_auditer_id <", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("finance_auditer_id <=", value, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdIn(List<Integer> values) {
            addCriterion("finance_auditer_id in", values, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdNotIn(List<Integer> values) {
            addCriterion("finance_auditer_id not in", values, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("finance_auditer_id between", value1, value2, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("finance_auditer_id not between", value1, value2, "financeAuditerId");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIsNull() {
            addCriterion("finance_auditer is null");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIsNotNull() {
            addCriterion("finance_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerEqualTo(String value) {
            addCriterion("finance_auditer =", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerNotEqualTo(String value) {
            addCriterion("finance_auditer <>", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerGreaterThan(String value) {
            addCriterion("finance_auditer >", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("finance_auditer >=", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerLessThan(String value) {
            addCriterion("finance_auditer <", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerLessThanOrEqualTo(String value) {
            addCriterion("finance_auditer <=", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerLike(String value) {
            addCriterion("finance_auditer like", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerNotLike(String value) {
            addCriterion("finance_auditer not like", value, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerIn(List<String> values) {
            addCriterion("finance_auditer in", values, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerNotIn(List<String> values) {
            addCriterion("finance_auditer not in", values, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerBetween(String value1, String value2) {
            addCriterion("finance_auditer between", value1, value2, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andFinanceAuditerNotBetween(String value1, String value2) {
            addCriterion("finance_auditer not between", value1, value2, "financeAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdIsNull() {
            addCriterion("bu_vp_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdIsNotNull() {
            addCriterion("bu_vp_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdEqualTo(Integer value) {
            addCriterion("bu_vp_auditer_id =", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdNotEqualTo(Integer value) {
            addCriterion("bu_vp_auditer_id <>", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdGreaterThan(Integer value) {
            addCriterion("bu_vp_auditer_id >", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bu_vp_auditer_id >=", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdLessThan(Integer value) {
            addCriterion("bu_vp_auditer_id <", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("bu_vp_auditer_id <=", value, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdIn(List<Integer> values) {
            addCriterion("bu_vp_auditer_id in", values, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdNotIn(List<Integer> values) {
            addCriterion("bu_vp_auditer_id not in", values, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("bu_vp_auditer_id between", value1, value2, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bu_vp_auditer_id not between", value1, value2, "buVpAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIsNull() {
            addCriterion("bu_vp_auditer is null");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIsNotNull() {
            addCriterion("bu_vp_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerEqualTo(String value) {
            addCriterion("bu_vp_auditer =", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerNotEqualTo(String value) {
            addCriterion("bu_vp_auditer <>", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerGreaterThan(String value) {
            addCriterion("bu_vp_auditer >", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("bu_vp_auditer >=", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerLessThan(String value) {
            addCriterion("bu_vp_auditer <", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerLessThanOrEqualTo(String value) {
            addCriterion("bu_vp_auditer <=", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerLike(String value) {
            addCriterion("bu_vp_auditer like", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerNotLike(String value) {
            addCriterion("bu_vp_auditer not like", value, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerIn(List<String> values) {
            addCriterion("bu_vp_auditer in", values, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerNotIn(List<String> values) {
            addCriterion("bu_vp_auditer not in", values, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerBetween(String value1, String value2) {
            addCriterion("bu_vp_auditer between", value1, value2, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andBuVpAuditerNotBetween(String value1, String value2) {
            addCriterion("bu_vp_auditer not between", value1, value2, "buVpAuditer");
            return (Criteria) this;
        }

        public Criteria andChairmanIdIsNull() {
            addCriterion("chairman_id is null");
            return (Criteria) this;
        }

        public Criteria andChairmanIdIsNotNull() {
            addCriterion("chairman_id is not null");
            return (Criteria) this;
        }

        public Criteria andChairmanIdEqualTo(Integer value) {
            addCriterion("chairman_id =", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdNotEqualTo(Integer value) {
            addCriterion("chairman_id <>", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdGreaterThan(Integer value) {
            addCriterion("chairman_id >", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("chairman_id >=", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdLessThan(Integer value) {
            addCriterion("chairman_id <", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdLessThanOrEqualTo(Integer value) {
            addCriterion("chairman_id <=", value, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdIn(List<Integer> values) {
            addCriterion("chairman_id in", values, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdNotIn(List<Integer> values) {
            addCriterion("chairman_id not in", values, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdBetween(Integer value1, Integer value2) {
            addCriterion("chairman_id between", value1, value2, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIdNotBetween(Integer value1, Integer value2) {
            addCriterion("chairman_id not between", value1, value2, "chairmanId");
            return (Criteria) this;
        }

        public Criteria andChairmanIsNull() {
            addCriterion("chairman is null");
            return (Criteria) this;
        }

        public Criteria andChairmanIsNotNull() {
            addCriterion("chairman is not null");
            return (Criteria) this;
        }

        public Criteria andChairmanEqualTo(String value) {
            addCriterion("chairman =", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanNotEqualTo(String value) {
            addCriterion("chairman <>", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanGreaterThan(String value) {
            addCriterion("chairman >", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanGreaterThanOrEqualTo(String value) {
            addCriterion("chairman >=", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanLessThan(String value) {
            addCriterion("chairman <", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanLessThanOrEqualTo(String value) {
            addCriterion("chairman <=", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanLike(String value) {
            addCriterion("chairman like", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanNotLike(String value) {
            addCriterion("chairman not like", value, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanIn(List<String> values) {
            addCriterion("chairman in", values, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanNotIn(List<String> values) {
            addCriterion("chairman not in", values, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanBetween(String value1, String value2) {
            addCriterion("chairman between", value1, value2, "chairman");
            return (Criteria) this;
        }

        public Criteria andChairmanNotBetween(String value1, String value2) {
            addCriterion("chairman not between", value1, value2, "chairman");
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

        public Criteria andSupplyAreaIsNull() {
            addCriterion("supply_area is null");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaIsNotNull() {
            addCriterion("supply_area is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaEqualTo(String value) {
            addCriterion("supply_area =", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaNotEqualTo(String value) {
            addCriterion("supply_area <>", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaGreaterThan(String value) {
            addCriterion("supply_area >", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaGreaterThanOrEqualTo(String value) {
            addCriterion("supply_area >=", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaLessThan(String value) {
            addCriterion("supply_area <", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaLessThanOrEqualTo(String value) {
            addCriterion("supply_area <=", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaLike(String value) {
            addCriterion("supply_area like", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaNotLike(String value) {
            addCriterion("supply_area not like", value, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaIn(List<String> values) {
            addCriterion("supply_area in", values, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaNotIn(List<String> values) {
            addCriterion("supply_area not in", values, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaBetween(String value1, String value2) {
            addCriterion("supply_area between", value1, value2, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andSupplyAreaNotBetween(String value1, String value2) {
            addCriterion("supply_area not between", value1, value2, "supplyArea");
            return (Criteria) this;
        }

        public Criteria andContractVersionIsNull() {
            addCriterion("contract_version is null");
            return (Criteria) this;
        }

        public Criteria andContractVersionIsNotNull() {
            addCriterion("contract_version is not null");
            return (Criteria) this;
        }

        public Criteria andContractVersionEqualTo(String value) {
            addCriterion("contract_version =", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionNotEqualTo(String value) {
            addCriterion("contract_version <>", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionGreaterThan(String value) {
            addCriterion("contract_version >", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionGreaterThanOrEqualTo(String value) {
            addCriterion("contract_version >=", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionLessThan(String value) {
            addCriterion("contract_version <", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionLessThanOrEqualTo(String value) {
            addCriterion("contract_version <=", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionLike(String value) {
            addCriterion("contract_version like", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionNotLike(String value) {
            addCriterion("contract_version not like", value, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionIn(List<String> values) {
            addCriterion("contract_version in", values, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionNotIn(List<String> values) {
            addCriterion("contract_version not in", values, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionBetween(String value1, String value2) {
            addCriterion("contract_version between", value1, value2, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andContractVersionNotBetween(String value1, String value2) {
            addCriterion("contract_version not between", value1, value2, "contractVersion");
            return (Criteria) this;
        }

        public Criteria andGoalCostIsNull() {
            addCriterion("goal_cost is null");
            return (Criteria) this;
        }

        public Criteria andGoalCostIsNotNull() {
            addCriterion("goal_cost is not null");
            return (Criteria) this;
        }

        public Criteria andGoalCostEqualTo(BigDecimal value) {
            addCriterion("goal_cost =", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostNotEqualTo(BigDecimal value) {
            addCriterion("goal_cost <>", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostGreaterThan(BigDecimal value) {
            addCriterion("goal_cost >", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("goal_cost >=", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostLessThan(BigDecimal value) {
            addCriterion("goal_cost <", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("goal_cost <=", value, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostIn(List<BigDecimal> values) {
            addCriterion("goal_cost in", values, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostNotIn(List<BigDecimal> values) {
            addCriterion("goal_cost not in", values, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goal_cost between", value1, value2, "goalCost");
            return (Criteria) this;
        }

        public Criteria andGoalCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("goal_cost not between", value1, value2, "goalCost");
            return (Criteria) this;
        }

        public Criteria andSaveAmountIsNull() {
            addCriterion("save_amount is null");
            return (Criteria) this;
        }

        public Criteria andSaveAmountIsNotNull() {
            addCriterion("save_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSaveAmountEqualTo(BigDecimal value) {
            addCriterion("save_amount =", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountNotEqualTo(BigDecimal value) {
            addCriterion("save_amount <>", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountGreaterThan(BigDecimal value) {
            addCriterion("save_amount >", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("save_amount >=", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountLessThan(BigDecimal value) {
            addCriterion("save_amount <", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("save_amount <=", value, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountIn(List<BigDecimal> values) {
            addCriterion("save_amount in", values, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountNotIn(List<BigDecimal> values) {
            addCriterion("save_amount not in", values, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("save_amount between", value1, value2, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("save_amount not between", value1, value2, "saveAmount");
            return (Criteria) this;
        }

        public Criteria andSaveModeIsNull() {
            addCriterion("save_mode is null");
            return (Criteria) this;
        }

        public Criteria andSaveModeIsNotNull() {
            addCriterion("save_mode is not null");
            return (Criteria) this;
        }

        public Criteria andSaveModeEqualTo(String value) {
            addCriterion("save_mode =", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeNotEqualTo(String value) {
            addCriterion("save_mode <>", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeGreaterThan(String value) {
            addCriterion("save_mode >", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeGreaterThanOrEqualTo(String value) {
            addCriterion("save_mode >=", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeLessThan(String value) {
            addCriterion("save_mode <", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeLessThanOrEqualTo(String value) {
            addCriterion("save_mode <=", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeLike(String value) {
            addCriterion("save_mode like", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeNotLike(String value) {
            addCriterion("save_mode not like", value, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeIn(List<String> values) {
            addCriterion("save_mode in", values, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeNotIn(List<String> values) {
            addCriterion("save_mode not in", values, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeBetween(String value1, String value2) {
            addCriterion("save_mode between", value1, value2, "saveMode");
            return (Criteria) this;
        }

        public Criteria andSaveModeNotBetween(String value1, String value2) {
            addCriterion("save_mode not between", value1, value2, "saveMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeIsNull() {
            addCriterion("price_mode is null");
            return (Criteria) this;
        }

        public Criteria andPriceModeIsNotNull() {
            addCriterion("price_mode is not null");
            return (Criteria) this;
        }

        public Criteria andPriceModeEqualTo(String value) {
            addCriterion("price_mode =", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotEqualTo(String value) {
            addCriterion("price_mode <>", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeGreaterThan(String value) {
            addCriterion("price_mode >", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeGreaterThanOrEqualTo(String value) {
            addCriterion("price_mode >=", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLessThan(String value) {
            addCriterion("price_mode <", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLessThanOrEqualTo(String value) {
            addCriterion("price_mode <=", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeLike(String value) {
            addCriterion("price_mode like", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotLike(String value) {
            addCriterion("price_mode not like", value, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeIn(List<String> values) {
            addCriterion("price_mode in", values, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotIn(List<String> values) {
            addCriterion("price_mode not in", values, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeBetween(String value1, String value2) {
            addCriterion("price_mode between", value1, value2, "priceMode");
            return (Criteria) this;
        }

        public Criteria andPriceModeNotBetween(String value1, String value2) {
            addCriterion("price_mode not between", value1, value2, "priceMode");
            return (Criteria) this;
        }

        public Criteria andProfitPercentIsNull() {
            addCriterion("profit_percent is null");
            return (Criteria) this;
        }

        public Criteria andProfitPercentIsNotNull() {
            addCriterion("profit_percent is not null");
            return (Criteria) this;
        }

        public Criteria andProfitPercentEqualTo(BigDecimal value) {
            addCriterion("profit_percent =", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentNotEqualTo(BigDecimal value) {
            addCriterion("profit_percent <>", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentGreaterThan(BigDecimal value) {
            addCriterion("profit_percent >", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_percent >=", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentLessThan(BigDecimal value) {
            addCriterion("profit_percent <", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_percent <=", value, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentIn(List<BigDecimal> values) {
            addCriterion("profit_percent in", values, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentNotIn(List<BigDecimal> values) {
            addCriterion("profit_percent not in", values, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_percent between", value1, value2, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andProfitPercentNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_percent not between", value1, value2, "profitPercent");
            return (Criteria) this;
        }

        public Criteria andContractTagIsNull() {
            addCriterion("contract_tag is null");
            return (Criteria) this;
        }

        public Criteria andContractTagIsNotNull() {
            addCriterion("contract_tag is not null");
            return (Criteria) this;
        }

        public Criteria andContractTagEqualTo(String value) {
            addCriterion("contract_tag =", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagNotEqualTo(String value) {
            addCriterion("contract_tag <>", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagGreaterThan(String value) {
            addCriterion("contract_tag >", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagGreaterThanOrEqualTo(String value) {
            addCriterion("contract_tag >=", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagLessThan(String value) {
            addCriterion("contract_tag <", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagLessThanOrEqualTo(String value) {
            addCriterion("contract_tag <=", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagLike(String value) {
            addCriterion("contract_tag like", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagNotLike(String value) {
            addCriterion("contract_tag not like", value, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagIn(List<String> values) {
            addCriterion("contract_tag in", values, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagNotIn(List<String> values) {
            addCriterion("contract_tag not in", values, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagBetween(String value1, String value2) {
            addCriterion("contract_tag between", value1, value2, "contractTag");
            return (Criteria) this;
        }

        public Criteria andContractTagNotBetween(String value1, String value2) {
            addCriterion("contract_tag not between", value1, value2, "contractTag");
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

        public Criteria andChairmanBoardIdIsNull() {
            addCriterion("chairman_board_id is null");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdIsNotNull() {
            addCriterion("chairman_board_id is not null");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdEqualTo(Integer value) {
            addCriterion("chairman_board_id =", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdNotEqualTo(Integer value) {
            addCriterion("chairman_board_id <>", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdGreaterThan(Integer value) {
            addCriterion("chairman_board_id >", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("chairman_board_id >=", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdLessThan(Integer value) {
            addCriterion("chairman_board_id <", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdLessThanOrEqualTo(Integer value) {
            addCriterion("chairman_board_id <=", value, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdIn(List<Integer> values) {
            addCriterion("chairman_board_id in", values, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdNotIn(List<Integer> values) {
            addCriterion("chairman_board_id not in", values, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdBetween(Integer value1, Integer value2) {
            addCriterion("chairman_board_id between", value1, value2, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIdNotBetween(Integer value1, Integer value2) {
            addCriterion("chairman_board_id not between", value1, value2, "chairmanBoardId");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIsNull() {
            addCriterion("chairman_board is null");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIsNotNull() {
            addCriterion("chairman_board is not null");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardEqualTo(String value) {
            addCriterion("chairman_board =", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardNotEqualTo(String value) {
            addCriterion("chairman_board <>", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardGreaterThan(String value) {
            addCriterion("chairman_board >", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardGreaterThanOrEqualTo(String value) {
            addCriterion("chairman_board >=", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardLessThan(String value) {
            addCriterion("chairman_board <", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardLessThanOrEqualTo(String value) {
            addCriterion("chairman_board <=", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardLike(String value) {
            addCriterion("chairman_board like", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardNotLike(String value) {
            addCriterion("chairman_board not like", value, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardIn(List<String> values) {
            addCriterion("chairman_board in", values, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardNotIn(List<String> values) {
            addCriterion("chairman_board not in", values, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardBetween(String value1, String value2) {
            addCriterion("chairman_board between", value1, value2, "chairmanBoard");
            return (Criteria) this;
        }

        public Criteria andChairmanBoardNotBetween(String value1, String value2) {
            addCriterion("chairman_board not between", value1, value2, "chairmanBoard");
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