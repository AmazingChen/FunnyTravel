package edu.sqchen.iubao.ui.activity;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gordonwong.materialsheetfab.DimOverlayFrameLayout;
import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.AttractionDetailPagerAdapter;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.model.entity.AttractionDetail;
import edu.sqchen.iubao.widget.MenuFab;
import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 参考AdjustableListView项目可实现仿微博“发现”页面的效果
 * 地址：http://www.qingpingshan.com/rjbc/az/170277.html
 * https://github.com/gowong/material-sheet-fab
 */
public class AttractionDetailActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.tool_bar_attraction_detail)
    Toolbar mToolBarAttractionDetail;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.view_pager_ticket)
    ViewPager mViewPagerTicket;
    @BindView(R.id.attraction_detail_img)
    ImageView mDetailImg;
    @BindView(R.id.tabs)
    SmartTabLayout mTabs;
    @BindView(R.id.attraction_detail_describe)
    TextView mAttractionDescribe;
    @BindView(R.id.rel_attraction_detail)
    RelativeLayout mRelAttractionDetail;
    @BindView(R.id.attraction_detail_lin)
    LinearLayout mAttractionLin;
    @BindView(R.id.attraction_detail_name)
    TextView mAttractionName;
    @BindView(R.id.fab_menu)
    MenuFab mFabMenu;
    @BindView(R.id.overlay)
    DimOverlayFrameLayout mOverlay;
    @BindView(R.id.item_add)
    TextView mItemAdd;
    @BindView(R.id.fab_sheet)
    CardView mFabSheet;
    @BindView(R.id.menu_collect)
    LinearLayout mMenuCollect;
    @BindView(R.id.menu_comment)
    LinearLayout mMenuComment;
    @BindView(R.id.menu_strategy)
    LinearLayout mMenuStrategy;
    @BindView(R.id.menu_trip)
    LinearLayout mMenuTrip;

    //是否隐藏了头部
    private boolean isImgHeaderHide = false;
    //fragment适配器
    private AttractionDetailPagerAdapter mDetailPagerAdapter;
    private Intent mIntent;
    private String mName;
    private AttractionDetail mAttractionDetail;
    private MaterialSheetFab mMaterialSheetFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction_detail);
        ButterKnife.bind(this);
        init();
        initView();
        getAttractionDetailData();
    }

    private void init() {
        mDetailPagerAdapter = new AttractionDetailPagerAdapter(getSupportFragmentManager(), this);
        mViewPagerTicket.setAdapter(mDetailPagerAdapter);
        mViewPagerTicket.setOffscreenPageLimit(mDetailPagerAdapter.getCount());
        mTabs.setViewPager(mViewPagerTicket);
        mIntent = getIntent();
        mName = mIntent.getStringExtra("selectedSName");
    }

    private void initView() {
        initToolbar();
        initCollapsingLayout();
        initImg();
        initAppBarLayout();
        initMenu();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        mToolBarAttractionDetail.setTitleTextColor(Color.WHITE);
        mToolBarAttractionDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBarAttractionDetail.setTitle(mName);
    }

    /**
     * 初始化CollapsingLayout
     */
    private void initCollapsingLayout() {
        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
    }

    /**
     * 初始化背景图片和导航标题
     */
    private void initImg() {
        mAttractionName.setText(mName);
        Glide.with(this)
                .load(mIntent.getStringExtra("selectedImgUrl"))
                .crossFade(1000)
                .bitmapTransform(new BlurTransformation(this, 2, 2))
                .into(mDetailImg);
    }

    /**
     * 初始化AppBarLayout
     */
    private void initAppBarLayout() {
        LayoutTransition mTransition = new LayoutTransition();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(null, "tranlationY", 0, 1.0f)
                .setDuration(mTransition.getDuration(LayoutTransition.APPEARING));
        mTransition.setAnimator(LayoutTransition.APPEARING, objectAnimator);

        final int headerHeight = getResources().getDimensionPixelOffset(R.dimen.attraction_detail_header);
        mAppBarLayout.setLayoutTransition(mTransition);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (Math.abs(i) >= appBarLayout.getTotalScrollRange()) {
                    mToolBarAttractionDetail.setTitleTextColor(Color.WHITE);
                    mCollapsingToolbarLayout.setTitle(mIntent.getStringExtra("selectedSName"));
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });
    }

    /**
     * 初始化浮动按钮的菜单
     */
    private void initMenu() {
        int sheetColor = getResources().getColor(R.color.grey_bg);
        int fabColor = getResources().getColor(R.color.blue);
        mMaterialSheetFab = new MaterialSheetFab<>(mFabMenu, mFabSheet, mOverlay, sheetColor, fabColor);

        //初始化菜单项点击事件
        mMenuCollect.setOnClickListener(this);
        mMenuComment.setOnClickListener(this);
        mMenuStrategy.setOnClickListener(this);
        mMenuTrip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.menu_collect:
                Log.d("menu","click collect");
                break;
            case R.id.menu_comment:
                Log.d("menu","click comment");
                break;
            case R.id.menu_strategy:
                Log.d("menu","click stratrgy");
                break;
            case R.id.menu_trip:
                Log.d("menu","click trip");
                break;
        }
        chargeSheetFab();
    }

    /**
     * 获取景点详细信息
     */
    private void getAttractionDetailData() {
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance().doUnifySubscribe(attractionService.getAttractionDetailResult(mIntent.getStringExtra("selectedSId"),
                "bc9350399df04805b88acb49a07e45e2"), new RxSubscriber<AttractionDetail>() {
            @Override
            protected void _onError(Throwable e) {

            }

            @Override
            protected void _onNext(final AttractionDetail attractionDetail) {
                AttractionDetailActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAttractionDescribe.setText(attractionDetail.getResult().get(0).getReferral());
                    }
                });
                mAttractionDetail = attractionDetail;
                mAttractionLin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mIntent = new Intent(AttractionDetailActivity.this, DescribeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ATTRACTION_DETAIL", mAttractionDetail);
                        mIntent.putExtra("ATTRACTION_NAME", mName);
                        mIntent.putExtras(bundle);
                        Log.d("DETAIL", "Detail:" + mAttractionDetail.getResult().get(0).getReferral());
                        startActivity(mIntent);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        chargeSheetFab();
    }

    /**
     * 判断菜单状态
     */
    private void chargeSheetFab() {
        if (mMaterialSheetFab.isSheetVisible()) {
            mMaterialSheetFab.hideSheet();
        } else {
            super.onBackPressed();
        }
    }

}