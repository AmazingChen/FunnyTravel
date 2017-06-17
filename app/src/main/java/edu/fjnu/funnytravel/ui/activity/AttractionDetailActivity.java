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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.AttractionDetailPagerAdapter;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.AttractionDetail;
import jp.wasabeef.glide.transformations.BlurTransformation;


/**
 * 参考AdjustableListView项目可实现仿微博“发现”页面的效果
 * 地址：http://www.qingpingshan.com/rjbc/az/170277.html
 */
public class AttractionDetailActivity extends AppCompatActivity {

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

    //是否隐藏了头部
    private boolean isImgHeaderHide = false;

    //fragment适配器
    private AttractionDetailPagerAdapter mDetailPagerAdapter;

    private Intent mIntent;

    private String mName;

    private AttractionDetail mAttractionDetail;

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
        mToolBarAttractionDetail.setTitleTextColor(Color.WHITE);
        mToolBarAttractionDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBarAttractionDetail.setTitle(mName);

        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));

        mAttractionName.setText(mName);


        Glide.with(this)
                .load(mIntent.getStringExtra("selectedImgUrl"))
                .crossFade(1000)
                .bitmapTransform(new BlurTransformation(this, 2, 2))
                .into(mDetailImg);

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
//                if(Math.abs(i) >= headerHeight) {
//                    //当偏移量超过顶部img的高度时，我们认为他已经完全移动出屏幕了
//                    isImgHeaderHide = true;
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams)  mDetailImg.getLayoutParams();
//                            layoutParams.setScrollFlags(0);
//                            mDetailImg.setLayoutParams(layoutParams);
//                            mDetailImg.setVisibility(View.GONE);
//                        }
//                    },100);
//                    mCollapsingToolbarLayout.setTitle("景点详情");
//                } else {
//                    mCollapsingToolbarLayout.setTitle("");
//                }
            }
        });

    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK) {
//            if(isImgHeaderHide) {
//                isImgHeaderHide = false;
//                ((TicketFragment) mDetailPagerAdapter.getFragmentList().get(0)).getListView().smoothScrollToPosition(0);
//                mDetailImg.setVisibility(View.VISIBLE);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        AppBarLayout.LayoutParams layoutParams = (AppBarLayout.LayoutParams) mDetailImg.getLayoutParams();
//                        layoutParams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
//                        AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
//                        mDetailImg.setLayoutParams(layoutParams);
//                    }
//                },300);
//            } else{
//                finish();
//            }
//        }
//        return super.onKeyDown(keyCode,event);
//    }

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
                        Intent mIntent = new Intent(AttractionDetailActivity.this,DescribeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("ATTRACTION_DETAIL",mAttractionDetail);
                        mIntent.putExtra("ATTRACTION_NAME",mName);
                        mIntent.putExtras(bundle);
                        Log.d("DETAIL","Detail:" + mAttractionDetail.getResult().get(0).getReferral());
                        startActivity(mIntent);
                    }
                });
            }
        });
    }

}