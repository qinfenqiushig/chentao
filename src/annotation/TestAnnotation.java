/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package annotation;

@Check(id = 0, name = "class", money = Long.class)
public class TestAnnotation {
    @Check(id = 1, name = "field", money = Long.class)
    private String name;

    /**
     * @param name
     */
    @Check(id = 2, name = "constructor", money = Long.class)
    public TestAnnotation(String name) {
        super();
        this.name = name;
    }

    @Check(id = 3, name = "method", money = Long.class)
    public static void show(@Check(id = 4, name = "param", money = Long.class) String str) {

    }
}
