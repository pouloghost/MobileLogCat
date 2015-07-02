package gt.utils.log.mobilelogcat.filter;

import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/2.
 */
public abstract class AbsLogFilter {
    abstract public boolean shouldShow(LogModel log);
}
