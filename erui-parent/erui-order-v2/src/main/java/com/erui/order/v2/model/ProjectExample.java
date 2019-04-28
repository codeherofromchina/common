package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ProjectExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectExample() {
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

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
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

        public Criteria andProjectNameIsNull() {
            addCriterion("project_name is null");
            return (Criteria) this;
        }

        public Criteria andProjectNameIsNotNull() {
            addCriterion("project_name is not null");
            return (Criteria) this;
        }

        public Criteria andProjectNameEqualTo(String value) {
            addCriterion("project_name =", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotEqualTo(String value) {
            addCriterion("project_name <>", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThan(String value) {
            addCriterion("project_name >", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameGreaterThanOrEqualTo(String value) {
            addCriterion("project_name >=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThan(String value) {
            addCriterion("project_name <", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLessThanOrEqualTo(String value) {
            addCriterion("project_name <=", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameLike(String value) {
            addCriterion("project_name like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotLike(String value) {
            addCriterion("project_name not like", value, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameIn(List<String> values) {
            addCriterion("project_name in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotIn(List<String> values) {
            addCriterion("project_name not in", values, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameBetween(String value1, String value2) {
            addCriterion("project_name between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andProjectNameNotBetween(String value1, String value2) {
            addCriterion("project_name not between", value1, value2, "projectName");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNull() {
            addCriterion("start_date is null");
            return (Criteria) this;
        }

        public Criteria andStartDateIsNotNull() {
            addCriterion("start_date is not null");
            return (Criteria) this;
        }

        public Criteria andStartDateEqualTo(Date value) {
            addCriterion("start_date =", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotEqualTo(Date value) {
            addCriterion("start_date <>", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThan(Date value) {
            addCriterion("start_date >", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateGreaterThanOrEqualTo(Date value) {
            addCriterion("start_date >=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThan(Date value) {
            addCriterion("start_date <", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateLessThanOrEqualTo(Date value) {
            addCriterion("start_date <=", value, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateIn(List<Date> values) {
            addCriterion("start_date in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotIn(List<Date> values) {
            addCriterion("start_date not in", values, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateBetween(Date value1, Date value2) {
            addCriterion("start_date between", value1, value2, "startDate");
            return (Criteria) this;
        }

        public Criteria andStartDateNotBetween(Date value1, Date value2) {
            addCriterion("start_date not between", value1, value2, "startDate");
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

        public Criteria andProfitIsNull() {
            addCriterion("profit is null");
            return (Criteria) this;
        }

        public Criteria andProfitIsNotNull() {
            addCriterion("profit is not null");
            return (Criteria) this;
        }

        public Criteria andProfitEqualTo(BigDecimal value) {
            addCriterion("profit =", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotEqualTo(BigDecimal value) {
            addCriterion("profit <>", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitGreaterThan(BigDecimal value) {
            addCriterion("profit >", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit >=", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitLessThan(BigDecimal value) {
            addCriterion("profit <", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit <=", value, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitIn(List<BigDecimal> values) {
            addCriterion("profit in", values, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotIn(List<BigDecimal> values) {
            addCriterion("profit not in", values, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit between", value1, value2, "profit");
            return (Criteria) this;
        }

        public Criteria andProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit not between", value1, value2, "profit");
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

        public Criteria andProjectStatusIsNull() {
            addCriterion("project_status is null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIsNotNull() {
            addCriterion("project_status is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStatusEqualTo(String value) {
            addCriterion("project_status =", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotEqualTo(String value) {
            addCriterion("project_status <>", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThan(String value) {
            addCriterion("project_status >", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusGreaterThanOrEqualTo(String value) {
            addCriterion("project_status >=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThan(String value) {
            addCriterion("project_status <", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLessThanOrEqualTo(String value) {
            addCriterion("project_status <=", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusLike(String value) {
            addCriterion("project_status like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotLike(String value) {
            addCriterion("project_status not like", value, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusIn(List<String> values) {
            addCriterion("project_status in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotIn(List<String> values) {
            addCriterion("project_status not in", values, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusBetween(String value1, String value2) {
            addCriterion("project_status between", value1, value2, "projectStatus");
            return (Criteria) this;
        }

        public Criteria andProjectStatusNotBetween(String value1, String value2) {
            addCriterion("project_status not between", value1, value2, "projectStatus");
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

        public Criteria andPurchDoneIsNull() {
            addCriterion("purch_done is null");
            return (Criteria) this;
        }

        public Criteria andPurchDoneIsNotNull() {
            addCriterion("purch_done is not null");
            return (Criteria) this;
        }

        public Criteria andPurchDoneEqualTo(String value) {
            addCriterion("purch_done =", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneNotEqualTo(String value) {
            addCriterion("purch_done <>", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneGreaterThan(String value) {
            addCriterion("purch_done >", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneGreaterThanOrEqualTo(String value) {
            addCriterion("purch_done >=", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneLessThan(String value) {
            addCriterion("purch_done <", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneLessThanOrEqualTo(String value) {
            addCriterion("purch_done <=", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneLike(String value) {
            addCriterion("purch_done like", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneNotLike(String value) {
            addCriterion("purch_done not like", value, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneIn(List<String> values) {
            addCriterion("purch_done in", values, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneNotIn(List<String> values) {
            addCriterion("purch_done not in", values, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneBetween(String value1, String value2) {
            addCriterion("purch_done between", value1, value2, "purchDone");
            return (Criteria) this;
        }

        public Criteria andPurchDoneNotBetween(String value1, String value2) {
            addCriterion("purch_done not between", value1, value2, "purchDone");
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

        public Criteria andRequirePurchaseDateIsNull() {
            addCriterion("require_purchase_date is null");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateIsNotNull() {
            addCriterion("require_purchase_date is not null");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("require_purchase_date =", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("require_purchase_date <>", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("require_purchase_date >", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("require_purchase_date >=", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateLessThan(Date value) {
            addCriterionForJDBCDate("require_purchase_date <", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("require_purchase_date <=", value, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("require_purchase_date in", values, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("require_purchase_date not in", values, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("require_purchase_date between", value1, value2, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("require_purchase_date not between", value1, value2, "requirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andHasManagerIsNull() {
            addCriterion("has_manager is null");
            return (Criteria) this;
        }

        public Criteria andHasManagerIsNotNull() {
            addCriterion("has_manager is not null");
            return (Criteria) this;
        }

        public Criteria andHasManagerEqualTo(Integer value) {
            addCriterion("has_manager =", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerNotEqualTo(Integer value) {
            addCriterion("has_manager <>", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerGreaterThan(Integer value) {
            addCriterion("has_manager >", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerGreaterThanOrEqualTo(Integer value) {
            addCriterion("has_manager >=", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerLessThan(Integer value) {
            addCriterion("has_manager <", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerLessThanOrEqualTo(Integer value) {
            addCriterion("has_manager <=", value, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerIn(List<Integer> values) {
            addCriterion("has_manager in", values, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerNotIn(List<Integer> values) {
            addCriterion("has_manager not in", values, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerBetween(Integer value1, Integer value2) {
            addCriterion("has_manager between", value1, value2, "hasManager");
            return (Criteria) this;
        }

        public Criteria andHasManagerNotBetween(Integer value1, Integer value2) {
            addCriterion("has_manager not between", value1, value2, "hasManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIsNull() {
            addCriterion("purchase_uid is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIsNotNull() {
            addCriterion("purchase_uid is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidEqualTo(Integer value) {
            addCriterion("purchase_uid =", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotEqualTo(Integer value) {
            addCriterion("purchase_uid <>", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidGreaterThan(Integer value) {
            addCriterion("purchase_uid >", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_uid >=", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidLessThan(Integer value) {
            addCriterion("purchase_uid <", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_uid <=", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIn(List<Integer> values) {
            addCriterion("purchase_uid in", values, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotIn(List<Integer> values) {
            addCriterion("purchase_uid not in", values, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidBetween(Integer value1, Integer value2) {
            addCriterion("purchase_uid between", value1, value2, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_uid not between", value1, value2, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidIsNull() {
            addCriterion("quality_uid is null");
            return (Criteria) this;
        }

        public Criteria andQualityUidIsNotNull() {
            addCriterion("quality_uid is not null");
            return (Criteria) this;
        }

        public Criteria andQualityUidEqualTo(Integer value) {
            addCriterion("quality_uid =", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidNotEqualTo(Integer value) {
            addCriterion("quality_uid <>", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidGreaterThan(Integer value) {
            addCriterion("quality_uid >", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("quality_uid >=", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidLessThan(Integer value) {
            addCriterion("quality_uid <", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidLessThanOrEqualTo(Integer value) {
            addCriterion("quality_uid <=", value, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidIn(List<Integer> values) {
            addCriterion("quality_uid in", values, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidNotIn(List<Integer> values) {
            addCriterion("quality_uid not in", values, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidBetween(Integer value1, Integer value2) {
            addCriterion("quality_uid between", value1, value2, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andQualityUidNotBetween(Integer value1, Integer value2) {
            addCriterion("quality_uid not between", value1, value2, "qualityUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidIsNull() {
            addCriterion("business_uid is null");
            return (Criteria) this;
        }

        public Criteria andBusinessUidIsNotNull() {
            addCriterion("business_uid is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessUidEqualTo(Integer value) {
            addCriterion("business_uid =", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidNotEqualTo(Integer value) {
            addCriterion("business_uid <>", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidGreaterThan(Integer value) {
            addCriterion("business_uid >", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("business_uid >=", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidLessThan(Integer value) {
            addCriterion("business_uid <", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidLessThanOrEqualTo(Integer value) {
            addCriterion("business_uid <=", value, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidIn(List<Integer> values) {
            addCriterion("business_uid in", values, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidNotIn(List<Integer> values) {
            addCriterion("business_uid not in", values, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidBetween(Integer value1, Integer value2) {
            addCriterion("business_uid between", value1, value2, "businessUid");
            return (Criteria) this;
        }

        public Criteria andBusinessUidNotBetween(Integer value1, Integer value2) {
            addCriterion("business_uid not between", value1, value2, "businessUid");
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

        public Criteria andManagerUidIsNull() {
            addCriterion("manager_uid is null");
            return (Criteria) this;
        }

        public Criteria andManagerUidIsNotNull() {
            addCriterion("manager_uid is not null");
            return (Criteria) this;
        }

        public Criteria andManagerUidEqualTo(Integer value) {
            addCriterion("manager_uid =", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidNotEqualTo(Integer value) {
            addCriterion("manager_uid <>", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidGreaterThan(Integer value) {
            addCriterion("manager_uid >", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("manager_uid >=", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidLessThan(Integer value) {
            addCriterion("manager_uid <", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidLessThanOrEqualTo(Integer value) {
            addCriterion("manager_uid <=", value, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidIn(List<Integer> values) {
            addCriterion("manager_uid in", values, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidNotIn(List<Integer> values) {
            addCriterion("manager_uid not in", values, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidBetween(Integer value1, Integer value2) {
            addCriterion("manager_uid between", value1, value2, "managerUid");
            return (Criteria) this;
        }

        public Criteria andManagerUidNotBetween(Integer value1, Integer value2) {
            addCriterion("manager_uid not between", value1, value2, "managerUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidIsNull() {
            addCriterion("logistics_uid is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidIsNotNull() {
            addCriterion("logistics_uid is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidEqualTo(Integer value) {
            addCriterion("logistics_uid =", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidNotEqualTo(Integer value) {
            addCriterion("logistics_uid <>", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidGreaterThan(Integer value) {
            addCriterion("logistics_uid >", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_uid >=", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidLessThan(Integer value) {
            addCriterion("logistics_uid <", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_uid <=", value, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidIn(List<Integer> values) {
            addCriterion("logistics_uid in", values, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidNotIn(List<Integer> values) {
            addCriterion("logistics_uid not in", values, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidBetween(Integer value1, Integer value2) {
            addCriterion("logistics_uid between", value1, value2, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andLogisticsUidNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_uid not between", value1, value2, "logisticsUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidIsNull() {
            addCriterion("warehouse_uid is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidIsNotNull() {
            addCriterion("warehouse_uid is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidEqualTo(Integer value) {
            addCriterion("warehouse_uid =", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidNotEqualTo(Integer value) {
            addCriterion("warehouse_uid <>", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidGreaterThan(Integer value) {
            addCriterion("warehouse_uid >", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("warehouse_uid >=", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidLessThan(Integer value) {
            addCriterion("warehouse_uid <", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidLessThanOrEqualTo(Integer value) {
            addCriterion("warehouse_uid <=", value, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidIn(List<Integer> values) {
            addCriterion("warehouse_uid in", values, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidNotIn(List<Integer> values) {
            addCriterion("warehouse_uid not in", values, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidBetween(Integer value1, Integer value2) {
            addCriterion("warehouse_uid between", value1, value2, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andWarehouseUidNotBetween(Integer value1, Integer value2) {
            addCriterion("warehouse_uid not between", value1, value2, "warehouseUid");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdIsNull() {
            addCriterion("send_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdIsNotNull() {
            addCriterion("send_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdEqualTo(Integer value) {
            addCriterion("send_dept_id =", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdNotEqualTo(Integer value) {
            addCriterion("send_dept_id <>", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdGreaterThan(Integer value) {
            addCriterion("send_dept_id >", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_dept_id >=", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdLessThan(Integer value) {
            addCriterion("send_dept_id <", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("send_dept_id <=", value, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdIn(List<Integer> values) {
            addCriterion("send_dept_id in", values, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdNotIn(List<Integer> values) {
            addCriterion("send_dept_id not in", values, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("send_dept_id between", value1, value2, "sendDeptId");
            return (Criteria) this;
        }

        public Criteria andSendDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("send_dept_id not between", value1, value2, "sendDeptId");
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

        public Criteria andQualityNameIsNull() {
            addCriterion("quality_name is null");
            return (Criteria) this;
        }

        public Criteria andQualityNameIsNotNull() {
            addCriterion("quality_name is not null");
            return (Criteria) this;
        }

        public Criteria andQualityNameEqualTo(String value) {
            addCriterion("quality_name =", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameNotEqualTo(String value) {
            addCriterion("quality_name <>", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameGreaterThan(String value) {
            addCriterion("quality_name >", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameGreaterThanOrEqualTo(String value) {
            addCriterion("quality_name >=", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameLessThan(String value) {
            addCriterion("quality_name <", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameLessThanOrEqualTo(String value) {
            addCriterion("quality_name <=", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameLike(String value) {
            addCriterion("quality_name like", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameNotLike(String value) {
            addCriterion("quality_name not like", value, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameIn(List<String> values) {
            addCriterion("quality_name in", values, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameNotIn(List<String> values) {
            addCriterion("quality_name not in", values, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameBetween(String value1, String value2) {
            addCriterion("quality_name between", value1, value2, "qualityName");
            return (Criteria) this;
        }

        public Criteria andQualityNameNotBetween(String value1, String value2) {
            addCriterion("quality_name not between", value1, value2, "qualityName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNull() {
            addCriterion("warehouse_name is null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIsNotNull() {
            addCriterion("warehouse_name is not null");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameEqualTo(String value) {
            addCriterion("warehouse_name =", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotEqualTo(String value) {
            addCriterion("warehouse_name <>", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThan(String value) {
            addCriterion("warehouse_name >", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("warehouse_name >=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThan(String value) {
            addCriterion("warehouse_name <", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLessThanOrEqualTo(String value) {
            addCriterion("warehouse_name <=", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameLike(String value) {
            addCriterion("warehouse_name like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotLike(String value) {
            addCriterion("warehouse_name not like", value, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameIn(List<String> values) {
            addCriterion("warehouse_name in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotIn(List<String> values) {
            addCriterion("warehouse_name not in", values, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameBetween(String value1, String value2) {
            addCriterion("warehouse_name between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andWarehouseNameNotBetween(String value1, String value2) {
            addCriterion("warehouse_name not between", value1, value2, "warehouseName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIsNull() {
            addCriterion("logistics_name is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIsNotNull() {
            addCriterion("logistics_name is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameEqualTo(String value) {
            addCriterion("logistics_name =", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotEqualTo(String value) {
            addCriterion("logistics_name <>", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameGreaterThan(String value) {
            addCriterion("logistics_name >", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_name >=", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLessThan(String value) {
            addCriterion("logistics_name <", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLessThanOrEqualTo(String value) {
            addCriterion("logistics_name <=", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameLike(String value) {
            addCriterion("logistics_name like", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotLike(String value) {
            addCriterion("logistics_name not like", value, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameIn(List<String> values) {
            addCriterion("logistics_name in", values, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotIn(List<String> values) {
            addCriterion("logistics_name not in", values, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameBetween(String value1, String value2) {
            addCriterion("logistics_name between", value1, value2, "logisticsName");
            return (Criteria) this;
        }

        public Criteria andLogisticsNameNotBetween(String value1, String value2) {
            addCriterion("logistics_name not between", value1, value2, "logisticsName");
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

        public Criteria andPurchTimeIsNull() {
            addCriterion("purch_time is null");
            return (Criteria) this;
        }

        public Criteria andPurchTimeIsNotNull() {
            addCriterion("purch_time is not null");
            return (Criteria) this;
        }

        public Criteria andPurchTimeEqualTo(Date value) {
            addCriterion("purch_time =", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeNotEqualTo(Date value) {
            addCriterion("purch_time <>", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeGreaterThan(Date value) {
            addCriterion("purch_time >", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("purch_time >=", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeLessThan(Date value) {
            addCriterion("purch_time <", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeLessThanOrEqualTo(Date value) {
            addCriterion("purch_time <=", value, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeIn(List<Date> values) {
            addCriterion("purch_time in", values, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeNotIn(List<Date> values) {
            addCriterion("purch_time not in", values, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeBetween(Date value1, Date value2) {
            addCriterion("purch_time between", value1, value2, "purchTime");
            return (Criteria) this;
        }

        public Criteria andPurchTimeNotBetween(Date value1, Date value2) {
            addCriterion("purch_time not between", value1, value2, "purchTime");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditIsNull() {
            addCriterion("logistics_audit is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditIsNotNull() {
            addCriterion("logistics_audit is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditEqualTo(Integer value) {
            addCriterion("logistics_audit =", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditNotEqualTo(Integer value) {
            addCriterion("logistics_audit <>", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditGreaterThan(Integer value) {
            addCriterion("logistics_audit >", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_audit >=", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditLessThan(Integer value) {
            addCriterion("logistics_audit <", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_audit <=", value, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditIn(List<Integer> values) {
            addCriterion("logistics_audit in", values, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditNotIn(List<Integer> values) {
            addCriterion("logistics_audit not in", values, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditBetween(Integer value1, Integer value2) {
            addCriterion("logistics_audit between", value1, value2, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_audit not between", value1, value2, "logisticsAudit");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelIsNull() {
            addCriterion("auditing_level is null");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelIsNotNull() {
            addCriterion("auditing_level is not null");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelEqualTo(Integer value) {
            addCriterion("auditing_level =", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelNotEqualTo(Integer value) {
            addCriterion("auditing_level <>", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelGreaterThan(Integer value) {
            addCriterion("auditing_level >", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelGreaterThanOrEqualTo(Integer value) {
            addCriterion("auditing_level >=", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelLessThan(Integer value) {
            addCriterion("auditing_level <", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelLessThanOrEqualTo(Integer value) {
            addCriterion("auditing_level <=", value, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelIn(List<Integer> values) {
            addCriterion("auditing_level in", values, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelNotIn(List<Integer> values) {
            addCriterion("auditing_level not in", values, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelBetween(Integer value1, Integer value2) {
            addCriterion("auditing_level between", value1, value2, "auditingLevel");
            return (Criteria) this;
        }

        public Criteria andAuditingLevelNotBetween(Integer value1, Integer value2) {
            addCriterion("auditing_level not between", value1, value2, "auditingLevel");
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

        public Criteria andLogisticsAuditerIdIsNull() {
            addCriterion("logistics_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdIsNotNull() {
            addCriterion("logistics_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdEqualTo(Integer value) {
            addCriterion("logistics_auditer_id =", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdNotEqualTo(Integer value) {
            addCriterion("logistics_auditer_id <>", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdGreaterThan(Integer value) {
            addCriterion("logistics_auditer_id >", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_auditer_id >=", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdLessThan(Integer value) {
            addCriterion("logistics_auditer_id <", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_auditer_id <=", value, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdIn(List<Integer> values) {
            addCriterion("logistics_auditer_id in", values, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdNotIn(List<Integer> values) {
            addCriterion("logistics_auditer_id not in", values, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("logistics_auditer_id between", value1, value2, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_auditer_id not between", value1, value2, "logisticsAuditerId");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIsNull() {
            addCriterion("logistics_auditer is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIsNotNull() {
            addCriterion("logistics_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerEqualTo(String value) {
            addCriterion("logistics_auditer =", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerNotEqualTo(String value) {
            addCriterion("logistics_auditer <>", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerGreaterThan(String value) {
            addCriterion("logistics_auditer >", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_auditer >=", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerLessThan(String value) {
            addCriterion("logistics_auditer <", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerLessThanOrEqualTo(String value) {
            addCriterion("logistics_auditer <=", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerLike(String value) {
            addCriterion("logistics_auditer like", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerNotLike(String value) {
            addCriterion("logistics_auditer not like", value, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerIn(List<String> values) {
            addCriterion("logistics_auditer in", values, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerNotIn(List<String> values) {
            addCriterion("logistics_auditer not in", values, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerBetween(String value1, String value2) {
            addCriterion("logistics_auditer between", value1, value2, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andLogisticsAuditerNotBetween(String value1, String value2) {
            addCriterion("logistics_auditer not between", value1, value2, "logisticsAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdIsNull() {
            addCriterion("bu_auditer_id is null");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdIsNotNull() {
            addCriterion("bu_auditer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdEqualTo(Integer value) {
            addCriterion("bu_auditer_id =", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdNotEqualTo(Integer value) {
            addCriterion("bu_auditer_id <>", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdGreaterThan(Integer value) {
            addCriterion("bu_auditer_id >", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("bu_auditer_id >=", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdLessThan(Integer value) {
            addCriterion("bu_auditer_id <", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdLessThanOrEqualTo(Integer value) {
            addCriterion("bu_auditer_id <=", value, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdIn(List<Integer> values) {
            addCriterion("bu_auditer_id in", values, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdNotIn(List<Integer> values) {
            addCriterion("bu_auditer_id not in", values, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdBetween(Integer value1, Integer value2) {
            addCriterion("bu_auditer_id between", value1, value2, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("bu_auditer_id not between", value1, value2, "buAuditerId");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIsNull() {
            addCriterion("bu_auditer is null");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIsNotNull() {
            addCriterion("bu_auditer is not null");
            return (Criteria) this;
        }

        public Criteria andBuAuditerEqualTo(String value) {
            addCriterion("bu_auditer =", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerNotEqualTo(String value) {
            addCriterion("bu_auditer <>", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerGreaterThan(String value) {
            addCriterion("bu_auditer >", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerGreaterThanOrEqualTo(String value) {
            addCriterion("bu_auditer >=", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerLessThan(String value) {
            addCriterion("bu_auditer <", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerLessThanOrEqualTo(String value) {
            addCriterion("bu_auditer <=", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerLike(String value) {
            addCriterion("bu_auditer like", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerNotLike(String value) {
            addCriterion("bu_auditer not like", value, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerIn(List<String> values) {
            addCriterion("bu_auditer in", values, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerNotIn(List<String> values) {
            addCriterion("bu_auditer not in", values, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerBetween(String value1, String value2) {
            addCriterion("bu_auditer between", value1, value2, "buAuditer");
            return (Criteria) this;
        }

        public Criteria andBuAuditerNotBetween(String value1, String value2) {
            addCriterion("bu_auditer not between", value1, value2, "buAuditer");
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

        public Criteria andCeoIdIsNull() {
            addCriterion("ceo_id is null");
            return (Criteria) this;
        }

        public Criteria andCeoIdIsNotNull() {
            addCriterion("ceo_id is not null");
            return (Criteria) this;
        }

        public Criteria andCeoIdEqualTo(Integer value) {
            addCriterion("ceo_id =", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdNotEqualTo(Integer value) {
            addCriterion("ceo_id <>", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdGreaterThan(Integer value) {
            addCriterion("ceo_id >", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("ceo_id >=", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdLessThan(Integer value) {
            addCriterion("ceo_id <", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdLessThanOrEqualTo(Integer value) {
            addCriterion("ceo_id <=", value, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdIn(List<Integer> values) {
            addCriterion("ceo_id in", values, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdNotIn(List<Integer> values) {
            addCriterion("ceo_id not in", values, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdBetween(Integer value1, Integer value2) {
            addCriterion("ceo_id between", value1, value2, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIdNotBetween(Integer value1, Integer value2) {
            addCriterion("ceo_id not between", value1, value2, "ceoId");
            return (Criteria) this;
        }

        public Criteria andCeoIsNull() {
            addCriterion("ceo is null");
            return (Criteria) this;
        }

        public Criteria andCeoIsNotNull() {
            addCriterion("ceo is not null");
            return (Criteria) this;
        }

        public Criteria andCeoEqualTo(String value) {
            addCriterion("ceo =", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoNotEqualTo(String value) {
            addCriterion("ceo <>", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoGreaterThan(String value) {
            addCriterion("ceo >", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoGreaterThanOrEqualTo(String value) {
            addCriterion("ceo >=", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoLessThan(String value) {
            addCriterion("ceo <", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoLessThanOrEqualTo(String value) {
            addCriterion("ceo <=", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoLike(String value) {
            addCriterion("ceo like", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoNotLike(String value) {
            addCriterion("ceo not like", value, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoIn(List<String> values) {
            addCriterion("ceo in", values, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoNotIn(List<String> values) {
            addCriterion("ceo not in", values, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoBetween(String value1, String value2) {
            addCriterion("ceo between", value1, value2, "ceo");
            return (Criteria) this;
        }

        public Criteria andCeoNotBetween(String value1, String value2) {
            addCriterion("ceo not between", value1, value2, "ceo");
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