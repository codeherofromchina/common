package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchContractSimpleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchContractSimpleExample() {
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

        public Criteria andPurchContractIdIsNull() {
            addCriterion("purch_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdIsNotNull() {
            addCriterion("purch_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdEqualTo(Integer value) {
            addCriterion("purch_contract_id =", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotEqualTo(Integer value) {
            addCriterion("purch_contract_id <>", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdGreaterThan(Integer value) {
            addCriterion("purch_contract_id >", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_contract_id >=", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdLessThan(Integer value) {
            addCriterion("purch_contract_id <", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdLessThanOrEqualTo(Integer value) {
            addCriterion("purch_contract_id <=", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdIn(List<Integer> values) {
            addCriterion("purch_contract_id in", values, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotIn(List<Integer> values) {
            addCriterion("purch_contract_id not in", values, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdBetween(Integer value1, Integer value2) {
            addCriterion("purch_contract_id between", value1, value2, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_contract_id not between", value1, value2, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andProductRequirementIsNull() {
            addCriterion("product_requirement is null");
            return (Criteria) this;
        }

        public Criteria andProductRequirementIsNotNull() {
            addCriterion("product_requirement is not null");
            return (Criteria) this;
        }

        public Criteria andProductRequirementEqualTo(String value) {
            addCriterion("product_requirement =", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementNotEqualTo(String value) {
            addCriterion("product_requirement <>", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementGreaterThan(String value) {
            addCriterion("product_requirement >", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementGreaterThanOrEqualTo(String value) {
            addCriterion("product_requirement >=", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementLessThan(String value) {
            addCriterion("product_requirement <", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementLessThanOrEqualTo(String value) {
            addCriterion("product_requirement <=", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementLike(String value) {
            addCriterion("product_requirement like", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementNotLike(String value) {
            addCriterion("product_requirement not like", value, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementIn(List<String> values) {
            addCriterion("product_requirement in", values, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementNotIn(List<String> values) {
            addCriterion("product_requirement not in", values, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementBetween(String value1, String value2) {
            addCriterion("product_requirement between", value1, value2, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andProductRequirementNotBetween(String value1, String value2) {
            addCriterion("product_requirement not between", value1, value2, "productRequirement");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIsNull() {
            addCriterion("warranty_period is null");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIsNotNull() {
            addCriterion("warranty_period is not null");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodEqualTo(String value) {
            addCriterion("warranty_period =", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotEqualTo(String value) {
            addCriterion("warranty_period <>", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodGreaterThan(String value) {
            addCriterion("warranty_period >", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("warranty_period >=", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLessThan(String value) {
            addCriterion("warranty_period <", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLessThanOrEqualTo(String value) {
            addCriterion("warranty_period <=", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLike(String value) {
            addCriterion("warranty_period like", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotLike(String value) {
            addCriterion("warranty_period not like", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIn(List<String> values) {
            addCriterion("warranty_period in", values, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotIn(List<String> values) {
            addCriterion("warranty_period not in", values, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodBetween(String value1, String value2) {
            addCriterion("warranty_period between", value1, value2, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotBetween(String value1, String value2) {
            addCriterion("warranty_period not between", value1, value2, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andShippingDateIsNull() {
            addCriterion("shipping_date is null");
            return (Criteria) this;
        }

        public Criteria andShippingDateIsNotNull() {
            addCriterion("shipping_date is not null");
            return (Criteria) this;
        }

        public Criteria andShippingDateEqualTo(Date value) {
            addCriterion("shipping_date =", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateNotEqualTo(Date value) {
            addCriterion("shipping_date <>", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateGreaterThan(Date value) {
            addCriterion("shipping_date >", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateGreaterThanOrEqualTo(Date value) {
            addCriterion("shipping_date >=", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateLessThan(Date value) {
            addCriterion("shipping_date <", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateLessThanOrEqualTo(Date value) {
            addCriterion("shipping_date <=", value, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateIn(List<Date> values) {
            addCriterion("shipping_date in", values, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateNotIn(List<Date> values) {
            addCriterion("shipping_date not in", values, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateBetween(Date value1, Date value2) {
            addCriterion("shipping_date between", value1, value2, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andShippingDateNotBetween(Date value1, Date value2) {
            addCriterion("shipping_date not between", value1, value2, "shippingDate");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationIsNull() {
            addCriterion("designated_location is null");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationIsNotNull() {
            addCriterion("designated_location is not null");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationEqualTo(String value) {
            addCriterion("designated_location =", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationNotEqualTo(String value) {
            addCriterion("designated_location <>", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationGreaterThan(String value) {
            addCriterion("designated_location >", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationGreaterThanOrEqualTo(String value) {
            addCriterion("designated_location >=", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationLessThan(String value) {
            addCriterion("designated_location <", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationLessThanOrEqualTo(String value) {
            addCriterion("designated_location <=", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationLike(String value) {
            addCriterion("designated_location like", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationNotLike(String value) {
            addCriterion("designated_location not like", value, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationIn(List<String> values) {
            addCriterion("designated_location in", values, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationNotIn(List<String> values) {
            addCriterion("designated_location not in", values, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationBetween(String value1, String value2) {
            addCriterion("designated_location between", value1, value2, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andDesignatedLocationNotBetween(String value1, String value2) {
            addCriterion("designated_location not between", value1, value2, "designatedLocation");
            return (Criteria) this;
        }

        public Criteria andCostBurdenIsNull() {
            addCriterion("cost_burden is null");
            return (Criteria) this;
        }

        public Criteria andCostBurdenIsNotNull() {
            addCriterion("cost_burden is not null");
            return (Criteria) this;
        }

        public Criteria andCostBurdenEqualTo(String value) {
            addCriterion("cost_burden =", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenNotEqualTo(String value) {
            addCriterion("cost_burden <>", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenGreaterThan(String value) {
            addCriterion("cost_burden >", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenGreaterThanOrEqualTo(String value) {
            addCriterion("cost_burden >=", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenLessThan(String value) {
            addCriterion("cost_burden <", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenLessThanOrEqualTo(String value) {
            addCriterion("cost_burden <=", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenLike(String value) {
            addCriterion("cost_burden like", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenNotLike(String value) {
            addCriterion("cost_burden not like", value, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenIn(List<String> values) {
            addCriterion("cost_burden in", values, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenNotIn(List<String> values) {
            addCriterion("cost_burden not in", values, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenBetween(String value1, String value2) {
            addCriterion("cost_burden between", value1, value2, "costBurden");
            return (Criteria) this;
        }

        public Criteria andCostBurdenNotBetween(String value1, String value2) {
            addCriterion("cost_burden not between", value1, value2, "costBurden");
            return (Criteria) this;
        }

        public Criteria andInspectionAtIsNull() {
            addCriterion("inspection_at is null");
            return (Criteria) this;
        }

        public Criteria andInspectionAtIsNotNull() {
            addCriterion("inspection_at is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionAtEqualTo(String value) {
            addCriterion("inspection_at =", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtNotEqualTo(String value) {
            addCriterion("inspection_at <>", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtGreaterThan(String value) {
            addCriterion("inspection_at >", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtGreaterThanOrEqualTo(String value) {
            addCriterion("inspection_at >=", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtLessThan(String value) {
            addCriterion("inspection_at <", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtLessThanOrEqualTo(String value) {
            addCriterion("inspection_at <=", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtLike(String value) {
            addCriterion("inspection_at like", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtNotLike(String value) {
            addCriterion("inspection_at not like", value, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtIn(List<String> values) {
            addCriterion("inspection_at in", values, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtNotIn(List<String> values) {
            addCriterion("inspection_at not in", values, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtBetween(String value1, String value2) {
            addCriterion("inspection_at between", value1, value2, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andInspectionAtNotBetween(String value1, String value2) {
            addCriterion("inspection_at not between", value1, value2, "inspectionAt");
            return (Criteria) this;
        }

        public Criteria andWithinDaysIsNull() {
            addCriterion("within_days is null");
            return (Criteria) this;
        }

        public Criteria andWithinDaysIsNotNull() {
            addCriterion("within_days is not null");
            return (Criteria) this;
        }

        public Criteria andWithinDaysEqualTo(String value) {
            addCriterion("within_days =", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysNotEqualTo(String value) {
            addCriterion("within_days <>", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysGreaterThan(String value) {
            addCriterion("within_days >", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysGreaterThanOrEqualTo(String value) {
            addCriterion("within_days >=", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysLessThan(String value) {
            addCriterion("within_days <", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysLessThanOrEqualTo(String value) {
            addCriterion("within_days <=", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysLike(String value) {
            addCriterion("within_days like", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysNotLike(String value) {
            addCriterion("within_days not like", value, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysIn(List<String> values) {
            addCriterion("within_days in", values, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysNotIn(List<String> values) {
            addCriterion("within_days not in", values, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysBetween(String value1, String value2) {
            addCriterion("within_days between", value1, value2, "withinDays");
            return (Criteria) this;
        }

        public Criteria andWithinDaysNotBetween(String value1, String value2) {
            addCriterion("within_days not between", value1, value2, "withinDays");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIsNull() {
            addCriterion("method_and_time is null");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIsNotNull() {
            addCriterion("method_and_time is not null");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeEqualTo(String value) {
            addCriterion("method_and_time =", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotEqualTo(String value) {
            addCriterion("method_and_time <>", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeGreaterThan(String value) {
            addCriterion("method_and_time >", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("method_and_time >=", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLessThan(String value) {
            addCriterion("method_and_time <", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLessThanOrEqualTo(String value) {
            addCriterion("method_and_time <=", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLike(String value) {
            addCriterion("method_and_time like", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotLike(String value) {
            addCriterion("method_and_time not like", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIn(List<String> values) {
            addCriterion("method_and_time in", values, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotIn(List<String> values) {
            addCriterion("method_and_time not in", values, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeBetween(String value1, String value2) {
            addCriterion("method_and_time between", value1, value2, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotBetween(String value1, String value2) {
            addCriterion("method_and_time not between", value1, value2, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andAgreementNameIsNull() {
            addCriterion("agreement_name is null");
            return (Criteria) this;
        }

        public Criteria andAgreementNameIsNotNull() {
            addCriterion("agreement_name is not null");
            return (Criteria) this;
        }

        public Criteria andAgreementNameEqualTo(String value) {
            addCriterion("agreement_name =", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotEqualTo(String value) {
            addCriterion("agreement_name <>", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameGreaterThan(String value) {
            addCriterion("agreement_name >", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameGreaterThanOrEqualTo(String value) {
            addCriterion("agreement_name >=", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLessThan(String value) {
            addCriterion("agreement_name <", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLessThanOrEqualTo(String value) {
            addCriterion("agreement_name <=", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameLike(String value) {
            addCriterion("agreement_name like", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotLike(String value) {
            addCriterion("agreement_name not like", value, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameIn(List<String> values) {
            addCriterion("agreement_name in", values, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotIn(List<String> values) {
            addCriterion("agreement_name not in", values, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameBetween(String value1, String value2) {
            addCriterion("agreement_name between", value1, value2, "agreementName");
            return (Criteria) this;
        }

        public Criteria andAgreementNameNotBetween(String value1, String value2) {
            addCriterion("agreement_name not between", value1, value2, "agreementName");
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