/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ParseAnnotation {

    public static void parseTypeAnnotation() throws ClassNotFoundException {
        Class annotation = Class.forName("annotation.TestAnnotation");
        Annotation[] annotations = annotation.getAnnotations();
        for (Annotation ann : annotations) {
            Check check = (Check) ann;
            println(annotation.getName(), check.id(), check.name(), check.money());
        }
    }

    public static void parseFieldAnnotation() {
        Field[] fields = TestAnnotation.class.getDeclaredFields();
        for (Field field : fields) {
            boolean hasAnnotation = field.isAnnotationPresent(Check.class);
            if (hasAnnotation) {
                Check check = field.getAnnotation(Check.class);
                println(field.getName(), check.id(), check.name(), check.money());
            }
        }
    }

    public static void parseConstructAnnotation() throws SecurityException, ClassNotFoundException {
        Constructor[] cons = Class.forName("annotation.TestAnnotation").getConstructors();
        for (Constructor con : cons) {
            boolean hasAnnotation = con.isAnnotationPresent(Check.class);
            if (hasAnnotation) {
                Check check = (Check) con.getAnnotation(Check.class);
                println(con.getName(), check.id(), check.name(), check.money());
            }
        }
    }

    public static void parseMethodAnnotation() throws SecurityException, ClassNotFoundException {
        Method[] methods = Class.forName("annotation.TestAnnotation").getDeclaredMethods();
        for (Method method : methods) {
            boolean hasAnnotation = method.isAnnotationPresent(Check.class);
            if (hasAnnotation) {
                Check check = method.getAnnotation(Check.class);
                println(method.getName(), check.id(), check.name(), check.money());
            }
        }
    }

    /**
     *
     * @param name
     * @param id
     * @param name2
     * @param money
     */
    private static void println(String name, int id, String name2, Class<Long> money) {
        System.out.println(String.format("AnnotationName = %s,     id = %d,    name = %s,  money = %s", name, id, name2, money));
    }

    public static void main(String[] args) {
        try {
            parseTypeAnnotation();
            parseFieldAnnotation();
            parseConstructAnnotation();
            parseMethodAnnotation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
