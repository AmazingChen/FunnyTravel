package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import edu.sqchen.iubao.R;

/**
 * Created by Administrator on 2017/5/21.
 */

public class PicListAdapter extends ArrayAdapter {

    @BindView(R.id.pic_picture)
    ImageView mPicPicture;
    @BindView(R.id.pic_rel_collect)
    RelativeLayout mPicRelCollect;
    private Context mContext;

    private String[] imgUrls;

    public PicListAdapter(@NonNull Context context, String[] imgUrls) {
        super(context, 0, imgUrls);
        this.mContext = context;
        this.imgUrls = imgUrls;
    }

    @Override
    public int getCount() {
        return imgUrls.length;
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_find_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(imgUrls[position]).into(holder.mImgPicture);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.pic_author_icon)
        CircleImageView mPicAuthorIcon;
        @BindView(R.id.pic_rel_love)
        RelativeLayout mPicRelLove;
        @BindView(R.id.pic_rel_comment)
        RelativeLayout mPicRelComment;
        @BindView(R.id.pic_rel_collect)
        RelativeLayout mPicRelCollect;
        @BindView(R.id.pic_picture)
        ImageView mImgPicture;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
