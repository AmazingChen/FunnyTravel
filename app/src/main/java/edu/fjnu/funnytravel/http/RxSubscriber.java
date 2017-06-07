package edu.fjnu.funnytravel.http;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import edu.fjnu.funnytravel.app.MyApplication;
import rx.Subscriber;

/**
 * 封装Subscriber，对错误统一处理
 *
 */

public abstract class RxSubscriber<T> extends Subscriber<T>{

    private Context mContext;

    public RxSubscriber() {
        mContext = MyApplication.getInstance();
    }

    @Override
    public void onCompleted() {
        Log.d("subscriber","onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof IOException) {
            Log.d("subscriber", e.toString() + "网络连接异常！");
        } else {
            Log.d("subscriber", e.toString());
        }
        _onError(e);
    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    public Context getContext() {
        return mContext;
    }

    protected abstract void _onError(Throwable e);

    protected abstract void _onNext(T t);

}
