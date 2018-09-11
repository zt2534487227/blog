package com.zt.blog.common.util;

import com.zt.blog.common.exception.SystemException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;

/**
 * @Author: zhoutian
 * @Description: file工具类
 * @Date: 2018/9/11
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static String readFileAsString(String path, String encoding)
            throws Exception {
        File f = new File(path);
        Assert.isTrue((f.exists()) || (f.isDirectory()), "文件 [" + path + "] 不存在或者目录(isDirectory)");
        f = null;
        BufferedReader br = null;
        StringBuffer s1 = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), encoding));

            s1 = new StringBuffer();
            String s;
            while ((s = br.readLine()) != null) {
                s1.append(s + "\n");
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        return s1 == null ? "" : s1.toString();
    }

    public static String getExtendFileName(String fname) {
        int slashIndex = fname.lastIndexOf("/");
        int abslashIndex = fname.lastIndexOf("\\");
        if (slashIndex > -1) {
            fname = fname.substring(slashIndex + 1);
        } else if (abslashIndex > -1) {
            fname = fname.substring(abslashIndex + 1);
        }
        if (fname.endsWith(".")) {
            return null;
        }
        if ((fname.startsWith(".")) && (StringUtils.countOccurrencesOf(fname, ".") == 1)) {
            return null;
        }
        int index = fname.lastIndexOf(".");
        if (index < 0) {
            return null;
        }
        return fname.substring(index + 1);
    }

    public static int copy(File in, File out)
            throws IOException {
        Assert.notNull(in, "没有指定源文件");
        Assert.notNull(out, "没有指定目标文件");
        return copy(new BufferedInputStream(new FileInputStream(in)), new BufferedOutputStream(new FileOutputStream(out)));
    }

    public static int copyDirectory(File in, File out, boolean cover)
            throws IOException {
        Assert.notNull(in, "没有指定源目录");
        Assert.notNull(out, "没有指定目标目录");
        Assert.isTrue(in.isDirectory(), "源目录 [" + in.getAbsolutePath() + "] 不是文件夹");
        int counter = 0;
        if ((!out.exists()) && (!out.mkdir())) {
            logger.warn("创建文件夹失败:" + out.getAbsolutePath());
            return counter;
        }
        Assert.isTrue(out.isDirectory(), "目标目录 [" + out.getAbsolutePath() + "] 不是文件夹");
        File[] files = in.listFiles();
        for (int i = 0; i < files.length; i++) {
            File targetFile = new File(out.getAbsolutePath() + File.separator + files[i].getName());
            if (files[i].isFile()) {
                if ((!targetFile.exists()) || (cover)) {
                    copy(files[i], targetFile);
                    counter++;
                }
            } else if (files[i].isDirectory()) {
                counter += copyDirectory(files[i], targetFile, cover);
            }
        }
        return counter;
    }

    public static void copy(byte[] in, File out)
            throws IOException {
        Assert.notNull(in, "没有指定源字节");
        Assert.notNull(out, "没有指定目标字节");
        ByteArrayInputStream inStream = new ByteArrayInputStream(in);
        OutputStream outStream = new BufferedOutputStream(new FileOutputStream(out));
        copy(inStream, outStream);
    }

    public static byte[] copyToByteArray(File in)
            throws IOException {
        Assert.notNull(in, "没有指定源文件");
        return copyToByteArray(new BufferedInputStream(new FileInputStream(in)));
    }

    public static int copy(InputStream in, OutputStream out)
            throws IOException {
        Assert.notNull(in, "没有指定输入流");
        Assert.notNull(out, "没有指定输出流");
        try {
            int byteCount = 0;
            byte[] buffer = new byte['?'];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {}
            try {
                out.close();
            } catch (IOException ex) {}
        }
    }

    public static void copy(byte[] in, OutputStream out)
            throws IOException {
        Assert.notNull(in, "没有指定输入字节");
        Assert.notNull(out, "没有指定输出流");
        try {
            out.write(in); return;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.warn("不能关闭输出流", ex);
            }
        }
    }

    public static byte[] copyToByteArray(InputStream in)
            throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
        copy(in, out);
        return out.toByteArray();
    }

    public static int copy(Reader in, Writer out)
            throws IOException {
        Assert.notNull(in, "没有指定Reader");
        Assert.notNull(out, "没有指定Writer");
        try {
            int byteCount = 0;
            char[] buffer = new char['?'];
            int bytesRead = -1;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                byteCount += bytesRead;
            }
            out.flush();
            return byteCount;
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.warn("不能关闭Reader", ex);
            }
            try {
                out.close();
            } catch (IOException ex) {
                logger.warn("不能关闭Writer", ex);
            }
        }
    }

    public static void copy(String in, Writer out)
            throws IOException {
        Assert.notNull(in, "没有指定源字符串");
        Assert.notNull(out, "没有指定Writer");
        try {
            out.write(in); return;
        } finally {
            try {
                out.close();
            } catch (IOException ex) {
                logger.warn("不能关闭Writer", ex);
            }
        }
    }

    public static String copyToString(Reader in)
            throws IOException {
        StringWriter out = new StringWriter();
        copy(in, out);
        return out.toString();
    }

    public static void delDirectory(String filepath) {
        boolean success = false;
        int tryCount = 0;
        Exception exception = null;
        while ((!success) && (tryCount++ < 10)) {
            System.gc();
            try {
                FileUtils.forceDelete(new File(filepath));
                success = true;
            } catch (Exception e) {
                exception = e;
            }
        }
        if (!success) {
            throw new SystemException("删除" + filepath + "时错误", exception);
        }
    }


}

