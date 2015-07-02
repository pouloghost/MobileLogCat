package gt.utils.log.mobilelogcat.callback;

import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/1.
 */
public class DebugLogCallback extends AbsLogCallback {
    private String name = "debug";

    @Override
    public long initUniqueId() {
        return 0;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public String shouldWriteToFile(LogModel log) {
        if (log.logLevel.equals("D")) {
            return name;
        }
        return null;
    }

    @Override
    public String getLogString(LogModel log) {
        return log.toString();
    }

    @Override
    public String getFileName() {
        return name;
    }

    @Override
    public String getName() {
        return "Debug";
    }
}
