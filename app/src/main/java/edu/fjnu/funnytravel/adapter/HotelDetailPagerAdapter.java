package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import edu.sqchen.iubao.ui.fragment.HotelIntroFragment;
import edu.sqchen.iubao.ui.fragment.HotelRoomFragment;

/**
 * Created by Administrator on 2017/5/6.
 */

public class HotelDetailPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;

    private String[] titles;

    private ArrayList<Fragment> mFragmentArrayList;

    public HotelDetailPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
        initFragments();
    }

    private void initFragments() {
        titles = new String[]{"详情","房间"};
        mFragmentArrayList = new ArrayList<>();
        mFragmentArrayList.add(new HotelIntroFragment());
        mFragmentArrayList.add(new HotelRoomFragment());
    }

    public ArrayList<Fragment> getFragmentArrayList() {
        return mFragmentArrayList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentArrayList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentArrayList.size();
    }
}
