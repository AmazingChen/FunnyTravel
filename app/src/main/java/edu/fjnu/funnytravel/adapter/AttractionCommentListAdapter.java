package edu.sqchen.iubao.adapter;

import android.content.Context;
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
import edu.sqchen.iubao.model.entity.AttractionComment;

/**
 * Created by Administrator on 2017/5/3.
 */

public class AttractionCommentListAdapter extends ArrayAdapter {

    private List<AttractionComment> mCommentList;

    private Context mContext;

    public AttractionCommentListAdapter(Context context, List<AttractionComment> comments) {
        super(context, 0, comments);
        this.mCommentList = comments;
        this.mContext = context;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return mCommentList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (holder == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_attraction_comment, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        AttractionComment comment = mCommentList.get(position);
        holder.mCommentAuthorName.setText(comment.getUsername());
        holder.mCommentPublishTime.setText(comment.getTime());
        holder.mCommentContent.setText(comment.getComment());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.comment_author_icon)
        CircleImageView mCommentAuthorIcon;
        @BindView(R.id.comment_author_name)
        TextView mCommentAuthorName;
        @BindView(R.id.comment_publish_time)
        TextView mCommentPublishTime;
        @BindView(R.id.comment_content)
        TextView mCommentContent;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

}
