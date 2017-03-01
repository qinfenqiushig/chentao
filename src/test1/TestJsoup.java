/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestJsoup {

    public static void main(String[] args) throws IOException {
        File home = new File("resource/home.html");
        Document doc = Jsoup.parse(home, CharsetEnum.GBK.getCharset());
        Element table = doc.getElementById("goods");
        String text = table.select("tr:eq(0)").text();
        System.out.println(text);
        Elements trs = table.select("tr");
        for (Element tr : trs) {
            System.out.println(tr.select("td:eq(0)").text());
        }
    }

    enum CharsetEnum {
        GBK("gbk"), UTF8("utf-8");
        String charset;

        /**
         * @param charset
         */
        private CharsetEnum(String charset) {
            this.charset = charset;
        }

        /**
         * Getter method for property <tt>charset</tt>.
         * 
         * @return property value of charset
         */
        public String getCharset() {
            return charset;
        }

        /**
         * Setter method for property <tt>charset</tt>.
         * 
         * @param charset value to be assigned to property charset
         */
        public void setCharset(String charset) {
            this.charset = charset;
        }

    }
}
