package gt.utils.log.mobilelogcat.filter;

import android.util.Log;

import java.lang.reflect.Field;

import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/2.
 */
public class ContainsFilter extends AbsLogFilter {
    private String mKey;
    private String mValue;

    public ContainsFilter(String key, String value) {
        mKey = key;
        mValue = value;
    }

    @Override
    public boolean shouldShow(LogModel log) {
        try {
            Field field = LogModel.class.getField(mKey);
            String logValue = field.get(log).toString();
            return logValue.toLowerCase().contains(mValue.toLowerCase());
        } catch (NoSuchFieldException e) {
            Log.w("LogCatManger", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }
}
