/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package enum4;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author chentao
 * @version $Id: Operation.java, v 0.1 2016年4月26日 下午3:49:55 chentao Exp $
 */
public enum Operation {

    PLUS("+") {
        double apply(double x, double y) {
            return x + y;
        }
    },
    MINUS("-") {
        double apply(double x, double y) {
            return x - y;
        }
    },
    TIMES("*") {
        double apply(double x, double y) {
            return x * y;
        }
    },
    DIVIDE("/") {
        double apply(double x, double y) {
            return x / y;
        }
    },
    HALF("1/2") {
        @Override
        double apply(double x, double y) {
            return (x + y) / 2;
        }
    };

    private final String symbol;

    //提供字符串与枚举类之间的相互转换，基于枚举规范的toString()
    private static final Map<String, Operation> stringToEnum = new HashMap<String, Operation>();

    static {
        for (Operation p : values()) {
            stringToEnum.put(p.toString(), p);
        }
    }

    public Operation fromString(String symbol) {
        return stringToEnum.get(symbol);
    }

    Operation(String symbol) {
        this.symbol = symbol;
    }

    abstract double apply(double x, double y);

    //重写toString
    @Override
    public String toString() {
        return symbol;
    }
}
