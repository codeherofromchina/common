package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchContractGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchContractGoodsExample() {
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

        public Criteria andGoodsIdIsNull() {
            addCriterion("goods_id is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIsNotNull() {
            addCriterion("goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsIdEqualTo(Integer value) {
            addCriterion("goods_id =", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotEqualTo(Integer value) {
            addCriterion("goods_id <>", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThan(Integer value) {
            addCriterion("goods_id >", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("goods_id >=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThan(Integer value) {
            addCriterion("goods_id <", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("goods_id <=", value, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdIn(List<Integer> values) {
            addCriterion("goods_id in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotIn(List<Integer> values) {
            addCriterion("goods_id not in", values, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("goods_id between", value1, value2, "goodsId");
            return (Criteria) this;
        }

        public Criteria andGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("goods_id not between", value1, value2, "goodsId");
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

        public Criteria andTotalPriceIsNull() {
            addCriterion("total_price is null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIsNotNull() {
            addCriterion("total_price is not null");
            return (Criteria) this;
        }

        public Criteria andTotalPriceEqualTo(BigDecimal value) {
            addCriterion("total_price =", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("total_price <>", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("total_price >", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price >=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThan(BigDecimal value) {
            addCriterion("total_price <", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_price <=", value, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceIn(List<BigDecimal> values) {
            addCriterion("total_price in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("total_price not in", values, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price between", value1, value2, "totalPrice");
            return (Criteria) this;
        }

        public Criteria andTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_price not between", value1, value2, "totalPrice");
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

        public Criteria andPurchaseNumIsNull() {
            addCriterion("purchase_num is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumIsNotNull() {
            addCriterion("purchase_num is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumEqualTo(Integer value) {
            addCriterion("purchase_num =", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumNotEqualTo(Integer value) {
            addCriterion("purchase_num <>", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumGreaterThan(Integer value) {
            addCriterion("purchase_num >", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_num >=", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumLessThan(Integer value) {
            addCriterion("purchase_num <", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_num <=", value, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumIn(List<Integer> values) {
            addCriterion("purchase_num in", values, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumNotIn(List<Integer> values) {
            addCriterion("purchase_num not in", values, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumBetween(Integer value1, Integer value2) {
            addCriterion("purchase_num between", value1, value2, "purchaseNum");
            return (Criteria) this;
        }

        public Criteria andPurchaseNumNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_num not between", value1, value2, "purchaseNum");
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

        public Criteria andPrePurchContractNumIsNull() {
            addCriterion("pre_purch_contract_num is null");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumIsNotNull() {
            addCriterion("pre_purch_contract_num is not null");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumEqualTo(Integer value) {
            addCriterion("pre_purch_contract_num =", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumNotEqualTo(Integer value) {
            addCriterion("pre_purch_contract_num <>", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumGreaterThan(Integer value) {
            addCriterion("pre_purch_contract_num >", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pre_purch_contract_num >=", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumLessThan(Integer value) {
            addCriterion("pre_purch_contract_num <", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumLessThanOrEqualTo(Integer value) {
            addCriterion("pre_purch_contract_num <=", value, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumIn(List<Integer> values) {
            addCriterion("pre_purch_contract_num in", values, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumNotIn(List<Integer> values) {
            addCriterion("pre_purch_contract_num not in", values, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumBetween(Integer value1, Integer value2) {
            addCriterion("pre_purch_contract_num between", value1, value2, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andPrePurchContractNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pre_purch_contract_num not between", value1, value2, "prePurchContractNum");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceIsNull() {
            addCriterion("non_tax_price is null");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceIsNotNull() {
            addCriterion("non_tax_price is not null");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceEqualTo(BigDecimal value) {
            addCriterion("non_tax_price =", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceNotEqualTo(BigDecimal value) {
            addCriterion("non_tax_price <>", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceGreaterThan(BigDecimal value) {
            addCriterion("non_tax_price >", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("non_tax_price >=", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceLessThan(BigDecimal value) {
            addCriterion("non_tax_price <", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("non_tax_price <=", value, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceIn(List<BigDecimal> values) {
            addCriterion("non_tax_price in", values, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceNotIn(List<BigDecimal> values) {
            addCriterion("non_tax_price not in", values, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("non_tax_price between", value1, value2, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andNonTaxPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("non_tax_price not between", value1, value2, "nonTaxPrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNull() {
            addCriterion("purchase_price is null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIsNotNull() {
            addCriterion("purchase_price is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceEqualTo(BigDecimal value) {
            addCriterion("purchase_price =", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotEqualTo(BigDecimal value) {
            addCriterion("purchase_price <>", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThan(BigDecimal value) {
            addCriterion("purchase_price >", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price >=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThan(BigDecimal value) {
            addCriterion("purchase_price <", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_price <=", value, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceIn(List<BigDecimal> values) {
            addCriterion("purchase_price in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotIn(List<BigDecimal> values) {
            addCriterion("purchase_price not in", values, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchasePriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_price not between", value1, value2, "purchasePrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceIsNull() {
            addCriterion("purchase_total_price is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceIsNotNull() {
            addCriterion("purchase_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceEqualTo(BigDecimal value) {
            addCriterion("purchase_total_price =", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("purchase_total_price <>", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("purchase_total_price >", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_total_price >=", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceLessThan(BigDecimal value) {
            addCriterion("purchase_total_price <", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchase_total_price <=", value, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceIn(List<BigDecimal> values) {
            addCriterion("purchase_total_price in", values, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("purchase_total_price not in", values, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_total_price between", value1, value2, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchase_total_price not between", value1, value2, "purchaseTotalPrice");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkIsNull() {
            addCriterion("purchase_remark is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkIsNotNull() {
            addCriterion("purchase_remark is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkEqualTo(String value) {
            addCriterion("purchase_remark =", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkNotEqualTo(String value) {
            addCriterion("purchase_remark <>", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkGreaterThan(String value) {
            addCriterion("purchase_remark >", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_remark >=", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkLessThan(String value) {
            addCriterion("purchase_remark <", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkLessThanOrEqualTo(String value) {
            addCriterion("purchase_remark <=", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkLike(String value) {
            addCriterion("purchase_remark like", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkNotLike(String value) {
            addCriterion("purchase_remark not like", value, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkIn(List<String> values) {
            addCriterion("purchase_remark in", values, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkNotIn(List<String> values) {
            addCriterion("purchase_remark not in", values, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkBetween(String value1, String value2) {
            addCriterion("purchase_remark between", value1, value2, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andPurchaseRemarkNotBetween(String value1, String value2) {
            addCriterion("purchase_remark not between", value1, value2, "purchaseRemark");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIsNull() {
            addCriterion("tax_price is null");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIsNotNull() {
            addCriterion("tax_price is not null");
            return (Criteria) this;
        }

        public Criteria andTaxPriceEqualTo(BigDecimal value) {
            addCriterion("tax_price =", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotEqualTo(BigDecimal value) {
            addCriterion("tax_price <>", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceGreaterThan(BigDecimal value) {
            addCriterion("tax_price >", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_price >=", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceLessThan(BigDecimal value) {
            addCriterion("tax_price <", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_price <=", value, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceIn(List<BigDecimal> values) {
            addCriterion("tax_price in", values, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotIn(List<BigDecimal> values) {
            addCriterion("tax_price not in", values, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_price between", value1, value2, "taxPrice");
            return (Criteria) this;
        }

        public Criteria andTaxPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_price not between", value1, value2, "taxPrice");
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