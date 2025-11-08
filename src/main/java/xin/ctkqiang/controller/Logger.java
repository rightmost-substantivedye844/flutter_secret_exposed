package xin.ctkqiang.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static  String APP_NAME = (String) "Flutter 秘密暴露";
    private static final DateTimeFormatter FORMATTER = (DateTimeFormatter) DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static String CURRENT_USER = (String) System.getProperty("user.name", "未知用户");

    public static void setCurrentUser(String user) {
        CURRENT_USER = user;
    }

    public static void setAppName(String appName) {
        APP_NAME = appName;
    }

    public void info(String message) {
        System.out.println(String.format("%s | %s [info] | %s | %s", CURRENT_USER, APP_NAME, LocalDateTime.now().format(FORMATTER), message));
    }

   public void warn(String message) {
        String redColor = (String) "\u001B[91m";
        String resetColor = (String) "\u001B[0m";
        System.out.println(redColor + String.format("%s | %s [warn] | %s | %s", CURRENT_USER, APP_NAME, LocalDateTime.now().format(FORMATTER), message) + resetColor);
    }
    public void error(String message) {
        System.err.println(String.format("%s | %s [error] | %s | %s", CURRENT_USER, APP_NAME, LocalDateTime.now().format(FORMATTER), message));
    }
}
