package gt.utils.log.mobilelogcat.component;

import android.app.IntentService;
import android.content.Intent;

import gt.utils.log.mobilelogcat.common.LogCatManager;
import gt.utils.log.mobilelogcat.common.ServiceUtils;

public class RecordService extends IntentService {
    public static final String ACTION_RECORD = "gt.utils.log.mobilelogcat.component.action.RECORD";

    public RecordService() {
        super("RecordService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_RECORD.equals(action)) {
                LogCatManager.getInstance().fetchLog();
            }
        }
    }
}
