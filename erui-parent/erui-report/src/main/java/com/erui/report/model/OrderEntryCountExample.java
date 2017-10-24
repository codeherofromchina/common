package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderEntryCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OrderEntryCountExample() {
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

        public Criteria andEntryNumIsNull() {
            addCriterion("entry_num is null");
            return (Criteria) this;
        }

        public Criteria andEntryNumIsNotNull() {
            addCriterion("entry_num is not null");
            return (Criteria) this;
        }

        public Criteria andEntryNumEqualTo(String value) {
            addCriterion("entry_num =", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumNotEqualTo(String value) {
            addCriterion("entry_num <>", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumGreaterThan(String value) {
            addCriterion("entry_num >", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumGreaterThanOrEqualTo(String value) {
            addCriterion("entry_num >=", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumLessThan(String value) {
            addCriterion("entry_num <", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumLessThanOrEqualTo(String value) {
            addCriterion("entry_num <=", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumLike(String value) {
            addCriterion("entry_num like", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumNotLike(String value) {
            addCriterion("entry_num not like", value, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumIn(List<String> values) {
            addCriterion("entry_num in", values, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumNotIn(List<String> values) {
            addCriterion("entry_num not in", values, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumBetween(String value1, String value2) {
            addCriterion("entry_num between", value1, value2, "entryNum");
            return (Criteria) this;
        }

        public Criteria andEntryNumNotBetween(String value1, String value2) {
            addCriterion("entry_num not between", value1, value2, "entryNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNull() {
            addCriterion("contract_num is null");
            return (Criteria) this;
        }

        public Criteria andContractNumIsNotNull() {
            addCriterion("contract_num is not null");
            return (Criteria) this;
        }

        public Criteria andContractNumEqualTo(String value) {
            addCriterion("contract_num =", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotEqualTo(String value) {
            addCriterion("contract_num <>", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThan(String value) {
            addCriterion("contract_num >", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumGreaterThanOrEqualTo(String value) {
            addCriterion("contract_num >=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThan(String value) {
            addCriterion("contract_num <", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLessThanOrEqualTo(String value) {
            addCriterion("contract_num <=", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumLike(String value) {
            addCriterion("contract_num like", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotLike(String value) {
            addCriterion("contract_num not like", value, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumIn(List<String> values) {
            addCriterion("contract_num in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotIn(List<String> values) {
            addCriterion("contract_num not in", values, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumBetween(String value1, String value2) {
            addCriterion("contract_num between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andContractNumNotBetween(String value1, String value2) {
            addCriterion("contract_num not between", value1, value2, "contractNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNull() {
            addCriterion("execute_num is null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIsNotNull() {
            addCriterion("execute_num is not null");
            return (Criteria) this;
        }

        public Criteria andExecuteNumEqualTo(String value) {
            addCriterion("execute_num =", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotEqualTo(String value) {
            addCriterion("execute_num <>", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThan(String value) {
            addCriterion("execute_num >", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumGreaterThanOrEqualTo(String value) {
            addCriterion("execute_num >=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThan(String value) {
            addCriterion("execute_num <", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLessThanOrEqualTo(String value) {
            addCriterion("execute_num <=", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumLike(String value) {
            addCriterion("execute_num like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotLike(String value) {
            addCriterion("execute_num not like", value, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumIn(List<String> values) {
            addCriterion("execute_num in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotIn(List<String> values) {
            addCriterion("execute_num not in", values, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumBetween(String value1, String value2) {
            addCriterion("execute_num between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andExecuteNumNotBetween(String value1, String value2) {
            addCriterion("execute_num not between", value1, value2, "executeNum");
            return (Criteria) this;
        }

        public Criteria andSuppliNameIsNull() {
            addCriterion("suppli_name is null");
            return (Criteria) this;
        }

        public Criteria andSuppliNameIsNotNull() {
            addCriterion("suppli_name is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliNameEqualTo(String value) {
            addCriterion("suppli_name =", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameNotEqualTo(String value) {
            addCriterion("suppli_name <>", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameGreaterThan(String value) {
            addCriterion("suppli_name >", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameGreaterThanOrEqualTo(String value) {
            addCriterion("suppli_name >=", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameLessThan(String value) {
            addCriterion("suppli_name <", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameLessThanOrEqualTo(String value) {
            addCriterion("suppli_name <=", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameLike(String value) {
            addCriterion("suppli_name like", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameNotLike(String value) {
            addCriterion("suppli_name not like", value, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameIn(List<String> values) {
            addCriterion("suppli_name in", values, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameNotIn(List<String> values) {
            addCriterion("suppli_name not in", values, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameBetween(String value1, String value2) {
            addCriterion("suppli_name between", value1, value2, "suppliName");
            return (Criteria) this;
        }

        public Criteria andSuppliNameNotBetween(String value1, String value2) {
            addCriterion("suppli_name not between", value1, value2, "suppliName");
            return (Criteria) this;
        }

        public Criteria andBuyerIsNull() {
            addCriterion("buyer is null");
            return (Criteria) this;
        }

        public Criteria andBuyerIsNotNull() {
            addCriterion("buyer is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerEqualTo(String value) {
            addCriterion("buyer =", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerNotEqualTo(String value) {
            addCriterion("buyer <>", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerGreaterThan(String value) {
            addCriterion("buyer >", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerGreaterThanOrEqualTo(String value) {
            addCriterion("buyer >=", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerLessThan(String value) {
            addCriterion("buyer <", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerLessThanOrEqualTo(String value) {
            addCriterion("buyer <=", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerLike(String value) {
            addCriterion("buyer like", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerNotLike(String value) {
            addCriterion("buyer not like", value, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerIn(List<String> values) {
            addCriterion("buyer in", values, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerNotIn(List<String> values) {
            addCriterion("buyer not in", values, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerBetween(String value1, String value2) {
            addCriterion("buyer between", value1, value2, "buyer");
            return (Criteria) this;
        }

        public Criteria andBuyerNotBetween(String value1, String value2) {
            addCriterion("buyer not between", value1, value2, "buyer");
            return (Criteria) this;
        }

        public Criteria andEntryCountIsNull() {
            addCriterion("entry_count is null");
            return (Criteria) this;
        }

        public Criteria andEntryCountIsNotNull() {
            addCriterion("entry_count is not null");
            return (Criteria) this;
        }

        public Criteria andEntryCountEqualTo(Integer value) {
            addCriterion("entry_count =", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountNotEqualTo(Integer value) {
            addCriterion("entry_count <>", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountGreaterThan(Integer value) {
            addCriterion("entry_count >", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("entry_count >=", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountLessThan(Integer value) {
            addCriterion("entry_count <", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountLessThanOrEqualTo(Integer value) {
            addCriterion("entry_count <=", value, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountIn(List<Integer> values) {
            addCriterion("entry_count in", values, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountNotIn(List<Integer> values) {
            addCriterion("entry_count not in", values, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountBetween(Integer value1, Integer value2) {
            addCriterion("entry_count between", value1, value2, "entryCount");
            return (Criteria) this;
        }

        public Criteria andEntryCountNotBetween(Integer value1, Integer value2) {
            addCriterion("entry_count not between", value1, value2, "entryCount");
            return (Criteria) this;
        }

        public Criteria andAmountsIsNull() {
            addCriterion("amounts is null");
            return (Criteria) this;
        }

        public Criteria andAmountsIsNotNull() {
            addCriterion("amounts is not null");
            return (Criteria) this;
        }

        public Criteria andAmountsEqualTo(BigDecimal value) {
            addCriterion("amounts =", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsNotEqualTo(BigDecimal value) {
            addCriterion("amounts <>", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsGreaterThan(BigDecimal value) {
            addCriterion("amounts >", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("amounts >=", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsLessThan(BigDecimal value) {
            addCriterion("amounts <", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsLessThanOrEqualTo(BigDecimal value) {
            addCriterion("amounts <=", value, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsIn(List<BigDecimal> values) {
            addCriterion("amounts in", values, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsNotIn(List<BigDecimal> values) {
            addCriterion("amounts not in", values, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amounts between", value1, value2, "amounts");
            return (Criteria) this;
        }

        public Criteria andAmountsNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("amounts not between", value1, value2, "amounts");
            return (Criteria) this;
        }

        public Criteria andStorageDateIsNull() {
            addCriterion("storage_date is null");
            return (Criteria) this;
        }

        public Criteria andStorageDateIsNotNull() {
            addCriterion("storage_date is not null");
            return (Criteria) this;
        }

        public Criteria andStorageDateEqualTo(Date value) {
            addCriterion("storage_date =", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateNotEqualTo(Date value) {
            addCriterion("storage_date <>", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateGreaterThan(Date value) {
            addCriterion("storage_date >", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateGreaterThanOrEqualTo(Date value) {
            addCriterion("storage_date >=", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateLessThan(Date value) {
            addCriterion("storage_date <", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateLessThanOrEqualTo(Date value) {
            addCriterion("storage_date <=", value, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateIn(List<Date> values) {
            addCriterion("storage_date in", values, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateNotIn(List<Date> values) {
            addCriterion("storage_date not in", values, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateBetween(Date value1, Date value2) {
            addCriterion("storage_date between", value1, value2, "storageDate");
            return (Criteria) this;
        }

        public Criteria andStorageDateNotBetween(Date value1, Date value2) {
            addCriterion("storage_date not between", value1, value2, "storageDate");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
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