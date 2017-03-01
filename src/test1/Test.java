/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;

/**
 *
 * @author chentao
 * @version $Id: Test.java, v 0.1 2016年5月6日 上午9:32:00 chentao Exp $
 */
public class Test {

    public static String js;

    public static void main(String[] args) throws IOException {
//                js = FileUtils.readFileToString(new File("C:\\Users\\chentao\\Desktop\\encrypto.js"));
//                System.out.println(js);
//                String str = "111111";
//                System.out.println(EncryPwd(str)); 
        //        String str = "回首已然看见故乡月亮";
        //        System.out.println(containsAny(str));
//        List list = Collections.emptyList();//内容不可变的空集合
//        List list2 = Collections.emptyList();
//        System.out.println(list == list2);//为true
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByMimeType("text/javascript");
//        try {
//            engine.put("dbInfo", new DbInfo("http", "hello", "123", "driver", "dbname"));
//            engine.eval("println(dbInfo)");
//            DbInfo dbIfo = (DbInfo)engine.get("dbInfo");
//        } catch (ScriptException e) {
//            e.printStackTrace();
//        }
        Boolean showDetai = null;
        System.out.println(String.format("showDeatil: [%s]", showDetai));
    }

    public static String EncryPwd(String username) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            engine.eval(js);
            engine.eval("var pwd = new base64().encode('"+username+"');");
            username = (String) engine.get("pwd");
            engine.eval("delete pwd");
            return username;
        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return null;
    }

    public static boolean isBlank(String str) {
        int len = str.length();
        boolean flage = true;
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                flage = false;
            }
        }
        return flage;
    }

    public static String toMoney(String str) {
        int index = str.indexOf(".");
        if (index == -1) {
            return getMoneyShape(str);
        } else {
            String s = str.substring(0, index);
            return getMoneyShape(s) + str.substring(index);
        }
    }

    /**
     *1,231.4
     * @param str
     * @return
     */
    private static String getMoneyShape(String str) {
        char[] cs = str.toCharArray();
        int len = cs.length;
        int key = 1;
        if (len <= 3) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = len - 1; i >= 0; i--) {
            sb.append(cs[i]);
            if (key++ % 3 == 0) {
                sb.append(",");
            }
        }
        return sb.reverse().toString();
    }

    public static String ascii2native(String ascii) {
        int n = ascii.length() / 6;
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0, j = 2; i < n; i++, j += 6) {
            String code = ascii.substring(j, j + 4);
            char ch = (char) Integer.parseInt(code, 16);
            sb.append(ch);
        }
        return sb.toString();
    }

    public static boolean containsAny(String str, String... orgs) {
        if (orgs.length == 0) {
            throw new IllegalArgumentException("比较参数长度不能为0！");
        }
        for (String s : orgs) {
            if (str.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
