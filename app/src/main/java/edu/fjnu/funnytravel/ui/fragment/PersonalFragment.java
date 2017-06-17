package edu.sqchen.iubao.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalFragment extends Fragment {

    @BindView(R.id.tool_bar_personal)
    Toolbar mToolBarPersonal;
    Unbinder unbinder;

    public PersonalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_personal, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        return view;
    }

    private void initToolbar() {
        mToolBarPersonal.setTitle("我的");
        mToolBarPersonal.setTitleTextColor(Color.WHITE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
