package gt.utils.log.mobilelogcat.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ghost on 2015/7/3.
 */
public class LogUtils {
    private static SimpleDateFormat sFORMAT = new SimpleDateFormat("hh:mm:ss:SSS");

    public static String getTimeString(long time) {
        return sFORMAT.format(new Date(time));
    }
}
