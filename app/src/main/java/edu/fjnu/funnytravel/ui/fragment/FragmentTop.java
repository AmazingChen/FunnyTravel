package edu.sqchen.iubao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.devspark.robototextview.widget.RobotoTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.model.entity.Travel;
import edu.sqchen.iubao.ui.activity.InfoActivity;

/**
 * Created by Administrator on 2017/6/5.
 */

public class FragmentTop extends Fragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";

    Travel travel;
    @BindView(R.id.image)
    ImageView mImage;
    @BindView(R.id.title)
    RobotoTextView mTitle;
    Unbinder unbinder;


    public static FragmentTop newInstance(Travel travel) {
        Bundle args = new Bundle();
        FragmentTop fragmentTop = new FragmentTop();
        args.putParcelable(ARG_TRAVEL, travel);
        fragmentTop.setArguments(args);
        return fragmentTop;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            travel = args.getParcelable(ARG_TRAVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_front, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (travel != null) {
            mImage.setImageResource(travel.getImage());
            mTitle.setText(travel.getName());
        }
    }

    private void startInfoAtcivty(View view, Travel travel) {
        FragmentActivity activity = getActivity();
        ActivityCompat.startActivity(activity, InfoActivity.newInstance(activity, travel),
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        activity,
                        new Pair<View, String>(view, "transition_image")).toBundle());
    }
}