/**
 * Shuli.com Inc.
 * Copyright (c) 2015-2016 All Rights Reserved.
 */
package test1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.IOUtils;

/**
 *
 * @author chentao
 * @version $Id: TestSym.java, v 0.1 2016年3月17日 上午11:15:24 chentao Exp $
 */
public class TestSym {

    private static final String            JAVA_VERSION                  = "java.version";                 //运行时环境版本
    private static final String            JAVA_VENDOR                   = "java.vendor";                  //运行时环境供应商
    private static final String            JAVA_VENDOR_URL               = "java.vendor.url";              //供应商的 URL
    private static final String            JAVA_HOME                     = "java.home";                    //Java 安装目录
    private static final String            JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";//Java 虚拟机规范版本
    private static final String            JAVA_VM_SPECIFICATION_VENDOR  = "java.vm.specification.vendor"; //Java 虚拟机规范供应
    private static final String            JAVA_VM_SPECIFICATION_NAME    = "java.vm.specification.name";   //Java 虚拟机规范名称
    private static final String            JAVA_VM_VERSION               = "java.vm.version";              //Java 虚拟机实现版本
    private static final String            JAVA_VM_VENDOR                = "java.vm.vendor";               //Java 虚拟机实现供应商
    private static final String            JAVA_VM_NAME                  = "java.vm.name";                 //Java 虚拟机实现名称
    private static final String            JAVA_SPECIFICATION_VERSION    = "java.specification.version";   //Java 运行时环境规范版本
    private static final String            JAVA_SPECIFICATION_VENDOR     = "java.specification.vendor";    //Java 运行时环境规范供应商
    private static final String            JAVA_SPECIFICATION_NAME       = "java.specification.name";      //Java 运行时环境规范名称
    private static final String            JAVA_CLASS_VERSION            = "java.class.version";           //Java 类格式版本号
    private static final String            JAVA_CLASS_PATH               = "java.class.path";              //Java 类路径
    private static final String            JAVA_LIBRARY_PATH             = "java.library.path";            //加载库时搜索的路径列表
    private static final String            JAVA_IO_TMPDIR                = "java.io.tmpdir";               //默认的临时文件路径
    private static final String            JAVA_COMPILER                 = "java.compiler";                //要使用的 JIT 编译器的名称
    private static final String            JAVA_EXT_DIRS                 = "java.ext.dirs";                //一个或多个扩展目录的路径
    private static final String            OS_NAME                       = "os.name";                      //操作系统的名称
    private static final String            OS_ARCH                       = "os.arch";                      //操作系统的架构
    private static final String            OS_VERSION                    = "os.version";                   //操作系统的版本
    private static final String            FILE_SEPARATOR                = "file.separator";               //文件分隔符（在 UNIX 系统中是“/”）
    private static final String            PATH_SEPARATOR                = "path.separator";               //路径分隔符（在 UNIX 系统中是“:”）
    private static final String            LINE_SEPARATOR                = "line.separator";               //行分隔符（在 UNIX 系统中是“/n”）
    private static final String            USER_NAME                     = "user.name";                    //用户的账户名称
    private static final String            USER_HOME                     = "user.home";                    //用户的主目录
    private static final String            USER_DIR                      = "user.dir";                     //用户的当前工作目录
    private static HashMap<String, String> systemProperties              = new HashMap<String, String>();

    public static void init() {
        systemProperties.put(JAVA_VERSION, "运行时环境版本");
        systemProperties.put(JAVA_VENDOR, "运行时环境供应商");
        systemProperties.put(JAVA_VENDOR_URL, "供应商的 URL");
        systemProperties.put(JAVA_HOME, "Java 安装目录");
        systemProperties.put(JAVA_VM_SPECIFICATION_VERSION, "Java 虚拟机规范版本");
        systemProperties.put(JAVA_VM_SPECIFICATION_VENDOR, "Java 虚拟机规范供应");
        systemProperties.put(JAVA_VM_SPECIFICATION_NAME, "Java 虚拟机规范名称");
        systemProperties.put(JAVA_VM_VERSION, "Java 虚拟机实现版本");
        systemProperties.put(JAVA_VM_VENDOR, "Java 虚拟机实现供应商");
        systemProperties.put(JAVA_VM_NAME, "Java 虚拟机实现名称");
        systemProperties.put(JAVA_SPECIFICATION_VERSION, "Java 运行时环境规范版本");
        systemProperties.put(JAVA_SPECIFICATION_VENDOR, "Java 运行时环境规范供应商");
        systemProperties.put(JAVA_SPECIFICATION_NAME, "Java 运行时环境规范名称");
        systemProperties.put(JAVA_CLASS_VERSION, "Java 类格式版本号");
        systemProperties.put(JAVA_CLASS_PATH, "Java 类路径");
        systemProperties.put(JAVA_LIBRARY_PATH, "加载库时搜索的路径列表");
        systemProperties.put(JAVA_IO_TMPDIR, "默认的临时文件路径");
        systemProperties.put(JAVA_COMPILER, "要使用的 JIT 编译器的名称");
        systemProperties.put(JAVA_EXT_DIRS, "一个或多个扩展目录的路径");
        systemProperties.put(OS_NAME, "操作系统的名称");
        systemProperties.put(OS_ARCH, "操作系统的架构");
        systemProperties.put(OS_VERSION, "操作系统的版本");
        systemProperties.put(FILE_SEPARATOR, "文件分隔符（在 UNIX 系统中是“/”）");
        systemProperties.put(PATH_SEPARATOR, "路径分隔符（在 UNIX 系统中是“:”）");
        systemProperties.put(LINE_SEPARATOR, "行分隔符（在 UNIX 系统中是“/n”）");
        systemProperties.put(USER_NAME, "用户的账户名称");
        systemProperties.put(USER_HOME, "用户的主目录");
        systemProperties.put(USER_DIR, "用户的当前工作目录");
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        init();
        HashMap<String, String> dataMap = getSystemProperties();
        try {
            writeToDir(dataMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getSystemProperties() {
        HashMap<String, String> properties = new HashMap<String, String>();
        Set<String> sysKeys = systemProperties.keySet();
        for (String key : sysKeys) {
            properties.put(systemProperties.get(key), System.getProperty(key));
        }
        return properties;
    }

    public static void writeToDir(HashMap<String, String> dataMap) throws IOException {
        StringBuilder sb = new StringBuilder();
        Set<String> names = dataMap.keySet();
        for (String name : names) {
            sb.append(name).append("=").append(dataMap.get(name)).append("\n");
        }
        //        FileUtils.writeStringToFile(new File("E:\\SystemProperties.properties"), sb.toString());
        IOUtils.write(sb.toString(), new FileOutputStream("E:\\SystemProperties.properties", true));
    }
}
