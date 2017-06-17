package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.HotelDetailPagerAdapter;

public class HotelDetailActivity extends AppCompatActivity {

    @BindView(R.id.hotel_detail_img)
    ImageView mHotelDetailImg;

    @BindView(R.id.tool_bar_hotel_detail)
    Toolbar mToolBarHotelDetail;

    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.tabs)
    SmartTabLayout mTabs;

    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.view_pager_ticket)
    ViewPager mViewPagerTicket;

    @BindView(R.id.rel_hotel_detail)
    RelativeLayout mRelHotelDetail;

    private HotelDetailPagerAdapter mDetailPagerAdapter;

    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_detail);
        ButterKnife.bind(this);
        init();
        initView();
    }

    private void init() {
        mIntent = getIntent();
        mDetailPagerAdapter = new HotelDetailPagerAdapter(getSupportFragmentManager(),this);
        mViewPagerTicket.setAdapter(mDetailPagerAdapter);
        mViewPagerTicket.setOffscreenPageLimit(mDetailPagerAdapter.getCount());
        mTabs.setViewPager(mViewPagerTicket);
    }

    private void initView() {
        mToolBarHotelDetail.setTitle("酒店详情");
        mToolBarHotelDetail.setTitleTextColor(Color.WHITE);
        mToolBarHotelDetail.setNavigationIcon(R.drawable.ic_back);
        mToolBarHotelDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);

        Glide.with(this)
                .load(mIntent.getStringExtra("selectedImgUrl"))
                .into(mHotelDetailImg);
        Log.d("imgurl",mIntent.getStringExtra("selectedImgUrl"));
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if(Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    mToolBarHotelDetail.setTitleTextColor(Color.WHITE);
                    mCollapsingToolbarLayout.setTitle(mIntent.getStringExtra("selectedHName"));
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });
    }
}
