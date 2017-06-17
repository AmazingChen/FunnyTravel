package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.AttractionDetail;

/**
 * Created by Administrator on 2017/5/4.
 */

public class DescribeListAdapter extends ArrayAdapter {

    private Context mContext;

    private List<AttractionDetail.ResultBean> mDetailList;

    public DescribeListAdapter(@NonNull Context context, List<AttractionDetail.ResultBean> mDetailList) {
        super(context, 0, mDetailList);
        this.mContext = context;
        this.mDetailList = mDetailList;
    }

    @Override
    public int getCount() {
        return mDetailList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if(holder == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_describe, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if(mDetailList != null || mDetailList.size() > 0) {
            //如果标题为空，则不隐藏控件
            if(mDetailList.get(position).getTitle().equals("") || mDetailList.get(position).getTitle().isEmpty()) {
                holder.mDetailTitle.setVisibility(View.GONE);
            } else {
                holder.mDetailTitle.setText("『" + mDetailList.get(position).getTitle() + "』");
            }
            //如果没有图片地址，则隐藏控件
            if(mDetailList.get(position).getImg().isEmpty() || mDetailList.get(position).getImg().equals("")) {
                holder.mDetailImg.setVisibility(View.GONE);
            } else {
                Glide.with(mContext).load(mDetailList.get(position).getImg()).into(holder.mDetailImg);
            }
            if(mDetailList.get(position).getReferral().equals("") || mDetailList.get(position).getReferral().isEmpty()) {
                holder.mDetailReferral.setVisibility(View.GONE);
            } else {
                holder.mDetailReferral.setText(mDetailList.get(position).getReferral());
            }
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.detail_title)
        TextView mDetailTitle;
        @BindView(R.id.detail_img)
        ImageView mDetailImg;
        @BindView(R.id.detail_referral)
        TextView mDetailReferral;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
