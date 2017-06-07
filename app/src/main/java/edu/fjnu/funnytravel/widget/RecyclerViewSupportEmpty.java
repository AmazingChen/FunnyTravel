package edu.fjnu.funnytravel.widget;

/**
 * Created by Administrator on 2017/5/11.
 */

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by shenminjie on 2016/10/19.
 * 用来演示如何在RecyclerView里面添加setEmptyView
 */

public class RecyclerViewSupportEmpty extends RecyclerView {

    /**
     * 当数据为空时展示的View
     */
    private View mEmptyView;

    /**
     * 创建一个观察者
     * 为什么要在onChanged里面写？
     * 因为每次notifyDataChanged的时候，系统都会调用这个观察者的onChange函数
     * 我们大可以在这个观察者这里判断我们的逻辑，就是显示隐藏
     */
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {


        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            //这种写发跟之前我们之前看到的ListView的是一样的，判断数据为空否，在进行显示或者隐藏
            if (adapter != null && mEmptyView != null) {
                if (adapter.getItemCount() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                    RecyclerViewSupportEmpty.this.setVisibility(View.GONE);
                } else {
                    mEmptyView.setVisibility(View.GONE);
                    RecyclerViewSupportEmpty.this.setVisibility(View.VISIBLE);
                }
            }

        }
    };

    public RecyclerViewSupportEmpty(Context context) {
        super(context);
    }

    public RecyclerViewSupportEmpty(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewSupportEmpty(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * 依赖注入
     *
     * @param emptyView 展示的空view
     */
    public void setEmptyView(View emptyView) {
        mEmptyView = emptyView;
    }


    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            //这里用了观察者模式，同时把这个观察者添加进去
            adapter.registerAdapterDataObserver(emptyObserver);
        }
        //当setAdapter的时候也调一次
        emptyObserver.onChanged();
    }
}