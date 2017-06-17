package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.List;

import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.SortCity;

/**
 * Created by Administrator on 2017/4/22.
 */

public class SortAdapter extends BaseAdapter implements SectionIndexer {

    private List<SortCity> mList = null;

    private Context mContext;

    public SortAdapter(Context context,List<SortCity> list) {
        this.mContext = context;
        this.mList = list;
    }

    /**
     * 当ListVier的数据发生变化时，调用此方法进行更新ListView
     * @param list
     */
    public void updateListView(List<SortCity> list) {
        this.mList = list;
        notifyDataSetChanged();;
    }

    @Override
    public int getCount() {
        return this.mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
     public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        final SortCity mTarget = mList.get(position);
        if(view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_city,null);
            holder.tvLetter = (TextView) view.findViewById(R.id.catalog);
            holder.tvCityName = (TextView) view.findViewById(R.id.title);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置，则认为是第一次出现
        if(position == getPositionForSection(section)) {
            holder.tvLetter.setVisibility(View.VISIBLE);
            holder.tvLetter.setText(mTarget.getSortLetter());
        } else {
            holder.tvLetter.setVisibility(View.GONE);
        }

        holder.tvCityName.setText(this.mList.get(position).getCityName());

        return view;
    }

    final static class ViewHolder {
        TextView tvLetter;
        TextView tvCityName;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的char ascii的值
     * @param position
     * @return
     */
    @Override
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetter().charAt(0);
    }

    @Override
    public int getPositionForSection(int section) {
        for(int i = 0; i < getCount();i++) {
            String sortStr = mList.get(i).getSortLetter();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if(firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0,1).toUpperCase();
        //正则表达式，判断首字母是否是英文字母
        if(sortStr.matches("[A-Z]"))
            return sortStr;
        else
            return "#";
    }

    @Override
    public Object[] getSections() {
        return  null;
    }
}
