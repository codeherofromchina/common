package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InstockGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InstockGoodsExample() {
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

        public Criteria andInstockIdIsNull() {
            addCriterion("instock_id is null");
            return (Criteria) this;
        }

        public Criteria andInstockIdIsNotNull() {
            addCriterion("instock_id is not null");
            return (Criteria) this;
        }

        public Criteria andInstockIdEqualTo(Integer value) {
            addCriterion("instock_id =", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdNotEqualTo(Integer value) {
            addCriterion("instock_id <>", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdGreaterThan(Integer value) {
            addCriterion("instock_id >", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("instock_id >=", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdLessThan(Integer value) {
            addCriterion("instock_id <", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdLessThanOrEqualTo(Integer value) {
            addCriterion("instock_id <=", value, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdIn(List<Integer> values) {
            addCriterion("instock_id in", values, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdNotIn(List<Integer> values) {
            addCriterion("instock_id not in", values, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdBetween(Integer value1, Integer value2) {
            addCriterion("instock_id between", value1, value2, "instockId");
            return (Criteria) this;
        }

        public Criteria andInstockIdNotBetween(Integer value1, Integer value2) {
            addCriterion("instock_id not between", value1, value2, "instockId");
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

        public Criteria andQualifiedNumIsNull() {
            addCriterion("qualified_num is null");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumIsNotNull() {
            addCriterion("qualified_num is not null");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumEqualTo(Integer value) {
            addCriterion("qualified_num =", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumNotEqualTo(Integer value) {
            addCriterion("qualified_num <>", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumGreaterThan(Integer value) {
            addCriterion("qualified_num >", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("qualified_num >=", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumLessThan(Integer value) {
            addCriterion("qualified_num <", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumLessThanOrEqualTo(Integer value) {
            addCriterion("qualified_num <=", value, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumIn(List<Integer> values) {
            addCriterion("qualified_num in", values, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumNotIn(List<Integer> values) {
            addCriterion("qualified_num not in", values, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumBetween(Integer value1, Integer value2) {
            addCriterion("qualified_num between", value1, value2, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andQualifiedNumNotBetween(Integer value1, Integer value2) {
            addCriterion("qualified_num not between", value1, value2, "qualifiedNum");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdIsNull() {
            addCriterion("inspect_apply_goods_id is null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdIsNotNull() {
            addCriterion("inspect_apply_goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdEqualTo(Integer value) {
            addCriterion("inspect_apply_goods_id =", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdNotEqualTo(Integer value) {
            addCriterion("inspect_apply_goods_id <>", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdGreaterThan(Integer value) {
            addCriterion("inspect_apply_goods_id >", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("inspect_apply_goods_id >=", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdLessThan(Integer value) {
            addCriterion("inspect_apply_goods_id <", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("inspect_apply_goods_id <=", value, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdIn(List<Integer> values) {
            addCriterion("inspect_apply_goods_id in", values, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdNotIn(List<Integer> values) {
            addCriterion("inspect_apply_goods_id not in", values, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("inspect_apply_goods_id between", value1, value2, "inspectApplyGoodsId");
            return (Criteria) this;
        }

        public Criteria andInspectApplyGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("inspect_apply_goods_id not between", value1, value2, "inspectApplyGoodsId");
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

        public Criteria andInstockStockIsNull() {
            addCriterion("instock_stock is null");
            return (Criteria) this;
        }

        public Criteria andInstockStockIsNotNull() {
            addCriterion("instock_stock is not null");
            return (Criteria) this;
        }

        public Criteria andInstockStockEqualTo(String value) {
            addCriterion("instock_stock =", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockNotEqualTo(String value) {
            addCriterion("instock_stock <>", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockGreaterThan(String value) {
            addCriterion("instock_stock >", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockGreaterThanOrEqualTo(String value) {
            addCriterion("instock_stock >=", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockLessThan(String value) {
            addCriterion("instock_stock <", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockLessThanOrEqualTo(String value) {
            addCriterion("instock_stock <=", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockLike(String value) {
            addCriterion("instock_stock like", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockNotLike(String value) {
            addCriterion("instock_stock not like", value, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockIn(List<String> values) {
            addCriterion("instock_stock in", values, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockNotIn(List<String> values) {
            addCriterion("instock_stock not in", values, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockBetween(String value1, String value2) {
            addCriterion("instock_stock between", value1, value2, "instockStock");
            return (Criteria) this;
        }

        public Criteria andInstockStockNotBetween(String value1, String value2) {
            addCriterion("instock_stock not between", value1, value2, "instockStock");
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

        public Criteria andCreateUserIdIsNull() {
            addCriterion("create_user_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIsNotNull() {
            addCriterion("create_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdEqualTo(String value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(String value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(String value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(String value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(String value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLike(String value) {
            addCriterion("create_user_id like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotLike(String value) {
            addCriterion("create_user_id not like", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<String> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<String> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(String value1, String value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(String value1, String value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
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