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
import edu.sqchen.iubao.model.entity.Hotel;

/**
 * Created by Administrator on 2017/4/27.
 */

public class HotelListAdapter extends ArrayAdapter {

    private List<Hotel> mHotelList;

    private LayoutInflater mInflater;

    private Context mContext;

    public HotelListAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.mHotelList = objects;
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mHotelList.size();
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
            convertView = mInflater.inflate(R.layout.item_hotel, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Hotel hotel = mHotelList.get(position);
        Glide.with(mContext).load(hotel.getLargePic()).into(holder.mHotelImg);
        holder.mHotelName.setText(hotel.getName());
        holder.mHotelAddress.setText(hotel.getAddress());
        holder.mHotelType.setText(hotel.getClassName());
        holder.mHotelSatisfaction.setText(hotel.getManyidu());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.hotel_img)
        ImageView mHotelImg;

        @BindView(R.id.hotel_name)
        TextView mHotelName;

        @BindView(R.id.hotel_type)
        TextView mHotelType;

        @BindView(R.id.hotel_address)
        TextView mHotelAddress;

        @BindView(R.id.hotel_satisfaction)
        TextView mHotelSatisfaction;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
