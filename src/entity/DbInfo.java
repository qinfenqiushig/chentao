/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package entity;

public class DbInfo {

    private String url;
    private String username;
    private String password;
    private String driver;
    private String dbname;

    /**
     * @param url
     * @param username
     * @param password
     * @param driver
     */
    public DbInfo(String url, String username, String password, String driver, String dbname) {
        super();
        this.url = url;
        this.username = username;
        this.password = password;
        this.driver = driver;
        this.dbname = dbname;
    }

    /**
     * 
     */
    public DbInfo() {
        super();
    }

    /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Getter method for property <tt>username</tt>.
     * 
     * @return property value of username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter method for property <tt>username</tt>.
     * 
     * @param username value to be assigned to property username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method for property <tt>password</tt>.
     * 
     * @return property value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for property <tt>password</tt>.
     * 
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter method for property <tt>driver</tt>.
     * 
     * @return property value of driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * Setter method for property <tt>driver</tt>.
     * 
     * @param driver value to be assigned to property driver
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * Getter method for property <tt>dbname</tt>.
     * 
     * @return property value of dbname
     */
    public String getDbname() {
        return dbname;
    }

    /**
     * Setter method for property <tt>dbname</tt>.
     * 
     * @param dbname value to be assigned to property dbname
     */
    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    /** 
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "DbInfo [url=" + url + ", username=" + username + ", password=" + password + ", driver=" + driver + ", dbname=" + dbname + "]";
    }

}
