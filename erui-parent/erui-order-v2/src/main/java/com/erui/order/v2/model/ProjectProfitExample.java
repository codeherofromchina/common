package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProjectProfitExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ProjectProfitExample() {
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

        public Criteria andCountryIsNull() {
            addCriterion("country is null");
            return (Criteria) this;
        }

        public Criteria andCountryIsNotNull() {
            addCriterion("country is not null");
            return (Criteria) this;
        }

        public Criteria andCountryEqualTo(String value) {
            addCriterion("country =", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotEqualTo(String value) {
            addCriterion("country <>", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThan(String value) {
            addCriterion("country >", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryGreaterThanOrEqualTo(String value) {
            addCriterion("country >=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThan(String value) {
            addCriterion("country <", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLessThanOrEqualTo(String value) {
            addCriterion("country <=", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryLike(String value) {
            addCriterion("country like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotLike(String value) {
            addCriterion("country not like", value, "country");
            return (Criteria) this;
        }

        public Criteria andCountryIn(List<String> values) {
            addCriterion("country in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotIn(List<String> values) {
            addCriterion("country not in", values, "country");
            return (Criteria) this;
        }

        public Criteria andCountryBetween(String value1, String value2) {
            addCriterion("country between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andCountryNotBetween(String value1, String value2) {
            addCriterion("country not between", value1, value2, "country");
            return (Criteria) this;
        }

        public Criteria andTradeTermIsNull() {
            addCriterion("trade_term is null");
            return (Criteria) this;
        }

        public Criteria andTradeTermIsNotNull() {
            addCriterion("trade_term is not null");
            return (Criteria) this;
        }

        public Criteria andTradeTermEqualTo(String value) {
            addCriterion("trade_term =", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermNotEqualTo(String value) {
            addCriterion("trade_term <>", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermGreaterThan(String value) {
            addCriterion("trade_term >", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermGreaterThanOrEqualTo(String value) {
            addCriterion("trade_term >=", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermLessThan(String value) {
            addCriterion("trade_term <", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermLessThanOrEqualTo(String value) {
            addCriterion("trade_term <=", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermLike(String value) {
            addCriterion("trade_term like", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermNotLike(String value) {
            addCriterion("trade_term not like", value, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermIn(List<String> values) {
            addCriterion("trade_term in", values, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermNotIn(List<String> values) {
            addCriterion("trade_term not in", values, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermBetween(String value1, String value2) {
            addCriterion("trade_term between", value1, value2, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andTradeTermNotBetween(String value1, String value2) {
            addCriterion("trade_term not between", value1, value2, "tradeTerm");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNull() {
            addCriterion("project_type is null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIsNotNull() {
            addCriterion("project_type is not null");
            return (Criteria) this;
        }

        public Criteria andProjectTypeEqualTo(String value) {
            addCriterion("project_type =", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotEqualTo(String value) {
            addCriterion("project_type <>", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThan(String value) {
            addCriterion("project_type >", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeGreaterThanOrEqualTo(String value) {
            addCriterion("project_type >=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThan(String value) {
            addCriterion("project_type <", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLessThanOrEqualTo(String value) {
            addCriterion("project_type <=", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeLike(String value) {
            addCriterion("project_type like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotLike(String value) {
            addCriterion("project_type not like", value, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeIn(List<String> values) {
            addCriterion("project_type in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotIn(List<String> values) {
            addCriterion("project_type not in", values, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeBetween(String value1, String value2) {
            addCriterion("project_type between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andProjectTypeNotBetween(String value1, String value2) {
            addCriterion("project_type not between", value1, value2, "projectType");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdIsNull() {
            addCriterion("contract_amount_usd is null");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdIsNotNull() {
            addCriterion("contract_amount_usd is not null");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdEqualTo(BigDecimal value) {
            addCriterion("contract_amount_usd =", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdNotEqualTo(BigDecimal value) {
            addCriterion("contract_amount_usd <>", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdGreaterThan(BigDecimal value) {
            addCriterion("contract_amount_usd >", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount_usd >=", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdLessThan(BigDecimal value) {
            addCriterion("contract_amount_usd <", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount_usd <=", value, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdIn(List<BigDecimal> values) {
            addCriterion("contract_amount_usd in", values, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdNotIn(List<BigDecimal> values) {
            addCriterion("contract_amount_usd not in", values, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount_usd between", value1, value2, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountUsdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount_usd not between", value1, value2, "contractAmountUsd");
            return (Criteria) this;
        }

        public Criteria andContractAmountIsNull() {
            addCriterion("contract_amount is null");
            return (Criteria) this;
        }

        public Criteria andContractAmountIsNotNull() {
            addCriterion("contract_amount is not null");
            return (Criteria) this;
        }

        public Criteria andContractAmountEqualTo(BigDecimal value) {
            addCriterion("contract_amount =", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotEqualTo(BigDecimal value) {
            addCriterion("contract_amount <>", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountGreaterThan(BigDecimal value) {
            addCriterion("contract_amount >", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount >=", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountLessThan(BigDecimal value) {
            addCriterion("contract_amount <", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("contract_amount <=", value, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountIn(List<BigDecimal> values) {
            addCriterion("contract_amount in", values, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotIn(List<BigDecimal> values) {
            addCriterion("contract_amount not in", values, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount between", value1, value2, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andContractAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("contract_amount not between", value1, value2, "contractAmount");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNull() {
            addCriterion("exchange_rate is null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIsNotNull() {
            addCriterion("exchange_rate is not null");
            return (Criteria) this;
        }

        public Criteria andExchangeRateEqualTo(BigDecimal value) {
            addCriterion("exchange_rate =", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <>", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThan(BigDecimal value) {
            addCriterion("exchange_rate >", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate >=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThan(BigDecimal value) {
            addCriterion("exchange_rate <", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("exchange_rate <=", value, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateIn(List<BigDecimal> values) {
            addCriterion("exchange_rate in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotIn(List<BigDecimal> values) {
            addCriterion("exchange_rate not in", values, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andExchangeRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("exchange_rate not between", value1, value2, "exchangeRate");
            return (Criteria) this;
        }

        public Criteria andTaxRefundIsNull() {
            addCriterion("tax_refund is null");
            return (Criteria) this;
        }

        public Criteria andTaxRefundIsNotNull() {
            addCriterion("tax_refund is not null");
            return (Criteria) this;
        }

        public Criteria andTaxRefundEqualTo(BigDecimal value) {
            addCriterion("tax_refund =", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundNotEqualTo(BigDecimal value) {
            addCriterion("tax_refund <>", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundGreaterThan(BigDecimal value) {
            addCriterion("tax_refund >", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_refund >=", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundLessThan(BigDecimal value) {
            addCriterion("tax_refund <", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tax_refund <=", value, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundIn(List<BigDecimal> values) {
            addCriterion("tax_refund in", values, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundNotIn(List<BigDecimal> values) {
            addCriterion("tax_refund not in", values, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_refund between", value1, value2, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andTaxRefundNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tax_refund not between", value1, value2, "taxRefund");
            return (Criteria) this;
        }

        public Criteria andLandFreightIsNull() {
            addCriterion("land_freight is null");
            return (Criteria) this;
        }

        public Criteria andLandFreightIsNotNull() {
            addCriterion("land_freight is not null");
            return (Criteria) this;
        }

        public Criteria andLandFreightEqualTo(BigDecimal value) {
            addCriterion("land_freight =", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightNotEqualTo(BigDecimal value) {
            addCriterion("land_freight <>", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightGreaterThan(BigDecimal value) {
            addCriterion("land_freight >", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("land_freight >=", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightLessThan(BigDecimal value) {
            addCriterion("land_freight <", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("land_freight <=", value, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightIn(List<BigDecimal> values) {
            addCriterion("land_freight in", values, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightNotIn(List<BigDecimal> values) {
            addCriterion("land_freight not in", values, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("land_freight between", value1, value2, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandFreightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("land_freight not between", value1, value2, "landFreight");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceIsNull() {
            addCriterion("land_insurance is null");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceIsNotNull() {
            addCriterion("land_insurance is not null");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceEqualTo(BigDecimal value) {
            addCriterion("land_insurance =", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceNotEqualTo(BigDecimal value) {
            addCriterion("land_insurance <>", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceGreaterThan(BigDecimal value) {
            addCriterion("land_insurance >", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("land_insurance >=", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceLessThan(BigDecimal value) {
            addCriterion("land_insurance <", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("land_insurance <=", value, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceIn(List<BigDecimal> values) {
            addCriterion("land_insurance in", values, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceNotIn(List<BigDecimal> values) {
            addCriterion("land_insurance not in", values, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("land_insurance between", value1, value2, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andLandInsuranceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("land_insurance not between", value1, value2, "landInsurance");
            return (Criteria) this;
        }

        public Criteria andPortChargesIsNull() {
            addCriterion("port_charges is null");
            return (Criteria) this;
        }

        public Criteria andPortChargesIsNotNull() {
            addCriterion("port_charges is not null");
            return (Criteria) this;
        }

        public Criteria andPortChargesEqualTo(BigDecimal value) {
            addCriterion("port_charges =", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesNotEqualTo(BigDecimal value) {
            addCriterion("port_charges <>", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesGreaterThan(BigDecimal value) {
            addCriterion("port_charges >", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("port_charges >=", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesLessThan(BigDecimal value) {
            addCriterion("port_charges <", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("port_charges <=", value, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesIn(List<BigDecimal> values) {
            addCriterion("port_charges in", values, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesNotIn(List<BigDecimal> values) {
            addCriterion("port_charges not in", values, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("port_charges between", value1, value2, "portCharges");
            return (Criteria) this;
        }

        public Criteria andPortChargesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("port_charges not between", value1, value2, "portCharges");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeIsNull() {
            addCriterion("inspection_fee is null");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeIsNotNull() {
            addCriterion("inspection_fee is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeEqualTo(BigDecimal value) {
            addCriterion("inspection_fee =", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeNotEqualTo(BigDecimal value) {
            addCriterion("inspection_fee <>", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeGreaterThan(BigDecimal value) {
            addCriterion("inspection_fee >", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("inspection_fee >=", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeLessThan(BigDecimal value) {
            addCriterion("inspection_fee <", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("inspection_fee <=", value, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeIn(List<BigDecimal> values) {
            addCriterion("inspection_fee in", values, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeNotIn(List<BigDecimal> values) {
            addCriterion("inspection_fee not in", values, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("inspection_fee between", value1, value2, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInspectionFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("inspection_fee not between", value1, value2, "inspectionFee");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportIsNull() {
            addCriterion("international_transport is null");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportIsNotNull() {
            addCriterion("international_transport is not null");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportEqualTo(BigDecimal value) {
            addCriterion("international_transport =", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportNotEqualTo(BigDecimal value) {
            addCriterion("international_transport <>", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportGreaterThan(BigDecimal value) {
            addCriterion("international_transport >", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("international_transport >=", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportLessThan(BigDecimal value) {
            addCriterion("international_transport <", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportLessThanOrEqualTo(BigDecimal value) {
            addCriterion("international_transport <=", value, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportIn(List<BigDecimal> values) {
            addCriterion("international_transport in", values, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportNotIn(List<BigDecimal> values) {
            addCriterion("international_transport not in", values, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("international_transport between", value1, value2, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andInternationalTransportNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("international_transport not between", value1, value2, "internationalTransport");
            return (Criteria) this;
        }

        public Criteria andTariffIsNull() {
            addCriterion("tariff is null");
            return (Criteria) this;
        }

        public Criteria andTariffIsNotNull() {
            addCriterion("tariff is not null");
            return (Criteria) this;
        }

        public Criteria andTariffEqualTo(BigDecimal value) {
            addCriterion("tariff =", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffNotEqualTo(BigDecimal value) {
            addCriterion("tariff <>", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffGreaterThan(BigDecimal value) {
            addCriterion("tariff >", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("tariff >=", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffLessThan(BigDecimal value) {
            addCriterion("tariff <", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffLessThanOrEqualTo(BigDecimal value) {
            addCriterion("tariff <=", value, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffIn(List<BigDecimal> values) {
            addCriterion("tariff in", values, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffNotIn(List<BigDecimal> values) {
            addCriterion("tariff not in", values, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tariff between", value1, value2, "tariff");
            return (Criteria) this;
        }

        public Criteria andTariffNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("tariff not between", value1, value2, "tariff");
            return (Criteria) this;
        }

        public Criteria andVatIsNull() {
            addCriterion("vat is null");
            return (Criteria) this;
        }

        public Criteria andVatIsNotNull() {
            addCriterion("vat is not null");
            return (Criteria) this;
        }

        public Criteria andVatEqualTo(BigDecimal value) {
            addCriterion("vat =", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatNotEqualTo(BigDecimal value) {
            addCriterion("vat <>", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatGreaterThan(BigDecimal value) {
            addCriterion("vat >", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("vat >=", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatLessThan(BigDecimal value) {
            addCriterion("vat <", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatLessThanOrEqualTo(BigDecimal value) {
            addCriterion("vat <=", value, "vat");
            return (Criteria) this;
        }

        public Criteria andVatIn(List<BigDecimal> values) {
            addCriterion("vat in", values, "vat");
            return (Criteria) this;
        }

        public Criteria andVatNotIn(List<BigDecimal> values) {
            addCriterion("vat not in", values, "vat");
            return (Criteria) this;
        }

        public Criteria andVatBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat between", value1, value2, "vat");
            return (Criteria) this;
        }

        public Criteria andVatNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("vat not between", value1, value2, "vat");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeIsNull() {
            addCriterion("customs_clear_fee is null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeIsNotNull() {
            addCriterion("customs_clear_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeEqualTo(BigDecimal value) {
            addCriterion("customs_clear_fee =", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeNotEqualTo(BigDecimal value) {
            addCriterion("customs_clear_fee <>", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeGreaterThan(BigDecimal value) {
            addCriterion("customs_clear_fee >", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_clear_fee >=", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeLessThan(BigDecimal value) {
            addCriterion("customs_clear_fee <", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_clear_fee <=", value, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeIn(List<BigDecimal> values) {
            addCriterion("customs_clear_fee in", values, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeNotIn(List<BigDecimal> values) {
            addCriterion("customs_clear_fee not in", values, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_clear_fee between", value1, value2, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsClearFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_clear_fee not between", value1, value2, "customsClearFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeIsNull() {
            addCriterion("customs_agent_fee is null");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeIsNotNull() {
            addCriterion("customs_agent_fee is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeEqualTo(BigDecimal value) {
            addCriterion("customs_agent_fee =", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeNotEqualTo(BigDecimal value) {
            addCriterion("customs_agent_fee <>", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeGreaterThan(BigDecimal value) {
            addCriterion("customs_agent_fee >", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_agent_fee >=", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeLessThan(BigDecimal value) {
            addCriterion("customs_agent_fee <", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("customs_agent_fee <=", value, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeIn(List<BigDecimal> values) {
            addCriterion("customs_agent_fee in", values, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeNotIn(List<BigDecimal> values) {
            addCriterion("customs_agent_fee not in", values, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_agent_fee between", value1, value2, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andCustomsAgentFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("customs_agent_fee not between", value1, value2, "customsAgentFee");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceIsNull() {
            addCriterion("transportion_insurance is null");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceIsNotNull() {
            addCriterion("transportion_insurance is not null");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceEqualTo(BigDecimal value) {
            addCriterion("transportion_insurance =", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceNotEqualTo(BigDecimal value) {
            addCriterion("transportion_insurance <>", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceGreaterThan(BigDecimal value) {
            addCriterion("transportion_insurance >", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("transportion_insurance >=", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceLessThan(BigDecimal value) {
            addCriterion("transportion_insurance <", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("transportion_insurance <=", value, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceIn(List<BigDecimal> values) {
            addCriterion("transportion_insurance in", values, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceNotIn(List<BigDecimal> values) {
            addCriterion("transportion_insurance not in", values, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transportion_insurance between", value1, value2, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andTransportionInsuranceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("transportion_insurance not between", value1, value2, "transportionInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceIsNull() {
            addCriterion("export_credit_insurance is null");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceIsNotNull() {
            addCriterion("export_credit_insurance is not null");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceEqualTo(BigDecimal value) {
            addCriterion("export_credit_insurance =", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceNotEqualTo(BigDecimal value) {
            addCriterion("export_credit_insurance <>", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceGreaterThan(BigDecimal value) {
            addCriterion("export_credit_insurance >", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("export_credit_insurance >=", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceLessThan(BigDecimal value) {
            addCriterion("export_credit_insurance <", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("export_credit_insurance <=", value, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceIn(List<BigDecimal> values) {
            addCriterion("export_credit_insurance in", values, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceNotIn(List<BigDecimal> values) {
            addCriterion("export_credit_insurance not in", values, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("export_credit_insurance between", value1, value2, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andExportCreditInsuranceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("export_credit_insurance not between", value1, value2, "exportCreditInsurance");
            return (Criteria) this;
        }

        public Criteria andOtherCreditIsNull() {
            addCriterion("other_credit is null");
            return (Criteria) this;
        }

        public Criteria andOtherCreditIsNotNull() {
            addCriterion("other_credit is not null");
            return (Criteria) this;
        }

        public Criteria andOtherCreditEqualTo(String value) {
            addCriterion("other_credit =", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditNotEqualTo(String value) {
            addCriterion("other_credit <>", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditGreaterThan(String value) {
            addCriterion("other_credit >", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditGreaterThanOrEqualTo(String value) {
            addCriterion("other_credit >=", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditLessThan(String value) {
            addCriterion("other_credit <", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditLessThanOrEqualTo(String value) {
            addCriterion("other_credit <=", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditLike(String value) {
            addCriterion("other_credit like", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditNotLike(String value) {
            addCriterion("other_credit not like", value, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditIn(List<String> values) {
            addCriterion("other_credit in", values, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditNotIn(List<String> values) {
            addCriterion("other_credit not in", values, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditBetween(String value1, String value2) {
            addCriterion("other_credit between", value1, value2, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andOtherCreditNotBetween(String value1, String value2) {
            addCriterion("other_credit not between", value1, value2, "otherCredit");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesIsNull() {
            addCriterion("travel_expenses is null");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesIsNotNull() {
            addCriterion("travel_expenses is not null");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesEqualTo(BigDecimal value) {
            addCriterion("travel_expenses =", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesNotEqualTo(BigDecimal value) {
            addCriterion("travel_expenses <>", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesGreaterThan(BigDecimal value) {
            addCriterion("travel_expenses >", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("travel_expenses >=", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesLessThan(BigDecimal value) {
            addCriterion("travel_expenses <", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("travel_expenses <=", value, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesIn(List<BigDecimal> values) {
            addCriterion("travel_expenses in", values, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesNotIn(List<BigDecimal> values) {
            addCriterion("travel_expenses not in", values, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("travel_expenses between", value1, value2, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andTravelExpensesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("travel_expenses not between", value1, value2, "travelExpenses");
            return (Criteria) this;
        }

        public Criteria andProjectCostIsNull() {
            addCriterion("project_cost is null");
            return (Criteria) this;
        }

        public Criteria andProjectCostIsNotNull() {
            addCriterion("project_cost is not null");
            return (Criteria) this;
        }

        public Criteria andProjectCostEqualTo(BigDecimal value) {
            addCriterion("project_cost =", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostNotEqualTo(BigDecimal value) {
            addCriterion("project_cost <>", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostGreaterThan(BigDecimal value) {
            addCriterion("project_cost >", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("project_cost >=", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostLessThan(BigDecimal value) {
            addCriterion("project_cost <", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("project_cost <=", value, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostIn(List<BigDecimal> values) {
            addCriterion("project_cost in", values, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostNotIn(List<BigDecimal> values) {
            addCriterion("project_cost not in", values, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("project_cost between", value1, value2, "projectCost");
            return (Criteria) this;
        }

        public Criteria andProjectCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("project_cost not between", value1, value2, "projectCost");
            return (Criteria) this;
        }

        public Criteria andGrossProfitIsNull() {
            addCriterion("gross_profit is null");
            return (Criteria) this;
        }

        public Criteria andGrossProfitIsNotNull() {
            addCriterion("gross_profit is not null");
            return (Criteria) this;
        }

        public Criteria andGrossProfitEqualTo(BigDecimal value) {
            addCriterion("gross_profit =", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitNotEqualTo(BigDecimal value) {
            addCriterion("gross_profit <>", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitGreaterThan(BigDecimal value) {
            addCriterion("gross_profit >", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_profit >=", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitLessThan(BigDecimal value) {
            addCriterion("gross_profit <", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_profit <=", value, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitIn(List<BigDecimal> values) {
            addCriterion("gross_profit in", values, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitNotIn(List<BigDecimal> values) {
            addCriterion("gross_profit not in", values, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_profit between", value1, value2, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_profit not between", value1, value2, "grossProfit");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginIsNull() {
            addCriterion("gross_profit_margin is null");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginIsNotNull() {
            addCriterion("gross_profit_margin is not null");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginEqualTo(BigDecimal value) {
            addCriterion("gross_profit_margin =", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginNotEqualTo(BigDecimal value) {
            addCriterion("gross_profit_margin <>", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginGreaterThan(BigDecimal value) {
            addCriterion("gross_profit_margin >", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_profit_margin >=", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginLessThan(BigDecimal value) {
            addCriterion("gross_profit_margin <", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("gross_profit_margin <=", value, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginIn(List<BigDecimal> values) {
            addCriterion("gross_profit_margin in", values, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginNotIn(List<BigDecimal> values) {
            addCriterion("gross_profit_margin not in", values, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_profit_margin between", value1, value2, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andGrossProfitMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("gross_profit_margin not between", value1, value2, "grossProfitMargin");
            return (Criteria) this;
        }

        public Criteria andAgentFeeIsNull() {
            addCriterion("agent_fee is null");
            return (Criteria) this;
        }

        public Criteria andAgentFeeIsNotNull() {
            addCriterion("agent_fee is not null");
            return (Criteria) this;
        }

        public Criteria andAgentFeeEqualTo(BigDecimal value) {
            addCriterion("agent_fee =", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeNotEqualTo(BigDecimal value) {
            addCriterion("agent_fee <>", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeGreaterThan(BigDecimal value) {
            addCriterion("agent_fee >", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("agent_fee >=", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeLessThan(BigDecimal value) {
            addCriterion("agent_fee <", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("agent_fee <=", value, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeIn(List<BigDecimal> values) {
            addCriterion("agent_fee in", values, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeNotIn(List<BigDecimal> values) {
            addCriterion("agent_fee not in", values, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agent_fee between", value1, value2, "agentFee");
            return (Criteria) this;
        }

        public Criteria andAgentFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("agent_fee not between", value1, value2, "agentFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeIsNull() {
            addCriterion("raise_fee is null");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeIsNotNull() {
            addCriterion("raise_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeEqualTo(BigDecimal value) {
            addCriterion("raise_fee =", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeNotEqualTo(BigDecimal value) {
            addCriterion("raise_fee <>", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeGreaterThan(BigDecimal value) {
            addCriterion("raise_fee >", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("raise_fee >=", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeLessThan(BigDecimal value) {
            addCriterion("raise_fee <", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("raise_fee <=", value, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeIn(List<BigDecimal> values) {
            addCriterion("raise_fee in", values, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeNotIn(List<BigDecimal> values) {
            addCriterion("raise_fee not in", values, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("raise_fee between", value1, value2, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andRaiseFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("raise_fee not between", value1, value2, "raiseFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeIsNull() {
            addCriterion("guarance_fee is null");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeIsNotNull() {
            addCriterion("guarance_fee is not null");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeEqualTo(BigDecimal value) {
            addCriterion("guarance_fee =", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeNotEqualTo(BigDecimal value) {
            addCriterion("guarance_fee <>", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeGreaterThan(BigDecimal value) {
            addCriterion("guarance_fee >", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("guarance_fee >=", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeLessThan(BigDecimal value) {
            addCriterion("guarance_fee <", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("guarance_fee <=", value, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeIn(List<BigDecimal> values) {
            addCriterion("guarance_fee in", values, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeNotIn(List<BigDecimal> values) {
            addCriterion("guarance_fee not in", values, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarance_fee between", value1, value2, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andGuaranceFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("guarance_fee not between", value1, value2, "guaranceFee");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestIsNull() {
            addCriterion("financing_interest is null");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestIsNotNull() {
            addCriterion("financing_interest is not null");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestEqualTo(BigDecimal value) {
            addCriterion("financing_interest =", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestNotEqualTo(BigDecimal value) {
            addCriterion("financing_interest <>", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestGreaterThan(BigDecimal value) {
            addCriterion("financing_interest >", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("financing_interest >=", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestLessThan(BigDecimal value) {
            addCriterion("financing_interest <", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestLessThanOrEqualTo(BigDecimal value) {
            addCriterion("financing_interest <=", value, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestIn(List<BigDecimal> values) {
            addCriterion("financing_interest in", values, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestNotIn(List<BigDecimal> values) {
            addCriterion("financing_interest not in", values, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financing_interest between", value1, value2, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andFinancingInterestNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("financing_interest not between", value1, value2, "financingInterest");
            return (Criteria) this;
        }

        public Criteria andBankFeesIsNull() {
            addCriterion("bank_fees is null");
            return (Criteria) this;
        }

        public Criteria andBankFeesIsNotNull() {
            addCriterion("bank_fees is not null");
            return (Criteria) this;
        }

        public Criteria andBankFeesEqualTo(BigDecimal value) {
            addCriterion("bank_fees =", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesNotEqualTo(BigDecimal value) {
            addCriterion("bank_fees <>", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesGreaterThan(BigDecimal value) {
            addCriterion("bank_fees >", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_fees >=", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesLessThan(BigDecimal value) {
            addCriterion("bank_fees <", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("bank_fees <=", value, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesIn(List<BigDecimal> values) {
            addCriterion("bank_fees in", values, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesNotIn(List<BigDecimal> values) {
            addCriterion("bank_fees not in", values, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_fees between", value1, value2, "bankFees");
            return (Criteria) this;
        }

        public Criteria andBankFeesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("bank_fees not between", value1, value2, "bankFees");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsIsNull() {
            addCriterion("domestic_taxs is null");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsIsNotNull() {
            addCriterion("domestic_taxs is not null");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsEqualTo(BigDecimal value) {
            addCriterion("domestic_taxs =", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsNotEqualTo(BigDecimal value) {
            addCriterion("domestic_taxs <>", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsGreaterThan(BigDecimal value) {
            addCriterion("domestic_taxs >", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("domestic_taxs >=", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsLessThan(BigDecimal value) {
            addCriterion("domestic_taxs <", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("domestic_taxs <=", value, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsIn(List<BigDecimal> values) {
            addCriterion("domestic_taxs in", values, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsNotIn(List<BigDecimal> values) {
            addCriterion("domestic_taxs not in", values, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("domestic_taxs between", value1, value2, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andDomesticTaxsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("domestic_taxs not between", value1, value2, "domesticTaxs");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesIsNull() {
            addCriterion("foreign_taxes is null");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesIsNotNull() {
            addCriterion("foreign_taxes is not null");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesEqualTo(BigDecimal value) {
            addCriterion("foreign_taxes =", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesNotEqualTo(BigDecimal value) {
            addCriterion("foreign_taxes <>", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesGreaterThan(BigDecimal value) {
            addCriterion("foreign_taxes >", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("foreign_taxes >=", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesLessThan(BigDecimal value) {
            addCriterion("foreign_taxes <", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("foreign_taxes <=", value, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesIn(List<BigDecimal> values) {
            addCriterion("foreign_taxes in", values, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesNotIn(List<BigDecimal> values) {
            addCriterion("foreign_taxes not in", values, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("foreign_taxes between", value1, value2, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andForeignTaxesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("foreign_taxes not between", value1, value2, "foreignTaxes");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNull() {
            addCriterion("manage_fee is null");
            return (Criteria) this;
        }

        public Criteria andManageFeeIsNotNull() {
            addCriterion("manage_fee is not null");
            return (Criteria) this;
        }

        public Criteria andManageFeeEqualTo(BigDecimal value) {
            addCriterion("manage_fee =", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotEqualTo(BigDecimal value) {
            addCriterion("manage_fee <>", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThan(BigDecimal value) {
            addCriterion("manage_fee >", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_fee >=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThan(BigDecimal value) {
            addCriterion("manage_fee <", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("manage_fee <=", value, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeIn(List<BigDecimal> values) {
            addCriterion("manage_fee in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotIn(List<BigDecimal> values) {
            addCriterion("manage_fee not in", values, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_fee between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andManageFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manage_fee not between", value1, value2, "manageFee");
            return (Criteria) this;
        }

        public Criteria andAfterProfitIsNull() {
            addCriterion("after_profit is null");
            return (Criteria) this;
        }

        public Criteria andAfterProfitIsNotNull() {
            addCriterion("after_profit is not null");
            return (Criteria) this;
        }

        public Criteria andAfterProfitEqualTo(BigDecimal value) {
            addCriterion("after_profit =", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitNotEqualTo(BigDecimal value) {
            addCriterion("after_profit <>", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitGreaterThan(BigDecimal value) {
            addCriterion("after_profit >", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("after_profit >=", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitLessThan(BigDecimal value) {
            addCriterion("after_profit <", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("after_profit <=", value, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitIn(List<BigDecimal> values) {
            addCriterion("after_profit in", values, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitNotIn(List<BigDecimal> values) {
            addCriterion("after_profit not in", values, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_profit between", value1, value2, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andAfterProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("after_profit not between", value1, value2, "afterProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitIsNull() {
            addCriterion("before_profit is null");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitIsNotNull() {
            addCriterion("before_profit is not null");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitEqualTo(BigDecimal value) {
            addCriterion("before_profit =", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitNotEqualTo(BigDecimal value) {
            addCriterion("before_profit <>", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitGreaterThan(BigDecimal value) {
            addCriterion("before_profit >", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("before_profit >=", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitLessThan(BigDecimal value) {
            addCriterion("before_profit <", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("before_profit <=", value, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitIn(List<BigDecimal> values) {
            addCriterion("before_profit in", values, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitNotIn(List<BigDecimal> values) {
            addCriterion("before_profit not in", values, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_profit between", value1, value2, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andBeforeProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("before_profit not between", value1, value2, "beforeProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitIsNull() {
            addCriterion("quotation_profit is null");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitIsNotNull() {
            addCriterion("quotation_profit is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitEqualTo(BigDecimal value) {
            addCriterion("quotation_profit =", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitNotEqualTo(BigDecimal value) {
            addCriterion("quotation_profit <>", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitGreaterThan(BigDecimal value) {
            addCriterion("quotation_profit >", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quotation_profit >=", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitLessThan(BigDecimal value) {
            addCriterion("quotation_profit <", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quotation_profit <=", value, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitIn(List<BigDecimal> values) {
            addCriterion("quotation_profit in", values, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitNotIn(List<BigDecimal> values) {
            addCriterion("quotation_profit not in", values, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quotation_profit between", value1, value2, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andQuotationProfitNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quotation_profit not between", value1, value2, "quotationProfit");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDIsNull() {
            addCriterion("purchasing_costs_d is null");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDIsNotNull() {
            addCriterion("purchasing_costs_d is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_d =", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDNotEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_d <>", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDGreaterThan(BigDecimal value) {
            addCriterion("purchasing_costs_d >", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_d >=", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDLessThan(BigDecimal value) {
            addCriterion("purchasing_costs_d <", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_d <=", value, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDIn(List<BigDecimal> values) {
            addCriterion("purchasing_costs_d in", values, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDNotIn(List<BigDecimal> values) {
            addCriterion("purchasing_costs_d not in", values, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchasing_costs_d between", value1, value2, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsDNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchasing_costs_d not between", value1, value2, "purchasingCostsD");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFIsNull() {
            addCriterion("purchasing_costs_f is null");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFIsNotNull() {
            addCriterion("purchasing_costs_f is not null");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_f =", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFNotEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_f <>", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFGreaterThan(BigDecimal value) {
            addCriterion("purchasing_costs_f >", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_f >=", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFLessThan(BigDecimal value) {
            addCriterion("purchasing_costs_f <", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFLessThanOrEqualTo(BigDecimal value) {
            addCriterion("purchasing_costs_f <=", value, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFIn(List<BigDecimal> values) {
            addCriterion("purchasing_costs_f in", values, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFNotIn(List<BigDecimal> values) {
            addCriterion("purchasing_costs_f not in", values, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchasing_costs_f between", value1, value2, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andPurchasingCostsFNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("purchasing_costs_f not between", value1, value2, "purchasingCostsF");
            return (Criteria) this;
        }

        public Criteria andDirectLaborIsNull() {
            addCriterion("direct_labor is null");
            return (Criteria) this;
        }

        public Criteria andDirectLaborIsNotNull() {
            addCriterion("direct_labor is not null");
            return (Criteria) this;
        }

        public Criteria andDirectLaborEqualTo(BigDecimal value) {
            addCriterion("direct_labor =", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborNotEqualTo(BigDecimal value) {
            addCriterion("direct_labor <>", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborGreaterThan(BigDecimal value) {
            addCriterion("direct_labor >", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("direct_labor >=", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborLessThan(BigDecimal value) {
            addCriterion("direct_labor <", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborLessThanOrEqualTo(BigDecimal value) {
            addCriterion("direct_labor <=", value, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborIn(List<BigDecimal> values) {
            addCriterion("direct_labor in", values, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborNotIn(List<BigDecimal> values) {
            addCriterion("direct_labor not in", values, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("direct_labor between", value1, value2, "directLabor");
            return (Criteria) this;
        }

        public Criteria andDirectLaborNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("direct_labor not between", value1, value2, "directLabor");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsIsNull() {
            addCriterion("manufacturing_costs is null");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsIsNotNull() {
            addCriterion("manufacturing_costs is not null");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsEqualTo(BigDecimal value) {
            addCriterion("manufacturing_costs =", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsNotEqualTo(BigDecimal value) {
            addCriterion("manufacturing_costs <>", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsGreaterThan(BigDecimal value) {
            addCriterion("manufacturing_costs >", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("manufacturing_costs >=", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsLessThan(BigDecimal value) {
            addCriterion("manufacturing_costs <", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("manufacturing_costs <=", value, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsIn(List<BigDecimal> values) {
            addCriterion("manufacturing_costs in", values, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsNotIn(List<BigDecimal> values) {
            addCriterion("manufacturing_costs not in", values, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manufacturing_costs between", value1, value2, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andManufacturingCostsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("manufacturing_costs not between", value1, value2, "manufacturingCosts");
            return (Criteria) this;
        }

        public Criteria andRaiseRateIsNull() {
            addCriterion("raise_rate is null");
            return (Criteria) this;
        }

        public Criteria andRaiseRateIsNotNull() {
            addCriterion("raise_rate is not null");
            return (Criteria) this;
        }

        public Criteria andRaiseRateEqualTo(BigDecimal value) {
            addCriterion("raise_rate =", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateNotEqualTo(BigDecimal value) {
            addCriterion("raise_rate <>", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateGreaterThan(BigDecimal value) {
            addCriterion("raise_rate >", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("raise_rate >=", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateLessThan(BigDecimal value) {
            addCriterion("raise_rate <", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateLessThanOrEqualTo(BigDecimal value) {
            addCriterion("raise_rate <=", value, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateIn(List<BigDecimal> values) {
            addCriterion("raise_rate in", values, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateNotIn(List<BigDecimal> values) {
            addCriterion("raise_rate not in", values, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("raise_rate between", value1, value2, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andRaiseRateNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("raise_rate not between", value1, value2, "raiseRate");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostIsNull() {
            addCriterion("total_project_cost is null");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostIsNotNull() {
            addCriterion("total_project_cost is not null");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostEqualTo(BigDecimal value) {
            addCriterion("total_project_cost =", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostNotEqualTo(BigDecimal value) {
            addCriterion("total_project_cost <>", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostGreaterThan(BigDecimal value) {
            addCriterion("total_project_cost >", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_project_cost >=", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostLessThan(BigDecimal value) {
            addCriterion("total_project_cost <", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_project_cost <=", value, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostIn(List<BigDecimal> values) {
            addCriterion("total_project_cost in", values, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostNotIn(List<BigDecimal> values) {
            addCriterion("total_project_cost not in", values, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_project_cost between", value1, value2, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andTotalProjectCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_project_cost not between", value1, value2, "totalProjectCost");
            return (Criteria) this;
        }

        public Criteria andRearFeeIsNull() {
            addCriterion("rear_fee is null");
            return (Criteria) this;
        }

        public Criteria andRearFeeIsNotNull() {
            addCriterion("rear_fee is not null");
            return (Criteria) this;
        }

        public Criteria andRearFeeEqualTo(BigDecimal value) {
            addCriterion("rear_fee =", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeNotEqualTo(BigDecimal value) {
            addCriterion("rear_fee <>", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeGreaterThan(BigDecimal value) {
            addCriterion("rear_fee >", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("rear_fee >=", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeLessThan(BigDecimal value) {
            addCriterion("rear_fee <", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("rear_fee <=", value, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeIn(List<BigDecimal> values) {
            addCriterion("rear_fee in", values, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeNotIn(List<BigDecimal> values) {
            addCriterion("rear_fee not in", values, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rear_fee between", value1, value2, "rearFee");
            return (Criteria) this;
        }

        public Criteria andRearFeeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("rear_fee not between", value1, value2, "rearFee");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostIsNull() {
            addCriterion("platform_agent_cost is null");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostIsNotNull() {
            addCriterion("platform_agent_cost is not null");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostEqualTo(BigDecimal value) {
            addCriterion("platform_agent_cost =", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostNotEqualTo(BigDecimal value) {
            addCriterion("platform_agent_cost <>", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostGreaterThan(BigDecimal value) {
            addCriterion("platform_agent_cost >", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_agent_cost >=", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostLessThan(BigDecimal value) {
            addCriterion("platform_agent_cost <", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("platform_agent_cost <=", value, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostIn(List<BigDecimal> values) {
            addCriterion("platform_agent_cost in", values, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostNotIn(List<BigDecimal> values) {
            addCriterion("platform_agent_cost not in", values, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_agent_cost between", value1, value2, "platformAgentCost");
            return (Criteria) this;
        }

        public Criteria andPlatformAgentCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("platform_agent_cost not between", value1, value2, "platformAgentCost");
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