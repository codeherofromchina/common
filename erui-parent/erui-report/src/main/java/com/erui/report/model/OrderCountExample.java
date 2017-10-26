package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderCountExample() {
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

        public Criteria andOrderSerialNumIsNull() {
            addCriterion("order_serial_num is null");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumIsNotNull() {
            addCriterion("order_serial_num is not null");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumEqualTo(String value) {
            addCriterion("order_serial_num =", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumNotEqualTo(String value) {
            addCriterion("order_serial_num <>", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumGreaterThan(String value) {
            addCriterion("order_serial_num >", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumGreaterThanOrEqualTo(String value) {
            addCriterion("order_serial_num >=", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumLessThan(String value) {
            addCriterion("order_serial_num <", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumLessThanOrEqualTo(String value) {
            addCriterion("order_serial_num <=", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumLike(String value) {
            addCriterion("order_serial_num like", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumNotLike(String value) {
            addCriterion("order_serial_num not like", value, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumIn(List<String> values) {
            addCriterion("order_serial_num in", values, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumNotIn(List<String> values) {
            addCriterion("order_serial_num not in", values, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumBetween(String value1, String value2) {
            addCriterion("order_serial_num between", value1, value2, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andOrderSerialNumNotBetween(String value1, String value2) {
            addCriterion("order_serial_num not between", value1, value2, "orderSerialNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumIsNull() {
            addCriterion("pro_notice_num is null");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumIsNotNull() {
            addCriterion("pro_notice_num is not null");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumEqualTo(String value) {
            addCriterion("pro_notice_num =", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumNotEqualTo(String value) {
            addCriterion("pro_notice_num <>", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumGreaterThan(String value) {
            addCriterion("pro_notice_num >", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumGreaterThanOrEqualTo(String value) {
            addCriterion("pro_notice_num >=", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumLessThan(String value) {
            addCriterion("pro_notice_num <", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumLessThanOrEqualTo(String value) {
            addCriterion("pro_notice_num <=", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumLike(String value) {
            addCriterion("pro_notice_num like", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumNotLike(String value) {
            addCriterion("pro_notice_num not like", value, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumIn(List<String> values) {
            addCriterion("pro_notice_num in", values, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumNotIn(List<String> values) {
            addCriterion("pro_notice_num not in", values, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumBetween(String value1, String value2) {
            addCriterion("pro_notice_num between", value1, value2, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andProNoticeNumNotBetween(String value1, String value2) {
            addCriterion("pro_notice_num not between", value1, value2, "proNoticeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNull() {
            addCriterion("execute_num is null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNotNull() {
            addCriterion("execute_num is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumEqualTo(String value) {
            addCriterion("execute_num =", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotEqualTo(String value) {
            addCriterion("execute_num <>", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThan(String value) {
            addCriterion("execute_num >", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThanOrEqualTo(String value) {
            addCriterion("execute_num >=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThan(String value) {
            addCriterion("execute_num <", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThanOrEqualTo(String value) {
            addCriterion("execute_num <=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLike(String value) {
            addCriterion("execute_num like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotLike(String value) {
            addCriterion("execute_num not like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIn(List<String> values) {
            addCriterion("execute_num in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotIn(List<String> values) {
            addCriterion("execute_num not in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumBetween(String value1, String value2) {
            addCriterion("execute_num between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotBetween(String value1, String value2) {
            addCriterion("execute_num not between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andPoNumIsNull() {
            addCriterion("po_num is null");
            return (Criteria) this;
        }

        public Criteria andPoNumIsNotNull() {
            addCriterion("po_num is not null");
            return (Criteria) this;
        }

        public Criteria andPoNumEqualTo(String value) {
            addCriterion("po_num =", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumNotEqualTo(String value) {
            addCriterion("po_num <>", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumGreaterThan(String value) {
            addCriterion("po_num >", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumGreaterThanOrEqualTo(String value) {
            addCriterion("po_num >=", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumLessThan(String value) {
            addCriterion("po_num <", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumLessThanOrEqualTo(String value) {
            addCriterion("po_num <=", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumLike(String value) {
            addCriterion("po_num like", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumNotLike(String value) {
            addCriterion("po_num not like", value, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumIn(List<String> values) {
            addCriterion("po_num in", values, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumNotIn(List<String> values) {
            addCriterion("po_num not in", values, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumBetween(String value1, String value2) {
            addCriterion("po_num between", value1, value2, "poNum");
            return (Criteria) this;
        }

        public Criteria andPoNumNotBetween(String value1, String value2) {
            addCriterion("po_num not between", value1, value2, "poNum");
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

        public Criteria andExeCompanyIsNull() {
            addCriterion("exe_company is null");
            return (Criteria) this;
        }

        public Criteria andExeCompanyIsNotNull() {
            addCriterion("exe_company is not null");
            return (Criteria) this;
        }

        public Criteria andExeCompanyEqualTo(String value) {
            addCriterion("exe_company =", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyNotEqualTo(String value) {
            addCriterion("exe_company <>", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyGreaterThan(String value) {
            addCriterion("exe_company >", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyGreaterThanOrEqualTo(String value) {
            addCriterion("exe_company >=", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyLessThan(String value) {
            addCriterion("exe_company <", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyLessThanOrEqualTo(String value) {
            addCriterion("exe_company <=", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyLike(String value) {
            addCriterion("exe_company like", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyNotLike(String value) {
            addCriterion("exe_company not like", value, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyIn(List<String> values) {
            addCriterion("exe_company in", values, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyNotIn(List<String> values) {
            addCriterion("exe_company not in", values, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyBetween(String value1, String value2) {
            addCriterion("exe_company between", value1, value2, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andExeCompanyNotBetween(String value1, String value2) {
            addCriterion("exe_company not between", value1, value2, "exeCompany");
            return (Criteria) this;
        }

        public Criteria andOrderAreaIsNull() {
            addCriterion("order_area is null");
            return (Criteria) this;
        }

        public Criteria andOrderAreaIsNotNull() {
            addCriterion("order_area is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAreaEqualTo(String value) {
            addCriterion("order_area =", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaNotEqualTo(String value) {
            addCriterion("order_area <>", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaGreaterThan(String value) {
            addCriterion("order_area >", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaGreaterThanOrEqualTo(String value) {
            addCriterion("order_area >=", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaLessThan(String value) {
            addCriterion("order_area <", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaLessThanOrEqualTo(String value) {
            addCriterion("order_area <=", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaLike(String value) {
            addCriterion("order_area like", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaNotLike(String value) {
            addCriterion("order_area not like", value, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaIn(List<String> values) {
            addCriterion("order_area in", values, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaNotIn(List<String> values) {
            addCriterion("order_area not in", values, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaBetween(String value1, String value2) {
            addCriterion("order_area between", value1, value2, "orderArea");
            return (Criteria) this;
        }

        public Criteria andOrderAreaNotBetween(String value1, String value2) {
            addCriterion("order_area not between", value1, value2, "orderArea");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIsNull() {
            addCriterion("cust_description is null");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIsNotNull() {
            addCriterion("cust_description is not null");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionEqualTo(String value) {
            addCriterion("cust_description =", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotEqualTo(String value) {
            addCriterion("cust_description <>", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionGreaterThan(String value) {
            addCriterion("cust_description >", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("cust_description >=", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLessThan(String value) {
            addCriterion("cust_description <", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLessThanOrEqualTo(String value) {
            addCriterion("cust_description <=", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLike(String value) {
            addCriterion("cust_description like", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotLike(String value) {
            addCriterion("cust_description not like", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIn(List<String> values) {
            addCriterion("cust_description in", values, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotIn(List<String> values) {
            addCriterion("cust_description not in", values, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionBetween(String value1, String value2) {
            addCriterion("cust_description between", value1, value2, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotBetween(String value1, String value2) {
            addCriterion("cust_description not between", value1, value2, "custDescription");
            return (Criteria) this;
        }

        public Criteria andProNameIsNull() {
            addCriterion("pro_name is null");
            return (Criteria) this;
        }

        public Criteria andProNameIsNotNull() {
            addCriterion("pro_name is not null");
            return (Criteria) this;
        }

        public Criteria andProNameEqualTo(String value) {
            addCriterion("pro_name =", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotEqualTo(String value) {
            addCriterion("pro_name <>", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThan(String value) {
            addCriterion("pro_name >", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThanOrEqualTo(String value) {
            addCriterion("pro_name >=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThan(String value) {
            addCriterion("pro_name <", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThanOrEqualTo(String value) {
            addCriterion("pro_name <=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLike(String value) {
            addCriterion("pro_name like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotLike(String value) {
            addCriterion("pro_name not like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameIn(List<String> values) {
            addCriterion("pro_name in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotIn(List<String> values) {
            addCriterion("pro_name not in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameBetween(String value1, String value2) {
            addCriterion("pro_name between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotBetween(String value1, String value2) {
            addCriterion("pro_name not between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameIsNull() {
            addCriterion("english_name is null");
            return (Criteria) this;
        }

        public Criteria andEnglishNameIsNotNull() {
            addCriterion("english_name is not null");
            return (Criteria) this;
        }

        public Criteria andEnglishNameEqualTo(String value) {
            addCriterion("english_name =", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameNotEqualTo(String value) {
            addCriterion("english_name <>", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameGreaterThan(String value) {
            addCriterion("english_name >", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameGreaterThanOrEqualTo(String value) {
            addCriterion("english_name >=", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameLessThan(String value) {
            addCriterion("english_name <", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameLessThanOrEqualTo(String value) {
            addCriterion("english_name <=", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameLike(String value) {
            addCriterion("english_name like", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameNotLike(String value) {
            addCriterion("english_name not like", value, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameIn(List<String> values) {
            addCriterion("english_name in", values, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameNotIn(List<String> values) {
            addCriterion("english_name not in", values, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameBetween(String value1, String value2) {
            addCriterion("english_name between", value1, value2, "englishName");
            return (Criteria) this;
        }

        public Criteria andEnglishNameNotBetween(String value1, String value2) {
            addCriterion("english_name not between", value1, value2, "englishName");
            return (Criteria) this;
        }

        public Criteria andSpecificationIsNull() {
            addCriterion("specification is null");
            return (Criteria) this;
        }

        public Criteria andSpecificationIsNotNull() {
            addCriterion("specification is not null");
            return (Criteria) this;
        }

        public Criteria andSpecificationEqualTo(String value) {
            addCriterion("specification =", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotEqualTo(String value) {
            addCriterion("specification <>", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationGreaterThan(String value) {
            addCriterion("specification >", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationGreaterThanOrEqualTo(String value) {
            addCriterion("specification >=", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLessThan(String value) {
            addCriterion("specification <", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLessThanOrEqualTo(String value) {
            addCriterion("specification <=", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLike(String value) {
            addCriterion("specification like", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotLike(String value) {
            addCriterion("specification not like", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationIn(List<String> values) {
            addCriterion("specification in", values, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotIn(List<String> values) {
            addCriterion("specification not in", values, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationBetween(String value1, String value2) {
            addCriterion("specification between", value1, value2, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotBetween(String value1, String value2) {
            addCriterion("specification not between", value1, value2, "specification");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIsNull() {
            addCriterion("is_oil_gas is null");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIsNotNull() {
            addCriterion("is_oil_gas is not null");
            return (Criteria) this;
        }

        public Criteria andIsOilGasEqualTo(String value) {
            addCriterion("is_oil_gas =", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotEqualTo(String value) {
            addCriterion("is_oil_gas <>", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasGreaterThan(String value) {
            addCriterion("is_oil_gas >", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasGreaterThanOrEqualTo(String value) {
            addCriterion("is_oil_gas >=", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLessThan(String value) {
            addCriterion("is_oil_gas <", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLessThanOrEqualTo(String value) {
            addCriterion("is_oil_gas <=", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLike(String value) {
            addCriterion("is_oil_gas like", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotLike(String value) {
            addCriterion("is_oil_gas not like", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIn(List<String> values) {
            addCriterion("is_oil_gas in", values, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotIn(List<String> values) {
            addCriterion("is_oil_gas not in", values, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasBetween(String value1, String value2) {
            addCriterion("is_oil_gas between", value1, value2, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotBetween(String value1, String value2) {
            addCriterion("is_oil_gas not between", value1, value2, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIsNull() {
            addCriterion("plat_pro_category is null");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIsNotNull() {
            addCriterion("plat_pro_category is not null");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryEqualTo(String value) {
            addCriterion("plat_pro_category =", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotEqualTo(String value) {
            addCriterion("plat_pro_category <>", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryGreaterThan(String value) {
            addCriterion("plat_pro_category >", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("plat_pro_category >=", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLessThan(String value) {
            addCriterion("plat_pro_category <", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLessThanOrEqualTo(String value) {
            addCriterion("plat_pro_category <=", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLike(String value) {
            addCriterion("plat_pro_category like", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotLike(String value) {
            addCriterion("plat_pro_category not like", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIn(List<String> values) {
            addCriterion("plat_pro_category in", values, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotIn(List<String> values) {
            addCriterion("plat_pro_category not in", values, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryBetween(String value1, String value2) {
            addCriterion("plat_pro_category between", value1, value2, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotBetween(String value1, String value2) {
            addCriterion("plat_pro_category not between", value1, value2, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryIsNull() {
            addCriterion("pro_category is null");
            return (Criteria) this;
        }

        public Criteria andProCategoryIsNotNull() {
            addCriterion("pro_category is not null");
            return (Criteria) this;
        }

        public Criteria andProCategoryEqualTo(String value) {
            addCriterion("pro_category =", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotEqualTo(String value) {
            addCriterion("pro_category <>", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryGreaterThan(String value) {
            addCriterion("pro_category >", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("pro_category >=", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLessThan(String value) {
            addCriterion("pro_category <", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLessThanOrEqualTo(String value) {
            addCriterion("pro_category <=", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLike(String value) {
            addCriterion("pro_category like", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotLike(String value) {
            addCriterion("pro_category not like", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryIn(List<String> values) {
            addCriterion("pro_category in", values, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotIn(List<String> values) {
            addCriterion("pro_category not in", values, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryBetween(String value1, String value2) {
            addCriterion("pro_category between", value1, value2, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotBetween(String value1, String value2) {
            addCriterion("pro_category not between", value1, value2, "proCategory");
            return (Criteria) this;
        }

        public Criteria andOrderCountIsNull() {
            addCriterion("order_count is null");
            return (Criteria) this;
        }

        public Criteria andOrderCountIsNotNull() {
            addCriterion("order_count is not null");
            return (Criteria) this;
        }

        public Criteria andOrderCountEqualTo(Integer value) {
            addCriterion("order_count =", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotEqualTo(Integer value) {
            addCriterion("order_count <>", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountGreaterThan(Integer value) {
            addCriterion("order_count >", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_count >=", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountLessThan(Integer value) {
            addCriterion("order_count <", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountLessThanOrEqualTo(Integer value) {
            addCriterion("order_count <=", value, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountIn(List<Integer> values) {
            addCriterion("order_count in", values, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotIn(List<Integer> values) {
            addCriterion("order_count not in", values, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountBetween(Integer value1, Integer value2) {
            addCriterion("order_count between", value1, value2, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderCountNotBetween(Integer value1, Integer value2) {
            addCriterion("order_count not between", value1, value2, "orderCount");
            return (Criteria) this;
        }

        public Criteria andOrderUnitIsNull() {
            addCriterion("order_unit is null");
            return (Criteria) this;
        }

        public Criteria andOrderUnitIsNotNull() {
            addCriterion("order_unit is not null");
            return (Criteria) this;
        }

        public Criteria andOrderUnitEqualTo(String value) {
            addCriterion("order_unit =", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitNotEqualTo(String value) {
            addCriterion("order_unit <>", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitGreaterThan(String value) {
            addCriterion("order_unit >", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitGreaterThanOrEqualTo(String value) {
            addCriterion("order_unit >=", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitLessThan(String value) {
            addCriterion("order_unit <", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitLessThanOrEqualTo(String value) {
            addCriterion("order_unit <=", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitLike(String value) {
            addCriterion("order_unit like", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitNotLike(String value) {
            addCriterion("order_unit not like", value, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitIn(List<String> values) {
            addCriterion("order_unit in", values, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitNotIn(List<String> values) {
            addCriterion("order_unit not in", values, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitBetween(String value1, String value2) {
            addCriterion("order_unit between", value1, value2, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andOrderUnitNotBetween(String value1, String value2) {
            addCriterion("order_unit not between", value1, value2, "orderUnit");
            return (Criteria) this;
        }

        public Criteria andSaleNumIsNull() {
            addCriterion("sale_num is null");
            return (Criteria) this;
        }

        public Criteria andSaleNumIsNotNull() {
            addCriterion("sale_num is not null");
            return (Criteria) this;
        }

        public Criteria andSaleNumEqualTo(String value) {
            addCriterion("sale_num =", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumNotEqualTo(String value) {
            addCriterion("sale_num <>", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumGreaterThan(String value) {
            addCriterion("sale_num >", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumGreaterThanOrEqualTo(String value) {
            addCriterion("sale_num >=", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumLessThan(String value) {
            addCriterion("sale_num <", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumLessThanOrEqualTo(String value) {
            addCriterion("sale_num <=", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumLike(String value) {
            addCriterion("sale_num like", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumNotLike(String value) {
            addCriterion("sale_num not like", value, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumIn(List<String> values) {
            addCriterion("sale_num in", values, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumNotIn(List<String> values) {
            addCriterion("sale_num not in", values, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumBetween(String value1, String value2) {
            addCriterion("sale_num between", value1, value2, "saleNum");
            return (Criteria) this;
        }

        public Criteria andSaleNumNotBetween(String value1, String value2) {
            addCriterion("sale_num not between", value1, value2, "saleNum");
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

        public Criteria andProjectAccountIsNull() {
            addCriterion("project_account is null");
            return (Criteria) this;
        }

        public Criteria andProjectAccountIsNotNull() {
            addCriterion("project_account is not null");
            return (Criteria) this;
        }

        public Criteria andProjectAccountEqualTo(BigDecimal value) {
            addCriterion("project_account =", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountNotEqualTo(BigDecimal value) {
            addCriterion("project_account <>", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountGreaterThan(BigDecimal value) {
            addCriterion("project_account >", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("project_account >=", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountLessThan(BigDecimal value) {
            addCriterion("project_account <", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("project_account <=", value, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountIn(List<BigDecimal> values) {
            addCriterion("project_account in", values, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountNotIn(List<BigDecimal> values) {
            addCriterion("project_account not in", values, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("project_account between", value1, value2, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andProjectAccountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("project_account not between", value1, value2, "projectAccount");
            return (Criteria) this;
        }

        public Criteria andPreProfitIsNull() {
            addCriterion("pre_profit is null");
            return (Criteria) this;
        }

        public Criteria andPreProfitIsNotNull() {
            addCriterion("pre_profit is not null");
            return (Criteria) this;
        }

        public Criteria andPreProfitEqualTo(String value) {
            addCriterion("pre_profit =", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitNotEqualTo(String value) {
            addCriterion("pre_profit <>", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitGreaterThan(String value) {
            addCriterion("pre_profit >", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitGreaterThanOrEqualTo(String value) {
            addCriterion("pre_profit >=", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitLessThan(String value) {
            addCriterion("pre_profit <", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitLessThanOrEqualTo(String value) {
            addCriterion("pre_profit <=", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitLike(String value) {
            addCriterion("pre_profit like", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitNotLike(String value) {
            addCriterion("pre_profit not like", value, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitIn(List<String> values) {
            addCriterion("pre_profit in", values, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitNotIn(List<String> values) {
            addCriterion("pre_profit not in", values, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitBetween(String value1, String value2) {
            addCriterion("pre_profit between", value1, value2, "preProfit");
            return (Criteria) this;
        }

        public Criteria andPreProfitNotBetween(String value1, String value2) {
            addCriterion("pre_profit not between", value1, value2, "preProfit");
            return (Criteria) this;
        }

        public Criteria andBackFormIsNull() {
            addCriterion("back_form is null");
            return (Criteria) this;
        }

        public Criteria andBackFormIsNotNull() {
            addCriterion("back_form is not null");
            return (Criteria) this;
        }

        public Criteria andBackFormEqualTo(String value) {
            addCriterion("back_form =", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormNotEqualTo(String value) {
            addCriterion("back_form <>", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormGreaterThan(String value) {
            addCriterion("back_form >", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormGreaterThanOrEqualTo(String value) {
            addCriterion("back_form >=", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormLessThan(String value) {
            addCriterion("back_form <", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormLessThanOrEqualTo(String value) {
            addCriterion("back_form <=", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormLike(String value) {
            addCriterion("back_form like", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormNotLike(String value) {
            addCriterion("back_form not like", value, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormIn(List<String> values) {
            addCriterion("back_form in", values, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormNotIn(List<String> values) {
            addCriterion("back_form not in", values, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormBetween(String value1, String value2) {
            addCriterion("back_form between", value1, value2, "backForm");
            return (Criteria) this;
        }

        public Criteria andBackFormNotBetween(String value1, String value2) {
            addCriterion("back_form not between", value1, value2, "backForm");
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

        public Criteria andBackAmountIsNull() {
            addCriterion("back_amount is null");
            return (Criteria) this;
        }

        public Criteria andBackAmountIsNotNull() {
            addCriterion("back_amount is not null");
            return (Criteria) this;
        }

        public Criteria andBackAmountEqualTo(BigDecimal value) {
            addCriterion("back_amount =", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountNotEqualTo(BigDecimal value) {
            addCriterion("back_amount <>", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountGreaterThan(BigDecimal value) {
            addCriterion("back_amount >", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("back_amount >=", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountLessThan(BigDecimal value) {
            addCriterion("back_amount <", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("back_amount <=", value, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountIn(List<BigDecimal> values) {
            addCriterion("back_amount in", values, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountNotIn(List<BigDecimal> values) {
            addCriterion("back_amount not in", values, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("back_amount between", value1, value2, "backAmount");
            return (Criteria) this;
        }

        public Criteria andBackAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("back_amount not between", value1, value2, "backAmount");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionIsNull() {
            addCriterion("credit_extension is null");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionIsNotNull() {
            addCriterion("credit_extension is not null");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionEqualTo(String value) {
            addCriterion("credit_extension =", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionNotEqualTo(String value) {
            addCriterion("credit_extension <>", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionGreaterThan(String value) {
            addCriterion("credit_extension >", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionGreaterThanOrEqualTo(String value) {
            addCriterion("credit_extension >=", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionLessThan(String value) {
            addCriterion("credit_extension <", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionLessThanOrEqualTo(String value) {
            addCriterion("credit_extension <=", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionLike(String value) {
            addCriterion("credit_extension like", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionNotLike(String value) {
            addCriterion("credit_extension not like", value, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionIn(List<String> values) {
            addCriterion("credit_extension in", values, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionNotIn(List<String> values) {
            addCriterion("credit_extension not in", values, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionBetween(String value1, String value2) {
            addCriterion("credit_extension between", value1, value2, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andCreditExtensionNotBetween(String value1, String value2) {
            addCriterion("credit_extension not between", value1, value2, "creditExtension");
            return (Criteria) this;
        }

        public Criteria andProjectStartIsNull() {
            addCriterion("project_start is null");
            return (Criteria) this;
        }

        public Criteria andProjectStartIsNotNull() {
            addCriterion("project_start is not null");
            return (Criteria) this;
        }

        public Criteria andProjectStartEqualTo(Date value) {
            addCriterion("project_start =", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartNotEqualTo(Date value) {
            addCriterion("project_start <>", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartGreaterThan(Date value) {
            addCriterion("project_start >", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartGreaterThanOrEqualTo(Date value) {
            addCriterion("project_start >=", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartLessThan(Date value) {
            addCriterion("project_start <", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartLessThanOrEqualTo(Date value) {
            addCriterion("project_start <=", value, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartIn(List<Date> values) {
            addCriterion("project_start in", values, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartNotIn(List<Date> values) {
            addCriterion("project_start not in", values, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartBetween(Date value1, Date value2) {
            addCriterion("project_start between", value1, value2, "projectStart");
            return (Criteria) this;
        }

        public Criteria andProjectStartNotBetween(Date value1, Date value2) {
            addCriterion("project_start not between", value1, value2, "projectStart");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateIsNull() {
            addCriterion("exe_promise_date is null");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateIsNotNull() {
            addCriterion("exe_promise_date is not null");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateEqualTo(Date value) {
            addCriterion("exe_promise_date =", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateNotEqualTo(Date value) {
            addCriterion("exe_promise_date <>", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateGreaterThan(Date value) {
            addCriterion("exe_promise_date >", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateGreaterThanOrEqualTo(Date value) {
            addCriterion("exe_promise_date >=", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateLessThan(Date value) {
            addCriterion("exe_promise_date <", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateLessThanOrEqualTo(Date value) {
            addCriterion("exe_promise_date <=", value, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateIn(List<Date> values) {
            addCriterion("exe_promise_date in", values, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateNotIn(List<Date> values) {
            addCriterion("exe_promise_date not in", values, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateBetween(Date value1, Date value2) {
            addCriterion("exe_promise_date between", value1, value2, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseDateNotBetween(Date value1, Date value2) {
            addCriterion("exe_promise_date not between", value1, value2, "exePromiseDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateIsNull() {
            addCriterion("exe_promise_update_date is null");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateIsNotNull() {
            addCriterion("exe_promise_update_date is not null");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateEqualTo(Date value) {
            addCriterion("exe_promise_update_date =", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateNotEqualTo(Date value) {
            addCriterion("exe_promise_update_date <>", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateGreaterThan(Date value) {
            addCriterion("exe_promise_update_date >", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateGreaterThanOrEqualTo(Date value) {
            addCriterion("exe_promise_update_date >=", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateLessThan(Date value) {
            addCriterion("exe_promise_update_date <", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateLessThanOrEqualTo(Date value) {
            addCriterion("exe_promise_update_date <=", value, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateIn(List<Date> values) {
            addCriterion("exe_promise_update_date in", values, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateNotIn(List<Date> values) {
            addCriterion("exe_promise_update_date not in", values, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateBetween(Date value1, Date value2) {
            addCriterion("exe_promise_update_date between", value1, value2, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andExePromiseUpdateDateNotBetween(Date value1, Date value2) {
            addCriterion("exe_promise_update_date not between", value1, value2, "exePromiseUpdateDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateIsNull() {
            addCriterion("require_purchase_get_date is null");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateIsNotNull() {
            addCriterion("require_purchase_get_date is not null");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateEqualTo(Date value) {
            addCriterion("require_purchase_get_date =", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateNotEqualTo(Date value) {
            addCriterion("require_purchase_get_date <>", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateGreaterThan(Date value) {
            addCriterion("require_purchase_get_date >", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateGreaterThanOrEqualTo(Date value) {
            addCriterion("require_purchase_get_date >=", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateLessThan(Date value) {
            addCriterion("require_purchase_get_date <", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateLessThanOrEqualTo(Date value) {
            addCriterion("require_purchase_get_date <=", value, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateIn(List<Date> values) {
            addCriterion("require_purchase_get_date in", values, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateNotIn(List<Date> values) {
            addCriterion("require_purchase_get_date not in", values, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateBetween(Date value1, Date value2) {
            addCriterion("require_purchase_get_date between", value1, value2, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andRequirePurchaseGetDateNotBetween(Date value1, Date value2) {
            addCriterion("require_purchase_get_date not between", value1, value2, "requirePurchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andMarketManagerIsNull() {
            addCriterion("market_manager is null");
            return (Criteria) this;
        }

        public Criteria andMarketManagerIsNotNull() {
            addCriterion("market_manager is not null");
            return (Criteria) this;
        }

        public Criteria andMarketManagerEqualTo(String value) {
            addCriterion("market_manager =", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerNotEqualTo(String value) {
            addCriterion("market_manager <>", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerGreaterThan(String value) {
            addCriterion("market_manager >", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerGreaterThanOrEqualTo(String value) {
            addCriterion("market_manager >=", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerLessThan(String value) {
            addCriterion("market_manager <", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerLessThanOrEqualTo(String value) {
            addCriterion("market_manager <=", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerLike(String value) {
            addCriterion("market_manager like", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerNotLike(String value) {
            addCriterion("market_manager not like", value, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerIn(List<String> values) {
            addCriterion("market_manager in", values, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerNotIn(List<String> values) {
            addCriterion("market_manager not in", values, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerBetween(String value1, String value2) {
            addCriterion("market_manager between", value1, value2, "marketManager");
            return (Criteria) this;
        }

        public Criteria andMarketManagerNotBetween(String value1, String value2) {
            addCriterion("market_manager not between", value1, value2, "marketManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIsNull() {
            addCriterion("business_manager is null");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIsNotNull() {
            addCriterion("business_manager is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerEqualTo(String value) {
            addCriterion("business_manager =", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotEqualTo(String value) {
            addCriterion("business_manager <>", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerGreaterThan(String value) {
            addCriterion("business_manager >", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerGreaterThanOrEqualTo(String value) {
            addCriterion("business_manager >=", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLessThan(String value) {
            addCriterion("business_manager <", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLessThanOrEqualTo(String value) {
            addCriterion("business_manager <=", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerLike(String value) {
            addCriterion("business_manager like", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotLike(String value) {
            addCriterion("business_manager not like", value, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerIn(List<String> values) {
            addCriterion("business_manager in", values, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotIn(List<String> values) {
            addCriterion("business_manager not in", values, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerBetween(String value1, String value2) {
            addCriterion("business_manager between", value1, value2, "businessManager");
            return (Criteria) this;
        }

        public Criteria andBusinessManagerNotBetween(String value1, String value2) {
            addCriterion("business_manager not between", value1, value2, "businessManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerIsNull() {
            addCriterion("purchase_manager is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerIsNotNull() {
            addCriterion("purchase_manager is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerEqualTo(String value) {
            addCriterion("purchase_manager =", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerNotEqualTo(String value) {
            addCriterion("purchase_manager <>", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerGreaterThan(String value) {
            addCriterion("purchase_manager >", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_manager >=", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerLessThan(String value) {
            addCriterion("purchase_manager <", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerLessThanOrEqualTo(String value) {
            addCriterion("purchase_manager <=", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerLike(String value) {
            addCriterion("purchase_manager like", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerNotLike(String value) {
            addCriterion("purchase_manager not like", value, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerIn(List<String> values) {
            addCriterion("purchase_manager in", values, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerNotIn(List<String> values) {
            addCriterion("purchase_manager not in", values, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerBetween(String value1, String value2) {
            addCriterion("purchase_manager between", value1, value2, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andPurchaseManagerNotBetween(String value1, String value2) {
            addCriterion("purchase_manager not between", value1, value2, "purchaseManager");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNull() {
            addCriterion("supplier is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIsNotNull() {
            addCriterion("supplier is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierEqualTo(String value) {
            addCriterion("supplier =", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotEqualTo(String value) {
            addCriterion("supplier <>", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThan(String value) {
            addCriterion("supplier >", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierGreaterThanOrEqualTo(String value) {
            addCriterion("supplier >=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThan(String value) {
            addCriterion("supplier <", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLessThanOrEqualTo(String value) {
            addCriterion("supplier <=", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierLike(String value) {
            addCriterion("supplier like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotLike(String value) {
            addCriterion("supplier not like", value, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierIn(List<String> values) {
            addCriterion("supplier in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotIn(List<String> values) {
            addCriterion("supplier not in", values, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierBetween(String value1, String value2) {
            addCriterion("supplier between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andSupplierNotBetween(String value1, String value2) {
            addCriterion("supplier not between", value1, value2, "supplier");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuIsNull() {
            addCriterion("purchase_contract_nu is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuIsNotNull() {
            addCriterion("purchase_contract_nu is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuEqualTo(String value) {
            addCriterion("purchase_contract_nu =", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuNotEqualTo(String value) {
            addCriterion("purchase_contract_nu <>", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuGreaterThan(String value) {
            addCriterion("purchase_contract_nu >", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_contract_nu >=", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuLessThan(String value) {
            addCriterion("purchase_contract_nu <", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuLessThanOrEqualTo(String value) {
            addCriterion("purchase_contract_nu <=", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuLike(String value) {
            addCriterion("purchase_contract_nu like", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuNotLike(String value) {
            addCriterion("purchase_contract_nu not like", value, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuIn(List<String> values) {
            addCriterion("purchase_contract_nu in", values, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuNotIn(List<String> values) {
            addCriterion("purchase_contract_nu not in", values, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuBetween(String value1, String value2) {
            addCriterion("purchase_contract_nu between", value1, value2, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractNuNotBetween(String value1, String value2) {
            addCriterion("purchase_contract_nu not between", value1, value2, "purchaseContractNu");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountIsNull() {
            addCriterion("pre_quotes_account is null");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountIsNotNull() {
            addCriterion("pre_quotes_account is not null");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountEqualTo(String value) {
            addCriterion("pre_quotes_account =", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountNotEqualTo(String value) {
            addCriterion("pre_quotes_account <>", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountGreaterThan(String value) {
            addCriterion("pre_quotes_account >", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountGreaterThanOrEqualTo(String value) {
            addCriterion("pre_quotes_account >=", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountLessThan(String value) {
            addCriterion("pre_quotes_account <", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountLessThanOrEqualTo(String value) {
            addCriterion("pre_quotes_account <=", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountLike(String value) {
            addCriterion("pre_quotes_account like", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountNotLike(String value) {
            addCriterion("pre_quotes_account not like", value, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountIn(List<String> values) {
            addCriterion("pre_quotes_account in", values, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountNotIn(List<String> values) {
            addCriterion("pre_quotes_account not in", values, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountBetween(String value1, String value2) {
            addCriterion("pre_quotes_account between", value1, value2, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPreQuotesAccountNotBetween(String value1, String value2) {
            addCriterion("pre_quotes_account not between", value1, value2, "preQuotesAccount");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyIsNull() {
            addCriterion("purchase_account_cny is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyIsNotNull() {
            addCriterion("purchase_account_cny is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyEqualTo(String value) {
            addCriterion("purchase_account_cny =", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyNotEqualTo(String value) {
            addCriterion("purchase_account_cny <>", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyGreaterThan(String value) {
            addCriterion("purchase_account_cny >", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_account_cny >=", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyLessThan(String value) {
            addCriterion("purchase_account_cny <", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyLessThanOrEqualTo(String value) {
            addCriterion("purchase_account_cny <=", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyLike(String value) {
            addCriterion("purchase_account_cny like", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyNotLike(String value) {
            addCriterion("purchase_account_cny not like", value, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyIn(List<String> values) {
            addCriterion("purchase_account_cny in", values, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyNotIn(List<String> values) {
            addCriterion("purchase_account_cny not in", values, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyBetween(String value1, String value2) {
            addCriterion("purchase_account_cny between", value1, value2, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountCnyNotBetween(String value1, String value2) {
            addCriterion("purchase_account_cny not between", value1, value2, "purchaseAccountCny");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdIsNull() {
            addCriterion("purchase_account_usd is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdIsNotNull() {
            addCriterion("purchase_account_usd is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdEqualTo(String value) {
            addCriterion("purchase_account_usd =", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdNotEqualTo(String value) {
            addCriterion("purchase_account_usd <>", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdGreaterThan(String value) {
            addCriterion("purchase_account_usd >", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_account_usd >=", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdLessThan(String value) {
            addCriterion("purchase_account_usd <", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdLessThanOrEqualTo(String value) {
            addCriterion("purchase_account_usd <=", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdLike(String value) {
            addCriterion("purchase_account_usd like", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdNotLike(String value) {
            addCriterion("purchase_account_usd not like", value, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdIn(List<String> values) {
            addCriterion("purchase_account_usd in", values, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdNotIn(List<String> values) {
            addCriterion("purchase_account_usd not in", values, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdBetween(String value1, String value2) {
            addCriterion("purchase_account_usd between", value1, value2, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseAccountUsdNotBetween(String value1, String value2) {
            addCriterion("purchase_account_usd not between", value1, value2, "purchaseAccountUsd");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateIsNull() {
            addCriterion("purchase_contract_date is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateIsNotNull() {
            addCriterion("purchase_contract_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateEqualTo(Date value) {
            addCriterion("purchase_contract_date =", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateNotEqualTo(Date value) {
            addCriterion("purchase_contract_date <>", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateGreaterThan(Date value) {
            addCriterion("purchase_contract_date >", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateGreaterThanOrEqualTo(Date value) {
            addCriterion("purchase_contract_date >=", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateLessThan(Date value) {
            addCriterion("purchase_contract_date <", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateLessThanOrEqualTo(Date value) {
            addCriterion("purchase_contract_date <=", value, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateIn(List<Date> values) {
            addCriterion("purchase_contract_date in", values, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateNotIn(List<Date> values) {
            addCriterion("purchase_contract_date not in", values, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateBetween(Date value1, Date value2) {
            addCriterion("purchase_contract_date between", value1, value2, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDateNotBetween(Date value1, Date value2) {
            addCriterion("purchase_contract_date not between", value1, value2, "purchaseContractDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateIsNull() {
            addCriterion("purchase_contract_delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateIsNotNull() {
            addCriterion("purchase_contract_delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateEqualTo(Date value) {
            addCriterion("purchase_contract_delivery_date =", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateNotEqualTo(Date value) {
            addCriterion("purchase_contract_delivery_date <>", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateGreaterThan(Date value) {
            addCriterion("purchase_contract_delivery_date >", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("purchase_contract_delivery_date >=", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateLessThan(Date value) {
            addCriterion("purchase_contract_delivery_date <", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("purchase_contract_delivery_date <=", value, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateIn(List<Date> values) {
            addCriterion("purchase_contract_delivery_date in", values, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateNotIn(List<Date> values) {
            addCriterion("purchase_contract_delivery_date not in", values, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("purchase_contract_delivery_date between", value1, value2, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseContractDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("purchase_contract_delivery_date not between", value1, value2, "purchaseContractDeliveryDate");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeIsNull() {
            addCriterion("purchase_payment_mode is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeIsNotNull() {
            addCriterion("purchase_payment_mode is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeEqualTo(String value) {
            addCriterion("purchase_payment_mode =", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeNotEqualTo(String value) {
            addCriterion("purchase_payment_mode <>", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeGreaterThan(String value) {
            addCriterion("purchase_payment_mode >", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_payment_mode >=", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeLessThan(String value) {
            addCriterion("purchase_payment_mode <", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeLessThanOrEqualTo(String value) {
            addCriterion("purchase_payment_mode <=", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeLike(String value) {
            addCriterion("purchase_payment_mode like", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeNotLike(String value) {
            addCriterion("purchase_payment_mode not like", value, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeIn(List<String> values) {
            addCriterion("purchase_payment_mode in", values, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeNotIn(List<String> values) {
            addCriterion("purchase_payment_mode not in", values, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeBetween(String value1, String value2) {
            addCriterion("purchase_payment_mode between", value1, value2, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andPurchasePaymentModeNotBetween(String value1, String value2) {
            addCriterion("purchase_payment_mode not between", value1, value2, "purchasePaymentMode");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateIsNull() {
            addCriterion("to_factory_payment_date is null");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateIsNotNull() {
            addCriterion("to_factory_payment_date is not null");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateEqualTo(Date value) {
            addCriterion("to_factory_payment_date =", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateNotEqualTo(Date value) {
            addCriterion("to_factory_payment_date <>", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateGreaterThan(Date value) {
            addCriterion("to_factory_payment_date >", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateGreaterThanOrEqualTo(Date value) {
            addCriterion("to_factory_payment_date >=", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateLessThan(Date value) {
            addCriterion("to_factory_payment_date <", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateLessThanOrEqualTo(Date value) {
            addCriterion("to_factory_payment_date <=", value, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateIn(List<Date> values) {
            addCriterion("to_factory_payment_date in", values, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateNotIn(List<Date> values) {
            addCriterion("to_factory_payment_date not in", values, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateBetween(Date value1, Date value2) {
            addCriterion("to_factory_payment_date between", value1, value2, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andToFactoryPaymentDateNotBetween(Date value1, Date value2) {
            addCriterion("to_factory_payment_date not between", value1, value2, "toFactoryPaymentDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateIsNull() {
            addCriterion("purchase_get_date is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateIsNotNull() {
            addCriterion("purchase_get_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateEqualTo(Date value) {
            addCriterion("purchase_get_date =", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateNotEqualTo(Date value) {
            addCriterion("purchase_get_date <>", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateGreaterThan(Date value) {
            addCriterion("purchase_get_date >", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateGreaterThanOrEqualTo(Date value) {
            addCriterion("purchase_get_date >=", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateLessThan(Date value) {
            addCriterion("purchase_get_date <", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateLessThanOrEqualTo(Date value) {
            addCriterion("purchase_get_date <=", value, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateIn(List<Date> values) {
            addCriterion("purchase_get_date in", values, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateNotIn(List<Date> values) {
            addCriterion("purchase_get_date not in", values, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateBetween(Date value1, Date value2) {
            addCriterion("purchase_get_date between", value1, value2, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseGetDateNotBetween(Date value1, Date value2) {
            addCriterion("purchase_get_date not between", value1, value2, "purchaseGetDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateIsNull() {
            addCriterion("inspection_date is null");
            return (Criteria) this;
        }

        public Criteria andInspectionDateIsNotNull() {
            addCriterion("inspection_date is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionDateEqualTo(Date value) {
            addCriterion("inspection_date =", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateNotEqualTo(Date value) {
            addCriterion("inspection_date <>", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateGreaterThan(Date value) {
            addCriterion("inspection_date >", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateGreaterThanOrEqualTo(Date value) {
            addCriterion("inspection_date >=", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateLessThan(Date value) {
            addCriterion("inspection_date <", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateLessThanOrEqualTo(Date value) {
            addCriterion("inspection_date <=", value, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateIn(List<Date> values) {
            addCriterion("inspection_date in", values, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateNotIn(List<Date> values) {
            addCriterion("inspection_date not in", values, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateBetween(Date value1, Date value2) {
            addCriterion("inspection_date between", value1, value2, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionDateNotBetween(Date value1, Date value2) {
            addCriterion("inspection_date not between", value1, value2, "inspectionDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateIsNull() {
            addCriterion("inspection_finash_date is null");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateIsNotNull() {
            addCriterion("inspection_finash_date is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateEqualTo(Date value) {
            addCriterion("inspection_finash_date =", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateNotEqualTo(Date value) {
            addCriterion("inspection_finash_date <>", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateGreaterThan(Date value) {
            addCriterion("inspection_finash_date >", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateGreaterThanOrEqualTo(Date value) {
            addCriterion("inspection_finash_date >=", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateLessThan(Date value) {
            addCriterion("inspection_finash_date <", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateLessThanOrEqualTo(Date value) {
            addCriterion("inspection_finash_date <=", value, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateIn(List<Date> values) {
            addCriterion("inspection_finash_date in", values, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateNotIn(List<Date> values) {
            addCriterion("inspection_finash_date not in", values, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateBetween(Date value1, Date value2) {
            addCriterion("inspection_finash_date between", value1, value2, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andInspectionFinashDateNotBetween(Date value1, Date value2) {
            addCriterion("inspection_finash_date not between", value1, value2, "inspectionFinashDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateIsNull() {
            addCriterion("to_storage_date is null");
            return (Criteria) this;
        }

        public Criteria andToStorageDateIsNotNull() {
            addCriterion("to_storage_date is not null");
            return (Criteria) this;
        }

        public Criteria andToStorageDateEqualTo(Date value) {
            addCriterion("to_storage_date =", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateNotEqualTo(Date value) {
            addCriterion("to_storage_date <>", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateGreaterThan(Date value) {
            addCriterion("to_storage_date >", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateGreaterThanOrEqualTo(Date value) {
            addCriterion("to_storage_date >=", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateLessThan(Date value) {
            addCriterion("to_storage_date <", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateLessThanOrEqualTo(Date value) {
            addCriterion("to_storage_date <=", value, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateIn(List<Date> values) {
            addCriterion("to_storage_date in", values, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateNotIn(List<Date> values) {
            addCriterion("to_storage_date not in", values, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateBetween(Date value1, Date value2) {
            addCriterion("to_storage_date between", value1, value2, "toStorageDate");
            return (Criteria) this;
        }

        public Criteria andToStorageDateNotBetween(Date value1, Date value2) {
            addCriterion("to_storage_date not between", value1, value2, "toStorageDate");
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
            addCriterion("booking_date =", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotEqualTo(Date value) {
            addCriterion("booking_date <>", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateGreaterThan(Date value) {
            addCriterion("booking_date >", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateGreaterThanOrEqualTo(Date value) {
            addCriterion("booking_date >=", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateLessThan(Date value) {
            addCriterion("booking_date <", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateLessThanOrEqualTo(Date value) {
            addCriterion("booking_date <=", value, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateIn(List<Date> values) {
            addCriterion("booking_date in", values, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotIn(List<Date> values) {
            addCriterion("booking_date not in", values, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateBetween(Date value1, Date value2) {
            addCriterion("booking_date between", value1, value2, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andBookingDateNotBetween(Date value1, Date value2) {
            addCriterion("booking_date not between", value1, value2, "bookingDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateIsNull() {
            addCriterion("market_request_date is null");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateIsNotNull() {
            addCriterion("market_request_date is not null");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateEqualTo(Date value) {
            addCriterion("market_request_date =", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateNotEqualTo(Date value) {
            addCriterion("market_request_date <>", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateGreaterThan(Date value) {
            addCriterion("market_request_date >", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateGreaterThanOrEqualTo(Date value) {
            addCriterion("market_request_date >=", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateLessThan(Date value) {
            addCriterion("market_request_date <", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateLessThanOrEqualTo(Date value) {
            addCriterion("market_request_date <=", value, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateIn(List<Date> values) {
            addCriterion("market_request_date in", values, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateNotIn(List<Date> values) {
            addCriterion("market_request_date not in", values, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateBetween(Date value1, Date value2) {
            addCriterion("market_request_date between", value1, value2, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andMarketRequestDateNotBetween(Date value1, Date value2) {
            addCriterion("market_request_date not between", value1, value2, "marketRequestDate");
            return (Criteria) this;
        }

        public Criteria andForwardManagerIsNull() {
            addCriterion("forward_manager is null");
            return (Criteria) this;
        }

        public Criteria andForwardManagerIsNotNull() {
            addCriterion("forward_manager is not null");
            return (Criteria) this;
        }

        public Criteria andForwardManagerEqualTo(String value) {
            addCriterion("forward_manager =", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerNotEqualTo(String value) {
            addCriterion("forward_manager <>", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerGreaterThan(String value) {
            addCriterion("forward_manager >", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerGreaterThanOrEqualTo(String value) {
            addCriterion("forward_manager >=", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerLessThan(String value) {
            addCriterion("forward_manager <", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerLessThanOrEqualTo(String value) {
            addCriterion("forward_manager <=", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerLike(String value) {
            addCriterion("forward_manager like", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerNotLike(String value) {
            addCriterion("forward_manager not like", value, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerIn(List<String> values) {
            addCriterion("forward_manager in", values, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerNotIn(List<String> values) {
            addCriterion("forward_manager not in", values, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerBetween(String value1, String value2) {
            addCriterion("forward_manager between", value1, value2, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andForwardManagerNotBetween(String value1, String value2) {
            addCriterion("forward_manager not between", value1, value2, "forwardManager");
            return (Criteria) this;
        }

        public Criteria andPackageDateIsNull() {
            addCriterion("package_date is null");
            return (Criteria) this;
        }

        public Criteria andPackageDateIsNotNull() {
            addCriterion("package_date is not null");
            return (Criteria) this;
        }

        public Criteria andPackageDateEqualTo(Date value) {
            addCriterion("package_date =", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateNotEqualTo(Date value) {
            addCriterion("package_date <>", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateGreaterThan(Date value) {
            addCriterion("package_date >", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateGreaterThanOrEqualTo(Date value) {
            addCriterion("package_date >=", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateLessThan(Date value) {
            addCriterion("package_date <", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateLessThanOrEqualTo(Date value) {
            addCriterion("package_date <=", value, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateIn(List<Date> values) {
            addCriterion("package_date in", values, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateNotIn(List<Date> values) {
            addCriterion("package_date not in", values, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateBetween(Date value1, Date value2) {
            addCriterion("package_date between", value1, value2, "packageDate");
            return (Criteria) this;
        }

        public Criteria andPackageDateNotBetween(Date value1, Date value2) {
            addCriterion("package_date not between", value1, value2, "packageDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateIsNull() {
            addCriterion("storage_out_date is null");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateIsNotNull() {
            addCriterion("storage_out_date is not null");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateEqualTo(Date value) {
            addCriterion("storage_out_date =", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateNotEqualTo(Date value) {
            addCriterion("storage_out_date <>", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateGreaterThan(Date value) {
            addCriterion("storage_out_date >", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateGreaterThanOrEqualTo(Date value) {
            addCriterion("storage_out_date >=", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateLessThan(Date value) {
            addCriterion("storage_out_date <", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateLessThanOrEqualTo(Date value) {
            addCriterion("storage_out_date <=", value, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateIn(List<Date> values) {
            addCriterion("storage_out_date in", values, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateNotIn(List<Date> values) {
            addCriterion("storage_out_date not in", values, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateBetween(Date value1, Date value2) {
            addCriterion("storage_out_date between", value1, value2, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andStorageOutDateNotBetween(Date value1, Date value2) {
            addCriterion("storage_out_date not between", value1, value2, "storageOutDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateIsNull() {
            addCriterion("ackage_notice_date is null");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateIsNotNull() {
            addCriterion("ackage_notice_date is not null");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateEqualTo(Date value) {
            addCriterion("ackage_notice_date =", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateNotEqualTo(Date value) {
            addCriterion("ackage_notice_date <>", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateGreaterThan(Date value) {
            addCriterion("ackage_notice_date >", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ackage_notice_date >=", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateLessThan(Date value) {
            addCriterion("ackage_notice_date <", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateLessThanOrEqualTo(Date value) {
            addCriterion("ackage_notice_date <=", value, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateIn(List<Date> values) {
            addCriterion("ackage_notice_date in", values, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateNotIn(List<Date> values) {
            addCriterion("ackage_notice_date not in", values, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateBetween(Date value1, Date value2) {
            addCriterion("ackage_notice_date between", value1, value2, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andAckageNoticeDateNotBetween(Date value1, Date value2) {
            addCriterion("ackage_notice_date not between", value1, value2, "ackageNoticeDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateIsNull() {
            addCriterion("ship_air_date is null");
            return (Criteria) this;
        }

        public Criteria andShipAirDateIsNotNull() {
            addCriterion("ship_air_date is not null");
            return (Criteria) this;
        }

        public Criteria andShipAirDateEqualTo(Date value) {
            addCriterion("ship_air_date =", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateNotEqualTo(Date value) {
            addCriterion("ship_air_date <>", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateGreaterThan(Date value) {
            addCriterion("ship_air_date >", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateGreaterThanOrEqualTo(Date value) {
            addCriterion("ship_air_date >=", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateLessThan(Date value) {
            addCriterion("ship_air_date <", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateLessThanOrEqualTo(Date value) {
            addCriterion("ship_air_date <=", value, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateIn(List<Date> values) {
            addCriterion("ship_air_date in", values, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateNotIn(List<Date> values) {
            addCriterion("ship_air_date not in", values, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateBetween(Date value1, Date value2) {
            addCriterion("ship_air_date between", value1, value2, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andShipAirDateNotBetween(Date value1, Date value2) {
            addCriterion("ship_air_date not between", value1, value2, "shipAirDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateIsNull() {
            addCriterion("arrive_date is null");
            return (Criteria) this;
        }

        public Criteria andArriveDateIsNotNull() {
            addCriterion("arrive_date is not null");
            return (Criteria) this;
        }

        public Criteria andArriveDateEqualTo(Date value) {
            addCriterion("arrive_date =", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotEqualTo(Date value) {
            addCriterion("arrive_date <>", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateGreaterThan(Date value) {
            addCriterion("arrive_date >", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateGreaterThanOrEqualTo(Date value) {
            addCriterion("arrive_date >=", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateLessThan(Date value) {
            addCriterion("arrive_date <", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateLessThanOrEqualTo(Date value) {
            addCriterion("arrive_date <=", value, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateIn(List<Date> values) {
            addCriterion("arrive_date in", values, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotIn(List<Date> values) {
            addCriterion("arrive_date not in", values, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateBetween(Date value1, Date value2) {
            addCriterion("arrive_date between", value1, value2, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andArriveDateNotBetween(Date value1, Date value2) {
            addCriterion("arrive_date not between", value1, value2, "arriveDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumIsNull() {
            addCriterion("logistics_num is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumIsNotNull() {
            addCriterion("logistics_num is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumEqualTo(String value) {
            addCriterion("logistics_num =", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumNotEqualTo(String value) {
            addCriterion("logistics_num <>", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumGreaterThan(String value) {
            addCriterion("logistics_num >", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_num >=", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumLessThan(String value) {
            addCriterion("logistics_num <", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumLessThanOrEqualTo(String value) {
            addCriterion("logistics_num <=", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumLike(String value) {
            addCriterion("logistics_num like", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumNotLike(String value) {
            addCriterion("logistics_num not like", value, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumIn(List<String> values) {
            addCriterion("logistics_num in", values, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumNotIn(List<String> values) {
            addCriterion("logistics_num not in", values, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumBetween(String value1, String value2) {
            addCriterion("logistics_num between", value1, value2, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andLogisticsNumNotBetween(String value1, String value2) {
            addCriterion("logistics_num not between", value1, value2, "logisticsNum");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountIsNull() {
            addCriterion("pre_logistics_amount is null");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountIsNotNull() {
            addCriterion("pre_logistics_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountEqualTo(BigDecimal value) {
            addCriterion("pre_logistics_amount =", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountNotEqualTo(BigDecimal value) {
            addCriterion("pre_logistics_amount <>", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountGreaterThan(BigDecimal value) {
            addCriterion("pre_logistics_amount >", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_logistics_amount >=", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountLessThan(BigDecimal value) {
            addCriterion("pre_logistics_amount <", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pre_logistics_amount <=", value, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountIn(List<BigDecimal> values) {
            addCriterion("pre_logistics_amount in", values, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountNotIn(List<BigDecimal> values) {
            addCriterion("pre_logistics_amount not in", values, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_logistics_amount between", value1, value2, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andPreLogisticsAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pre_logistics_amount not between", value1, value2, "preLogisticsAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountIsNull() {
            addCriterion("logistics_forward_amount is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountIsNotNull() {
            addCriterion("logistics_forward_amount is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountEqualTo(BigDecimal value) {
            addCriterion("logistics_forward_amount =", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountNotEqualTo(BigDecimal value) {
            addCriterion("logistics_forward_amount <>", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountGreaterThan(BigDecimal value) {
            addCriterion("logistics_forward_amount >", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_forward_amount >=", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountLessThan(BigDecimal value) {
            addCriterion("logistics_forward_amount <", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_forward_amount <=", value, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountIn(List<BigDecimal> values) {
            addCriterion("logistics_forward_amount in", values, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountNotIn(List<BigDecimal> values) {
            addCriterion("logistics_forward_amount not in", values, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_forward_amount between", value1, value2, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andLogisticsForwardAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_forward_amount not between", value1, value2, "logisticsForwardAmount");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNull() {
            addCriterion("finish_date is null");
            return (Criteria) this;
        }

        public Criteria andFinishDateIsNotNull() {
            addCriterion("finish_date is not null");
            return (Criteria) this;
        }

        public Criteria andFinishDateEqualTo(Date value) {
            addCriterion("finish_date =", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotEqualTo(Date value) {
            addCriterion("finish_date <>", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThan(Date value) {
            addCriterion("finish_date >", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("finish_date >=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThan(Date value) {
            addCriterion("finish_date <", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateLessThanOrEqualTo(Date value) {
            addCriterion("finish_date <=", value, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateIn(List<Date> values) {
            addCriterion("finish_date in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotIn(List<Date> values) {
            addCriterion("finish_date not in", values, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateBetween(Date value1, Date value2) {
            addCriterion("finish_date between", value1, value2, "finishDate");
            return (Criteria) this;
        }

        public Criteria andFinishDateNotBetween(Date value1, Date value2) {
            addCriterion("finish_date not between", value1, value2, "finishDate");
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

        public Criteria andPurchaseDelayDateIsNull() {
            addCriterion("purchase_delay_date is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateIsNotNull() {
            addCriterion("purchase_delay_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateEqualTo(Integer value) {
            addCriterion("purchase_delay_date =", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateNotEqualTo(Integer value) {
            addCriterion("purchase_delay_date <>", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateGreaterThan(Integer value) {
            addCriterion("purchase_delay_date >", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_delay_date >=", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateLessThan(Integer value) {
            addCriterion("purchase_delay_date <", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_delay_date <=", value, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateIn(List<Integer> values) {
            addCriterion("purchase_delay_date in", values, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateNotIn(List<Integer> values) {
            addCriterion("purchase_delay_date not in", values, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateBetween(Integer value1, Integer value2) {
            addCriterion("purchase_delay_date between", value1, value2, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andPurchaseDelayDateNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_delay_date not between", value1, value2, "purchaseDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateIsNull() {
            addCriterion("logistics_delay_date is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateIsNotNull() {
            addCriterion("logistics_delay_date is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateEqualTo(Integer value) {
            addCriterion("logistics_delay_date =", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateNotEqualTo(Integer value) {
            addCriterion("logistics_delay_date <>", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateGreaterThan(Integer value) {
            addCriterion("logistics_delay_date >", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_delay_date >=", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateLessThan(Integer value) {
            addCriterion("logistics_delay_date <", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_delay_date <=", value, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateIn(List<Integer> values) {
            addCriterion("logistics_delay_date in", values, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateNotIn(List<Integer> values) {
            addCriterion("logistics_delay_date not in", values, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateBetween(Integer value1, Integer value2) {
            addCriterion("logistics_delay_date between", value1, value2, "logisticsDelayDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDelayDateNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_delay_date not between", value1, value2, "logisticsDelayDate");
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

        public Criteria andReasonTypeIsNull() {
            addCriterion("reason_type is null");
            return (Criteria) this;
        }

        public Criteria andReasonTypeIsNotNull() {
            addCriterion("reason_type is not null");
            return (Criteria) this;
        }

        public Criteria andReasonTypeEqualTo(String value) {
            addCriterion("reason_type =", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotEqualTo(String value) {
            addCriterion("reason_type <>", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeGreaterThan(String value) {
            addCriterion("reason_type >", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeGreaterThanOrEqualTo(String value) {
            addCriterion("reason_type >=", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLessThan(String value) {
            addCriterion("reason_type <", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLessThanOrEqualTo(String value) {
            addCriterion("reason_type <=", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeLike(String value) {
            addCriterion("reason_type like", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotLike(String value) {
            addCriterion("reason_type not like", value, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeIn(List<String> values) {
            addCriterion("reason_type in", values, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotIn(List<String> values) {
            addCriterion("reason_type not in", values, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeBetween(String value1, String value2) {
            addCriterion("reason_type between", value1, value2, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonTypeNotBetween(String value1, String value2) {
            addCriterion("reason_type not between", value1, value2, "reasonType");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryIsNull() {
            addCriterion("reason_category is null");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryIsNotNull() {
            addCriterion("reason_category is not null");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryEqualTo(String value) {
            addCriterion("reason_category =", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryNotEqualTo(String value) {
            addCriterion("reason_category <>", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryGreaterThan(String value) {
            addCriterion("reason_category >", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("reason_category >=", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryLessThan(String value) {
            addCriterion("reason_category <", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryLessThanOrEqualTo(String value) {
            addCriterion("reason_category <=", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryLike(String value) {
            addCriterion("reason_category like", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryNotLike(String value) {
            addCriterion("reason_category not like", value, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryIn(List<String> values) {
            addCriterion("reason_category in", values, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryNotIn(List<String> values) {
            addCriterion("reason_category not in", values, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryBetween(String value1, String value2) {
            addCriterion("reason_category between", value1, value2, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andReasonCategoryNotBetween(String value1, String value2) {
            addCriterion("reason_category not between", value1, value2, "reasonCategory");
            return (Criteria) this;
        }

        public Criteria andRealityReasonIsNull() {
            addCriterion("reality_reason is null");
            return (Criteria) this;
        }

        public Criteria andRealityReasonIsNotNull() {
            addCriterion("reality_reason is not null");
            return (Criteria) this;
        }

        public Criteria andRealityReasonEqualTo(String value) {
            addCriterion("reality_reason =", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonNotEqualTo(String value) {
            addCriterion("reality_reason <>", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonGreaterThan(String value) {
            addCriterion("reality_reason >", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonGreaterThanOrEqualTo(String value) {
            addCriterion("reality_reason >=", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonLessThan(String value) {
            addCriterion("reality_reason <", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonLessThanOrEqualTo(String value) {
            addCriterion("reality_reason <=", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonLike(String value) {
            addCriterion("reality_reason like", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonNotLike(String value) {
            addCriterion("reality_reason not like", value, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonIn(List<String> values) {
            addCriterion("reality_reason in", values, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonNotIn(List<String> values) {
            addCriterion("reality_reason not in", values, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonBetween(String value1, String value2) {
            addCriterion("reality_reason between", value1, value2, "realityReason");
            return (Criteria) this;
        }

        public Criteria andRealityReasonNotBetween(String value1, String value2) {
            addCriterion("reality_reason not between", value1, value2, "realityReason");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIsNull() {
            addCriterion("project_progress is null");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIsNotNull() {
            addCriterion("project_progress is not null");
            return (Criteria) this;
        }

        public Criteria andProjectProgressEqualTo(String value) {
            addCriterion("project_progress =", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotEqualTo(String value) {
            addCriterion("project_progress <>", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressGreaterThan(String value) {
            addCriterion("project_progress >", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressGreaterThanOrEqualTo(String value) {
            addCriterion("project_progress >=", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLessThan(String value) {
            addCriterion("project_progress <", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLessThanOrEqualTo(String value) {
            addCriterion("project_progress <=", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressLike(String value) {
            addCriterion("project_progress like", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotLike(String value) {
            addCriterion("project_progress not like", value, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressIn(List<String> values) {
            addCriterion("project_progress in", values, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotIn(List<String> values) {
            addCriterion("project_progress not in", values, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressBetween(String value1, String value2) {
            addCriterion("project_progress between", value1, value2, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andProjectProgressNotBetween(String value1, String value2) {
            addCriterion("project_progress not between", value1, value2, "projectProgress");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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