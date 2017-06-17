package edu.sqchen.iubao.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.AttractionListAdapter;
import edu.sqchen.iubao.app.MyApplication;
import edu.sqchen.iubao.db.IUBaoDB;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.Attraction;
import edu.sqchen.iubao.model.entity.AttractionComment;
import edu.sqchen.iubao.model.entity.ResultData;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.model.entity.User;
import edu.sqchen.iubao.ui.activity.AttractionDetailActivity;
import edu.sqchen.iubao.ui.util.DateStrUtil;
import edu.sqchen.iubao.widget.ScrollListView;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 * BaseFragment<IAttractionView, AttractionPresenter> implements IAttractionView
 */
public class AttractionFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.attraction_list_view)
    ScrollListView mAttractionListView;
    @BindView(R.id.attraction_refresh)
    SwipeRefreshLayout mAttractionRefresh;
    Unbinder unbinder;

    private AttractionListAdapter mAdapter;
    private List<Attraction> mAttractionList = new ArrayList<>();
    private IUBaoDB mIUBaoDB;
    private View mView;
    private SweetAlertDialog mLoadingDialog;
    private MyApplication locApp;
    private Calendar mCalendar;
    private DatePickerDialog mDatePicker;

    //长按选择的项
    private int selectPosition;
    //收藏
    private static final int ITEM_COLLECT = Menu.FIRST;
    //评价
    private static final int ITEM_EVALUATE = Menu.FIRST + 1;
    //添加攻略
    private static final int ITEM_STRATEGY = Menu.FIRST + 2;
    //加入行程
    private static final int ITEM_ADD = Menu.FIRST + 3;

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(mAttractionList.size() != 0 && mAttractionList != null) {
                mAttractionList.clear();
                getAttractionData();
            } else {
                getAttractionData();
            }
        }
    };

    public AttractionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("create", "attractionfragment create");
        mView = inflater.inflate(R.layout.fragment_attraction, container, false);
        unbinder = ButterKnife.bind(this, mView);
        locApp = (MyApplication) getActivity().getApplication();
        initLoadingDialog();
        initListView();
        mAttractionRefresh.post(new Runnable() {
            @Override
            public void run() {
                mAttractionRefresh.setRefreshing(true);
            }
        });
        mRefreshListener.onRefresh();
        initDateDialog();

        return mView;
    }

    private void initLoadingDialog() {
        mLoadingDialog = new SweetAlertDialog(getContext(), SweetAlertDialog.PROGRESS_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.blue));
        mLoadingDialog.setTitleText("Loading");
        mLoadingDialog.setCancelable(false);
    }

    private void initListView() {
        mAttractionRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mAttractionRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mAttractionRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mAttractionRefresh.setOnRefreshListener(mRefreshListener);

        mAttractionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), AttractionDetailActivity.class);
                mIntent.putExtra("selectedSId",mAttractionList.get(position).getSid());
                mIntent.putExtra("selectedImgUrl",mAttractionList.get(position).getImgurl());
                mIntent.putExtra("selectedSName",mAttractionList.get(position).getTitle());
                startActivity(mIntent);
            }
        });
        mAttractionListView.setOnCreateContextMenuListener(this);
    }


    /**
     * 初始化DatePickerDialog
     */
    private void initDateDialog() {
        mCalendar = Calendar.getInstance();
        mDatePicker = DatePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * 获取当前城市所有景点数据
     */
    private void getAttractionData() {
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance()
                .doUnifySubscribe(attractionService.getAttractionsInfo(
                        Integer.parseInt(locApp.getCuurentCity().getCityId()),
                        1,
                        "bc9350399df04805b88acb49a07e45e2"),new RxSubscriber<ResultData>() {
                            @Override
                            protected void _onError(Throwable e) {
                                mAttractionRefresh.setRefreshing(false);
                                Toast.makeText(getContext(),"获取失败，请检查网络设置!",Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            protected void _onNext(ResultData resultData) {
                                ArrayList attractions = (ArrayList) resultData.getResult();

                                Gson gson = new Gson();
                                String[] attractionArray = new String[attractions.size()];
                                for (int i = 0; i < attractions.size(); i++) {
                                    attractionArray[i] = gson.toJson(attractions.get(i));
                                    Attraction attraction = gson.fromJson(attractionArray[i], Attraction.class);
                                    mAttractionList.add(attraction);
                                }
                                mAdapter = new AttractionListAdapter(getContext(), mAttractionList);
                                mAttractionListView.setAdapter(mAdapter);
                                mAttractionRefresh.setRefreshing(false);
                            }
                        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectPosition = adapterContextMenuInfo.position;
        menu.setHeaderTitle(mAttractionList.get(selectPosition).getTitle());
        menu.add(0,ITEM_COLLECT,0,"收藏");
        menu.add(0,ITEM_EVALUATE,0,"评价");
        menu.add(0,ITEM_STRATEGY,0,"添加攻略");
        menu.add(0,ITEM_ADD,0,"加入行程");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        Intent mIntent = null;
        switch(menuItem.getItemId()) {
            //收藏景点
            case ITEM_COLLECT:
                String attractionId = mAttractionList.get(selectPosition).getSid();
                collectAttraction("sqchen",attractionId);
                break;
            //评价景点
            case ITEM_EVALUATE:
                long seconds = System.currentTimeMillis();
                AttractionComment attractionComment = new AttractionComment(mAttractionList.get(selectPosition).getSid(),
                        "这地方真不错！",
                        "sqchen",
                        DateStrUtil.timeStamp2Date(seconds,null));
                addComment(attractionComment);
                break;
            //添加景点攻略
            case ITEM_STRATEGY:

                break;
            //将景点加入行程
            case ITEM_ADD:
                    mDatePicker.show(getActivity().getFragmentManager(),"DatePicker");
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onDateSet(DatePickerDialog view,
                          int year,
                          int monthOfYear,
                          int dayOfMonth,
                          int yearEnd,
                          int monthOfYearEnd,
                          int dayOfMonthEnd) {
        Attraction selectAttraction = mAttractionList.get(selectPosition);
        //目的地id
        String destinationId = selectAttraction.getSid();
        //目的地名称
        String destinationName = selectAttraction.getTitle();
        //目的地图片URL
        String imgUrl = selectAttraction.getImgurl();
        //行程开始时间
        String beginTimeStr = DateStrUtil.getDateStr(monthOfYear,dayOfMonth);
        //行程结束时间
        String overTimeStr = DateStrUtil.getDateStr(monthOfYearEnd,dayOfMonthEnd);
        Trip trip = new Trip(destinationId,destinationName,imgUrl,beginTimeStr,overTimeStr,"null weather info","sqchen");
        saveTrip(trip);
    }

    /**
     * 保存行程信息
     * @param trip
     */
    private void saveTrip(Trip trip) {
        //将Trip对象转换为JSON数据，作为参数传递，发送到后台
        Gson gson = new Gson();
        String tripJson = gson.toJson(trip);
        AttractionService attractionService = NetManager
                .getInstance()
                .createWithUrl(AttractionService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(attractionService.saveTripInfo(tripJson),
                new RxSubscriber<Trip>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),
                                "行程制定失败！",
                                Toast.LENGTH_SHORT)
                                .show();
                    }

                    @Override
                    protected void _onNext(Trip trip) {
                        Log.d("arrt","返回的行程对象：" + trip.getUsername() + trip.getDestinationName() + "行程ID：" + trip.getTripId());
                        Toast.makeText(getContext(),
                                "行程制定成功！",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    /**
     * 收藏景点
     * @param username
     * @param attractionId
     */
    private void collectAttraction(String username,String attractionId) {
        AttractionService service = NetManager.getInstance().createWithUrl(AttractionService.class,ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribe(service.addCollection(username, attractionId),
                new RxSubscriber<HttpResult>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),"收藏失败！请检查网络设置！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(HttpResult httpResult) {
                        if(httpResult.getCode() == 1) {
                            Toast.makeText(getContext(),"收藏成功！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"收藏失败！" + httpResult.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 添加评价
     * @param comment
     */
    private void addComment(AttractionComment comment) {
        Gson gson = new Gson();
        String commentJson = gson.toJson(comment);
        Log.d("attr",commentJson);
        AttractionService service = NetManager.getInstance().createWithUrl(AttractionService.class,ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribe(service.addComment(commentJson),
                new RxSubscriber<HttpResult>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),"评价失败！请检查网络设置！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(HttpResult httpResult) {
                        if(httpResult.getCode() == 1) {
                            Toast.makeText(getContext(),"评价成功！",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(),"评价失败！" + httpResult.getMsg(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
