/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

/**
 *
 * @author chentao
 * @version $Id: TestMysql.java, v 0.1 2016年2月5日 上午11:05:49 chentao Exp $
 */
public class TestMysql {

    private static String driver;
    private static String url;
    private static String username;
    private static String password;

    static {
        Properties p = new Properties();
        try {
            p.load(TestMysql.class.getClassLoader().getResourceAsStream("db.properties"));
            driver = p.getProperty("driver");
            url = p.getProperty("url");
            username = p.getProperty("username");
            password = p.getProperty("password");
            Class.forName(driver);
        } catch (IOException e) {
            throw new RuntimeException("未找到相应配置文件", e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("加载驱动失败", e);
        }
    }

    public static void main(String[] args) {
        Connection conn = null;
        String sql = "select *from student";
        try {
            conn = DriverManager.getConnection(url, username, password);
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            while (result.next()) {
                int id = result.getInt("id");
                String name = result.getString("username");
                int age = result.getInt("age");
                System.out.println(id + " " + name + " " + age);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
