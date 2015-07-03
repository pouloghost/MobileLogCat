package gt.utils.log.mobilelogcat.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gt.utils.log.mobilelogcat.R;
import gt.utils.log.mobilelogcat.common.Constants;
import gt.utils.log.mobilelogcat.common.LogModel;
import gt.utils.log.mobilelogcat.common.LogUtils;
import gt.utils.log.mobilelogcat.filter.AbsLogFilter;

/**
 * Created by ghost on 2015/7/1.
 */
public class BaseLogAdapter extends BaseAdapter implements FilterView.OnNewFiltersListener {
    private Context mContext;
    private List<LogModel> mData;
    private List<LogModel> mFullData;

    public BaseLogAdapter(Context context, List<LogModel> data) {
        mContext = context;
        mFullData = data;
        if (null != data) {
            mData = new ArrayList<LogModel>(data);
        }
    }

    public int getCount() {
        return null == mData ? 0 : mData.size();
    }

    public Object getItem(int i) {
        return mData.get(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_base_log, null);
            holder = new Holder();
            holder.onCreate(convertView);
            convertView.setTag(holder);
        }

        holder = (Holder) convertView.getTag();
        if (null != holder) {
            holder.resetViews();
            holder.onBind((LogModel) getItem(position));
        }
        return convertView;
    }

    public void onNewFilters(List<AbsLogFilter> filters) {
        mData.clear();
        for (LogModel model : mFullData) {
            if (model.isFiltered(filters)) {
                mData.add(model);
            }
        }
        notifyDataSetChanged();
    }

    private static class Holder {
        public TextView tag;
        public TextView time;
        public TextView abstraction;

        public void onCreate(View view) {
            tag = (TextView) view.findViewById(R.id.log_tag);
            time = (TextView) view.findViewById(R.id.log_time);
            abstraction = (TextView) view.findViewById(R.id.log_abstract);
        }

        public void onBind(LogModel model) {
            tag.setText(model.tag);
            time.setText(LogUtils.getTimeString(model.timestamp));
            abstraction.setText(model.content);
            Integer color;
            if (null != (color = Constants.LEVEL_COLORS.get(model.logLevel))) {
                tag.setTextColor(color);
            }
        }

        public void resetViews() {
            if (null == tag) {
                return;
            }
            tag.setTextColor(Color.BLACK);
            abstraction.setSingleLine(true);
        }
    }
}
