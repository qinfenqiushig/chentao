/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

/**
 *
 * @author chentao
 * @version $Id: AutoBoxing.java, v 0.1 2016年4月13日 下午2:41:36 chentao Exp $
 */
public class AutoBoxing {

    public static void main(String[] args) {
        Long sum = 0L;
        for (long i = 0; i < Integer.MAX_VALUE; i++) {
            sum += i;//频繁装箱，影响性能
        }
        System.out.println(sum);
    }
}
