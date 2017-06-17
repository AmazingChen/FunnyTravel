package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.GridPicture;

/**
 * Created by Administrator on 2017/5/20.
 */

public class GridAdapter extends BaseAdapter {
    private List<GridPicture> mPictureList;

    private Context mContext;

    public GridAdapter(Context context, List<GridPicture> pictureList) {
        this.mContext = context;
        this.mPictureList = pictureList;
    }

    @Override
    public int getCount() {
        return mPictureList.size();
    }

    @Override
    public Object getItem(int position) {
        return mPictureList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_find_grid, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Glide.with(mContext).load(mPictureList.get(position).getPicUrl()).into(holder.mItemPicture);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_picture)
        ImageView mItemPicture;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
