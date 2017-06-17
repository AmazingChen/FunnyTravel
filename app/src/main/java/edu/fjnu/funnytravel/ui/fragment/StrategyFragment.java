package edu.sqchen.iubao.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.StrategyListAdapter;
import edu.sqchen.iubao.model.entity.Strategy;
import edu.sqchen.iubao.widget.ScrollListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class StrategyFragment extends Fragment {
    @BindView(R.id.strategy_list_view)
    ScrollListView mStrategyListView;

    Unbinder unbinder;
    @BindView(R.id.lin_empty_view)
    LinearLayout mLinEmptyView;

    private List<Strategy> mStrategies;

    private StrategyListAdapter mListAdapter;


    public StrategyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_strategy, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListView();

        return view;
    }

    private void initListView() {
        mStrategies = new ArrayList<>();
        mListAdapter = new StrategyListAdapter(getContext(), mStrategies);
        mStrategyListView.setAdapter(mListAdapter);
        mStrategyListView.setEmptyView(mLinEmptyView);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
