package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class InstockExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InstockExample() {
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

        public Criteria andInspectReportIdIsNull() {
            addCriterion("inspect_report_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdIsNotNull() {
            addCriterion("inspect_report_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdEqualTo(Integer value) {
            addCriterion("inspect_report_id =", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotEqualTo(Integer value) {
            addCriterion("inspect_report_id <>", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdGreaterThan(Integer value) {
            addCriterion("inspect_report_id >", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_report_id >=", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdLessThan(Integer value) {
            addCriterion("inspect_report_id <", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_report_id <=", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdIn(List<Integer> values) {
            addCriterion("inspect_report_id in", values, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotIn(List<Integer> values) {
            addCriterion("inspect_report_id not in", values, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdBetween(Integer value1, Integer value2) {
            addCriterion("inspect_report_id between", value1, value2, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_report_id not between", value1, value2, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andInstockDateIsNull() {
            addCriterion("instock_date is null");
            return (Criteria) this;
        }

        public Criteria andInstockDateIsNotNull() {
            addCriterion("instock_date is not null");
            return (Criteria) this;
        }

        public Criteria andInstockDateEqualTo(Date value) {
            addCriterionForJDBCDate("instock_date =", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("instock_date <>", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateGreaterThan(Date value) {
            addCriterionForJDBCDate("instock_date >", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("instock_date >=", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateLessThan(Date value) {
            addCriterionForJDBCDate("instock_date <", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("instock_date <=", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateIn(List<Date> values) {
            addCriterionForJDBCDate("instock_date in", values, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("instock_date not in", values, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("instock_date between", value1, value2, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("instock_date not between", value1, value2, "instockDate");
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

        public Criteria andCurrentUserIdIsNull() {
            addCriterion("current_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdIsNotNull() {
            addCriterion("current_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdEqualTo(Integer value) {
            addCriterion("current_user_id =", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdNotEqualTo(Integer value) {
            addCriterion("current_user_id <>", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdGreaterThan(Integer value) {
            addCriterion("current_user_id >", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("current_user_id >=", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdLessThan(Integer value) {
            addCriterion("current_user_id <", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("current_user_id <=", value, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdIn(List<Integer> values) {
            addCriterion("current_user_id in", values, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdNotIn(List<Integer> values) {
            addCriterion("current_user_id not in", values, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdBetween(Integer value1, Integer value2) {
            addCriterion("current_user_id between", value1, value2, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("current_user_id not between", value1, value2, "currentUserId");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameIsNull() {
            addCriterion("current_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameIsNotNull() {
            addCriterion("current_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameEqualTo(String value) {
            addCriterion("current_user_name =", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameNotEqualTo(String value) {
            addCriterion("current_user_name <>", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameGreaterThan(String value) {
            addCriterion("current_user_name >", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("current_user_name >=", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameLessThan(String value) {
            addCriterion("current_user_name <", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameLessThanOrEqualTo(String value) {
            addCriterion("current_user_name <=", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameLike(String value) {
            addCriterion("current_user_name like", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameNotLike(String value) {
            addCriterion("current_user_name not like", value, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameIn(List<String> values) {
            addCriterion("current_user_name in", values, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameNotIn(List<String> values) {
            addCriterion("current_user_name not in", values, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameBetween(String value1, String value2) {
            addCriterion("current_user_name between", value1, value2, "currentUserName");
            return (Criteria) this;
        }

        public Criteria andCurrentUserNameNotBetween(String value1, String value2) {
            addCriterion("current_user_name not between", value1, value2, "currentUserName");
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

        public Criteria andUnameIsNull() {
            addCriterion("uname is null");
            return (Criteria) this;
        }

        public Criteria andUnameIsNotNull() {
            addCriterion("uname is not null");
            return (Criteria) this;
        }

        public Criteria andUnameEqualTo(String value) {
            addCriterion("uname =", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameNotEqualTo(String value) {
            addCriterion("uname <>", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameGreaterThan(String value) {
            addCriterion("uname >", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameGreaterThanOrEqualTo(String value) {
            addCriterion("uname >=", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameLessThan(String value) {
            addCriterion("uname <", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameLessThanOrEqualTo(String value) {
            addCriterion("uname <=", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameLike(String value) {
            addCriterion("uname like", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameNotLike(String value) {
            addCriterion("uname not like", value, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameIn(List<String> values) {
            addCriterion("uname in", values, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameNotIn(List<String> values) {
            addCriterion("uname not in", values, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameBetween(String value1, String value2) {
            addCriterion("uname between", value1, value2, "uname");
            return (Criteria) this;
        }

        public Criteria andUnameNotBetween(String value1, String value2) {
            addCriterion("uname not between", value1, value2, "uname");
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

        public Criteria andOutCheckEqualTo(Integer value) {
            addCriterion("out_check =", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotEqualTo(Integer value) {
            addCriterion("out_check <>", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThan(Integer value) {
            addCriterion("out_check >", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_check >=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThan(Integer value) {
            addCriterion("out_check <", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThanOrEqualTo(Integer value) {
            addCriterion("out_check <=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckIn(List<Integer> values) {
            addCriterion("out_check in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotIn(List<Integer> values) {
            addCriterion("out_check not in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckBetween(Integer value1, Integer value2) {
            addCriterion("out_check between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotBetween(Integer value1, Integer value2) {
            addCriterion("out_check not between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIsNull() {
            addCriterion("submenu_name is null");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIsNotNull() {
            addCriterion("submenu_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameEqualTo(String value) {
            addCriterion("submenu_name =", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotEqualTo(String value) {
            addCriterion("submenu_name <>", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameGreaterThan(String value) {
            addCriterion("submenu_name >", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("submenu_name >=", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLessThan(String value) {
            addCriterion("submenu_name <", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLessThanOrEqualTo(String value) {
            addCriterion("submenu_name <=", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLike(String value) {
            addCriterion("submenu_name like", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotLike(String value) {
            addCriterion("submenu_name not like", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIn(List<String> values) {
            addCriterion("submenu_name in", values, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotIn(List<String> values) {
            addCriterion("submenu_name not in", values, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameBetween(String value1, String value2) {
            addCriterion("submenu_name between", value1, value2, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotBetween(String value1, String value2) {
            addCriterion("submenu_name not between", value1, value2, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIsNull() {
            addCriterion("submenu_id is null");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIsNotNull() {
            addCriterion("submenu_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdEqualTo(Integer value) {
            addCriterion("submenu_id =", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotEqualTo(Integer value) {
            addCriterion("submenu_id <>", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdGreaterThan(Integer value) {
            addCriterion("submenu_id >", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("submenu_id >=", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdLessThan(Integer value) {
            addCriterion("submenu_id <", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdLessThanOrEqualTo(Integer value) {
            addCriterion("submenu_id <=", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIn(List<Integer> values) {
            addCriterion("submenu_id in", values, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotIn(List<Integer> values) {
            addCriterion("submenu_id not in", values, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdBetween(Integer value1, Integer value2) {
            addCriterion("submenu_id between", value1, value2, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotBetween(Integer value1, Integer value2) {
            addCriterion("submenu_id not between", value1, value2, "submenuId");
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