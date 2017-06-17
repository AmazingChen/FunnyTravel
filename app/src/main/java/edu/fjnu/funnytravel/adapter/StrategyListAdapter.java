package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Strategy;

/**
 * Created by Administrator on 2017/5/3.
 */

public class StrategyListAdapter extends ArrayAdapter {

    private Context mContext;

    private List<Strategy> mStrategyList;

    public StrategyListAdapter(Context context, List<Strategy> strategyList) {
        super(context, 0, strategyList);
        this.mContext = context;
        this.mStrategyList = strategyList;
    }

    @Override
    public int getCount() {
        return this.mStrategyList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(holder == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_strategy, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.strategy_img)
        ImageView mStrategyImg;

        @BindView(R.id.strategy_title)
        TextView mStrategyTitle;

        @BindView(R.id.strategy_publish_time)
        TextView mStrategyPublishTime;

        @BindView(R.id.strategy_author)
        TextView mStrategyAuthor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
