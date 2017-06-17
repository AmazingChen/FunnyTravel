package edu.sqchen.iubao.ui.fragment;


import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.qslll.library.ExpandingPagerFactory;
import com.qslll.library.fragments.ExpandingFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.TravelViewPagerAdapter;
import edu.sqchen.iubao.model.entity.Travel;
import edu.sqchen.iubao.ui.activity.InfoActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FindFragment extends Fragment implements ExpandingFragment.OnExpandingClickListener {


    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.back)
    RelativeLayout mBack;
    Unbinder unbinder;
    @BindView(R.id.tool_bar_find)
    Toolbar mToolBarFind;

    public FindFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_picture, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        setUpWindowAnimations();

        TravelViewPagerAdapter adapter = new TravelViewPagerAdapter(getChildFragmentManager());
        adapter.addAll(getTravelList());
        mViewPager.setAdapter(adapter);

        ExpandingPagerFactory.setupViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                ExpandingFragment expandingFragment = ExpandingPagerFactory.getCurrentFragment(mViewPager);
                if (expandingFragment != null && expandingFragment.isOpenend()) {
                    expandingFragment.close();
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    private void initToolbar() {
        mToolBarFind.setTitle("·¢ÏÖ");
        mToolBarFind.setTitleTextColor(Color.WHITE);
    }

//    @Override
//    public void onBackPressed() {
//        if(!ExpandingPagerFactory.onBackPressed(mViewPager)) {
//            super.onBackPressed();
//        }
//    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setUpWindowAnimations() {
        Explode slideTransition = new Explode();
        getActivity().getWindow().setReenterTransition(slideTransition);
        getActivity().getWindow().setExitTransition(slideTransition);
    }

    private List<Travel> getTravelList() {
        List<Travel> travels = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            travels.add(new Travel("Seychelles", R.drawable.seychelles));
            travels.add(new Travel("Shang Hai", R.drawable.shh));
            travels.add(new Travel("New York", R.drawable.newyork));
            travels.add(new Travel("castle", R.drawable.p1));
        }
        return travels;
    }

    private void startInfoActivity(View view, Travel travel) {
        ActivityCompat.startActivity(getContext(), InfoActivity.newInstance(getContext(), travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        getActivity(),
                        new Pair<View, String>(view, "transition_image"))
                        .toBundle());
//        Intent intent = InfoActivity.newInstance(getContext(),travel);
//        startActivity(intent);
    }

    @Override
    public void onExpandingClick(View v) {
        Log.d("sqchen", "you click findfragment");
        View view = v.findViewById(R.id.image);
        Travel travel = getTravelList().get(mViewPager.getCurrentItem());
        startInfoActivity(view, travel);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
