/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import entity.DbInfo;

public class ReflectDemo {

    public static void reflectByClass() throws Exception {
        Class cla = DbInfo.class;
        //        cla = Class.forName("DbInfo");
        //        DbInfo info = new DbInfo();
        //        cla = info.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getName() + ":" + f.getType());
        }
        Method[] methods = cla.getDeclaredMethods();
        for (Method m : methods) {
            System.out.println(m.getName() + ":" + m.getParameterTypes());
        }
        Object obj = cla.newInstance();
        Method method = cla.getDeclaredMethod("setPassword", String.class);
        method.invoke(obj, "12345");
        Method mm = cla.getDeclaredMethod("toString");
        System.out.println(mm.invoke(obj));
    }

    public static void main(String[] args) throws Exception {
        reflectByClass();
    }
}
