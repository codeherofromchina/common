package com.erui.report.util;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class SetList implements Comparator<String> {

   private  List<String> setList;

    public List<String> getSetList() {
        setList.sort(this);
        return setList;
    }

    public void setSetList(List<String> setList) {
        this.setList = setList;
    }
    public SetList(List<String> setList){
        this.setList=setList;
    }
    @Override
    public int compare(String o1, String o2) {
        return getNum(o1) - getNum(o2);
    }

    public static int getNum(String s) {// 获取字符串中的数字
        String num = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= '0' && s.charAt(i) <= '9') {// 遍历判断是否为数字
                num += s.charAt(i);
            }
        }
        return Integer.parseInt(num);
    }
}
