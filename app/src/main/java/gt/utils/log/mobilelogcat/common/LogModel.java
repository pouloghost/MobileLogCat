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
    private static final Pattern sPATTERN = Pattern.compile("([0-9\\-:\\.\\s]+?)\\s*([VDIWEFS]{1})\\s*/\\s*([^\\(]*)\\(\\s*([^\\)]*)\\s*\\)\\s*:\\s*(.*)", Pattern.DOTALL);
    private static final SimpleDateFormat sFORMAT = new SimpleDateFormat("MM-dd hh:mm:ss.SSS");
    private static final int sPARTS = 5;
    private boolean mLegal = false;
    public long timestamp;
    public String logLevel;
    public String tag;
    public int process;
    public String content;

    public LogModel() {
    }

    public LogModel(String log) {
        Matcher matcher = sPATTERN.matcher(log);
        if (matcher.find()) {
            if (sPARTS == matcher.groupCount()) {
                try {
                    timestamp = sFORMAT.parse(matcher.group(1)).getTime();
                    logLevel = matcher.group(2);
                    tag = matcher.group(3);
                    process = Integer.parseInt(matcher.group(4));
                    content = matcher.group(5);
                    mLegal = true;
                } catch (Exception e) {
                }
            }
        }
    }

    public boolean isLegal() {
        return mLegal;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this) + "\n";
    }

    public boolean isFiltered(List<AbsLogFilter> filters) {
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
