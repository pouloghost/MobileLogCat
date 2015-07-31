package gt.utils.log.mobilelogcat.common;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import gt.utils.log.mobilelogcat.component.RecordService;

/**
 * Created by ayi.zty on 2015/7/31.
 */
public class ServiceUtils {
    public static final long DELAY = 60 * 1000;

    public static void startRecordDelay(Context context, long delay) {
        //todo finish this method
    }

    public static void startRecordRepeating(Context context, long interval) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(RecordService.ACTION_RECORD);
        intent.setPackage(context.getPackageName());
        intent.setClassName(context, String.valueOf(RecordService.class));
        PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, interval, pendingIntent);
    }
}
