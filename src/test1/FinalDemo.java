/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

/**
 *不可变类演示
 * @author chentao
 * @version $Id: FinalDemo.java, v 0.1 2016年4月19日 下午12:58:02 chentao Exp $
 */
//1.设置为final类，防止被子类化
public final class FinalDemo {

    //对经常用到的实例常量化
    private static final FinalDemo ONE = new FinalDemo(0, 0);
    private static final FinalDemo TWO = new FinalDemo(1, 1);
    //2.私有化所有成员变量
    private final double           width;
    private final double           heigh;

    public FinalDemo(double width, double heigh) {
        this.width = width;
        this.heigh = heigh;
    }

    //3.提供获取成员变量方法，不提供修改成员变量的方法
    @Deprecated
    public double getWidth() {
        return width;
    }

    @Deprecated
    public double getHeigh() {
        return heigh;
    }

    //4.返回的是新的FinalDemo实例，原有实例不会改变
    public FinalDemo bigger(FinalDemo demo) {
        return new FinalDemo(width + demo.width, heigh + demo.heigh);
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(heigh);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(width);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    /**
     * @param obj
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FinalDemo other = (FinalDemo) obj;
        if (Double.doubleToLongBits(heigh) != Double.doubleToLongBits(other.heigh))
            return false;
        if (Double.doubleToLongBits(width) != Double.doubleToLongBits(other.width))
            return false;
        return true;
    }

}
