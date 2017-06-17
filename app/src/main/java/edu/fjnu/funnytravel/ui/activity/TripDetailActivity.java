package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.TripService;
import edu.sqchen.iubao.model.entity.Attraction;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.ui.util.DateStrUtil;

public class TripDetailActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tool_bar_trip_detail)
    Toolbar mToolBarTripDetail;
    @BindView(R.id.trip_destination)
    TextView mTripDestination;
    @BindView(R.id.trip_begin_time)
    TextView mTripBeginTime;
    @BindView(R.id.trip_over_time)
    TextView mTripOverTime;
    @BindView(R.id.trip_notice_time)
    TextView mTripNoticeTime;
    @BindView(R.id.trip_note)
    TextView mTripNote;
    @BindView(R.id.trip_image)
    ImageView mTripImage;

    private Intent mIntent;
    private Trip selectTrip;
    private static final String SELECT_TRIP = "SELECT_TRIP";
    private Calendar mCalendar;
    private DatePickerDialog mDatePicker;

    //布局参考1：https://github.com/ksoichiro/Android-ObservableScrollView
    //布局参考2：https://github.com/Gnod/ParallaxListView
    //提醒时间参考：https://github.com/fenjuly/ToggleExpandLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_detail);
        ButterKnife.bind(this);
        initToolbar();
        initTripData();
        initDateDialog();
        initDateText();
    }

    public void initToolbar() {
        mToolBarTripDetail.setNavigationIcon(R.drawable.ic_back);
        mToolBarTripDetail.setTitle("详情");
        mToolBarTripDetail.setTitleTextColor(Color.WHITE);
        mToolBarTripDetail.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initTripData() {
        mIntent = getIntent();
        selectTrip = (Trip) mIntent.getSerializableExtra(SELECT_TRIP);
        if(selectTrip != null) {
            mTripDestination.setText(selectTrip.getDestinationName());
            mTripBeginTime.setText(selectTrip.getBeginTime());
            mTripOverTime.setText(selectTrip.getOverTime());
            Glide.with(this).load(selectTrip.getImageUrl()).into(mTripImage);
        }
    }

    private void initDateText() {
        mTripBeginTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(getFragmentManager(),"DatePicker");
            }
        });
        mTripOverTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatePicker.show(getFragmentManager(),"DatePicker");
            }
        });
    }

    /**
     * 初始化日期选择对话框
     */
    private void initDateDialog() {
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
        //行程开始时间
        String beginTimeStr = DateStrUtil.getDateStr(monthOfYear,dayOfMonth);
        //行程结束时间
        String overTimeStr = DateStrUtil.getDateStr(monthOfYearEnd,dayOfMonthEnd);
        Log.d("attr","开始：" + beginTimeStr + "结束：" + overTimeStr);
        selectTrip.setBeginTime(beginTimeStr);
        selectTrip.setOverTime(overTimeStr);
        //修改行程
        updateTrip(selectTrip);
    }

    /**
     * 修改行程
     * @param trip
     */
    private void updateTrip(Trip trip) {
        Gson gson = new Gson();
        String tripJson = gson.toJson(trip);
        TripService tripService = NetManager.getInstance().createWithUrl(TripService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(
                tripService.updateTrip(trip.getTripId(), tripJson),
                new RxSubscriber<Trip>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(),"修改失败！",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(Trip trip) {
                        mTripBeginTime.setText(trip.getBeginTime());
                        mTripOverTime.setText(trip.getOverTime());
                        Toast.makeText(getContext(),"修改成功！",Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
