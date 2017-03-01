/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.util.Comparator;

public class CompareSon implements Comparator<Son> {

    public static final CompareSon COMPARE_SON = new CompareSon();

    private CompareSon() {

    }

    /** 
     * @param o1
     * @param o2
     * @return
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Son o1, Son o2) {
        return o1.getAge() - o2.getAge();
    }

}
