package com.erui.report.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InquirySkuExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public InquirySkuExample() {
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

        public Criteria andCateCountIsNull() {
            addCriterion("cate_count is null");
            return (Criteria) this;
        }

        public Criteria andCateCountIsNotNull() {
            addCriterion("cate_count is not null");
            return (Criteria) this;
        }

        public Criteria andCateCountEqualTo(Integer value) {
            addCriterion("cate_count =", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountNotEqualTo(Integer value) {
            addCriterion("cate_count <>", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountGreaterThan(Integer value) {
            addCriterion("cate_count >", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("cate_count >=", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountLessThan(Integer value) {
            addCriterion("cate_count <", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountLessThanOrEqualTo(Integer value) {
            addCriterion("cate_count <=", value, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountIn(List<Integer> values) {
            addCriterion("cate_count in", values, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountNotIn(List<Integer> values) {
            addCriterion("cate_count not in", values, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountBetween(Integer value1, Integer value2) {
            addCriterion("cate_count between", value1, value2, "cateCount");
            return (Criteria) this;
        }

        public Criteria andCateCountNotBetween(Integer value1, Integer value2) {
            addCriterion("cate_count not between", value1, value2, "cateCount");
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