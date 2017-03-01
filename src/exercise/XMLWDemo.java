/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import entity.DbInfo;

/**
 *将properties配置文件写入xml配置文件中
 * @author chentao
 * @version $Id: XMLWDemo.java, v 0.1 2016年3月1日 下午12:02:29 chentao Exp $
 */
public class XMLWDemo {

    private static List<DbInfo> dbs = new ArrayList<DbInfo>();

    /**
     * 初始化参数，读取properties文件
     *
     * @throws IOException
     */
    public static void init() throws IOException {
        Properties p = new Properties();
        p.load(XMLWDemo.class.getClassLoader().getResourceAsStream("db.properties"));
        DbInfo myInfo = new DbInfo();
        myInfo.setDriver(p.getProperty("mysql.driver"));
        myInfo.setUrl(p.getProperty("mysql.url"));
        myInfo.setUsername(p.getProperty("mysql.username"));
        myInfo.setPassword(p.getProperty("mysql.password"));
        myInfo.setDbname("MySqlInfo");
        DbInfo orcInfo = new DbInfo();
        orcInfo.setDriver(p.getProperty("oracle.driver"));
        orcInfo.setUrl(p.getProperty("oracle.url"));
        orcInfo.setUsername(p.getProperty("oracle.username"));
        orcInfo.setPassword(p.getProperty("oracle.password"));
        orcInfo.setDbname("OracleInfo");
        dbs.add(myInfo);
        dbs.add(orcInfo);
    }

    public static void main(String[] args) {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException("初始化异常", e);
        }
        try {
            writeXML();
        } catch (IOException e) {
            throw new RuntimeException("xml写入失败", e);
        }
    }

    public static void writeXML() throws IOException {
        Document doc = DocumentHelper.createDocument();
        Element root = doc.addElement("Project");
        Element sqls = root.addElement("sqls");
        for (DbInfo info : dbs) {
            Element mysql = sqls.addElement("sql");
            mysql.addAttribute("name", info.getDbname());
            Element driver = mysql.addElement("driver");
            driver.addText(info.getDriver());
            Element url = mysql.addElement("url");
            url.addText(info.getUrl());
            Element username = mysql.addElement("username");
            username.addText(info.getUsername());
            Element password = mysql.addElement("password");
            password.addText(info.getPassword());
        }
        File file = new File("resource");
        if (!file.exists()) {
            file.mkdirs();
        }
        OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath() + "/db.xml"), "utf8");
        XMLWriter writer = new XMLWriter(osw, OutputFormat.createPrettyPrint());
        writer.write(doc);
        writer.close();
        System.out.println("写入完毕！");
    }
}
