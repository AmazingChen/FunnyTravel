package edu.sqchen.iubao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.WeatherRVAdapter;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.TripService;
import edu.sqchen.iubao.model.entity.WeatherData;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherFragment extends Fragment {

    Unbinder unbinder;

    @BindView(R.id.weather_recyclerview)
    RecyclerView mWeatherRecyclerview;

    private WeatherRVAdapter mWeatherRVAdapter;

    private List<WeatherData.ResultsBean.DailyBean> mWeatherList = new ArrayList<>();

    public WeatherFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("create","weatherfragment create");
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);
        initRecyclerView();
        getWeatherResult();


        return view;
    }

    public void initRecyclerView() {
//        mWeatherRVAdapter = new WeatherRVAdapter(mWeatherList);
        mWeatherRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        mWeatherRecyclerview.setHasFixedSize(true);
        mWeatherRecyclerview.setItemAnimator(new DefaultItemAnimator());
//        mWeatherRecyclerview.setAdapter(mWeatherRVAdapter);
//        mWeatherRVAdapter.setOnItemClickListener(new WeatherRVAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(final View view, int position) {
//                view.animate()
//                        .translationZ(8F)
//                        .setDuration(300)
//                        .setListener(new AnimatorListenerAdapter() {
//                            @Override
//                            public void onAnimationEnd(Animator animation) {
//                                super.onAnimationEnd(animation);
//                                view.animate()
//                                        .translationZ(1f)
//                                        .setDuration(500)
//                                        .start();
//                            }
//                        });
//            }
//        });

    }

    private void getWeatherResult() {
        TripService tripService = NetManager.getInstance().createWithUrl(TripService.class, ApiUrl.WEATHER_BASE_URL);
        RxManager.getInstance()
                .doUnifySubscribe(
                        tripService.getWeatherResult(
                                "f7ga61n50ffcbt28", "漳州",
                                3)
                        , new RxSubscriber<WeatherData>() {
                            @Override
                            protected void _onError(Throwable e) {

                            }

                            @Override
                            protected void _onNext(WeatherData weatherData) {
                                Log.d("weather",weatherData.toString());
                                Log.d("weather","weatherData:" + weatherData.getResults().get(0).getLast_update());
                                mWeatherList = weatherData.getResults().get(0).getDaily();
                                mWeatherRVAdapter = new WeatherRVAdapter(mWeatherList);
                                mWeatherRecyclerview.setAdapter(mWeatherRVAdapter);
                            }
                        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
