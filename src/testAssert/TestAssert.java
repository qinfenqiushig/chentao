/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package testAssert;

import java.util.Arrays;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TestAssert {

    private int      age;
    private String   name;
    private String[] hobbies;

    public static void main(String[] args) {
        TestAssert as = new TestAssert();
        as.setAge(101);
        as.setName("JACK");
        as.setHobbies(new String[] { "game", "read", "heihei" });
        int age = as.getAge();
        assert age > 0 && age < 100 : "年龄需在0~100之间";
        System.out.println(as);
    }

    @Override
    public String toString() {
        return this.name + "," + this.age + "," + Arrays.toString(this.hobbies);
    }

    /**
     * Getter method for property <tt>age</tt>.
     *
     * @return property value of age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter method for property <tt>age</tt>.
     *
     * @param age value to be assigned to property age
     */
    public void setAge(int age) {
        if (age < 0 || age > 100) {
            throw new IllegalArgumentException("年龄需在0~100之间");
        }
        this.age = age;
    }

    /**
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>hobbies</tt>.
     *
     * @return property value of hobbies
     */
    public String[] getHobbies() {
        return hobbies;
    }

    /**
     * Setter method for property <tt>hobbies</tt>.
     *
     * @param hobbies value to be assigned to property hobbies
     */
    public void setHobbies(String[] hobbies) {
        this.hobbies = hobbies;
    }
}
