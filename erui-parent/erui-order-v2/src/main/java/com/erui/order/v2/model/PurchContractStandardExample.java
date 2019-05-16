package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PurchContractStandardExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PurchContractStandardExample() {
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

        public Criteria andPurchContractIdIsNull() {
            addCriterion("purch_contract_id is null");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdIsNotNull() {
            addCriterion("purch_contract_id is not null");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdEqualTo(Integer value) {
            addCriterion("purch_contract_id =", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotEqualTo(Integer value) {
            addCriterion("purch_contract_id <>", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdGreaterThan(Integer value) {
            addCriterion("purch_contract_id >", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("purch_contract_id >=", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdLessThan(Integer value) {
            addCriterion("purch_contract_id <", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdLessThanOrEqualTo(Integer value) {
            addCriterion("purch_contract_id <=", value, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdIn(List<Integer> values) {
            addCriterion("purch_contract_id in", values, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotIn(List<Integer> values) {
            addCriterion("purch_contract_id not in", values, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdBetween(Integer value1, Integer value2) {
            addCriterion("purch_contract_id between", value1, value2, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andPurchContractIdNotBetween(Integer value1, Integer value2) {
            addCriterion("purch_contract_id not between", value1, value2, "purchContractId");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerIsNull() {
            addCriterion("used_for_buyer is null");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerIsNotNull() {
            addCriterion("used_for_buyer is not null");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerEqualTo(String value) {
            addCriterion("used_for_buyer =", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerNotEqualTo(String value) {
            addCriterion("used_for_buyer <>", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerGreaterThan(String value) {
            addCriterion("used_for_buyer >", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerGreaterThanOrEqualTo(String value) {
            addCriterion("used_for_buyer >=", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerLessThan(String value) {
            addCriterion("used_for_buyer <", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerLessThanOrEqualTo(String value) {
            addCriterion("used_for_buyer <=", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerLike(String value) {
            addCriterion("used_for_buyer like", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerNotLike(String value) {
            addCriterion("used_for_buyer not like", value, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerIn(List<String> values) {
            addCriterion("used_for_buyer in", values, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerNotIn(List<String> values) {
            addCriterion("used_for_buyer not in", values, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerBetween(String value1, String value2) {
            addCriterion("used_for_buyer between", value1, value2, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andUsedForBuyerNotBetween(String value1, String value2) {
            addCriterion("used_for_buyer not between", value1, value2, "usedForBuyer");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireIsNull() {
            addCriterion("standard_and_require is null");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireIsNotNull() {
            addCriterion("standard_and_require is not null");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireEqualTo(String value) {
            addCriterion("standard_and_require =", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireNotEqualTo(String value) {
            addCriterion("standard_and_require <>", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireGreaterThan(String value) {
            addCriterion("standard_and_require >", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireGreaterThanOrEqualTo(String value) {
            addCriterion("standard_and_require >=", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireLessThan(String value) {
            addCriterion("standard_and_require <", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireLessThanOrEqualTo(String value) {
            addCriterion("standard_and_require <=", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireLike(String value) {
            addCriterion("standard_and_require like", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireNotLike(String value) {
            addCriterion("standard_and_require not like", value, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireIn(List<String> values) {
            addCriterion("standard_and_require in", values, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireNotIn(List<String> values) {
            addCriterion("standard_and_require not in", values, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireBetween(String value1, String value2) {
            addCriterion("standard_and_require between", value1, value2, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andStandardAndRequireNotBetween(String value1, String value2) {
            addCriterion("standard_and_require not between", value1, value2, "standardAndRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireIsNull() {
            addCriterion("meet_require is null");
            return (Criteria) this;
        }

        public Criteria andMeetRequireIsNotNull() {
            addCriterion("meet_require is not null");
            return (Criteria) this;
        }

        public Criteria andMeetRequireEqualTo(String value) {
            addCriterion("meet_require =", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireNotEqualTo(String value) {
            addCriterion("meet_require <>", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireGreaterThan(String value) {
            addCriterion("meet_require >", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireGreaterThanOrEqualTo(String value) {
            addCriterion("meet_require >=", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireLessThan(String value) {
            addCriterion("meet_require <", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireLessThanOrEqualTo(String value) {
            addCriterion("meet_require <=", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireLike(String value) {
            addCriterion("meet_require like", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireNotLike(String value) {
            addCriterion("meet_require not like", value, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireIn(List<String> values) {
            addCriterion("meet_require in", values, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireNotIn(List<String> values) {
            addCriterion("meet_require not in", values, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireBetween(String value1, String value2) {
            addCriterion("meet_require between", value1, value2, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andMeetRequireNotBetween(String value1, String value2) {
            addCriterion("meet_require not between", value1, value2, "meetRequire");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIsNull() {
            addCriterion("warranty_period is null");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIsNotNull() {
            addCriterion("warranty_period is not null");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodEqualTo(String value) {
            addCriterion("warranty_period =", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotEqualTo(String value) {
            addCriterion("warranty_period <>", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodGreaterThan(String value) {
            addCriterion("warranty_period >", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("warranty_period >=", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLessThan(String value) {
            addCriterion("warranty_period <", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLessThanOrEqualTo(String value) {
            addCriterion("warranty_period <=", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodLike(String value) {
            addCriterion("warranty_period like", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotLike(String value) {
            addCriterion("warranty_period not like", value, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodIn(List<String> values) {
            addCriterion("warranty_period in", values, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotIn(List<String> values) {
            addCriterion("warranty_period not in", values, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodBetween(String value1, String value2) {
            addCriterion("warranty_period between", value1, value2, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andWarrantyPeriodNotBetween(String value1, String value2) {
            addCriterion("warranty_period not between", value1, value2, "warrantyPeriod");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNull() {
            addCriterion("delivery_date is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIsNotNull() {
            addCriterion("delivery_date is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateEqualTo(Date value) {
            addCriterion("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(Date value) {
            addCriterion("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(Date value) {
            addCriterion("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(Date value) {
            addCriterion("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(Date value) {
            addCriterion("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(Date value) {
            addCriterion("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<Date> values) {
            addCriterion("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<Date> values) {
            addCriterion("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(Date value1, Date value2) {
            addCriterion("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(Date value1, Date value2) {
            addCriterion("delivery_date not between", value1, value2, "deliveryDate");
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

        public Criteria andInspectionPeriodIsNull() {
            addCriterion("inspection_period is null");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodIsNotNull() {
            addCriterion("inspection_period is not null");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodEqualTo(String value) {
            addCriterion("inspection_period =", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodNotEqualTo(String value) {
            addCriterion("inspection_period <>", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodGreaterThan(String value) {
            addCriterion("inspection_period >", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodGreaterThanOrEqualTo(String value) {
            addCriterion("inspection_period >=", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodLessThan(String value) {
            addCriterion("inspection_period <", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodLessThanOrEqualTo(String value) {
            addCriterion("inspection_period <=", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodLike(String value) {
            addCriterion("inspection_period like", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodNotLike(String value) {
            addCriterion("inspection_period not like", value, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodIn(List<String> values) {
            addCriterion("inspection_period in", values, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodNotIn(List<String> values) {
            addCriterion("inspection_period not in", values, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodBetween(String value1, String value2) {
            addCriterion("inspection_period between", value1, value2, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andInspectionPeriodNotBetween(String value1, String value2) {
            addCriterion("inspection_period not between", value1, value2, "inspectionPeriod");
            return (Criteria) this;
        }

        public Criteria andDataVersionIsNull() {
            addCriterion("data_version is null");
            return (Criteria) this;
        }

        public Criteria andDataVersionIsNotNull() {
            addCriterion("data_version is not null");
            return (Criteria) this;
        }

        public Criteria andDataVersionEqualTo(String value) {
            addCriterion("data_version =", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionNotEqualTo(String value) {
            addCriterion("data_version <>", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionGreaterThan(String value) {
            addCriterion("data_version >", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionGreaterThanOrEqualTo(String value) {
            addCriterion("data_version >=", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionLessThan(String value) {
            addCriterion("data_version <", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionLessThanOrEqualTo(String value) {
            addCriterion("data_version <=", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionLike(String value) {
            addCriterion("data_version like", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionNotLike(String value) {
            addCriterion("data_version not like", value, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionIn(List<String> values) {
            addCriterion("data_version in", values, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionNotIn(List<String> values) {
            addCriterion("data_version not in", values, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionBetween(String value1, String value2) {
            addCriterion("data_version between", value1, value2, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andDataVersionNotBetween(String value1, String value2) {
            addCriterion("data_version not between", value1, value2, "dataVersion");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIsNull() {
            addCriterion("method_and_time is null");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIsNotNull() {
            addCriterion("method_and_time is not null");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeEqualTo(String value) {
            addCriterion("method_and_time =", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotEqualTo(String value) {
            addCriterion("method_and_time <>", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeGreaterThan(String value) {
            addCriterion("method_and_time >", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeGreaterThanOrEqualTo(String value) {
            addCriterion("method_and_time >=", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLessThan(String value) {
            addCriterion("method_and_time <", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLessThanOrEqualTo(String value) {
            addCriterion("method_and_time <=", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeLike(String value) {
            addCriterion("method_and_time like", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotLike(String value) {
            addCriterion("method_and_time not like", value, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeIn(List<String> values) {
            addCriterion("method_and_time in", values, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotIn(List<String> values) {
            addCriterion("method_and_time not in", values, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeBetween(String value1, String value2) {
            addCriterion("method_and_time between", value1, value2, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andMethodAndTimeNotBetween(String value1, String value2) {
            addCriterion("method_and_time not between", value1, value2, "methodAndTime");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSIsNull() {
            addCriterion("inside_address_s is null");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSIsNotNull() {
            addCriterion("inside_address_s is not null");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSEqualTo(String value) {
            addCriterion("inside_address_s =", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSNotEqualTo(String value) {
            addCriterion("inside_address_s <>", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSGreaterThan(String value) {
            addCriterion("inside_address_s >", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSGreaterThanOrEqualTo(String value) {
            addCriterion("inside_address_s >=", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSLessThan(String value) {
            addCriterion("inside_address_s <", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSLessThanOrEqualTo(String value) {
            addCriterion("inside_address_s <=", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSLike(String value) {
            addCriterion("inside_address_s like", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSNotLike(String value) {
            addCriterion("inside_address_s not like", value, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSIn(List<String> values) {
            addCriterion("inside_address_s in", values, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSNotIn(List<String> values) {
            addCriterion("inside_address_s not in", values, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSBetween(String value1, String value2) {
            addCriterion("inside_address_s between", value1, value2, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressSNotBetween(String value1, String value2) {
            addCriterion("inside_address_s not between", value1, value2, "insideAddressS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSIsNull() {
            addCriterion("addressee_s is null");
            return (Criteria) this;
        }

        public Criteria andAddresseeSIsNotNull() {
            addCriterion("addressee_s is not null");
            return (Criteria) this;
        }

        public Criteria andAddresseeSEqualTo(String value) {
            addCriterion("addressee_s =", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSNotEqualTo(String value) {
            addCriterion("addressee_s <>", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSGreaterThan(String value) {
            addCriterion("addressee_s >", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSGreaterThanOrEqualTo(String value) {
            addCriterion("addressee_s >=", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSLessThan(String value) {
            addCriterion("addressee_s <", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSLessThanOrEqualTo(String value) {
            addCriterion("addressee_s <=", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSLike(String value) {
            addCriterion("addressee_s like", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSNotLike(String value) {
            addCriterion("addressee_s not like", value, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSIn(List<String> values) {
            addCriterion("addressee_s in", values, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSNotIn(List<String> values) {
            addCriterion("addressee_s not in", values, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSBetween(String value1, String value2) {
            addCriterion("addressee_s between", value1, value2, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andAddresseeSNotBetween(String value1, String value2) {
            addCriterion("addressee_s not between", value1, value2, "addresseeS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSIsNull() {
            addCriterion("telephone_s is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneSIsNotNull() {
            addCriterion("telephone_s is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneSEqualTo(String value) {
            addCriterion("telephone_s =", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSNotEqualTo(String value) {
            addCriterion("telephone_s <>", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSGreaterThan(String value) {
            addCriterion("telephone_s >", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSGreaterThanOrEqualTo(String value) {
            addCriterion("telephone_s >=", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSLessThan(String value) {
            addCriterion("telephone_s <", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSLessThanOrEqualTo(String value) {
            addCriterion("telephone_s <=", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSLike(String value) {
            addCriterion("telephone_s like", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSNotLike(String value) {
            addCriterion("telephone_s not like", value, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSIn(List<String> values) {
            addCriterion("telephone_s in", values, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSNotIn(List<String> values) {
            addCriterion("telephone_s not in", values, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSBetween(String value1, String value2) {
            addCriterion("telephone_s between", value1, value2, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andTelephoneSNotBetween(String value1, String value2) {
            addCriterion("telephone_s not between", value1, value2, "telephoneS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSIsNull() {
            addCriterion("inbox_address_s is null");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSIsNotNull() {
            addCriterion("inbox_address_s is not null");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSEqualTo(String value) {
            addCriterion("inbox_address_s =", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSNotEqualTo(String value) {
            addCriterion("inbox_address_s <>", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSGreaterThan(String value) {
            addCriterion("inbox_address_s >", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSGreaterThanOrEqualTo(String value) {
            addCriterion("inbox_address_s >=", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSLessThan(String value) {
            addCriterion("inbox_address_s <", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSLessThanOrEqualTo(String value) {
            addCriterion("inbox_address_s <=", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSLike(String value) {
            addCriterion("inbox_address_s like", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSNotLike(String value) {
            addCriterion("inbox_address_s not like", value, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSIn(List<String> values) {
            addCriterion("inbox_address_s in", values, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSNotIn(List<String> values) {
            addCriterion("inbox_address_s not in", values, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSBetween(String value1, String value2) {
            addCriterion("inbox_address_s between", value1, value2, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInboxAddressSNotBetween(String value1, String value2) {
            addCriterion("inbox_address_s not between", value1, value2, "inboxAddressS");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBIsNull() {
            addCriterion("inside_address_b is null");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBIsNotNull() {
            addCriterion("inside_address_b is not null");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBEqualTo(String value) {
            addCriterion("inside_address_b =", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBNotEqualTo(String value) {
            addCriterion("inside_address_b <>", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBGreaterThan(String value) {
            addCriterion("inside_address_b >", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBGreaterThanOrEqualTo(String value) {
            addCriterion("inside_address_b >=", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBLessThan(String value) {
            addCriterion("inside_address_b <", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBLessThanOrEqualTo(String value) {
            addCriterion("inside_address_b <=", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBLike(String value) {
            addCriterion("inside_address_b like", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBNotLike(String value) {
            addCriterion("inside_address_b not like", value, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBIn(List<String> values) {
            addCriterion("inside_address_b in", values, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBNotIn(List<String> values) {
            addCriterion("inside_address_b not in", values, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBBetween(String value1, String value2) {
            addCriterion("inside_address_b between", value1, value2, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andInsideAddressBNotBetween(String value1, String value2) {
            addCriterion("inside_address_b not between", value1, value2, "insideAddressB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBIsNull() {
            addCriterion("addressee_b is null");
            return (Criteria) this;
        }

        public Criteria andAddresseeBIsNotNull() {
            addCriterion("addressee_b is not null");
            return (Criteria) this;
        }

        public Criteria andAddresseeBEqualTo(String value) {
            addCriterion("addressee_b =", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBNotEqualTo(String value) {
            addCriterion("addressee_b <>", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBGreaterThan(String value) {
            addCriterion("addressee_b >", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBGreaterThanOrEqualTo(String value) {
            addCriterion("addressee_b >=", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBLessThan(String value) {
            addCriterion("addressee_b <", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBLessThanOrEqualTo(String value) {
            addCriterion("addressee_b <=", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBLike(String value) {
            addCriterion("addressee_b like", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBNotLike(String value) {
            addCriterion("addressee_b not like", value, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBIn(List<String> values) {
            addCriterion("addressee_b in", values, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBNotIn(List<String> values) {
            addCriterion("addressee_b not in", values, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBBetween(String value1, String value2) {
            addCriterion("addressee_b between", value1, value2, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andAddresseeBNotBetween(String value1, String value2) {
            addCriterion("addressee_b not between", value1, value2, "addresseeB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBIsNull() {
            addCriterion("telephone_b is null");
            return (Criteria) this;
        }

        public Criteria andTelephoneBIsNotNull() {
            addCriterion("telephone_b is not null");
            return (Criteria) this;
        }

        public Criteria andTelephoneBEqualTo(String value) {
            addCriterion("telephone_b =", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBNotEqualTo(String value) {
            addCriterion("telephone_b <>", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBGreaterThan(String value) {
            addCriterion("telephone_b >", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBGreaterThanOrEqualTo(String value) {
            addCriterion("telephone_b >=", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBLessThan(String value) {
            addCriterion("telephone_b <", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBLessThanOrEqualTo(String value) {
            addCriterion("telephone_b <=", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBLike(String value) {
            addCriterion("telephone_b like", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBNotLike(String value) {
            addCriterion("telephone_b not like", value, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBIn(List<String> values) {
            addCriterion("telephone_b in", values, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBNotIn(List<String> values) {
            addCriterion("telephone_b not in", values, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBBetween(String value1, String value2) {
            addCriterion("telephone_b between", value1, value2, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andTelephoneBNotBetween(String value1, String value2) {
            addCriterion("telephone_b not between", value1, value2, "telephoneB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBIsNull() {
            addCriterion("inbox_address_b is null");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBIsNotNull() {
            addCriterion("inbox_address_b is not null");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBEqualTo(String value) {
            addCriterion("inbox_address_b =", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBNotEqualTo(String value) {
            addCriterion("inbox_address_b <>", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBGreaterThan(String value) {
            addCriterion("inbox_address_b >", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBGreaterThanOrEqualTo(String value) {
            addCriterion("inbox_address_b >=", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBLessThan(String value) {
            addCriterion("inbox_address_b <", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBLessThanOrEqualTo(String value) {
            addCriterion("inbox_address_b <=", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBLike(String value) {
            addCriterion("inbox_address_b like", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBNotLike(String value) {
            addCriterion("inbox_address_b not like", value, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBIn(List<String> values) {
            addCriterion("inbox_address_b in", values, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBNotIn(List<String> values) {
            addCriterion("inbox_address_b not in", values, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBBetween(String value1, String value2) {
            addCriterion("inbox_address_b between", value1, value2, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andInboxAddressBNotBetween(String value1, String value2) {
            addCriterion("inbox_address_b not between", value1, value2, "inboxAddressB");
            return (Criteria) this;
        }

        public Criteria andSolutionIsNull() {
            addCriterion("solution is null");
            return (Criteria) this;
        }

        public Criteria andSolutionIsNotNull() {
            addCriterion("solution is not null");
            return (Criteria) this;
        }

        public Criteria andSolutionEqualTo(String value) {
            addCriterion("solution =", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotEqualTo(String value) {
            addCriterion("solution <>", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionGreaterThan(String value) {
            addCriterion("solution >", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionGreaterThanOrEqualTo(String value) {
            addCriterion("solution >=", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLessThan(String value) {
            addCriterion("solution <", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLessThanOrEqualTo(String value) {
            addCriterion("solution <=", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionLike(String value) {
            addCriterion("solution like", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotLike(String value) {
            addCriterion("solution not like", value, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionIn(List<String> values) {
            addCriterion("solution in", values, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotIn(List<String> values) {
            addCriterion("solution not in", values, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionBetween(String value1, String value2) {
            addCriterion("solution between", value1, value2, "solution");
            return (Criteria) this;
        }

        public Criteria andSolutionNotBetween(String value1, String value2) {
            addCriterion("solution not between", value1, value2, "solution");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationIsNull() {
            addCriterion("board_arbitration is null");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationIsNotNull() {
            addCriterion("board_arbitration is not null");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationEqualTo(String value) {
            addCriterion("board_arbitration =", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationNotEqualTo(String value) {
            addCriterion("board_arbitration <>", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationGreaterThan(String value) {
            addCriterion("board_arbitration >", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationGreaterThanOrEqualTo(String value) {
            addCriterion("board_arbitration >=", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationLessThan(String value) {
            addCriterion("board_arbitration <", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationLessThanOrEqualTo(String value) {
            addCriterion("board_arbitration <=", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationLike(String value) {
            addCriterion("board_arbitration like", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationNotLike(String value) {
            addCriterion("board_arbitration not like", value, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationIn(List<String> values) {
            addCriterion("board_arbitration in", values, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationNotIn(List<String> values) {
            addCriterion("board_arbitration not in", values, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationBetween(String value1, String value2) {
            addCriterion("board_arbitration between", value1, value2, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andBoardArbitrationNotBetween(String value1, String value2) {
            addCriterion("board_arbitration not between", value1, value2, "boardArbitration");
            return (Criteria) this;
        }

        public Criteria andFewCopiesIsNull() {
            addCriterion("few_copies is null");
            return (Criteria) this;
        }

        public Criteria andFewCopiesIsNotNull() {
            addCriterion("few_copies is not null");
            return (Criteria) this;
        }

        public Criteria andFewCopiesEqualTo(String value) {
            addCriterion("few_copies =", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesNotEqualTo(String value) {
            addCriterion("few_copies <>", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesGreaterThan(String value) {
            addCriterion("few_copies >", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesGreaterThanOrEqualTo(String value) {
            addCriterion("few_copies >=", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesLessThan(String value) {
            addCriterion("few_copies <", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesLessThanOrEqualTo(String value) {
            addCriterion("few_copies <=", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesLike(String value) {
            addCriterion("few_copies like", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesNotLike(String value) {
            addCriterion("few_copies not like", value, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesIn(List<String> values) {
            addCriterion("few_copies in", values, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesNotIn(List<String> values) {
            addCriterion("few_copies not in", values, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesBetween(String value1, String value2) {
            addCriterion("few_copies between", value1, value2, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andFewCopiesNotBetween(String value1, String value2) {
            addCriterion("few_copies not between", value1, value2, "fewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesIsNull() {
            addCriterion("seller_few_copies is null");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesIsNotNull() {
            addCriterion("seller_few_copies is not null");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesEqualTo(String value) {
            addCriterion("seller_few_copies =", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesNotEqualTo(String value) {
            addCriterion("seller_few_copies <>", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesGreaterThan(String value) {
            addCriterion("seller_few_copies >", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesGreaterThanOrEqualTo(String value) {
            addCriterion("seller_few_copies >=", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesLessThan(String value) {
            addCriterion("seller_few_copies <", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesLessThanOrEqualTo(String value) {
            addCriterion("seller_few_copies <=", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesLike(String value) {
            addCriterion("seller_few_copies like", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesNotLike(String value) {
            addCriterion("seller_few_copies not like", value, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesIn(List<String> values) {
            addCriterion("seller_few_copies in", values, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesNotIn(List<String> values) {
            addCriterion("seller_few_copies not in", values, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesBetween(String value1, String value2) {
            addCriterion("seller_few_copies between", value1, value2, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andSellerFewCopiesNotBetween(String value1, String value2) {
            addCriterion("seller_few_copies not between", value1, value2, "sellerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesIsNull() {
            addCriterion("buyer_few_copies is null");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesIsNotNull() {
            addCriterion("buyer_few_copies is not null");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesEqualTo(String value) {
            addCriterion("buyer_few_copies =", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesNotEqualTo(String value) {
            addCriterion("buyer_few_copies <>", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesGreaterThan(String value) {
            addCriterion("buyer_few_copies >", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesGreaterThanOrEqualTo(String value) {
            addCriterion("buyer_few_copies >=", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesLessThan(String value) {
            addCriterion("buyer_few_copies <", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesLessThanOrEqualTo(String value) {
            addCriterion("buyer_few_copies <=", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesLike(String value) {
            addCriterion("buyer_few_copies like", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesNotLike(String value) {
            addCriterion("buyer_few_copies not like", value, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesIn(List<String> values) {
            addCriterion("buyer_few_copies in", values, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesNotIn(List<String> values) {
            addCriterion("buyer_few_copies not in", values, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesBetween(String value1, String value2) {
            addCriterion("buyer_few_copies between", value1, value2, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andBuyerFewCopiesNotBetween(String value1, String value2) {
            addCriterion("buyer_few_copies not between", value1, value2, "buyerFewCopies");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1IsNull() {
            addCriterion("appendices_name1 is null");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1IsNotNull() {
            addCriterion("appendices_name1 is not null");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1EqualTo(String value) {
            addCriterion("appendices_name1 =", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1NotEqualTo(String value) {
            addCriterion("appendices_name1 <>", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1GreaterThan(String value) {
            addCriterion("appendices_name1 >", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1GreaterThanOrEqualTo(String value) {
            addCriterion("appendices_name1 >=", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1LessThan(String value) {
            addCriterion("appendices_name1 <", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1LessThanOrEqualTo(String value) {
            addCriterion("appendices_name1 <=", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1Like(String value) {
            addCriterion("appendices_name1 like", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1NotLike(String value) {
            addCriterion("appendices_name1 not like", value, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1In(List<String> values) {
            addCriterion("appendices_name1 in", values, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1NotIn(List<String> values) {
            addCriterion("appendices_name1 not in", values, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1Between(String value1, String value2) {
            addCriterion("appendices_name1 between", value1, value2, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName1NotBetween(String value1, String value2) {
            addCriterion("appendices_name1 not between", value1, value2, "appendicesName1");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2IsNull() {
            addCriterion("appendices_name2 is null");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2IsNotNull() {
            addCriterion("appendices_name2 is not null");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2EqualTo(String value) {
            addCriterion("appendices_name2 =", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2NotEqualTo(String value) {
            addCriterion("appendices_name2 <>", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2GreaterThan(String value) {
            addCriterion("appendices_name2 >", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2GreaterThanOrEqualTo(String value) {
            addCriterion("appendices_name2 >=", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2LessThan(String value) {
            addCriterion("appendices_name2 <", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2LessThanOrEqualTo(String value) {
            addCriterion("appendices_name2 <=", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2Like(String value) {
            addCriterion("appendices_name2 like", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2NotLike(String value) {
            addCriterion("appendices_name2 not like", value, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2In(List<String> values) {
            addCriterion("appendices_name2 in", values, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2NotIn(List<String> values) {
            addCriterion("appendices_name2 not in", values, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2Between(String value1, String value2) {
            addCriterion("appendices_name2 between", value1, value2, "appendicesName2");
            return (Criteria) this;
        }

        public Criteria andAppendicesName2NotBetween(String value1, String value2) {
            addCriterion("appendices_name2 not between", value1, value2, "appendicesName2");
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