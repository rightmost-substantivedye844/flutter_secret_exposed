package xin.ctkqiang.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApkTool {
    private static final Logger logger = (Logger) new Logger();
    public static void decodeApk(String apkPath, String outputDir) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = (ProcessBuilder) new ProcessBuilder(
                "java", 
                "-jar", 
                "libs/apktool.jar", 
                "d", 
                apkPath, 
                "-o", 
                outputDir, 
                "-f"
        );
        processBuilder.redirectErrorStream((boolean) true);

        Process process = (Process) processBuilder.start();

        try (BufferedReader reader = (BufferedReader) new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;

            while ((line = (String) reader.readLine()) != null) {
                logger.info((String) line);
            }
        }

        int exitCode = (int) process.waitFor();
        logger.info((String) "ApkTool 进程退出码: " + exitCode);
    }
    
}
