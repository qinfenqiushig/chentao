/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author chentao
 * @version $Id: ReverserWhatYouSay.java, v 0.1 2016年4月13日 上午9:41:33 chentao Exp $
 */
public class ReverserWhatYouSay {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("请打出想说的话：");
        String words = scan.nextLine();
        System.out.println(RollBack(words));
    }

    public static String Reverse(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }

    public static String RollBack(String str) {
        Stack stack = new Stack();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            stack.push(str.charAt(i));
        }
        StringBuffer sb = new StringBuffer();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}
