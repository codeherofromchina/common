package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SupplyChainReadExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SupplyChainReadExample() {
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

        public Criteria andAuditSuppliNumIsNull() {
            addCriterion("audit_suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumIsNotNull() {
            addCriterion("audit_suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumEqualTo(Integer value) {
            addCriterion("audit_suppli_num =", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumNotEqualTo(Integer value) {
            addCriterion("audit_suppli_num <>", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumGreaterThan(Integer value) {
            addCriterion("audit_suppli_num >", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_suppli_num >=", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumLessThan(Integer value) {
            addCriterion("audit_suppli_num <", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("audit_suppli_num <=", value, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumIn(List<Integer> values) {
            addCriterion("audit_suppli_num in", values, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumNotIn(List<Integer> values) {
            addCriterion("audit_suppli_num not in", values, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("audit_suppli_num between", value1, value2, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andAuditSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_suppli_num not between", value1, value2, "auditSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumIsNull() {
            addCriterion("pass_suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumIsNotNull() {
            addCriterion("pass_suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumEqualTo(Integer value) {
            addCriterion("pass_suppli_num =", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumNotEqualTo(Integer value) {
            addCriterion("pass_suppli_num <>", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumGreaterThan(Integer value) {
            addCriterion("pass_suppli_num >", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pass_suppli_num >=", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumLessThan(Integer value) {
            addCriterion("pass_suppli_num <", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("pass_suppli_num <=", value, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumIn(List<Integer> values) {
            addCriterion("pass_suppli_num in", values, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumNotIn(List<Integer> values) {
            addCriterion("pass_suppli_num not in", values, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("pass_suppli_num between", value1, value2, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andPassSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pass_suppli_num not between", value1, value2, "passSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumIsNull() {
            addCriterion("reject_suppli_num is null");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumIsNotNull() {
            addCriterion("reject_suppli_num is not null");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumEqualTo(Integer value) {
            addCriterion("reject_suppli_num =", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumNotEqualTo(Integer value) {
            addCriterion("reject_suppli_num <>", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumGreaterThan(Integer value) {
            addCriterion("reject_suppli_num >", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reject_suppli_num >=", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumLessThan(Integer value) {
            addCriterion("reject_suppli_num <", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumLessThanOrEqualTo(Integer value) {
            addCriterion("reject_suppli_num <=", value, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumIn(List<Integer> values) {
            addCriterion("reject_suppli_num in", values, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumNotIn(List<Integer> values) {
            addCriterion("reject_suppli_num not in", values, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumBetween(Integer value1, Integer value2) {
            addCriterion("reject_suppli_num between", value1, value2, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andRejectSuppliNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reject_suppli_num not between", value1, value2, "rejectSuppliNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumIsNull() {
            addCriterion("brand_num is null");
            return (Criteria) this;
        }

        public Criteria andBrandNumIsNotNull() {
            addCriterion("brand_num is not null");
            return (Criteria) this;
        }

        public Criteria andBrandNumEqualTo(Integer value) {
            addCriterion("brand_num =", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumNotEqualTo(Integer value) {
            addCriterion("brand_num <>", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumGreaterThan(Integer value) {
            addCriterion("brand_num >", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("brand_num >=", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumLessThan(Integer value) {
            addCriterion("brand_num <", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumLessThanOrEqualTo(Integer value) {
            addCriterion("brand_num <=", value, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumIn(List<Integer> values) {
            addCriterion("brand_num in", values, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumNotIn(List<Integer> values) {
            addCriterion("brand_num not in", values, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumBetween(Integer value1, Integer value2) {
            addCriterion("brand_num between", value1, value2, "brandNum");
            return (Criteria) this;
        }

        public Criteria andBrandNumNotBetween(Integer value1, Integer value2) {
            addCriterion("brand_num not between", value1, value2, "brandNum");
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

        public Criteria andTempoSpuNumIsNull() {
            addCriterion("tempo_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumIsNotNull() {
            addCriterion("tempo_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumEqualTo(Integer value) {
            addCriterion("tempo_spu_num =", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumNotEqualTo(Integer value) {
            addCriterion("tempo_spu_num <>", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumGreaterThan(Integer value) {
            addCriterion("tempo_spu_num >", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("tempo_spu_num >=", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumLessThan(Integer value) {
            addCriterion("tempo_spu_num <", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("tempo_spu_num <=", value, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumIn(List<Integer> values) {
            addCriterion("tempo_spu_num in", values, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumNotIn(List<Integer> values) {
            addCriterion("tempo_spu_num not in", values, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("tempo_spu_num between", value1, value2, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("tempo_spu_num not between", value1, value2, "tempoSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumIsNull() {
            addCriterion("audit_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumIsNotNull() {
            addCriterion("audit_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumEqualTo(Integer value) {
            addCriterion("audit_spu_num =", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumNotEqualTo(Integer value) {
            addCriterion("audit_spu_num <>", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumGreaterThan(Integer value) {
            addCriterion("audit_spu_num >", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_spu_num >=", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumLessThan(Integer value) {
            addCriterion("audit_spu_num <", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("audit_spu_num <=", value, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumIn(List<Integer> values) {
            addCriterion("audit_spu_num in", values, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumNotIn(List<Integer> values) {
            addCriterion("audit_spu_num not in", values, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("audit_spu_num between", value1, value2, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_spu_num not between", value1, value2, "auditSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumIsNull() {
            addCriterion("pass_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumIsNotNull() {
            addCriterion("pass_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumEqualTo(Integer value) {
            addCriterion("pass_spu_num =", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumNotEqualTo(Integer value) {
            addCriterion("pass_spu_num <>", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumGreaterThan(Integer value) {
            addCriterion("pass_spu_num >", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pass_spu_num >=", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumLessThan(Integer value) {
            addCriterion("pass_spu_num <", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("pass_spu_num <=", value, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumIn(List<Integer> values) {
            addCriterion("pass_spu_num in", values, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumNotIn(List<Integer> values) {
            addCriterion("pass_spu_num not in", values, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("pass_spu_num between", value1, value2, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andPassSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pass_spu_num not between", value1, value2, "passSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumIsNull() {
            addCriterion("reject_spu_num is null");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumIsNotNull() {
            addCriterion("reject_spu_num is not null");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumEqualTo(Integer value) {
            addCriterion("reject_spu_num =", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumNotEqualTo(Integer value) {
            addCriterion("reject_spu_num <>", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumGreaterThan(Integer value) {
            addCriterion("reject_spu_num >", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reject_spu_num >=", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumLessThan(Integer value) {
            addCriterion("reject_spu_num <", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumLessThanOrEqualTo(Integer value) {
            addCriterion("reject_spu_num <=", value, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumIn(List<Integer> values) {
            addCriterion("reject_spu_num in", values, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumNotIn(List<Integer> values) {
            addCriterion("reject_spu_num not in", values, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumBetween(Integer value1, Integer value2) {
            addCriterion("reject_spu_num between", value1, value2, "rejectSpuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSpuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reject_spu_num not between", value1, value2, "rejectSpuNum");
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

        public Criteria andTempoSkuNumIsNull() {
            addCriterion("tempo_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumIsNotNull() {
            addCriterion("tempo_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumEqualTo(Integer value) {
            addCriterion("tempo_sku_num =", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumNotEqualTo(Integer value) {
            addCriterion("tempo_sku_num <>", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumGreaterThan(Integer value) {
            addCriterion("tempo_sku_num >", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("tempo_sku_num >=", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumLessThan(Integer value) {
            addCriterion("tempo_sku_num <", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("tempo_sku_num <=", value, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumIn(List<Integer> values) {
            addCriterion("tempo_sku_num in", values, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumNotIn(List<Integer> values) {
            addCriterion("tempo_sku_num not in", values, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("tempo_sku_num between", value1, value2, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andTempoSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("tempo_sku_num not between", value1, value2, "tempoSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumIsNull() {
            addCriterion("audit_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumIsNotNull() {
            addCriterion("audit_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumEqualTo(Integer value) {
            addCriterion("audit_sku_num =", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumNotEqualTo(Integer value) {
            addCriterion("audit_sku_num <>", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumGreaterThan(Integer value) {
            addCriterion("audit_sku_num >", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("audit_sku_num >=", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumLessThan(Integer value) {
            addCriterion("audit_sku_num <", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("audit_sku_num <=", value, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumIn(List<Integer> values) {
            addCriterion("audit_sku_num in", values, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumNotIn(List<Integer> values) {
            addCriterion("audit_sku_num not in", values, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("audit_sku_num between", value1, value2, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andAuditSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("audit_sku_num not between", value1, value2, "auditSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumIsNull() {
            addCriterion("pass_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumIsNotNull() {
            addCriterion("pass_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumEqualTo(Integer value) {
            addCriterion("pass_sku_num =", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumNotEqualTo(Integer value) {
            addCriterion("pass_sku_num <>", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumGreaterThan(Integer value) {
            addCriterion("pass_sku_num >", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("pass_sku_num >=", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumLessThan(Integer value) {
            addCriterion("pass_sku_num <", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("pass_sku_num <=", value, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumIn(List<Integer> values) {
            addCriterion("pass_sku_num in", values, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumNotIn(List<Integer> values) {
            addCriterion("pass_sku_num not in", values, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("pass_sku_num between", value1, value2, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andPassSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("pass_sku_num not between", value1, value2, "passSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumIsNull() {
            addCriterion("reject_sku_num is null");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumIsNotNull() {
            addCriterion("reject_sku_num is not null");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumEqualTo(Integer value) {
            addCriterion("reject_sku_num =", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumNotEqualTo(Integer value) {
            addCriterion("reject_sku_num <>", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumGreaterThan(Integer value) {
            addCriterion("reject_sku_num >", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("reject_sku_num >=", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumLessThan(Integer value) {
            addCriterion("reject_sku_num <", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumLessThanOrEqualTo(Integer value) {
            addCriterion("reject_sku_num <=", value, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumIn(List<Integer> values) {
            addCriterion("reject_sku_num in", values, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumNotIn(List<Integer> values) {
            addCriterion("reject_sku_num not in", values, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumBetween(Integer value1, Integer value2) {
            addCriterion("reject_sku_num between", value1, value2, "rejectSkuNum");
            return (Criteria) this;
        }

        public Criteria andRejectSkuNumNotBetween(Integer value1, Integer value2) {
            addCriterion("reject_sku_num not between", value1, value2, "rejectSkuNum");
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