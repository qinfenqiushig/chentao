/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.math.BigDecimal;

/**
 *BigDecimal精确运算
 * @author chentao
 * @version $Id: PreciseCompute.java, v 0.1 2016年5月9日 上午9:48:32 chentao Exp $
 */
public class PreciseCompute {
    //默认除法精度
    private static final int DEF_DIV_SCALE = 2;

    /**
     *
     *提供精确的加法运算
     * @param d1，被加数
     * @param d2，加数
     * @return d1+d2的值
     */
    public static double add(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算
     *
     * @param d1，被减数
     * @param d2，减数
     * @return d1-d2的值
     */
    public static double subtract(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算
     *
     * @param d1
     * @param d2
     * @return d1*d2的值
     */
    public static double multiply(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 除法运算，小数超过10位数时，默认保留10位小数，采取四舍五入
     * @param d1
     * @param d2
     * @return d1/d2的值
     */
    public static double divide(double d1, double d2) {
        if (d1 == 0) {
            throw new IllegalArgumentException("分母不能为0");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        return b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_DOWN).doubleValue();
    }
}
