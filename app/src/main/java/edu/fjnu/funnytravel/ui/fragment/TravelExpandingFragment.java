package edu.sqchen.iubao.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.qslll.library.fragments.ExpandingFragment;

import edu.sqchen.iubao.model.entity.Travel;


/**
 * Created by Administrator on 2017/6/5.
 */

public class TravelExpandingFragment extends ExpandingFragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    Travel travel;

    public static TravelExpandingFragment newInstance(Travel travel) {
        TravelExpandingFragment fragment = new TravelExpandingFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRAVEL,travel);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            travel = args.getParcelable(ARG_TRAVEL);
        }

    }

    @Override
    public Fragment getFragmentTop() {
        return FragmentTop.newInstance(travel);
    }

    @Override
    public Fragment getFragmentBottom() {
        return FragmentBottom.newInstance();
    }
}
