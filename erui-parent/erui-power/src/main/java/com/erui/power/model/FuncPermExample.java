package com.erui.power.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FuncPermExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public FuncPermExample() {
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNull() {
            addCriterion("parent_id is null");
            return (Criteria) this;
        }

        public Criteria andParentIdIsNotNull() {
            addCriterion("parent_id is not null");
            return (Criteria) this;
        }

        public Criteria andParentIdEqualTo(Long value) {
            addCriterion("parent_id =", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotEqualTo(Long value) {
            addCriterion("parent_id <>", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThan(Long value) {
            addCriterion("parent_id >", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdGreaterThanOrEqualTo(Long value) {
            addCriterion("parent_id >=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThan(Long value) {
            addCriterion("parent_id <", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdLessThanOrEqualTo(Long value) {
            addCriterion("parent_id <=", value, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdIn(List<Long> values) {
            addCriterion("parent_id in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotIn(List<Long> values) {
            addCriterion("parent_id not in", values, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdBetween(Long value1, Long value2) {
            addCriterion("parent_id between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andParentIdNotBetween(Long value1, Long value2) {
            addCriterion("parent_id not between", value1, value2, "parentId");
            return (Criteria) this;
        }

        public Criteria andFnIsNull() {
            addCriterion("fn is null");
            return (Criteria) this;
        }

        public Criteria andFnIsNotNull() {
            addCriterion("fn is not null");
            return (Criteria) this;
        }

        public Criteria andFnEqualTo(String value) {
            addCriterion("fn =", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnNotEqualTo(String value) {
            addCriterion("fn <>", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnGreaterThan(String value) {
            addCriterion("fn >", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnGreaterThanOrEqualTo(String value) {
            addCriterion("fn >=", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnLessThan(String value) {
            addCriterion("fn <", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnLessThanOrEqualTo(String value) {
            addCriterion("fn <=", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnLike(String value) {
            addCriterion("fn like", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnNotLike(String value) {
            addCriterion("fn not like", value, "fn");
            return (Criteria) this;
        }

        public Criteria andFnIn(List<String> values) {
            addCriterion("fn in", values, "fn");
            return (Criteria) this;
        }

        public Criteria andFnNotIn(List<String> values) {
            addCriterion("fn not in", values, "fn");
            return (Criteria) this;
        }

        public Criteria andFnBetween(String value1, String value2) {
            addCriterion("fn between", value1, value2, "fn");
            return (Criteria) this;
        }

        public Criteria andFnNotBetween(String value1, String value2) {
            addCriterion("fn not between", value1, value2, "fn");
            return (Criteria) this;
        }

        public Criteria andFnEnIsNull() {
            addCriterion("fn_en is null");
            return (Criteria) this;
        }

        public Criteria andFnEnIsNotNull() {
            addCriterion("fn_en is not null");
            return (Criteria) this;
        }

        public Criteria andFnEnEqualTo(String value) {
            addCriterion("fn_en =", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnNotEqualTo(String value) {
            addCriterion("fn_en <>", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnGreaterThan(String value) {
            addCriterion("fn_en >", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnGreaterThanOrEqualTo(String value) {
            addCriterion("fn_en >=", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnLessThan(String value) {
            addCriterion("fn_en <", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnLessThanOrEqualTo(String value) {
            addCriterion("fn_en <=", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnLike(String value) {
            addCriterion("fn_en like", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnNotLike(String value) {
            addCriterion("fn_en not like", value, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnIn(List<String> values) {
            addCriterion("fn_en in", values, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnNotIn(List<String> values) {
            addCriterion("fn_en not in", values, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnBetween(String value1, String value2) {
            addCriterion("fn_en between", value1, value2, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEnNotBetween(String value1, String value2) {
            addCriterion("fn_en not between", value1, value2, "fnEn");
            return (Criteria) this;
        }

        public Criteria andFnEsIsNull() {
            addCriterion("fn_es is null");
            return (Criteria) this;
        }

        public Criteria andFnEsIsNotNull() {
            addCriterion("fn_es is not null");
            return (Criteria) this;
        }

        public Criteria andFnEsEqualTo(String value) {
            addCriterion("fn_es =", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsNotEqualTo(String value) {
            addCriterion("fn_es <>", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsGreaterThan(String value) {
            addCriterion("fn_es >", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsGreaterThanOrEqualTo(String value) {
            addCriterion("fn_es >=", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsLessThan(String value) {
            addCriterion("fn_es <", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsLessThanOrEqualTo(String value) {
            addCriterion("fn_es <=", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsLike(String value) {
            addCriterion("fn_es like", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsNotLike(String value) {
            addCriterion("fn_es not like", value, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsIn(List<String> values) {
            addCriterion("fn_es in", values, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsNotIn(List<String> values) {
            addCriterion("fn_es not in", values, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsBetween(String value1, String value2) {
            addCriterion("fn_es between", value1, value2, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnEsNotBetween(String value1, String value2) {
            addCriterion("fn_es not between", value1, value2, "fnEs");
            return (Criteria) this;
        }

        public Criteria andFnRuIsNull() {
            addCriterion("fn_ru is null");
            return (Criteria) this;
        }

        public Criteria andFnRuIsNotNull() {
            addCriterion("fn_ru is not null");
            return (Criteria) this;
        }

        public Criteria andFnRuEqualTo(String value) {
            addCriterion("fn_ru =", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuNotEqualTo(String value) {
            addCriterion("fn_ru <>", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuGreaterThan(String value) {
            addCriterion("fn_ru >", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuGreaterThanOrEqualTo(String value) {
            addCriterion("fn_ru >=", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuLessThan(String value) {
            addCriterion("fn_ru <", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuLessThanOrEqualTo(String value) {
            addCriterion("fn_ru <=", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuLike(String value) {
            addCriterion("fn_ru like", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuNotLike(String value) {
            addCriterion("fn_ru not like", value, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuIn(List<String> values) {
            addCriterion("fn_ru in", values, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuNotIn(List<String> values) {
            addCriterion("fn_ru not in", values, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuBetween(String value1, String value2) {
            addCriterion("fn_ru between", value1, value2, "fnRu");
            return (Criteria) this;
        }

        public Criteria andFnRuNotBetween(String value1, String value2) {
            addCriterion("fn_ru not between", value1, value2, "fnRu");
            return (Criteria) this;
        }

        public Criteria andShowNameIsNull() {
            addCriterion("show_name is null");
            return (Criteria) this;
        }

        public Criteria andShowNameIsNotNull() {
            addCriterion("show_name is not null");
            return (Criteria) this;
        }

        public Criteria andShowNameEqualTo(String value) {
            addCriterion("show_name =", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotEqualTo(String value) {
            addCriterion("show_name <>", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameGreaterThan(String value) {
            addCriterion("show_name >", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameGreaterThanOrEqualTo(String value) {
            addCriterion("show_name >=", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLessThan(String value) {
            addCriterion("show_name <", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLessThanOrEqualTo(String value) {
            addCriterion("show_name <=", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameLike(String value) {
            addCriterion("show_name like", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotLike(String value) {
            addCriterion("show_name not like", value, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameIn(List<String> values) {
            addCriterion("show_name in", values, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotIn(List<String> values) {
            addCriterion("show_name not in", values, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameBetween(String value1, String value2) {
            addCriterion("show_name between", value1, value2, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameNotBetween(String value1, String value2) {
            addCriterion("show_name not between", value1, value2, "showName");
            return (Criteria) this;
        }

        public Criteria andShowNameEnIsNull() {
            addCriterion("show_name_en is null");
            return (Criteria) this;
        }

        public Criteria andShowNameEnIsNotNull() {
            addCriterion("show_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andShowNameEnEqualTo(String value) {
            addCriterion("show_name_en =", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnNotEqualTo(String value) {
            addCriterion("show_name_en <>", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnGreaterThan(String value) {
            addCriterion("show_name_en >", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("show_name_en >=", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnLessThan(String value) {
            addCriterion("show_name_en <", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnLessThanOrEqualTo(String value) {
            addCriterion("show_name_en <=", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnLike(String value) {
            addCriterion("show_name_en like", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnNotLike(String value) {
            addCriterion("show_name_en not like", value, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnIn(List<String> values) {
            addCriterion("show_name_en in", values, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnNotIn(List<String> values) {
            addCriterion("show_name_en not in", values, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnBetween(String value1, String value2) {
            addCriterion("show_name_en between", value1, value2, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEnNotBetween(String value1, String value2) {
            addCriterion("show_name_en not between", value1, value2, "showNameEn");
            return (Criteria) this;
        }

        public Criteria andShowNameEsIsNull() {
            addCriterion("show_name_es is null");
            return (Criteria) this;
        }

        public Criteria andShowNameEsIsNotNull() {
            addCriterion("show_name_es is not null");
            return (Criteria) this;
        }

        public Criteria andShowNameEsEqualTo(String value) {
            addCriterion("show_name_es =", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsNotEqualTo(String value) {
            addCriterion("show_name_es <>", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsGreaterThan(String value) {
            addCriterion("show_name_es >", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsGreaterThanOrEqualTo(String value) {
            addCriterion("show_name_es >=", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsLessThan(String value) {
            addCriterion("show_name_es <", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsLessThanOrEqualTo(String value) {
            addCriterion("show_name_es <=", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsLike(String value) {
            addCriterion("show_name_es like", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsNotLike(String value) {
            addCriterion("show_name_es not like", value, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsIn(List<String> values) {
            addCriterion("show_name_es in", values, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsNotIn(List<String> values) {
            addCriterion("show_name_es not in", values, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsBetween(String value1, String value2) {
            addCriterion("show_name_es between", value1, value2, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameEsNotBetween(String value1, String value2) {
            addCriterion("show_name_es not between", value1, value2, "showNameEs");
            return (Criteria) this;
        }

        public Criteria andShowNameRuIsNull() {
            addCriterion("show_name_ru is null");
            return (Criteria) this;
        }

        public Criteria andShowNameRuIsNotNull() {
            addCriterion("show_name_ru is not null");
            return (Criteria) this;
        }

        public Criteria andShowNameRuEqualTo(String value) {
            addCriterion("show_name_ru =", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuNotEqualTo(String value) {
            addCriterion("show_name_ru <>", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuGreaterThan(String value) {
            addCriterion("show_name_ru >", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuGreaterThanOrEqualTo(String value) {
            addCriterion("show_name_ru >=", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuLessThan(String value) {
            addCriterion("show_name_ru <", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuLessThanOrEqualTo(String value) {
            addCriterion("show_name_ru <=", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuLike(String value) {
            addCriterion("show_name_ru like", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuNotLike(String value) {
            addCriterion("show_name_ru not like", value, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuIn(List<String> values) {
            addCriterion("show_name_ru in", values, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuNotIn(List<String> values) {
            addCriterion("show_name_ru not in", values, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuBetween(String value1, String value2) {
            addCriterion("show_name_ru between", value1, value2, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andShowNameRuNotBetween(String value1, String value2) {
            addCriterion("show_name_ru not between", value1, value2, "showNameRu");
            return (Criteria) this;
        }

        public Criteria andFnGroupIsNull() {
            addCriterion("fn_group is null");
            return (Criteria) this;
        }

        public Criteria andFnGroupIsNotNull() {
            addCriterion("fn_group is not null");
            return (Criteria) this;
        }

        public Criteria andFnGroupEqualTo(String value) {
            addCriterion("fn_group =", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupNotEqualTo(String value) {
            addCriterion("fn_group <>", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupGreaterThan(String value) {
            addCriterion("fn_group >", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupGreaterThanOrEqualTo(String value) {
            addCriterion("fn_group >=", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupLessThan(String value) {
            addCriterion("fn_group <", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupLessThanOrEqualTo(String value) {
            addCriterion("fn_group <=", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupLike(String value) {
            addCriterion("fn_group like", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupNotLike(String value) {
            addCriterion("fn_group not like", value, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupIn(List<String> values) {
            addCriterion("fn_group in", values, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupNotIn(List<String> values) {
            addCriterion("fn_group not in", values, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupBetween(String value1, String value2) {
            addCriterion("fn_group between", value1, value2, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andFnGroupNotBetween(String value1, String value2) {
            addCriterion("fn_group not between", value1, value2, "fnGroup");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andGrantFlagIsNull() {
            addCriterion("grant_flag is null");
            return (Criteria) this;
        }

        public Criteria andGrantFlagIsNotNull() {
            addCriterion("grant_flag is not null");
            return (Criteria) this;
        }

        public Criteria andGrantFlagEqualTo(String value) {
            addCriterion("grant_flag =", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagNotEqualTo(String value) {
            addCriterion("grant_flag <>", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagGreaterThan(String value) {
            addCriterion("grant_flag >", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagGreaterThanOrEqualTo(String value) {
            addCriterion("grant_flag >=", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagLessThan(String value) {
            addCriterion("grant_flag <", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagLessThanOrEqualTo(String value) {
            addCriterion("grant_flag <=", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagLike(String value) {
            addCriterion("grant_flag like", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagNotLike(String value) {
            addCriterion("grant_flag not like", value, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagIn(List<String> values) {
            addCriterion("grant_flag in", values, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagNotIn(List<String> values) {
            addCriterion("grant_flag not in", values, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagBetween(String value1, String value2) {
            addCriterion("grant_flag between", value1, value2, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andGrantFlagNotBetween(String value1, String value2) {
            addCriterion("grant_flag not between", value1, value2, "grantFlag");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNull() {
            addCriterion("created_by is null");
            return (Criteria) this;
        }

        public Criteria andCreatedByIsNotNull() {
            addCriterion("created_by is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedByEqualTo(Long value) {
            addCriterion("created_by =", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotEqualTo(Long value) {
            addCriterion("created_by <>", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThan(Long value) {
            addCriterion("created_by >", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByGreaterThanOrEqualTo(Long value) {
            addCriterion("created_by >=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThan(Long value) {
            addCriterion("created_by <", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByLessThanOrEqualTo(Long value) {
            addCriterion("created_by <=", value, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByIn(List<Long> values) {
            addCriterion("created_by in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotIn(List<Long> values) {
            addCriterion("created_by not in", values, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByBetween(Long value1, Long value2) {
            addCriterion("created_by between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedByNotBetween(Long value1, Long value2) {
            addCriterion("created_by not between", value1, value2, "createdBy");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("created_at is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("created_at is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("created_at =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("created_at <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("created_at >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("created_at >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("created_at <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("created_at <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("created_at in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("created_at not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("created_at between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("created_at not between", value1, value2, "createdAt");
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