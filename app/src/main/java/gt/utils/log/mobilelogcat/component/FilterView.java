package gt.utils.log.mobilelogcat.component;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import gt.utils.log.mobilelogcat.R;
import gt.utils.log.mobilelogcat.filter.AbsLogFilter;
import gt.utils.log.mobilelogcat.filter.ContainsFilter;
import gt.utils.log.mobilelogcat.filter.LevelFilter;


/**
 * Created by ghost on 2015/7/2.
 */
public class FilterView extends FrameLayout {
    private Spinner mLevelSpinner;
    private EditText mTagEditText;
    private EditText mContentEditText;
    private Button mOkButton;

    private LevelFilter mLevelFilter;

    private OnNewFiltersListener mListener;

    public FilterView(Context context) {
        super(context);
        initView(context);
    }

    public FilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public FilterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_log_filter, this, true);
        initLevelFilter();
        mTagEditText = (EditText) findViewById(R.id.log_filter_tag);
        mContentEditText = (EditText) findViewById(R.id.log_filter_content);
        initOk();
    }

    private void initOk() {
        mOkButton = (Button) findViewById(R.id.log_filter_ok);
        mOkButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (null == mListener) {
                    return;
                }
                List<AbsLogFilter> filters = new ArrayList<AbsLogFilter>(3);
                String tag = mTagEditText.getText().toString();
                if (!TextUtils.isEmpty(tag)) {
                    filters.add(new ContainsFilter("tag", tag));
                }

                String content = mContentEditText.getText().toString();
                if (!TextUtils.isEmpty(tag)) {
                    filters.add(new ContainsFilter("content", content));
                }

                if (null != mLevelFilter) {
                    filters.add(mLevelFilter);
                }

                mListener.onNewFilters(filters);
            }
        });
    }

    private void initLevelFilter() {
        mLevelSpinner = (Spinner) findViewById(R.id.log_filter_level);
        final String[] levels = new String[]{"S", "F", "E", "W", "I", "D", "V"};
        mLevelSpinner.setAdapter(new ArrayAdapter<String>(this.getContext(), android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, levels));
        mLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mLevelFilter = new LevelFilter(levels[i]);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
                mLevelFilter = null;
            }
        });
    }

    public void setListener(OnNewFiltersListener listener) {
        mListener = listener;
    }

    public interface OnNewFiltersListener {
        void onNewFilters(List<AbsLogFilter> filters);
    }
}
