package gt.utils.log.mobilelogcat.common;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import gt.utils.log.mobilelogcat.callback.AbsLogCallback;

/**
 * Created by ghost on 2015/7/1.
 */
public class LogCatManager {
    private static LogCatManager sINSTANCE;

    public static LogCatManager getInstance() {
        if (null == sINSTANCE) {
            synchronized (LogCatManager.class) {
                if (null == sINSTANCE) {
                    sINSTANCE = new LogCatManager();
                }
            }
        }
        return sINSTANCE;
    }

    private List<AbsLogCallback> mCallbacks;
    private ArrayMap<String, OutputStreamWriter> mLogWriters;
    private long mLastLogTime;

    private LogCatManager() {
        mCallbacks = new ArrayList<AbsLogCallback>();
        mLogWriters = new ArrayMap<String, OutputStreamWriter>();
        mLastLogTime = -1;
    }

    public void registerCallback(AbsLogCallback callback) {
        mCallbacks.add(callback);
        callback.onRegistered();
    }

    public void unregisterCallback(AbsLogCallback callback) {
        mCallbacks.remove(callback);
        callback.onUnregistered();
    }

    public void fetchLog() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d -v time");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            LogModel model = null;
            while ((line = bufferedReader.readLine()) != null) {
                model = LogFactory.onNewLine(line);
                if (null != model && model.timestamp > mLastLogTime) {
                    onNewLog(model);
                    mLastLogTime = model.timestamp;
                    model = null;
                }
            }
        } catch (IOException e) {
            Log.w("LogCatManager", e);
        } finally {
            for (String key : mLogWriters.keySet()) {
                try {
                    OutputStreamWriter writer = mLogWriters.get(key);
                    if (null != writer) {
                        writer.flush();
                        writer.close();
                    }
                } catch (Exception e) {
                    Log.w("LogCatManager", e);
                }
            }
            mLogWriters.clear();
        }
    }

    private void onNewLog(LogModel log) {
        String path = null;
        for (AbsLogCallback callback : mCallbacks) {
            path = callback.shouldWriteToFile(log);
            if (null != path) {
                OutputStreamWriter writer = getWriter(path);
                try {
                    writer.append(callback.getLogString(log));
                } catch (IOException e) {
                    Log.w("LogCatManager", e);
                }
            }
        }
    }

    public static void deleteLog(String path) {
        File file = new File(Constants.PATH, path);
        if (file.exists()) {
            file.delete();
        }
    }

    private OutputStreamWriter getWriter(String path) {
        OutputStreamWriter writer = mLogWriters.get(path);
        if (null == writer) {
            File dir = new File(Constants.PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try {
                writer = new OutputStreamWriter(new FileOutputStream(LogFileUtils.getLogFile(dir, path), true));
            } catch (FileNotFoundException e) {
                Log.w("LogCatManager", e);
            }
            mLogWriters.put(path, writer);
        }
        return writer;
    }

    private void deleteExpiredFiles() {
        if (Constants.CHECK_EXPIRE_ON_WRITE) {
            File dir = new File(Constants.PATH);
            if (dir.exists()) {
                for (AbsLogCallback callback : Constants.RUNNING_CALLBACKS) {
                    if (LogFileUtils.checkAndDeleteExpireFile(dir, callback.getFileName())) {
                        mLogWriters.remove(callback.getFileName());
                    }
                }
            }
        }
    }
}
