package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplyChainExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplyChainExample() {
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

        public Criteria andCreateAtIsNull() {
            addCriterion("create_at is null");
            return (Criteria) this;
        }

        public Criteria andCreateAtIsNotNull() {
            addCriterion("create_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreateAtEqualTo(Date value) {
            addCriterion("create_at =", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotEqualTo(Date value) {
            addCriterion("create_at <>", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThan(Date value) {
            addCriterion("create_at >", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtGreaterThanOrEqualTo(Date value) {
            addCriterion("create_at >=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThan(Date value) {
            addCriterion("create_at <", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtLessThanOrEqualTo(Date value) {
            addCriterion("create_at <=", value, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtIn(List<Date> values) {
            addCriterion("create_at in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotIn(List<Date> values) {
            addCriterion("create_at not in", values, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtBetween(Date value1, Date value2) {
            addCriterion("create_at between", value1, value2, "createAt");
            return (Criteria) this;
        }

        public Criteria andCreateAtNotBetween(Date value1, Date value2) {
            addCriterion("create_at not between", value1, value2, "createAt");
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

        public Criteria andCategoryIsNull() {
            addCriterion("category is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIsNotNull() {
            addCriterion("category is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryEqualTo(String value) {
            addCriterion("category =", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotEqualTo(String value) {
            addCriterion("category <>", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThan(String value) {
            addCriterion("category >", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("category >=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThan(String value) {
            addCriterion("category <", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLessThanOrEqualTo(String value) {
            addCriterion("category <=", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryLike(String value) {
            addCriterion("category like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotLike(String value) {
            addCriterion("category not like", value, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryIn(List<String> values) {
            addCriterion("category in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotIn(List<String> values) {
            addCriterion("category not in", values, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryBetween(String value1, String value2) {
            addCriterion("category between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andCategoryNotBetween(String value1, String value2) {
            addCriterion("category not between", value1, value2, "category");
            return (Criteria) this;
        }

        public Criteria andItemClassIsNull() {
            addCriterion("item_class is null");
            return (Criteria) this;
        }

        public Criteria andItemClassIsNotNull() {
            addCriterion("item_class is not null");
            return (Criteria) this;
        }

        public Criteria andItemClassEqualTo(String value) {
            addCriterion("item_class =", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassNotEqualTo(String value) {
            addCriterion("item_class <>", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassGreaterThan(String value) {
            addCriterion("item_class >", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassGreaterThanOrEqualTo(String value) {
            addCriterion("item_class >=", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassLessThan(String value) {
            addCriterion("item_class <", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassLessThanOrEqualTo(String value) {
            addCriterion("item_class <=", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassLike(String value) {
            addCriterion("item_class like", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassNotLike(String value) {
            addCriterion("item_class not like", value, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassIn(List<String> values) {
            addCriterion("item_class in", values, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassNotIn(List<String> values) {
            addCriterion("item_class not in", values, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassBetween(String value1, String value2) {
            addCriterion("item_class between", value1, value2, "itemClass");
            return (Criteria) this;
        }

        public Criteria andItemClassNotBetween(String value1, String value2) {
            addCriterion("item_class not between", value1, value2, "itemClass");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumIsNull() {
            addCriterion("plan_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumIsNotNull() {
            addCriterion("plan_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumEqualTo(Integer value) {
            addCriterion("plan_sku_num =", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumNotEqualTo(Integer value) {
            addCriterion("plan_sku_num <>", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumGreaterThan(Integer value) {
            addCriterion("plan_sku_num >", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_sku_num >=", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumLessThan(Integer value) {
            addCriterion("plan_sku_num <", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("plan_sku_num <=", value, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumIn(List<Integer> values) {
            addCriterion("plan_sku_num in", values, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumNotIn(List<Integer> values) {
            addCriterion("plan_sku_num not in", values, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("plan_sku_num between", value1, value2, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_sku_num not between", value1, value2, "planSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumIsNull() {
            addCriterion("finish_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumIsNotNull() {
            addCriterion("finish_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumEqualTo(Integer value) {
            addCriterion("finish_sku_num =", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumNotEqualTo(Integer value) {
            addCriterion("finish_sku_num <>", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumGreaterThan(Integer value) {
            addCriterion("finish_sku_num >", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_sku_num >=", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumLessThan(Integer value) {
            addCriterion("finish_sku_num <", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("finish_sku_num <=", value, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumIn(List<Integer> values) {
            addCriterion("finish_sku_num in", values, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumNotIn(List<Integer> values) {
            addCriterion("finish_sku_num not in", values, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("finish_sku_num between", value1, value2, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_sku_num not between", value1, value2, "finishSkuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumIsNull() {
            addCriterion("plan_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumIsNotNull() {
            addCriterion("plan_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumEqualTo(Integer value) {
            addCriterion("plan_spu_num =", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumNotEqualTo(Integer value) {
            addCriterion("plan_spu_num <>", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumGreaterThan(Integer value) {
            addCriterion("plan_spu_num >", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_spu_num >=", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumLessThan(Integer value) {
            addCriterion("plan_spu_num <", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("plan_spu_num <=", value, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumIn(List<Integer> values) {
            addCriterion("plan_spu_num in", values, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumNotIn(List<Integer> values) {
            addCriterion("plan_spu_num not in", values, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("plan_spu_num between", value1, value2, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_spu_num not between", value1, value2, "planSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumIsNull() {
            addCriterion("finish_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumIsNotNull() {
            addCriterion("finish_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumEqualTo(Integer value) {
            addCriterion("finish_spu_num =", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumNotEqualTo(Integer value) {
            addCriterion("finish_spu_num <>", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumGreaterThan(Integer value) {
            addCriterion("finish_spu_num >", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_spu_num >=", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumLessThan(Integer value) {
            addCriterion("finish_spu_num <", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("finish_spu_num <=", value, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumIn(List<Integer> values) {
            addCriterion("finish_spu_num in", values, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumNotIn(List<Integer> values) {
            addCriterion("finish_spu_num not in", values, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("finish_spu_num between", value1, value2, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andFinishSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_spu_num not between", value1, value2, "finishSpuNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumIsNull() {
            addCriterion("plan_suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumIsNotNull() {
            addCriterion("plan_suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumEqualTo(Integer value) {
            addCriterion("plan_suppli_num =", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumNotEqualTo(Integer value) {
            addCriterion("plan_suppli_num <>", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumGreaterThan(Integer value) {
            addCriterion("plan_suppli_num >", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_suppli_num >=", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumLessThan(Integer value) {
            addCriterion("plan_suppli_num <", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("plan_suppli_num <=", value, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumIn(List<Integer> values) {
            addCriterion("plan_suppli_num in", values, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumNotIn(List<Integer> values) {
            addCriterion("plan_suppli_num not in", values, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("plan_suppli_num between", value1, value2, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPlanSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_suppli_num not between", value1, value2, "planSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumIsNull() {
            addCriterion("finish_suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumIsNotNull() {
            addCriterion("finish_suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumEqualTo(Integer value) {
            addCriterion("finish_suppli_num =", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumNotEqualTo(Integer value) {
            addCriterion("finish_suppli_num <>", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumGreaterThan(Integer value) {
            addCriterion("finish_suppli_num >", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("finish_suppli_num >=", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumLessThan(Integer value) {
            addCriterion("finish_suppli_num <", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("finish_suppli_num <=", value, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumIn(List<Integer> values) {
            addCriterion("finish_suppli_num in", values, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumNotIn(List<Integer> values) {
            addCriterion("finish_suppli_num not in", values, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("finish_suppli_num between", value1, value2, "finishSuppliNum");
            return (Criteria) this;
        }

        public Criteria andFinishSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("finish_suppli_num not between", value1, value2, "finishSuppliNum");
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