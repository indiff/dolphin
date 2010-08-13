package com.tan.util;
import java.io.*;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
//import org.apache.tools.zip.ZipEntry;
//import org.apache.tools.zip.ZipOutputStream;

/**
* Java版的Zip工具
*
* @author leizhimin 2008-11-27 11:16:05
*/
public class ZipUtils {
        private static final int BUFF_SIZE = 1024 * 1024;     //1M Byte

        /**
         * 批量压缩文件（夹）
         *
         * @param resFileList 要压缩的文件（夹）列表
         * @param zipFile         生成的压缩文件
         * @throws IOException 当压缩过程出错时抛出
         */
        public static void zipFiles(Collection<File> resFileList, File zipFile) throws IOException {
                ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
                for (File resFile : resFileList) {
                        zipFile(resFile, zipout, "");
                }
                zipout.close();
        }

        /**
         * 批量压缩文件（夹）
         *
         * @param resFileList 要压缩的文件（夹）列表
         * @param zipFile         生成的压缩文件
         * @param comment         压缩文件的注释
         * @throws IOException 当压缩过程出错时抛出
         */
        public static void zipFiles(Collection<File> resFileList, File zipFile, String comment) throws IOException {
                ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), BUFF_SIZE));
                for (File resFile : resFileList) {
                        zipFile(resFile, zipout, "");
                }
                zipout.setComment(comment);
                zipout.close();
        }

        /**
         * 解压缩一个文件
         *
         * @param zipFile        压缩文件
         * @param folderPath 解压缩的目标目录
         * @throws IOException 当压缩过程出错时抛出
         */
        public static void upZipFile(File zipFile, String folderPath) throws IOException {
                ZipFile zf = new ZipFile(zipFile);
                for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                        ZipEntry entry = ((ZipEntry) entries.nextElement());
                        InputStream in = zf.getInputStream(entry);
                        OutputStream out = new FileOutputStream(folderPath + File.separator + entry.getName());
                        byte buffer[] = new byte[BUFF_SIZE];
                        int realLength;
                        while ((realLength = in.read(buffer)) > 0) {
                                out.write(buffer, 0, realLength);
                        }
                        in.close();
                        out.close();
                }
        }

        private static void zipFile(File resFile, ZipOutputStream zipout, String rootpath) throws IOException {
                rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
                if (resFile.isDirectory()) {
                        File[] fileList = resFile.listFiles();
                        for (File file : fileList) {
                                zipFile(file, zipout, rootpath);
                        }
                } else {
                        byte buffer[] = new byte[BUFF_SIZE];
                        BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), BUFF_SIZE);
                        zipout.putNextEntry(new ZipEntry(rootpath));
                        int realLength;
                        while ((realLength = in.read(buffer)) != -1) {
                                zipout.write(buffer, 0, realLength);
                        }
                        in.close();
                        zipout.flush();
                        zipout.closeEntry();
                }
        }
}