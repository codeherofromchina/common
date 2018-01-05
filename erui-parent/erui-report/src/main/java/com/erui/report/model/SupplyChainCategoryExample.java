package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplyChainCategoryExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplyChainCategoryExample() {
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

        public Criteria andSpuNumIsNull() {
            addCriterion("spu_num is null");
            return (Criteria) this;
        }

        public Criteria andSpuNumIsNotNull() {
            addCriterion("spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andSpuNumEqualTo(Integer value) {
            addCriterion("spu_num =", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumNotEqualTo(Integer value) {
            addCriterion("spu_num <>", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumGreaterThan(Integer value) {
            addCriterion("spu_num >", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("spu_num >=", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumLessThan(Integer value) {
            addCriterion("spu_num <", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("spu_num <=", value, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumIn(List<Integer> values) {
            addCriterion("spu_num in", values, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumNotIn(List<Integer> values) {
            addCriterion("spu_num not in", values, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("spu_num between", value1, value2, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("spu_num not between", value1, value2, "spuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumIsNull() {
            addCriterion("sku_num is null");
            return (Criteria) this;
        }

        public Criteria andSkuNumIsNotNull() {
            addCriterion("sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andSkuNumEqualTo(Integer value) {
            addCriterion("sku_num =", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotEqualTo(Integer value) {
            addCriterion("sku_num <>", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumGreaterThan(Integer value) {
            addCriterion("sku_num >", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("sku_num >=", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumLessThan(Integer value) {
            addCriterion("sku_num <", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("sku_num <=", value, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumIn(List<Integer> values) {
            addCriterion("sku_num in", values, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotIn(List<Integer> values) {
            addCriterion("sku_num not in", values, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("sku_num between", value1, value2, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("sku_num not between", value1, value2, "skuNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumIsNull() {
            addCriterion("suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andSuppliNumIsNotNull() {
            addCriterion("suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliNumEqualTo(Integer value) {
            addCriterion("suppli_num =", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumNotEqualTo(Integer value) {
            addCriterion("suppli_num <>", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumGreaterThan(Integer value) {
            addCriterion("suppli_num >", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("suppli_num >=", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumLessThan(Integer value) {
            addCriterion("suppli_num <", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("suppli_num <=", value, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumIn(List<Integer> values) {
            addCriterion("suppli_num in", values, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumNotIn(List<Integer> values) {
            addCriterion("suppli_num not in", values, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("suppli_num between", value1, value2, "suppliNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("suppli_num not between", value1, value2, "suppliNum");
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