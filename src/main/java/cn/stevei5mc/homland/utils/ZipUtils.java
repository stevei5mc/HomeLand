package cn.stevei5mc.homland.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtils {

    /**
     * 将 ZIP 文件解压到目标目录
     *
     * @param zipFilePath   要解压的 ZIP 文件路径
     * @param destDirectory 解压后存放文件的目标目录路径
     * @throws IOException 如果发生 I/O 错误
     */
    public static void decompress(String zipFilePath, String destDirectory) throws IOException {
        File destDir = new File(destDirectory);

        if (!destDir.exists()) {
            destDir.mkdir();
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                String fileName = entry.getName();
                File newFile = new File(destDirectory, fileName);

                // 确保父目录存在，防止路径遍历漏洞
                String parentPath = newFile.getParent();
                if (parentPath != null) {
                    File parentDir = new File(parentPath);
                    if (!parentDir.exists() && !parentDir.mkdirs()) {
                        throw new IOException("无法创建父目录: " + parentDir.getAbsolutePath());
                    }
                }

                if (entry.isDirectory()) {
                    newFile.mkdir();
                } else {
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        byte[] buffer = new byte[8192];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    /**
     * 将源文件或目录压缩到目标 ZIP 文件（不包含根文件夹本身）
     *
     * @param sourceFilePath 要压缩的源文件或目录的路径
     * @param zipFilePath    目标 ZIP 文件的路径（不需要加 .zip 后缀）
     * @throws IOException 如果发生 I/O 错误
     */
    public static void compress(String sourceFilePath, String zipFilePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zipFilePath + ".zip");
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            File sourceFile = new File(sourceFilePath);

            if (sourceFile.isDirectory()) {
                // 如果是目录，遍历其子文件/子目录，entryName 直接使用相对路径（不包含根目录名）
                for (File child : sourceFile.listFiles()) {
                    addFileToZip(zos, child, child.getName());
                }
            } else {
                // 如果是单个文件，直接添加
                addFileToZip(zos, sourceFile, sourceFile.getName());
            }
        }
    }

    /**
     * 递归地将文件或目录添加到 ZIP 输出流中
     *
     * @param zos        ZIP 输出流
     * @param file       当前要添加的文件或目录
     * @param entryName  在 ZIP 文件中的条目名称
     * @throws IOException 如果发生 I/O 错误
     */
    private static void addFileToZip(ZipOutputStream zos, File file, String entryName) throws IOException {
        if (file.isDirectory()) {
            for (File childFile : file.listFiles()) {
                String childEntryName = entryName + "/" + childFile.getName();
                addFileToZip(zos, childFile, childEntryName);
            }
        } else {
            try (FileInputStream fis = new FileInputStream(file)) {
                ZipEntry zipEntry = new ZipEntry(entryName);
                zos.putNextEntry(zipEntry);

                byte[] buffer = new byte[8192];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }
                zos.closeEntry();
            }
        }
    }
}