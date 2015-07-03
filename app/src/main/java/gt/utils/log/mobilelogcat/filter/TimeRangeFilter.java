package gt.utils.log.mobilelogcat.filter;

import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/3.
 */
public class TimeRangeFilter extends AbsLogFilter {
    private long mStart;
    private long mEnd;

    public TimeRangeFilter(long start, long end) {
        mStart = start;
        mEnd = end;
    }

    @Override
    public boolean shouldShow(LogModel log) {
        return log.timestamp >= mStart && log.timestamp <= mEnd;
    }
}
