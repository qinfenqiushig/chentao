/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package thread.lock;

import java.util.concurrent.locks.Lock;

public class User {

    private String name;
    private MyCount myCount;
    private Lock lock;
    
    User(){
        
    }
    
    User(String name,MyCount myCount,Lock lock){
        this.name = name;
        this.myCount = myCount;
        this.lock = lock;
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
     * Getter method for property <tt>myCount</tt>.
     * 
     * @return property value of myCount
     */
    public MyCount getMyCount() {
        return myCount;
    }

    /**
     * Setter method for property <tt>myCount</tt>.
     * 
     * @param myCount value to be assigned to property myCount
     */
    public void setMyCount(MyCount myCount) {
        this.myCount = myCount;
    }

    /**
     * Getter method for property <tt>lock</tt>.
     * 
     * @return property value of lock
     */
    public Lock getLock() {
        return lock;
    }

    /**
     * Setter method for property <tt>lock</tt>.
     * 
     * @param lock value to be assigned to property lock
     */
    public void setLock(Lock lock) {
        this.lock = lock;
    }
    
}
