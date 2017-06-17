package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/4/21.
 */

public class TabFragmentAdapter extends FragmentPagerAdapter {

    private Context mContext;

    private String[] topicName;

    private List<Fragment> mFragments;


    public TabFragmentAdapter(List<Fragment> fragments, String[] titles, FragmentManager fm, Context context) {
        super(fm);
        mContext = context;
        topicName = titles;
        mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return topicName.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return topicName[position];
    }

}
