package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InspectApplyGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InspectApplyGoodsExample() {
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

        public Criteria andInspectReportIdIsNull() {
            addCriterion("inspect_report_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdIsNotNull() {
            addCriterion("inspect_report_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdEqualTo(Integer value) {
            addCriterion("inspect_report_id =", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotEqualTo(Integer value) {
            addCriterion("inspect_report_id <>", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdGreaterThan(Integer value) {
            addCriterion("inspect_report_id >", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_report_id >=", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdLessThan(Integer value) {
            addCriterion("inspect_report_id <", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_report_id <=", value, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdIn(List<Integer> values) {
            addCriterion("inspect_report_id in", values, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotIn(List<Integer> values) {
            addCriterion("inspect_report_id not in", values, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdBetween(Integer value1, Integer value2) {
            addCriterion("inspect_report_id between", value1, value2, "inspectReportId");
            return (Criteria) this;
        }

        public Criteria andInspectReportIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_report_id not between", value1, value2, "inspectReportId");
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

        public Criteria andPurchGoodsIdIsNull() {
            addCriterion("purch_goods_id is null");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdIsNotNull() {
            addCriterion("purch_goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdEqualTo(Integer value) {
            addCriterion("purch_goods_id =", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdNotEqualTo(Integer value) {
            addCriterion("purch_goods_id <>", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdGreaterThan(Integer value) {
            addCriterion("purch_goods_id >", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_goods_id >=", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdLessThan(Integer value) {
            addCriterion("purch_goods_id <", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("purch_goods_id <=", value, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdIn(List<Integer> values) {
            addCriterion("purch_goods_id in", values, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdNotIn(List<Integer> values) {
            addCriterion("purch_goods_id not in", values, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("purch_goods_id between", value1, value2, "purchGoodsId");
            return (Criteria) this;
        }

        public Criteria andPurchGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_goods_id not between", value1, value2, "purchGoodsId");
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

        public Criteria andHeightIsNull() {
            addCriterion("height is null");
            return (Criteria) this;
        }

        public Criteria andHeightIsNotNull() {
            addCriterion("height is not null");
            return (Criteria) this;
        }

        public Criteria andHeightEqualTo(BigDecimal value) {
            addCriterion("height =", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotEqualTo(BigDecimal value) {
            addCriterion("height <>", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThan(BigDecimal value) {
            addCriterion("height >", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("height >=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThan(BigDecimal value) {
            addCriterion("height <", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("height <=", value, "height");
            return (Criteria) this;
        }

        public Criteria andHeightIn(List<BigDecimal> values) {
            addCriterion("height in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotIn(List<BigDecimal> values) {
            addCriterion("height not in", values, "height");
            return (Criteria) this;
        }

        public Criteria andHeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("height between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andHeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("height not between", value1, value2, "height");
            return (Criteria) this;
        }

        public Criteria andLwhIsNull() {
            addCriterion("lwh is null");
            return (Criteria) this;
        }

        public Criteria andLwhIsNotNull() {
            addCriterion("lwh is not null");
            return (Criteria) this;
        }

        public Criteria andLwhEqualTo(String value) {
            addCriterion("lwh =", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhNotEqualTo(String value) {
            addCriterion("lwh <>", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhGreaterThan(String value) {
            addCriterion("lwh >", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhGreaterThanOrEqualTo(String value) {
            addCriterion("lwh >=", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhLessThan(String value) {
            addCriterion("lwh <", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhLessThanOrEqualTo(String value) {
            addCriterion("lwh <=", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhLike(String value) {
            addCriterion("lwh like", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhNotLike(String value) {
            addCriterion("lwh not like", value, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhIn(List<String> values) {
            addCriterion("lwh in", values, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhNotIn(List<String> values) {
            addCriterion("lwh not in", values, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhBetween(String value1, String value2) {
            addCriterion("lwh between", value1, value2, "lwh");
            return (Criteria) this;
        }

        public Criteria andLwhNotBetween(String value1, String value2) {
            addCriterion("lwh not between", value1, value2, "lwh");
            return (Criteria) this;
        }

        public Criteria andSamplesIsNull() {
            addCriterion("samples is null");
            return (Criteria) this;
        }

        public Criteria andSamplesIsNotNull() {
            addCriterion("samples is not null");
            return (Criteria) this;
        }

        public Criteria andSamplesEqualTo(Integer value) {
            addCriterion("samples =", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesNotEqualTo(Integer value) {
            addCriterion("samples <>", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesGreaterThan(Integer value) {
            addCriterion("samples >", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesGreaterThanOrEqualTo(Integer value) {
            addCriterion("samples >=", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesLessThan(Integer value) {
            addCriterion("samples <", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesLessThanOrEqualTo(Integer value) {
            addCriterion("samples <=", value, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesIn(List<Integer> values) {
            addCriterion("samples in", values, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesNotIn(List<Integer> values) {
            addCriterion("samples not in", values, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesBetween(Integer value1, Integer value2) {
            addCriterion("samples between", value1, value2, "samples");
            return (Criteria) this;
        }

        public Criteria andSamplesNotBetween(Integer value1, Integer value2) {
            addCriterion("samples not between", value1, value2, "samples");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedIsNull() {
            addCriterion("unqualified is null");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedIsNotNull() {
            addCriterion("unqualified is not null");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedEqualTo(Integer value) {
            addCriterion("unqualified =", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedNotEqualTo(Integer value) {
            addCriterion("unqualified <>", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedGreaterThan(Integer value) {
            addCriterion("unqualified >", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedGreaterThanOrEqualTo(Integer value) {
            addCriterion("unqualified >=", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedLessThan(Integer value) {
            addCriterion("unqualified <", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedLessThanOrEqualTo(Integer value) {
            addCriterion("unqualified <=", value, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedIn(List<Integer> values) {
            addCriterion("unqualified in", values, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedNotIn(List<Integer> values) {
            addCriterion("unqualified not in", values, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedBetween(Integer value1, Integer value2) {
            addCriterion("unqualified between", value1, value2, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedNotBetween(Integer value1, Integer value2) {
            addCriterion("unqualified not between", value1, value2, "unqualified");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescIsNull() {
            addCriterion("unqualified_desc is null");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescIsNotNull() {
            addCriterion("unqualified_desc is not null");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescEqualTo(String value) {
            addCriterion("unqualified_desc =", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescNotEqualTo(String value) {
            addCriterion("unqualified_desc <>", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescGreaterThan(String value) {
            addCriterion("unqualified_desc >", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescGreaterThanOrEqualTo(String value) {
            addCriterion("unqualified_desc >=", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescLessThan(String value) {
            addCriterion("unqualified_desc <", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescLessThanOrEqualTo(String value) {
            addCriterion("unqualified_desc <=", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescLike(String value) {
            addCriterion("unqualified_desc like", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescNotLike(String value) {
            addCriterion("unqualified_desc not like", value, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescIn(List<String> values) {
            addCriterion("unqualified_desc in", values, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescNotIn(List<String> values) {
            addCriterion("unqualified_desc not in", values, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescBetween(String value1, String value2) {
            addCriterion("unqualified_desc between", value1, value2, "unqualifiedDesc");
            return (Criteria) this;
        }

        public Criteria andUnqualifiedDescNotBetween(String value1, String value2) {
            addCriterion("unqualified_desc not between", value1, value2, "unqualifiedDesc");
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