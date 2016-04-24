package com.comiyun.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 文件工具类
 *
 * @author ydwcn
 * @ClassName: FileUtil
 * @date 2014-6-18 下午1:29:31
 */
public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public static void deleteFile(String filePath) {
        try {
            logger.info("Begin delete files from :=" + filePath);
            File file = new File(filePath);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] fileList = file.listFiles();
                    if ((fileList != null) && (fileList.length > 0))
                        for (int i = 0; i < fileList.length; i++) {
                            String path = fileList[i].getCanonicalPath();
                            deleteFile(path);
                        }
                } else {
                    logger.info("Delete file :=" + file.getCanonicalPath());
                    file.delete();
                }
                file.delete();
            }
        } catch (Exception e) {
            logger.error("delete file error", e);
        }
    }

    public static void copyFiles(String src, String dest, String newFileName) {
        try {
            logger.info("**Begin copy files from " + src + " to " + dest
                    + "==========");
            File srcFile = new File(src);
            if (srcFile.exists()) {
                createDirectory(dest);
                if (srcFile.isDirectory()) {
                    String newDest = dest + File.separator + srcFile.getName();
                    File newDestFile = new File(newDest);
                    if (!newDestFile.exists()) {
                        newDestFile.mkdir();
                    }
                    File[] fileList = srcFile.listFiles();
                    if ((fileList != null) && (fileList.length > 0))
                        for (int i = 0; i < fileList.length; i++)
                            copyFiles(fileList[i].getCanonicalPath(), newDest);
                } else {
                    cpFile(new File(src), new File(dest + File.separator
                            + newFileName));
                }
            } else {
                throw new RuntimeException("Source file[" + src
                        + "] does not exsit!");
            }
        } catch (Exception e) {
            logger.error("copy file error", e);
        }
    }

    public static void copyFiles(String src, String dest) {
        try {
            logger.info("**Begin copy files from " + src + " to " + dest
                    + "==========");
            File srcFile = new File(src);
            if (srcFile.exists()) {
                createDirectory(dest);
                if (srcFile.isDirectory()) {
                    String newDest = dest + File.separator + srcFile.getName();
                    File newDestFile = new File(newDest);
                    if (!newDestFile.exists()) {
                        newDestFile.mkdir();
                    }
                    File[] fileList = srcFile.listFiles();
                    if ((fileList != null) && (fileList.length > 0))
                        for (int i = 0; i < fileList.length; i++)
                            copyFiles(fileList[i].getCanonicalPath(), newDest);
                } else {
                    cpFile(new File(src), new File(dest + File.separator
                            + srcFile.getName()));
                }
            } else {
                throw new RuntimeException("Source file[" + src
                        + "] does not exsit!");
            }
        } catch (Exception e) {
            logger.error("copy file error", e);
        }
    }

    public static void copyFileFromDirToDir(String srcDir, String dest,
                                            ArrayList<?> filter) throws Exception {
        if ((new File(srcDir).exists()) && (new File(srcDir).exists())) {
            File[] fileList = new File(srcDir).listFiles();
            if ((fileList != null) && (fileList.length > 0))
                for (int i = 0; i < fileList.length; i++)
                    if (fileList[i].isDirectory()) {
                        if (!filter.contains(fileList[i].getName()))
                            copyFiles(fileList[i].getCanonicalPath(), dest);
                        else
                            logger.warn(fileList[i].getName()
                                    + " contains in filter");
                    } else
                        cpFile(fileList[i], new File(dest + File.separator
                                + fileList[i].getName()));
        }
    }

    public static void copyFileFromDirToDir(String srcDir, String dest)
            throws Exception {
        if (new File(srcDir).exists()) {
            File[] fileList = new File(srcDir).listFiles();
            if ((fileList != null) && (fileList.length > 0))
                for (int i = 0; i < fileList.length; i++)
                    if (fileList[i].isDirectory())
                        copyFiles(fileList[i].getCanonicalPath(), dest);
                    else
                        cpFile(fileList[i], new File(dest + File.separator
                                + fileList[i].getName()));
        }
    }

    public static void cpFile(File srcFile, File destFile) {
        try {
            FileInputStream fis = new FileInputStream(srcFile);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] buf = new byte[1024];
            int j = 0;
            while ((j = fis.read(buf)) != -1) {
                fos.write(buf, 0, j);
            }
            fis.close();
            fos.close();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public static void createDirectory(String path) throws Exception {
        File currDirect = new File(path);
        if ((currDirect.getParentFile() != null)
                && (!currDirect.getParentFile().exists())) {
            createDirectory(currDirect.getParentFile().getCanonicalPath());
        }
        if (!currDirect.exists())
            currDirect.mkdir();
    }

    public static boolean isIncludeJarOrZipInRoot(String path) {
        boolean isInclude = false;
        try {
            File file = new File(path);
            if ((file.exists()) && (file.isDirectory())) {
                File[] fileList = file.listFiles();
                if ((fileList != null) && (fileList.length > 0))
                    for (int i = 0; i < fileList.length; i++) {
                        String name = fileList[i].getName();
                        if ((name.endsWith("jar")) || (name.endsWith("zip"))) {
                            isInclude = true;
                            break;
                        }
                    }
            }
        } catch (Throwable e) {
            logger.error(
                    "execute isIncludeJarOrZipInRoot failed! Set isInclude =false!",
                    e);
        }
        return isInclude;
    }

    public static void main(String[] args) {
        try {
            System.out
                    .println(isIncludeJarOrZipInRoot("D:\\Kingdee_600sp1\\eas\\server\\industry"));
        } catch (Exception localException) {
        }
    }
}
