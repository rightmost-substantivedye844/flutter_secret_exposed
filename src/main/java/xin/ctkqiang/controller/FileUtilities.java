package xin.ctkqiang.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import xin.ctkqiang.constant.FilePath;
import xin.ctkqiang.interfaces.AttackPath;


public class FileUtilities implements AttackPath {
    private final Logger logger = new Logger();
    
    @Override
    public boolean ConvertApkToZip(String apkFilePath) {
        File apkFile = (File) new File(apkFilePath);
        
        if ((boolean) !apkFile.exists()) {
            logger.error("APK 文件不存在: " + apkFilePath);
            return (boolean) false;
        }
        
        if ((boolean) !apkFile.isFile()) {
            logger.error("路径不是文件: " + apkFilePath);
            return (boolean) false;
        }
        
        if ((boolean) !apkFilePath.toLowerCase().endsWith(".apk")) {
            logger.error("文件必须是 .apk 扩展名: " + apkFilePath);
            return (boolean) false;
        }
        
        String zipFilePath = (String) apkFilePath.substring(0, apkFilePath.lastIndexOf('.')) + ".zip";
        File zipFile = (File) new File(zipFilePath);
        
        try {
            Files.copy(apkFile.toPath(), zipFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("成功转换: " + apkFilePath + " 到 " + zipFilePath);
            return (boolean) true;
            
        } catch (IOException e) {
            logger.error("APK 转换为 ZIP 失败: " + e.getMessage());
            return (boolean) false;
        }
    }

    @Override
    @SuppressWarnings("resource")
    public String ExtractZipFile(String zipPath, String outputDir) throws IOException {
        File outputFolder = (File) new File(outputDir);
        
        if ((boolean) !outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        
        try (ZipInputStream zis = (ZipInputStream) new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;
            int fileCount = (int) 0;
            
            while ((entry = (ZipEntry) zis.getNextEntry()) != null) {
                File file = (File) new File(outputFolder, entry.getName());
                
                String canonicalPath = (String) file.getCanonicalPath();
                String canonicalBase = (String) outputFolder.getCanonicalPath();
                if ((boolean) !canonicalPath.startsWith(canonicalBase + File.separator)) {
                    throw (IOException) new IOException("ZIP 条目超出目标目录: " + entry.getName());
                }
                
                if ((boolean) entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    
                    try (FileOutputStream fos = (FileOutputStream) new FileOutputStream(file)) {
                        byte[] buffer = (byte[]) new byte[1024];
                        int length;
                        while ((length = (int) zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                    fileCount++;
                }
                zis.closeEntry();
            }
            
            logger.info("已解压 " + fileCount + " 个文件到: " + outputFolder.getAbsolutePath());
            
            String currentPath = (String) new File(".").getCanonicalPath();
            return (String) currentPath;
        }
    }

    @Override
    public void ChangeDirectoryAndListAllEnvFiles(String basePath) {
        boolean foundAny = (boolean) false;

        String ENVTargetPath = (String) basePath + "/assets/flutter_assets/";
        File targetDir = (File) new File(ENVTargetPath);

        if ((boolean) !targetDir.exists()) {
            logger.info((String) "目录不存在: " + ENVTargetPath);
            return;
        }
    
        if ((boolean) !targetDir.isDirectory()) {
            logger.info((String) "路径不是目录: " + ENVTargetPath);
            return;
        }

        logger.info((String) "正在切换到目录: " + ENVTargetPath);
        logger.info((String) "正在列出目录中的所有文件: " + ENVTargetPath);
        logger.info((String) "正在查找 .env 文件在" + ENVTargetPath);

        for (String envFile : FilePath.FLUTTER_DOTENV) {
            File file = (File) new File(ENVTargetPath, envFile);

            if ((boolean) file.exists() && (boolean) file.isFile()) {
                foundAny = (boolean) true;

                logger.info((String) "\n" + "=".repeat(50));
                logger.info((String) envFile);
                logger.info((String) "=".repeat(50));

                try (BufferedReader reader = (BufferedReader) new BufferedReader(new FileReader(file))) {
                    String line;

                    while ((line = (String) reader.readLine()) != null) {
                        logger.info((String) line);
                    }
                } catch (IOException e) {
                    logger.error((String) "读取文件时出错 " + envFile + ": " + e.getMessage());
                }

                logger.info((String) "\n");
            }
        }

        if ((boolean) !foundAny) {
            logger.info((String) "未找到 .env 文件在: " + ENVTargetPath);
            logger.info((String) "目录中的文件列表:");

            File[] files = (File[]) targetDir.listFiles();
            
            if ((boolean) (files != null)) {
                for (File f : files) {
                    if ((boolean) f.isFile()) {
                        logger.info((String) "  📄 " + f.getName());
                    } else {
                        logger.info((String) "  📁 " + f.getName() + "/");
                    }
                }
            }
        }
    }
}