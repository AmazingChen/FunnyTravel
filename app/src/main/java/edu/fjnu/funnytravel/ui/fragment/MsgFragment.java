package edu.sqchen.iubao.ui.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import edu.sqchen.iubao.R;
import edu.sqchen.iubao.adapter.ChatListAdapter;
import edu.sqchen.iubao.model.entity.Chat;
import edu.sqchen.iubao.widget.ScrollListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MsgFragment extends Fragment {


    @BindView(R.id.tool_bar_msg)
    Toolbar mToolBarMsg;

    Unbinder unbinder;

    @BindView(R.id.hotel_list_view)
    ScrollListView mHotelListView;

    private List<Chat> mChatList;

    private ChatListAdapter mChatListAdapter;

    public MsgFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        unbinder = ButterKnife.bind(this, view);
        initToolbar();
        initListView();

        return view;
    }

    private void initToolbar() {
        mToolBarMsg.setTitle("消息");
        mToolBarMsg.setTitleTextColor(Color.WHITE);
    }

    private void initListView() {
        mChatList = new ArrayList<>();
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());
        mChatList.add(new Chat());

        mChatListAdapter = new ChatListAdapter(getContext(),mChatList);
        mHotelListView.setAdapter(mChatListAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
