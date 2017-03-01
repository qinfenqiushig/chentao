/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package testAssert;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author chentao
 * @version $Id: TestException.java, v 0.1 2016年5月11日 上午10:25:21 chentao Exp $
 */
public class TestException {

    public static void main(String[] args) throws IOException {
        //        File file = new File("testLog.txt");
        //        readFileToString(file);
        //        System.out.println(fasterReadFileToString(file));
        //        DecimalFormat dc = new DecimalFormat();
        //        dc.applyPattern("0.00");
        //        System.out.println(dc.format(12.3));
//        System.out.println(generateMixed(32));
//        String str = FileUtils.readFileToString(new File("C:\\Users\\chentao\\Desktop\\jn.html"),"utf8");
//        int index = str.indexOf("dataList({\r");
//                if(index!=-1){
//                    int cindex = str.indexOf(")",index);
//                    String datas = str.substring(index+9,cindex);
//                    System.out.println(datas);
//                }
//                InputStreamReader isr = new InputStreamReader(System.in);
//                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream("testLog.txt",true),"utf8");
//                int ind =-1;
//                while((ind = isr.read())!=-1){
//                    osw.write(ind);
//                    osw.flush();
//                }
//                isr.close();
//                osw.close();
        System.out.println(URLEncoder.encode("杨明","gbk"));
    }

    /**
     *
     */
    private static String readFileToString(File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int index = -1;
            int count = 0;
            byte[] byts = new byte[(int) file.length()];
            while ((index = fis.read()) != -1) {
                byts[count++] = (byte) index;
            }
            return new String(byts, "utf8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String fasterReadFileToString(File file) {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "utf8");
            int index = -1;
            StringBuilder sb = new StringBuilder();
            while ((index = isr.read()) != -1) {
                char c = (char) index;
                sb.append(c);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String generateMixed(int n){
        char[] chars = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
                'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++){
            int id = (int) Math.ceil(Math.random()*n);
            sb.append(chars[id]);
        }
        return sb.toString();
    }

    private static String getLastDateOfMonth(Calendar calendar){
        calendar.set(Calendar.DAY_OF_MONTH,calendar.getMaximum(Calendar.DAY_OF_MONTH));
        return DateFormatUtils.format(calendar,"yyyy-MM-dd");
    }
}
