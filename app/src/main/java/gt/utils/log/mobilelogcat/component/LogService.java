package gt.utils.log.mobilelogcat.component;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.io.File;

import gt.utils.log.mobilelogcat.callback.AbsLogCallback;
import gt.utils.log.mobilelogcat.common.Constants;
import gt.utils.log.mobilelogcat.common.LogCatManager;
import gt.utils.log.mobilelogcat.common.LogFileUtils;

/**
 * Created by ghost on 2015/7/1.
 */
public class LogService extends Service {
    private Handler mHandler;
    private Runnable mRunnable = new Runnable() {
        public void run() {
            LogCatManager.getInstance().fetchLog();
            mHandler.postDelayed(this, Constants.LOOP_TIME);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler(getMainLooper());
        for (AbsLogCallback callback : Constants.RUNNING_CALLBACKS) {
            LogCatManager.getInstance().registerCallback(callback);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mHandler.post(mRunnable);
        deleteExpiredFiles();
        return super.onStartCommand(intent, flags, startId);
    }

    private void deleteExpiredFiles() {
        if (Constants.CHECK_EXPIRE_ON_START) {
            File dir = new File(Constants.PATH);
            if (dir.exists()) {
                for (AbsLogCallback callback : Constants.RUNNING_CALLBACKS) {
                    LogFileUtils.checkAndDeleteExpireFile(dir, callback.getFileName());
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mHandler.removeCallbacks(mRunnable);
        super.onDestroy();
    }

}
