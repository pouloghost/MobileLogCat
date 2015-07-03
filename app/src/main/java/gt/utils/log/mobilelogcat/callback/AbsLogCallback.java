package gt.utils.log.mobilelogcat.callback;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import gt.utils.log.mobilelogcat.common.Constants;
import gt.utils.log.mobilelogcat.common.LogCatManager;
import gt.utils.log.mobilelogcat.common.LogFileUtils;
import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/1.
 */
public abstract class AbsLogCallback {
    private long uniqueId = initUniqueId();

    public abstract long initUniqueId();

    public abstract void onRegistered();

    public abstract void onUnregistered();

    //null for not write to file, return file name of the log file
    public abstract String shouldWriteToFile(LogModel log);

    public abstract String getLogString(LogModel log);

    public abstract String getFileName();

    public List<LogModel> getFullData() {
        List<LogModel> data = null;
        BufferedReader reader = null;
        try {
            File dir = new File(Constants.PATH);
            File file = LogFileUtils.getLogFile(dir, getFileName());
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            data = new ArrayList<LogModel>();
            String line;
            while (null != (line = reader.readLine())) {
                data.add(JSON.parseObject(line, LogModel.class));
            }
        } catch (Exception e) {
            Log.w("LogCatManager", e);
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.w("LogCatManager", e);
                }
            }
        }
        return data;
    }

    public abstract String getName();

    @Override
    public boolean equals(Object o) {
        return uniqueId == ((AbsLogCallback) o).uniqueId;
    }
}
