package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Trip;

/**
 * Created by Administrator on 2017/4/25.
 */

public class TripRVAdapter extends RecyclerView.Adapter<TripRVAdapter.ViewHolder> {

    private List<Trip> mTripList;

    private Context mContext;

    private int position;

    private static final int ITEM_DELETE = Menu.FIRST;

    private static final int ITEM_SHARE = Menu.FIRST + 1;

    public TripRVAdapter(Context context,List<Trip> tripList) {
        this.mContext = context;
        this.mTripList = tripList;
    }

    public OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view,int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from((parent.getContext())).inflate(R.layout.item_trip,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //第4步，因为上下文菜单的onCreateContextMenu()方法在onLongClickListener()方法之后才调用，
        // 所以在长按事件中传递position索引值，
        // 并且要返回false上下文菜单才会生成，若返回true，表示消费长按事件，不会调用onCreateContextMneu()方法
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                setPosition(holder.getPosition());
                return false;
            }
        });

        TextView destinationName = (TextView) holder.mCardView.findViewById(R.id.trip_attraction);
        TextView tripBeginTime = (TextView) holder.mCardView.findViewById(R.id.trip_begin_time);
        TextView tripOverTime = (TextView) holder.mCardView.findViewById(R.id.trip_over_time);
        ImageView destinationImg = (ImageView) holder.mCardView.findViewById(R.id.trip_attraction_img);

        Trip currTrip = mTripList.get(position);
        //字体加粗
        destinationName.getPaint().setFakeBoldText(true);
        //目的地名称
        destinationName.setText(currTrip.getDestinationName());
        //行程开始时间
        tripBeginTime.setText(currTrip.getBeginTime());
        //行程结束时间
        tripOverTime.setText(currTrip.getOverTime());
        //目的地图片
        Glide.with(mContext).load(currTrip.getImageUrl()).into(destinationImg);
    }

    @Override
    public int getItemCount() {
        return mTripList.size();
    }

    //第2步，实现下面两个方法，获取点击项索引，用于数据传递
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    //第5步，设置长按事件为null
    @Override
    public void onViewRecycled(ViewHolder holder) {
        holder.mCardView.setOnLongClickListener(null);
        super.onViewRecycled(holder);
    }

    //第3步，让ViewHolder实现OnCreateContextMneuListener接口，分为3步
    //3.1 实现接口
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{
        public CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.mCardView = (CardView) itemView;
            mCardView.setOnClickListener(this);
            //3.2 注册监听
            mCardView.setOnCreateContextMenuListener(this);
        }

        //通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v,getPosition());
            }
        }

        //3.3 重写onCreateContextMenu()方法，添加菜单项
        @Override
        public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(0,ITEM_DELETE,0, R.string.delete);
            menu.add(0,ITEM_SHARE,0, R.string.share);
        }
    }


}
