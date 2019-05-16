package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchRequisitionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchRequisitionExample() {
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

        public Criteria andPmUidIsNull() {
            addCriterion("pm_uid is null");
            return (Criteria) this;
        }

        public Criteria andPmUidIsNotNull() {
            addCriterion("pm_uid is not null");
            return (Criteria) this;
        }

        public Criteria andPmUidEqualTo(Integer value) {
            addCriterion("pm_uid =", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidNotEqualTo(Integer value) {
            addCriterion("pm_uid <>", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidGreaterThan(Integer value) {
            addCriterion("pm_uid >", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pm_uid >=", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidLessThan(Integer value) {
            addCriterion("pm_uid <", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidLessThanOrEqualTo(Integer value) {
            addCriterion("pm_uid <=", value, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidIn(List<Integer> values) {
            addCriterion("pm_uid in", values, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidNotIn(List<Integer> values) {
            addCriterion("pm_uid not in", values, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidBetween(Integer value1, Integer value2) {
            addCriterion("pm_uid between", value1, value2, "pmUid");
            return (Criteria) this;
        }

        public Criteria andPmUidNotBetween(Integer value1, Integer value2) {
            addCriterion("pm_uid not between", value1, value2, "pmUid");
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

        public Criteria andDepartmentEqualTo(Integer value) {
            addCriterion("department =", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotEqualTo(Integer value) {
            addCriterion("department <>", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThan(Integer value) {
            addCriterion("department >", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThanOrEqualTo(Integer value) {
            addCriterion("department >=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThan(Integer value) {
            addCriterion("department <", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThanOrEqualTo(Integer value) {
            addCriterion("department <=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentIn(List<Integer> values) {
            addCriterion("department in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotIn(List<Integer> values) {
            addCriterion("department not in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentBetween(Integer value1, Integer value2) {
            addCriterion("department between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotBetween(Integer value1, Integer value2) {
            addCriterion("department not between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIsNull() {
            addCriterion("submit_date is null");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIsNotNull() {
            addCriterion("submit_date is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitDateEqualTo(Date value) {
            addCriterion("submit_date =", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotEqualTo(Date value) {
            addCriterion("submit_date <>", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateGreaterThan(Date value) {
            addCriterion("submit_date >", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_date >=", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateLessThan(Date value) {
            addCriterion("submit_date <", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateLessThanOrEqualTo(Date value) {
            addCriterion("submit_date <=", value, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateIn(List<Date> values) {
            addCriterion("submit_date in", values, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotIn(List<Date> values) {
            addCriterion("submit_date not in", values, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateBetween(Date value1, Date value2) {
            addCriterion("submit_date between", value1, value2, "submitDate");
            return (Criteria) this;
        }

        public Criteria andSubmitDateNotBetween(Date value1, Date value2) {
            addCriterion("submit_date not between", value1, value2, "submitDate");
            return (Criteria) this;
        }

        public Criteria andTradeMethodIsNull() {
            addCriterion("trade_method is null");
            return (Criteria) this;
        }

        public Criteria andTradeMethodIsNotNull() {
            addCriterion("trade_method is not null");
            return (Criteria) this;
        }

        public Criteria andTradeMethodEqualTo(String value) {
            addCriterion("trade_method =", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodNotEqualTo(String value) {
            addCriterion("trade_method <>", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodGreaterThan(String value) {
            addCriterion("trade_method >", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodGreaterThanOrEqualTo(String value) {
            addCriterion("trade_method >=", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodLessThan(String value) {
            addCriterion("trade_method <", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodLessThanOrEqualTo(String value) {
            addCriterion("trade_method <=", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodLike(String value) {
            addCriterion("trade_method like", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodNotLike(String value) {
            addCriterion("trade_method not like", value, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodIn(List<String> values) {
            addCriterion("trade_method in", values, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodNotIn(List<String> values) {
            addCriterion("trade_method not in", values, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodBetween(String value1, String value2) {
            addCriterion("trade_method between", value1, value2, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTradeMethodNotBetween(String value1, String value2) {
            addCriterion("trade_method not between", value1, value2, "tradeMethod");
            return (Criteria) this;
        }

        public Criteria andTransModeBnIsNull() {
            addCriterion("trans_mode_bn is null");
            return (Criteria) this;
        }

        public Criteria andTransModeBnIsNotNull() {
            addCriterion("trans_mode_bn is not null");
            return (Criteria) this;
        }

        public Criteria andTransModeBnEqualTo(String value) {
            addCriterion("trans_mode_bn =", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnNotEqualTo(String value) {
            addCriterion("trans_mode_bn <>", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnGreaterThan(String value) {
            addCriterion("trans_mode_bn >", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnGreaterThanOrEqualTo(String value) {
            addCriterion("trans_mode_bn >=", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnLessThan(String value) {
            addCriterion("trans_mode_bn <", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnLessThanOrEqualTo(String value) {
            addCriterion("trans_mode_bn <=", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnLike(String value) {
            addCriterion("trans_mode_bn like", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnNotLike(String value) {
            addCriterion("trans_mode_bn not like", value, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnIn(List<String> values) {
            addCriterion("trans_mode_bn in", values, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnNotIn(List<String> values) {
            addCriterion("trans_mode_bn not in", values, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnBetween(String value1, String value2) {
            addCriterion("trans_mode_bn between", value1, value2, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andTransModeBnNotBetween(String value1, String value2) {
            addCriterion("trans_mode_bn not between", value1, value2, "transModeBn");
            return (Criteria) this;
        }

        public Criteria andFactorySendIsNull() {
            addCriterion("factory_send is null");
            return (Criteria) this;
        }

        public Criteria andFactorySendIsNotNull() {
            addCriterion("factory_send is not null");
            return (Criteria) this;
        }

        public Criteria andFactorySendEqualTo(Boolean value) {
            addCriterion("factory_send =", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendNotEqualTo(Boolean value) {
            addCriterion("factory_send <>", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendGreaterThan(Boolean value) {
            addCriterion("factory_send >", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendGreaterThanOrEqualTo(Boolean value) {
            addCriterion("factory_send >=", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendLessThan(Boolean value) {
            addCriterion("factory_send <", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendLessThanOrEqualTo(Boolean value) {
            addCriterion("factory_send <=", value, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendIn(List<Boolean> values) {
            addCriterion("factory_send in", values, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendNotIn(List<Boolean> values) {
            addCriterion("factory_send not in", values, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendBetween(Boolean value1, Boolean value2) {
            addCriterion("factory_send between", value1, value2, "factorySend");
            return (Criteria) this;
        }

        public Criteria andFactorySendNotBetween(Boolean value1, Boolean value2) {
            addCriterion("factory_send not between", value1, value2, "factorySend");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceIsNull() {
            addCriterion("delivery_place is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceIsNotNull() {
            addCriterion("delivery_place is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceEqualTo(String value) {
            addCriterion("delivery_place =", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceNotEqualTo(String value) {
            addCriterion("delivery_place <>", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceGreaterThan(String value) {
            addCriterion("delivery_place >", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("delivery_place >=", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceLessThan(String value) {
            addCriterion("delivery_place <", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceLessThanOrEqualTo(String value) {
            addCriterion("delivery_place <=", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceLike(String value) {
            addCriterion("delivery_place like", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceNotLike(String value) {
            addCriterion("delivery_place not like", value, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceIn(List<String> values) {
            addCriterion("delivery_place in", values, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceNotIn(List<String> values) {
            addCriterion("delivery_place not in", values, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceBetween(String value1, String value2) {
            addCriterion("delivery_place between", value1, value2, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andDeliveryPlaceNotBetween(String value1, String value2) {
            addCriterion("delivery_place not between", value1, value2, "deliveryPlace");
            return (Criteria) this;
        }

        public Criteria andRequirementsIsNull() {
            addCriterion("requirements is null");
            return (Criteria) this;
        }

        public Criteria andRequirementsIsNotNull() {
            addCriterion("requirements is not null");
            return (Criteria) this;
        }

        public Criteria andRequirementsEqualTo(String value) {
            addCriterion("requirements =", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsNotEqualTo(String value) {
            addCriterion("requirements <>", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsGreaterThan(String value) {
            addCriterion("requirements >", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsGreaterThanOrEqualTo(String value) {
            addCriterion("requirements >=", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsLessThan(String value) {
            addCriterion("requirements <", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsLessThanOrEqualTo(String value) {
            addCriterion("requirements <=", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsLike(String value) {
            addCriterion("requirements like", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsNotLike(String value) {
            addCriterion("requirements not like", value, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsIn(List<String> values) {
            addCriterion("requirements in", values, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsNotIn(List<String> values) {
            addCriterion("requirements not in", values, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsBetween(String value1, String value2) {
            addCriterion("requirements between", value1, value2, "requirements");
            return (Criteria) this;
        }

        public Criteria andRequirementsNotBetween(String value1, String value2) {
            addCriterion("requirements not between", value1, value2, "requirements");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPurchStatusIsNull() {
            addCriterion("purch_status is null");
            return (Criteria) this;
        }

        public Criteria andPurchStatusIsNotNull() {
            addCriterion("purch_status is not null");
            return (Criteria) this;
        }

        public Criteria andPurchStatusEqualTo(Integer value) {
            addCriterion("purch_status =", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusNotEqualTo(Integer value) {
            addCriterion("purch_status <>", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusGreaterThan(Integer value) {
            addCriterion("purch_status >", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_status >=", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusLessThan(Integer value) {
            addCriterion("purch_status <", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusLessThanOrEqualTo(Integer value) {
            addCriterion("purch_status <=", value, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusIn(List<Integer> values) {
            addCriterion("purch_status in", values, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusNotIn(List<Integer> values) {
            addCriterion("purch_status not in", values, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusBetween(Integer value1, Integer value2) {
            addCriterion("purch_status between", value1, value2, "purchStatus");
            return (Criteria) this;
        }

        public Criteria andPurchStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_status not between", value1, value2, "purchStatus");
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

        public Criteria andPurchaseNameIsNull() {
            addCriterion("purchase_name is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameIsNotNull() {
            addCriterion("purchase_name is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameEqualTo(String value) {
            addCriterion("purchase_name =", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotEqualTo(String value) {
            addCriterion("purchase_name <>", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameGreaterThan(String value) {
            addCriterion("purchase_name >", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameGreaterThanOrEqualTo(String value) {
            addCriterion("purchase_name >=", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLessThan(String value) {
            addCriterion("purchase_name <", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLessThanOrEqualTo(String value) {
            addCriterion("purchase_name <=", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameLike(String value) {
            addCriterion("purchase_name like", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotLike(String value) {
            addCriterion("purchase_name not like", value, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameIn(List<String> values) {
            addCriterion("purchase_name in", values, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotIn(List<String> values) {
            addCriterion("purchase_name not in", values, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameBetween(String value1, String value2) {
            addCriterion("purchase_name between", value1, value2, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseNameNotBetween(String value1, String value2) {
            addCriterion("purchase_name not between", value1, value2, "purchaseName");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIsNull() {
            addCriterion("purchase_uid is null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIsNotNull() {
            addCriterion("purchase_uid is not null");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidEqualTo(Integer value) {
            addCriterion("purchase_uid =", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotEqualTo(Integer value) {
            addCriterion("purchase_uid <>", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidGreaterThan(Integer value) {
            addCriterion("purchase_uid >", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("purchase_uid >=", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidLessThan(Integer value) {
            addCriterion("purchase_uid <", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidLessThanOrEqualTo(Integer value) {
            addCriterion("purchase_uid <=", value, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidIn(List<Integer> values) {
            addCriterion("purchase_uid in", values, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotIn(List<Integer> values) {
            addCriterion("purchase_uid not in", values, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidBetween(Integer value1, Integer value2) {
            addCriterion("purchase_uid between", value1, value2, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andPurchaseUidNotBetween(Integer value1, Integer value2) {
            addCriterion("purchase_uid not between", value1, value2, "purchaseUid");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdIsNull() {
            addCriterion("single_person_id is null");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdIsNotNull() {
            addCriterion("single_person_id is not null");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdEqualTo(Integer value) {
            addCriterion("single_person_id =", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdNotEqualTo(Integer value) {
            addCriterion("single_person_id <>", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdGreaterThan(Integer value) {
            addCriterion("single_person_id >", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("single_person_id >=", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdLessThan(Integer value) {
            addCriterion("single_person_id <", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdLessThanOrEqualTo(Integer value) {
            addCriterion("single_person_id <=", value, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdIn(List<Integer> values) {
            addCriterion("single_person_id in", values, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdNotIn(List<Integer> values) {
            addCriterion("single_person_id not in", values, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdBetween(Integer value1, Integer value2) {
            addCriterion("single_person_id between", value1, value2, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIdNotBetween(Integer value1, Integer value2) {
            addCriterion("single_person_id not between", value1, value2, "singlePersonId");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIsNull() {
            addCriterion("single_person is null");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIsNotNull() {
            addCriterion("single_person is not null");
            return (Criteria) this;
        }

        public Criteria andSinglePersonEqualTo(String value) {
            addCriterion("single_person =", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonNotEqualTo(String value) {
            addCriterion("single_person <>", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonGreaterThan(String value) {
            addCriterion("single_person >", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonGreaterThanOrEqualTo(String value) {
            addCriterion("single_person >=", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonLessThan(String value) {
            addCriterion("single_person <", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonLessThanOrEqualTo(String value) {
            addCriterion("single_person <=", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonLike(String value) {
            addCriterion("single_person like", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonNotLike(String value) {
            addCriterion("single_person not like", value, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonIn(List<String> values) {
            addCriterion("single_person in", values, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonNotIn(List<String> values) {
            addCriterion("single_person not in", values, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonBetween(String value1, String value2) {
            addCriterion("single_person between", value1, value2, "singlePerson");
            return (Criteria) this;
        }

        public Criteria andSinglePersonNotBetween(String value1, String value2) {
            addCriterion("single_person not between", value1, value2, "singlePerson");
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