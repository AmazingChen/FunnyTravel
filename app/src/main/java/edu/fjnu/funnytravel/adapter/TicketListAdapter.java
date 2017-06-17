package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Ticket;

/**
 * Created by Administrator on 2017/5/2.
 */

public class TicketListAdapter extends ArrayAdapter {

    private List<Ticket.ResultBean> mTicketList;

    private Context mContext;

    public TicketListAdapter(@NonNull Context context, List<Ticket.ResultBean> mTicketList) {
        super(context, 0, mTicketList);
        this.mContext = context;
        this.mTicketList = mTicketList;
    }

    @Override
    public int getCount() {
        return mTicketList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_ticket, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mTicketName.setText(mTicketList.get(position).getName());
        holder.mTicketPrice.setText("￥" + mTicketList.get(position).getPrice());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.ticket_name)
        TextView mTicketName;

        @BindView(R.id.ticket_price)
        TextView mTicketPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
