package gt.utils.log.mobilelogcat.common;

import android.graphics.Color;
import android.support.v4.util.ArrayMap;

/**
 * Created by ayi.zty on 2015/7/2.
 */
public class Constants {
    public static final String PATH = "/sdcard/afwealth/logs";
    public static final ArrayMap<String, Integer> LEVEL_COLORS = new ArrayMap<String, Integer>();

    static {
        LEVEL_COLORS.put(LogLevel.S.toString(), Color.parseColor("#303030"));
        LEVEL_COLORS.put(LogLevel.F.toString(), Color.parseColor("#FF00FF"));
        LEVEL_COLORS.put(LogLevel.E.toString(), Color.parseColor("#FF0000"));
        LEVEL_COLORS.put(LogLevel.W.toString(), Color.parseColor("#FFC125"));
        LEVEL_COLORS.put(LogLevel.I.toString(), Color.parseColor("#B452CD"));
        LEVEL_COLORS.put(LogLevel.D.toString(), Color.parseColor("#6495ED"));
        LEVEL_COLORS.put(LogLevel.V.toString(), Color.parseColor("#000000"));
    }

    public enum LogLevel {
        S, F, E, W, I, D, V
    }

}
