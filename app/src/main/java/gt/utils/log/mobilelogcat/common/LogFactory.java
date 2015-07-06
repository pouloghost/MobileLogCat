package gt.utils.log.mobilelogcat.common;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ghost on 2015/7/3.
 */
public class LogFactory {
    private static final Pattern sPATTERN = Pattern.compile("([0-9\\-:\\.\\s]+?)\\s*([VDIWEFS]{1})\\s*/\\s*([^\\(]*)\\(\\s*([^\\)]*)\\s*\\)\\s*:\\s*(.*)", Pattern.DOTALL);
    private static final SimpleDateFormat sFORMAT = new SimpleDateFormat("MM-dd hh:mm:ss.SSS");
    private static final int sPARTS = 5;

    private static LogModel sLastLog;

    public static LogModel onNewLine(String log) {
        Matcher matcher = sPATTERN.matcher(log);
        LogModel model = null;
        boolean shouldReturn = true;
        if (matcher.find()) {
            if (sPARTS == matcher.groupCount()) {
                model = new LogModel();
                try {
                    model.timestamp = sFORMAT.parse(matcher.group(1)).getTime();
                    model.logLevel = matcher.group(2);
                    model.tag = matcher.group(3);
                    model.process = Integer.parseInt(matcher.group(4));
                    model.content = matcher.group(5);
                    if (null != sLastLog && model.timestamp == sLastLog.timestamp && !sLastLog.content.equals(matcher.groupCount())) {
                        sLastLog.content = sLastLog.content + "\n" + model.content;
                        model = null;
                    }
                } catch (Exception e) {
                    Log.e("LogCatManager", e.getMessage());
                }
            }
        } else {
            if (!log.startsWith("----") && null != sLastLog) {
                sLastLog.content += log;
            }
        }
        if (null != model) {
            LogModel tmp = sLastLog;
            sLastLog = model;
            return tmp;
        }
        return null;
    }
}
