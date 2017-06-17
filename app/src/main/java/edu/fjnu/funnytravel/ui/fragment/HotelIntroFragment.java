package edu.sqchen.iubao.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.model.entity.Attraction;
import edu.sqchen.iubao.model.entity.HotelDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelIntroFragment extends Fragment {


    @BindView(R.id.hotel_intro)
    TextView mHotelIntro;

    @BindView(R.id.hotel_basic)
    TextView mHotelBasic;

    @BindView(R.id.hotel_serve)
    TextView mHotelServe;

    @BindView(R.id.hotel_room_facility)
    TextView mHotelRoomFacility;

    @BindView(R.id.hotel_policy)
    TextView mHotelPolicy;

    @BindView(R.id.hotel_change)
    TextView mHotelChange;

    @BindView(R.id.hotel_foodfunf)
    TextView mHotelFoodfunf;

    @BindView(R.id.hotel_entertainment)
    TextView mHotelEntertainment;

    @BindView(R.id.hotel_traffic)
    TextView mHotelTraffic;

    Unbinder unbinder;

    private Intent mIntent;

    private String trafficStr;

    public HotelIntroFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hotel_intro, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        getHotelDetailData();

        return view;
    }

    private void init() {
        mIntent = getActivity().getIntent();
        Log.d("hotel","酒店编号:" + mIntent.getStringExtra("selectedHId"));
        trafficStr = "";
    }

    private void getHotelDetailData() {
        AttractionService attractionService = NetManager.getInstance().create(AttractionService.class);
        RxManager.getInstance()
                .doUnifySubscribe(
                        attractionService.getHotelDetailData(
                                Integer.parseInt(mIntent.getStringExtra("selectedHId")),
                                "bc9350399df04805b88acb49a07e45e2")
                        , new RxSubscriber<HotelDetail>() {
                            @Override
                            protected void _onError(Throwable e) {

                            }

                            @Override
                            protected void _onNext(HotelDetail hotelDetail) {
                                if(hotelDetail != null) {
                                    bindDataView(hotelDetail);
                                }
                            }
                        });
    }

    private void bindDataView(HotelDetail hotelDetail) {
        HotelDetail.ResultBean hotelResult = hotelDetail.getResult();
        Log.d("hotel","intro:" + hotelResult.getIntro());
        if (hotelResult.getIntro().isEmpty() || hotelResult.getIntro().equals("")) {
            mHotelIntro.setText("无");
        } else {
            mHotelIntro.setText(hotelResult.getIntro());
        }

        if(hotelResult.getBasic().isEmpty() || hotelResult.getBasic().equals("")) {
            mHotelBasic.setText("无");
        } else {
            mHotelBasic.setText(hotelResult.getBasic());
        }

        if(hotelResult.getServe().isEmpty() || hotelResult.getServe().equals("")) {
            mHotelServe.setText("无");
        } else {
            mHotelServe.setText(hotelResult.getServe());
        }

        if(hotelResult.getRoomFacility().isEmpty() || hotelResult.getRoomFacility().equals("")) {
            mHotelRoomFacility.setText("无");
        } else {
            mHotelRoomFacility.setText(hotelResult.getRoomFacility());
        }

        if(hotelResult.getPolicy().isEmpty() || hotelResult.getPolicy().equals("")) {
            mHotelPolicy.setText("无");
        } else {
            mHotelPolicy.setText(hotelResult.getPolicy());
        }

        if(hotelResult.getChange().isEmpty() || hotelResult.getChange().equals("")) {
            mHotelChange.setText("无");
        } else {
            mHotelChange.setText(hotelResult.getChange());
        }

        if(hotelResult.getFoodfunf().isEmpty() || hotelResult.getFoodfunf().equals("")) {
            mHotelFoodfunf.setText("无");
        } else {
            mHotelFoodfunf.setText(hotelResult.getFoodfunf());
        }

        if(hotelResult.getEntertainment().isEmpty() || hotelResult.getEntertainment().equals("")) {
            mHotelEntertainment.setText("无");
        } else {
            mHotelEntertainment.setText(hotelResult.getEntertainment());
        }

        for (int i = 0; i < hotelResult.getTrafficeList().size(); i++) {
            if(!hotelResult.getTrafficeList().get(i).getTraffic().isEmpty()
                    && !hotelResult.getTrafficeList().get(i).getTraffic().equals("")) {
                trafficStr += hotelResult.getTrafficeList().get(i).getTraffic() + "\n";
            }
        }
        if(trafficStr.isEmpty() || trafficStr.equals("")) {
            mHotelTraffic.setText("无");
        } else {
            mHotelTraffic.setText(trafficStr);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
