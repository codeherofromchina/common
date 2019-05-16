package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderLogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderLogExample() {
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

        public Criteria andLogTypeIsNull() {
            addCriterion("log_type is null");
            return (Criteria) this;
        }

        public Criteria andLogTypeIsNotNull() {
            addCriterion("log_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogTypeEqualTo(Integer value) {
            addCriterion("log_type =", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotEqualTo(Integer value) {
            addCriterion("log_type <>", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThan(Integer value) {
            addCriterion("log_type >", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("log_type >=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThan(Integer value) {
            addCriterion("log_type <", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeLessThanOrEqualTo(Integer value) {
            addCriterion("log_type <=", value, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeIn(List<Integer> values) {
            addCriterion("log_type in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotIn(List<Integer> values) {
            addCriterion("log_type not in", values, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeBetween(Integer value1, Integer value2) {
            addCriterion("log_type between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andLogTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("log_type not between", value1, value2, "logType");
            return (Criteria) this;
        }

        public Criteria andOperationIsNull() {
            addCriterion("operation is null");
            return (Criteria) this;
        }

        public Criteria andOperationIsNotNull() {
            addCriterion("operation is not null");
            return (Criteria) this;
        }

        public Criteria andOperationEqualTo(String value) {
            addCriterion("operation =", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotEqualTo(String value) {
            addCriterion("operation <>", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThan(String value) {
            addCriterion("operation >", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationGreaterThanOrEqualTo(String value) {
            addCriterion("operation >=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThan(String value) {
            addCriterion("operation <", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLessThanOrEqualTo(String value) {
            addCriterion("operation <=", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationLike(String value) {
            addCriterion("operation like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotLike(String value) {
            addCriterion("operation not like", value, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationIn(List<String> values) {
            addCriterion("operation in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotIn(List<String> values) {
            addCriterion("operation not in", values, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationBetween(String value1, String value2) {
            addCriterion("operation between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andOperationNotBetween(String value1, String value2) {
            addCriterion("operation not between", value1, value2, "operation");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNull() {
            addCriterion("create_id is null");
            return (Criteria) this;
        }

        public Criteria andCreateIdIsNotNull() {
            addCriterion("create_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreateIdEqualTo(Integer value) {
            addCriterion("create_id =", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotEqualTo(Integer value) {
            addCriterion("create_id <>", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThan(Integer value) {
            addCriterion("create_id >", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_id >=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThan(Integer value) {
            addCriterion("create_id <", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_id <=", value, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdIn(List<Integer> values) {
            addCriterion("create_id in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotIn(List<Integer> values) {
            addCriterion("create_id not in", values, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdBetween(Integer value1, Integer value2) {
            addCriterion("create_id between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andCreateIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_id not between", value1, value2, "createId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdIsNull() {
            addCriterion("orders_goods_id is null");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdIsNotNull() {
            addCriterion("orders_goods_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdEqualTo(Integer value) {
            addCriterion("orders_goods_id =", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdNotEqualTo(Integer value) {
            addCriterion("orders_goods_id <>", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdGreaterThan(Integer value) {
            addCriterion("orders_goods_id >", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("orders_goods_id >=", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdLessThan(Integer value) {
            addCriterion("orders_goods_id <", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdLessThanOrEqualTo(Integer value) {
            addCriterion("orders_goods_id <=", value, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdIn(List<Integer> values) {
            addCriterion("orders_goods_id in", values, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdNotIn(List<Integer> values) {
            addCriterion("orders_goods_id not in", values, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdBetween(Integer value1, Integer value2) {
            addCriterion("orders_goods_id between", value1, value2, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andOrdersGoodsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("orders_goods_id not between", value1, value2, "ordersGoodsId");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNull() {
            addCriterion("create_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateNameIsNotNull() {
            addCriterion("create_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateNameEqualTo(String value) {
            addCriterion("create_name =", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotEqualTo(String value) {
            addCriterion("create_name <>", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThan(String value) {
            addCriterion("create_name >", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_name >=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThan(String value) {
            addCriterion("create_name <", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLessThanOrEqualTo(String value) {
            addCriterion("create_name <=", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameLike(String value) {
            addCriterion("create_name like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotLike(String value) {
            addCriterion("create_name not like", value, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameIn(List<String> values) {
            addCriterion("create_name in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotIn(List<String> values) {
            addCriterion("create_name not in", values, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameBetween(String value1, String value2) {
            addCriterion("create_name between", value1, value2, "createName");
            return (Criteria) this;
        }

        public Criteria andCreateNameNotBetween(String value1, String value2) {
            addCriterion("create_name not between", value1, value2, "createName");
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

        public Criteria andOrderAccountIdIsNull() {
            addCriterion("order_account_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdIsNotNull() {
            addCriterion("order_account_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdEqualTo(Integer value) {
            addCriterion("order_account_id =", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdNotEqualTo(Integer value) {
            addCriterion("order_account_id <>", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdGreaterThan(Integer value) {
            addCriterion("order_account_id >", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_account_id >=", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdLessThan(Integer value) {
            addCriterion("order_account_id <", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_account_id <=", value, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdIn(List<Integer> values) {
            addCriterion("order_account_id in", values, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdNotIn(List<Integer> values) {
            addCriterion("order_account_id not in", values, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdBetween(Integer value1, Integer value2) {
            addCriterion("order_account_id between", value1, value2, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andOrderAccountIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_account_id not between", value1, value2, "orderAccountId");
            return (Criteria) this;
        }

        public Criteria andBusinessDateIsNull() {
            addCriterion("business_date is null");
            return (Criteria) this;
        }

        public Criteria andBusinessDateIsNotNull() {
            addCriterion("business_date is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessDateEqualTo(Date value) {
            addCriterion("business_date =", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateNotEqualTo(Date value) {
            addCriterion("business_date <>", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateGreaterThan(Date value) {
            addCriterion("business_date >", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateGreaterThanOrEqualTo(Date value) {
            addCriterion("business_date >=", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateLessThan(Date value) {
            addCriterion("business_date <", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateLessThanOrEqualTo(Date value) {
            addCriterion("business_date <=", value, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateIn(List<Date> values) {
            addCriterion("business_date in", values, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateNotIn(List<Date> values) {
            addCriterion("business_date not in", values, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateBetween(Date value1, Date value2) {
            addCriterion("business_date between", value1, value2, "businessDate");
            return (Criteria) this;
        }

        public Criteria andBusinessDateNotBetween(Date value1, Date value2) {
            addCriterion("business_date not between", value1, value2, "businessDate");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdIsNull() {
            addCriterion("deliver_detail_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdIsNotNull() {
            addCriterion("deliver_detail_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdEqualTo(Integer value) {
            addCriterion("deliver_detail_id =", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdNotEqualTo(Integer value) {
            addCriterion("deliver_detail_id <>", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdGreaterThan(Integer value) {
            addCriterion("deliver_detail_id >", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_detail_id >=", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdLessThan(Integer value) {
            addCriterion("deliver_detail_id <", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_detail_id <=", value, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdIn(List<Integer> values) {
            addCriterion("deliver_detail_id in", values, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdNotIn(List<Integer> values) {
            addCriterion("deliver_detail_id not in", values, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdBetween(Integer value1, Integer value2) {
            addCriterion("deliver_detail_id between", value1, value2, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_detail_id not between", value1, value2, "deliverDetailId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdIsNull() {
            addCriterion("logistics_data_id is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdIsNotNull() {
            addCriterion("logistics_data_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdEqualTo(Integer value) {
            addCriterion("logistics_data_id =", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdNotEqualTo(Integer value) {
            addCriterion("logistics_data_id <>", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdGreaterThan(Integer value) {
            addCriterion("logistics_data_id >", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_data_id >=", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdLessThan(Integer value) {
            addCriterion("logistics_data_id <", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_data_id <=", value, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdIn(List<Integer> values) {
            addCriterion("logistics_data_id in", values, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdNotIn(List<Integer> values) {
            addCriterion("logistics_data_id not in", values, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdBetween(Integer value1, Integer value2) {
            addCriterion("logistics_data_id between", value1, value2, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andLogisticsDataIdNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_data_id not between", value1, value2, "logisticsDataId");
            return (Criteria) this;
        }

        public Criteria andEnOperationIsNull() {
            addCriterion("en_operation is null");
            return (Criteria) this;
        }

        public Criteria andEnOperationIsNotNull() {
            addCriterion("en_operation is not null");
            return (Criteria) this;
        }

        public Criteria andEnOperationEqualTo(String value) {
            addCriterion("en_operation =", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationNotEqualTo(String value) {
            addCriterion("en_operation <>", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationGreaterThan(String value) {
            addCriterion("en_operation >", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationGreaterThanOrEqualTo(String value) {
            addCriterion("en_operation >=", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationLessThan(String value) {
            addCriterion("en_operation <", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationLessThanOrEqualTo(String value) {
            addCriterion("en_operation <=", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationLike(String value) {
            addCriterion("en_operation like", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationNotLike(String value) {
            addCriterion("en_operation not like", value, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationIn(List<String> values) {
            addCriterion("en_operation in", values, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationNotIn(List<String> values) {
            addCriterion("en_operation not in", values, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationBetween(String value1, String value2) {
            addCriterion("en_operation between", value1, value2, "enOperation");
            return (Criteria) this;
        }

        public Criteria andEnOperationNotBetween(String value1, String value2) {
            addCriterion("en_operation not between", value1, value2, "enOperation");
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