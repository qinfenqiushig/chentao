/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 *
 * @author chentao
 * @version $Id: SAXRDemo.java, v 0.1 2016��3��1�� ����12:02:14 chentao Exp $
 */
public class SAXRDemo {

    public static void readXMl() throws DocumentException {
        SAXReader reader = new SAXReader();
        Document doc = reader.read("resource/db.xml");
        Element root = doc.getRootElement();
        Element element = root.element("sqls");
        List<Element> elements = element.elements();
        for (Element e : elements) {
            String name = e.attributeValue("name");
            String url = e.elementText("url");
            String driver = e.elementText("driver");
            String username = e.elementText("username");
            String password = e.elementText("password");
            System.out.println(name + ":" + driver + "-" + url + "-" + username + "-" + password);
        }
    }

    public static void main(String[] args) {
        try {
            readXMl();
        } catch (DocumentException e) {
            throw new RuntimeException("��ȡʧ��", e);
        }
    }
}
