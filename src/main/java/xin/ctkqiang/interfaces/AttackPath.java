package xin.ctkqiang.interfaces;

import java.io.IOException;

public interface AttackPath {
    String ExtractZipFile(String zipPath, String outputDir) throws IOException;
    boolean ConvertApkToZip(String apkFilePath);

    void ChangeDirectoryAndListAllEnvFiles(String basePath);
}
