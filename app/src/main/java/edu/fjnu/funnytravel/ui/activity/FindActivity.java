package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.PicListAdapter;

public class FindActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar_find)
    Toolbar mToolBarFind;
    @BindView(R.id.find_list_view)
    ListView mFindListView;
    private PicListAdapter mListAdapter;

    private String[] picUrls;

    private int selectPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Bundle bundle = getIntent().getExtras();
        picUrls = bundle.getStringArray("PIC_URLS");
        selectPosition = Integer.parseInt(getIntent().getStringExtra("SELECT_POSITION"));
        mListAdapter = new PicListAdapter(this,picUrls);
        mFindListView.setAdapter(mListAdapter);
        mFindListView.setSelection(selectPosition);

        mToolBarFind.setNavigationIcon(R.drawable.ic_back);
        mToolBarFind.setTitle("发现");
        mToolBarFind.setTitleTextColor(getResources().getColor(R.color.blue));
        mToolBarFind.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
