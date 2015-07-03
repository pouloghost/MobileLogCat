package gt.utils.log.mobilelogcat.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import gt.utils.log.mobilelogcat.R;


/**
 * Created by ayi.zty on 2015/7/3.
 */
public class RangeSeekBarView extends FrameLayout {

    private SeekBar mLowView;
    private SeekBar mHighView;

    private int mLowValue;

    public void setListener(OnRangeChangedListener listener) {
        mListener = listener;
    }

    private OnRangeChangedListener mListener;


    public RangeSeekBarView(Context context) {
        super(context);
        initView(context);
    }

    public RangeSeekBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public RangeSeekBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_related_seekbar, this, true);
        mLowView = (SeekBar) findViewById(R.id.lowwer_bound);
        mHighView = (SeekBar) findViewById(R.id.higher_bound);
    }

    public void setRange(int low, int high) {
        mLowValue = low;
        final int range = high - low;
        mLowView.setMax(range);
        mHighView.setMax(range);
        mLowView.setProgress(0);
        mHighView.setProgress(range);
        mLowView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int high = mHighView.getProgress();
                if (progress > high) {
                    mHighView.setProgress(progress);
                }
                onRangeChanged();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mHighView.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int low = mLowView.getProgress();
                if (progress < low) {
                    mLowView.setProgress(progress);
                }
                onRangeChanged();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void onRangeChanged() {
        if (null != mListener) {
            mListener.onRangeChanged(mLowValue + mLowView.getProgress(), mLowValue + mHighView.getProgress());
        }
    }

    public interface OnRangeChangedListener {
        void onRangeChanged(int low, int high);
    }
}
