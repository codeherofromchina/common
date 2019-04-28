package com.erui.order.v2.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class LogisticsDataExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LogisticsDataExample() {
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

        public Criteria andTheAwbNoIsNull() {
            addCriterion("the_awb_no is null");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoIsNotNull() {
            addCriterion("the_awb_no is not null");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoEqualTo(String value) {
            addCriterion("the_awb_no =", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoNotEqualTo(String value) {
            addCriterion("the_awb_no <>", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoGreaterThan(String value) {
            addCriterion("the_awb_no >", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoGreaterThanOrEqualTo(String value) {
            addCriterion("the_awb_no >=", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoLessThan(String value) {
            addCriterion("the_awb_no <", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoLessThanOrEqualTo(String value) {
            addCriterion("the_awb_no <=", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoLike(String value) {
            addCriterion("the_awb_no like", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoNotLike(String value) {
            addCriterion("the_awb_no not like", value, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoIn(List<String> values) {
            addCriterion("the_awb_no in", values, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoNotIn(List<String> values) {
            addCriterion("the_awb_no not in", values, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoBetween(String value1, String value2) {
            addCriterion("the_awb_no between", value1, value2, "theAwbNo");
            return (Criteria) this;
        }

        public Criteria andTheAwbNoNotBetween(String value1, String value2) {
            addCriterion("the_awb_no not between", value1, value2, "theAwbNo");
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

        public Criteria andReleaseDateIsNull() {
            addCriterion("release_date is null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIsNotNull() {
            addCriterion("release_date is not null");
            return (Criteria) this;
        }

        public Criteria andReleaseDateEqualTo(String value) {
            addCriterion("release_date =", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotEqualTo(String value) {
            addCriterion("release_date <>", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThan(String value) {
            addCriterion("release_date >", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateGreaterThanOrEqualTo(String value) {
            addCriterion("release_date >=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThan(String value) {
            addCriterion("release_date <", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLessThanOrEqualTo(String value) {
            addCriterion("release_date <=", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateLike(String value) {
            addCriterion("release_date like", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotLike(String value) {
            addCriterion("release_date not like", value, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateIn(List<String> values) {
            addCriterion("release_date in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotIn(List<String> values) {
            addCriterion("release_date not in", values, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateBetween(String value1, String value2) {
            addCriterion("release_date between", value1, value2, "releaseDate");
            return (Criteria) this;
        }

        public Criteria andReleaseDateNotBetween(String value1, String value2) {
            addCriterion("release_date not between", value1, value2, "releaseDate");
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

        public Criteria andLogisticsPriceUsdIsNull() {
            addCriterion("logistics_price_usd is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdIsNotNull() {
            addCriterion("logistics_price_usd is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdEqualTo(BigDecimal value) {
            addCriterion("logistics_price_usd =", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdNotEqualTo(BigDecimal value) {
            addCriterion("logistics_price_usd <>", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdGreaterThan(BigDecimal value) {
            addCriterion("logistics_price_usd >", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_price_usd >=", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdLessThan(BigDecimal value) {
            addCriterion("logistics_price_usd <", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_price_usd <=", value, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdIn(List<BigDecimal> values) {
            addCriterion("logistics_price_usd in", values, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdNotIn(List<BigDecimal> values) {
            addCriterion("logistics_price_usd not in", values, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_price_usd between", value1, value2, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsPriceUsdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_price_usd not between", value1, value2, "logisticsPriceUsd");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostIsNull() {
            addCriterion("logistics_cost is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostIsNotNull() {
            addCriterion("logistics_cost is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostEqualTo(BigDecimal value) {
            addCriterion("logistics_cost =", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostNotEqualTo(BigDecimal value) {
            addCriterion("logistics_cost <>", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostGreaterThan(BigDecimal value) {
            addCriterion("logistics_cost >", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_cost >=", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostLessThan(BigDecimal value) {
            addCriterion("logistics_cost <", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostLessThanOrEqualTo(BigDecimal value) {
            addCriterion("logistics_cost <=", value, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostIn(List<BigDecimal> values) {
            addCriterion("logistics_cost in", values, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostNotIn(List<BigDecimal> values) {
            addCriterion("logistics_cost not in", values, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_cost between", value1, value2, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("logistics_cost not between", value1, value2, "logisticsCost");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeIsNull() {
            addCriterion("logistics_cost_type is null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeIsNotNull() {
            addCriterion("logistics_cost_type is not null");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeEqualTo(String value) {
            addCriterion("logistics_cost_type =", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeNotEqualTo(String value) {
            addCriterion("logistics_cost_type <>", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeGreaterThan(String value) {
            addCriterion("logistics_cost_type >", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeGreaterThanOrEqualTo(String value) {
            addCriterion("logistics_cost_type >=", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeLessThan(String value) {
            addCriterion("logistics_cost_type <", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeLessThanOrEqualTo(String value) {
            addCriterion("logistics_cost_type <=", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeLike(String value) {
            addCriterion("logistics_cost_type like", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeNotLike(String value) {
            addCriterion("logistics_cost_type not like", value, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeIn(List<String> values) {
            addCriterion("logistics_cost_type in", values, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeNotIn(List<String> values) {
            addCriterion("logistics_cost_type not in", values, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeBetween(String value1, String value2) {
            addCriterion("logistics_cost_type between", value1, value2, "logisticsCostType");
            return (Criteria) this;
        }

        public Criteria andLogisticsCostTypeNotBetween(String value1, String value2) {
            addCriterion("logistics_cost_type not between", value1, value2, "logisticsCostType");
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