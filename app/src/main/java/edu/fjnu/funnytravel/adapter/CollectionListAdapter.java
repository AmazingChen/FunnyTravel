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
import edu.sqchen.iubao.model.entity.Collection;

/**
 * Created by Administrator on 2017/6/11.
 */

public class CollectionListAdapter extends ArrayAdapter {
    private List<Collection> mCollectionList;
    private Context mContext;

    public CollectionListAdapter(@NonNull Context context, List<Collection> mCollections) {
        super(context, 0, mCollections);
        this.mCollectionList = mCollections;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mCollectionList.size();
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_collection, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mCollectionName.setText(mCollectionList.get(position).getUsername());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.collection_name)
        TextView mCollectionName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
