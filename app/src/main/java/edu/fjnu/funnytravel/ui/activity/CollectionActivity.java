package edu.sqchen.iubao.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.CollectionListAdapter;
import edu.sqchen.iubao.http.ApiUrl;
import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.NetManager;
import edu.sqchen.iubao.http.RxManager;
import edu.sqchen.iubao.http.RxSubscriber;
import edu.sqchen.iubao.http.service.AttractionService;
import edu.sqchen.iubao.http.service.UserService;
import edu.sqchen.iubao.model.entity.AttractionComment;
import edu.sqchen.iubao.model.entity.Collection;
import edu.sqchen.iubao.ui.util.DateStrUtil;

public class CollectionActivity extends AppCompatActivity {

    @BindView(R.id.tool_bar)
    Toolbar mToolBar;
    @BindView(R.id.collection_list_view)
    ListView mCollectionListView;
    @BindView(R.id.lin_empty_view)
    LinearLayout mLinEmptyView;

    private List<Collection> mCollectionList;
    private CollectionListAdapter mAdapter;
    private int selectPosition;

    //取消收藏
    private static final int ITEM_CANCEL = Menu.FIRST;
    //分享
    private static final int ITEM_SHARE = Menu.FIRST + 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        initToolbar();
        initListView();
        getCollections();
    }

    private void initToolbar() {
        mToolBar.setTitle("我的收藏");
        mToolBar.setTitleTextColor(Color.WHITE);
        mToolBar.setNavigationIcon(R.drawable.ic_back);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListView() {
        mCollectionList = new ArrayList<>();
        mCollectionListView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) menuInfo;
        selectPosition = adapterContextMenuInfo.position;
        menu.add(0,ITEM_CANCEL,0,"取消收藏");
        menu.add(0,ITEM_SHARE,0,"分享");
    }

    @Override
    public boolean onContextItemSelected(MenuItem menuItem) {
        Intent mIntent = null;
        switch(menuItem.getItemId()) {
            //收藏景点
            case ITEM_CANCEL:
                Log.d("collection","click cancel");
                deleteCollections(mCollectionList.get(selectPosition));
                break;
            //评价景点
            case ITEM_SHARE:
                Log.d("collection","click share");
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 获取收藏数据
     */
    private void getCollections() {
        AttractionService service = NetManager.getInstance().createWithUrl(AttractionService.class, ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribeT(service.getCollections("sqchen"),
                new RxSubscriber<List<Collection>>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(), "收藏数据获取失败！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(List<Collection> collections) {
                        if(collections.size() == 0) {
                            Toast.makeText(getContext(), "暂无收藏数据！", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mCollectionList = collections;
                        Log.d("collection","chenggong");
                        mAdapter = new CollectionListAdapter(getContext(),mCollectionList);
                        mCollectionListView.setAdapter(mAdapter);
                        Toast.makeText(getContext(), "收藏数据获取成功！", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * 取消收藏
     * @param collection
     */
    private void deleteCollections(Collection collection) {
        String userName = collection.getUsername();
        String id = collection.getId();
        Log.d("collection",userName + "ID是：" + id);
        UserService service = NetManager.getInstance().createWithUrl(UserService.class,ApiUrl.PHP_USER_BASE_URL);
        RxManager.getInstance().doSubscribe(service.deleteCollection(userName, id),
                new RxSubscriber<HttpResult>() {
                    @Override
                    protected void _onError(Throwable e) {
                        Toast.makeText(getContext(), "取消收藏失败！请检查网络设置！", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected void _onNext(HttpResult httpResult) {
                        if(httpResult.getCode() == 1) {
                            Log.d("collection","刪除前大小:" +mCollectionList.size());
                            mCollectionList.remove(selectPosition);
                            Log.d("collection","刪除后大小:" +mCollectionList.size());
                            mAdapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "取消收藏成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "取消收藏失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
