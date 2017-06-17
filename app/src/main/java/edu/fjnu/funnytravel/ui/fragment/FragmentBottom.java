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
public class FragmentBottom extends Fragment {


    public FragmentBottom() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bottom, container, false);
    }

    public static Fragment newInstance() {
        return new FragmentBottom();
    }

}
