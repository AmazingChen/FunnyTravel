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

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.HotelListAdapter;
import edu.sqchen.iubao.app.MyApplication;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.Attraction;
import edu.sqchen.iubao.model.entity.Hotel;
import edu.sqchen.iubao.model.entity.ResultData;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.ui.activity.HotelDetailActivity;
import edu.sqchen.iubao.ui.util.DateStrUtil;
import edu.sqchen.iubao.ui.util.LoadingDialogUtil;
import edu.sqchen.iubao.widget.ScrollListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HotelFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    @BindView(R.id.hotel_list_view)
    ScrollListView mHotelListView;
    @BindView(R.id.hotel_refresh)
    SwipeRefreshLayout mHotelRefresh;

    private HotelListAdapter mListAdapter;
    private List<Hotel> mHotelList = new ArrayList<>();
    private MyApplication locApp;
    private Calendar mCalendar;
    private DatePickerDialog mDatePicker;
    private int selectPosition;
    Unbinder unbinder;

    //收藏
    private static final int ITEM_COLLECT = 10;
    //评价
    private static final int ITEM_EVALUATE = 11;
    //加入行程
    private static final int ITEM_ADD = 12;

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if(mHotelList.size() != 0 && mHotelList != null) {
                mHotelList.clear();
                getHotelData();
            } else {
                getHotelData();
            }
        }
    };

    public HotelFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hotel, container, false);
        unbinder = ButterKnife.bind(this, view);
        locApp = (MyApplication) getActivity().getApplication();
        initDateDialog();
        initListView();
        initRefresh();

        return view;
    }

    private void initRefresh() {
        mHotelRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mHotelRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light,android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mHotelRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mHotelRefresh.setOnRefreshListener(mRefreshListener);
        mHotelRefresh.post(new Runnable() {
            @Override
            public void run() {
                mHotelRefresh.setRefreshing(true);
            }
        });
        mRefreshListener.onRefresh();
    }

    /**
     * 获取酒店数据
     */
    private void getHotelData() {
        LoadingDialogUtil.initDialog(getContext());
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance().doUnifySubscribe(attractionService.getHotelData(
                Integer.parseInt(locApp.getCuurentCity().getCityId()),
                1, "bc9350399df04805b88acb49a07e45e2")
                , new RxSubscriber<ResultData>() {
                            @Override
                            protected void _onError(Throwable e) {

                            }

                            @Override
                            protected void _onNext(ResultData resultData) {
                                ArrayList hotels = (ArrayList) resultData.getResult();

                                Gson gson = new Gson();
                                String[] hotelArray = new String[hotels.size()];
                                for (int i = 0; i < hotels.size(); i++) {
                                    hotelArray[i] = gson.toJson(hotels.get(i));
                                    Hotel hotel = gson.fromJson(hotelArray[i], Hotel.class);
                                    mHotelList.add(hotel);
                                }
                                mListAdapter = new HotelListAdapter(getContext(), mHotelList);
                                mHotelListView.setAdapter(mListAdapter);
                                mHotelRefresh.setRefreshing(false);
                            }
                        });
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        mHotelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getContext(), HotelDetailActivity.class);
                mIntent.putExtra("selectedImgUrl",mHotelList.get(position).getLargePic());
                mIntent.putExtra("selectedHName",mHotelList.get(position).getName());
                mIntent.putExtra("selectedHId",String.valueOf(mHotelList.get(position).getId()));
                startActivity(mIntent);
            }
        });
        mHotelListView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        Log.d("hotel","onCreateContextMenu");
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectPosition = adapterContextMenuInfo.position;
        menu.setHeaderTitle(mHotelList.get(selectPosition).getName());
        menu.add(1,ITEM_COLLECT,0,"收藏");
        menu.add(1,ITEM_EVALUATE,0,"评价");
        menu.add(1,ITEM_ADD,0,"加入行程");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        Log.d("hotel","onContextItemSelected: OUTSIDE");
        if(getUserVisibleHint() == false) {
            return false;
        }
        switch(menuItem.getItemId()) {
            case ITEM_COLLECT:
                Log.d("hotel","onContextItemSelected: OUTSIDE");
                break;
            case ITEM_EVALUATE:
                Log.d("hotel","onContextItemSelected: OUTSIDE");
                break;
            case ITEM_ADD:
                Log.d("hotel","onContextItemSelected: ITEM_ADD");
                mDatePicker.show(getActivity().getFragmentManager(),"DatePicker");
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 初始化DatePickerDialog
     */
    private void initDateDialog() {
        Log.d("hotel","initDateDialog");
        mCalendar = Calendar.getInstance();
        mDatePicker = DatePickerDialog.newInstance(
                this,
                mCalendar.get(Calendar.YEAR),
                mCalendar.get(Calendar.MONTH),
                mCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onDateSet(DatePickerDialog view,
                          int year,
                          int monthOfYear,
                          int dayOfMonth,
                          int yearEnd,
                          int monthOfYearEnd,
                          int dayOfMonthEnd) {
        Hotel hotel = mHotelList.get(selectPosition);
        //目的地id
        String destinationId = String.valueOf(hotel.getId());
        //目的地名称
        String destinationName = hotel.getName();
        //目的地图片URL
        String imgUrl = hotel.getLargePic();
        //行程开始时间
        String beginTimeStr = DateStrUtil.getDateStr(monthOfYear,dayOfMonth);
        //行程结束时间
        String overTimeStr = DateStrUtil.getDateStr(monthOfYearEnd,dayOfMonthEnd);
        Log.d("attr","开始：" + beginTimeStr + "结束：" + overTimeStr);
        Trip trip = new Trip(destinationId,destinationName,imgUrl,beginTimeStr,overTimeStr,"null weather info","sqchen");
        saveTrip(trip);
    }

    /**
     * 加入行程
     * @param trip
     */
    private void saveTrip(Trip trip) {
        Gson gson = new Gson();
        String tripJson = gson.toJson(trip);
        Log.d("hotel",tripJson);
        AttractionService service = NetManager.getInstance().createWithUrl(AttractionService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(service.saveTripInfo(tripJson),
                new RxSubscriber<Trip>() {
                    @Override
                    protected void _onError(Throwable e) {

                    }

                    @Override
                    protected void _onNext(Trip trip) {

                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
