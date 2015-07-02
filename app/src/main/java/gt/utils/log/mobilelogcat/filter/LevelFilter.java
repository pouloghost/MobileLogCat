package gt.utils.log.mobilelogcat.filter;

import gt.utils.log.mobilelogcat.common.Constants;
import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/2.
 */
public class LevelFilter extends AbsLogFilter {
    private Constants.LogLevel mLevel;

    public LevelFilter(String level) {
        mLevel = Constants.LogLevel.valueOf(level);
    }

    @Override
    public boolean shouldShow(LogModel log) {
        Constants.LogLevel level = Constants.LogLevel.valueOf(log.logLevel);
        return level.compareTo(mLevel) < 0;
    }
}
