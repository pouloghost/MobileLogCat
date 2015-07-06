package gt.utils.log.mobilelogcat.common;

import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * Created by ayi.zty on 2015/7/3.
 */
public class LogFileUtils {
    public static void deleteLog(String path) {
        File dir = new File(Constants.PATH);
        if (dir.exists()) {
            File file = getLogFile(dir, path);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    public static File getLogFile(File dir, String path) {
        File file = null;
        for (String name : dir.list()) {
            String[] parts = name.split(Constants.SEPARATOR);
            if (parts[0].equals(path)) {
                file = new File(dir, name);
                break;
            }
        }
        if (null == file) {
            file = new File(dir, path + Constants.SEPARATOR + System.currentTimeMillis());
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    Log.w("LogCatManager", e);
                }
            }
        }
        return file;
    }

    public static boolean checkAndDeleteExpireFile(File dir, String path) {
        File file = getLogFile(dir, path);
        String[] parts = file.getName().split(Constants.SEPARATOR);
        try {
            if (2 == parts.length && System.currentTimeMillis() - Long.parseLong(parts[1]) > Constants.EXPIRE_TIME) {
                file.delete();
                return true;
            }
        } catch (Exception e) {
            Log.w("LogCatManager", e);
        }
        return false;
    }
}
