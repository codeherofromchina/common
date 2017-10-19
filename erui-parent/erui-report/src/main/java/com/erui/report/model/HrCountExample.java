package com.erui.report.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HrCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public HrCountExample() {
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

        public Criteria andDepartmentIsNull() {
            addCriterion("department is null");
            return (Criteria) this;
        }

        public Criteria andDepartmentIsNotNull() {
            addCriterion("department is not null");
            return (Criteria) this;
        }

        public Criteria andDepartmentEqualTo(String value) {
            addCriterion("department =", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotEqualTo(String value) {
            addCriterion("department <>", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThan(String value) {
            addCriterion("department >", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentGreaterThanOrEqualTo(String value) {
            addCriterion("department >=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThan(String value) {
            addCriterion("department <", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLessThanOrEqualTo(String value) {
            addCriterion("department <=", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentLike(String value) {
            addCriterion("department like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotLike(String value) {
            addCriterion("department not like", value, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentIn(List<String> values) {
            addCriterion("department in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotIn(List<String> values) {
            addCriterion("department not in", values, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentBetween(String value1, String value2) {
            addCriterion("department between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andDepartmentNotBetween(String value1, String value2) {
            addCriterion("department not between", value1, value2, "department");
            return (Criteria) this;
        }

        public Criteria andPlanCountIsNull() {
            addCriterion("plan_count is null");
            return (Criteria) this;
        }

        public Criteria andPlanCountIsNotNull() {
            addCriterion("plan_count is not null");
            return (Criteria) this;
        }

        public Criteria andPlanCountEqualTo(Integer value) {
            addCriterion("plan_count =", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountNotEqualTo(Integer value) {
            addCriterion("plan_count <>", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountGreaterThan(Integer value) {
            addCriterion("plan_count >", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("plan_count >=", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountLessThan(Integer value) {
            addCriterion("plan_count <", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountLessThanOrEqualTo(Integer value) {
            addCriterion("plan_count <=", value, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountIn(List<Integer> values) {
            addCriterion("plan_count in", values, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountNotIn(List<Integer> values) {
            addCriterion("plan_count not in", values, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountBetween(Integer value1, Integer value2) {
            addCriterion("plan_count between", value1, value2, "planCount");
            return (Criteria) this;
        }

        public Criteria andPlanCountNotBetween(Integer value1, Integer value2) {
            addCriterion("plan_count not between", value1, value2, "planCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountIsNull() {
            addCriterion("regular_count is null");
            return (Criteria) this;
        }

        public Criteria andRegularCountIsNotNull() {
            addCriterion("regular_count is not null");
            return (Criteria) this;
        }

        public Criteria andRegularCountEqualTo(Integer value) {
            addCriterion("regular_count =", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountNotEqualTo(Integer value) {
            addCriterion("regular_count <>", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountGreaterThan(Integer value) {
            addCriterion("regular_count >", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("regular_count >=", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountLessThan(Integer value) {
            addCriterion("regular_count <", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountLessThanOrEqualTo(Integer value) {
            addCriterion("regular_count <=", value, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountIn(List<Integer> values) {
            addCriterion("regular_count in", values, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountNotIn(List<Integer> values) {
            addCriterion("regular_count not in", values, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountBetween(Integer value1, Integer value2) {
            addCriterion("regular_count between", value1, value2, "regularCount");
            return (Criteria) this;
        }

        public Criteria andRegularCountNotBetween(Integer value1, Integer value2) {
            addCriterion("regular_count not between", value1, value2, "regularCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountIsNull() {
            addCriterion("china_count is null");
            return (Criteria) this;
        }

        public Criteria andChinaCountIsNotNull() {
            addCriterion("china_count is not null");
            return (Criteria) this;
        }

        public Criteria andChinaCountEqualTo(Integer value) {
            addCriterion("china_count =", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountNotEqualTo(Integer value) {
            addCriterion("china_count <>", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountGreaterThan(Integer value) {
            addCriterion("china_count >", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("china_count >=", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountLessThan(Integer value) {
            addCriterion("china_count <", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountLessThanOrEqualTo(Integer value) {
            addCriterion("china_count <=", value, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountIn(List<Integer> values) {
            addCriterion("china_count in", values, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountNotIn(List<Integer> values) {
            addCriterion("china_count not in", values, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountBetween(Integer value1, Integer value2) {
            addCriterion("china_count between", value1, value2, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andChinaCountNotBetween(Integer value1, Integer value2) {
            addCriterion("china_count not between", value1, value2, "chinaCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountIsNull() {
            addCriterion("foreign_count is null");
            return (Criteria) this;
        }

        public Criteria andForeignCountIsNotNull() {
            addCriterion("foreign_count is not null");
            return (Criteria) this;
        }

        public Criteria andForeignCountEqualTo(Integer value) {
            addCriterion("foreign_count =", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountNotEqualTo(Integer value) {
            addCriterion("foreign_count <>", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountGreaterThan(Integer value) {
            addCriterion("foreign_count >", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("foreign_count >=", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountLessThan(Integer value) {
            addCriterion("foreign_count <", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountLessThanOrEqualTo(Integer value) {
            addCriterion("foreign_count <=", value, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountIn(List<Integer> values) {
            addCriterion("foreign_count in", values, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountNotIn(List<Integer> values) {
            addCriterion("foreign_count not in", values, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountBetween(Integer value1, Integer value2) {
            addCriterion("foreign_count between", value1, value2, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andForeignCountNotBetween(Integer value1, Integer value2) {
            addCriterion("foreign_count not between", value1, value2, "foreignCount");
            return (Criteria) this;
        }

        public Criteria andNewCountIsNull() {
            addCriterion("new_count is null");
            return (Criteria) this;
        }

        public Criteria andNewCountIsNotNull() {
            addCriterion("new_count is not null");
            return (Criteria) this;
        }

        public Criteria andNewCountEqualTo(Integer value) {
            addCriterion("new_count =", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountNotEqualTo(Integer value) {
            addCriterion("new_count <>", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountGreaterThan(Integer value) {
            addCriterion("new_count >", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("new_count >=", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountLessThan(Integer value) {
            addCriterion("new_count <", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountLessThanOrEqualTo(Integer value) {
            addCriterion("new_count <=", value, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountIn(List<Integer> values) {
            addCriterion("new_count in", values, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountNotIn(List<Integer> values) {
            addCriterion("new_count not in", values, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountBetween(Integer value1, Integer value2) {
            addCriterion("new_count between", value1, value2, "newCount");
            return (Criteria) this;
        }

        public Criteria andNewCountNotBetween(Integer value1, Integer value2) {
            addCriterion("new_count not between", value1, value2, "newCount");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInIsNull() {
            addCriterion("group_transfer_in is null");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInIsNotNull() {
            addCriterion("group_transfer_in is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInEqualTo(Integer value) {
            addCriterion("group_transfer_in =", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInNotEqualTo(Integer value) {
            addCriterion("group_transfer_in <>", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInGreaterThan(Integer value) {
            addCriterion("group_transfer_in >", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_transfer_in >=", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInLessThan(Integer value) {
            addCriterion("group_transfer_in <", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInLessThanOrEqualTo(Integer value) {
            addCriterion("group_transfer_in <=", value, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInIn(List<Integer> values) {
            addCriterion("group_transfer_in in", values, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInNotIn(List<Integer> values) {
            addCriterion("group_transfer_in not in", values, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInBetween(Integer value1, Integer value2) {
            addCriterion("group_transfer_in between", value1, value2, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferInNotBetween(Integer value1, Integer value2) {
            addCriterion("group_transfer_in not between", value1, value2, "groupTransferIn");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutIsNull() {
            addCriterion("group_transfer_out is null");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutIsNotNull() {
            addCriterion("group_transfer_out is not null");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutEqualTo(Integer value) {
            addCriterion("group_transfer_out =", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutNotEqualTo(Integer value) {
            addCriterion("group_transfer_out <>", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutGreaterThan(Integer value) {
            addCriterion("group_transfer_out >", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutGreaterThanOrEqualTo(Integer value) {
            addCriterion("group_transfer_out >=", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutLessThan(Integer value) {
            addCriterion("group_transfer_out <", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutLessThanOrEqualTo(Integer value) {
            addCriterion("group_transfer_out <=", value, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutIn(List<Integer> values) {
            addCriterion("group_transfer_out in", values, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutNotIn(List<Integer> values) {
            addCriterion("group_transfer_out not in", values, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutBetween(Integer value1, Integer value2) {
            addCriterion("group_transfer_out between", value1, value2, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andGroupTransferOutNotBetween(Integer value1, Integer value2) {
            addCriterion("group_transfer_out not between", value1, value2, "groupTransferOut");
            return (Criteria) this;
        }

        public Criteria andDimissionCountIsNull() {
            addCriterion("dimission_count is null");
            return (Criteria) this;
        }

        public Criteria andDimissionCountIsNotNull() {
            addCriterion("dimission_count is not null");
            return (Criteria) this;
        }

        public Criteria andDimissionCountEqualTo(Integer value) {
            addCriterion("dimission_count =", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountNotEqualTo(Integer value) {
            addCriterion("dimission_count <>", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountGreaterThan(Integer value) {
            addCriterion("dimission_count >", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("dimission_count >=", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountLessThan(Integer value) {
            addCriterion("dimission_count <", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountLessThanOrEqualTo(Integer value) {
            addCriterion("dimission_count <=", value, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountIn(List<Integer> values) {
            addCriterion("dimission_count in", values, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountNotIn(List<Integer> values) {
            addCriterion("dimission_count not in", values, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountBetween(Integer value1, Integer value2) {
            addCriterion("dimission_count between", value1, value2, "dimissionCount");
            return (Criteria) this;
        }

        public Criteria andDimissionCountNotBetween(Integer value1, Integer value2) {
            addCriterion("dimission_count not between", value1, value2, "dimissionCount");
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