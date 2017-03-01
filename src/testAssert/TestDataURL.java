/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package testAssert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author chentao
 * @version $Id: TestDataURL.java, v 0.1 2016年5月19日 下午3:23:18 chentao Exp $
 */
public class TestDataURL {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        String path = "C:\\Users\\chentao\\Desktop\\desk.jpg";
        String newPath = "D:\\image\\image.html";
        try {
            putImageToHtml(path, newPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void putImageToHtml(String imagePath,String newPath) throws IOException{
        File file = new File(imagePath);
        if(!file.exists()){
            throw new FileNotFoundException("找不到文件所在的路径！");
        }
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int len = -1;
        byte[] imageBytes = null;
        while((len =fis.read(bytes))!=-1){
            output.write(bytes, 0, len);
        }
        fis.close();
        output.close();
        imageBytes = output.toByteArray();
        Base64 base = new Base64();
        String imageContent = new String(base.encode(imageBytes));
        String image = "<image src='data:image/jpeg;base64,"+imageContent+"' />";
        File newFile = new File(newPath);
        FileWriter writer = new FileWriter(newFile);
        writer.write(image);
        writer.flush();
        writer.close();
    }
}
