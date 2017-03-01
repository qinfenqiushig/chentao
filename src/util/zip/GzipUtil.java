/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2015 All Rights Reserved.
 */
package util.zip;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipException;

/**
 * Gzip解压缩工具类
 */
public class GzipUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GzipUtil.class);

    /**
     * 压缩
     * 
     * @param source
     * @return
     */
    public static byte[] compress2Bytes(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gzipOutputStream = null;
        try {
            gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(source.getBytes("UTF-8"));
            gzipOutputStream.finish();
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            LOGGER.error(String.format("ERROR ## GzipUtil 压缩文件异常,source[%s]", source), e);
        } finally {
            if (gzipOutputStream != null) {
                try {
                    gzipOutputStream.close();
                } catch (Exception e) {
                    LOGGER.error(String.format("ERROR ## GzipUtil 关闭输入流失败"), e);
                }
            }
        }
        return null;
    }

    /**
     * 解压
     * 
     * @param data
     * @return
     * @throws IOException
     */
    public static String decompress(byte[] data) throws IOException {
        if (data == null || data.length == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        GZIPInputStream gzipInputStream = null;
        try {
            gzipInputStream = new GZIPInputStream(in);
            byte[] buffer = new byte[1024];
            int n;
            while ((n = gzipInputStream.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            out.flush();
            return out.toString("UTF-8");
        } catch (ZipException e) {
            LOGGER.error(String.format("ERROR ## GzipUtil 解压文件ZipException, data.length[%d]", data.length), e);
            throw e;
        } catch (IOException e) {
            LOGGER.error(String.format("ERROR ## GzipUtil 解压文件IOException, data.length[%d]", data.length), e);
            throw e;
        } finally {
            if (gzipInputStream != null) {
                try {
                    gzipInputStream.close();
                } catch (Exception e) {
                    LOGGER.error("ERROR ## GzipUtil 关闭输入流失败", e);
                }
            }
        }
    }
}
