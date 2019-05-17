package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspectReportExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InspectReportExample() {
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

        public Criteria andInspectApplyIdIsNull() {
            addCriterion("inspect_apply_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdIsNotNull() {
            addCriterion("inspect_apply_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdEqualTo(Integer value) {
            addCriterion("inspect_apply_id =", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdNotEqualTo(Integer value) {
            addCriterion("inspect_apply_id <>", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdGreaterThan(Integer value) {
            addCriterion("inspect_apply_id >", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_apply_id >=", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdLessThan(Integer value) {
            addCriterion("inspect_apply_id <", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_apply_id <=", value, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdIn(List<Integer> values) {
            addCriterion("inspect_apply_id in", values, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdNotIn(List<Integer> values) {
            addCriterion("inspect_apply_id not in", values, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdBetween(Integer value1, Integer value2) {
            addCriterion("inspect_apply_id between", value1, value2, "inspectApplyId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_apply_id not between", value1, value2, "inspectApplyId");
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

        public Criteria andCheckTimesIsNull() {
            addCriterion("check_times is null");
            return (Criteria) this;
        }

        public Criteria andCheckTimesIsNotNull() {
            addCriterion("check_times is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTimesEqualTo(Integer value) {
            addCriterion("check_times =", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesNotEqualTo(Integer value) {
            addCriterion("check_times <>", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesGreaterThan(Integer value) {
            addCriterion("check_times >", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_times >=", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesLessThan(Integer value) {
            addCriterion("check_times <", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesLessThanOrEqualTo(Integer value) {
            addCriterion("check_times <=", value, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesIn(List<Integer> values) {
            addCriterion("check_times in", values, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesNotIn(List<Integer> values) {
            addCriterion("check_times not in", values, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesBetween(Integer value1, Integer value2) {
            addCriterion("check_times between", value1, value2, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckTimesNotBetween(Integer value1, Integer value2) {
            addCriterion("check_times not between", value1, value2, "checkTimes");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterion("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterion("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterion("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterion("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterion("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterion("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterion("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterion("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterion("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterion("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andReportFirstIsNull() {
            addCriterion("report_first is null");
            return (Criteria) this;
        }

        public Criteria andReportFirstIsNotNull() {
            addCriterion("report_first is not null");
            return (Criteria) this;
        }

        public Criteria andReportFirstEqualTo(Boolean value) {
            addCriterion("report_first =", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstNotEqualTo(Boolean value) {
            addCriterion("report_first <>", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstGreaterThan(Boolean value) {
            addCriterion("report_first >", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstGreaterThanOrEqualTo(Boolean value) {
            addCriterion("report_first >=", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstLessThan(Boolean value) {
            addCriterion("report_first <", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstLessThanOrEqualTo(Boolean value) {
            addCriterion("report_first <=", value, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstIn(List<Boolean> values) {
            addCriterion("report_first in", values, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstNotIn(List<Boolean> values) {
            addCriterion("report_first not in", values, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstBetween(Boolean value1, Boolean value2) {
            addCriterion("report_first between", value1, value2, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andReportFirstNotBetween(Boolean value1, Boolean value2) {
            addCriterion("report_first not between", value1, value2, "reportFirst");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdIsNull() {
            addCriterion("check_dept_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdIsNotNull() {
            addCriterion("check_dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdEqualTo(Integer value) {
            addCriterion("check_dept_id =", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdNotEqualTo(Integer value) {
            addCriterion("check_dept_id <>", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdGreaterThan(Integer value) {
            addCriterion("check_dept_id >", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_dept_id >=", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdLessThan(Integer value) {
            addCriterion("check_dept_id <", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_dept_id <=", value, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdIn(List<Integer> values) {
            addCriterion("check_dept_id in", values, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdNotIn(List<Integer> values) {
            addCriterion("check_dept_id not in", values, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("check_dept_id between", value1, value2, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_dept_id not between", value1, value2, "checkDeptId");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameIsNull() {
            addCriterion("check_dept_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameIsNotNull() {
            addCriterion("check_dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameEqualTo(String value) {
            addCriterion("check_dept_name =", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameNotEqualTo(String value) {
            addCriterion("check_dept_name <>", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameGreaterThan(String value) {
            addCriterion("check_dept_name >", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_dept_name >=", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameLessThan(String value) {
            addCriterion("check_dept_name <", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameLessThanOrEqualTo(String value) {
            addCriterion("check_dept_name <=", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameLike(String value) {
            addCriterion("check_dept_name like", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameNotLike(String value) {
            addCriterion("check_dept_name not like", value, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameIn(List<String> values) {
            addCriterion("check_dept_name in", values, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameNotIn(List<String> values) {
            addCriterion("check_dept_name not in", values, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameBetween(String value1, String value2) {
            addCriterion("check_dept_name between", value1, value2, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNameNotBetween(String value1, String value2) {
            addCriterion("check_dept_name not between", value1, value2, "checkDeptName");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNull() {
            addCriterion("check_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIsNotNull() {
            addCriterion("check_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdEqualTo(Integer value) {
            addCriterion("check_user_id =", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotEqualTo(Integer value) {
            addCriterion("check_user_id <>", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThan(Integer value) {
            addCriterion("check_user_id >", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("check_user_id >=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThan(Integer value) {
            addCriterion("check_user_id <", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("check_user_id <=", value, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdIn(List<Integer> values) {
            addCriterion("check_user_id in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotIn(List<Integer> values) {
            addCriterion("check_user_id not in", values, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("check_user_id not between", value1, value2, "checkUserId");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameIsNull() {
            addCriterion("check_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameIsNotNull() {
            addCriterion("check_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameEqualTo(String value) {
            addCriterion("check_user_name =", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameNotEqualTo(String value) {
            addCriterion("check_user_name <>", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameGreaterThan(String value) {
            addCriterion("check_user_name >", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("check_user_name >=", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameLessThan(String value) {
            addCriterion("check_user_name <", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameLessThanOrEqualTo(String value) {
            addCriterion("check_user_name <=", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameLike(String value) {
            addCriterion("check_user_name like", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameNotLike(String value) {
            addCriterion("check_user_name not like", value, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameIn(List<String> values) {
            addCriterion("check_user_name in", values, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameNotIn(List<String> values) {
            addCriterion("check_user_name not in", values, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameBetween(String value1, String value2) {
            addCriterion("check_user_name between", value1, value2, "checkUserName");
            return (Criteria) this;
        }

        public Criteria andCheckUserNameNotBetween(String value1, String value2) {
            addCriterion("check_user_name not between", value1, value2, "checkUserName");
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

        public Criteria andDoneDateIsNull() {
            addCriterion("done_date is null");
            return (Criteria) this;
        }

        public Criteria andDoneDateIsNotNull() {
            addCriterion("done_date is not null");
            return (Criteria) this;
        }

        public Criteria andDoneDateEqualTo(Date value) {
            addCriterion("done_date =", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotEqualTo(Date value) {
            addCriterion("done_date <>", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateGreaterThan(Date value) {
            addCriterion("done_date >", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateGreaterThanOrEqualTo(Date value) {
            addCriterion("done_date >=", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateLessThan(Date value) {
            addCriterion("done_date <", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateLessThanOrEqualTo(Date value) {
            addCriterion("done_date <=", value, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateIn(List<Date> values) {
            addCriterion("done_date in", values, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotIn(List<Date> values) {
            addCriterion("done_date not in", values, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateBetween(Date value1, Date value2) {
            addCriterion("done_date between", value1, value2, "doneDate");
            return (Criteria) this;
        }

        public Criteria andDoneDateNotBetween(Date value1, Date value2) {
            addCriterion("done_date not between", value1, value2, "doneDate");
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

        public Criteria andNcrNoIsNull() {
            addCriterion("ncr_no is null");
            return (Criteria) this;
        }

        public Criteria andNcrNoIsNotNull() {
            addCriterion("ncr_no is not null");
            return (Criteria) this;
        }

        public Criteria andNcrNoEqualTo(String value) {
            addCriterion("ncr_no =", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoNotEqualTo(String value) {
            addCriterion("ncr_no <>", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoGreaterThan(String value) {
            addCriterion("ncr_no >", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoGreaterThanOrEqualTo(String value) {
            addCriterion("ncr_no >=", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoLessThan(String value) {
            addCriterion("ncr_no <", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoLessThanOrEqualTo(String value) {
            addCriterion("ncr_no <=", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoLike(String value) {
            addCriterion("ncr_no like", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoNotLike(String value) {
            addCriterion("ncr_no not like", value, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoIn(List<String> values) {
            addCriterion("ncr_no in", values, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoNotIn(List<String> values) {
            addCriterion("ncr_no not in", values, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoBetween(String value1, String value2) {
            addCriterion("ncr_no between", value1, value2, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andNcrNoNotBetween(String value1, String value2) {
            addCriterion("ncr_no not between", value1, value2, "ncrNo");
            return (Criteria) this;
        }

        public Criteria andProcessIsNull() {
            addCriterion("process is null");
            return (Criteria) this;
        }

        public Criteria andProcessIsNotNull() {
            addCriterion("process is not null");
            return (Criteria) this;
        }

        public Criteria andProcessEqualTo(Boolean value) {
            addCriterion("process =", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotEqualTo(Boolean value) {
            addCriterion("process <>", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThan(Boolean value) {
            addCriterion("process >", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessGreaterThanOrEqualTo(Boolean value) {
            addCriterion("process >=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThan(Boolean value) {
            addCriterion("process <", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessLessThanOrEqualTo(Boolean value) {
            addCriterion("process <=", value, "process");
            return (Criteria) this;
        }

        public Criteria andProcessIn(List<Boolean> values) {
            addCriterion("process in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotIn(List<Boolean> values) {
            addCriterion("process not in", values, "process");
            return (Criteria) this;
        }

        public Criteria andProcessBetween(Boolean value1, Boolean value2) {
            addCriterion("process between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andProcessNotBetween(Boolean value1, Boolean value2) {
            addCriterion("process not between", value1, value2, "process");
            return (Criteria) this;
        }

        public Criteria andReportRemarksIsNull() {
            addCriterion("report_remarks is null");
            return (Criteria) this;
        }

        public Criteria andReportRemarksIsNotNull() {
            addCriterion("report_remarks is not null");
            return (Criteria) this;
        }

        public Criteria andReportRemarksEqualTo(String value) {
            addCriterion("report_remarks =", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksNotEqualTo(String value) {
            addCriterion("report_remarks <>", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksGreaterThan(String value) {
            addCriterion("report_remarks >", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("report_remarks >=", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksLessThan(String value) {
            addCriterion("report_remarks <", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksLessThanOrEqualTo(String value) {
            addCriterion("report_remarks <=", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksLike(String value) {
            addCriterion("report_remarks like", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksNotLike(String value) {
            addCriterion("report_remarks not like", value, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksIn(List<String> values) {
            addCriterion("report_remarks in", values, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksNotIn(List<String> values) {
            addCriterion("report_remarks not in", values, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksBetween(String value1, String value2) {
            addCriterion("report_remarks between", value1, value2, "reportRemarks");
            return (Criteria) this;
        }

        public Criteria andReportRemarksNotBetween(String value1, String value2) {
            addCriterion("report_remarks not between", value1, value2, "reportRemarks");
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

        public Criteria andLastDoneDateIsNull() {
            addCriterion("last_done_date is null");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateIsNotNull() {
            addCriterion("last_done_date is not null");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateEqualTo(Date value) {
            addCriterion("last_done_date =", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateNotEqualTo(Date value) {
            addCriterion("last_done_date <>", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateGreaterThan(Date value) {
            addCriterion("last_done_date >", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateGreaterThanOrEqualTo(Date value) {
            addCriterion("last_done_date >=", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateLessThan(Date value) {
            addCriterion("last_done_date <", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateLessThanOrEqualTo(Date value) {
            addCriterion("last_done_date <=", value, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateIn(List<Date> values) {
            addCriterion("last_done_date in", values, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateNotIn(List<Date> values) {
            addCriterion("last_done_date not in", values, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateBetween(Date value1, Date value2) {
            addCriterion("last_done_date between", value1, value2, "lastDoneDate");
            return (Criteria) this;
        }

        public Criteria andLastDoneDateNotBetween(Date value1, Date value2) {
            addCriterion("last_done_date not between", value1, value2, "lastDoneDate");
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