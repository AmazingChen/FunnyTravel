package edu.sqchen.iubao.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.TabFragmentAdapter;
import edu.sqchen.iubao.db.IUBaoDB;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.City;
import edu.sqchen.iubao.model.entity.ResultData;
import edu.sqchen.iubao.ui.activity.EditLocActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.tool_bar_home)
    Toolbar mToolBarHome;

    @BindView(R.id.tablayout_home)
    TabLayout mTablayoutHome;

    @BindView(R.id.view_pager_home)
    ViewPager mViewPagerHome;

    Unbinder unbinder;
    private TabFragmentAdapter mTabAdapter;

    private String[] mTabNameList = new String[]{"", ""};

    private List<Fragment> mFragmentList;

    private AttractionFragment mAttractionFragment;

    private WeatherFragment mWeatherFragment;

    private HotelFragment mHotelFragment;

    private IUBaoDB mIUBaoDB;

    private DrawerLayout mDrawerLayout;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("create","homefragment create");
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        initToolbar();
        getCityData();
        initTabLayout();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void init(View view) {
        unbinder = ButterKnife.bind(this, view);
        mFragmentList = new ArrayList<>();
        mIUBaoDB = IUBaoDB.getInstance(getContext());
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.activity_main);
    }

    private void initToolbar() {
        mToolBarHome.setTitle("首页");
        mToolBarHome.setTitleTextColor(Color.WHITE);
        mToolBarHome.setNavigationIcon(R.drawable.ic_menu);
        mToolBarHome.inflateMenu(R.menu.home_menu);
        mToolBarHome.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit_location:
                        Log.d("toolbar", "you click the edit-location item");
                        Intent intent = new Intent(getContext(), EditLocActivity.class);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
        mToolBarHome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void initTabLayout() {
        mAttractionFragment = new AttractionFragment();
        mFragmentList.add(mAttractionFragment);
        mHotelFragment = new HotelFragment();
        mFragmentList.add(mHotelFragment);

        mTabAdapter = new TabFragmentAdapter(mFragmentList, mTabNameList, getChildFragmentManager(), getContext());
        mViewPagerHome.setAdapter(mTabAdapter);
        mTablayoutHome.setupWithViewPager(mViewPagerHome);
        mTablayoutHome.setTabMode(TabLayout.MODE_FIXED);
        mTablayoutHome.setBackgroundColor(getResources().getColor(R.color.blue));
        mTablayoutHome.setTabTextColors(getResources().getColor(R.color.grey_light), Color.WHITE);
        mTablayoutHome.getTabAt(0).setIcon(R.drawable.ic_place_pressed);
        mTablayoutHome.getTabAt(1).setIcon(R.drawable.ic_hotel_pressed);
    }

    private void getCityData() {
        if (mIUBaoDB.loadCities().size() > 0) {
            return;
        }
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance().doUnifySubscribe(attractionService.getCityInfo("bc9350399df04805b88acb49a07e45e2"), new RxSubscriber<ResultData>() {
            @Override
            protected void _onError(Throwable e) {
                Log.d("cityInfo", e.toString());
            }

            @Override
            protected void _onNext(ResultData resultData) {
                ArrayList cities = (ArrayList) resultData.getResult();

                Gson gson = new Gson();
                String[] cityArray = new String[cities.size()];
                for (int i = 0; i < cities.size(); i++) {
                    cityArray[i] = gson.toJson(cities.get(i));
                    City city = gson.fromJson(cityArray[i], City.class);

                    //由于第340个之后的数据存在问题（340和363），暂时先舍弃,不保存到数据库中
                    if (Integer.parseInt(city.getCityId()) > 35) {
                        mIUBaoDB.saveCity(city);
                    }
                    if(city.getCityId() == "1") {
                        Log.d("cityId",city.getCityName());
                    }
                }
                Log.d("cityInfo", cities.toString());
                Log.d("cityInfo", cities.get(cities.size() - 1).toString());
                Log.d("cityInfo", mIUBaoDB.loadCities().get(0).getCityName());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
