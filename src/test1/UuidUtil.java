/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.util.UUID;

public class UuidUtil {

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            String str = UUID.randomUUID().toString();
            System.out.println(str);
        }
    }
}
