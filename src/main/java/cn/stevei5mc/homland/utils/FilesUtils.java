package cn.stevei5mc.homland.utils;

import cn.stevei5mc.homland.HomeLandMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;

public class FilesUtils {

    private static final HomeLandMain main = HomeLandMain.getInstance();

    public static void createDirectory(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                main.getLogger().info("目录创建成功: " + path.toAbsolutePath());
            } else if (!Files.isDirectory(path)) {
                main.getLogger().warning("路径已存在，但它不是一个目录，而是一个文件: " + path.toAbsolutePath());
            }
        } catch (IOException e) {
            main.getLogger().error("创建目录时发生错误: " + e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public static void deleteDirectory(Path directory) throws IOException {
        if (!Files.exists(directory)) {
            return;
        }

        try (Stream<Path> walk = Files.walk(directory)) {
            walk.sorted(Comparator.reverseOrder()).forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException e) {
                    main.getLogger().error("无法删除目录: " + path + ", 原因: " + e.getMessage(), e);
                }
            });
        }
    }

    public static boolean deleteFile(String filePath) {
        Path pathToFile = Paths.get(filePath);
        try {
            return Files.deleteIfExists(pathToFile);
        } catch (IOException e) {
            main.getLogger().error("删除文件时发生错误: " + e.getMessage());
            return false;
        }
    }
}