/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.util.ArrayList;
import java.util.Stack;

/**
 *字符串算式转化为算式计算结果
 * @author chentao
 * @version $Id: CalTest.java, v 0.1 2016年3月25日 下午3:18:18 chentao Exp $
 */
public class Calculate {

    private static final char LEFT_BRACKET  = '(';
    private static final char RIGHT_BRACKET = ')';
    private static final char PLUS          = '+';
    private static final char MINUS         = '-';
    private static final char MULTIPLY      = '*';
    private static final char DIVID         = '/';

    /**
     * 将字符串转化成List
     * @param str
     * @return
     */
    public ArrayList<String> getStringList(String str) {
        ArrayList<String> result = new ArrayList<String>();
        String num = "";
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (Character.isDigit(c)) {
                num = num + c;
            } else {
                if (num != "") {
                    result.add(num);
                }
                result.add(c + "");
                num = "";
            }
        }
        if (num != "") {
            result.add(num);
        }
        return result;
    }

    /**
     * 将中缀表达式转化为后缀表达式
     * @param inOrderList
     * @return
     */
    public ArrayList<String> getPostOrder(ArrayList<String> inOrderList) {

        ArrayList<String> result = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < inOrderList.size(); i++) {
            if (Character.isDigit(inOrderList.get(i).charAt(0))) {
                result.add(inOrderList.get(i));
            } else {
                switch (inOrderList.get(i).charAt(0)) {
                    case LEFT_BRACKET:
                        stack.push(inOrderList.get(i));
                        break;
                    case RIGHT_BRACKET:
                        while (!stack.peek().equals("(")) {
                            result.add(stack.pop());
                        }
                        stack.pop();
                        break;
                    default:
                        while (!stack.isEmpty() && compare(stack.peek(), inOrderList.get(i))) {
                            result.add(stack.pop());
                        }
                        stack.push(inOrderList.get(i));
                        break;
                }
            }
        }
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    /**
     * 计算后缀表达式
     * @param postOrder
     * @return
     */
    public Double calculate(ArrayList<String> postOrder) {
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < postOrder.size(); i++) {
            if (Character.isDigit(postOrder.get(i).charAt(0))) {
                stack.push(Double.parseDouble(postOrder.get(i)));
            } else {
                Double back = stack.pop();
                Double front = stack.pop();
                Double res = 0.0;
                switch (postOrder.get(i).charAt(0)) {
                    case PLUS:
                        res = front + back;
                        break;
                    case MINUS:
                        res = front - back;
                        break;
                    case MULTIPLY:
                        res = front * back;
                        break;
                    case DIVID:
                        res = front / back;
                        break;
                }
                stack.push(res);
            }
        }
        return stack.pop();
    }

    /**
     * 比较运算符等级
     * @param peek
     * @param cur
     * @return
     */
    public static boolean compare(String peek, String cur) {
        if (Symbol.MULTIPLY.equals(peek) && (Symbol.DIVID.equals(cur) || Symbol.MULTIPLY.equals(cur) || Symbol.PLUS.equals(cur) || Symbol.MINUS.equals(cur))) {
            return true;
        } else if (Symbol.DIVID.equals(peek) && (Symbol.DIVID.equals(cur) || Symbol.MULTIPLY.equals(cur) || Symbol.PLUS.equals(cur) || Symbol.MINUS.equals(cur))) {
            return true;
        } else if (Symbol.PLUS.equals(peek) && (Symbol.PLUS.equals(cur) || Symbol.MINUS.equals(cur))) {
            return true;
        } else if (Symbol.MINUS.equals(peek) && (Symbol.PLUS.equals(cur) || Symbol.MINUS.equals(cur))) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Calculate calculate = new Calculate();
        String s = "12+(4-2)+2*2+4/2";
        ArrayList<String> result = calculate.getStringList(s); //String转换为List
        result = calculate.getPostOrder(result); //中缀变后缀
        double i = calculate.calculate(result); //计算
        System.out.println(i);

    }

}

enum Symbol {
    LEFT_BRACKET("("), PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVID("/");

    private final String symbol;

    Symbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * Getter method for property <tt>symbol</tt>.
     *
     * @return property value of symbol
     */
    public String getSymbol() {
        return symbol;
    }

    public String toString() {
        return symbol;
    }
}
