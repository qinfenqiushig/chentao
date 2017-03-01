/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author chentao
 * @version $Id: Son.java, v 0.1 2016年4月15日 上午8:53:54 chentao Exp $
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Son extends Parent implements Comparable<Son>{

    private Integer age;
    private Date    birth;
    private String username;
    private String password;

    /**
     * @param age
     * @param birth
     */
    public Son(Integer age, Date birth) {
        super();
        this.age = age;
        this.birth = birth;
    }

    public void sayBye() {
        System.out.println("see you");
    }

    public void sing() {
        System.out.println("i like singing");
    }

    /**
     *  Son类中，定义son对象之间的比较规则
     * @param o
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Son o) {
        return this.age-o.getAge();
    }

    public static void main(String[] args) {
        Son s = new Son(1, new Date());
        Son s1 = new Son(3, new Date());
        Son s2 = new Son(4, new Date());
        Son s3 = new Son(2, new Date());
        Son[] sons = new Son[] { s, s1, s2, s3 };
        // 数组或集合排序时，再自定义比较规则
        Arrays.sort(sons, new Comparator<Son>() {

            @Override
            public int compare(Son o1, Son o2) {
                return o1.getAge() - o2.getAge();
            }

        });
//        Arrays.sort(sons, CompareSon.COMPARE_SON);
        System.out.println(Arrays.toString(sons));
    }

}
