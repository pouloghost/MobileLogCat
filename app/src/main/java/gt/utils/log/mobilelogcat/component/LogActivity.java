package gt.utils.log.mobilelogcat.component;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gt.utils.log.mobilelogcat.R;
import gt.utils.log.mobilelogcat.callback.AbsLogCallback;
import gt.utils.log.mobilelogcat.callback.DebugLogCallback;
import gt.utils.log.mobilelogcat.callback.ErrorLogCallback;
import gt.utils.log.mobilelogcat.common.Constants;
import gt.utils.log.mobilelogcat.common.LogCatManager;
import gt.utils.log.mobilelogcat.common.LogModel;
import gt.utils.log.mobilelogcat.filter.AbsLogFilter;

/**
 * Created by ghost on 2015/7/1.
 */
public class LogActivity extends Activity {
    private ListView mLogList;
    private Spinner mTypeSpinner;
    private FilterView mFilterView;
    private View mAnchor;

    private BaseLogAdapter mAdapter;

    private LogDetailView mDetailView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        initList();
        initSpinner();
        initFilter();
        initDelete();
    }

    private void initDelete() {
        Button delete = (Button) findViewById(R.id.log_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int i = mTypeSpinner.getSelectedItemPosition();
                LogCatManager.deleteLog(Constants.RUNNING_CALLBACKS.get(i).getFileName());
            }
        });
    }

    private void initFilter() {
        mFilterView = (FilterView) findViewById(R.id.log_filter_wrapper);
        mFilterView.setListener(new FilterView.OnNewFiltersListener() {
            public void onNewFilters(List<AbsLogFilter> filters) {
                if (null != mAdapter) {
                    mAdapter.onNewFilters(filters);
                }
            }
        });
        Button filterButton = (Button) findViewById(R.id.log_filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (View.GONE == mFilterView.getVisibility()) {
                    mFilterView.setVisibility(View.VISIBLE);
                } else {
                    mFilterView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initList() {
        mAnchor = findViewById(R.id.log_top_anchor);
        mLogList = (ListView) findViewById(R.id.log_list);
        mLogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PopupWindow popupWindow = new PopupWindow(getDetailView((LogModel) adapterView.getAdapter().getItem(i)), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setTouchable(true);
                popupWindow.setFocusable(true);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.abc_btn_borderless_material));
                popupWindow.showAsDropDown(mAnchor);
            }
        });
    }

    private void initSpinner() {
        List<Map<String, ?>> list = new ArrayList<Map<String, ?>>();
        final int end = Constants.RUNNING_CALLBACKS.size();
        for (int i = 0; i < end; i++) {
            Map<String, String> keyValuePair = new ArrayMap<String, String>();
            keyValuePair.put("name", Constants.RUNNING_CALLBACKS.get(i).getName());
            list.add(keyValuePair);
        }
        mTypeSpinner = (Spinner) findViewById(R.id.log_type_spinner);
        mTypeSpinner.setAdapter(new SimpleAdapter(this, list, android.R.layout.simple_spinner_dropdown_item, new String[]{"name"},
                new int[]{android.R.id.text1}));
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                List<LogModel> data = Constants.RUNNING_CALLBACKS.get(i).getFullData();
                if (data.size() > 0) {
                    mFilterView.setRange((int) data.get(0).timestamp, (int) data.get(data.size() - 1).timestamp);
                }
                mAdapter = new BaseLogAdapter(LogActivity.this, data);
                mLogList.setAdapter(mAdapter);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private LogDetailView getDetailView(LogModel log) {
        if (null == mDetailView) {
            mDetailView = new LogDetailView(this);
        }
        mDetailView.setDetail(log);
        return mDetailView;
    }
}