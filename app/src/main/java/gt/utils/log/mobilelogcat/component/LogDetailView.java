package gt.utils.log.mobilelogcat.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import gt.utils.log.mobilelogcat.R;
import gt.utils.log.mobilelogcat.common.LogModel;

/**
 * Created by ghost on 2015/7/2.
 */
public class LogDetailView extends FrameLayout {
    private TextView mTag;
    private TextView mLevel;
    private TextView mTime;
    private TextView mProcess;
    private TextView mContent;

    private SimpleDateFormat mFormat = new SimpleDateFormat("dd-hh:mm:ss:SSS");

    public LogDetailView(Context context) {
        super(context);
        initView(context);
    }

    public LogDetailView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LogDetailView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    public void setDetail(LogModel log) {
        mTag.setText(log.tag);
        mLevel.setText(log.logLevel);
        mTime.setText(mFormat.format(new Date(log.timestamp)));
        mProcess.setText(log.process + "");
        mContent.setText(log.content);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_log_detail, this, true);
        mTag = (TextView) findViewById(R.id.log_detail_tag);
        mLevel = (TextView) findViewById(R.id.log_detail_level);
        mTime = (TextView) findViewById(R.id.log_detail_time);
        mProcess = (TextView) findViewById(R.id.log_detail_process);
        mContent = (TextView) findViewById(R.id.log_detail_content);
    }

}
