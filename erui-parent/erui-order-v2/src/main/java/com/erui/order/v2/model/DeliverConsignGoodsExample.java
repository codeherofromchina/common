package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliverConsignGoodsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeliverConsignGoodsExample() {
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

        public Criteria andDeliverConsignIdIsNull() {
            addCriterion("deliver_consign_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdIsNotNull() {
            addCriterion("deliver_consign_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdEqualTo(Integer value) {
            addCriterion("deliver_consign_id =", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotEqualTo(Integer value) {
            addCriterion("deliver_consign_id <>", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdGreaterThan(Integer value) {
            addCriterion("deliver_consign_id >", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_id >=", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdLessThan(Integer value) {
            addCriterion("deliver_consign_id <", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_consign_id <=", value, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdIn(List<Integer> values) {
            addCriterion("deliver_consign_id in", values, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotIn(List<Integer> values) {
            addCriterion("deliver_consign_id not in", values, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_id between", value1, value2, "deliverConsignId");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_consign_id not between", value1, value2, "deliverConsignId");
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

        public Criteria andPackRequireIsNull() {
            addCriterion("pack_require is null");
            return (Criteria) this;
        }

        public Criteria andPackRequireIsNotNull() {
            addCriterion("pack_require is not null");
            return (Criteria) this;
        }

        public Criteria andPackRequireEqualTo(String value) {
            addCriterion("pack_require =", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireNotEqualTo(String value) {
            addCriterion("pack_require <>", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireGreaterThan(String value) {
            addCriterion("pack_require >", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireGreaterThanOrEqualTo(String value) {
            addCriterion("pack_require >=", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireLessThan(String value) {
            addCriterion("pack_require <", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireLessThanOrEqualTo(String value) {
            addCriterion("pack_require <=", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireLike(String value) {
            addCriterion("pack_require like", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireNotLike(String value) {
            addCriterion("pack_require not like", value, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireIn(List<String> values) {
            addCriterion("pack_require in", values, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireNotIn(List<String> values) {
            addCriterion("pack_require not in", values, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireBetween(String value1, String value2) {
            addCriterion("pack_require between", value1, value2, "packRequire");
            return (Criteria) this;
        }

        public Criteria andPackRequireNotBetween(String value1, String value2) {
            addCriterion("pack_require not between", value1, value2, "packRequire");
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

        public Criteria andCreateUserIdEqualTo(Integer value) {
            addCriterion("create_user_id =", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotEqualTo(Integer value) {
            addCriterion("create_user_id <>", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThan(Integer value) {
            addCriterion("create_user_id >", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("create_user_id >=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThan(Integer value) {
            addCriterion("create_user_id <", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("create_user_id <=", value, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdIn(List<Integer> values) {
            addCriterion("create_user_id in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotIn(List<Integer> values) {
            addCriterion("create_user_id not in", values, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andCreateUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("create_user_id not between", value1, value2, "createUserId");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkIsNull() {
            addCriterion("outbound_remark is null");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkIsNotNull() {
            addCriterion("outbound_remark is not null");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkEqualTo(String value) {
            addCriterion("outbound_remark =", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkNotEqualTo(String value) {
            addCriterion("outbound_remark <>", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkGreaterThan(String value) {
            addCriterion("outbound_remark >", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("outbound_remark >=", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkLessThan(String value) {
            addCriterion("outbound_remark <", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkLessThanOrEqualTo(String value) {
            addCriterion("outbound_remark <=", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkLike(String value) {
            addCriterion("outbound_remark like", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkNotLike(String value) {
            addCriterion("outbound_remark not like", value, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkIn(List<String> values) {
            addCriterion("outbound_remark in", values, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkNotIn(List<String> values) {
            addCriterion("outbound_remark not in", values, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkBetween(String value1, String value2) {
            addCriterion("outbound_remark between", value1, value2, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundRemarkNotBetween(String value1, String value2) {
            addCriterion("outbound_remark not between", value1, value2, "outboundRemark");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIsNull() {
            addCriterion("outbound_num is null");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIsNotNull() {
            addCriterion("outbound_num is not null");
            return (Criteria) this;
        }

        public Criteria andOutboundNumEqualTo(Integer value) {
            addCriterion("outbound_num =", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotEqualTo(Integer value) {
            addCriterion("outbound_num <>", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumGreaterThan(Integer value) {
            addCriterion("outbound_num >", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("outbound_num >=", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumLessThan(Integer value) {
            addCriterion("outbound_num <", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumLessThanOrEqualTo(Integer value) {
            addCriterion("outbound_num <=", value, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumIn(List<Integer> values) {
            addCriterion("outbound_num in", values, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotIn(List<Integer> values) {
            addCriterion("outbound_num not in", values, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumBetween(Integer value1, Integer value2) {
            addCriterion("outbound_num between", value1, value2, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andOutboundNumNotBetween(Integer value1, Integer value2) {
            addCriterion("outbound_num not between", value1, value2, "outboundNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumIsNull() {
            addCriterion("straight_num is null");
            return (Criteria) this;
        }

        public Criteria andStraightNumIsNotNull() {
            addCriterion("straight_num is not null");
            return (Criteria) this;
        }

        public Criteria andStraightNumEqualTo(Integer value) {
            addCriterion("straight_num =", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumNotEqualTo(Integer value) {
            addCriterion("straight_num <>", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumGreaterThan(Integer value) {
            addCriterion("straight_num >", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("straight_num >=", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumLessThan(Integer value) {
            addCriterion("straight_num <", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumLessThanOrEqualTo(Integer value) {
            addCriterion("straight_num <=", value, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumIn(List<Integer> values) {
            addCriterion("straight_num in", values, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumNotIn(List<Integer> values) {
            addCriterion("straight_num not in", values, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumBetween(Integer value1, Integer value2) {
            addCriterion("straight_num between", value1, value2, "straightNum");
            return (Criteria) this;
        }

        public Criteria andStraightNumNotBetween(Integer value1, Integer value2) {
            addCriterion("straight_num not between", value1, value2, "straightNum");
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