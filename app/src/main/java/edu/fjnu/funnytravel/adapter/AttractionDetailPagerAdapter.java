package edu.sqchen.iubao.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import edu.sqchen.iubao.ui.fragment.AttractionCommentFragment;
import edu.sqchen.iubao.ui.fragment.StrategyFragment;
import edu.sqchen.iubao.ui.fragment.TicketFragment;

/**
 * Created by Administrator on 2017/5/2.
 */

public class AttractionDetailPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragmentList;

    private Context mContext;

    private String[] titles;

    public AttractionDetailPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.mContext = context;
        initFragments();
    }

    public ArrayList<Fragment> getFragmentList() {
        return fragmentList;
    }

    private void initFragments() {
        titles = new String[] {"门票","攻略","评价"};
        fragmentList = new ArrayList<>();
        fragmentList.add(new TicketFragment());
        fragmentList.add(new StrategyFragment());
        fragmentList.add(new AttractionCommentFragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
