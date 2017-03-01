/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.util.HashMap;
import java.util.Stack;

public class StringDemo {
    public static void main(String[] args) {
        String str = "To Be Or Not To Be Is Not A Problem";
        //        HashMap<String, Integer> map = count4Str2(str);
        //        Set<Entry<String, Integer>> set = map.entrySet();
        //        for (Entry<String, Integer> entry : set) {
        //            System.out.println(entry.getKey() + ":" + entry.getValue());
        //        }
        System.out.println(reverse4Str2(str));
    }

    public static HashMap<Character, Integer> count4Str(String str) {
        str = str.replaceAll("\\s", "");
        char[] cs = str.toCharArray();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();
        for (Character c : cs) {
            if (map.containsKey(c)) {
                map.put(c, map.get(c) + 1);
            } else {
                map.put(c, 1);
            }
        }
        return map;
    }

    public static String reverse4Str(String str) {
        char[] cs = str.toCharArray();
        int len = cs.length - 1;
        int half = len / 2;
        for (int i = 0; i <= half; i++) {
            char t = cs[i];
            cs[i] = cs[len - i];
            cs[len - i] = t;
        }
        return new String(cs);
    }

    public static HashMap<String, Integer> count4Str2(String str) {
        str = str.replaceAll("\\s", "");
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        while (str.length() > 0) {
            int len = str.length();
            String first = str.substring(0, 1);
            String less = str.replaceAll(first, "");
            int lessStr = less.length();
            map.put(first, len - lessStr);
            str = less;
        }
        return map;
    }

    public static String reverse4Str2(String str) {
        Stack<Character> stack = new Stack<Character>();
        int len = str.length();
        for (int i = 0; i < len; i++) {
            stack.push(str.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }
}
