package edu.sqchen.iubao.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.StrategyListAdapter;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.model.entity.Collection;
import edu.sqchen.iubao.model.entity.Strategy;

public class PersonalStrategy extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBarFind;
    @BindView(R.id.personal_strategy_list_view)
    ListView mStrategyListView;
    @BindView(R.id.lin_empty_view)
    LinearLayout mLinEmptyView;

    private List<Strategy> mStrategieList;
    private StrategyListAdapter mListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_strategy);
        ButterKnife.bind(this);
        initToolbar();
        initListView();
    }

    /**
     * 初始化标题栏
     */
    private void initToolbar() {
        mToolBarFind.setTitle("我的攻略");
        mToolBarFind.setTitleTextColor(Color.WHITE);
        mToolBarFind.setNavigationIcon(R.drawable.ic_back);
        mToolBarFind.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 初始化ListView
     */
    private void initListView() {
        mStrategieList = new ArrayList<>();
        mStrategieList.add(new Strategy());
        mStrategieList.add(new Strategy());
        mStrategieList.add(new Strategy());
        mStrategieList.add(new Strategy());
        mStrategyListView.setEmptyView(mLinEmptyView);
        mListAdapter = new StrategyListAdapter(this, mStrategieList);
        mStrategyListView.setAdapter(mListAdapter);
    }

}
