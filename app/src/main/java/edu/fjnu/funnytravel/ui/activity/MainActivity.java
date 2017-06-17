package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qslll.library.fragments.ExpandingFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Travel;
import edu.sqchen.iubao.ui.fragment.FindFragment;
import edu.sqchen.iubao.ui.fragment.HomeFragment;
import edu.sqchen.iubao.ui.fragment.MsgFragment;
import edu.sqchen.iubao.ui.fragment.PersonalFragment;
import edu.sqchen.iubao.ui.fragment.TripFragment;

public class MainActivity extends AppCompatActivity implements ExpandingFragment.OnExpandingClickListener {

    @BindView(R.id.tab_img_1)
    ImageView mImgHome;
    @BindView(R.id.tab_rel_1)
    RelativeLayout mRelHome;
    @BindView(R.id.tab_img_2)
    ImageView mImgFind;
    @BindView(R.id.tab_rel_2)
    RelativeLayout mRelFind;
    @BindView(R.id.tab_img_3)
    ImageView mImgMsg;
    @BindView(R.id.tab_rel_3)
    RelativeLayout mRelMsg;
    @BindView(R.id.tab_img_4)
    ImageView mImgPersonal;
    @BindView(R.id.tab_rel_4)
    RelativeLayout mRelPersonal;
    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;

    private FragmentManager mManager;
    private HomeFragment mHomeFragment;
    private FindFragment mFindFragment;
    private MsgFragment mMsgFragment;
    private TripFragment mTripFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mManager = getSupportFragmentManager();
        setTabSelection(0);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent mIntent = null;
                switch(item.getItemId()) {
                    case R.id.my_strategy:
                        Log.d("main","click");
                        mIntent = new Intent(getApplicationContext(),PersonalStrategy.class);
                        break;
                    case R.id.my_collection:
                        mIntent = new Intent(getApplicationContext(),CollectionActivity.class);
                        Log.d("main","click");
                        break;
                    case R.id.my_trip:
                        Log.d("main","click");
                        break;
                    case R.id.my_setting:
                        Log.d("main","click");
                        break;
                    default:
                        break;
                }
                startActivity(mIntent);
                return false;
            }
        });
    }

    private void setTabSelection(int position) {
        resetStatus();
        FragmentTransaction transaction = mManager.beginTransaction();
        hideFragment(transaction);
        switch (position) {
            case 0:
                mImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_pressed));
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.main_content_layout, mHomeFragment);
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case 1:
                mImgPersonal.setImageDrawable(getResources().getDrawable(R.drawable.ic_trip_pressed));
                if (mTripFragment == null) {
                    mTripFragment = new TripFragment();
                    transaction.add(R.id.main_content_layout, mTripFragment);
                } else {
                    transaction.show(mTripFragment);
                }
                break;
            case 2:
                mImgFind.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_pressed));
                if (mFindFragment == null) {
                    mFindFragment = new FindFragment();
                    transaction.add(R.id.main_content_layout, mFindFragment);
                } else {
                    transaction.show(mFindFragment);
                }
                break;
            case 3:
                mImgMsg.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_pressed));
                if (mMsgFragment == null) {
                    mMsgFragment = new MsgFragment();
                    transaction.add(R.id.main_content_layout, mMsgFragment);
                } else {
                    transaction.show(mMsgFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 重置图片状态
     */
    private void resetStatus() {
        mImgHome.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_normal));
        mImgFind.setImageDrawable(getResources().getDrawable(R.drawable.ic_find_normal));
        mImgMsg.setImageDrawable(getResources().getDrawable(R.drawable.ic_message_normal));
        mImgPersonal.setImageDrawable(getResources().getDrawable(R.drawable.ic_trip_normal));
    }

    /**
     * 隐藏碎片
     *
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mFindFragment != null) {
            transaction.hide(mFindFragment);
        }
        if (mMsgFragment != null) {
            transaction.hide(mMsgFragment);
        }
        if (mTripFragment != null) {
            transaction.hide(mTripFragment);
        }
    }

    @OnClick({R.id.tab_rel_1, R.id.tab_rel_2, R.id.tab_rel_3, R.id.tab_rel_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tab_rel_1:
                setTabSelection(0);
                break;
            case R.id.tab_rel_2:
                setTabSelection(2);
                break;
            case R.id.tab_rel_3:
                setTabSelection(3);
                break;
            case R.id.tab_rel_4:
                setTabSelection(1);
                break;
        }
    }

    @Override
    public void onExpandingClick(View v) {
        Log.d("sqchen","you click mainactivity");
    }

}
