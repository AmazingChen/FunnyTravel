package edu.sqchen.iubao.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.TripRVAdapter;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.TripService;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.ui.activity.AttractionDetailActivity;
import edu.sqchen.iubao.ui.activity.TripDetailActivity;
import edu.sqchen.iubao.ui.activity.WeatherActivity;
import edu.sqchen.iubao.widget.RecyclerViewSupportEmpty;

/**
 * 滑动删除：https://github.com/daimajia/AndroidSwipeLayout
 * 删除提示：https://github.com/pedant/sweet-alert-dialog
 * A simple {@link Fragment} subclass.
 */
public class TripFragment extends Fragment {


    @BindView(R.id.tool_bar_trip)
    Toolbar mToolBarTrip;
    Unbinder unbinder;
    @BindView(R.id.lin_empty_view)
    LinearLayout mLinEmptyView;
    @BindView(R.id.trip_recyclerview)
    RecyclerViewSupportEmpty mTripRecyclerview;
    @BindView(R.id.trip_refresh)
    SwipeRefreshLayout mTripRefresh;

    private LinearLayoutManager mLayoutManager;
    private TripRVAdapter mAdapter;
    private List<Trip> mTripList;
    private Intent mIntent;
    private static final String SELECT_TRIP = "SELECT_TRIP";

    //删除行程
    private static final int ITEM_DELETE = Menu.FIRST;
    //分享行程
    private static final int ITEM_SHARE = Menu.FIRST + 1;
    private int selectPosition = -1;

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(mTripList.size() != 0 && mTripList != null) {
                mTripList.clear();
                loadTripData();
            } else {
                loadTripData();
            }
        }
    };

    public TripFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        loadTripData();
        initRecyclerView();
        mTripRefresh.post(new Runnable() {
            @Override
            public void run() {
                mTripRefresh.setRefreshing(true);
            }
        });
        mRefreshListener.onRefresh();

        return view;
    }

    private void initToolbar() {
        mToolBarTrip.setTitle("行程");
        mToolBarTrip.setTitleTextColor(Color.WHITE);
        mToolBarTrip.inflateMenu(R.menu.trip_menu);
        mToolBarTrip.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.check_weather:
                        Intent mIntent = new Intent(getContext(), WeatherActivity.class);
                        startActivity(mIntent);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 初始化RecyclerView及下拉刷新
     */
    private void initRecyclerView() {
        mTripRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mTripRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mTripRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mTripRefresh.setOnRefreshListener(mRefreshListener);

        mLayoutManager = new LinearLayoutManager(getContext());
        mTripRecyclerview.setLayoutManager(mLayoutManager);
        mTripRecyclerview.setHasFixedSize(true);
        mTripRecyclerview.setEmptyView(mLinEmptyView);
        //设置显示动画
        mTripRecyclerview.setItemAnimator(new DefaultItemAnimator());
        mTripRecyclerview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
        //添加长按上下文菜单的监听,共6步
        //参考链接：https://stackoverflow.com/questions/26466877/how-to-create-context-menu-for-recyclerview
        //原理是在适配器TripRVAdapter中拦截长按事件，实现上下文菜单的生成事件
        //第1步，为RecyclerView注册上下文菜单事件
        registerForContextMenu(mTripRecyclerview);
    }

    /**
     * 加载行程数据
     */
    private void loadTripData() {
        mTripList = new ArrayList<>();
        TripService tripService = NetManager.getInstance().createWithUrl(TripService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(tripService.getTripList("sqchen"),
                new RxSubscriber<List<Trip>>() {
                    @Override
                    protected void _onError(Throwable e) {
                        mTripRefresh.setRefreshing(false);
                        Toast.makeText(getContext(), "行程数据获取失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(List<Trip> tripList) {
                        mTripList = tripList;
                        Log.d("trip", "地址是：" + mTripList.get(0).getImageUrl());
                        Log.d("arrt","行程ID：" + tripList.get(0).getTripId());
//                        Toast.makeText(getContext(), "行程数据获取成功！", Toast.LENGTH_SHORT).show();
                        mAdapter = new TripRVAdapter(getContext(),mTripList);
                        //取消刷新
                        mTripRefresh.setRefreshing(false);
                        mTripRecyclerview.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new TripRVAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(final View view, final int position) {
                                view.animate()
                                        .translationZ(8F)
                                        .setDuration(300)
                                        .setListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                mIntent = new Intent(getContext(), TripDetailActivity.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putSerializable(SELECT_TRIP,mTripList.get(position));
                                                mIntent.putExtras(bundle);
                                                startActivity(mIntent);
                                            }
                                        });
                            }
                        });
                    }
                });
    }

    //第6步，重写onContextItemSelected()方法
    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        try {
            selectPosition = mAdapter.getPosition();
        } catch (Exception e) {
            return super.onContextItemSelected(menuItem);
        }
        switch (menuItem.getItemId()) {
            case ITEM_DELETE:
                new SweetAlertDialog(getContext(),SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("删除此行程？")
                        .setContentText("删除后不可恢复")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                deleteTrip(mTripList.get(selectPosition).getTripId());
                                sweetAlertDialog.setTitleText("完成")
                                        .setContentText("此行程已被删除")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        }).show();
                break;
            case ITEM_SHARE:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 删除行程
     * @param tripId 选中项行程ID
     */
    private void deleteTrip(int tripId) {
        TripService tripService = NetManager.getInstance().createWithUrl(TripService.class,ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribe(
                tripService.deleteTrip(tripId),
                new RxSubscriber<HttpResult>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(), "删除失败,请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(HttpResult httpResult) {
                        Log.d("trip",httpResult.getCode() + "");
                        if(httpResult.getCode() == 1) {
                            mTripList.remove(selectPosition);
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "删除失败！" + httpResult.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
