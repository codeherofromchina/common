package com.erui.order.util;

import com.erui.order.entity.Goods;

import java.util.Collections;
import java.util.List;

/**
 * Created by wangxiaodan on 2018/3/29.
 */
public final class GoodsUtils {


    public static void sortGoodsByParentAndSon(List<Goods> goodsList){
        if (goodsList != null && goodsList.size() > 1) {
            // 将商品按照父子关系升序排列
            Collections.sort(goodsList, (g1, g2) -> {
                Integer g1Id = g1.getId();
                Integer g2Id = g2.getId();
                Integer g1Pid = g1.getParentId();
                Integer g2Pid = g2.getParentId();

                if (g1Pid == null && g2Pid == null) {
                    return compare(g1Id, g2Id);
                } else if (g1Pid == null) { // g2Pid != null
                    if (g1Id == null) {
                        return 1;
                    }
                    return -compare(g2Pid, g1Id);
                } else if (g2Pid == null) {// g1Pid != null
                    if (g2Id == null) {
                        return -1;
                    }
                    return compare(g1Pid, g2Id) >= 0 ? 1 : -1;
                } else { // g1Pid != null && g2Pid != null
                    int tmp = g1Pid - g2Pid;
                    if (tmp == 0) {
                        return compare(g1Id, g2Id);
                    }
                    return tmp;
                }
            });
        }
    }


    private static int compare(Integer i1, Integer i2) {
        if (i1 == null && i2 == null) {
            return 0;
        } else if (i1 == null) {
            return -1;
        } else if (i2 == null) {
            return 1;
        } else {
            return i1 - i2;
        }
    }
}
