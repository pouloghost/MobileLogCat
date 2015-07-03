package gt.utils.log.mobilelogcat.callback;


import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/3.
 */
public class AllLogCallback extends AbsLogCallback {
    private String name = "all";

    @Override
    public long initUniqueId() {
        return 2;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public String shouldWriteToFile(LogModel log) {
        return name;
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
        return "All";
    }
}
