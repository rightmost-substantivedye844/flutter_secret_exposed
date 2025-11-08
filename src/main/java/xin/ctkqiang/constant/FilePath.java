package xin.ctkqiang.constant;

public class FilePath {
    public static final String[] FILE_TYPES = {
        "env",
        "gradle",
        "kts",
        "xml",
        "android_manifest"
    };

    public static final String[] FILE_PATHS = {
        "src/main/resources/env",
        "build.gradle",
        "build.gradle.kts",
        "src/main/res/values/strings.xml",
        "src/main/AndroidManifest.xml"
    };

    public static final String[] FLUTTER_DOTENV = {
        ".env",
        ".env.dev",
        ".env.develop",
        ".env.development",
        ".env.test",
        ".env.testing",
        ".env.prod", 
        ".env.production",
        ".env.stag",
        ".env.staging",
        ".env.release",
        ".env.example",
    }; 
}
