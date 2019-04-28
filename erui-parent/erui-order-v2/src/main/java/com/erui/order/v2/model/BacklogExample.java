package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BacklogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public BacklogExample() {
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

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(String value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(String value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(String value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(String value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(String value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(String value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLike(String value) {
            addCriterion("create_date like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotLike(String value) {
            addCriterion("create_date not like", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<String> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<String> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(String value1, String value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(String value1, String value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemIsNull() {
            addCriterion("place_system is null");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemIsNotNull() {
            addCriterion("place_system is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemEqualTo(String value) {
            addCriterion("place_system =", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemNotEqualTo(String value) {
            addCriterion("place_system <>", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemGreaterThan(String value) {
            addCriterion("place_system >", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemGreaterThanOrEqualTo(String value) {
            addCriterion("place_system >=", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemLessThan(String value) {
            addCriterion("place_system <", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemLessThanOrEqualTo(String value) {
            addCriterion("place_system <=", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemLike(String value) {
            addCriterion("place_system like", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemNotLike(String value) {
            addCriterion("place_system not like", value, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemIn(List<String> values) {
            addCriterion("place_system in", values, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemNotIn(List<String> values) {
            addCriterion("place_system not in", values, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemBetween(String value1, String value2) {
            addCriterion("place_system between", value1, value2, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andPlaceSystemNotBetween(String value1, String value2) {
            addCriterion("place_system not between", value1, value2, "placeSystem");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameIsNull() {
            addCriterion("function_explain_name is null");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameIsNotNull() {
            addCriterion("function_explain_name is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameEqualTo(String value) {
            addCriterion("function_explain_name =", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameNotEqualTo(String value) {
            addCriterion("function_explain_name <>", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameGreaterThan(String value) {
            addCriterion("function_explain_name >", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameGreaterThanOrEqualTo(String value) {
            addCriterion("function_explain_name >=", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameLessThan(String value) {
            addCriterion("function_explain_name <", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameLessThanOrEqualTo(String value) {
            addCriterion("function_explain_name <=", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameLike(String value) {
            addCriterion("function_explain_name like", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameNotLike(String value) {
            addCriterion("function_explain_name not like", value, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameIn(List<String> values) {
            addCriterion("function_explain_name in", values, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameNotIn(List<String> values) {
            addCriterion("function_explain_name not in", values, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameBetween(String value1, String value2) {
            addCriterion("function_explain_name between", value1, value2, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainNameNotBetween(String value1, String value2) {
            addCriterion("function_explain_name not between", value1, value2, "functionExplainName");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdIsNull() {
            addCriterion("function_explain_id is null");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdIsNotNull() {
            addCriterion("function_explain_id is not null");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdEqualTo(Integer value) {
            addCriterion("function_explain_id =", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdNotEqualTo(Integer value) {
            addCriterion("function_explain_id <>", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdGreaterThan(Integer value) {
            addCriterion("function_explain_id >", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("function_explain_id >=", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdLessThan(Integer value) {
            addCriterion("function_explain_id <", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdLessThanOrEqualTo(Integer value) {
            addCriterion("function_explain_id <=", value, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdIn(List<Integer> values) {
            addCriterion("function_explain_id in", values, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdNotIn(List<Integer> values) {
            addCriterion("function_explain_id not in", values, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdBetween(Integer value1, Integer value2) {
            addCriterion("function_explain_id between", value1, value2, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andFunctionExplainIdNotBetween(Integer value1, Integer value2) {
            addCriterion("function_explain_id not between", value1, value2, "functionExplainId");
            return (Criteria) this;
        }

        public Criteria andReturnNoIsNull() {
            addCriterion("return_no is null");
            return (Criteria) this;
        }

        public Criteria andReturnNoIsNotNull() {
            addCriterion("return_no is not null");
            return (Criteria) this;
        }

        public Criteria andReturnNoEqualTo(String value) {
            addCriterion("return_no =", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotEqualTo(String value) {
            addCriterion("return_no <>", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoGreaterThan(String value) {
            addCriterion("return_no >", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoGreaterThanOrEqualTo(String value) {
            addCriterion("return_no >=", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLessThan(String value) {
            addCriterion("return_no <", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLessThanOrEqualTo(String value) {
            addCriterion("return_no <=", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoLike(String value) {
            addCriterion("return_no like", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotLike(String value) {
            addCriterion("return_no not like", value, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoIn(List<String> values) {
            addCriterion("return_no in", values, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotIn(List<String> values) {
            addCriterion("return_no not in", values, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoBetween(String value1, String value2) {
            addCriterion("return_no between", value1, value2, "returnNo");
            return (Criteria) this;
        }

        public Criteria andReturnNoNotBetween(String value1, String value2) {
            addCriterion("return_no not between", value1, value2, "returnNo");
            return (Criteria) this;
        }

        public Criteria andInformTheContentIsNull() {
            addCriterion("inform_the_content is null");
            return (Criteria) this;
        }

        public Criteria andInformTheContentIsNotNull() {
            addCriterion("inform_the_content is not null");
            return (Criteria) this;
        }

        public Criteria andInformTheContentEqualTo(String value) {
            addCriterion("inform_the_content =", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentNotEqualTo(String value) {
            addCriterion("inform_the_content <>", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentGreaterThan(String value) {
            addCriterion("inform_the_content >", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentGreaterThanOrEqualTo(String value) {
            addCriterion("inform_the_content >=", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentLessThan(String value) {
            addCriterion("inform_the_content <", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentLessThanOrEqualTo(String value) {
            addCriterion("inform_the_content <=", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentLike(String value) {
            addCriterion("inform_the_content like", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentNotLike(String value) {
            addCriterion("inform_the_content not like", value, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentIn(List<String> values) {
            addCriterion("inform_the_content in", values, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentNotIn(List<String> values) {
            addCriterion("inform_the_content not in", values, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentBetween(String value1, String value2) {
            addCriterion("inform_the_content between", value1, value2, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andInformTheContentNotBetween(String value1, String value2) {
            addCriterion("inform_the_content not between", value1, value2, "informTheContent");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNull() {
            addCriterion("host_id is null");
            return (Criteria) this;
        }

        public Criteria andHostIdIsNotNull() {
            addCriterion("host_id is not null");
            return (Criteria) this;
        }

        public Criteria andHostIdEqualTo(Integer value) {
            addCriterion("host_id =", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotEqualTo(Integer value) {
            addCriterion("host_id <>", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThan(Integer value) {
            addCriterion("host_id >", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("host_id >=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThan(Integer value) {
            addCriterion("host_id <", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdLessThanOrEqualTo(Integer value) {
            addCriterion("host_id <=", value, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdIn(List<Integer> values) {
            addCriterion("host_id in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotIn(List<Integer> values) {
            addCriterion("host_id not in", values, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdBetween(Integer value1, Integer value2) {
            addCriterion("host_id between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andHostIdNotBetween(Integer value1, Integer value2) {
            addCriterion("host_id not between", value1, value2, "hostId");
            return (Criteria) this;
        }

        public Criteria andFollowIdIsNull() {
            addCriterion("follow_id is null");
            return (Criteria) this;
        }

        public Criteria andFollowIdIsNotNull() {
            addCriterion("follow_id is not null");
            return (Criteria) this;
        }

        public Criteria andFollowIdEqualTo(Integer value) {
            addCriterion("follow_id =", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotEqualTo(Integer value) {
            addCriterion("follow_id <>", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdGreaterThan(Integer value) {
            addCriterion("follow_id >", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("follow_id >=", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdLessThan(Integer value) {
            addCriterion("follow_id <", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdLessThanOrEqualTo(Integer value) {
            addCriterion("follow_id <=", value, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdIn(List<Integer> values) {
            addCriterion("follow_id in", values, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotIn(List<Integer> values) {
            addCriterion("follow_id not in", values, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdBetween(Integer value1, Integer value2) {
            addCriterion("follow_id between", value1, value2, "followId");
            return (Criteria) this;
        }

        public Criteria andFollowIdNotBetween(Integer value1, Integer value2) {
            addCriterion("follow_id not between", value1, value2, "followId");
            return (Criteria) this;
        }

        public Criteria andUidIsNull() {
            addCriterion("uid is null");
            return (Criteria) this;
        }

        public Criteria andUidIsNotNull() {
            addCriterion("uid is not null");
            return (Criteria) this;
        }

        public Criteria andUidEqualTo(Integer value) {
            addCriterion("uid =", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotEqualTo(Integer value) {
            addCriterion("uid <>", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThan(Integer value) {
            addCriterion("uid >", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("uid >=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThan(Integer value) {
            addCriterion("uid <", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidLessThanOrEqualTo(Integer value) {
            addCriterion("uid <=", value, "uid");
            return (Criteria) this;
        }

        public Criteria andUidIn(List<Integer> values) {
            addCriterion("uid in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotIn(List<Integer> values) {
            addCriterion("uid not in", values, "uid");
            return (Criteria) this;
        }

        public Criteria andUidBetween(Integer value1, Integer value2) {
            addCriterion("uid between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andUidNotBetween(Integer value1, Integer value2) {
            addCriterion("uid not between", value1, value2, "uid");
            return (Criteria) this;
        }

        public Criteria andDelYnIsNull() {
            addCriterion("del_yn is null");
            return (Criteria) this;
        }

        public Criteria andDelYnIsNotNull() {
            addCriterion("del_yn is not null");
            return (Criteria) this;
        }

        public Criteria andDelYnEqualTo(Integer value) {
            addCriterion("del_yn =", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnNotEqualTo(Integer value) {
            addCriterion("del_yn <>", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnGreaterThan(Integer value) {
            addCriterion("del_yn >", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnGreaterThanOrEqualTo(Integer value) {
            addCriterion("del_yn >=", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnLessThan(Integer value) {
            addCriterion("del_yn <", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnLessThanOrEqualTo(Integer value) {
            addCriterion("del_yn <=", value, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnIn(List<Integer> values) {
            addCriterion("del_yn in", values, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnNotIn(List<Integer> values) {
            addCriterion("del_yn not in", values, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnBetween(Integer value1, Integer value2) {
            addCriterion("del_yn between", value1, value2, "delYn");
            return (Criteria) this;
        }

        public Criteria andDelYnNotBetween(Integer value1, Integer value2) {
            addCriterion("del_yn not between", value1, value2, "delYn");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNull() {
            addCriterion("delete_time is null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIsNotNull() {
            addCriterion("delete_time is not null");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeEqualTo(Date value) {
            addCriterion("delete_time =", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotEqualTo(Date value) {
            addCriterion("delete_time <>", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThan(Date value) {
            addCriterion("delete_time >", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("delete_time >=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThan(Date value) {
            addCriterion("delete_time <", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("delete_time <=", value, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeIn(List<Date> values) {
            addCriterion("delete_time in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotIn(List<Date> values) {
            addCriterion("delete_time not in", values, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeBetween(Date value1, Date value2) {
            addCriterion("delete_time between", value1, value2, "deleteTime");
            return (Criteria) this;
        }

        public Criteria andDeleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("delete_time not between", value1, value2, "deleteTime");
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