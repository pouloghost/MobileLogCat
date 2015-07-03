package gt.utils.log.mobilelogcat.common;

import android.graphics.Color;
import android.support.v4.util.ArrayMap;

import java.util.ArrayList;

import gt.utils.log.mobilelogcat.callback.AbsLogCallback;
import gt.utils.log.mobilelogcat.callback.AllLogCallback;

/**
 * Created by ghost on 2015/7/2.
 */
public class Constants {
    public static final String PATH = "/sdcard/logs";
    public static final ArrayMap<String, Integer> LEVEL_COLORS = new ArrayMap<String, Integer>();
    public static final ArrayList<AbsLogCallback> RUNNING_CALLBACKS = new ArrayList<AbsLogCallback>();
    public static final long LOOP_TIME = 1000 * 6;
    public static final String SEPARATOR = "-";
    public static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;
    public static boolean CHECK_EXPIRE_ON_WRITE = false;
    public static boolean CHECK_EXPIRE_ON_START = true;

    static {
        LEVEL_COLORS.put(LogLevel.S.toString(), Color.parseColor("#303030"));
        LEVEL_COLORS.put(LogLevel.F.toString(), Color.parseColor("#FF00FF"));
        LEVEL_COLORS.put(LogLevel.E.toString(), Color.parseColor("#FF0000"));
        LEVEL_COLORS.put(LogLevel.W.toString(), Color.parseColor("#FFC125"));
        LEVEL_COLORS.put(LogLevel.I.toString(), Color.parseColor("#B452CD"));
        LEVEL_COLORS.put(LogLevel.D.toString(), Color.parseColor("#6495ED"));
        LEVEL_COLORS.put(LogLevel.V.toString(), Color.parseColor("#000000"));

        RUNNING_CALLBACKS.add(new AllLogCallback());
    }

    public enum LogLevel {
        S, F, E, W, I, D, V
    }

}
