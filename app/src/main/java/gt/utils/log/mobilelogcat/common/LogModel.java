package gt.utils.log.mobilelogcat.common;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gt.utils.log.mobilelogcat.filter.AbsLogFilter;

/**
 * Created by ghost on 2015/7/1.
 */
public class LogModel implements Serializable {
    public long timestamp;
    public String logLevel;
    public String tag;
    public int process;
    public String content;

    public LogModel() {
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this) + "\n";
    }

    public boolean isFiltered(List<AbsLogFilter> filters) {
        if (null == filters) {
            return true;
        }
        boolean result = true;
        for (AbsLogFilter filter : filters) {
            result &= filter.shouldShow(this);
            if (!result) {
                break;
            }
        }
        return result;
    }
}
