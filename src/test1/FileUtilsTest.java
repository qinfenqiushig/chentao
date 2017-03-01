/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *文件工具类
 * @author chentao
 * @version $Id: FileUtilsTest.java, v 0.1 2016年3月16日 下午2:12:28 chentao Exp $
 */
public class FileUtilsTest {

    public static void main(String[] args) throws IOException {
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("java.io.tmpdir"));
        System.out.println(System.getProperty("user.home"));
        File file = new File("testLog.txt");
        file.setWritable(false);
        System.out.println(file.canRead());
        FileInputStream fis = openInputStream(file);
        FileOutputStream fos = openOutputStream(file);
    }

    /**
     * 创建一个文件输入流
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileInputStream openInputStream(File file) throws IOException {
        if (file.exists()) {

            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }

            if (!file.canRead()) {
                throw new IOException("File '" + file + "' can not be read");
            }
        } else {
            throw new FileNotFoundException("File '" + file + "' does not exists");
        }
        return new FileInputStream(file);
    }

    /**
     * 创建一个文件输出流
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static FileOutputStream openOutputStream(File file) throws IOException {
        if (file.exists()) {

            if (file.isDirectory()) {
                throw new IOException("File '" + file + "' exists but is a directory");
            }
            if (!file.canWrite()) {
                throw new IOException("File '" + file + "' can not be written to");
            }
        } else {
            File parent = file.getParentFile();
            if (parent != null) {
                if (!parent.mkdirs() && !parent.isDirectory()) {
                    throw new IOException("Directory '" + parent + "' can not be created");
                }
            }
        }
        return new FileOutputStream(file);
    }
}
