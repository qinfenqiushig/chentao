package util.zip;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Zip解压缩工具类
 * <p>
 * Created by chentao on 2016/12/5.
 */
public class ZipUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipUtil.class);

    /**
     * 压缩文件
     *
     * @param sourceFile 需要被压缩的文件（文件夹）
     * @param rootName   被压缩文件的根路径名称
     * @param zipFile    压缩后的zip文件
     */
    public static void compress(File sourceFile, String rootName, File zipFile) {
        if (!sourceFile.exists()) {
            throw new RuntimeException(String.format("需要压缩的文件 [%s] 不存在！", sourceFile));
        }
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            writeFileToZip(sourceFile, rootName, zipOutputStream);
        } catch (FileNotFoundException e) {
            LOGGER.error("压缩文件出错", e);
        } finally {
            IOUtils.closeQuietly(zipOutputStream);
        }
    }

    private static void writeFileToZip(File sourceFile, String rootName, ZipOutputStream zipOutputStream) {
        FileInputStream fileInputStream = null;
        try {
            if (sourceFile.isDirectory()) {
                for (File file : sourceFile.listFiles()) {
                    writeFileToZip(file, rootName == null ? file.getName() : rootName + "/" + file.getName(), zipOutputStream);
                }
            } else {
                LOGGER.info(String.format("开始压缩文件 [%s] ... ...", rootName));
                zipOutputStream.putNextEntry(new ZipEntry(rootName));
                byte[] bytes = new byte[1024 * 10];
                fileInputStream = new FileInputStream(sourceFile);
                int len = -1;
                while ((len = fileInputStream.read(bytes)) != -1) {
                    zipOutputStream.write(bytes, 0, len);
                }
                zipOutputStream.flush();
                LOGGER.info(String.format("[%s]压缩完成！", rootName));
            }
        } catch (IOException e) {
            LOGGER.error("压缩文件失败", e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }

    public static void deCompress(File target, File source) {
        FileOutputStream outputStream = null;
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(source));
            ZipEntry zipEntry = null;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                LOGGER.info(String.format("开始解压文件[%s]", zipEntry.getName()));
                File file = new File(target, zipEntry.getName());
                if (!file.exists()) {
                    new File(file.getParent()).mkdirs();
                }
                outputStream = new FileOutputStream(file);
                byte[] bytes = new byte[1024 * 10];
                int len = -1;
                while ((len = zipInputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, len);
                }
                outputStream.flush();
                LOGGER.info(String.format("[%s] 解压完成！", zipEntry.getName()));
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("解压失败", e);
        } catch (IOException e) {
            LOGGER.error("解压失败", e);
        } finally {
            IOUtils.closeQuietly(zipInputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }
}
