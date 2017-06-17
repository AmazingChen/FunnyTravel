package edu.sqchen.iubao.ui.common;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.sqchen.iubao.R;
import edu.sqchen.iubao.presenter.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment<V,T extends BasePresenter<V>> extends Fragment {

    protected T mPresenter;

    public BaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);

        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    protected abstract T createPresenter();

}
