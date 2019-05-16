package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeliverConsignExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeliverConsignExample() {
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

        public Criteria andDeliverConsignNoIsNull() {
            addCriterion("deliver_consign_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIsNotNull() {
            addCriterion("deliver_consign_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoEqualTo(String value) {
            addCriterion("deliver_consign_no =", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotEqualTo(String value) {
            addCriterion("deliver_consign_no <>", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThan(String value) {
            addCriterion("deliver_consign_no >", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no >=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThan(String value) {
            addCriterion("deliver_consign_no <", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no <=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLike(String value) {
            addCriterion("deliver_consign_no like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotLike(String value) {
            addCriterion("deliver_consign_no not like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIn(List<String> values) {
            addCriterion("deliver_consign_no in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotIn(List<String> values) {
            addCriterion("deliver_consign_no not in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoBetween(String value1, String value2) {
            addCriterion("deliver_consign_no between", value1, value2, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotBetween(String value1, String value2) {
            addCriterion("deliver_consign_no not between", value1, value2, "deliverConsignNo");
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

        public Criteria andDeptIdIsNull() {
            addCriterion("dept_id is null");
            return (Criteria) this;
        }

        public Criteria andDeptIdIsNotNull() {
            addCriterion("dept_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeptIdEqualTo(Integer value) {
            addCriterion("dept_id =", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotEqualTo(Integer value) {
            addCriterion("dept_id <>", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThan(Integer value) {
            addCriterion("dept_id >", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("dept_id >=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThan(Integer value) {
            addCriterion("dept_id <", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdLessThanOrEqualTo(Integer value) {
            addCriterion("dept_id <=", value, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdIn(List<Integer> values) {
            addCriterion("dept_id in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotIn(List<Integer> values) {
            addCriterion("dept_id not in", values, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdBetween(Integer value1, Integer value2) {
            addCriterion("dept_id between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andDeptIdNotBetween(Integer value1, Integer value2) {
            addCriterion("dept_id not between", value1, value2, "deptId");
            return (Criteria) this;
        }

        public Criteria andCoIdIsNull() {
            addCriterion("co_id is null");
            return (Criteria) this;
        }

        public Criteria andCoIdIsNotNull() {
            addCriterion("co_id is not null");
            return (Criteria) this;
        }

        public Criteria andCoIdEqualTo(String value) {
            addCriterion("co_id =", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdNotEqualTo(String value) {
            addCriterion("co_id <>", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdGreaterThan(String value) {
            addCriterion("co_id >", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdGreaterThanOrEqualTo(String value) {
            addCriterion("co_id >=", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdLessThan(String value) {
            addCriterion("co_id <", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdLessThanOrEqualTo(String value) {
            addCriterion("co_id <=", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdLike(String value) {
            addCriterion("co_id like", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdNotLike(String value) {
            addCriterion("co_id not like", value, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdIn(List<String> values) {
            addCriterion("co_id in", values, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdNotIn(List<String> values) {
            addCriterion("co_id not in", values, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdBetween(String value1, String value2) {
            addCriterion("co_id between", value1, value2, "coId");
            return (Criteria) this;
        }

        public Criteria andCoIdNotBetween(String value1, String value2) {
            addCriterion("co_id not between", value1, value2, "coId");
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

        public Criteria andWriteDateIsNull() {
            addCriterion("write_date is null");
            return (Criteria) this;
        }

        public Criteria andWriteDateIsNotNull() {
            addCriterion("write_date is not null");
            return (Criteria) this;
        }

        public Criteria andWriteDateEqualTo(Date value) {
            addCriterionForJDBCDate("write_date =", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("write_date <>", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateGreaterThan(Date value) {
            addCriterionForJDBCDate("write_date >", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("write_date >=", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateLessThan(Date value) {
            addCriterionForJDBCDate("write_date <", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("write_date <=", value, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateIn(List<Date> values) {
            addCriterionForJDBCDate("write_date in", values, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("write_date not in", values, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("write_date between", value1, value2, "writeDate");
            return (Criteria) this;
        }

        public Criteria andWriteDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("write_date not between", value1, value2, "writeDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateIsNull() {
            addCriterion("booking_date is null");
            return (Criteria) this;
        }

        public Criteria andBookingDateIsNotNull() {
            addCriterion("booking_date is not null");
            return (Criteria) this;
        }

        public Criteria andBookingDateEqualTo(Date value) {
            addCriterionForJDBCDate("booking_date =", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("booking_date <>", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateGreaterThan(Date value) {
            addCriterionForJDBCDate("booking_date >", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("booking_date >=", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateLessThan(Date value) {
            addCriterionForJDBCDate("booking_date <", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("booking_date <=", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateIn(List<Date> values) {
            addCriterionForJDBCDate("booking_date in", values, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("booking_date not in", values, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("booking_date between", value1, value2, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("booking_date not between", value1, value2, "bookingDate");
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

        public Criteria andDeliverYnIsNull() {
            addCriterion("deliver_yn is null");
            return (Criteria) this;
        }

        public Criteria andDeliverYnIsNotNull() {
            addCriterion("deliver_yn is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverYnEqualTo(Integer value) {
            addCriterion("deliver_yn =", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnNotEqualTo(Integer value) {
            addCriterion("deliver_yn <>", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnGreaterThan(Integer value) {
            addCriterion("deliver_yn >", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_yn >=", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnLessThan(Integer value) {
            addCriterion("deliver_yn <", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_yn <=", value, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnIn(List<Integer> values) {
            addCriterion("deliver_yn in", values, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnNotIn(List<Integer> values) {
            addCriterion("deliver_yn not in", values, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnBetween(Integer value1, Integer value2) {
            addCriterion("deliver_yn between", value1, value2, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andDeliverYnNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_yn not between", value1, value2, "deliverYn");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditIsNull() {
            addCriterion("line_of_credit is null");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditIsNotNull() {
            addCriterion("line_of_credit is not null");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditEqualTo(BigDecimal value) {
            addCriterion("line_of_credit =", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditNotEqualTo(BigDecimal value) {
            addCriterion("line_of_credit <>", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditGreaterThan(BigDecimal value) {
            addCriterion("line_of_credit >", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("line_of_credit >=", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditLessThan(BigDecimal value) {
            addCriterion("line_of_credit <", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditLessThanOrEqualTo(BigDecimal value) {
            addCriterion("line_of_credit <=", value, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditIn(List<BigDecimal> values) {
            addCriterion("line_of_credit in", values, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditNotIn(List<BigDecimal> values) {
            addCriterion("line_of_credit not in", values, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("line_of_credit between", value1, value2, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andLineOfCreditNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("line_of_credit not between", value1, value2, "lineOfCredit");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableIsNull() {
            addCriterion("credit_available is null");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableIsNotNull() {
            addCriterion("credit_available is not null");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableEqualTo(BigDecimal value) {
            addCriterion("credit_available =", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableNotEqualTo(BigDecimal value) {
            addCriterion("credit_available <>", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableGreaterThan(BigDecimal value) {
            addCriterion("credit_available >", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_available >=", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableLessThan(BigDecimal value) {
            addCriterion("credit_available <", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableLessThanOrEqualTo(BigDecimal value) {
            addCriterion("credit_available <=", value, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableIn(List<BigDecimal> values) {
            addCriterion("credit_available in", values, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableNotIn(List<BigDecimal> values) {
            addCriterion("credit_available not in", values, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_available between", value1, value2, "creditAvailable");
            return (Criteria) this;
        }

        public Criteria andCreditAvailableNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("credit_available not between", value1, value2, "creditAvailable");
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

        public Criteria andThisShipmentsMoneyIsNull() {
            addCriterion("this_shipments_money is null");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyIsNotNull() {
            addCriterion("this_shipments_money is not null");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyEqualTo(BigDecimal value) {
            addCriterion("this_shipments_money =", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyNotEqualTo(BigDecimal value) {
            addCriterion("this_shipments_money <>", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyGreaterThan(BigDecimal value) {
            addCriterion("this_shipments_money >", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("this_shipments_money >=", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyLessThan(BigDecimal value) {
            addCriterion("this_shipments_money <", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("this_shipments_money <=", value, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyIn(List<BigDecimal> values) {
            addCriterion("this_shipments_money in", values, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyNotIn(List<BigDecimal> values) {
            addCriterion("this_shipments_money not in", values, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("this_shipments_money between", value1, value2, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andThisShipmentsMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("this_shipments_money not between", value1, value2, "thisShipmentsMoney");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseIsNull() {
            addCriterion("invoice_rise is null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseIsNotNull() {
            addCriterion("invoice_rise is not null");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseEqualTo(String value) {
            addCriterion("invoice_rise =", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseNotEqualTo(String value) {
            addCriterion("invoice_rise <>", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseGreaterThan(String value) {
            addCriterion("invoice_rise >", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseGreaterThanOrEqualTo(String value) {
            addCriterion("invoice_rise >=", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseLessThan(String value) {
            addCriterion("invoice_rise <", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseLessThanOrEqualTo(String value) {
            addCriterion("invoice_rise <=", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseLike(String value) {
            addCriterion("invoice_rise like", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseNotLike(String value) {
            addCriterion("invoice_rise not like", value, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseIn(List<String> values) {
            addCriterion("invoice_rise in", values, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseNotIn(List<String> values) {
            addCriterion("invoice_rise not in", values, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseBetween(String value1, String value2) {
            addCriterion("invoice_rise between", value1, value2, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andInvoiceRiseNotBetween(String value1, String value2) {
            addCriterion("invoice_rise not between", value1, value2, "invoiceRise");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureIsNull() {
            addCriterion("business_nature is null");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureIsNotNull() {
            addCriterion("business_nature is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureEqualTo(String value) {
            addCriterion("business_nature =", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureNotEqualTo(String value) {
            addCriterion("business_nature <>", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureGreaterThan(String value) {
            addCriterion("business_nature >", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureGreaterThanOrEqualTo(String value) {
            addCriterion("business_nature >=", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureLessThan(String value) {
            addCriterion("business_nature <", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureLessThanOrEqualTo(String value) {
            addCriterion("business_nature <=", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureLike(String value) {
            addCriterion("business_nature like", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureNotLike(String value) {
            addCriterion("business_nature not like", value, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureIn(List<String> values) {
            addCriterion("business_nature in", values, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureNotIn(List<String> values) {
            addCriterion("business_nature not in", values, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureBetween(String value1, String value2) {
            addCriterion("business_nature between", value1, value2, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessNatureNotBetween(String value1, String value2) {
            addCriterion("business_nature not between", value1, value2, "businessNature");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchIsNull() {
            addCriterion("business_sketch is null");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchIsNotNull() {
            addCriterion("business_sketch is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchEqualTo(String value) {
            addCriterion("business_sketch =", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotEqualTo(String value) {
            addCriterion("business_sketch <>", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchGreaterThan(String value) {
            addCriterion("business_sketch >", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchGreaterThanOrEqualTo(String value) {
            addCriterion("business_sketch >=", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLessThan(String value) {
            addCriterion("business_sketch <", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLessThanOrEqualTo(String value) {
            addCriterion("business_sketch <=", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchLike(String value) {
            addCriterion("business_sketch like", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotLike(String value) {
            addCriterion("business_sketch not like", value, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchIn(List<String> values) {
            addCriterion("business_sketch in", values, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotIn(List<String> values) {
            addCriterion("business_sketch not in", values, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchBetween(String value1, String value2) {
            addCriterion("business_sketch between", value1, value2, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andBusinessSketchNotBetween(String value1, String value2) {
            addCriterion("business_sketch not between", value1, value2, "businessSketch");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyIsNull() {
            addCriterion("declare_customs_money is null");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyIsNotNull() {
            addCriterion("declare_customs_money is not null");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyEqualTo(BigDecimal value) {
            addCriterion("declare_customs_money =", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyNotEqualTo(BigDecimal value) {
            addCriterion("declare_customs_money <>", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyGreaterThan(BigDecimal value) {
            addCriterion("declare_customs_money >", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("declare_customs_money >=", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyLessThan(BigDecimal value) {
            addCriterion("declare_customs_money <", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("declare_customs_money <=", value, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyIn(List<BigDecimal> values) {
            addCriterion("declare_customs_money in", values, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyNotIn(List<BigDecimal> values) {
            addCriterion("declare_customs_money not in", values, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("declare_customs_money between", value1, value2, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andDeclareCustomsMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("declare_customs_money not between", value1, value2, "declareCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIsNull() {
            addCriterion("trade_money is null");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIsNotNull() {
            addCriterion("trade_money is not null");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyEqualTo(BigDecimal value) {
            addCriterion("trade_money =", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotEqualTo(BigDecimal value) {
            addCriterion("trade_money <>", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyGreaterThan(BigDecimal value) {
            addCriterion("trade_money >", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("trade_money >=", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyLessThan(BigDecimal value) {
            addCriterion("trade_money <", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("trade_money <=", value, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyIn(List<BigDecimal> values) {
            addCriterion("trade_money in", values, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotIn(List<BigDecimal> values) {
            addCriterion("trade_money not in", values, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trade_money between", value1, value2, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andTradeMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("trade_money not between", value1, value2, "tradeMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyIsNull() {
            addCriterion("direct_transfer_money is null");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyIsNotNull() {
            addCriterion("direct_transfer_money is not null");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyEqualTo(BigDecimal value) {
            addCriterion("direct_transfer_money =", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyNotEqualTo(BigDecimal value) {
            addCriterion("direct_transfer_money <>", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyGreaterThan(BigDecimal value) {
            addCriterion("direct_transfer_money >", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("direct_transfer_money >=", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyLessThan(BigDecimal value) {
            addCriterion("direct_transfer_money <", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("direct_transfer_money <=", value, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyIn(List<BigDecimal> values) {
            addCriterion("direct_transfer_money in", values, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyNotIn(List<BigDecimal> values) {
            addCriterion("direct_transfer_money not in", values, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("direct_transfer_money between", value1, value2, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andDirectTransferMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("direct_transfer_money not between", value1, value2, "directTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyIsNull() {
            addCriterion("indirect_transfer_money is null");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyIsNotNull() {
            addCriterion("indirect_transfer_money is not null");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyEqualTo(BigDecimal value) {
            addCriterion("indirect_transfer_money =", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyNotEqualTo(BigDecimal value) {
            addCriterion("indirect_transfer_money <>", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyGreaterThan(BigDecimal value) {
            addCriterion("indirect_transfer_money >", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("indirect_transfer_money >=", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyLessThan(BigDecimal value) {
            addCriterion("indirect_transfer_money <", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("indirect_transfer_money <=", value, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyIn(List<BigDecimal> values) {
            addCriterion("indirect_transfer_money in", values, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyNotIn(List<BigDecimal> values) {
            addCriterion("indirect_transfer_money not in", values, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("indirect_transfer_money between", value1, value2, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andIndirectTransferMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("indirect_transfer_money not between", value1, value2, "indirectTransferMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyIsNull() {
            addCriterion("clear_customs_money is null");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyIsNotNull() {
            addCriterion("clear_customs_money is not null");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyEqualTo(BigDecimal value) {
            addCriterion("clear_customs_money =", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyNotEqualTo(BigDecimal value) {
            addCriterion("clear_customs_money <>", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyGreaterThan(BigDecimal value) {
            addCriterion("clear_customs_money >", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("clear_customs_money >=", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyLessThan(BigDecimal value) {
            addCriterion("clear_customs_money <", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("clear_customs_money <=", value, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyIn(List<BigDecimal> values) {
            addCriterion("clear_customs_money in", values, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyNotIn(List<BigDecimal> values) {
            addCriterion("clear_customs_money not in", values, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("clear_customs_money between", value1, value2, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andClearCustomsMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("clear_customs_money not between", value1, value2, "clearCustomsMoney");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNull() {
            addCriterion("pay_method is null");
            return (Criteria) this;
        }

        public Criteria andPayMethodIsNotNull() {
            addCriterion("pay_method is not null");
            return (Criteria) this;
        }

        public Criteria andPayMethodEqualTo(Integer value) {
            addCriterion("pay_method =", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotEqualTo(Integer value) {
            addCriterion("pay_method <>", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThan(Integer value) {
            addCriterion("pay_method >", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodGreaterThanOrEqualTo(Integer value) {
            addCriterion("pay_method >=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThan(Integer value) {
            addCriterion("pay_method <", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodLessThanOrEqualTo(Integer value) {
            addCriterion("pay_method <=", value, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodIn(List<Integer> values) {
            addCriterion("pay_method in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotIn(List<Integer> values) {
            addCriterion("pay_method not in", values, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodBetween(Integer value1, Integer value2) {
            addCriterion("pay_method between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andPayMethodNotBetween(Integer value1, Integer value2) {
            addCriterion("pay_method not between", value1, value2, "payMethod");
            return (Criteria) this;
        }

        public Criteria andShippingBatchIsNull() {
            addCriterion("shipping_batch is null");
            return (Criteria) this;
        }

        public Criteria andShippingBatchIsNotNull() {
            addCriterion("shipping_batch is not null");
            return (Criteria) this;
        }

        public Criteria andShippingBatchEqualTo(String value) {
            addCriterion("shipping_batch =", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchNotEqualTo(String value) {
            addCriterion("shipping_batch <>", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchGreaterThan(String value) {
            addCriterion("shipping_batch >", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchGreaterThanOrEqualTo(String value) {
            addCriterion("shipping_batch >=", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchLessThan(String value) {
            addCriterion("shipping_batch <", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchLessThanOrEqualTo(String value) {
            addCriterion("shipping_batch <=", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchLike(String value) {
            addCriterion("shipping_batch like", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchNotLike(String value) {
            addCriterion("shipping_batch not like", value, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchIn(List<String> values) {
            addCriterion("shipping_batch in", values, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchNotIn(List<String> values) {
            addCriterion("shipping_batch not in", values, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchBetween(String value1, String value2) {
            addCriterion("shipping_batch between", value1, value2, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andShippingBatchNotBetween(String value1, String value2) {
            addCriterion("shipping_batch not between", value1, value2, "shippingBatch");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainIsNull() {
            addCriterion("more_batch_explain is null");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainIsNotNull() {
            addCriterion("more_batch_explain is not null");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainEqualTo(String value) {
            addCriterion("more_batch_explain =", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainNotEqualTo(String value) {
            addCriterion("more_batch_explain <>", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainGreaterThan(String value) {
            addCriterion("more_batch_explain >", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainGreaterThanOrEqualTo(String value) {
            addCriterion("more_batch_explain >=", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainLessThan(String value) {
            addCriterion("more_batch_explain <", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainLessThanOrEqualTo(String value) {
            addCriterion("more_batch_explain <=", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainLike(String value) {
            addCriterion("more_batch_explain like", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainNotLike(String value) {
            addCriterion("more_batch_explain not like", value, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainIn(List<String> values) {
            addCriterion("more_batch_explain in", values, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainNotIn(List<String> values) {
            addCriterion("more_batch_explain not in", values, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainBetween(String value1, String value2) {
            addCriterion("more_batch_explain between", value1, value2, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andMoreBatchExplainNotBetween(String value1, String value2) {
            addCriterion("more_batch_explain not between", value1, value2, "moreBatchExplain");
            return (Criteria) this;
        }

        public Criteria andIsDangerousIsNull() {
            addCriterion("is_dangerous is null");
            return (Criteria) this;
        }

        public Criteria andIsDangerousIsNotNull() {
            addCriterion("is_dangerous is not null");
            return (Criteria) this;
        }

        public Criteria andIsDangerousEqualTo(Integer value) {
            addCriterion("is_dangerous =", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousNotEqualTo(Integer value) {
            addCriterion("is_dangerous <>", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousGreaterThan(Integer value) {
            addCriterion("is_dangerous >", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_dangerous >=", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousLessThan(Integer value) {
            addCriterion("is_dangerous <", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousLessThanOrEqualTo(Integer value) {
            addCriterion("is_dangerous <=", value, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousIn(List<Integer> values) {
            addCriterion("is_dangerous in", values, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousNotIn(List<Integer> values) {
            addCriterion("is_dangerous not in", values, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousBetween(Integer value1, Integer value2) {
            addCriterion("is_dangerous between", value1, value2, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andIsDangerousNotBetween(Integer value1, Integer value2) {
            addCriterion("is_dangerous not between", value1, value2, "isDangerous");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIsNull() {
            addCriterion("goods_deposit_place is null");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIsNotNull() {
            addCriterion("goods_deposit_place is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceEqualTo(String value) {
            addCriterion("goods_deposit_place =", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotEqualTo(String value) {
            addCriterion("goods_deposit_place <>", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceGreaterThan(String value) {
            addCriterion("goods_deposit_place >", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("goods_deposit_place >=", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLessThan(String value) {
            addCriterion("goods_deposit_place <", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLessThanOrEqualTo(String value) {
            addCriterion("goods_deposit_place <=", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceLike(String value) {
            addCriterion("goods_deposit_place like", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotLike(String value) {
            addCriterion("goods_deposit_place not like", value, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceIn(List<String> values) {
            addCriterion("goods_deposit_place in", values, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotIn(List<String> values) {
            addCriterion("goods_deposit_place not in", values, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceBetween(String value1, String value2) {
            addCriterion("goods_deposit_place between", value1, value2, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andGoodsDepositPlaceNotBetween(String value1, String value2) {
            addCriterion("goods_deposit_place not between", value1, value2, "goodsDepositPlace");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceIsNull() {
            addCriterion("has_insurance is null");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceIsNotNull() {
            addCriterion("has_insurance is not null");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceEqualTo(Integer value) {
            addCriterion("has_insurance =", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceNotEqualTo(Integer value) {
            addCriterion("has_insurance <>", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceGreaterThan(Integer value) {
            addCriterion("has_insurance >", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceGreaterThanOrEqualTo(Integer value) {
            addCriterion("has_insurance >=", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceLessThan(Integer value) {
            addCriterion("has_insurance <", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceLessThanOrEqualTo(Integer value) {
            addCriterion("has_insurance <=", value, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceIn(List<Integer> values) {
            addCriterion("has_insurance in", values, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceNotIn(List<Integer> values) {
            addCriterion("has_insurance not in", values, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceBetween(Integer value1, Integer value2) {
            addCriterion("has_insurance between", value1, value2, "hasInsurance");
            return (Criteria) this;
        }

        public Criteria andHasInsuranceNotBetween(Integer value1, Integer value2) {
            addCriterion("has_insurance not between", value1, value2, "hasInsurance");
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

        public Criteria andLogisticsLeaderIdIsNull() {
            addCriterion("logistics_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdIsNotNull() {
            addCriterion("logistics_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdEqualTo(Integer value) {
            addCriterion("logistics_leader_id =", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdNotEqualTo(Integer value) {
            addCriterion("logistics_leader_id <>", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdGreaterThan(Integer value) {
            addCriterion("logistics_leader_id >", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_leader_id >=", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdLessThan(Integer value) {
            addCriterion("logistics_leader_id <", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_leader_id <=", value, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdIn(List<Integer> values) {
            addCriterion("logistics_leader_id in", values, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdNotIn(List<Integer> values) {
            addCriterion("logistics_leader_id not in", values, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("logistics_leader_id between", value1, value2, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_leader_id not between", value1, value2, "logisticsLeaderId");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIsNull() {
            addCriterion("logistics_leader is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIsNotNull() {
            addCriterion("logistics_leader is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderEqualTo(String value) {
            addCriterion("logistics_leader =", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderNotEqualTo(String value) {
            addCriterion("logistics_leader <>", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderGreaterThan(String value) {
            addCriterion("logistics_leader >", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_leader >=", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderLessThan(String value) {
            addCriterion("logistics_leader <", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderLessThanOrEqualTo(String value) {
            addCriterion("logistics_leader <=", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderLike(String value) {
            addCriterion("logistics_leader like", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderNotLike(String value) {
            addCriterion("logistics_leader not like", value, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderIn(List<String> values) {
            addCriterion("logistics_leader in", values, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderNotIn(List<String> values) {
            addCriterion("logistics_leader not in", values, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderBetween(String value1, String value2) {
            addCriterion("logistics_leader between", value1, value2, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andLogisticsLeaderNotBetween(String value1, String value2) {
            addCriterion("logistics_leader not between", value1, value2, "logisticsLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdIsNull() {
            addCriterion("business_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdIsNotNull() {
            addCriterion("business_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdEqualTo(Integer value) {
            addCriterion("business_leader_id =", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdNotEqualTo(Integer value) {
            addCriterion("business_leader_id <>", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdGreaterThan(Integer value) {
            addCriterion("business_leader_id >", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("business_leader_id >=", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdLessThan(Integer value) {
            addCriterion("business_leader_id <", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("business_leader_id <=", value, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdIn(List<Integer> values) {
            addCriterion("business_leader_id in", values, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdNotIn(List<Integer> values) {
            addCriterion("business_leader_id not in", values, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("business_leader_id between", value1, value2, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("business_leader_id not between", value1, value2, "businessLeaderId");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIsNull() {
            addCriterion("business_leader is null");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIsNotNull() {
            addCriterion("business_leader is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderEqualTo(String value) {
            addCriterion("business_leader =", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderNotEqualTo(String value) {
            addCriterion("business_leader <>", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderGreaterThan(String value) {
            addCriterion("business_leader >", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderGreaterThanOrEqualTo(String value) {
            addCriterion("business_leader >=", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderLessThan(String value) {
            addCriterion("business_leader <", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderLessThanOrEqualTo(String value) {
            addCriterion("business_leader <=", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderLike(String value) {
            addCriterion("business_leader like", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderNotLike(String value) {
            addCriterion("business_leader not like", value, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderIn(List<String> values) {
            addCriterion("business_leader in", values, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderNotIn(List<String> values) {
            addCriterion("business_leader not in", values, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderBetween(String value1, String value2) {
            addCriterion("business_leader between", value1, value2, "businessLeader");
            return (Criteria) this;
        }

        public Criteria andBusinessLeaderNotBetween(String value1, String value2) {
            addCriterion("business_leader not between", value1, value2, "businessLeader");
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

        public Criteria andCrmCodeOrNameIsNull() {
            addCriterion("crm_code_or_name is null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameIsNotNull() {
            addCriterion("crm_code_or_name is not null");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameEqualTo(String value) {
            addCriterion("crm_code_or_name =", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotEqualTo(String value) {
            addCriterion("crm_code_or_name <>", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameGreaterThan(String value) {
            addCriterion("crm_code_or_name >", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameGreaterThanOrEqualTo(String value) {
            addCriterion("crm_code_or_name >=", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLessThan(String value) {
            addCriterion("crm_code_or_name <", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLessThanOrEqualTo(String value) {
            addCriterion("crm_code_or_name <=", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameLike(String value) {
            addCriterion("crm_code_or_name like", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotLike(String value) {
            addCriterion("crm_code_or_name not like", value, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameIn(List<String> values) {
            addCriterion("crm_code_or_name in", values, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotIn(List<String> values) {
            addCriterion("crm_code_or_name not in", values, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameBetween(String value1, String value2) {
            addCriterion("crm_code_or_name between", value1, value2, "crmCodeOrName");
            return (Criteria) this;
        }

        public Criteria andCrmCodeOrNameNotBetween(String value1, String value2) {
            addCriterion("crm_code_or_name not between", value1, value2, "crmCodeOrName");
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

        public Criteria andDeptNameIsNull() {
            addCriterion("dept_name is null");
            return (Criteria) this;
        }

        public Criteria andDeptNameIsNotNull() {
            addCriterion("dept_name is not null");
            return (Criteria) this;
        }

        public Criteria andDeptNameEqualTo(String value) {
            addCriterion("dept_name =", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotEqualTo(String value) {
            addCriterion("dept_name <>", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThan(String value) {
            addCriterion("dept_name >", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameGreaterThanOrEqualTo(String value) {
            addCriterion("dept_name >=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThan(String value) {
            addCriterion("dept_name <", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLessThanOrEqualTo(String value) {
            addCriterion("dept_name <=", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameLike(String value) {
            addCriterion("dept_name like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotLike(String value) {
            addCriterion("dept_name not like", value, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameIn(List<String> values) {
            addCriterion("dept_name in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotIn(List<String> values) {
            addCriterion("dept_name not in", values, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameBetween(String value1, String value2) {
            addCriterion("dept_name between", value1, value2, "deptName");
            return (Criteria) this;
        }

        public Criteria andDeptNameNotBetween(String value1, String value2) {
            addCriterion("dept_name not between", value1, value2, "deptName");
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

        public Criteria andBookingOfficerIdIsNull() {
            addCriterion("booking_officer_id is null");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdIsNotNull() {
            addCriterion("booking_officer_id is not null");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdEqualTo(Integer value) {
            addCriterion("booking_officer_id =", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdNotEqualTo(Integer value) {
            addCriterion("booking_officer_id <>", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdGreaterThan(Integer value) {
            addCriterion("booking_officer_id >", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("booking_officer_id >=", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdLessThan(Integer value) {
            addCriterion("booking_officer_id <", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdLessThanOrEqualTo(Integer value) {
            addCriterion("booking_officer_id <=", value, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdIn(List<Integer> values) {
            addCriterion("booking_officer_id in", values, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdNotIn(List<Integer> values) {
            addCriterion("booking_officer_id not in", values, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdBetween(Integer value1, Integer value2) {
            addCriterion("booking_officer_id between", value1, value2, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIdNotBetween(Integer value1, Integer value2) {
            addCriterion("booking_officer_id not between", value1, value2, "bookingOfficerId");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIsNull() {
            addCriterion("booking_officer is null");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIsNotNull() {
            addCriterion("booking_officer is not null");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerEqualTo(String value) {
            addCriterion("booking_officer =", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerNotEqualTo(String value) {
            addCriterion("booking_officer <>", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerGreaterThan(String value) {
            addCriterion("booking_officer >", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerGreaterThanOrEqualTo(String value) {
            addCriterion("booking_officer >=", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerLessThan(String value) {
            addCriterion("booking_officer <", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerLessThanOrEqualTo(String value) {
            addCriterion("booking_officer <=", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerLike(String value) {
            addCriterion("booking_officer like", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerNotLike(String value) {
            addCriterion("booking_officer not like", value, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerIn(List<String> values) {
            addCriterion("booking_officer in", values, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerNotIn(List<String> values) {
            addCriterion("booking_officer not in", values, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerBetween(String value1, String value2) {
            addCriterion("booking_officer between", value1, value2, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andBookingOfficerNotBetween(String value1, String value2) {
            addCriterion("booking_officer not between", value1, value2, "bookingOfficer");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIsNull() {
            addCriterion("operation_specialist_id is null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIsNotNull() {
            addCriterion("operation_specialist_id is not null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdEqualTo(Integer value) {
            addCriterion("operation_specialist_id =", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotEqualTo(Integer value) {
            addCriterion("operation_specialist_id <>", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdGreaterThan(Integer value) {
            addCriterion("operation_specialist_id >", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("operation_specialist_id >=", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdLessThan(Integer value) {
            addCriterion("operation_specialist_id <", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdLessThanOrEqualTo(Integer value) {
            addCriterion("operation_specialist_id <=", value, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdIn(List<Integer> values) {
            addCriterion("operation_specialist_id in", values, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotIn(List<Integer> values) {
            addCriterion("operation_specialist_id not in", values, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdBetween(Integer value1, Integer value2) {
            addCriterion("operation_specialist_id between", value1, value2, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIdNotBetween(Integer value1, Integer value2) {
            addCriterion("operation_specialist_id not between", value1, value2, "operationSpecialistId");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIsNull() {
            addCriterion("operation_specialist is null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIsNotNull() {
            addCriterion("operation_specialist is not null");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistEqualTo(String value) {
            addCriterion("operation_specialist =", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotEqualTo(String value) {
            addCriterion("operation_specialist <>", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistGreaterThan(String value) {
            addCriterion("operation_specialist >", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistGreaterThanOrEqualTo(String value) {
            addCriterion("operation_specialist >=", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLessThan(String value) {
            addCriterion("operation_specialist <", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLessThanOrEqualTo(String value) {
            addCriterion("operation_specialist <=", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistLike(String value) {
            addCriterion("operation_specialist like", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotLike(String value) {
            addCriterion("operation_specialist not like", value, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistIn(List<String> values) {
            addCriterion("operation_specialist in", values, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotIn(List<String> values) {
            addCriterion("operation_specialist not in", values, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistBetween(String value1, String value2) {
            addCriterion("operation_specialist between", value1, value2, "operationSpecialist");
            return (Criteria) this;
        }

        public Criteria andOperationSpecialistNotBetween(String value1, String value2) {
            addCriterion("operation_specialist not between", value1, value2, "operationSpecialist");
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