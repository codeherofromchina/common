package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SalesmanNumsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SalesmanNumsExample() {
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

        public Criteria andStartPrescriptionIsNull() {
            addCriterion("start_prescription is null");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionIsNotNull() {
            addCriterion("start_prescription is not null");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionEqualTo(Date value) {
            addCriterionForJDBCDate("start_prescription =", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionNotEqualTo(Date value) {
            addCriterionForJDBCDate("start_prescription <>", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionGreaterThan(Date value) {
            addCriterionForJDBCDate("start_prescription >", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_prescription >=", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionLessThan(Date value) {
            addCriterionForJDBCDate("start_prescription <", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("start_prescription <=", value, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionIn(List<Date> values) {
            addCriterionForJDBCDate("start_prescription in", values, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionNotIn(List<Date> values) {
            addCriterionForJDBCDate("start_prescription not in", values, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_prescription between", value1, value2, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andStartPrescriptionNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("start_prescription not between", value1, value2, "startPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionIsNull() {
            addCriterion("end_prescription is null");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionIsNotNull() {
            addCriterion("end_prescription is not null");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionEqualTo(Date value) {
            addCriterionForJDBCDate("end_prescription =", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionNotEqualTo(Date value) {
            addCriterionForJDBCDate("end_prescription <>", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionGreaterThan(Date value) {
            addCriterionForJDBCDate("end_prescription >", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_prescription >=", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionLessThan(Date value) {
            addCriterionForJDBCDate("end_prescription <", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("end_prescription <=", value, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionIn(List<Date> values) {
            addCriterionForJDBCDate("end_prescription in", values, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionNotIn(List<Date> values) {
            addCriterionForJDBCDate("end_prescription not in", values, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_prescription between", value1, value2, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andEndPrescriptionNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("end_prescription not between", value1, value2, "endPrescription");
            return (Criteria) this;
        }

        public Criteria andCountryBnIsNull() {
            addCriterion("country_bn is null");
            return (Criteria) this;
        }

        public Criteria andCountryBnIsNotNull() {
            addCriterion("country_bn is not null");
            return (Criteria) this;
        }

        public Criteria andCountryBnEqualTo(String value) {
            addCriterion("country_bn =", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnNotEqualTo(String value) {
            addCriterion("country_bn <>", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnGreaterThan(String value) {
            addCriterion("country_bn >", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnGreaterThanOrEqualTo(String value) {
            addCriterion("country_bn >=", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnLessThan(String value) {
            addCriterion("country_bn <", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnLessThanOrEqualTo(String value) {
            addCriterion("country_bn <=", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnLike(String value) {
            addCriterion("country_bn like", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnNotLike(String value) {
            addCriterion("country_bn not like", value, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnIn(List<String> values) {
            addCriterion("country_bn in", values, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnNotIn(List<String> values) {
            addCriterion("country_bn not in", values, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnBetween(String value1, String value2) {
            addCriterion("country_bn between", value1, value2, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryBnNotBetween(String value1, String value2) {
            addCriterion("country_bn not between", value1, value2, "countryBn");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNull() {
            addCriterion("country_name is null");
            return (Criteria) this;
        }

        public Criteria andCountryNameIsNotNull() {
            addCriterion("country_name is not null");
            return (Criteria) this;
        }

        public Criteria andCountryNameEqualTo(String value) {
            addCriterion("country_name =", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotEqualTo(String value) {
            addCriterion("country_name <>", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThan(String value) {
            addCriterion("country_name >", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameGreaterThanOrEqualTo(String value) {
            addCriterion("country_name >=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThan(String value) {
            addCriterion("country_name <", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLessThanOrEqualTo(String value) {
            addCriterion("country_name <=", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameLike(String value) {
            addCriterion("country_name like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotLike(String value) {
            addCriterion("country_name not like", value, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameIn(List<String> values) {
            addCriterion("country_name in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotIn(List<String> values) {
            addCriterion("country_name not in", values, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameBetween(String value1, String value2) {
            addCriterion("country_name between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andCountryNameNotBetween(String value1, String value2) {
            addCriterion("country_name not between", value1, value2, "countryName");
            return (Criteria) this;
        }

        public Criteria andAreaBnIsNull() {
            addCriterion("area_bn is null");
            return (Criteria) this;
        }

        public Criteria andAreaBnIsNotNull() {
            addCriterion("area_bn is not null");
            return (Criteria) this;
        }

        public Criteria andAreaBnEqualTo(String value) {
            addCriterion("area_bn =", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnNotEqualTo(String value) {
            addCriterion("area_bn <>", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnGreaterThan(String value) {
            addCriterion("area_bn >", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnGreaterThanOrEqualTo(String value) {
            addCriterion("area_bn >=", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnLessThan(String value) {
            addCriterion("area_bn <", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnLessThanOrEqualTo(String value) {
            addCriterion("area_bn <=", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnLike(String value) {
            addCriterion("area_bn like", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnNotLike(String value) {
            addCriterion("area_bn not like", value, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnIn(List<String> values) {
            addCriterion("area_bn in", values, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnNotIn(List<String> values) {
            addCriterion("area_bn not in", values, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnBetween(String value1, String value2) {
            addCriterion("area_bn between", value1, value2, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaBnNotBetween(String value1, String value2) {
            addCriterion("area_bn not between", value1, value2, "areaBn");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNull() {
            addCriterion("area_name is null");
            return (Criteria) this;
        }

        public Criteria andAreaNameIsNotNull() {
            addCriterion("area_name is not null");
            return (Criteria) this;
        }

        public Criteria andAreaNameEqualTo(String value) {
            addCriterion("area_name =", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotEqualTo(String value) {
            addCriterion("area_name <>", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThan(String value) {
            addCriterion("area_name >", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameGreaterThanOrEqualTo(String value) {
            addCriterion("area_name >=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThan(String value) {
            addCriterion("area_name <", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLessThanOrEqualTo(String value) {
            addCriterion("area_name <=", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameLike(String value) {
            addCriterion("area_name like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotLike(String value) {
            addCriterion("area_name not like", value, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameIn(List<String> values) {
            addCriterion("area_name in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotIn(List<String> values) {
            addCriterion("area_name not in", values, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameBetween(String value1, String value2) {
            addCriterion("area_name between", value1, value2, "areaName");
            return (Criteria) this;
        }

        public Criteria andAreaNameNotBetween(String value1, String value2) {
            addCriterion("area_name not between", value1, value2, "areaName");
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