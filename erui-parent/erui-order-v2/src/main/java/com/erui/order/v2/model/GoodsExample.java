package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class GoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public GoodsExample() {
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

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Integer value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Integer value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Integer value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Integer value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Integer value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Integer> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Integer> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Integer value1, Integer value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
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

        public Criteria andProjectIdIsNull() {
            addCriterion("project_id is null");
            return (Criteria) this;
        }

        public Criteria andProjectIdIsNotNull() {
            addCriterion("project_id is not null");
            return (Criteria) this;
        }

        public Criteria andProjectIdEqualTo(Integer value) {
            addCriterion("project_id =", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotEqualTo(Integer value) {
            addCriterion("project_id <>", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThan(Integer value) {
            addCriterion("project_id >", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("project_id >=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThan(Integer value) {
            addCriterion("project_id <", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdLessThanOrEqualTo(Integer value) {
            addCriterion("project_id <=", value, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdIn(List<Integer> values) {
            addCriterion("project_id in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotIn(List<Integer> values) {
            addCriterion("project_id not in", values, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdBetween(Integer value1, Integer value2) {
            addCriterion("project_id between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andProjectIdNotBetween(Integer value1, Integer value2) {
            addCriterion("project_id not between", value1, value2, "projectId");
            return (Criteria) this;
        }

        public Criteria andSeqIsNull() {
            addCriterion("seq is null");
            return (Criteria) this;
        }

        public Criteria andSeqIsNotNull() {
            addCriterion("seq is not null");
            return (Criteria) this;
        }

        public Criteria andSeqEqualTo(Integer value) {
            addCriterion("seq =", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotEqualTo(Integer value) {
            addCriterion("seq <>", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThan(Integer value) {
            addCriterion("seq >", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqGreaterThanOrEqualTo(Integer value) {
            addCriterion("seq >=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThan(Integer value) {
            addCriterion("seq <", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqLessThanOrEqualTo(Integer value) {
            addCriterion("seq <=", value, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqIn(List<Integer> values) {
            addCriterion("seq in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotIn(List<Integer> values) {
            addCriterion("seq not in", values, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqBetween(Integer value1, Integer value2) {
            addCriterion("seq between", value1, value2, "seq");
            return (Criteria) this;
        }

        public Criteria andSeqNotBetween(Integer value1, Integer value2) {
            addCriterion("seq not between", value1, value2, "seq");
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

        public Criteria andSkuIsNull() {
            addCriterion("sku is null");
            return (Criteria) this;
        }

        public Criteria andSkuIsNotNull() {
            addCriterion("sku is not null");
            return (Criteria) this;
        }

        public Criteria andSkuEqualTo(String value) {
            addCriterion("sku =", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotEqualTo(String value) {
            addCriterion("sku <>", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThan(String value) {
            addCriterion("sku >", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuGreaterThanOrEqualTo(String value) {
            addCriterion("sku >=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThan(String value) {
            addCriterion("sku <", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLessThanOrEqualTo(String value) {
            addCriterion("sku <=", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuLike(String value) {
            addCriterion("sku like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotLike(String value) {
            addCriterion("sku not like", value, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuIn(List<String> values) {
            addCriterion("sku in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotIn(List<String> values) {
            addCriterion("sku not in", values, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuBetween(String value1, String value2) {
            addCriterion("sku between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andSkuNotBetween(String value1, String value2) {
            addCriterion("sku not between", value1, value2, "sku");
            return (Criteria) this;
        }

        public Criteria andMeteTypeIsNull() {
            addCriterion("mete_type is null");
            return (Criteria) this;
        }

        public Criteria andMeteTypeIsNotNull() {
            addCriterion("mete_type is not null");
            return (Criteria) this;
        }

        public Criteria andMeteTypeEqualTo(String value) {
            addCriterion("mete_type =", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeNotEqualTo(String value) {
            addCriterion("mete_type <>", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeGreaterThan(String value) {
            addCriterion("mete_type >", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeGreaterThanOrEqualTo(String value) {
            addCriterion("mete_type >=", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeLessThan(String value) {
            addCriterion("mete_type <", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeLessThanOrEqualTo(String value) {
            addCriterion("mete_type <=", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeLike(String value) {
            addCriterion("mete_type like", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeNotLike(String value) {
            addCriterion("mete_type not like", value, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeIn(List<String> values) {
            addCriterion("mete_type in", values, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeNotIn(List<String> values) {
            addCriterion("mete_type not in", values, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeBetween(String value1, String value2) {
            addCriterion("mete_type between", value1, value2, "meteType");
            return (Criteria) this;
        }

        public Criteria andMeteTypeNotBetween(String value1, String value2) {
            addCriterion("mete_type not between", value1, value2, "meteType");
            return (Criteria) this;
        }

        public Criteria andProTypeIsNull() {
            addCriterion("pro_type is null");
            return (Criteria) this;
        }

        public Criteria andProTypeIsNotNull() {
            addCriterion("pro_type is not null");
            return (Criteria) this;
        }

        public Criteria andProTypeEqualTo(String value) {
            addCriterion("pro_type =", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotEqualTo(String value) {
            addCriterion("pro_type <>", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThan(String value) {
            addCriterion("pro_type >", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeGreaterThanOrEqualTo(String value) {
            addCriterion("pro_type >=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThan(String value) {
            addCriterion("pro_type <", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLessThanOrEqualTo(String value) {
            addCriterion("pro_type <=", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeLike(String value) {
            addCriterion("pro_type like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotLike(String value) {
            addCriterion("pro_type not like", value, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeIn(List<String> values) {
            addCriterion("pro_type in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotIn(List<String> values) {
            addCriterion("pro_type not in", values, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeBetween(String value1, String value2) {
            addCriterion("pro_type between", value1, value2, "proType");
            return (Criteria) this;
        }

        public Criteria andProTypeNotBetween(String value1, String value2) {
            addCriterion("pro_type not between", value1, value2, "proType");
            return (Criteria) this;
        }

        public Criteria andNameEnIsNull() {
            addCriterion("name_en is null");
            return (Criteria) this;
        }

        public Criteria andNameEnIsNotNull() {
            addCriterion("name_en is not null");
            return (Criteria) this;
        }

        public Criteria andNameEnEqualTo(String value) {
            addCriterion("name_en =", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotEqualTo(String value) {
            addCriterion("name_en <>", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThan(String value) {
            addCriterion("name_en >", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("name_en >=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThan(String value) {
            addCriterion("name_en <", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLessThanOrEqualTo(String value) {
            addCriterion("name_en <=", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnLike(String value) {
            addCriterion("name_en like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotLike(String value) {
            addCriterion("name_en not like", value, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnIn(List<String> values) {
            addCriterion("name_en in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotIn(List<String> values) {
            addCriterion("name_en not in", values, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnBetween(String value1, String value2) {
            addCriterion("name_en between", value1, value2, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameEnNotBetween(String value1, String value2) {
            addCriterion("name_en not between", value1, value2, "nameEn");
            return (Criteria) this;
        }

        public Criteria andNameZhIsNull() {
            addCriterion("name_zh is null");
            return (Criteria) this;
        }

        public Criteria andNameZhIsNotNull() {
            addCriterion("name_zh is not null");
            return (Criteria) this;
        }

        public Criteria andNameZhEqualTo(String value) {
            addCriterion("name_zh =", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhNotEqualTo(String value) {
            addCriterion("name_zh <>", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhGreaterThan(String value) {
            addCriterion("name_zh >", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhGreaterThanOrEqualTo(String value) {
            addCriterion("name_zh >=", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhLessThan(String value) {
            addCriterion("name_zh <", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhLessThanOrEqualTo(String value) {
            addCriterion("name_zh <=", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhLike(String value) {
            addCriterion("name_zh like", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhNotLike(String value) {
            addCriterion("name_zh not like", value, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhIn(List<String> values) {
            addCriterion("name_zh in", values, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhNotIn(List<String> values) {
            addCriterion("name_zh not in", values, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhBetween(String value1, String value2) {
            addCriterion("name_zh between", value1, value2, "nameZh");
            return (Criteria) this;
        }

        public Criteria andNameZhNotBetween(String value1, String value2) {
            addCriterion("name_zh not between", value1, value2, "nameZh");
            return (Criteria) this;
        }

        public Criteria andUnitIsNull() {
            addCriterion("unit is null");
            return (Criteria) this;
        }

        public Criteria andUnitIsNotNull() {
            addCriterion("unit is not null");
            return (Criteria) this;
        }

        public Criteria andUnitEqualTo(String value) {
            addCriterion("unit =", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotEqualTo(String value) {
            addCriterion("unit <>", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThan(String value) {
            addCriterion("unit >", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitGreaterThanOrEqualTo(String value) {
            addCriterion("unit >=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThan(String value) {
            addCriterion("unit <", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLessThanOrEqualTo(String value) {
            addCriterion("unit <=", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitLike(String value) {
            addCriterion("unit like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotLike(String value) {
            addCriterion("unit not like", value, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitIn(List<String> values) {
            addCriterion("unit in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotIn(List<String> values) {
            addCriterion("unit not in", values, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitBetween(String value1, String value2) {
            addCriterion("unit between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andUnitNotBetween(String value1, String value2) {
            addCriterion("unit not between", value1, value2, "unit");
            return (Criteria) this;
        }

        public Criteria andBrandIsNull() {
            addCriterion("brand is null");
            return (Criteria) this;
        }

        public Criteria andBrandIsNotNull() {
            addCriterion("brand is not null");
            return (Criteria) this;
        }

        public Criteria andBrandEqualTo(String value) {
            addCriterion("brand =", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotEqualTo(String value) {
            addCriterion("brand <>", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThan(String value) {
            addCriterion("brand >", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandGreaterThanOrEqualTo(String value) {
            addCriterion("brand >=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThan(String value) {
            addCriterion("brand <", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLessThanOrEqualTo(String value) {
            addCriterion("brand <=", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandLike(String value) {
            addCriterion("brand like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotLike(String value) {
            addCriterion("brand not like", value, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandIn(List<String> values) {
            addCriterion("brand in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotIn(List<String> values) {
            addCriterion("brand not in", values, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandBetween(String value1, String value2) {
            addCriterion("brand between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andBrandNotBetween(String value1, String value2) {
            addCriterion("brand not between", value1, value2, "brand");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumIsNull() {
            addCriterion("contract_goods_num is null");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumIsNotNull() {
            addCriterion("contract_goods_num is not null");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumEqualTo(Integer value) {
            addCriterion("contract_goods_num =", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumNotEqualTo(Integer value) {
            addCriterion("contract_goods_num <>", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumGreaterThan(Integer value) {
            addCriterion("contract_goods_num >", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("contract_goods_num >=", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumLessThan(Integer value) {
            addCriterion("contract_goods_num <", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumLessThanOrEqualTo(Integer value) {
            addCriterion("contract_goods_num <=", value, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumIn(List<Integer> values) {
            addCriterion("contract_goods_num in", values, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumNotIn(List<Integer> values) {
            addCriterion("contract_goods_num not in", values, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumBetween(Integer value1, Integer value2) {
            addCriterion("contract_goods_num between", value1, value2, "contractGoodsNum");
            return (Criteria) this;
        }

        public Criteria andContractGoodsNumNotBetween(Integer value1, Integer value2) {
            addCriterion("contract_goods_num not between", value1, value2, "contractGoodsNum");
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

        public Criteria andTechRequireIsNull() {
            addCriterion("tech_require is null");
            return (Criteria) this;
        }

        public Criteria andTechRequireIsNotNull() {
            addCriterion("tech_require is not null");
            return (Criteria) this;
        }

        public Criteria andTechRequireEqualTo(String value) {
            addCriterion("tech_require =", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireNotEqualTo(String value) {
            addCriterion("tech_require <>", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireGreaterThan(String value) {
            addCriterion("tech_require >", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireGreaterThanOrEqualTo(String value) {
            addCriterion("tech_require >=", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireLessThan(String value) {
            addCriterion("tech_require <", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireLessThanOrEqualTo(String value) {
            addCriterion("tech_require <=", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireLike(String value) {
            addCriterion("tech_require like", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireNotLike(String value) {
            addCriterion("tech_require not like", value, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireIn(List<String> values) {
            addCriterion("tech_require in", values, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireNotIn(List<String> values) {
            addCriterion("tech_require not in", values, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireBetween(String value1, String value2) {
            addCriterion("tech_require between", value1, value2, "techRequire");
            return (Criteria) this;
        }

        public Criteria andTechRequireNotBetween(String value1, String value2) {
            addCriterion("tech_require not between", value1, value2, "techRequire");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNull() {
            addCriterion("check_type is null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIsNotNull() {
            addCriterion("check_type is not null");
            return (Criteria) this;
        }

        public Criteria andCheckTypeEqualTo(String value) {
            addCriterion("check_type =", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotEqualTo(String value) {
            addCriterion("check_type <>", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThan(String value) {
            addCriterion("check_type >", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeGreaterThanOrEqualTo(String value) {
            addCriterion("check_type >=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThan(String value) {
            addCriterion("check_type <", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLessThanOrEqualTo(String value) {
            addCriterion("check_type <=", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeLike(String value) {
            addCriterion("check_type like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotLike(String value) {
            addCriterion("check_type not like", value, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeIn(List<String> values) {
            addCriterion("check_type in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotIn(List<String> values) {
            addCriterion("check_type not in", values, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeBetween(String value1, String value2) {
            addCriterion("check_type between", value1, value2, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckTypeNotBetween(String value1, String value2) {
            addCriterion("check_type not between", value1, value2, "checkType");
            return (Criteria) this;
        }

        public Criteria andCheckMethodIsNull() {
            addCriterion("check_method is null");
            return (Criteria) this;
        }

        public Criteria andCheckMethodIsNotNull() {
            addCriterion("check_method is not null");
            return (Criteria) this;
        }

        public Criteria andCheckMethodEqualTo(String value) {
            addCriterion("check_method =", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodNotEqualTo(String value) {
            addCriterion("check_method <>", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodGreaterThan(String value) {
            addCriterion("check_method >", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodGreaterThanOrEqualTo(String value) {
            addCriterion("check_method >=", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodLessThan(String value) {
            addCriterion("check_method <", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodLessThanOrEqualTo(String value) {
            addCriterion("check_method <=", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodLike(String value) {
            addCriterion("check_method like", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodNotLike(String value) {
            addCriterion("check_method not like", value, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodIn(List<String> values) {
            addCriterion("check_method in", values, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodNotIn(List<String> values) {
            addCriterion("check_method not in", values, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodBetween(String value1, String value2) {
            addCriterion("check_method between", value1, value2, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andCheckMethodNotBetween(String value1, String value2) {
            addCriterion("check_method not between", value1, value2, "checkMethod");
            return (Criteria) this;
        }

        public Criteria andSendNumIsNull() {
            addCriterion("send_num is null");
            return (Criteria) this;
        }

        public Criteria andSendNumIsNotNull() {
            addCriterion("send_num is not null");
            return (Criteria) this;
        }

        public Criteria andSendNumEqualTo(Integer value) {
            addCriterion("send_num =", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotEqualTo(Integer value) {
            addCriterion("send_num <>", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumGreaterThan(Integer value) {
            addCriterion("send_num >", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("send_num >=", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumLessThan(Integer value) {
            addCriterion("send_num <", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumLessThanOrEqualTo(Integer value) {
            addCriterion("send_num <=", value, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumIn(List<Integer> values) {
            addCriterion("send_num in", values, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotIn(List<Integer> values) {
            addCriterion("send_num not in", values, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumBetween(Integer value1, Integer value2) {
            addCriterion("send_num between", value1, value2, "sendNum");
            return (Criteria) this;
        }

        public Criteria andSendNumNotBetween(Integer value1, Integer value2) {
            addCriterion("send_num not between", value1, value2, "sendNum");
            return (Criteria) this;
        }

        public Criteria andCertificateIsNull() {
            addCriterion("certificate is null");
            return (Criteria) this;
        }

        public Criteria andCertificateIsNotNull() {
            addCriterion("certificate is not null");
            return (Criteria) this;
        }

        public Criteria andCertificateEqualTo(String value) {
            addCriterion("certificate =", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotEqualTo(String value) {
            addCriterion("certificate <>", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateGreaterThan(String value) {
            addCriterion("certificate >", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateGreaterThanOrEqualTo(String value) {
            addCriterion("certificate >=", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLessThan(String value) {
            addCriterion("certificate <", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLessThanOrEqualTo(String value) {
            addCriterion("certificate <=", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateLike(String value) {
            addCriterion("certificate like", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotLike(String value) {
            addCriterion("certificate not like", value, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateIn(List<String> values) {
            addCriterion("certificate in", values, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotIn(List<String> values) {
            addCriterion("certificate not in", values, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateBetween(String value1, String value2) {
            addCriterion("certificate between", value1, value2, "certificate");
            return (Criteria) this;
        }

        public Criteria andCertificateNotBetween(String value1, String value2) {
            addCriterion("certificate not between", value1, value2, "certificate");
            return (Criteria) this;
        }

        public Criteria andTechAuditIsNull() {
            addCriterion("tech_audit is null");
            return (Criteria) this;
        }

        public Criteria andTechAuditIsNotNull() {
            addCriterion("tech_audit is not null");
            return (Criteria) this;
        }

        public Criteria andTechAuditEqualTo(String value) {
            addCriterion("tech_audit =", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditNotEqualTo(String value) {
            addCriterion("tech_audit <>", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditGreaterThan(String value) {
            addCriterion("tech_audit >", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditGreaterThanOrEqualTo(String value) {
            addCriterion("tech_audit >=", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditLessThan(String value) {
            addCriterion("tech_audit <", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditLessThanOrEqualTo(String value) {
            addCriterion("tech_audit <=", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditLike(String value) {
            addCriterion("tech_audit like", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditNotLike(String value) {
            addCriterion("tech_audit not like", value, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditIn(List<String> values) {
            addCriterion("tech_audit in", values, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditNotIn(List<String> values) {
            addCriterion("tech_audit not in", values, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditBetween(String value1, String value2) {
            addCriterion("tech_audit between", value1, value2, "techAudit");
            return (Criteria) this;
        }

        public Criteria andTechAuditNotBetween(String value1, String value2) {
            addCriterion("tech_audit not between", value1, value2, "techAudit");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumIsNull() {
            addCriterion("purchased_num is null");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumIsNotNull() {
            addCriterion("purchased_num is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumEqualTo(Integer value) {
            addCriterion("purchased_num =", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumNotEqualTo(Integer value) {
            addCriterion("purchased_num <>", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumGreaterThan(Integer value) {
            addCriterion("purchased_num >", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchased_num >=", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumLessThan(Integer value) {
            addCriterion("purchased_num <", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumLessThanOrEqualTo(Integer value) {
            addCriterion("purchased_num <=", value, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumIn(List<Integer> values) {
            addCriterion("purchased_num in", values, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumNotIn(List<Integer> values) {
            addCriterion("purchased_num not in", values, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumBetween(Integer value1, Integer value2) {
            addCriterion("purchased_num between", value1, value2, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andPurchasedNumNotBetween(Integer value1, Integer value2) {
            addCriterion("purchased_num not between", value1, value2, "purchasedNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumIsNull() {
            addCriterion("inspect_num is null");
            return (Criteria) this;
        }

        public Criteria andInspectNumIsNotNull() {
            addCriterion("inspect_num is not null");
            return (Criteria) this;
        }

        public Criteria andInspectNumEqualTo(Integer value) {
            addCriterion("inspect_num =", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumNotEqualTo(Integer value) {
            addCriterion("inspect_num <>", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumGreaterThan(Integer value) {
            addCriterion("inspect_num >", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_num >=", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumLessThan(Integer value) {
            addCriterion("inspect_num <", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_num <=", value, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumIn(List<Integer> values) {
            addCriterion("inspect_num in", values, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumNotIn(List<Integer> values) {
            addCriterion("inspect_num not in", values, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumBetween(Integer value1, Integer value2) {
            addCriterion("inspect_num between", value1, value2, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInspectNumNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_num not between", value1, value2, "inspectNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumIsNull() {
            addCriterion("instock_num is null");
            return (Criteria) this;
        }

        public Criteria andInstockNumIsNotNull() {
            addCriterion("instock_num is not null");
            return (Criteria) this;
        }

        public Criteria andInstockNumEqualTo(Integer value) {
            addCriterion("instock_num =", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumNotEqualTo(Integer value) {
            addCriterion("instock_num <>", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumGreaterThan(Integer value) {
            addCriterion("instock_num >", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("instock_num >=", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumLessThan(Integer value) {
            addCriterion("instock_num <", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumLessThanOrEqualTo(Integer value) {
            addCriterion("instock_num <=", value, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumIn(List<Integer> values) {
            addCriterion("instock_num in", values, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumNotIn(List<Integer> values) {
            addCriterion("instock_num not in", values, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumBetween(Integer value1, Integer value2) {
            addCriterion("instock_num between", value1, value2, "instockNum");
            return (Criteria) this;
        }

        public Criteria andInstockNumNotBetween(Integer value1, Integer value2) {
            addCriterion("instock_num not between", value1, value2, "instockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumIsNull() {
            addCriterion("outstock_apply_num is null");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumIsNotNull() {
            addCriterion("outstock_apply_num is not null");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumEqualTo(Integer value) {
            addCriterion("outstock_apply_num =", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumNotEqualTo(Integer value) {
            addCriterion("outstock_apply_num <>", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumGreaterThan(Integer value) {
            addCriterion("outstock_apply_num >", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("outstock_apply_num >=", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumLessThan(Integer value) {
            addCriterion("outstock_apply_num <", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumLessThanOrEqualTo(Integer value) {
            addCriterion("outstock_apply_num <=", value, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumIn(List<Integer> values) {
            addCriterion("outstock_apply_num in", values, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumNotIn(List<Integer> values) {
            addCriterion("outstock_apply_num not in", values, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumBetween(Integer value1, Integer value2) {
            addCriterion("outstock_apply_num between", value1, value2, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockApplyNumNotBetween(Integer value1, Integer value2) {
            addCriterion("outstock_apply_num not between", value1, value2, "outstockApplyNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumIsNull() {
            addCriterion("outstock_num is null");
            return (Criteria) this;
        }

        public Criteria andOutstockNumIsNotNull() {
            addCriterion("outstock_num is not null");
            return (Criteria) this;
        }

        public Criteria andOutstockNumEqualTo(Integer value) {
            addCriterion("outstock_num =", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumNotEqualTo(Integer value) {
            addCriterion("outstock_num <>", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumGreaterThan(Integer value) {
            addCriterion("outstock_num >", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("outstock_num >=", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumLessThan(Integer value) {
            addCriterion("outstock_num <", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumLessThanOrEqualTo(Integer value) {
            addCriterion("outstock_num <=", value, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumIn(List<Integer> values) {
            addCriterion("outstock_num in", values, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumNotIn(List<Integer> values) {
            addCriterion("outstock_num not in", values, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumBetween(Integer value1, Integer value2) {
            addCriterion("outstock_num between", value1, value2, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andOutstockNumNotBetween(Integer value1, Integer value2) {
            addCriterion("outstock_num not between", value1, value2, "outstockNum");
            return (Criteria) this;
        }

        public Criteria andExchangedIsNull() {
            addCriterion("exchanged is null");
            return (Criteria) this;
        }

        public Criteria andExchangedIsNotNull() {
            addCriterion("exchanged is not null");
            return (Criteria) this;
        }

        public Criteria andExchangedEqualTo(Boolean value) {
            addCriterion("exchanged =", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedNotEqualTo(Boolean value) {
            addCriterion("exchanged <>", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedGreaterThan(Boolean value) {
            addCriterion("exchanged >", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedGreaterThanOrEqualTo(Boolean value) {
            addCriterion("exchanged >=", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedLessThan(Boolean value) {
            addCriterion("exchanged <", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedLessThanOrEqualTo(Boolean value) {
            addCriterion("exchanged <=", value, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedIn(List<Boolean> values) {
            addCriterion("exchanged in", values, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedNotIn(List<Boolean> values) {
            addCriterion("exchanged not in", values, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedBetween(Boolean value1, Boolean value2) {
            addCriterion("exchanged between", value1, value2, "exchanged");
            return (Criteria) this;
        }

        public Criteria andExchangedNotBetween(Boolean value1, Boolean value2) {
            addCriterion("exchanged not between", value1, value2, "exchanged");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumIsNull() {
            addCriterion("pre_purchased_num is null");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumIsNotNull() {
            addCriterion("pre_purchased_num is not null");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumEqualTo(Integer value) {
            addCriterion("pre_purchased_num =", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumNotEqualTo(Integer value) {
            addCriterion("pre_purchased_num <>", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumGreaterThan(Integer value) {
            addCriterion("pre_purchased_num >", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pre_purchased_num >=", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumLessThan(Integer value) {
            addCriterion("pre_purchased_num <", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumLessThanOrEqualTo(Integer value) {
            addCriterion("pre_purchased_num <=", value, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumIn(List<Integer> values) {
            addCriterion("pre_purchased_num in", values, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumNotIn(List<Integer> values) {
            addCriterion("pre_purchased_num not in", values, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumBetween(Integer value1, Integer value2) {
            addCriterion("pre_purchased_num between", value1, value2, "prePurchasedNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchasedNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pre_purchased_num not between", value1, value2, "prePurchasedNum");
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

        public Criteria andPurChgDateIsNull() {
            addCriterion("pur_chg_date is null");
            return (Criteria) this;
        }

        public Criteria andPurChgDateIsNotNull() {
            addCriterion("pur_chg_date is not null");
            return (Criteria) this;
        }

        public Criteria andPurChgDateEqualTo(Date value) {
            addCriterion("pur_chg_date =", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotEqualTo(Date value) {
            addCriterion("pur_chg_date <>", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateGreaterThan(Date value) {
            addCriterion("pur_chg_date >", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateGreaterThanOrEqualTo(Date value) {
            addCriterion("pur_chg_date >=", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateLessThan(Date value) {
            addCriterion("pur_chg_date <", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateLessThanOrEqualTo(Date value) {
            addCriterion("pur_chg_date <=", value, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateIn(List<Date> values) {
            addCriterion("pur_chg_date in", values, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotIn(List<Date> values) {
            addCriterion("pur_chg_date not in", values, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateBetween(Date value1, Date value2) {
            addCriterion("pur_chg_date between", value1, value2, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andPurChgDateNotBetween(Date value1, Date value2) {
            addCriterion("pur_chg_date not between", value1, value2, "purChgDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateIsNull() {
            addCriterion("signing_date is null");
            return (Criteria) this;
        }

        public Criteria andSigningDateIsNotNull() {
            addCriterion("signing_date is not null");
            return (Criteria) this;
        }

        public Criteria andSigningDateEqualTo(Date value) {
            addCriterion("signing_date =", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotEqualTo(Date value) {
            addCriterion("signing_date <>", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateGreaterThan(Date value) {
            addCriterion("signing_date >", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateGreaterThanOrEqualTo(Date value) {
            addCriterion("signing_date >=", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateLessThan(Date value) {
            addCriterion("signing_date <", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateLessThanOrEqualTo(Date value) {
            addCriterion("signing_date <=", value, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateIn(List<Date> values) {
            addCriterion("signing_date in", values, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotIn(List<Date> values) {
            addCriterion("signing_date not in", values, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateBetween(Date value1, Date value2) {
            addCriterion("signing_date between", value1, value2, "signingDate");
            return (Criteria) this;
        }

        public Criteria andSigningDateNotBetween(Date value1, Date value2) {
            addCriterion("signing_date not between", value1, value2, "signingDate");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNull() {
            addCriterion("agent_id is null");
            return (Criteria) this;
        }

        public Criteria andAgentIdIsNotNull() {
            addCriterion("agent_id is not null");
            return (Criteria) this;
        }

        public Criteria andAgentIdEqualTo(Integer value) {
            addCriterion("agent_id =", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotEqualTo(Integer value) {
            addCriterion("agent_id <>", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThan(Integer value) {
            addCriterion("agent_id >", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("agent_id >=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThan(Integer value) {
            addCriterion("agent_id <", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdLessThanOrEqualTo(Integer value) {
            addCriterion("agent_id <=", value, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdIn(List<Integer> values) {
            addCriterion("agent_id in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotIn(List<Integer> values) {
            addCriterion("agent_id not in", values, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdBetween(Integer value1, Integer value2) {
            addCriterion("agent_id between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andAgentIdNotBetween(Integer value1, Integer value2) {
            addCriterion("agent_id not between", value1, value2, "agentId");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNull() {
            addCriterion("arrival_date is null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIsNotNull() {
            addCriterion("arrival_date is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalDateEqualTo(Date value) {
            addCriterion("arrival_date =", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotEqualTo(Date value) {
            addCriterion("arrival_date <>", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThan(Date value) {
            addCriterion("arrival_date >", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateGreaterThanOrEqualTo(Date value) {
            addCriterion("arrival_date >=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThan(Date value) {
            addCriterion("arrival_date <", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateLessThanOrEqualTo(Date value) {
            addCriterion("arrival_date <=", value, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateIn(List<Date> values) {
            addCriterion("arrival_date in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotIn(List<Date> values) {
            addCriterion("arrival_date not in", values, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateBetween(Date value1, Date value2) {
            addCriterion("arrival_date between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andArrivalDateNotBetween(Date value1, Date value2) {
            addCriterion("arrival_date not between", value1, value2, "arrivalDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNull() {
            addCriterion("inspect_date is null");
            return (Criteria) this;
        }

        public Criteria andInspectDateIsNotNull() {
            addCriterion("inspect_date is not null");
            return (Criteria) this;
        }

        public Criteria andInspectDateEqualTo(Date value) {
            addCriterion("inspect_date =", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotEqualTo(Date value) {
            addCriterion("inspect_date <>", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThan(Date value) {
            addCriterion("inspect_date >", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateGreaterThanOrEqualTo(Date value) {
            addCriterion("inspect_date >=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThan(Date value) {
            addCriterion("inspect_date <", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateLessThanOrEqualTo(Date value) {
            addCriterion("inspect_date <=", value, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateIn(List<Date> values) {
            addCriterion("inspect_date in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotIn(List<Date> values) {
            addCriterion("inspect_date not in", values, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateBetween(Date value1, Date value2) {
            addCriterion("inspect_date between", value1, value2, "inspectDate");
            return (Criteria) this;
        }

        public Criteria andInspectDateNotBetween(Date value1, Date value2) {
            addCriterion("inspect_date not between", value1, value2, "inspectDate");
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

        public Criteria andCheckDoneDateIsNull() {
            addCriterion("check_done_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateIsNotNull() {
            addCriterion("check_done_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateEqualTo(Date value) {
            addCriterion("check_done_date =", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateNotEqualTo(Date value) {
            addCriterion("check_done_date <>", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateGreaterThan(Date value) {
            addCriterion("check_done_date >", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateGreaterThanOrEqualTo(Date value) {
            addCriterion("check_done_date >=", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateLessThan(Date value) {
            addCriterion("check_done_date <", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateLessThanOrEqualTo(Date value) {
            addCriterion("check_done_date <=", value, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateIn(List<Date> values) {
            addCriterion("check_done_date in", values, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateNotIn(List<Date> values) {
            addCriterion("check_done_date not in", values, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateBetween(Date value1, Date value2) {
            addCriterion("check_done_date between", value1, value2, "checkDoneDate");
            return (Criteria) this;
        }

        public Criteria andCheckDoneDateNotBetween(Date value1, Date value2) {
            addCriterion("check_done_date not between", value1, value2, "checkDoneDate");
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
            addCriterion("instock_date =", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotEqualTo(Date value) {
            addCriterion("instock_date <>", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateGreaterThan(Date value) {
            addCriterion("instock_date >", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateGreaterThanOrEqualTo(Date value) {
            addCriterion("instock_date >=", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateLessThan(Date value) {
            addCriterion("instock_date <", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateLessThanOrEqualTo(Date value) {
            addCriterion("instock_date <=", value, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateIn(List<Date> values) {
            addCriterion("instock_date in", values, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotIn(List<Date> values) {
            addCriterion("instock_date not in", values, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateBetween(Date value1, Date value2) {
            addCriterion("instock_date between", value1, value2, "instockDate");
            return (Criteria) this;
        }

        public Criteria andInstockDateNotBetween(Date value1, Date value2) {
            addCriterion("instock_date not between", value1, value2, "instockDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNull() {
            addCriterion("release_date is null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNotNull() {
            addCriterion("release_date is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateEqualTo(Date value) {
            addCriterion("release_date =", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotEqualTo(Date value) {
            addCriterion("release_date <>", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThan(Date value) {
            addCriterion("release_date >", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThanOrEqualTo(Date value) {
            addCriterion("release_date >=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThan(Date value) {
            addCriterion("release_date <", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThanOrEqualTo(Date value) {
            addCriterion("release_date <=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIn(List<Date> values) {
            addCriterion("release_date in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotIn(List<Date> values) {
            addCriterion("release_date not in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateBetween(Date value1, Date value2) {
            addCriterion("release_date between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotBetween(Date value1, Date value2) {
            addCriterion("release_date not between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("send_date is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("send_date is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterion("send_date =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterion("send_date <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterion("send_date >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterion("send_date >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterion("send_date <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterion("send_date <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterion("send_date in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterion("send_date not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterion("send_date between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterion("send_date not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNull() {
            addCriterion("sender_id is null");
            return (Criteria) this;
        }

        public Criteria andSenderIdIsNotNull() {
            addCriterion("sender_id is not null");
            return (Criteria) this;
        }

        public Criteria andSenderIdEqualTo(Integer value) {
            addCriterion("sender_id =", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotEqualTo(Integer value) {
            addCriterion("sender_id <>", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThan(Integer value) {
            addCriterion("sender_id >", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender_id >=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThan(Integer value) {
            addCriterion("sender_id <", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdLessThanOrEqualTo(Integer value) {
            addCriterion("sender_id <=", value, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdIn(List<Integer> values) {
            addCriterion("sender_id in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotIn(List<Integer> values) {
            addCriterion("sender_id not in", values, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdBetween(Integer value1, Integer value2) {
            addCriterion("sender_id between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andSenderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("sender_id not between", value1, value2, "senderId");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIsNull() {
            addCriterion("booking_time is null");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIsNotNull() {
            addCriterion("booking_time is not null");
            return (Criteria) this;
        }

        public Criteria andBookingTimeEqualTo(Date value) {
            addCriterion("booking_time =", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotEqualTo(Date value) {
            addCriterion("booking_time <>", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeGreaterThan(Date value) {
            addCriterion("booking_time >", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("booking_time >=", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeLessThan(Date value) {
            addCriterion("booking_time <", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeLessThanOrEqualTo(Date value) {
            addCriterion("booking_time <=", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIn(List<Date> values) {
            addCriterion("booking_time in", values, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotIn(List<Date> values) {
            addCriterion("booking_time not in", values, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeBetween(Date value1, Date value2) {
            addCriterion("booking_time between", value1, value2, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotBetween(Date value1, Date value2) {
            addCriterion("booking_time not between", value1, value2, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andSailingDateIsNull() {
            addCriterion("sailing_date is null");
            return (Criteria) this;
        }

        public Criteria andSailingDateIsNotNull() {
            addCriterion("sailing_date is not null");
            return (Criteria) this;
        }

        public Criteria andSailingDateEqualTo(Date value) {
            addCriterion("sailing_date =", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotEqualTo(Date value) {
            addCriterion("sailing_date <>", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateGreaterThan(Date value) {
            addCriterion("sailing_date >", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sailing_date >=", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateLessThan(Date value) {
            addCriterion("sailing_date <", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateLessThanOrEqualTo(Date value) {
            addCriterion("sailing_date <=", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateIn(List<Date> values) {
            addCriterion("sailing_date in", values, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotIn(List<Date> values) {
            addCriterion("sailing_date not in", values, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateBetween(Date value1, Date value2) {
            addCriterion("sailing_date between", value1, value2, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotBetween(Date value1, Date value2) {
            addCriterion("sailing_date not between", value1, value2, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIsNull() {
            addCriterion("customs_clearance is null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIsNotNull() {
            addCriterion("customs_clearance is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceEqualTo(Date value) {
            addCriterion("customs_clearance =", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotEqualTo(Date value) {
            addCriterion("customs_clearance <>", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceGreaterThan(Date value) {
            addCriterion("customs_clearance >", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceGreaterThanOrEqualTo(Date value) {
            addCriterion("customs_clearance >=", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceLessThan(Date value) {
            addCriterion("customs_clearance <", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceLessThanOrEqualTo(Date value) {
            addCriterion("customs_clearance <=", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIn(List<Date> values) {
            addCriterion("customs_clearance in", values, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotIn(List<Date> values) {
            addCriterion("customs_clearance not in", values, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceBetween(Date value1, Date value2) {
            addCriterion("customs_clearance between", value1, value2, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotBetween(Date value1, Date value2) {
            addCriterion("customs_clearance not between", value1, value2, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIsNull() {
            addCriterion("leave_port_time is null");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIsNotNull() {
            addCriterion("leave_port_time is not null");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeEqualTo(Date value) {
            addCriterion("leave_port_time =", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotEqualTo(Date value) {
            addCriterion("leave_port_time <>", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeGreaterThan(Date value) {
            addCriterion("leave_port_time >", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("leave_port_time >=", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeLessThan(Date value) {
            addCriterion("leave_port_time <", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeLessThanOrEqualTo(Date value) {
            addCriterion("leave_port_time <=", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIn(List<Date> values) {
            addCriterion("leave_port_time in", values, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotIn(List<Date> values) {
            addCriterion("leave_port_time not in", values, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeBetween(Date value1, Date value2) {
            addCriterion("leave_port_time between", value1, value2, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotBetween(Date value1, Date value2) {
            addCriterion("leave_port_time not between", value1, value2, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIsNull() {
            addCriterion("arrival_port_time is null");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIsNotNull() {
            addCriterion("arrival_port_time is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeEqualTo(Date value) {
            addCriterion("arrival_port_time =", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotEqualTo(Date value) {
            addCriterion("arrival_port_time <>", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeGreaterThan(Date value) {
            addCriterion("arrival_port_time >", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("arrival_port_time >=", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeLessThan(Date value) {
            addCriterion("arrival_port_time <", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeLessThanOrEqualTo(Date value) {
            addCriterion("arrival_port_time <=", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIn(List<Date> values) {
            addCriterion("arrival_port_time in", values, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotIn(List<Date> values) {
            addCriterion("arrival_port_time not in", values, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeBetween(Date value1, Date value2) {
            addCriterion("arrival_port_time between", value1, value2, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotBetween(Date value1, Date value2) {
            addCriterion("arrival_port_time not between", value1, value2, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIsNull() {
            addCriterion("accomplish_date is null");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIsNotNull() {
            addCriterion("accomplish_date is not null");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateEqualTo(Date value) {
            addCriterion("accomplish_date =", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotEqualTo(Date value) {
            addCriterion("accomplish_date <>", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateGreaterThan(Date value) {
            addCriterion("accomplish_date >", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("accomplish_date >=", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateLessThan(Date value) {
            addCriterion("accomplish_date <", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateLessThanOrEqualTo(Date value) {
            addCriterion("accomplish_date <=", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIn(List<Date> values) {
            addCriterion("accomplish_date in", values, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotIn(List<Date> values) {
            addCriterion("accomplish_date not in", values, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateBetween(Date value1, Date value2) {
            addCriterion("accomplish_date between", value1, value2, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotBetween(Date value1, Date value2) {
            addCriterion("accomplish_date not between", value1, value2, "accomplishDate");
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

        public Criteria andProjectRequirePurchaseDateIsNull() {
            addCriterion("project_require_purchase_date is null");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateIsNotNull() {
            addCriterion("project_require_purchase_date is not null");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date =", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date <>", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date >", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date >=", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateLessThan(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date <", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("project_require_purchase_date <=", value, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("project_require_purchase_date in", values, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("project_require_purchase_date not in", values, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("project_require_purchase_date between", value1, value2, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andProjectRequirePurchaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("project_require_purchase_date not between", value1, value2, "projectRequirePurchaseDate");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumIsNull() {
            addCriterion("inspect_instock_num is null");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumIsNotNull() {
            addCriterion("inspect_instock_num is not null");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumEqualTo(Integer value) {
            addCriterion("inspect_instock_num =", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumNotEqualTo(Integer value) {
            addCriterion("inspect_instock_num <>", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumGreaterThan(Integer value) {
            addCriterion("inspect_instock_num >", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_instock_num >=", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumLessThan(Integer value) {
            addCriterion("inspect_instock_num <", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_instock_num <=", value, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumIn(List<Integer> values) {
            addCriterion("inspect_instock_num in", values, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumNotIn(List<Integer> values) {
            addCriterion("inspect_instock_num not in", values, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumBetween(Integer value1, Integer value2) {
            addCriterion("inspect_instock_num between", value1, value2, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andInspectInstockNumNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_instock_num not between", value1, value2, "inspectInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumIsNull() {
            addCriterion("null_instock_num is null");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumIsNotNull() {
            addCriterion("null_instock_num is not null");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumEqualTo(Integer value) {
            addCriterion("null_instock_num =", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumNotEqualTo(Integer value) {
            addCriterion("null_instock_num <>", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumGreaterThan(Integer value) {
            addCriterion("null_instock_num >", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("null_instock_num >=", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumLessThan(Integer value) {
            addCriterion("null_instock_num <", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumLessThanOrEqualTo(Integer value) {
            addCriterion("null_instock_num <=", value, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumIn(List<Integer> values) {
            addCriterion("null_instock_num in", values, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumNotIn(List<Integer> values) {
            addCriterion("null_instock_num not in", values, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumBetween(Integer value1, Integer value2) {
            addCriterion("null_instock_num between", value1, value2, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andNullInstockNumNotBetween(Integer value1, Integer value2) {
            addCriterion("null_instock_num not between", value1, value2, "nullInstockNum");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIsNull() {
            addCriterion("ware_houseman is null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIsNotNull() {
            addCriterion("ware_houseman is not null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanEqualTo(Integer value) {
            addCriterion("ware_houseman =", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotEqualTo(Integer value) {
            addCriterion("ware_houseman <>", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanGreaterThan(Integer value) {
            addCriterion("ware_houseman >", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanGreaterThanOrEqualTo(Integer value) {
            addCriterion("ware_houseman >=", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanLessThan(Integer value) {
            addCriterion("ware_houseman <", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanLessThanOrEqualTo(Integer value) {
            addCriterion("ware_houseman <=", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIn(List<Integer> values) {
            addCriterion("ware_houseman in", values, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotIn(List<Integer> values) {
            addCriterion("ware_houseman not in", values, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanBetween(Integer value1, Integer value2) {
            addCriterion("ware_houseman between", value1, value2, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotBetween(Integer value1, Integer value2) {
            addCriterion("ware_houseman not between", value1, value2, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIsNull() {
            addCriterion("leave_date is null");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIsNotNull() {
            addCriterion("leave_date is not null");
            return (Criteria) this;
        }

        public Criteria andLeaveDateEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date =", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date <>", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateGreaterThan(Date value) {
            addCriterionForJDBCDate("leave_date >", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date >=", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateLessThan(Date value) {
            addCriterionForJDBCDate("leave_date <", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date <=", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIn(List<Date> values) {
            addCriterionForJDBCDate("leave_date in", values, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("leave_date not in", values, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("leave_date between", value1, value2, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("leave_date not between", value1, value2, "leaveDate");
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

        public Criteria andPriceIsNull() {
            addCriterion("price is null");
            return (Criteria) this;
        }

        public Criteria andPriceIsNotNull() {
            addCriterion("price is not null");
            return (Criteria) this;
        }

        public Criteria andPriceEqualTo(BigDecimal value) {
            addCriterion("price =", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotEqualTo(BigDecimal value) {
            addCriterion("price <>", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThan(BigDecimal value) {
            addCriterion("price >", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("price >=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThan(BigDecimal value) {
            addCriterion("price <", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("price <=", value, "price");
            return (Criteria) this;
        }

        public Criteria andPriceIn(List<BigDecimal> values) {
            addCriterion("price in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotIn(List<BigDecimal> values) {
            addCriterion("price not in", values, "price");
            return (Criteria) this;
        }

        public Criteria andPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("price not between", value1, value2, "price");
            return (Criteria) this;
        }

        public Criteria andMeteNameIsNull() {
            addCriterion("mete_name is null");
            return (Criteria) this;
        }

        public Criteria andMeteNameIsNotNull() {
            addCriterion("mete_name is not null");
            return (Criteria) this;
        }

        public Criteria andMeteNameEqualTo(String value) {
            addCriterion("mete_name =", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameNotEqualTo(String value) {
            addCriterion("mete_name <>", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameGreaterThan(String value) {
            addCriterion("mete_name >", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameGreaterThanOrEqualTo(String value) {
            addCriterion("mete_name >=", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameLessThan(String value) {
            addCriterion("mete_name <", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameLessThanOrEqualTo(String value) {
            addCriterion("mete_name <=", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameLike(String value) {
            addCriterion("mete_name like", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameNotLike(String value) {
            addCriterion("mete_name not like", value, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameIn(List<String> values) {
            addCriterion("mete_name in", values, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameNotIn(List<String> values) {
            addCriterion("mete_name not in", values, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameBetween(String value1, String value2) {
            addCriterion("mete_name between", value1, value2, "meteName");
            return (Criteria) this;
        }

        public Criteria andMeteNameNotBetween(String value1, String value2) {
            addCriterion("mete_name not between", value1, value2, "meteName");
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