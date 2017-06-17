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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.TravelNote;

/**
 * Created by Administrator on 2017/4/26.
 */

public class TravelNoteListAdapter extends ArrayAdapter {

    private List<TravelNote> mTravelNoteList;

    private LayoutInflater mInflater;

    public TravelNoteListAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
        this.mTravelNoteList = objects;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTravelNoteList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(viewHolder == null) {
            convertView = mInflater.inflate(R.layout.item_travel_note, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //To bind data with view

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.note_img)
        ImageView mNoteImg;

        @BindView(R.id.note_title)
        TextView mNoteTitle;

        @BindView(R.id.note_publish_time)
        TextView mNotePublishTime;

        @BindView(R.id.note_author)
        TextView mNoteAuthor;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
