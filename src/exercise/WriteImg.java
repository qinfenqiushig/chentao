/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package exercise;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author chentao
 * @version $Id: WriteImg.java, v 0.1 2016年3月16日 上午10:32:44 chentao Exp $
 */
public class WriteImg {

    private static String filePath = "C:\\Users\\Public\\Pictures\\Sample Pictures";
    private static String newPath  = "E:\\images";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        File[] images = getImages();
        writeImage(images);
    }

    /**
     *
     * @param images
     */
    private static void writeImage(File[] images) {
        File newImgs = new File(newPath);
        if (!newImgs.exists())
            newImgs.mkdir();
        for (File image : images) {
            print(" * img <%s>(%s)", image.getName().split("\\.")[0], image.getName().split("\\.")[1]);
            FileOutputStream imageWriter = null;
            FileInputStream imageReader = null;
            try {
                imageWriter = new FileOutputStream(newImgs.getAbsolutePath() + "\\" + image.getName());
                imageReader = new FileInputStream(image);
                int index = -1;
                while ((index = imageReader.read()) != -1) {
                    imageWriter.write(index);
                }
                System.out.println("......复制完毕！");
            } catch (Exception e) {
                throw new RuntimeException("文件读写失败！", e);
            } finally {
                if (imageWriter != null) {
                    try {
                        imageWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (imageReader != null) {
                    try {
                        imageReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    private static File[] getImages() {
        File imageDir = new File(filePath);
        FileFilter filter4Img = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                String fileName = pathname.getName();
                boolean isImg = fileName.split("\\.")[1].matches("(jpe?g|png)$");
                return isImg;
            }
        };
        File[] images = imageDir.listFiles(filter4Img);
        print("imageLenth (%d):", images.length);
        return images;
    }

    /**
     *
     * @param format
     * @param args
     */
    private static void print(String format, Object... args) {
        System.out.println(String.format(format, args));
    }

}
