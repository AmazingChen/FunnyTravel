package edu.sqchen.iubao.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.AttractionCommentListAdapter;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.model.entity.AttractionComment;
import edu.sqchen.iubao.widget.ScrollListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttractionCommentFragment extends Fragment {


    @BindView(R.id.comment_list_view)
    ScrollListView mCommentListView;
    @BindView(R.id.lin_empty_view)
    LinearLayout mLinEmptyView;
    @BindView(R.id.comment_refresh)
    SwipeRefreshLayout mCommentRefresh;

    private AttractionCommentListAdapter mCommentListAdapter;

    private List<AttractionComment> mCommentList;

    Unbinder unbinder;

    private Intent mIntent;

    private String attractionId;

    private SwipeRefreshLayout.OnRefreshListener mRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            if (mCommentList.size() != 0 && mCommentList != null) {
                mCommentList.clear();
                getCommentList(attractionId);
            } else {
                getCommentList(attractionId);
            }
        }
    };

    public AttractionCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_attraction_comment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initListView();
        mCommentRefresh.post(new Runnable() {
            @Override
            public void run() {
                mCommentRefresh.setRefreshing(true);
            }
        });
        mRefreshListener.onRefresh();
        mIntent = getActivity().getIntent();
        if (mIntent != null) {
            attractionId = mIntent.getStringExtra("selectedSId");
//            getCommentList(attractionId);
        }

        return view;
    }

    private void initListView() {
        mCommentRefresh.setProgressBackgroundColorSchemeColor(Color.WHITE);
        mCommentRefresh.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        mCommentRefresh.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        mCommentRefresh.setOnRefreshListener(mRefreshListener);

        mCommentList = new ArrayList<>();
        mCommentListView.setEmptyView(mLinEmptyView);

    }


    /**
     * 获取评价列表
     *
     * @param attractionId 根据景点ID获取所有评价
     */
    private void getCommentList(String attractionId) {
        Log.d("comment","景点ID是：" + attractionId);
        AttractionService service = NetManager.getInstance().createWithUrl(AttractionService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(service.getCommentList("26225"),
                new RxSubscriber<List<AttractionComment>>() {
                    @Override
                    protected void _onError(Throwable e) {
                        mCommentRefresh.setRefreshing(false);
                        Toast.makeText(getContext(),"获取失败，请检查网络设置!",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(List<AttractionComment> comments) {
                        Toast.makeText(getContext(),"获取成功！！" + comments.size(),Toast.LENGTH_SHORT).show();
                        mCommentRefresh.setRefreshing(false);
                        mCommentList = comments;
                        mCommentListAdapter = new AttractionCommentListAdapter(getContext(), mCommentList);
                        mCommentListView.setAdapter(mCommentListAdapter);
//                        mCommentListAdapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
