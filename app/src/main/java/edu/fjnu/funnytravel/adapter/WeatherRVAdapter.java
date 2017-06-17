package edu.sqchen.iubao.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.WeatherData;

/**
 * Created by Administrator on 2017/4/26.
 */

public class WeatherRVAdapter extends RecyclerView.Adapter<WeatherRVAdapter.ViewHolder> {

    @BindView(R.id.weather_icon)
    ImageView mWeatherIcon;

    @BindView(R.id.weather_tem)
    TextView mWeatherTem;

    @BindView(R.id.weather_date)
    TextView mWeatherDate;

    @BindView(R.id.weather_recommend)
    TextView mWeatherRecommend;

    @BindView(R.id.weather_carview)
    CardView mWeatherCarview;

    private List<WeatherData.ResultsBean.DailyBean> mWeatherList;

    public OnItemClickListener mOnItemClickListener;

    public WeatherRVAdapter(List<WeatherData.ResultsBean.DailyBean> mList) {
        this.mWeatherList = mList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mCardView = (CardView) itemView;
            this.mCardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_weather, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        ((TextView) holder.mCardView.findViewById(R.id.trip_attraction)).getPaint().setFakeBoldText(true);
        ((TextView) holder.mCardView.findViewById(R.id.weather_date)).setText(mWeatherList.get(position).getDate());
        ((TextView) holder.mCardView.findViewById(R.id.weather_tem)).setText(mWeatherList.get(position).getHigh());
//        mWeatherDate.setText(mWeatherList.get(position).getDate());
//        mWeatherTem.setText(mWeatherList.get(position).getHigh());
    }

    @Override
    public int getItemCount() {
        return mWeatherList.size();
    }


}
