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
import de.hdodenhof.circleimageview.CircleImageView;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Chat;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ChatListAdapter extends ArrayAdapter {

    private List<Chat> mChatList;
    private LayoutInflater mInflater;

    public ChatListAdapter(Context context, List objects) {
        super(context, 0, objects);
        this.mChatList = objects;
        this.mInflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return mChatList.size();
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
            convertView = mInflater.inflate(R.layout.item_chat, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.chat_img)
        CircleImageView mChatImg;

        @BindView(R.id.chat_name)
        TextView mChatName;

        @BindView(R.id.chat_time)
        TextView mChatTime;

        @BindView(R.id.chat_content)
        TextView mChatContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
