package gt.utils.log.mobilelogcat.common;

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

    private static LogModel mCurrentLog;

    public static LogModel onNewLine(String log) {
        Matcher matcher = sPATTERN.matcher(log);
        LogModel model = null;
        if (matcher.find()) {
            if (sPARTS == matcher.groupCount()) {
                model = mCurrentLog;
                mCurrentLog = new LogModel();
                try {
                    mCurrentLog.timestamp = sFORMAT.parse(matcher.group(1)).getTime();
                    mCurrentLog.logLevel = matcher.group(2);
                    mCurrentLog.tag = matcher.group(3);
                    mCurrentLog.process = Integer.parseInt(matcher.group(4));
                    mCurrentLog.content = matcher.group(5);
                } catch (Exception e) {
                }
            }
        } else {
            if (null != mCurrentLog) {
                mCurrentLog.content += log;
            }
        }
        return model;
    }
}
