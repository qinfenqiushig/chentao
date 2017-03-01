/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.lock;

import java.math.BigDecimal;

/**
 *
 * @author chentao
 * @version $Id: MyCount.java, v 0.1 2016年6月6日 上午10:12:38 chentao Exp $
 */
public class MyCount {

    private String cid;
    private BigDecimal cash;

    MyCount(){

    }

    MyCount(String cid,BigDecimal cash){
        this.cid = cid;
        this.cash = cash;
    }

    /**
     * Getter method for property <tt>cid</tt>.
     *
     * @return property value of cid
     */
    public String getCid() {
        return cid;
    }

    /**
     * Setter method for property <tt>cid</tt>.
     *
     * @param cid value to be assigned to property cid
     */
    public void setCid(String cid) {
        this.cid = cid;
    }

    /**
     * Getter method for property <tt>cash</tt>.
     *
     * @return property value of cash
     */
    public BigDecimal getCash() {
        return cash;
    }

    /**
     * Setter method for property <tt>cash</tt>.
     *
     * @param cash value to be assigned to property cash
     */
    public void setCash(BigDecimal cash) {
        this.cash = cash;
    }

    public void takeCash(BigDecimal num){
        if(this.cash.compareTo(num)<0){
            throw new IllegalArgumentException("余额不足！");
        }
        this.cash = this.cash.subtract(num);
    }

    public void putCash(BigDecimal num){
        if(num.compareTo(BigDecimal.ZERO)<=0){
            throw new IllegalArgumentException("存入金额须大于0！");
        }
        this.cash = this.cash.add(num);
    }
}
