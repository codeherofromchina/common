package com.erui.order.util.excel;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据导入完成后的返回vo
 *
 * @author wangxiaodan
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImportDataResponse {
    // 总导入条数
    private int total;
    // 成功条数
    private int success;
    // 失败条数
    private int fail;
    // 导入是否完成
    private boolean done;
    // 导入失败的条目
    private List<FailItem> failItems = null;
    // 和数据
    private Map<String, BigDecimal> sumMap;
    // 其他信息
    private String otherMsg;

    public ImportDataResponse() {
        this.total = 0;
        this.success = 0;
        this.fail = 0;
        this.done = false;
    }

    public ImportDataResponse(String[] keys) {
        this();
        if (keys != null && keys.length > 0) {
            this.sumMap = new HashMap<>();
            for (String key : keys) {
                this.sumMap.put(key, BigDecimal.ZERO);
            }
        }
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    public int getSuccess() {
        return success;
    }

    public int getFail() {
        return fail;
    }

    public Map<String, BigDecimal> getSumMap() {
        return this.sumMap;
    }

    public String getOtherMsg() {
        return otherMsg;
    }

    public void setOtherMsg(String otherMsg) {
        this.otherMsg = otherMsg;
    }

    public void setSumMap(Map<String, BigDecimal> sumMap) {
        this.sumMap = sumMap;
    }

    public void incrSuccess() {
        this.success++;
        this.total++;
    }

    public void incrFail() {
        this.fail++;
        this.total++;
    }

    public boolean getDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public List<FailItem> getFailItems() {
        return failItems;
    }
    public void pushFailItem(String tableName, int index, String reason) {
        if (failItems == null) {
            failItems = new ArrayList<>();
        }
        failItems.add(new FailItem(tableName, index, reason));
    }

    public void sumData(Object ic) {
        if (ic != null && this.sumMap != null) {
            BeanWrapper wrapper = new BeanWrapperImpl(ic);
            sumMap.keySet().parallelStream().forEach(key -> {
                try {
                    Number number = (Number) wrapper.getPropertyValue(key);
                    if (number != null) {
                        BigDecimal value = this.sumMap.get(key);
                        value = value.add(new BigDecimal(String.valueOf(number)));
                        this.sumMap.put(key, value);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }


 /*   public static void main(String[] args) {
        Object obj = new InquiryCount();
        BeanWrapper wrapper = new BeanWrapperImpl(obj);

        wrapper.setPropertyValue("id", 12L);
        PropertyDescriptor id = wrapper.getPropertyDescriptor("id");
        Class<?> propertyType = id.getPropertyType();
        System.out.println(propertyType);

        Number l = 1L;


        BigDecimal bigDecimal = new BigDecimal(String.valueOf(l));


        System.out.println(bigDecimal);

    }*/

    private static class FailItem {
        private String tableName;
        private int index;
        private String reason;

        public FailItem(String tableName, int index, String reason) {
            this.tableName = tableName;
            this.index = index;
            this.reason = reason;
        }

        public String getTableName() {
            return tableName;
        }

        public int getIndex() {
            return index;
        }

        public String getReason() {
            return reason;
        }
    }


}
