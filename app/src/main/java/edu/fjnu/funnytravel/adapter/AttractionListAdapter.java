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
import edu.sqchen.iubao.model.entity.Attraction;

/**
 * Created by Administrator on 2017/4/23.
 */

public class AttractionListAdapter extends ArrayAdapter {

    private List<Attraction> mAttractionList;

    private Context mContext;

    private LayoutInflater mInflater;

    public AttractionListAdapter(Context context, List<Attraction> attractionList) {
        super(context, 0, attractionList);
        mContext = context;
        mAttractionList = attractionList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mAttractionList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = mInflater.inflate(R.layout.item_attraction, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Attraction currAttraction = mAttractionList.get(position);
        Glide.with(mContext).load(currAttraction.getImgurl()).into(holder.mAttractionsImg);
        holder.mAttractionsName.setText(currAttraction.getTitle());
        holder.mAttractionsLevel.setText(currAttraction.getGrade());
        holder.mAttractionsAddr.setText(currAttraction.getAddress());

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.attractions_img)
        ImageView mAttractionsImg;

        @BindView(R.id.attractions_name)
        TextView mAttractionsName;

        @BindView(R.id.attractions_level)
        TextView mAttractionsLevel;

        @BindView(R.id.attractions_addr)
        TextView mAttractionsAddr;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
