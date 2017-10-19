package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InquiryCountExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InquiryCountExample() {
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

        public Criteria andInquiryNumIsNull() {
            addCriterion("inquiry_num is null");
            return (Criteria) this;
        }

        public Criteria andInquiryNumIsNotNull() {
            addCriterion("inquiry_num is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryNumEqualTo(String value) {
            addCriterion("inquiry_num =", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumNotEqualTo(String value) {
            addCriterion("inquiry_num <>", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumGreaterThan(String value) {
            addCriterion("inquiry_num >", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_num >=", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumLessThan(String value) {
            addCriterion("inquiry_num <", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumLessThanOrEqualTo(String value) {
            addCriterion("inquiry_num <=", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumLike(String value) {
            addCriterion("inquiry_num like", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumNotLike(String value) {
            addCriterion("inquiry_num not like", value, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumIn(List<String> values) {
            addCriterion("inquiry_num in", values, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumNotIn(List<String> values) {
            addCriterion("inquiry_num not in", values, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumBetween(String value1, String value2) {
            addCriterion("inquiry_num between", value1, value2, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andInquiryNumNotBetween(String value1, String value2) {
            addCriterion("inquiry_num not between", value1, value2, "inquiryNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumIsNull() {
            addCriterion("quotation_num is null");
            return (Criteria) this;
        }

        public Criteria andQuotationNumIsNotNull() {
            addCriterion("quotation_num is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationNumEqualTo(String value) {
            addCriterion("quotation_num =", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotEqualTo(String value) {
            addCriterion("quotation_num <>", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumGreaterThan(String value) {
            addCriterion("quotation_num >", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumGreaterThanOrEqualTo(String value) {
            addCriterion("quotation_num >=", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLessThan(String value) {
            addCriterion("quotation_num <", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLessThanOrEqualTo(String value) {
            addCriterion("quotation_num <=", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumLike(String value) {
            addCriterion("quotation_num like", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotLike(String value) {
            addCriterion("quotation_num not like", value, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumIn(List<String> values) {
            addCriterion("quotation_num in", values, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotIn(List<String> values) {
            addCriterion("quotation_num not in", values, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumBetween(String value1, String value2) {
            addCriterion("quotation_num between", value1, value2, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andQuotationNumNotBetween(String value1, String value2) {
            addCriterion("quotation_num not between", value1, value2, "quotationNum");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIsNull() {
            addCriterion("inquiry_unit is null");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIsNotNull() {
            addCriterion("inquiry_unit is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitEqualTo(String value) {
            addCriterion("inquiry_unit =", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotEqualTo(String value) {
            addCriterion("inquiry_unit <>", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitGreaterThan(String value) {
            addCriterion("inquiry_unit >", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_unit >=", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLessThan(String value) {
            addCriterion("inquiry_unit <", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLessThanOrEqualTo(String value) {
            addCriterion("inquiry_unit <=", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitLike(String value) {
            addCriterion("inquiry_unit like", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotLike(String value) {
            addCriterion("inquiry_unit not like", value, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitIn(List<String> values) {
            addCriterion("inquiry_unit in", values, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotIn(List<String> values) {
            addCriterion("inquiry_unit not in", values, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitBetween(String value1, String value2) {
            addCriterion("inquiry_unit between", value1, value2, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryUnitNotBetween(String value1, String value2) {
            addCriterion("inquiry_unit not between", value1, value2, "inquiryUnit");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIsNull() {
            addCriterion("inquiry_area is null");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIsNotNull() {
            addCriterion("inquiry_area is not null");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaEqualTo(String value) {
            addCriterion("inquiry_area =", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotEqualTo(String value) {
            addCriterion("inquiry_area <>", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaGreaterThan(String value) {
            addCriterion("inquiry_area >", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaGreaterThanOrEqualTo(String value) {
            addCriterion("inquiry_area >=", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLessThan(String value) {
            addCriterion("inquiry_area <", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLessThanOrEqualTo(String value) {
            addCriterion("inquiry_area <=", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaLike(String value) {
            addCriterion("inquiry_area like", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotLike(String value) {
            addCriterion("inquiry_area not like", value, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaIn(List<String> values) {
            addCriterion("inquiry_area in", values, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotIn(List<String> values) {
            addCriterion("inquiry_area not in", values, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaBetween(String value1, String value2) {
            addCriterion("inquiry_area between", value1, value2, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andInquiryAreaNotBetween(String value1, String value2) {
            addCriterion("inquiry_area not between", value1, value2, "inquiryArea");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNull() {
            addCriterion("organization is null");
            return (Criteria) this;
        }

        public Criteria andOrganizationIsNotNull() {
            addCriterion("organization is not null");
            return (Criteria) this;
        }

        public Criteria andOrganizationEqualTo(String value) {
            addCriterion("organization =", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotEqualTo(String value) {
            addCriterion("organization <>", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThan(String value) {
            addCriterion("organization >", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationGreaterThanOrEqualTo(String value) {
            addCriterion("organization >=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThan(String value) {
            addCriterion("organization <", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLessThanOrEqualTo(String value) {
            addCriterion("organization <=", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationLike(String value) {
            addCriterion("organization like", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotLike(String value) {
            addCriterion("organization not like", value, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationIn(List<String> values) {
            addCriterion("organization in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotIn(List<String> values) {
            addCriterion("organization not in", values, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationBetween(String value1, String value2) {
            addCriterion("organization between", value1, value2, "organization");
            return (Criteria) this;
        }

        public Criteria andOrganizationNotBetween(String value1, String value2) {
            addCriterion("organization not between", value1, value2, "organization");
            return (Criteria) this;
        }

        public Criteria andCustNameIsNull() {
            addCriterion("cust_name is null");
            return (Criteria) this;
        }

        public Criteria andCustNameIsNotNull() {
            addCriterion("cust_name is not null");
            return (Criteria) this;
        }

        public Criteria andCustNameEqualTo(String value) {
            addCriterion("cust_name =", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotEqualTo(String value) {
            addCriterion("cust_name <>", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameGreaterThan(String value) {
            addCriterion("cust_name >", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameGreaterThanOrEqualTo(String value) {
            addCriterion("cust_name >=", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLessThan(String value) {
            addCriterion("cust_name <", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLessThanOrEqualTo(String value) {
            addCriterion("cust_name <=", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameLike(String value) {
            addCriterion("cust_name like", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotLike(String value) {
            addCriterion("cust_name not like", value, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameIn(List<String> values) {
            addCriterion("cust_name in", values, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotIn(List<String> values) {
            addCriterion("cust_name not in", values, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameBetween(String value1, String value2) {
            addCriterion("cust_name between", value1, value2, "custName");
            return (Criteria) this;
        }

        public Criteria andCustNameNotBetween(String value1, String value2) {
            addCriterion("cust_name not between", value1, value2, "custName");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIsNull() {
            addCriterion("cust_description is null");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIsNotNull() {
            addCriterion("cust_description is not null");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionEqualTo(String value) {
            addCriterion("cust_description =", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotEqualTo(String value) {
            addCriterion("cust_description <>", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionGreaterThan(String value) {
            addCriterion("cust_description >", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("cust_description >=", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLessThan(String value) {
            addCriterion("cust_description <", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLessThanOrEqualTo(String value) {
            addCriterion("cust_description <=", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionLike(String value) {
            addCriterion("cust_description like", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotLike(String value) {
            addCriterion("cust_description not like", value, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionIn(List<String> values) {
            addCriterion("cust_description in", values, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotIn(List<String> values) {
            addCriterion("cust_description not in", values, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionBetween(String value1, String value2) {
            addCriterion("cust_description between", value1, value2, "custDescription");
            return (Criteria) this;
        }

        public Criteria andCustDescriptionNotBetween(String value1, String value2) {
            addCriterion("cust_description not between", value1, value2, "custDescription");
            return (Criteria) this;
        }

        public Criteria andProNameIsNull() {
            addCriterion("pro_name is null");
            return (Criteria) this;
        }

        public Criteria andProNameIsNotNull() {
            addCriterion("pro_name is not null");
            return (Criteria) this;
        }

        public Criteria andProNameEqualTo(String value) {
            addCriterion("pro_name =", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotEqualTo(String value) {
            addCriterion("pro_name <>", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThan(String value) {
            addCriterion("pro_name >", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameGreaterThanOrEqualTo(String value) {
            addCriterion("pro_name >=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThan(String value) {
            addCriterion("pro_name <", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLessThanOrEqualTo(String value) {
            addCriterion("pro_name <=", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameLike(String value) {
            addCriterion("pro_name like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotLike(String value) {
            addCriterion("pro_name not like", value, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameIn(List<String> values) {
            addCriterion("pro_name in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotIn(List<String> values) {
            addCriterion("pro_name not in", values, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameBetween(String value1, String value2) {
            addCriterion("pro_name between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameNotBetween(String value1, String value2) {
            addCriterion("pro_name not between", value1, value2, "proName");
            return (Criteria) this;
        }

        public Criteria andProNameForeignIsNull() {
            addCriterion("pro_name_foreign is null");
            return (Criteria) this;
        }

        public Criteria andProNameForeignIsNotNull() {
            addCriterion("pro_name_foreign is not null");
            return (Criteria) this;
        }

        public Criteria andProNameForeignEqualTo(String value) {
            addCriterion("pro_name_foreign =", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignNotEqualTo(String value) {
            addCriterion("pro_name_foreign <>", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignGreaterThan(String value) {
            addCriterion("pro_name_foreign >", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignGreaterThanOrEqualTo(String value) {
            addCriterion("pro_name_foreign >=", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignLessThan(String value) {
            addCriterion("pro_name_foreign <", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignLessThanOrEqualTo(String value) {
            addCriterion("pro_name_foreign <=", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignLike(String value) {
            addCriterion("pro_name_foreign like", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignNotLike(String value) {
            addCriterion("pro_name_foreign not like", value, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignIn(List<String> values) {
            addCriterion("pro_name_foreign in", values, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignNotIn(List<String> values) {
            addCriterion("pro_name_foreign not in", values, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignBetween(String value1, String value2) {
            addCriterion("pro_name_foreign between", value1, value2, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andProNameForeignNotBetween(String value1, String value2) {
            addCriterion("pro_name_foreign not between", value1, value2, "proNameForeign");
            return (Criteria) this;
        }

        public Criteria andSpecificationIsNull() {
            addCriterion("specification is null");
            return (Criteria) this;
        }

        public Criteria andSpecificationIsNotNull() {
            addCriterion("specification is not null");
            return (Criteria) this;
        }

        public Criteria andSpecificationEqualTo(String value) {
            addCriterion("specification =", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotEqualTo(String value) {
            addCriterion("specification <>", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationGreaterThan(String value) {
            addCriterion("specification >", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationGreaterThanOrEqualTo(String value) {
            addCriterion("specification >=", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLessThan(String value) {
            addCriterion("specification <", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLessThanOrEqualTo(String value) {
            addCriterion("specification <=", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationLike(String value) {
            addCriterion("specification like", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotLike(String value) {
            addCriterion("specification not like", value, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationIn(List<String> values) {
            addCriterion("specification in", values, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotIn(List<String> values) {
            addCriterion("specification not in", values, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationBetween(String value1, String value2) {
            addCriterion("specification between", value1, value2, "specification");
            return (Criteria) this;
        }

        public Criteria andSpecificationNotBetween(String value1, String value2) {
            addCriterion("specification not between", value1, value2, "specification");
            return (Criteria) this;
        }

        public Criteria andFigureNumIsNull() {
            addCriterion("figure_num is null");
            return (Criteria) this;
        }

        public Criteria andFigureNumIsNotNull() {
            addCriterion("figure_num is not null");
            return (Criteria) this;
        }

        public Criteria andFigureNumEqualTo(String value) {
            addCriterion("figure_num =", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumNotEqualTo(String value) {
            addCriterion("figure_num <>", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumGreaterThan(String value) {
            addCriterion("figure_num >", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumGreaterThanOrEqualTo(String value) {
            addCriterion("figure_num >=", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumLessThan(String value) {
            addCriterion("figure_num <", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumLessThanOrEqualTo(String value) {
            addCriterion("figure_num <=", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumLike(String value) {
            addCriterion("figure_num like", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumNotLike(String value) {
            addCriterion("figure_num not like", value, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumIn(List<String> values) {
            addCriterion("figure_num in", values, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumNotIn(List<String> values) {
            addCriterion("figure_num not in", values, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumBetween(String value1, String value2) {
            addCriterion("figure_num between", value1, value2, "figureNum");
            return (Criteria) this;
        }

        public Criteria andFigureNumNotBetween(String value1, String value2) {
            addCriterion("figure_num not between", value1, value2, "figureNum");
            return (Criteria) this;
        }

        public Criteria andProCountIsNull() {
            addCriterion("pro_count is null");
            return (Criteria) this;
        }

        public Criteria andProCountIsNotNull() {
            addCriterion("pro_count is not null");
            return (Criteria) this;
        }

        public Criteria andProCountEqualTo(Integer value) {
            addCriterion("pro_count =", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountNotEqualTo(Integer value) {
            addCriterion("pro_count <>", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountGreaterThan(Integer value) {
            addCriterion("pro_count >", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("pro_count >=", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountLessThan(Integer value) {
            addCriterion("pro_count <", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountLessThanOrEqualTo(Integer value) {
            addCriterion("pro_count <=", value, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountIn(List<Integer> values) {
            addCriterion("pro_count in", values, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountNotIn(List<Integer> values) {
            addCriterion("pro_count not in", values, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountBetween(Integer value1, Integer value2) {
            addCriterion("pro_count between", value1, value2, "proCount");
            return (Criteria) this;
        }

        public Criteria andProCountNotBetween(Integer value1, Integer value2) {
            addCriterion("pro_count not between", value1, value2, "proCount");
            return (Criteria) this;
        }

        public Criteria andProUnitIsNull() {
            addCriterion("pro_unit is null");
            return (Criteria) this;
        }

        public Criteria andProUnitIsNotNull() {
            addCriterion("pro_unit is not null");
            return (Criteria) this;
        }

        public Criteria andProUnitEqualTo(String value) {
            addCriterion("pro_unit =", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitNotEqualTo(String value) {
            addCriterion("pro_unit <>", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitGreaterThan(String value) {
            addCriterion("pro_unit >", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitGreaterThanOrEqualTo(String value) {
            addCriterion("pro_unit >=", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitLessThan(String value) {
            addCriterion("pro_unit <", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitLessThanOrEqualTo(String value) {
            addCriterion("pro_unit <=", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitLike(String value) {
            addCriterion("pro_unit like", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitNotLike(String value) {
            addCriterion("pro_unit not like", value, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitIn(List<String> values) {
            addCriterion("pro_unit in", values, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitNotIn(List<String> values) {
            addCriterion("pro_unit not in", values, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitBetween(String value1, String value2) {
            addCriterion("pro_unit between", value1, value2, "proUnit");
            return (Criteria) this;
        }

        public Criteria andProUnitNotBetween(String value1, String value2) {
            addCriterion("pro_unit not between", value1, value2, "proUnit");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIsNull() {
            addCriterion("is_oil_gas is null");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIsNotNull() {
            addCriterion("is_oil_gas is not null");
            return (Criteria) this;
        }

        public Criteria andIsOilGasEqualTo(String value) {
            addCriterion("is_oil_gas =", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotEqualTo(String value) {
            addCriterion("is_oil_gas <>", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasGreaterThan(String value) {
            addCriterion("is_oil_gas >", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasGreaterThanOrEqualTo(String value) {
            addCriterion("is_oil_gas >=", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLessThan(String value) {
            addCriterion("is_oil_gas <", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLessThanOrEqualTo(String value) {
            addCriterion("is_oil_gas <=", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasLike(String value) {
            addCriterion("is_oil_gas like", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotLike(String value) {
            addCriterion("is_oil_gas not like", value, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasIn(List<String> values) {
            addCriterion("is_oil_gas in", values, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotIn(List<String> values) {
            addCriterion("is_oil_gas not in", values, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasBetween(String value1, String value2) {
            addCriterion("is_oil_gas between", value1, value2, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andIsOilGasNotBetween(String value1, String value2) {
            addCriterion("is_oil_gas not between", value1, value2, "isOilGas");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIsNull() {
            addCriterion("plat_pro_category is null");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIsNotNull() {
            addCriterion("plat_pro_category is not null");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryEqualTo(String value) {
            addCriterion("plat_pro_category =", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotEqualTo(String value) {
            addCriterion("plat_pro_category <>", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryGreaterThan(String value) {
            addCriterion("plat_pro_category >", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("plat_pro_category >=", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLessThan(String value) {
            addCriterion("plat_pro_category <", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLessThanOrEqualTo(String value) {
            addCriterion("plat_pro_category <=", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryLike(String value) {
            addCriterion("plat_pro_category like", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotLike(String value) {
            addCriterion("plat_pro_category not like", value, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryIn(List<String> values) {
            addCriterion("plat_pro_category in", values, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotIn(List<String> values) {
            addCriterion("plat_pro_category not in", values, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryBetween(String value1, String value2) {
            addCriterion("plat_pro_category between", value1, value2, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andPlatProCategoryNotBetween(String value1, String value2) {
            addCriterion("plat_pro_category not between", value1, value2, "platProCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryIsNull() {
            addCriterion("pro_category is null");
            return (Criteria) this;
        }

        public Criteria andProCategoryIsNotNull() {
            addCriterion("pro_category is not null");
            return (Criteria) this;
        }

        public Criteria andProCategoryEqualTo(String value) {
            addCriterion("pro_category =", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotEqualTo(String value) {
            addCriterion("pro_category <>", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryGreaterThan(String value) {
            addCriterion("pro_category >", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("pro_category >=", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLessThan(String value) {
            addCriterion("pro_category <", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLessThanOrEqualTo(String value) {
            addCriterion("pro_category <=", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryLike(String value) {
            addCriterion("pro_category like", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotLike(String value) {
            addCriterion("pro_category not like", value, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryIn(List<String> values) {
            addCriterion("pro_category in", values, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotIn(List<String> values) {
            addCriterion("pro_category not in", values, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryBetween(String value1, String value2) {
            addCriterion("pro_category between", value1, value2, "proCategory");
            return (Criteria) this;
        }

        public Criteria andProCategoryNotBetween(String value1, String value2) {
            addCriterion("pro_category not between", value1, value2, "proCategory");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsIsNull() {
            addCriterion("is_kerui_equip_parts is null");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsIsNotNull() {
            addCriterion("is_kerui_equip_parts is not null");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsEqualTo(String value) {
            addCriterion("is_kerui_equip_parts =", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsNotEqualTo(String value) {
            addCriterion("is_kerui_equip_parts <>", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsGreaterThan(String value) {
            addCriterion("is_kerui_equip_parts >", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsGreaterThanOrEqualTo(String value) {
            addCriterion("is_kerui_equip_parts >=", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsLessThan(String value) {
            addCriterion("is_kerui_equip_parts <", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsLessThanOrEqualTo(String value) {
            addCriterion("is_kerui_equip_parts <=", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsLike(String value) {
            addCriterion("is_kerui_equip_parts like", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsNotLike(String value) {
            addCriterion("is_kerui_equip_parts not like", value, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsIn(List<String> values) {
            addCriterion("is_kerui_equip_parts in", values, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsNotIn(List<String> values) {
            addCriterion("is_kerui_equip_parts not in", values, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsBetween(String value1, String value2) {
            addCriterion("is_kerui_equip_parts between", value1, value2, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsKeruiEquipPartsNotBetween(String value1, String value2) {
            addCriterion("is_kerui_equip_parts not between", value1, value2, "isKeruiEquipParts");
            return (Criteria) this;
        }

        public Criteria andIsBidIsNull() {
            addCriterion("is_bid is null");
            return (Criteria) this;
        }

        public Criteria andIsBidIsNotNull() {
            addCriterion("is_bid is not null");
            return (Criteria) this;
        }

        public Criteria andIsBidEqualTo(String value) {
            addCriterion("is_bid =", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidNotEqualTo(String value) {
            addCriterion("is_bid <>", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidGreaterThan(String value) {
            addCriterion("is_bid >", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidGreaterThanOrEqualTo(String value) {
            addCriterion("is_bid >=", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidLessThan(String value) {
            addCriterion("is_bid <", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidLessThanOrEqualTo(String value) {
            addCriterion("is_bid <=", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidLike(String value) {
            addCriterion("is_bid like", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidNotLike(String value) {
            addCriterion("is_bid not like", value, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidIn(List<String> values) {
            addCriterion("is_bid in", values, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidNotIn(List<String> values) {
            addCriterion("is_bid not in", values, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidBetween(String value1, String value2) {
            addCriterion("is_bid between", value1, value2, "isBid");
            return (Criteria) this;
        }

        public Criteria andIsBidNotBetween(String value1, String value2) {
            addCriterion("is_bid not between", value1, value2, "isBid");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIsNull() {
            addCriterion("rollin_time is null");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIsNotNull() {
            addCriterion("rollin_time is not null");
            return (Criteria) this;
        }

        public Criteria andRollinTimeEqualTo(Date value) {
            addCriterion("rollin_time =", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotEqualTo(Date value) {
            addCriterion("rollin_time <>", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeGreaterThan(Date value) {
            addCriterion("rollin_time >", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rollin_time >=", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeLessThan(Date value) {
            addCriterion("rollin_time <", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeLessThanOrEqualTo(Date value) {
            addCriterion("rollin_time <=", value, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeIn(List<Date> values) {
            addCriterion("rollin_time in", values, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotIn(List<Date> values) {
            addCriterion("rollin_time not in", values, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeBetween(Date value1, Date value2) {
            addCriterion("rollin_time between", value1, value2, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andRollinTimeNotBetween(Date value1, Date value2) {
            addCriterion("rollin_time not between", value1, value2, "rollinTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeIsNull() {
            addCriterion("need_time is null");
            return (Criteria) this;
        }

        public Criteria andNeedTimeIsNotNull() {
            addCriterion("need_time is not null");
            return (Criteria) this;
        }

        public Criteria andNeedTimeEqualTo(Date value) {
            addCriterion("need_time =", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeNotEqualTo(Date value) {
            addCriterion("need_time <>", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeGreaterThan(Date value) {
            addCriterion("need_time >", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("need_time >=", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeLessThan(Date value) {
            addCriterion("need_time <", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeLessThanOrEqualTo(Date value) {
            addCriterion("need_time <=", value, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeIn(List<Date> values) {
            addCriterion("need_time in", values, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeNotIn(List<Date> values) {
            addCriterion("need_time not in", values, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeBetween(Date value1, Date value2) {
            addCriterion("need_time between", value1, value2, "needTime");
            return (Criteria) this;
        }

        public Criteria andNeedTimeNotBetween(Date value1, Date value2) {
            addCriterion("need_time not between", value1, value2, "needTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeIsNull() {
            addCriterion("clarify_time is null");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeIsNotNull() {
            addCriterion("clarify_time is not null");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeEqualTo(Date value) {
            addCriterion("clarify_time =", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeNotEqualTo(Date value) {
            addCriterion("clarify_time <>", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeGreaterThan(Date value) {
            addCriterion("clarify_time >", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("clarify_time >=", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeLessThan(Date value) {
            addCriterion("clarify_time <", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("clarify_time <=", value, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeIn(List<Date> values) {
            addCriterion("clarify_time in", values, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeNotIn(List<Date> values) {
            addCriterion("clarify_time not in", values, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeBetween(Date value1, Date value2) {
            addCriterion("clarify_time between", value1, value2, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andClarifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("clarify_time not between", value1, value2, "clarifyTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNull() {
            addCriterion("submit_time is null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIsNotNull() {
            addCriterion("submit_time is not null");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeEqualTo(Date value) {
            addCriterion("submit_time =", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotEqualTo(Date value) {
            addCriterion("submit_time <>", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThan(Date value) {
            addCriterion("submit_time >", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("submit_time >=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThan(Date value) {
            addCriterion("submit_time <", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeLessThanOrEqualTo(Date value) {
            addCriterion("submit_time <=", value, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeIn(List<Date> values) {
            addCriterion("submit_time in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotIn(List<Date> values) {
            addCriterion("submit_time not in", values, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeBetween(Date value1, Date value2) {
            addCriterion("submit_time between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andSubmitTimeNotBetween(Date value1, Date value2) {
            addCriterion("submit_time not between", value1, value2, "submitTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeIsNull() {
            addCriterion("quote_need_time is null");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeIsNotNull() {
            addCriterion("quote_need_time is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeEqualTo(BigDecimal value) {
            addCriterion("quote_need_time =", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeNotEqualTo(BigDecimal value) {
            addCriterion("quote_need_time <>", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeGreaterThan(BigDecimal value) {
            addCriterion("quote_need_time >", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_need_time >=", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeLessThan(BigDecimal value) {
            addCriterion("quote_need_time <", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_need_time <=", value, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeIn(List<BigDecimal> values) {
            addCriterion("quote_need_time in", values, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeNotIn(List<BigDecimal> values) {
            addCriterion("quote_need_time not in", values, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_need_time between", value1, value2, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andQuoteNeedTimeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_need_time not between", value1, value2, "quoteNeedTime");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalIsNull() {
            addCriterion("market_principal is null");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalIsNotNull() {
            addCriterion("market_principal is not null");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalEqualTo(String value) {
            addCriterion("market_principal =", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalNotEqualTo(String value) {
            addCriterion("market_principal <>", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalGreaterThan(String value) {
            addCriterion("market_principal >", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalGreaterThanOrEqualTo(String value) {
            addCriterion("market_principal >=", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalLessThan(String value) {
            addCriterion("market_principal <", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalLessThanOrEqualTo(String value) {
            addCriterion("market_principal <=", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalLike(String value) {
            addCriterion("market_principal like", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalNotLike(String value) {
            addCriterion("market_principal not like", value, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalIn(List<String> values) {
            addCriterion("market_principal in", values, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalNotIn(List<String> values) {
            addCriterion("market_principal not in", values, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalBetween(String value1, String value2) {
            addCriterion("market_principal between", value1, value2, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andMarketPrincipalNotBetween(String value1, String value2) {
            addCriterion("market_principal not between", value1, value2, "marketPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderIsNull() {
            addCriterion("bus_technical_bidder is null");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderIsNotNull() {
            addCriterion("bus_technical_bidder is not null");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderEqualTo(String value) {
            addCriterion("bus_technical_bidder =", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderNotEqualTo(String value) {
            addCriterion("bus_technical_bidder <>", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderGreaterThan(String value) {
            addCriterion("bus_technical_bidder >", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderGreaterThanOrEqualTo(String value) {
            addCriterion("bus_technical_bidder >=", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderLessThan(String value) {
            addCriterion("bus_technical_bidder <", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderLessThanOrEqualTo(String value) {
            addCriterion("bus_technical_bidder <=", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderLike(String value) {
            addCriterion("bus_technical_bidder like", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderNotLike(String value) {
            addCriterion("bus_technical_bidder not like", value, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderIn(List<String> values) {
            addCriterion("bus_technical_bidder in", values, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderNotIn(List<String> values) {
            addCriterion("bus_technical_bidder not in", values, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderBetween(String value1, String value2) {
            addCriterion("bus_technical_bidder between", value1, value2, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusTechnicalBidderNotBetween(String value1, String value2) {
            addCriterion("bus_technical_bidder not between", value1, value2, "busTechnicalBidder");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalIsNull() {
            addCriterion("bus_unit_principal is null");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalIsNotNull() {
            addCriterion("bus_unit_principal is not null");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalEqualTo(String value) {
            addCriterion("bus_unit_principal =", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalNotEqualTo(String value) {
            addCriterion("bus_unit_principal <>", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalGreaterThan(String value) {
            addCriterion("bus_unit_principal >", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalGreaterThanOrEqualTo(String value) {
            addCriterion("bus_unit_principal >=", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalLessThan(String value) {
            addCriterion("bus_unit_principal <", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalLessThanOrEqualTo(String value) {
            addCriterion("bus_unit_principal <=", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalLike(String value) {
            addCriterion("bus_unit_principal like", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalNotLike(String value) {
            addCriterion("bus_unit_principal not like", value, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalIn(List<String> values) {
            addCriterion("bus_unit_principal in", values, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalNotIn(List<String> values) {
            addCriterion("bus_unit_principal not in", values, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalBetween(String value1, String value2) {
            addCriterion("bus_unit_principal between", value1, value2, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andBusUnitPrincipalNotBetween(String value1, String value2) {
            addCriterion("bus_unit_principal not between", value1, value2, "busUnitPrincipal");
            return (Criteria) this;
        }

        public Criteria andProBrandIsNull() {
            addCriterion("pro_brand is null");
            return (Criteria) this;
        }

        public Criteria andProBrandIsNotNull() {
            addCriterion("pro_brand is not null");
            return (Criteria) this;
        }

        public Criteria andProBrandEqualTo(String value) {
            addCriterion("pro_brand =", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandNotEqualTo(String value) {
            addCriterion("pro_brand <>", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandGreaterThan(String value) {
            addCriterion("pro_brand >", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandGreaterThanOrEqualTo(String value) {
            addCriterion("pro_brand >=", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandLessThan(String value) {
            addCriterion("pro_brand <", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandLessThanOrEqualTo(String value) {
            addCriterion("pro_brand <=", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandLike(String value) {
            addCriterion("pro_brand like", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandNotLike(String value) {
            addCriterion("pro_brand not like", value, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandIn(List<String> values) {
            addCriterion("pro_brand in", values, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandNotIn(List<String> values) {
            addCriterion("pro_brand not in", values, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandBetween(String value1, String value2) {
            addCriterion("pro_brand between", value1, value2, "proBrand");
            return (Criteria) this;
        }

        public Criteria andProBrandNotBetween(String value1, String value2) {
            addCriterion("pro_brand not between", value1, value2, "proBrand");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliIsNull() {
            addCriterion("quonation_suppli is null");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliIsNotNull() {
            addCriterion("quonation_suppli is not null");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliEqualTo(String value) {
            addCriterion("quonation_suppli =", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliNotEqualTo(String value) {
            addCriterion("quonation_suppli <>", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliGreaterThan(String value) {
            addCriterion("quonation_suppli >", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliGreaterThanOrEqualTo(String value) {
            addCriterion("quonation_suppli >=", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliLessThan(String value) {
            addCriterion("quonation_suppli <", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliLessThanOrEqualTo(String value) {
            addCriterion("quonation_suppli <=", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliLike(String value) {
            addCriterion("quonation_suppli like", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliNotLike(String value) {
            addCriterion("quonation_suppli not like", value, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliIn(List<String> values) {
            addCriterion("quonation_suppli in", values, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliNotIn(List<String> values) {
            addCriterion("quonation_suppli not in", values, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliBetween(String value1, String value2) {
            addCriterion("quonation_suppli between", value1, value2, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andQuonationSuppliNotBetween(String value1, String value2) {
            addCriterion("quonation_suppli not between", value1, value2, "quonationSuppli");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderIsNull() {
            addCriterion("suppli_bidder is null");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderIsNotNull() {
            addCriterion("suppli_bidder is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderEqualTo(String value) {
            addCriterion("suppli_bidder =", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderNotEqualTo(String value) {
            addCriterion("suppli_bidder <>", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderGreaterThan(String value) {
            addCriterion("suppli_bidder >", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderGreaterThanOrEqualTo(String value) {
            addCriterion("suppli_bidder >=", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderLessThan(String value) {
            addCriterion("suppli_bidder <", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderLessThanOrEqualTo(String value) {
            addCriterion("suppli_bidder <=", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderLike(String value) {
            addCriterion("suppli_bidder like", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderNotLike(String value) {
            addCriterion("suppli_bidder not like", value, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderIn(List<String> values) {
            addCriterion("suppli_bidder in", values, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderNotIn(List<String> values) {
            addCriterion("suppli_bidder not in", values, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderBetween(String value1, String value2) {
            addCriterion("suppli_bidder between", value1, value2, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andSuppliBidderNotBetween(String value1, String value2) {
            addCriterion("suppli_bidder not between", value1, value2, "suppliBidder");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneIsNull() {
            addCriterion("bidder_phone is null");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneIsNotNull() {
            addCriterion("bidder_phone is not null");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneEqualTo(String value) {
            addCriterion("bidder_phone =", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneNotEqualTo(String value) {
            addCriterion("bidder_phone <>", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneGreaterThan(String value) {
            addCriterion("bidder_phone >", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("bidder_phone >=", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneLessThan(String value) {
            addCriterion("bidder_phone <", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneLessThanOrEqualTo(String value) {
            addCriterion("bidder_phone <=", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneLike(String value) {
            addCriterion("bidder_phone like", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneNotLike(String value) {
            addCriterion("bidder_phone not like", value, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneIn(List<String> values) {
            addCriterion("bidder_phone in", values, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneNotIn(List<String> values) {
            addCriterion("bidder_phone not in", values, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneBetween(String value1, String value2) {
            addCriterion("bidder_phone between", value1, value2, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andBidderPhoneNotBetween(String value1, String value2) {
            addCriterion("bidder_phone not between", value1, value2, "bidderPhone");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceIsNull() {
            addCriterion("suppli_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceIsNotNull() {
            addCriterion("suppli_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceEqualTo(BigDecimal value) {
            addCriterion("suppli_unit_price =", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("suppli_unit_price <>", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("suppli_unit_price >", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("suppli_unit_price >=", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceLessThan(BigDecimal value) {
            addCriterion("suppli_unit_price <", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("suppli_unit_price <=", value, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceIn(List<BigDecimal> values) {
            addCriterion("suppli_unit_price in", values, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("suppli_unit_price not in", values, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suppli_unit_price between", value1, value2, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suppli_unit_price not between", value1, value2, "suppliUnitPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceIsNull() {
            addCriterion("suppli_total_price is null");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceIsNotNull() {
            addCriterion("suppli_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceEqualTo(BigDecimal value) {
            addCriterion("suppli_total_price =", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("suppli_total_price <>", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("suppli_total_price >", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("suppli_total_price >=", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceLessThan(BigDecimal value) {
            addCriterion("suppli_total_price <", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("suppli_total_price <=", value, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceIn(List<BigDecimal> values) {
            addCriterion("suppli_total_price in", values, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("suppli_total_price not in", values, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suppli_total_price between", value1, value2, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andSuppliTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("suppli_total_price not between", value1, value2, "suppliTotalPrice");
            return (Criteria) this;
        }

        public Criteria andProfitMarginIsNull() {
            addCriterion("profit_margin is null");
            return (Criteria) this;
        }

        public Criteria andProfitMarginIsNotNull() {
            addCriterion("profit_margin is not null");
            return (Criteria) this;
        }

        public Criteria andProfitMarginEqualTo(BigDecimal value) {
            addCriterion("profit_margin =", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginNotEqualTo(BigDecimal value) {
            addCriterion("profit_margin <>", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginGreaterThan(BigDecimal value) {
            addCriterion("profit_margin >", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_margin >=", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginLessThan(BigDecimal value) {
            addCriterion("profit_margin <", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginLessThanOrEqualTo(BigDecimal value) {
            addCriterion("profit_margin <=", value, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginIn(List<BigDecimal> values) {
            addCriterion("profit_margin in", values, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginNotIn(List<BigDecimal> values) {
            addCriterion("profit_margin not in", values, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_margin between", value1, value2, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andProfitMarginNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("profit_margin not between", value1, value2, "profitMargin");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceIsNull() {
            addCriterion("quote_unit_price is null");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceIsNotNull() {
            addCriterion("quote_unit_price is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceEqualTo(BigDecimal value) {
            addCriterion("quote_unit_price =", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceNotEqualTo(BigDecimal value) {
            addCriterion("quote_unit_price <>", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceGreaterThan(BigDecimal value) {
            addCriterion("quote_unit_price >", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_unit_price >=", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceLessThan(BigDecimal value) {
            addCriterion("quote_unit_price <", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_unit_price <=", value, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceIn(List<BigDecimal> values) {
            addCriterion("quote_unit_price in", values, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceNotIn(List<BigDecimal> values) {
            addCriterion("quote_unit_price not in", values, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_unit_price between", value1, value2, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteUnitPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_unit_price not between", value1, value2, "quoteUnitPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceIsNull() {
            addCriterion("quote_total_price is null");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceIsNotNull() {
            addCriterion("quote_total_price is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceEqualTo(BigDecimal value) {
            addCriterion("quote_total_price =", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceNotEqualTo(BigDecimal value) {
            addCriterion("quote_total_price <>", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceGreaterThan(BigDecimal value) {
            addCriterion("quote_total_price >", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_total_price >=", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceLessThan(BigDecimal value) {
            addCriterion("quote_total_price <", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quote_total_price <=", value, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceIn(List<BigDecimal> values) {
            addCriterion("quote_total_price in", values, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceNotIn(List<BigDecimal> values) {
            addCriterion("quote_total_price not in", values, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_total_price between", value1, value2, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuoteTotalPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quote_total_price not between", value1, value2, "quoteTotalPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceIsNull() {
            addCriterion("quotation_price is null");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceIsNotNull() {
            addCriterion("quotation_price is not null");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceEqualTo(BigDecimal value) {
            addCriterion("quotation_price =", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceNotEqualTo(BigDecimal value) {
            addCriterion("quotation_price <>", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceGreaterThan(BigDecimal value) {
            addCriterion("quotation_price >", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("quotation_price >=", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceLessThan(BigDecimal value) {
            addCriterion("quotation_price <", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("quotation_price <=", value, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceIn(List<BigDecimal> values) {
            addCriterion("quotation_price in", values, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceNotIn(List<BigDecimal> values) {
            addCriterion("quotation_price not in", values, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quotation_price between", value1, value2, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andQuotationPriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("quotation_price not between", value1, value2, "quotationPrice");
            return (Criteria) this;
        }

        public Criteria andPieceWeightIsNull() {
            addCriterion("piece_weight is null");
            return (Criteria) this;
        }

        public Criteria andPieceWeightIsNotNull() {
            addCriterion("piece_weight is not null");
            return (Criteria) this;
        }

        public Criteria andPieceWeightEqualTo(BigDecimal value) {
            addCriterion("piece_weight =", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightNotEqualTo(BigDecimal value) {
            addCriterion("piece_weight <>", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightGreaterThan(BigDecimal value) {
            addCriterion("piece_weight >", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("piece_weight >=", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightLessThan(BigDecimal value) {
            addCriterion("piece_weight <", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("piece_weight <=", value, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightIn(List<BigDecimal> values) {
            addCriterion("piece_weight in", values, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightNotIn(List<BigDecimal> values) {
            addCriterion("piece_weight not in", values, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("piece_weight between", value1, value2, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andPieceWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("piece_weight not between", value1, value2, "pieceWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightIsNull() {
            addCriterion("total_weight is null");
            return (Criteria) this;
        }

        public Criteria andTotalWeightIsNotNull() {
            addCriterion("total_weight is not null");
            return (Criteria) this;
        }

        public Criteria andTotalWeightEqualTo(BigDecimal value) {
            addCriterion("total_weight =", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightNotEqualTo(BigDecimal value) {
            addCriterion("total_weight <>", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightGreaterThan(BigDecimal value) {
            addCriterion("total_weight >", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("total_weight >=", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightLessThan(BigDecimal value) {
            addCriterion("total_weight <", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("total_weight <=", value, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightIn(List<BigDecimal> values) {
            addCriterion("total_weight in", values, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightNotIn(List<BigDecimal> values) {
            addCriterion("total_weight not in", values, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_weight between", value1, value2, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andTotalWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("total_weight not between", value1, value2, "totalWeight");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeIsNull() {
            addCriterion("packaging_volume is null");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeIsNotNull() {
            addCriterion("packaging_volume is not null");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeEqualTo(String value) {
            addCriterion("packaging_volume =", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeNotEqualTo(String value) {
            addCriterion("packaging_volume <>", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeGreaterThan(String value) {
            addCriterion("packaging_volume >", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeGreaterThanOrEqualTo(String value) {
            addCriterion("packaging_volume >=", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeLessThan(String value) {
            addCriterion("packaging_volume <", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeLessThanOrEqualTo(String value) {
            addCriterion("packaging_volume <=", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeLike(String value) {
            addCriterion("packaging_volume like", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeNotLike(String value) {
            addCriterion("packaging_volume not like", value, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeIn(List<String> values) {
            addCriterion("packaging_volume in", values, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeNotIn(List<String> values) {
            addCriterion("packaging_volume not in", values, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeBetween(String value1, String value2) {
            addCriterion("packaging_volume between", value1, value2, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingVolumeNotBetween(String value1, String value2) {
            addCriterion("packaging_volume not between", value1, value2, "packagingVolume");
            return (Criteria) this;
        }

        public Criteria andPackagingWayIsNull() {
            addCriterion("packaging_way is null");
            return (Criteria) this;
        }

        public Criteria andPackagingWayIsNotNull() {
            addCriterion("packaging_way is not null");
            return (Criteria) this;
        }

        public Criteria andPackagingWayEqualTo(String value) {
            addCriterion("packaging_way =", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayNotEqualTo(String value) {
            addCriterion("packaging_way <>", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayGreaterThan(String value) {
            addCriterion("packaging_way >", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayGreaterThanOrEqualTo(String value) {
            addCriterion("packaging_way >=", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayLessThan(String value) {
            addCriterion("packaging_way <", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayLessThanOrEqualTo(String value) {
            addCriterion("packaging_way <=", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayLike(String value) {
            addCriterion("packaging_way like", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayNotLike(String value) {
            addCriterion("packaging_way not like", value, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayIn(List<String> values) {
            addCriterion("packaging_way in", values, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayNotIn(List<String> values) {
            addCriterion("packaging_way not in", values, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayBetween(String value1, String value2) {
            addCriterion("packaging_way between", value1, value2, "packagingWay");
            return (Criteria) this;
        }

        public Criteria andPackagingWayNotBetween(String value1, String value2) {
            addCriterion("packaging_way not between", value1, value2, "packagingWay");
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

        public Criteria andDeliveryDateEqualTo(Integer value) {
            addCriterion("delivery_date =", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotEqualTo(Integer value) {
            addCriterion("delivery_date <>", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThan(Integer value) {
            addCriterion("delivery_date >", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("delivery_date >=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThan(Integer value) {
            addCriterion("delivery_date <", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateLessThanOrEqualTo(Integer value) {
            addCriterion("delivery_date <=", value, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateIn(List<Integer> values) {
            addCriterion("delivery_date in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotIn(List<Integer> values) {
            addCriterion("delivery_date not in", values, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateBetween(Integer value1, Integer value2) {
            addCriterion("delivery_date between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andDeliveryDateNotBetween(Integer value1, Integer value2) {
            addCriterion("delivery_date not between", value1, value2, "deliveryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNull() {
            addCriterion("expiry_date is null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIsNotNull() {
            addCriterion("expiry_date is not null");
            return (Criteria) this;
        }

        public Criteria andExpiryDateEqualTo(Integer value) {
            addCriterion("expiry_date =", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotEqualTo(Integer value) {
            addCriterion("expiry_date <>", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThan(Integer value) {
            addCriterion("expiry_date >", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateGreaterThanOrEqualTo(Integer value) {
            addCriterion("expiry_date >=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThan(Integer value) {
            addCriterion("expiry_date <", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateLessThanOrEqualTo(Integer value) {
            addCriterion("expiry_date <=", value, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateIn(List<Integer> values) {
            addCriterion("expiry_date in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotIn(List<Integer> values) {
            addCriterion("expiry_date not in", values, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateBetween(Integer value1, Integer value2) {
            addCriterion("expiry_date between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andExpiryDateNotBetween(Integer value1, Integer value2) {
            addCriterion("expiry_date not between", value1, value2, "expiryDate");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsIsNull() {
            addCriterion("standard_trade_items is null");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsIsNotNull() {
            addCriterion("standard_trade_items is not null");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsEqualTo(String value) {
            addCriterion("standard_trade_items =", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsNotEqualTo(String value) {
            addCriterion("standard_trade_items <>", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsGreaterThan(String value) {
            addCriterion("standard_trade_items >", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsGreaterThanOrEqualTo(String value) {
            addCriterion("standard_trade_items >=", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsLessThan(String value) {
            addCriterion("standard_trade_items <", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsLessThanOrEqualTo(String value) {
            addCriterion("standard_trade_items <=", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsLike(String value) {
            addCriterion("standard_trade_items like", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsNotLike(String value) {
            addCriterion("standard_trade_items not like", value, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsIn(List<String> values) {
            addCriterion("standard_trade_items in", values, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsNotIn(List<String> values) {
            addCriterion("standard_trade_items not in", values, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsBetween(String value1, String value2) {
            addCriterion("standard_trade_items between", value1, value2, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andStandardTradeItemsNotBetween(String value1, String value2) {
            addCriterion("standard_trade_items not between", value1, value2, "standardTradeItems");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleIsNull() {
            addCriterion("latest_schedule is null");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleIsNotNull() {
            addCriterion("latest_schedule is not null");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleEqualTo(String value) {
            addCriterion("latest_schedule =", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleNotEqualTo(String value) {
            addCriterion("latest_schedule <>", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleGreaterThan(String value) {
            addCriterion("latest_schedule >", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleGreaterThanOrEqualTo(String value) {
            addCriterion("latest_schedule >=", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleLessThan(String value) {
            addCriterion("latest_schedule <", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleLessThanOrEqualTo(String value) {
            addCriterion("latest_schedule <=", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleLike(String value) {
            addCriterion("latest_schedule like", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleNotLike(String value) {
            addCriterion("latest_schedule not like", value, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleIn(List<String> values) {
            addCriterion("latest_schedule in", values, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleNotIn(List<String> values) {
            addCriterion("latest_schedule not in", values, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleBetween(String value1, String value2) {
            addCriterion("latest_schedule between", value1, value2, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andLatestScheduleNotBetween(String value1, String value2) {
            addCriterion("latest_schedule not between", value1, value2, "latestSchedule");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusIsNull() {
            addCriterion("quoted_status is null");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusIsNotNull() {
            addCriterion("quoted_status is not null");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusEqualTo(String value) {
            addCriterion("quoted_status =", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusNotEqualTo(String value) {
            addCriterion("quoted_status <>", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusGreaterThan(String value) {
            addCriterion("quoted_status >", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusGreaterThanOrEqualTo(String value) {
            addCriterion("quoted_status >=", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusLessThan(String value) {
            addCriterion("quoted_status <", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusLessThanOrEqualTo(String value) {
            addCriterion("quoted_status <=", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusLike(String value) {
            addCriterion("quoted_status like", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusNotLike(String value) {
            addCriterion("quoted_status not like", value, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusIn(List<String> values) {
            addCriterion("quoted_status in", values, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusNotIn(List<String> values) {
            addCriterion("quoted_status not in", values, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusBetween(String value1, String value2) {
            addCriterion("quoted_status between", value1, value2, "quotedStatus");
            return (Criteria) this;
        }

        public Criteria andQuotedStatusNotBetween(String value1, String value2) {
            addCriterion("quoted_status not between", value1, value2, "quotedStatus");
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

        public Criteria andQuoteOvertimeCategoryIsNull() {
            addCriterion("quote_overtime_category is null");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryIsNotNull() {
            addCriterion("quote_overtime_category is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryEqualTo(String value) {
            addCriterion("quote_overtime_category =", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryNotEqualTo(String value) {
            addCriterion("quote_overtime_category <>", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryGreaterThan(String value) {
            addCriterion("quote_overtime_category >", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("quote_overtime_category >=", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryLessThan(String value) {
            addCriterion("quote_overtime_category <", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryLessThanOrEqualTo(String value) {
            addCriterion("quote_overtime_category <=", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryLike(String value) {
            addCriterion("quote_overtime_category like", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryNotLike(String value) {
            addCriterion("quote_overtime_category not like", value, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryIn(List<String> values) {
            addCriterion("quote_overtime_category in", values, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryNotIn(List<String> values) {
            addCriterion("quote_overtime_category not in", values, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryBetween(String value1, String value2) {
            addCriterion("quote_overtime_category between", value1, value2, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCategoryNotBetween(String value1, String value2) {
            addCriterion("quote_overtime_category not between", value1, value2, "quoteOvertimeCategory");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseIsNull() {
            addCriterion("quote_overtime_cause is null");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseIsNotNull() {
            addCriterion("quote_overtime_cause is not null");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseEqualTo(String value) {
            addCriterion("quote_overtime_cause =", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseNotEqualTo(String value) {
            addCriterion("quote_overtime_cause <>", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseGreaterThan(String value) {
            addCriterion("quote_overtime_cause >", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseGreaterThanOrEqualTo(String value) {
            addCriterion("quote_overtime_cause >=", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseLessThan(String value) {
            addCriterion("quote_overtime_cause <", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseLessThanOrEqualTo(String value) {
            addCriterion("quote_overtime_cause <=", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseLike(String value) {
            addCriterion("quote_overtime_cause like", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseNotLike(String value) {
            addCriterion("quote_overtime_cause not like", value, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseIn(List<String> values) {
            addCriterion("quote_overtime_cause in", values, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseNotIn(List<String> values) {
            addCriterion("quote_overtime_cause not in", values, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseBetween(String value1, String value2) {
            addCriterion("quote_overtime_cause between", value1, value2, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andQuoteOvertimeCauseNotBetween(String value1, String value2) {
            addCriterion("quote_overtime_cause not between", value1, value2, "quoteOvertimeCause");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderIsNull() {
            addCriterion("is_success_order is null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderIsNotNull() {
            addCriterion("is_success_order is not null");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderEqualTo(String value) {
            addCriterion("is_success_order =", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderNotEqualTo(String value) {
            addCriterion("is_success_order <>", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderGreaterThan(String value) {
            addCriterion("is_success_order >", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderGreaterThanOrEqualTo(String value) {
            addCriterion("is_success_order >=", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderLessThan(String value) {
            addCriterion("is_success_order <", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderLessThanOrEqualTo(String value) {
            addCriterion("is_success_order <=", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderLike(String value) {
            addCriterion("is_success_order like", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderNotLike(String value) {
            addCriterion("is_success_order not like", value, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderIn(List<String> values) {
            addCriterion("is_success_order in", values, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderNotIn(List<String> values) {
            addCriterion("is_success_order not in", values, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderBetween(String value1, String value2) {
            addCriterion("is_success_order between", value1, value2, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andIsSuccessOrderNotBetween(String value1, String value2) {
            addCriterion("is_success_order not between", value1, value2, "isSuccessOrder");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryIsNull() {
            addCriterion("lose_order_category is null");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryIsNotNull() {
            addCriterion("lose_order_category is not null");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryEqualTo(String value) {
            addCriterion("lose_order_category =", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryNotEqualTo(String value) {
            addCriterion("lose_order_category <>", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryGreaterThan(String value) {
            addCriterion("lose_order_category >", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryGreaterThanOrEqualTo(String value) {
            addCriterion("lose_order_category >=", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryLessThan(String value) {
            addCriterion("lose_order_category <", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryLessThanOrEqualTo(String value) {
            addCriterion("lose_order_category <=", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryLike(String value) {
            addCriterion("lose_order_category like", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryNotLike(String value) {
            addCriterion("lose_order_category not like", value, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryIn(List<String> values) {
            addCriterion("lose_order_category in", values, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryNotIn(List<String> values) {
            addCriterion("lose_order_category not in", values, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryBetween(String value1, String value2) {
            addCriterion("lose_order_category between", value1, value2, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCategoryNotBetween(String value1, String value2) {
            addCriterion("lose_order_category not between", value1, value2, "loseOrderCategory");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseIsNull() {
            addCriterion("lose_order_cause is null");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseIsNotNull() {
            addCriterion("lose_order_cause is not null");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseEqualTo(String value) {
            addCriterion("lose_order_cause =", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseNotEqualTo(String value) {
            addCriterion("lose_order_cause <>", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseGreaterThan(String value) {
            addCriterion("lose_order_cause >", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseGreaterThanOrEqualTo(String value) {
            addCriterion("lose_order_cause >=", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseLessThan(String value) {
            addCriterion("lose_order_cause <", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseLessThanOrEqualTo(String value) {
            addCriterion("lose_order_cause <=", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseLike(String value) {
            addCriterion("lose_order_cause like", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseNotLike(String value) {
            addCriterion("lose_order_cause not like", value, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseIn(List<String> values) {
            addCriterion("lose_order_cause in", values, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseNotIn(List<String> values) {
            addCriterion("lose_order_cause not in", values, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseBetween(String value1, String value2) {
            addCriterion("lose_order_cause between", value1, value2, "loseOrderCause");
            return (Criteria) this;
        }

        public Criteria andLoseOrderCauseNotBetween(String value1, String value2) {
            addCriterion("lose_order_cause not between", value1, value2, "loseOrderCause");
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