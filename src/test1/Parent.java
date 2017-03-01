/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import lombok.Data;

@Data
public class Parent {

    public Integer id;
    public String  name;

    /**
     * 
     */
    public Parent() {
        super();
        sayBye();
    }

    /**
     * @param id
     * @param name
     */
    public Parent(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    /**
     * Getter method for property <tt>id</tt>.
     * 
     * @return property value of id
     */
    public Integer getId() {
        return id;
    }

    public void sayBye() {
        System.out.println("good-bye");
    }

}
