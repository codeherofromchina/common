package com.erui.comm;
import java.text.DecimalFormat;

/**
 * Created by erui on 2017/10/20.
 */
public class RateUtil {
    public static void main(String args[]){
        System.out.println(intChainRate(5,8));
        System.out.println(doubleChainRate(344.55,888.8));
        double v = intChainRate(1, 1);
        System.out.println(v);
    }
     
    /**
     * @Author:SHIGS
     * @Description:整数计算保留四位位小数
     * @Date:15:21 2017/10/20
     * @modified By
     */
    public static double intChainRate(int element, int denominator){
        DecimalFormat df=new DecimalFormat("0.0000");
        if (denominator > 0){
         return Double.parseDouble(df.format(((double) element)/((double) denominator)));
        }if (element == 0 || denominator == 0 ){
            return 0;
        }
        return 1;
    }
     /**
      * @Author:SHIGS
      * @Description :小数计算保留四位小数
      * @Date:15:22 2017/10/20
      * @modified By
      */
    public static double doubleChainRate(double element, double denominator){
        DecimalFormat df=new DecimalFormat("0.0000");
        if (denominator > 0){
        return Double.parseDouble(df.format((element)/( denominator)));
        }if (element == 0 || denominator == 0 ){
            return 0;
        }
        return 1;
    }

    /**
     * @Author:SHIGS
     * @Description:整数计算保留两位小数
     * @Date:15:21 2017/10/20
     * @modified By
     */
    public static double intChainRateTwo(int element, int denominator){
        DecimalFormat df=new DecimalFormat("0.00");
        if (denominator > 0){
            return Double.parseDouble(df.format(((double) element)/((double) denominator)));
        }if (element == 0 || denominator == 0 ){
            return 0;
        }
        return 1;
    }
    /**
     * @Author:SHIGS
     * @Description :小数计算保留两位小数
     * @Date:15:22 2017/10/20
     * @modified By
     */
    public static double doubleChainRateTwo(double element, double denominator){
        DecimalFormat df=new DecimalFormat("0.00");
        if (denominator > 0){
            return Double.parseDouble(df.format((element)/( denominator)));
        }if (element == 0 || denominator == 0 ){
            return 0;
        }
        return 1;
    }
}
