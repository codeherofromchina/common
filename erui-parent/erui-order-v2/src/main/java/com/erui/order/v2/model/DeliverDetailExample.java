package com.erui.order.v2.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DeliverDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DeliverDetailExample() {
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

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
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

        public Criteria andDeliverNoticeIdIsNull() {
            addCriterion("deliver_notice_id is null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdIsNotNull() {
            addCriterion("deliver_notice_id is not null");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdEqualTo(Integer value) {
            addCriterion("deliver_notice_id =", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotEqualTo(Integer value) {
            addCriterion("deliver_notice_id <>", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdGreaterThan(Integer value) {
            addCriterion("deliver_notice_id >", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("deliver_notice_id >=", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdLessThan(Integer value) {
            addCriterion("deliver_notice_id <", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdLessThanOrEqualTo(Integer value) {
            addCriterion("deliver_notice_id <=", value, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdIn(List<Integer> values) {
            addCriterion("deliver_notice_id in", values, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotIn(List<Integer> values) {
            addCriterion("deliver_notice_id not in", values, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdBetween(Integer value1, Integer value2) {
            addCriterion("deliver_notice_id between", value1, value2, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andDeliverNoticeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("deliver_notice_id not between", value1, value2, "deliverNoticeId");
            return (Criteria) this;
        }

        public Criteria andBillingDateIsNull() {
            addCriterion("billing_date is null");
            return (Criteria) this;
        }

        public Criteria andBillingDateIsNotNull() {
            addCriterion("billing_date is not null");
            return (Criteria) this;
        }

        public Criteria andBillingDateEqualTo(Date value) {
            addCriterionForJDBCDate("billing_date =", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("billing_date <>", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateGreaterThan(Date value) {
            addCriterionForJDBCDate("billing_date >", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("billing_date >=", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateLessThan(Date value) {
            addCriterionForJDBCDate("billing_date <", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("billing_date <=", value, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateIn(List<Date> values) {
            addCriterionForJDBCDate("billing_date in", values, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("billing_date not in", values, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("billing_date between", value1, value2, "billingDate");
            return (Criteria) this;
        }

        public Criteria andBillingDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("billing_date not between", value1, value2, "billingDate");
            return (Criteria) this;
        }

        public Criteria andCarrierCoIsNull() {
            addCriterion("carrier_co is null");
            return (Criteria) this;
        }

        public Criteria andCarrierCoIsNotNull() {
            addCriterion("carrier_co is not null");
            return (Criteria) this;
        }

        public Criteria andCarrierCoEqualTo(String value) {
            addCriterion("carrier_co =", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoNotEqualTo(String value) {
            addCriterion("carrier_co <>", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoGreaterThan(String value) {
            addCriterion("carrier_co >", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoGreaterThanOrEqualTo(String value) {
            addCriterion("carrier_co >=", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoLessThan(String value) {
            addCriterion("carrier_co <", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoLessThanOrEqualTo(String value) {
            addCriterion("carrier_co <=", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoLike(String value) {
            addCriterion("carrier_co like", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoNotLike(String value) {
            addCriterion("carrier_co not like", value, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoIn(List<String> values) {
            addCriterion("carrier_co in", values, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoNotIn(List<String> values) {
            addCriterion("carrier_co not in", values, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoBetween(String value1, String value2) {
            addCriterion("carrier_co between", value1, value2, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andCarrierCoNotBetween(String value1, String value2) {
            addCriterion("carrier_co not between", value1, value2, "carrierCo");
            return (Criteria) this;
        }

        public Criteria andDriverIsNull() {
            addCriterion("driver is null");
            return (Criteria) this;
        }

        public Criteria andDriverIsNotNull() {
            addCriterion("driver is not null");
            return (Criteria) this;
        }

        public Criteria andDriverEqualTo(String value) {
            addCriterion("driver =", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotEqualTo(String value) {
            addCriterion("driver <>", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverGreaterThan(String value) {
            addCriterion("driver >", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverGreaterThanOrEqualTo(String value) {
            addCriterion("driver >=", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLessThan(String value) {
            addCriterion("driver <", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLessThanOrEqualTo(String value) {
            addCriterion("driver <=", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverLike(String value) {
            addCriterion("driver like", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotLike(String value) {
            addCriterion("driver not like", value, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverIn(List<String> values) {
            addCriterion("driver in", values, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotIn(List<String> values) {
            addCriterion("driver not in", values, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverBetween(String value1, String value2) {
            addCriterion("driver between", value1, value2, "driver");
            return (Criteria) this;
        }

        public Criteria andDriverNotBetween(String value1, String value2) {
            addCriterion("driver not between", value1, value2, "driver");
            return (Criteria) this;
        }

        public Criteria andPlateNoIsNull() {
            addCriterion("plate_no is null");
            return (Criteria) this;
        }

        public Criteria andPlateNoIsNotNull() {
            addCriterion("plate_no is not null");
            return (Criteria) this;
        }

        public Criteria andPlateNoEqualTo(String value) {
            addCriterion("plate_no =", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoNotEqualTo(String value) {
            addCriterion("plate_no <>", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoGreaterThan(String value) {
            addCriterion("plate_no >", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoGreaterThanOrEqualTo(String value) {
            addCriterion("plate_no >=", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoLessThan(String value) {
            addCriterion("plate_no <", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoLessThanOrEqualTo(String value) {
            addCriterion("plate_no <=", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoLike(String value) {
            addCriterion("plate_no like", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoNotLike(String value) {
            addCriterion("plate_no not like", value, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoIn(List<String> values) {
            addCriterion("plate_no in", values, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoNotIn(List<String> values) {
            addCriterion("plate_no not in", values, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoBetween(String value1, String value2) {
            addCriterion("plate_no between", value1, value2, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPlateNoNotBetween(String value1, String value2) {
            addCriterion("plate_no not between", value1, value2, "plateNo");
            return (Criteria) this;
        }

        public Criteria andPickupDateIsNull() {
            addCriterion("pickup_date is null");
            return (Criteria) this;
        }

        public Criteria andPickupDateIsNotNull() {
            addCriterion("pickup_date is not null");
            return (Criteria) this;
        }

        public Criteria andPickupDateEqualTo(Date value) {
            addCriterionForJDBCDate("pickup_date =", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("pickup_date <>", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateGreaterThan(Date value) {
            addCriterionForJDBCDate("pickup_date >", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pickup_date >=", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateLessThan(Date value) {
            addCriterionForJDBCDate("pickup_date <", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("pickup_date <=", value, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateIn(List<Date> values) {
            addCriterionForJDBCDate("pickup_date in", values, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("pickup_date not in", values, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pickup_date between", value1, value2, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andPickupDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("pickup_date not between", value1, value2, "pickupDate");
            return (Criteria) this;
        }

        public Criteria andContactTelIsNull() {
            addCriterion("contact_tel is null");
            return (Criteria) this;
        }

        public Criteria andContactTelIsNotNull() {
            addCriterion("contact_tel is not null");
            return (Criteria) this;
        }

        public Criteria andContactTelEqualTo(String value) {
            addCriterion("contact_tel =", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelNotEqualTo(String value) {
            addCriterion("contact_tel <>", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelGreaterThan(String value) {
            addCriterion("contact_tel >", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelGreaterThanOrEqualTo(String value) {
            addCriterion("contact_tel >=", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelLessThan(String value) {
            addCriterion("contact_tel <", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelLessThanOrEqualTo(String value) {
            addCriterion("contact_tel <=", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelLike(String value) {
            addCriterion("contact_tel like", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelNotLike(String value) {
            addCriterion("contact_tel not like", value, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelIn(List<String> values) {
            addCriterion("contact_tel in", values, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelNotIn(List<String> values) {
            addCriterion("contact_tel not in", values, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelBetween(String value1, String value2) {
            addCriterion("contact_tel between", value1, value2, "contactTel");
            return (Criteria) this;
        }

        public Criteria andContactTelNotBetween(String value1, String value2) {
            addCriterion("contact_tel not between", value1, value2, "contactTel");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentIsNull() {
            addCriterion("handle_department is null");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentIsNotNull() {
            addCriterion("handle_department is not null");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentEqualTo(String value) {
            addCriterion("handle_department =", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentNotEqualTo(String value) {
            addCriterion("handle_department <>", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentGreaterThan(String value) {
            addCriterion("handle_department >", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentGreaterThanOrEqualTo(String value) {
            addCriterion("handle_department >=", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentLessThan(String value) {
            addCriterion("handle_department <", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentLessThanOrEqualTo(String value) {
            addCriterion("handle_department <=", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentLike(String value) {
            addCriterion("handle_department like", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentNotLike(String value) {
            addCriterion("handle_department not like", value, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentIn(List<String> values) {
            addCriterion("handle_department in", values, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentNotIn(List<String> values) {
            addCriterion("handle_department not in", values, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentBetween(String value1, String value2) {
            addCriterion("handle_department between", value1, value2, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andHandleDepartmentNotBetween(String value1, String value2) {
            addCriterion("handle_department not between", value1, value2, "handleDepartment");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIsNull() {
            addCriterion("ware_houseman is null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIsNotNull() {
            addCriterion("ware_houseman is not null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanEqualTo(Integer value) {
            addCriterion("ware_houseman =", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotEqualTo(Integer value) {
            addCriterion("ware_houseman <>", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanGreaterThan(Integer value) {
            addCriterion("ware_houseman >", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanGreaterThanOrEqualTo(Integer value) {
            addCriterion("ware_houseman >=", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanLessThan(Integer value) {
            addCriterion("ware_houseman <", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanLessThanOrEqualTo(Integer value) {
            addCriterion("ware_houseman <=", value, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanIn(List<Integer> values) {
            addCriterion("ware_houseman in", values, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotIn(List<Integer> values) {
            addCriterion("ware_houseman not in", values, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanBetween(Integer value1, Integer value2) {
            addCriterion("ware_houseman between", value1, value2, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNotBetween(Integer value1, Integer value2) {
            addCriterion("ware_houseman not between", value1, value2, "wareHouseman");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameIsNull() {
            addCriterion("ware_houseman_name is null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameIsNotNull() {
            addCriterion("ware_houseman_name is not null");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameEqualTo(String value) {
            addCriterion("ware_houseman_name =", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameNotEqualTo(String value) {
            addCriterion("ware_houseman_name <>", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameGreaterThan(String value) {
            addCriterion("ware_houseman_name >", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameGreaterThanOrEqualTo(String value) {
            addCriterion("ware_houseman_name >=", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameLessThan(String value) {
            addCriterion("ware_houseman_name <", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameLessThanOrEqualTo(String value) {
            addCriterion("ware_houseman_name <=", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameLike(String value) {
            addCriterion("ware_houseman_name like", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameNotLike(String value) {
            addCriterion("ware_houseman_name not like", value, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameIn(List<String> values) {
            addCriterion("ware_houseman_name in", values, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameNotIn(List<String> values) {
            addCriterion("ware_houseman_name not in", values, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameBetween(String value1, String value2) {
            addCriterion("ware_houseman_name between", value1, value2, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andWareHousemanNameNotBetween(String value1, String value2) {
            addCriterion("ware_houseman_name not between", value1, value2, "wareHousemanName");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNull() {
            addCriterion("send_date is null");
            return (Criteria) this;
        }

        public Criteria andSendDateIsNotNull() {
            addCriterion("send_date is not null");
            return (Criteria) this;
        }

        public Criteria andSendDateEqualTo(Date value) {
            addCriterionForJDBCDate("send_date =", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("send_date <>", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThan(Date value) {
            addCriterionForJDBCDate("send_date >", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("send_date >=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThan(Date value) {
            addCriterionForJDBCDate("send_date <", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("send_date <=", value, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateIn(List<Date> values) {
            addCriterionForJDBCDate("send_date in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("send_date not in", values, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("send_date between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSendDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("send_date not between", value1, value2, "sendDate");
            return (Criteria) this;
        }

        public Criteria andSenderIsNull() {
            addCriterion("sender is null");
            return (Criteria) this;
        }

        public Criteria andSenderIsNotNull() {
            addCriterion("sender is not null");
            return (Criteria) this;
        }

        public Criteria andSenderEqualTo(Integer value) {
            addCriterion("sender =", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotEqualTo(Integer value) {
            addCriterion("sender <>", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThan(Integer value) {
            addCriterion("sender >", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderGreaterThanOrEqualTo(Integer value) {
            addCriterion("sender >=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThan(Integer value) {
            addCriterion("sender <", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderLessThanOrEqualTo(Integer value) {
            addCriterion("sender <=", value, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderIn(List<Integer> values) {
            addCriterion("sender in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotIn(List<Integer> values) {
            addCriterion("sender not in", values, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderBetween(Integer value1, Integer value2) {
            addCriterion("sender between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNotBetween(Integer value1, Integer value2) {
            addCriterion("sender not between", value1, value2, "sender");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNull() {
            addCriterion("sender_name is null");
            return (Criteria) this;
        }

        public Criteria andSenderNameIsNotNull() {
            addCriterion("sender_name is not null");
            return (Criteria) this;
        }

        public Criteria andSenderNameEqualTo(String value) {
            addCriterion("sender_name =", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotEqualTo(String value) {
            addCriterion("sender_name <>", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThan(String value) {
            addCriterion("sender_name >", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameGreaterThanOrEqualTo(String value) {
            addCriterion("sender_name >=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThan(String value) {
            addCriterion("sender_name <", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLessThanOrEqualTo(String value) {
            addCriterion("sender_name <=", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameLike(String value) {
            addCriterion("sender_name like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotLike(String value) {
            addCriterion("sender_name not like", value, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameIn(List<String> values) {
            addCriterion("sender_name in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotIn(List<String> values) {
            addCriterion("sender_name not in", values, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameBetween(String value1, String value2) {
            addCriterion("sender_name between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andSenderNameNotBetween(String value1, String value2) {
            addCriterion("sender_name not between", value1, value2, "senderName");
            return (Criteria) this;
        }

        public Criteria andReviewerIsNull() {
            addCriterion("reviewer is null");
            return (Criteria) this;
        }

        public Criteria andReviewerIsNotNull() {
            addCriterion("reviewer is not null");
            return (Criteria) this;
        }

        public Criteria andReviewerEqualTo(Integer value) {
            addCriterion("reviewer =", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerNotEqualTo(Integer value) {
            addCriterion("reviewer <>", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerGreaterThan(Integer value) {
            addCriterion("reviewer >", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerGreaterThanOrEqualTo(Integer value) {
            addCriterion("reviewer >=", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerLessThan(Integer value) {
            addCriterion("reviewer <", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerLessThanOrEqualTo(Integer value) {
            addCriterion("reviewer <=", value, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerIn(List<Integer> values) {
            addCriterion("reviewer in", values, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerNotIn(List<Integer> values) {
            addCriterion("reviewer not in", values, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerBetween(Integer value1, Integer value2) {
            addCriterion("reviewer between", value1, value2, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerNotBetween(Integer value1, Integer value2) {
            addCriterion("reviewer not between", value1, value2, "reviewer");
            return (Criteria) this;
        }

        public Criteria andReviewerNameIsNull() {
            addCriterion("reviewer_name is null");
            return (Criteria) this;
        }

        public Criteria andReviewerNameIsNotNull() {
            addCriterion("reviewer_name is not null");
            return (Criteria) this;
        }

        public Criteria andReviewerNameEqualTo(String value) {
            addCriterion("reviewer_name =", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameNotEqualTo(String value) {
            addCriterion("reviewer_name <>", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameGreaterThan(String value) {
            addCriterion("reviewer_name >", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameGreaterThanOrEqualTo(String value) {
            addCriterion("reviewer_name >=", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameLessThan(String value) {
            addCriterion("reviewer_name <", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameLessThanOrEqualTo(String value) {
            addCriterion("reviewer_name <=", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameLike(String value) {
            addCriterion("reviewer_name like", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameNotLike(String value) {
            addCriterion("reviewer_name not like", value, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameIn(List<String> values) {
            addCriterion("reviewer_name in", values, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameNotIn(List<String> values) {
            addCriterion("reviewer_name not in", values, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameBetween(String value1, String value2) {
            addCriterion("reviewer_name between", value1, value2, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andReviewerNameNotBetween(String value1, String value2) {
            addCriterion("reviewer_name not between", value1, value2, "reviewerName");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusIsNull() {
            addCriterion("goods_chk_status is null");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusIsNotNull() {
            addCriterion("goods_chk_status is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusEqualTo(String value) {
            addCriterion("goods_chk_status =", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusNotEqualTo(String value) {
            addCriterion("goods_chk_status <>", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusGreaterThan(String value) {
            addCriterion("goods_chk_status >", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusGreaterThanOrEqualTo(String value) {
            addCriterion("goods_chk_status >=", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusLessThan(String value) {
            addCriterion("goods_chk_status <", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusLessThanOrEqualTo(String value) {
            addCriterion("goods_chk_status <=", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusLike(String value) {
            addCriterion("goods_chk_status like", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusNotLike(String value) {
            addCriterion("goods_chk_status not like", value, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusIn(List<String> values) {
            addCriterion("goods_chk_status in", values, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusNotIn(List<String> values) {
            addCriterion("goods_chk_status not in", values, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusBetween(String value1, String value2) {
            addCriterion("goods_chk_status between", value1, value2, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andGoodsChkStatusNotBetween(String value1, String value2) {
            addCriterion("goods_chk_status not between", value1, value2, "goodsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusIsNull() {
            addCriterion("bills_chk_status is null");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusIsNotNull() {
            addCriterion("bills_chk_status is not null");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusEqualTo(String value) {
            addCriterion("bills_chk_status =", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusNotEqualTo(String value) {
            addCriterion("bills_chk_status <>", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusGreaterThan(String value) {
            addCriterion("bills_chk_status >", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusGreaterThanOrEqualTo(String value) {
            addCriterion("bills_chk_status >=", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusLessThan(String value) {
            addCriterion("bills_chk_status <", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusLessThanOrEqualTo(String value) {
            addCriterion("bills_chk_status <=", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusLike(String value) {
            addCriterion("bills_chk_status like", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusNotLike(String value) {
            addCriterion("bills_chk_status not like", value, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusIn(List<String> values) {
            addCriterion("bills_chk_status in", values, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusNotIn(List<String> values) {
            addCriterion("bills_chk_status not in", values, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusBetween(String value1, String value2) {
            addCriterion("bills_chk_status between", value1, value2, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andBillsChkStatusNotBetween(String value1, String value2) {
            addCriterion("bills_chk_status not between", value1, value2, "billsChkStatus");
            return (Criteria) this;
        }

        public Criteria andCheckerUidIsNull() {
            addCriterion("checker_uid is null");
            return (Criteria) this;
        }

        public Criteria andCheckerUidIsNotNull() {
            addCriterion("checker_uid is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerUidEqualTo(Integer value) {
            addCriterion("checker_uid =", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidNotEqualTo(Integer value) {
            addCriterion("checker_uid <>", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidGreaterThan(Integer value) {
            addCriterion("checker_uid >", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("checker_uid >=", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidLessThan(Integer value) {
            addCriterion("checker_uid <", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidLessThanOrEqualTo(Integer value) {
            addCriterion("checker_uid <=", value, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidIn(List<Integer> values) {
            addCriterion("checker_uid in", values, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidNotIn(List<Integer> values) {
            addCriterion("checker_uid not in", values, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidBetween(Integer value1, Integer value2) {
            addCriterion("checker_uid between", value1, value2, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerUidNotBetween(Integer value1, Integer value2) {
            addCriterion("checker_uid not between", value1, value2, "checkerUid");
            return (Criteria) this;
        }

        public Criteria andCheckerNameIsNull() {
            addCriterion("checker_name is null");
            return (Criteria) this;
        }

        public Criteria andCheckerNameIsNotNull() {
            addCriterion("checker_name is not null");
            return (Criteria) this;
        }

        public Criteria andCheckerNameEqualTo(String value) {
            addCriterion("checker_name =", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameNotEqualTo(String value) {
            addCriterion("checker_name <>", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameGreaterThan(String value) {
            addCriterion("checker_name >", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameGreaterThanOrEqualTo(String value) {
            addCriterion("checker_name >=", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameLessThan(String value) {
            addCriterion("checker_name <", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameLessThanOrEqualTo(String value) {
            addCriterion("checker_name <=", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameLike(String value) {
            addCriterion("checker_name like", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameNotLike(String value) {
            addCriterion("checker_name not like", value, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameIn(List<String> values) {
            addCriterion("checker_name in", values, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameNotIn(List<String> values) {
            addCriterion("checker_name not in", values, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameBetween(String value1, String value2) {
            addCriterion("checker_name between", value1, value2, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckerNameNotBetween(String value1, String value2) {
            addCriterion("checker_name not between", value1, value2, "checkerName");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIsNull() {
            addCriterion("check_dept is null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIsNotNull() {
            addCriterion("check_dept is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDeptEqualTo(String value) {
            addCriterion("check_dept =", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNotEqualTo(String value) {
            addCriterion("check_dept <>", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptGreaterThan(String value) {
            addCriterion("check_dept >", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptGreaterThanOrEqualTo(String value) {
            addCriterion("check_dept >=", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptLessThan(String value) {
            addCriterion("check_dept <", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptLessThanOrEqualTo(String value) {
            addCriterion("check_dept <=", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptLike(String value) {
            addCriterion("check_dept like", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNotLike(String value) {
            addCriterion("check_dept not like", value, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptIn(List<String> values) {
            addCriterion("check_dept in", values, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNotIn(List<String> values) {
            addCriterion("check_dept not in", values, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptBetween(String value1, String value2) {
            addCriterion("check_dept between", value1, value2, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDeptNotBetween(String value1, String value2) {
            addCriterion("check_dept not between", value1, value2, "checkDept");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNull() {
            addCriterion("check_date is null");
            return (Criteria) this;
        }

        public Criteria andCheckDateIsNotNull() {
            addCriterion("check_date is not null");
            return (Criteria) this;
        }

        public Criteria andCheckDateEqualTo(Date value) {
            addCriterionForJDBCDate("check_date =", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <>", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThan(Date value) {
            addCriterionForJDBCDate("check_date >", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date >=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThan(Date value) {
            addCriterionForJDBCDate("check_date <", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("check_date <=", value, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateIn(List<Date> values) {
            addCriterionForJDBCDate("check_date in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("check_date not in", values, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andCheckDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("check_date not between", value1, value2, "checkDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNull() {
            addCriterion("release_date is null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNotNull() {
            addCriterion("release_date is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateEqualTo(Date value) {
            addCriterionForJDBCDate("release_date =", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("release_date <>", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThan(Date value) {
            addCriterionForJDBCDate("release_date >", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("release_date >=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThan(Date value) {
            addCriterionForJDBCDate("release_date <", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("release_date <=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIn(List<Date> values) {
            addCriterionForJDBCDate("release_date in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("release_date not in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("release_date between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("release_date not between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseNameIsNull() {
            addCriterion("release_name is null");
            return (Criteria) this;
        }

        public Criteria andReleaseNameIsNotNull() {
            addCriterion("release_name is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseNameEqualTo(String value) {
            addCriterion("release_name =", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameNotEqualTo(String value) {
            addCriterion("release_name <>", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameGreaterThan(String value) {
            addCriterion("release_name >", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameGreaterThanOrEqualTo(String value) {
            addCriterion("release_name >=", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameLessThan(String value) {
            addCriterion("release_name <", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameLessThanOrEqualTo(String value) {
            addCriterion("release_name <=", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameLike(String value) {
            addCriterion("release_name like", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameNotLike(String value) {
            addCriterion("release_name not like", value, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameIn(List<String> values) {
            addCriterion("release_name in", values, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameNotIn(List<String> values) {
            addCriterion("release_name not in", values, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameBetween(String value1, String value2) {
            addCriterion("release_name between", value1, value2, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseNameNotBetween(String value1, String value2) {
            addCriterion("release_name not between", value1, value2, "releaseName");
            return (Criteria) this;
        }

        public Criteria andReleaseUidIsNull() {
            addCriterion("release_uid is null");
            return (Criteria) this;
        }

        public Criteria andReleaseUidIsNotNull() {
            addCriterion("release_uid is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseUidEqualTo(Integer value) {
            addCriterion("release_uid =", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidNotEqualTo(Integer value) {
            addCriterion("release_uid <>", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidGreaterThan(Integer value) {
            addCriterion("release_uid >", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidGreaterThanOrEqualTo(Integer value) {
            addCriterion("release_uid >=", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidLessThan(Integer value) {
            addCriterion("release_uid <", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidLessThanOrEqualTo(Integer value) {
            addCriterion("release_uid <=", value, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidIn(List<Integer> values) {
            addCriterion("release_uid in", values, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidNotIn(List<Integer> values) {
            addCriterion("release_uid not in", values, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidBetween(Integer value1, Integer value2) {
            addCriterion("release_uid between", value1, value2, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andReleaseUidNotBetween(Integer value1, Integer value2) {
            addCriterion("release_uid not between", value1, value2, "releaseUid");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdIsNull() {
            addCriterion("quality_leader_id is null");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdIsNotNull() {
            addCriterion("quality_leader_id is not null");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdEqualTo(Integer value) {
            addCriterion("quality_leader_id =", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdNotEqualTo(Integer value) {
            addCriterion("quality_leader_id <>", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdGreaterThan(Integer value) {
            addCriterion("quality_leader_id >", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("quality_leader_id >=", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdLessThan(Integer value) {
            addCriterion("quality_leader_id <", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdLessThanOrEqualTo(Integer value) {
            addCriterion("quality_leader_id <=", value, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdIn(List<Integer> values) {
            addCriterion("quality_leader_id in", values, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdNotIn(List<Integer> values) {
            addCriterion("quality_leader_id not in", values, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdBetween(Integer value1, Integer value2) {
            addCriterion("quality_leader_id between", value1, value2, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("quality_leader_id not between", value1, value2, "qualityLeaderId");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameIsNull() {
            addCriterion("quality_leader_name is null");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameIsNotNull() {
            addCriterion("quality_leader_name is not null");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameEqualTo(String value) {
            addCriterion("quality_leader_name =", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameNotEqualTo(String value) {
            addCriterion("quality_leader_name <>", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameGreaterThan(String value) {
            addCriterion("quality_leader_name >", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameGreaterThanOrEqualTo(String value) {
            addCriterion("quality_leader_name >=", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameLessThan(String value) {
            addCriterion("quality_leader_name <", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameLessThanOrEqualTo(String value) {
            addCriterion("quality_leader_name <=", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameLike(String value) {
            addCriterion("quality_leader_name like", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameNotLike(String value) {
            addCriterion("quality_leader_name not like", value, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameIn(List<String> values) {
            addCriterion("quality_leader_name in", values, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameNotIn(List<String> values) {
            addCriterion("quality_leader_name not in", values, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameBetween(String value1, String value2) {
            addCriterion("quality_leader_name between", value1, value2, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andQualityLeaderNameNotBetween(String value1, String value2) {
            addCriterion("quality_leader_name not between", value1, value2, "qualityLeaderName");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNull() {
            addCriterion("applicant is null");
            return (Criteria) this;
        }

        public Criteria andApplicantIsNotNull() {
            addCriterion("applicant is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantEqualTo(Integer value) {
            addCriterion("applicant =", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotEqualTo(Integer value) {
            addCriterion("applicant <>", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThan(Integer value) {
            addCriterion("applicant >", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantGreaterThanOrEqualTo(Integer value) {
            addCriterion("applicant >=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThan(Integer value) {
            addCriterion("applicant <", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantLessThanOrEqualTo(Integer value) {
            addCriterion("applicant <=", value, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantIn(List<Integer> values) {
            addCriterion("applicant in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotIn(List<Integer> values) {
            addCriterion("applicant not in", values, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantBetween(Integer value1, Integer value2) {
            addCriterion("applicant between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNotBetween(Integer value1, Integer value2) {
            addCriterion("applicant not between", value1, value2, "applicant");
            return (Criteria) this;
        }

        public Criteria andApplicantNameIsNull() {
            addCriterion("applicant_name is null");
            return (Criteria) this;
        }

        public Criteria andApplicantNameIsNotNull() {
            addCriterion("applicant_name is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantNameEqualTo(String value) {
            addCriterion("applicant_name =", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameNotEqualTo(String value) {
            addCriterion("applicant_name <>", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameGreaterThan(String value) {
            addCriterion("applicant_name >", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameGreaterThanOrEqualTo(String value) {
            addCriterion("applicant_name >=", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameLessThan(String value) {
            addCriterion("applicant_name <", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameLessThanOrEqualTo(String value) {
            addCriterion("applicant_name <=", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameLike(String value) {
            addCriterion("applicant_name like", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameNotLike(String value) {
            addCriterion("applicant_name not like", value, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameIn(List<String> values) {
            addCriterion("applicant_name in", values, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameNotIn(List<String> values) {
            addCriterion("applicant_name not in", values, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameBetween(String value1, String value2) {
            addCriterion("applicant_name between", value1, value2, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantNameNotBetween(String value1, String value2) {
            addCriterion("applicant_name not between", value1, value2, "applicantName");
            return (Criteria) this;
        }

        public Criteria andApplicantDateIsNull() {
            addCriterion("applicant_date is null");
            return (Criteria) this;
        }

        public Criteria andApplicantDateIsNotNull() {
            addCriterion("applicant_date is not null");
            return (Criteria) this;
        }

        public Criteria andApplicantDateEqualTo(Date value) {
            addCriterionForJDBCDate("applicant_date =", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("applicant_date <>", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateGreaterThan(Date value) {
            addCriterionForJDBCDate("applicant_date >", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("applicant_date >=", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateLessThan(Date value) {
            addCriterionForJDBCDate("applicant_date <", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("applicant_date <=", value, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateIn(List<Date> values) {
            addCriterionForJDBCDate("applicant_date in", values, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("applicant_date not in", values, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("applicant_date between", value1, value2, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApplicantDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("applicant_date not between", value1, value2, "applicantDate");
            return (Criteria) this;
        }

        public Criteria andApproverIsNull() {
            addCriterion("approver is null");
            return (Criteria) this;
        }

        public Criteria andApproverIsNotNull() {
            addCriterion("approver is not null");
            return (Criteria) this;
        }

        public Criteria andApproverEqualTo(Integer value) {
            addCriterion("approver =", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotEqualTo(Integer value) {
            addCriterion("approver <>", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThan(Integer value) {
            addCriterion("approver >", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverGreaterThanOrEqualTo(Integer value) {
            addCriterion("approver >=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThan(Integer value) {
            addCriterion("approver <", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverLessThanOrEqualTo(Integer value) {
            addCriterion("approver <=", value, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverIn(List<Integer> values) {
            addCriterion("approver in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotIn(List<Integer> values) {
            addCriterion("approver not in", values, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverBetween(Integer value1, Integer value2) {
            addCriterion("approver between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNotBetween(Integer value1, Integer value2) {
            addCriterion("approver not between", value1, value2, "approver");
            return (Criteria) this;
        }

        public Criteria andApproverNameIsNull() {
            addCriterion("approver_name is null");
            return (Criteria) this;
        }

        public Criteria andApproverNameIsNotNull() {
            addCriterion("approver_name is not null");
            return (Criteria) this;
        }

        public Criteria andApproverNameEqualTo(String value) {
            addCriterion("approver_name =", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameNotEqualTo(String value) {
            addCriterion("approver_name <>", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameGreaterThan(String value) {
            addCriterion("approver_name >", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameGreaterThanOrEqualTo(String value) {
            addCriterion("approver_name >=", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameLessThan(String value) {
            addCriterion("approver_name <", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameLessThanOrEqualTo(String value) {
            addCriterion("approver_name <=", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameLike(String value) {
            addCriterion("approver_name like", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameNotLike(String value) {
            addCriterion("approver_name not like", value, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameIn(List<String> values) {
            addCriterion("approver_name in", values, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameNotIn(List<String> values) {
            addCriterion("approver_name not in", values, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameBetween(String value1, String value2) {
            addCriterion("approver_name between", value1, value2, "approverName");
            return (Criteria) this;
        }

        public Criteria andApproverNameNotBetween(String value1, String value2) {
            addCriterion("approver_name not between", value1, value2, "approverName");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNull() {
            addCriterion("approval_date is null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIsNotNull() {
            addCriterion("approval_date is not null");
            return (Criteria) this;
        }

        public Criteria andApprovalDateEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date =", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <>", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThan(Date value) {
            addCriterionForJDBCDate("approval_date >", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date >=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThan(Date value) {
            addCriterionForJDBCDate("approval_date <", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("approval_date <=", value, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("approval_date not in", values, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andApprovalDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("approval_date not between", value1, value2, "approvalDate");
            return (Criteria) this;
        }

        public Criteria andOpinionIsNull() {
            addCriterion("opinion is null");
            return (Criteria) this;
        }

        public Criteria andOpinionIsNotNull() {
            addCriterion("opinion is not null");
            return (Criteria) this;
        }

        public Criteria andOpinionEqualTo(String value) {
            addCriterion("opinion =", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionNotEqualTo(String value) {
            addCriterion("opinion <>", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionGreaterThan(String value) {
            addCriterion("opinion >", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionGreaterThanOrEqualTo(String value) {
            addCriterion("opinion >=", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionLessThan(String value) {
            addCriterion("opinion <", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionLessThanOrEqualTo(String value) {
            addCriterion("opinion <=", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionLike(String value) {
            addCriterion("opinion like", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionNotLike(String value) {
            addCriterion("opinion not like", value, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionIn(List<String> values) {
            addCriterion("opinion in", values, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionNotIn(List<String> values) {
            addCriterion("opinion not in", values, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionBetween(String value1, String value2) {
            addCriterion("opinion between", value1, value2, "opinion");
            return (Criteria) this;
        }

        public Criteria andOpinionNotBetween(String value1, String value2) {
            addCriterion("opinion not between", value1, value2, "opinion");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIsNull() {
            addCriterion("leave_date is null");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIsNotNull() {
            addCriterion("leave_date is not null");
            return (Criteria) this;
        }

        public Criteria andLeaveDateEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date =", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date <>", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateGreaterThan(Date value) {
            addCriterionForJDBCDate("leave_date >", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date >=", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateLessThan(Date value) {
            addCriterionForJDBCDate("leave_date <", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("leave_date <=", value, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateIn(List<Date> values) {
            addCriterionForJDBCDate("leave_date in", values, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("leave_date not in", values, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("leave_date between", value1, value2, "leaveDate");
            return (Criteria) this;
        }

        public Criteria andLeaveDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("leave_date not between", value1, value2, "leaveDate");
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

        public Criteria andLogisticsDateIsNull() {
            addCriterion("logistics_date is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateIsNotNull() {
            addCriterion("logistics_date is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateEqualTo(Date value) {
            addCriterionForJDBCDate("logistics_date =", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateNotEqualTo(Date value) {
            addCriterionForJDBCDate("logistics_date <>", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateGreaterThan(Date value) {
            addCriterionForJDBCDate("logistics_date >", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("logistics_date >=", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateLessThan(Date value) {
            addCriterionForJDBCDate("logistics_date <", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("logistics_date <=", value, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateIn(List<Date> values) {
            addCriterionForJDBCDate("logistics_date in", values, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateNotIn(List<Date> values) {
            addCriterionForJDBCDate("logistics_date not in", values, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("logistics_date between", value1, value2, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andLogisticsDateNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("logistics_date not between", value1, value2, "logisticsDate");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIsNull() {
            addCriterion("booking_time is null");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIsNotNull() {
            addCriterion("booking_time is not null");
            return (Criteria) this;
        }

        public Criteria andBookingTimeEqualTo(Date value) {
            addCriterion("booking_time =", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotEqualTo(Date value) {
            addCriterion("booking_time <>", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeGreaterThan(Date value) {
            addCriterion("booking_time >", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("booking_time >=", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeLessThan(Date value) {
            addCriterion("booking_time <", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeLessThanOrEqualTo(Date value) {
            addCriterion("booking_time <=", value, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeIn(List<Date> values) {
            addCriterion("booking_time in", values, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotIn(List<Date> values) {
            addCriterion("booking_time not in", values, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeBetween(Date value1, Date value2) {
            addCriterion("booking_time between", value1, value2, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andBookingTimeNotBetween(Date value1, Date value2) {
            addCriterion("booking_time not between", value1, value2, "bookingTime");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoIsNull() {
            addCriterion("logi_invoice_no is null");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoIsNotNull() {
            addCriterion("logi_invoice_no is not null");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoEqualTo(String value) {
            addCriterion("logi_invoice_no =", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoNotEqualTo(String value) {
            addCriterion("logi_invoice_no <>", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoGreaterThan(String value) {
            addCriterion("logi_invoice_no >", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoGreaterThanOrEqualTo(String value) {
            addCriterion("logi_invoice_no >=", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoLessThan(String value) {
            addCriterion("logi_invoice_no <", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoLessThanOrEqualTo(String value) {
            addCriterion("logi_invoice_no <=", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoLike(String value) {
            addCriterion("logi_invoice_no like", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoNotLike(String value) {
            addCriterion("logi_invoice_no not like", value, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoIn(List<String> values) {
            addCriterion("logi_invoice_no in", values, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoNotIn(List<String> values) {
            addCriterion("logi_invoice_no not in", values, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoBetween(String value1, String value2) {
            addCriterion("logi_invoice_no between", value1, value2, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andLogiInvoiceNoNotBetween(String value1, String value2) {
            addCriterion("logi_invoice_no not between", value1, value2, "logiInvoiceNo");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIsNull() {
            addCriterion("packing_time is null");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIsNotNull() {
            addCriterion("packing_time is not null");
            return (Criteria) this;
        }

        public Criteria andPackingTimeEqualTo(Date value) {
            addCriterion("packing_time =", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotEqualTo(Date value) {
            addCriterion("packing_time <>", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeGreaterThan(Date value) {
            addCriterion("packing_time >", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("packing_time >=", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeLessThan(Date value) {
            addCriterion("packing_time <", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeLessThanOrEqualTo(Date value) {
            addCriterion("packing_time <=", value, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeIn(List<Date> values) {
            addCriterion("packing_time in", values, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotIn(List<Date> values) {
            addCriterion("packing_time not in", values, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeBetween(Date value1, Date value2) {
            addCriterion("packing_time between", value1, value2, "packingTime");
            return (Criteria) this;
        }

        public Criteria andPackingTimeNotBetween(Date value1, Date value2) {
            addCriterion("packing_time not between", value1, value2, "packingTime");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryIsNull() {
            addCriterion("leave_factory is null");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryIsNotNull() {
            addCriterion("leave_factory is not null");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryEqualTo(Date value) {
            addCriterion("leave_factory =", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryNotEqualTo(Date value) {
            addCriterion("leave_factory <>", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryGreaterThan(Date value) {
            addCriterion("leave_factory >", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryGreaterThanOrEqualTo(Date value) {
            addCriterion("leave_factory >=", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryLessThan(Date value) {
            addCriterion("leave_factory <", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryLessThanOrEqualTo(Date value) {
            addCriterion("leave_factory <=", value, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryIn(List<Date> values) {
            addCriterion("leave_factory in", values, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryNotIn(List<Date> values) {
            addCriterion("leave_factory not in", values, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryBetween(Date value1, Date value2) {
            addCriterion("leave_factory between", value1, value2, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andLeaveFactoryNotBetween(Date value1, Date value2) {
            addCriterion("leave_factory not between", value1, value2, "leaveFactory");
            return (Criteria) this;
        }

        public Criteria andSailingDateIsNull() {
            addCriterion("sailing_date is null");
            return (Criteria) this;
        }

        public Criteria andSailingDateIsNotNull() {
            addCriterion("sailing_date is not null");
            return (Criteria) this;
        }

        public Criteria andSailingDateEqualTo(Date value) {
            addCriterion("sailing_date =", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotEqualTo(Date value) {
            addCriterion("sailing_date <>", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateGreaterThan(Date value) {
            addCriterion("sailing_date >", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateGreaterThanOrEqualTo(Date value) {
            addCriterion("sailing_date >=", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateLessThan(Date value) {
            addCriterion("sailing_date <", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateLessThanOrEqualTo(Date value) {
            addCriterion("sailing_date <=", value, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateIn(List<Date> values) {
            addCriterion("sailing_date in", values, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotIn(List<Date> values) {
            addCriterion("sailing_date not in", values, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateBetween(Date value1, Date value2) {
            addCriterion("sailing_date between", value1, value2, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andSailingDateNotBetween(Date value1, Date value2) {
            addCriterion("sailing_date not between", value1, value2, "sailingDate");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIsNull() {
            addCriterion("customs_clearance is null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIsNotNull() {
            addCriterion("customs_clearance is not null");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceEqualTo(Date value) {
            addCriterion("customs_clearance =", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotEqualTo(Date value) {
            addCriterion("customs_clearance <>", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceGreaterThan(Date value) {
            addCriterion("customs_clearance >", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceGreaterThanOrEqualTo(Date value) {
            addCriterion("customs_clearance >=", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceLessThan(Date value) {
            addCriterion("customs_clearance <", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceLessThanOrEqualTo(Date value) {
            addCriterion("customs_clearance <=", value, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceIn(List<Date> values) {
            addCriterion("customs_clearance in", values, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotIn(List<Date> values) {
            addCriterion("customs_clearance not in", values, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceBetween(Date value1, Date value2) {
            addCriterion("customs_clearance between", value1, value2, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andCustomsClearanceNotBetween(Date value1, Date value2) {
            addCriterion("customs_clearance not between", value1, value2, "customsClearance");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIsNull() {
            addCriterion("leave_port_time is null");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIsNotNull() {
            addCriterion("leave_port_time is not null");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeEqualTo(Date value) {
            addCriterion("leave_port_time =", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotEqualTo(Date value) {
            addCriterion("leave_port_time <>", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeGreaterThan(Date value) {
            addCriterion("leave_port_time >", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("leave_port_time >=", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeLessThan(Date value) {
            addCriterion("leave_port_time <", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeLessThanOrEqualTo(Date value) {
            addCriterion("leave_port_time <=", value, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeIn(List<Date> values) {
            addCriterion("leave_port_time in", values, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotIn(List<Date> values) {
            addCriterion("leave_port_time not in", values, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeBetween(Date value1, Date value2) {
            addCriterion("leave_port_time between", value1, value2, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andLeavePortTimeNotBetween(Date value1, Date value2) {
            addCriterion("leave_port_time not between", value1, value2, "leavePortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIsNull() {
            addCriterion("arrival_port_time is null");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIsNotNull() {
            addCriterion("arrival_port_time is not null");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeEqualTo(Date value) {
            addCriterion("arrival_port_time =", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotEqualTo(Date value) {
            addCriterion("arrival_port_time <>", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeGreaterThan(Date value) {
            addCriterion("arrival_port_time >", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("arrival_port_time >=", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeLessThan(Date value) {
            addCriterion("arrival_port_time <", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeLessThanOrEqualTo(Date value) {
            addCriterion("arrival_port_time <=", value, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeIn(List<Date> values) {
            addCriterion("arrival_port_time in", values, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotIn(List<Date> values) {
            addCriterion("arrival_port_time not in", values, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeBetween(Date value1, Date value2) {
            addCriterion("arrival_port_time between", value1, value2, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andArrivalPortTimeNotBetween(Date value1, Date value2) {
            addCriterion("arrival_port_time not between", value1, value2, "arrivalPortTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNull() {
            addCriterion("create_user_name is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIsNotNull() {
            addCriterion("create_user_name is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameEqualTo(String value) {
            addCriterion("create_user_name =", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotEqualTo(String value) {
            addCriterion("create_user_name <>", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThan(String value) {
            addCriterion("create_user_name >", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("create_user_name >=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThan(String value) {
            addCriterion("create_user_name <", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLessThanOrEqualTo(String value) {
            addCriterion("create_user_name <=", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameLike(String value) {
            addCriterion("create_user_name like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotLike(String value) {
            addCriterion("create_user_name not like", value, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameIn(List<String> values) {
            addCriterion("create_user_name in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotIn(List<String> values) {
            addCriterion("create_user_name not in", values, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameBetween(String value1, String value2) {
            addCriterion("create_user_name between", value1, value2, "createUserName");
            return (Criteria) this;
        }

        public Criteria andCreateUserNameNotBetween(String value1, String value2) {
            addCriterion("create_user_name not between", value1, value2, "createUserName");
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

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andPackTotalIsNull() {
            addCriterion("pack_total is null");
            return (Criteria) this;
        }

        public Criteria andPackTotalIsNotNull() {
            addCriterion("pack_total is not null");
            return (Criteria) this;
        }

        public Criteria andPackTotalEqualTo(Integer value) {
            addCriterion("pack_total =", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalNotEqualTo(Integer value) {
            addCriterion("pack_total <>", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalGreaterThan(Integer value) {
            addCriterion("pack_total >", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalGreaterThanOrEqualTo(Integer value) {
            addCriterion("pack_total >=", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalLessThan(Integer value) {
            addCriterion("pack_total <", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalLessThanOrEqualTo(Integer value) {
            addCriterion("pack_total <=", value, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalIn(List<Integer> values) {
            addCriterion("pack_total in", values, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalNotIn(List<Integer> values) {
            addCriterion("pack_total not in", values, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalBetween(Integer value1, Integer value2) {
            addCriterion("pack_total between", value1, value2, "packTotal");
            return (Criteria) this;
        }

        public Criteria andPackTotalNotBetween(Integer value1, Integer value2) {
            addCriterion("pack_total not between", value1, value2, "packTotal");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeIsNull() {
            addCriterion("the_end_time is null");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeIsNotNull() {
            addCriterion("the_end_time is not null");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeEqualTo(Date value) {
            addCriterion("the_end_time =", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeNotEqualTo(Date value) {
            addCriterion("the_end_time <>", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeGreaterThan(Date value) {
            addCriterion("the_end_time >", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("the_end_time >=", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeLessThan(Date value) {
            addCriterion("the_end_time <", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("the_end_time <=", value, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeIn(List<Date> values) {
            addCriterion("the_end_time in", values, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeNotIn(List<Date> values) {
            addCriterion("the_end_time not in", values, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeBetween(Date value1, Date value2) {
            addCriterion("the_end_time between", value1, value2, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andTheEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("the_end_time not between", value1, value2, "theEndTime");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsIsNull() {
            addCriterion("confirm_the_goods is null");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsIsNotNull() {
            addCriterion("confirm_the_goods is not null");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsEqualTo(Date value) {
            addCriterion("confirm_the_goods =", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsNotEqualTo(Date value) {
            addCriterion("confirm_the_goods <>", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsGreaterThan(Date value) {
            addCriterion("confirm_the_goods >", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsGreaterThanOrEqualTo(Date value) {
            addCriterion("confirm_the_goods >=", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsLessThan(Date value) {
            addCriterion("confirm_the_goods <", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsLessThanOrEqualTo(Date value) {
            addCriterion("confirm_the_goods <=", value, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsIn(List<Date> values) {
            addCriterion("confirm_the_goods in", values, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsNotIn(List<Date> values) {
            addCriterion("confirm_the_goods not in", values, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsBetween(Date value1, Date value2) {
            addCriterion("confirm_the_goods between", value1, value2, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andConfirmTheGoodsNotBetween(Date value1, Date value2) {
            addCriterion("confirm_the_goods not between", value1, value2, "confirmTheGoods");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIsNull() {
            addCriterion("accomplish_date is null");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIsNotNull() {
            addCriterion("accomplish_date is not null");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateEqualTo(Date value) {
            addCriterion("accomplish_date =", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotEqualTo(Date value) {
            addCriterion("accomplish_date <>", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateGreaterThan(Date value) {
            addCriterion("accomplish_date >", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateGreaterThanOrEqualTo(Date value) {
            addCriterion("accomplish_date >=", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateLessThan(Date value) {
            addCriterion("accomplish_date <", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateLessThanOrEqualTo(Date value) {
            addCriterion("accomplish_date <=", value, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateIn(List<Date> values) {
            addCriterion("accomplish_date in", values, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotIn(List<Date> values) {
            addCriterion("accomplish_date not in", values, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateBetween(Date value1, Date value2) {
            addCriterion("accomplish_date between", value1, value2, "accomplishDate");
            return (Criteria) this;
        }

        public Criteria andAccomplishDateNotBetween(Date value1, Date value2) {
            addCriterion("accomplish_date not between", value1, value2, "accomplishDate");
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