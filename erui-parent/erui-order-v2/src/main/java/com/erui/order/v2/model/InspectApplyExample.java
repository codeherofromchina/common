package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InspectApplyExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InspectApplyExample() {
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

        public Criteria andInspectApplyNoIsNull() {
            addCriterion("inspect_apply_no is null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoIsNotNull() {
            addCriterion("inspect_apply_no is not null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoEqualTo(String value) {
            addCriterion("inspect_apply_no =", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoNotEqualTo(String value) {
            addCriterion("inspect_apply_no <>", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoGreaterThan(String value) {
            addCriterion("inspect_apply_no >", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoGreaterThanOrEqualTo(String value) {
            addCriterion("inspect_apply_no >=", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoLessThan(String value) {
            addCriterion("inspect_apply_no <", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoLessThanOrEqualTo(String value) {
            addCriterion("inspect_apply_no <=", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoLike(String value) {
            addCriterion("inspect_apply_no like", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoNotLike(String value) {
            addCriterion("inspect_apply_no not like", value, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoIn(List<String> values) {
            addCriterion("inspect_apply_no in", values, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoNotIn(List<String> values) {
            addCriterion("inspect_apply_no not in", values, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoBetween(String value1, String value2) {
            addCriterion("inspect_apply_no between", value1, value2, "inspectApplyNo");
            return (Criteria) this;
        }

        public Criteria andInspectApplyNoNotBetween(String value1, String value2) {
            addCriterion("inspect_apply_no not between", value1, value2, "inspectApplyNo");
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

        public Criteria andPurchIdIsNull() {
            addCriterion("purch_id is null");
            return (Criteria) this;
        }

        public Criteria andPurchIdIsNotNull() {
            addCriterion("purch_id is not null");
            return (Criteria) this;
        }

        public Criteria andPurchIdEqualTo(Integer value) {
            addCriterion("purch_id =", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdNotEqualTo(Integer value) {
            addCriterion("purch_id <>", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdGreaterThan(Integer value) {
            addCriterion("purch_id >", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_id >=", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdLessThan(Integer value) {
            addCriterion("purch_id <", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdLessThanOrEqualTo(Integer value) {
            addCriterion("purch_id <=", value, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdIn(List<Integer> values) {
            addCriterion("purch_id in", values, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdNotIn(List<Integer> values) {
            addCriterion("purch_id not in", values, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdBetween(Integer value1, Integer value2) {
            addCriterion("purch_id between", value1, value2, "purchId");
            return (Criteria) this;
        }

        public Criteria andPurchIdNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_id not between", value1, value2, "purchId");
            return (Criteria) this;
        }

        public Criteria andMasterIsNull() {
            addCriterion("master is null");
            return (Criteria) this;
        }

        public Criteria andMasterIsNotNull() {
            addCriterion("master is not null");
            return (Criteria) this;
        }

        public Criteria andMasterEqualTo(Boolean value) {
            addCriterion("master =", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotEqualTo(Boolean value) {
            addCriterion("master <>", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterGreaterThan(Boolean value) {
            addCriterion("master >", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterGreaterThanOrEqualTo(Boolean value) {
            addCriterion("master >=", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterLessThan(Boolean value) {
            addCriterion("master <", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterLessThanOrEqualTo(Boolean value) {
            addCriterion("master <=", value, "master");
            return (Criteria) this;
        }

        public Criteria andMasterIn(List<Boolean> values) {
            addCriterion("master in", values, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotIn(List<Boolean> values) {
            addCriterion("master not in", values, "master");
            return (Criteria) this;
        }

        public Criteria andMasterBetween(Boolean value1, Boolean value2) {
            addCriterion("master between", value1, value2, "master");
            return (Criteria) this;
        }

        public Criteria andMasterNotBetween(Boolean value1, Boolean value2) {
            addCriterion("master not between", value1, value2, "master");
            return (Criteria) this;
        }

        public Criteria andPIdIsNull() {
            addCriterion("p_id is null");
            return (Criteria) this;
        }

        public Criteria andPIdIsNotNull() {
            addCriterion("p_id is not null");
            return (Criteria) this;
        }

        public Criteria andPIdEqualTo(Integer value) {
            addCriterion("p_id =", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotEqualTo(Integer value) {
            addCriterion("p_id <>", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThan(Integer value) {
            addCriterion("p_id >", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("p_id >=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThan(Integer value) {
            addCriterion("p_id <", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdLessThanOrEqualTo(Integer value) {
            addCriterion("p_id <=", value, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdIn(List<Integer> values) {
            addCriterion("p_id in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotIn(List<Integer> values) {
            addCriterion("p_id not in", values, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdBetween(Integer value1, Integer value2) {
            addCriterion("p_id between", value1, value2, "pId");
            return (Criteria) this;
        }

        public Criteria andPIdNotBetween(Integer value1, Integer value2) {
            addCriterion("p_id not between", value1, value2, "pId");
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

        public Criteria andPurchaseNameIsNull() {
            addCriterion("purchase_name is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameIsNotNull() {
            addCriterion("purchase_name is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameEqualTo(String value) {
            addCriterion("purchase_name =", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotEqualTo(String value) {
            addCriterion("purchase_name <>", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameGreaterThan(String value) {
            addCriterion("purchase_name >", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_name >=", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLessThan(String value) {
            addCriterion("purchase_name <", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLessThanOrEqualTo(String value) {
            addCriterion("purchase_name <=", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLike(String value) {
            addCriterion("purchase_name like", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotLike(String value) {
            addCriterion("purchase_name not like", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameIn(List<String> values) {
            addCriterion("purchase_name in", values, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotIn(List<String> values) {
            addCriterion("purchase_name not in", values, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameBetween(String value1, String value2) {
            addCriterion("purchase_name between", value1, value2, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotBetween(String value1, String value2) {
            addCriterion("purchase_name not between", value1, value2, "purchaseName");
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

        public Criteria andAbroadCoNameIsNull() {
            addCriterion("abroad_co_name is null");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameIsNotNull() {
            addCriterion("abroad_co_name is not null");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameEqualTo(String value) {
            addCriterion("abroad_co_name =", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameNotEqualTo(String value) {
            addCriterion("abroad_co_name <>", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameGreaterThan(String value) {
            addCriterion("abroad_co_name >", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameGreaterThanOrEqualTo(String value) {
            addCriterion("abroad_co_name >=", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameLessThan(String value) {
            addCriterion("abroad_co_name <", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameLessThanOrEqualTo(String value) {
            addCriterion("abroad_co_name <=", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameLike(String value) {
            addCriterion("abroad_co_name like", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameNotLike(String value) {
            addCriterion("abroad_co_name not like", value, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameIn(List<String> values) {
            addCriterion("abroad_co_name in", values, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameNotIn(List<String> values) {
            addCriterion("abroad_co_name not in", values, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameBetween(String value1, String value2) {
            addCriterion("abroad_co_name between", value1, value2, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andAbroadCoNameNotBetween(String value1, String value2) {
            addCriterion("abroad_co_name not between", value1, value2, "abroadCoName");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNull() {
            addCriterion("inspect_date is null");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNotNull() {
            addCriterion("inspect_date is not null");
            return (Criteria) this;
        }

        public Criteria andInspectDateEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date =", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date <>", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThan(Date value) {
            addCriterionForJDBCDate("inspect_date >", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date >=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThan(Date value) {
            addCriterionForJDBCDate("inspect_date <", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("inspect_date <=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateIn(List<Date> values) {
            addCriterionForJDBCDate("inspect_date in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("inspect_date not in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("inspect_date between", value1, value2, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("inspect_date not between", value1, value2, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andDirectIsNull() {
            addCriterion("direct is null");
            return (Criteria) this;
        }

        public Criteria andDirectIsNotNull() {
            addCriterion("direct is not null");
            return (Criteria) this;
        }

        public Criteria andDirectEqualTo(Boolean value) {
            addCriterion("direct =", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectNotEqualTo(Boolean value) {
            addCriterion("direct <>", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectGreaterThan(Boolean value) {
            addCriterion("direct >", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectGreaterThanOrEqualTo(Boolean value) {
            addCriterion("direct >=", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectLessThan(Boolean value) {
            addCriterion("direct <", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectLessThanOrEqualTo(Boolean value) {
            addCriterion("direct <=", value, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectIn(List<Boolean> values) {
            addCriterion("direct in", values, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectNotIn(List<Boolean> values) {
            addCriterion("direct not in", values, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectBetween(Boolean value1, Boolean value2) {
            addCriterion("direct between", value1, value2, "direct");
            return (Criteria) this;
        }

        public Criteria andDirectNotBetween(Boolean value1, Boolean value2) {
            addCriterion("direct not between", value1, value2, "direct");
            return (Criteria) this;
        }

        public Criteria andOutCheckIsNull() {
            addCriterion("out_check is null");
            return (Criteria) this;
        }

        public Criteria andOutCheckIsNotNull() {
            addCriterion("out_check is not null");
            return (Criteria) this;
        }

        public Criteria andOutCheckEqualTo(Boolean value) {
            addCriterion("out_check =", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotEqualTo(Boolean value) {
            addCriterion("out_check <>", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThan(Boolean value) {
            addCriterion("out_check >", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThanOrEqualTo(Boolean value) {
            addCriterion("out_check >=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThan(Boolean value) {
            addCriterion("out_check <", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThanOrEqualTo(Boolean value) {
            addCriterion("out_check <=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckIn(List<Boolean> values) {
            addCriterion("out_check in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotIn(List<Boolean> values) {
            addCriterion("out_check not in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckBetween(Boolean value1, Boolean value2) {
            addCriterion("out_check between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotBetween(Boolean value1, Boolean value2) {
            addCriterion("out_check not between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andHistoryIsNull() {
            addCriterion("history is null");
            return (Criteria) this;
        }

        public Criteria andHistoryIsNotNull() {
            addCriterion("history is not null");
            return (Criteria) this;
        }

        public Criteria andHistoryEqualTo(Boolean value) {
            addCriterion("history =", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryNotEqualTo(Boolean value) {
            addCriterion("history <>", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryGreaterThan(Boolean value) {
            addCriterion("history >", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryGreaterThanOrEqualTo(Boolean value) {
            addCriterion("history >=", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryLessThan(Boolean value) {
            addCriterion("history <", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryLessThanOrEqualTo(Boolean value) {
            addCriterion("history <=", value, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryIn(List<Boolean> values) {
            addCriterion("history in", values, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryNotIn(List<Boolean> values) {
            addCriterion("history not in", values, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryBetween(Boolean value1, Boolean value2) {
            addCriterion("history between", value1, value2, "history");
            return (Criteria) this;
        }

        public Criteria andHistoryNotBetween(Boolean value1, Boolean value2) {
            addCriterion("history not between", value1, value2, "history");
            return (Criteria) this;
        }

        public Criteria andNumIsNull() {
            addCriterion("num is null");
            return (Criteria) this;
        }

        public Criteria andNumIsNotNull() {
            addCriterion("num is not null");
            return (Criteria) this;
        }

        public Criteria andNumEqualTo(Integer value) {
            addCriterion("num =", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotEqualTo(Integer value) {
            addCriterion("num <>", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThan(Integer value) {
            addCriterion("num >", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("num >=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThan(Integer value) {
            addCriterion("num <", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumLessThanOrEqualTo(Integer value) {
            addCriterion("num <=", value, "num");
            return (Criteria) this;
        }

        public Criteria andNumIn(List<Integer> values) {
            addCriterion("num in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotIn(List<Integer> values) {
            addCriterion("num not in", values, "num");
            return (Criteria) this;
        }

        public Criteria andNumBetween(Integer value1, Integer value2) {
            addCriterion("num between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andNumNotBetween(Integer value1, Integer value2) {
            addCriterion("num not between", value1, value2, "num");
            return (Criteria) this;
        }

        public Criteria andMsgIsNull() {
            addCriterion("msg is null");
            return (Criteria) this;
        }

        public Criteria andMsgIsNotNull() {
            addCriterion("msg is not null");
            return (Criteria) this;
        }

        public Criteria andMsgEqualTo(String value) {
            addCriterion("msg =", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotEqualTo(String value) {
            addCriterion("msg <>", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThan(String value) {
            addCriterion("msg >", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgGreaterThanOrEqualTo(String value) {
            addCriterion("msg >=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThan(String value) {
            addCriterion("msg <", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLessThanOrEqualTo(String value) {
            addCriterion("msg <=", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgLike(String value) {
            addCriterion("msg like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotLike(String value) {
            addCriterion("msg not like", value, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgIn(List<String> values) {
            addCriterion("msg in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotIn(List<String> values) {
            addCriterion("msg not in", values, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgBetween(String value1, String value2) {
            addCriterion("msg between", value1, value2, "msg");
            return (Criteria) this;
        }

        public Criteria andMsgNotBetween(String value1, String value2) {
            addCriterion("msg not between", value1, value2, "msg");
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

        public Criteria andTmpMsgIsNull() {
            addCriterion("tmp_msg is null");
            return (Criteria) this;
        }

        public Criteria andTmpMsgIsNotNull() {
            addCriterion("tmp_msg is not null");
            return (Criteria) this;
        }

        public Criteria andTmpMsgEqualTo(String value) {
            addCriterion("tmp_msg =", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgNotEqualTo(String value) {
            addCriterion("tmp_msg <>", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgGreaterThan(String value) {
            addCriterion("tmp_msg >", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgGreaterThanOrEqualTo(String value) {
            addCriterion("tmp_msg >=", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgLessThan(String value) {
            addCriterion("tmp_msg <", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgLessThanOrEqualTo(String value) {
            addCriterion("tmp_msg <=", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgLike(String value) {
            addCriterion("tmp_msg like", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgNotLike(String value) {
            addCriterion("tmp_msg not like", value, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgIn(List<String> values) {
            addCriterion("tmp_msg in", values, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgNotIn(List<String> values) {
            addCriterion("tmp_msg not in", values, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgBetween(String value1, String value2) {
            addCriterion("tmp_msg between", value1, value2, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andTmpMsgNotBetween(String value1, String value2) {
            addCriterion("tmp_msg not between", value1, value2, "tmpMsg");
            return (Criteria) this;
        }

        public Criteria andPubStatusIsNull() {
            addCriterion("pub_status is null");
            return (Criteria) this;
        }

        public Criteria andPubStatusIsNotNull() {
            addCriterion("pub_status is not null");
            return (Criteria) this;
        }

        public Criteria andPubStatusEqualTo(Integer value) {
            addCriterion("pub_status =", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotEqualTo(Integer value) {
            addCriterion("pub_status <>", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThan(Integer value) {
            addCriterion("pub_status >", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("pub_status >=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThan(Integer value) {
            addCriterion("pub_status <", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusLessThanOrEqualTo(Integer value) {
            addCriterion("pub_status <=", value, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusIn(List<Integer> values) {
            addCriterion("pub_status in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotIn(List<Integer> values) {
            addCriterion("pub_status not in", values, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusBetween(Integer value1, Integer value2) {
            addCriterion("pub_status between", value1, value2, "pubStatus");
            return (Criteria) this;
        }

        public Criteria andPubStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("pub_status not between", value1, value2, "pubStatus");
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