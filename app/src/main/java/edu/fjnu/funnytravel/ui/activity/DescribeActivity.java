package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.DescribeListAdapter;
import edu.sqchen.iubao.model.entity.AttractionDetail;

public class DescribeActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_describe)
    Toolbar mToolBarDescribe;

    @BindView(R.id.describe_list_view)
    ListView mDescribeListView;

    private Intent mIntent;

    private AttractionDetail mAttractionDetail;

    private String mName;

    private DescribeListAdapter mDescribeListAdapter;

    private List<AttractionDetail.ResultBean> mDetailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
        ButterKnife.bind(this);
        Log.d("DETAIL","describe oncreate");
        init();
        initToolbar();
        initListView();
    }

    private void init() {
        mDetailList = new ArrayList<>();

        mIntent = getIntent();
        mAttractionDetail = (AttractionDetail) mIntent.getSerializableExtra("ATTRACTION_DETAIL");
        mName = mIntent.getStringExtra("ATTRACTION_NAME");
    }

    private void initToolbar() {
        mToolBarDescribe.setTitle("景点简介");
        mToolBarDescribe.setNavigationIcon(R.drawable.ic_back);
        mToolBarDescribe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToolBarDescribe.setTitleTextColor(Color.WHITE);
    }

    private void initListView() {
        mDetailList = mAttractionDetail.getResult();
        mDescribeListAdapter = new DescribeListAdapter(this,mDetailList);
        mDescribeListView.setAdapter(mDescribeListAdapter);
    }
}
