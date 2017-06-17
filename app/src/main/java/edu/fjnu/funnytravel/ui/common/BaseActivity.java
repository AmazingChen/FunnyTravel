package edu.sqchen.iubao.ui.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import edu.sqchen.iubao.R;
import edu.sqchen.iubao.presenter.BasePresenter;

public abstract class BaseActivity<V,T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        //创建Presenter
        mPresenter = createPresenter();
        //关联View
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除关联
        mPresenter.detachView();
    }

    protected abstract T createPresenter();
}
