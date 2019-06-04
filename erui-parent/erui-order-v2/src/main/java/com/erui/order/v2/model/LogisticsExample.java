package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.List;

public class LogisticsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LogisticsExample() {
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

        public Criteria andDeliverDetailNoIsNull() {
            addCriterion("deliver_detail_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoIsNotNull() {
            addCriterion("deliver_detail_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoEqualTo(String value) {
            addCriterion("deliver_detail_no =", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoNotEqualTo(String value) {
            addCriterion("deliver_detail_no <>", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoGreaterThan(String value) {
            addCriterion("deliver_detail_no >", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_detail_no >=", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoLessThan(String value) {
            addCriterion("deliver_detail_no <", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoLessThanOrEqualTo(String value) {
            addCriterion("deliver_detail_no <=", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoLike(String value) {
            addCriterion("deliver_detail_no like", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoNotLike(String value) {
            addCriterion("deliver_detail_no not like", value, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoIn(List<String> values) {
            addCriterion("deliver_detail_no in", values, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoNotIn(List<String> values) {
            addCriterion("deliver_detail_no not in", values, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoBetween(String value1, String value2) {
            addCriterion("deliver_detail_no between", value1, value2, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andDeliverDetailNoNotBetween(String value1, String value2) {
            addCriterion("deliver_detail_no not between", value1, value2, "deliverDetailNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIsNull() {
            addCriterion("logistics_no is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIsNotNull() {
            addCriterion("logistics_no is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoEqualTo(String value) {
            addCriterion("logistics_no =", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotEqualTo(String value) {
            addCriterion("logistics_no <>", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoGreaterThan(String value) {
            addCriterion("logistics_no >", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_no >=", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLessThan(String value) {
            addCriterion("logistics_no <", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLessThanOrEqualTo(String value) {
            addCriterion("logistics_no <=", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoLike(String value) {
            addCriterion("logistics_no like", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotLike(String value) {
            addCriterion("logistics_no not like", value, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoIn(List<String> values) {
            addCriterion("logistics_no in", values, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotIn(List<String> values) {
            addCriterion("logistics_no not in", values, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoBetween(String value1, String value2) {
            addCriterion("logistics_no between", value1, value2, "logisticsNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsNoNotBetween(String value1, String value2) {
            addCriterion("logistics_no not between", value1, value2, "logisticsNo");
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

        public Criteria andDeliverConsignNoIsNull() {
            addCriterion("deliver_consign_no is null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIsNotNull() {
            addCriterion("deliver_consign_no is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoEqualTo(String value) {
            addCriterion("deliver_consign_no =", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotEqualTo(String value) {
            addCriterion("deliver_consign_no <>", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThan(String value) {
            addCriterion("deliver_consign_no >", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoGreaterThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no >=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThan(String value) {
            addCriterion("deliver_consign_no <", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLessThanOrEqualTo(String value) {
            addCriterion("deliver_consign_no <=", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoLike(String value) {
            addCriterion("deliver_consign_no like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotLike(String value) {
            addCriterion("deliver_consign_no not like", value, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoIn(List<String> values) {
            addCriterion("deliver_consign_no in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotIn(List<String> values) {
            addCriterion("deliver_consign_no not in", values, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoBetween(String value1, String value2) {
            addCriterion("deliver_consign_no between", value1, value2, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andDeliverConsignNoNotBetween(String value1, String value2) {
            addCriterion("deliver_consign_no not between", value1, value2, "deliverConsignNo");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdIsNull() {
            addCriterion("logistics_user_id is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdIsNotNull() {
            addCriterion("logistics_user_id is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdEqualTo(Integer value) {
            addCriterion("logistics_user_id =", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdNotEqualTo(Integer value) {
            addCriterion("logistics_user_id <>", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdGreaterThan(Integer value) {
            addCriterion("logistics_user_id >", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("logistics_user_id >=", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdLessThan(Integer value) {
            addCriterion("logistics_user_id <", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdLessThanOrEqualTo(Integer value) {
            addCriterion("logistics_user_id <=", value, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdIn(List<Integer> values) {
            addCriterion("logistics_user_id in", values, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdNotIn(List<Integer> values) {
            addCriterion("logistics_user_id not in", values, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdBetween(Integer value1, Integer value2) {
            addCriterion("logistics_user_id between", value1, value2, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserIdNotBetween(Integer value1, Integer value2) {
            addCriterion("logistics_user_id not between", value1, value2, "logisticsUserId");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameIsNull() {
            addCriterion("logistics_user_name is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameIsNotNull() {
            addCriterion("logistics_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameEqualTo(String value) {
            addCriterion("logistics_user_name =", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameNotEqualTo(String value) {
            addCriterion("logistics_user_name <>", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameGreaterThan(String value) {
            addCriterion("logistics_user_name >", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_user_name >=", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameLessThan(String value) {
            addCriterion("logistics_user_name <", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameLessThanOrEqualTo(String value) {
            addCriterion("logistics_user_name <=", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameLike(String value) {
            addCriterion("logistics_user_name like", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameNotLike(String value) {
            addCriterion("logistics_user_name not like", value, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameIn(List<String> values) {
            addCriterion("logistics_user_name in", values, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameNotIn(List<String> values) {
            addCriterion("logistics_user_name not in", values, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameBetween(String value1, String value2) {
            addCriterion("logistics_user_name between", value1, value2, "logisticsUserName");
            return (Criteria) this;
        }

        public Criteria andLogisticsUserNameNotBetween(String value1, String value2) {
            addCriterion("logistics_user_name not between", value1, value2, "logisticsUserName");
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

        public Criteria andPidIsNull() {
            addCriterion("pid is null");
            return (Criteria) this;
        }

        public Criteria andPidIsNotNull() {
            addCriterion("pid is not null");
            return (Criteria) this;
        }

        public Criteria andPidEqualTo(Integer value) {
            addCriterion("pid =", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotEqualTo(Integer value) {
            addCriterion("pid <>", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThan(Integer value) {
            addCriterion("pid >", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidGreaterThanOrEqualTo(Integer value) {
            addCriterion("pid >=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThan(Integer value) {
            addCriterion("pid <", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidLessThanOrEqualTo(Integer value) {
            addCriterion("pid <=", value, "pid");
            return (Criteria) this;
        }

        public Criteria andPidIn(List<Integer> values) {
            addCriterion("pid in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotIn(List<Integer> values) {
            addCriterion("pid not in", values, "pid");
            return (Criteria) this;
        }

        public Criteria andPidBetween(Integer value1, Integer value2) {
            addCriterion("pid between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andPidNotBetween(Integer value1, Integer value2) {
            addCriterion("pid not between", value1, value2, "pid");
            return (Criteria) this;
        }

        public Criteria andOutCheckIsNull() {
            addCriterion("out_check is null");
            return (Criteria) this;
        }

        public Criteria andOutCheckIsNotNull() {
            addCriterion("out_check is not null");
            return (Criteria) this;
        }

        public Criteria andOutCheckEqualTo(Integer value) {
            addCriterion("out_check =", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotEqualTo(Integer value) {
            addCriterion("out_check <>", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThan(Integer value) {
            addCriterion("out_check >", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_check >=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThan(Integer value) {
            addCriterion("out_check <", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckLessThanOrEqualTo(Integer value) {
            addCriterion("out_check <=", value, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckIn(List<Integer> values) {
            addCriterion("out_check in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotIn(List<Integer> values) {
            addCriterion("out_check not in", values, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckBetween(Integer value1, Integer value2) {
            addCriterion("out_check between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutCheckNotBetween(Integer value1, Integer value2) {
            addCriterion("out_check not between", value1, value2, "outCheck");
            return (Criteria) this;
        }

        public Criteria andOutYnIsNull() {
            addCriterion("out_yn is null");
            return (Criteria) this;
        }

        public Criteria andOutYnIsNotNull() {
            addCriterion("out_yn is not null");
            return (Criteria) this;
        }

        public Criteria andOutYnEqualTo(Integer value) {
            addCriterion("out_yn =", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnNotEqualTo(Integer value) {
            addCriterion("out_yn <>", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnGreaterThan(Integer value) {
            addCriterion("out_yn >", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnGreaterThanOrEqualTo(Integer value) {
            addCriterion("out_yn >=", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnLessThan(Integer value) {
            addCriterion("out_yn <", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnLessThanOrEqualTo(Integer value) {
            addCriterion("out_yn <=", value, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnIn(List<Integer> values) {
            addCriterion("out_yn in", values, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnNotIn(List<Integer> values) {
            addCriterion("out_yn not in", values, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnBetween(Integer value1, Integer value2) {
            addCriterion("out_yn between", value1, value2, "outYn");
            return (Criteria) this;
        }

        public Criteria andOutYnNotBetween(Integer value1, Integer value2) {
            addCriterion("out_yn not between", value1, value2, "outYn");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIsNull() {
            addCriterion("submenu_name is null");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIsNotNull() {
            addCriterion("submenu_name is not null");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameEqualTo(String value) {
            addCriterion("submenu_name =", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotEqualTo(String value) {
            addCriterion("submenu_name <>", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameGreaterThan(String value) {
            addCriterion("submenu_name >", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameGreaterThanOrEqualTo(String value) {
            addCriterion("submenu_name >=", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLessThan(String value) {
            addCriterion("submenu_name <", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLessThanOrEqualTo(String value) {
            addCriterion("submenu_name <=", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameLike(String value) {
            addCriterion("submenu_name like", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotLike(String value) {
            addCriterion("submenu_name not like", value, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameIn(List<String> values) {
            addCriterion("submenu_name in", values, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotIn(List<String> values) {
            addCriterion("submenu_name not in", values, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameBetween(String value1, String value2) {
            addCriterion("submenu_name between", value1, value2, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuNameNotBetween(String value1, String value2) {
            addCriterion("submenu_name not between", value1, value2, "submenuName");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIsNull() {
            addCriterion("submenu_id is null");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIsNotNull() {
            addCriterion("submenu_id is not null");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdEqualTo(Integer value) {
            addCriterion("submenu_id =", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotEqualTo(Integer value) {
            addCriterion("submenu_id <>", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdGreaterThan(Integer value) {
            addCriterion("submenu_id >", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("submenu_id >=", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdLessThan(Integer value) {
            addCriterion("submenu_id <", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdLessThanOrEqualTo(Integer value) {
            addCriterion("submenu_id <=", value, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdIn(List<Integer> values) {
            addCriterion("submenu_id in", values, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotIn(List<Integer> values) {
            addCriterion("submenu_id not in", values, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdBetween(Integer value1, Integer value2) {
            addCriterion("submenu_id between", value1, value2, "submenuId");
            return (Criteria) this;
        }

        public Criteria andSubmenuIdNotBetween(Integer value1, Integer value2) {
            addCriterion("submenu_id not between", value1, value2, "submenuId");
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