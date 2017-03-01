/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package enum4;

/**
 * 1.  枚举中的每个枚举常量(WEEKDAY...)，都是该枚举类的一个实例，
 *  需要实现枚举类中的抽象方法(overtimePay)，同时也共享了枚举类的常规方法(pay)
 *
 * 2.  嵌套枚举
 */
public enum PayrollDay {

    MONDAY(PayType.WEEKDAY), TUESDAY(PayType.WEEKDAY), WEDNESDAY(PayType.WEEKDAY), THURSDAY(PayType.WEEKDAY), FRIDAY(PayType.WEEKDAY), SATURDAY(PayType.WEEKDAY), SUNDAY(PayType.WEEKDAY);
    private final PayType payType;

    PayrollDay(PayType payType) {
        this.payType = payType;
    }

    double pay(double hoursWorked, double payRate) {
        return payType.pay(hoursWorked, payRate);
    }

    //工资计算类型枚举
    private enum PayType {
        WEEKDAY {

            @Override
            double overtimePay(double hoursWorked, double payRate) {
                return (hoursWorked - HOURS_PER_SHIFT) * payRate / 2;
            }

        },
        WEEKEND {

            @Override
            double overtimePay(double hoursWorked, double payRate) {
                return hoursWorked * payRate / 2;
            }

        };
        private static final int HOURS_PER_SHIFT = 8;

        abstract double overtimePay(double hoursWorked, double payRate);

        double pay(double hoursWorked, double payRate) {
            double basePay = hoursWorked * payRate;
            return basePay + overtimePay(hoursWorked, payRate);
        }
    }
}