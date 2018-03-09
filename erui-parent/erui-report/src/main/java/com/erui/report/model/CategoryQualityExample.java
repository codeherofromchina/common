package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryQualityExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CategoryQualityExample() {
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

        public Criteria andQualityControlDateIsNull() {
            addCriterion("quality_control_date is null");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateIsNotNull() {
            addCriterion("quality_control_date is not null");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateEqualTo(Date value) {
            addCriterion("quality_control_date =", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateNotEqualTo(Date value) {
            addCriterion("quality_control_date <>", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateGreaterThan(Date value) {
            addCriterion("quality_control_date >", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateGreaterThanOrEqualTo(Date value) {
            addCriterion("quality_control_date >=", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateLessThan(Date value) {
            addCriterion("quality_control_date <", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateLessThanOrEqualTo(Date value) {
            addCriterion("quality_control_date <=", value, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateIn(List<Date> values) {
            addCriterion("quality_control_date in", values, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateNotIn(List<Date> values) {
            addCriterion("quality_control_date not in", values, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateBetween(Date value1, Date value2) {
            addCriterion("quality_control_date between", value1, value2, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andQualityControlDateNotBetween(Date value1, Date value2) {
            addCriterion("quality_control_date not between", value1, value2, "qualityControlDate");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalIsNull() {
            addCriterion("inspection_total is null");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalIsNotNull() {
            addCriterion("inspection_total is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalEqualTo(Integer value) {
            addCriterion("inspection_total =", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalNotEqualTo(Integer value) {
            addCriterion("inspection_total <>", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalGreaterThan(Integer value) {
            addCriterion("inspection_total >", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspection_total >=", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalLessThan(Integer value) {
            addCriterion("inspection_total <", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalLessThanOrEqualTo(Integer value) {
            addCriterion("inspection_total <=", value, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalIn(List<Integer> values) {
            addCriterion("inspection_total in", values, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalNotIn(List<Integer> values) {
            addCriterion("inspection_total not in", values, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalBetween(Integer value1, Integer value2) {
            addCriterion("inspection_total between", value1, value2, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andInspectionTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("inspection_total not between", value1, value2, "inspectionTotal");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountIsNull() {
            addCriterion("pro_infactory_check_pass_count is null");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountIsNotNull() {
            addCriterion("pro_infactory_check_pass_count is not null");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountEqualTo(Integer value) {
            addCriterion("pro_infactory_check_pass_count =", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountNotEqualTo(Integer value) {
            addCriterion("pro_infactory_check_pass_count <>", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountGreaterThan(Integer value) {
            addCriterion("pro_infactory_check_pass_count >", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pro_infactory_check_pass_count >=", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountLessThan(Integer value) {
            addCriterion("pro_infactory_check_pass_count <", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountLessThanOrEqualTo(Integer value) {
            addCriterion("pro_infactory_check_pass_count <=", value, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountIn(List<Integer> values) {
            addCriterion("pro_infactory_check_pass_count in", values, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountNotIn(List<Integer> values) {
            addCriterion("pro_infactory_check_pass_count not in", values, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountBetween(Integer value1, Integer value2) {
            addCriterion("pro_infactory_check_pass_count between", value1, value2, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pro_infactory_check_pass_count not between", value1, value2, "proInfactoryCheckPassCount");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateIsNull() {
            addCriterion("pro_infactory_check_pass_rate is null");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateIsNotNull() {
            addCriterion("pro_infactory_check_pass_rate is not null");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateEqualTo(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate =", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateNotEqualTo(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate <>", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateGreaterThan(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate >", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate >=", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateLessThan(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate <", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pro_infactory_check_pass_rate <=", value, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateIn(List<BigDecimal> values) {
            addCriterion("pro_infactory_check_pass_rate in", values, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateNotIn(List<BigDecimal> values) {
            addCriterion("pro_infactory_check_pass_rate not in", values, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pro_infactory_check_pass_rate between", value1, value2, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProInfactoryCheckPassRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pro_infactory_check_pass_rate not between", value1, value2, "proInfactoryCheckPassRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalIsNull() {
            addCriterion("pro_outfactory_total is null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalIsNotNull() {
            addCriterion("pro_outfactory_total is not null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalEqualTo(Integer value) {
            addCriterion("pro_outfactory_total =", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalNotEqualTo(Integer value) {
            addCriterion("pro_outfactory_total <>", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalGreaterThan(Integer value) {
            addCriterion("pro_outfactory_total >", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("pro_outfactory_total >=", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalLessThan(Integer value) {
            addCriterion("pro_outfactory_total <", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalLessThanOrEqualTo(Integer value) {
            addCriterion("pro_outfactory_total <=", value, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalIn(List<Integer> values) {
            addCriterion("pro_outfactory_total in", values, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalNotIn(List<Integer> values) {
            addCriterion("pro_outfactory_total not in", values, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalBetween(Integer value1, Integer value2) {
            addCriterion("pro_outfactory_total between", value1, value2, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("pro_outfactory_total not between", value1, value2, "proOutfactoryTotal");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountIsNull() {
            addCriterion("pro_outfactory_check_count is null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountIsNotNull() {
            addCriterion("pro_outfactory_check_count is not null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountEqualTo(Integer value) {
            addCriterion("pro_outfactory_check_count =", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountNotEqualTo(Integer value) {
            addCriterion("pro_outfactory_check_count <>", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountGreaterThan(Integer value) {
            addCriterion("pro_outfactory_check_count >", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pro_outfactory_check_count >=", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountLessThan(Integer value) {
            addCriterion("pro_outfactory_check_count <", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountLessThanOrEqualTo(Integer value) {
            addCriterion("pro_outfactory_check_count <=", value, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountIn(List<Integer> values) {
            addCriterion("pro_outfactory_check_count in", values, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountNotIn(List<Integer> values) {
            addCriterion("pro_outfactory_check_count not in", values, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountBetween(Integer value1, Integer value2) {
            addCriterion("pro_outfactory_check_count between", value1, value2, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pro_outfactory_check_count not between", value1, value2, "proOutfactoryCheckCount");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateIsNull() {
            addCriterion("pro_outfactory_check_rate is null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateIsNotNull() {
            addCriterion("pro_outfactory_check_rate is not null");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateEqualTo(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate =", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateNotEqualTo(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate <>", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateGreaterThan(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate >", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate >=", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateLessThan(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate <", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("pro_outfactory_check_rate <=", value, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateIn(List<BigDecimal> values) {
            addCriterion("pro_outfactory_check_rate in", values, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateNotIn(List<BigDecimal> values) {
            addCriterion("pro_outfactory_check_rate not in", values, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pro_outfactory_check_rate between", value1, value2, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andProOutfactoryCheckRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("pro_outfactory_check_rate not between", value1, value2, "proOutfactoryCheckRate");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalIsNull() {
            addCriterion("assignments_total is null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalIsNotNull() {
            addCriterion("assignments_total is not null");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalEqualTo(Integer value) {
            addCriterion("assignments_total =", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalNotEqualTo(Integer value) {
            addCriterion("assignments_total <>", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalGreaterThan(Integer value) {
            addCriterion("assignments_total >", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("assignments_total >=", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalLessThan(Integer value) {
            addCriterion("assignments_total <", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalLessThanOrEqualTo(Integer value) {
            addCriterion("assignments_total <=", value, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalIn(List<Integer> values) {
            addCriterion("assignments_total in", values, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalNotIn(List<Integer> values) {
            addCriterion("assignments_total not in", values, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalBetween(Integer value1, Integer value2) {
            addCriterion("assignments_total between", value1, value2, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andAssignmentsTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("assignments_total not between", value1, value2, "assignmentsTotal");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountIsNull() {
            addCriterion("products_qualified_count is null");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountIsNotNull() {
            addCriterion("products_qualified_count is not null");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountEqualTo(Integer value) {
            addCriterion("products_qualified_count =", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountNotEqualTo(Integer value) {
            addCriterion("products_qualified_count <>", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountGreaterThan(Integer value) {
            addCriterion("products_qualified_count >", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("products_qualified_count >=", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountLessThan(Integer value) {
            addCriterion("products_qualified_count <", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountLessThanOrEqualTo(Integer value) {
            addCriterion("products_qualified_count <=", value, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountIn(List<Integer> values) {
            addCriterion("products_qualified_count in", values, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountNotIn(List<Integer> values) {
            addCriterion("products_qualified_count not in", values, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountBetween(Integer value1, Integer value2) {
            addCriterion("products_qualified_count between", value1, value2, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsQualifiedCountNotBetween(Integer value1, Integer value2) {
            addCriterion("products_qualified_count not between", value1, value2, "productsQualifiedCount");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateIsNull() {
            addCriterion("products_assignments_qualified_rate is null");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateIsNotNull() {
            addCriterion("products_assignments_qualified_rate is not null");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateEqualTo(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate =", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateNotEqualTo(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate <>", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateGreaterThan(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate >", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate >=", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateLessThan(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate <", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("products_assignments_qualified_rate <=", value, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateIn(List<BigDecimal> values) {
            addCriterion("products_assignments_qualified_rate in", values, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateNotIn(List<BigDecimal> values) {
            addCriterion("products_assignments_qualified_rate not in", values, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("products_assignments_qualified_rate between", value1, value2, "productsAssignmentsQualifiedRate");
            return (Criteria) this;
        }

        public Criteria andProductsAssignmentsQualifiedRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("products_assignments_qualified_rate not between", value1, value2, "productsAssignmentsQualifiedRate");
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