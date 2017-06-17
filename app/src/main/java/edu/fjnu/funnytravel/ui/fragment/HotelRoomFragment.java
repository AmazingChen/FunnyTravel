package edu.sqchen.iubao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.sqchen.iubao.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotelRoomFragment extends Fragment {


    public HotelRoomFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hotel_room, container, false);
    }

}
