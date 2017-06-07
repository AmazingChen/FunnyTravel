package edu.fjnu.funnytravel.http;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 对Subscription进行封装
 */

public class RxManager {

    private static RxManager mRxmanager = null;

    private RxManager() {

    }

    /**
     * 获取单例
     * @return
     */
    public synchronized static RxManager getInstance() {
        if(mRxmanager == null) {
            mRxmanager = new RxManager();
        }
        return mRxmanager;
    }

    /**
     * 处理比较简单的请求，HttpResult类只返回了请求是否成功及相应的信息
     * @param observable
     * @param subscriber
     * @return
     */
    public <T> Subscription doUnifySubscribe(Observable<T> observable,
                                         Subscriber<T> subscriber) {
        return observable.map(new Func1<T, T>() {
            @Override
            public T call(T t) {
//                if(httpResult.getError_code() != 0) {
//                    throw new ApiException(httpResult.getReason());
//                }
                return t;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 处理返回结果比较复杂的请求，HttpResultT类中，泛型放具体要处理的请求结果
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> Subscription doSubscribeT(Observable<HttpResultT<T>> observable,
                                         Subscriber<T> subscriber) {
        return observable.map(new Func1<HttpResultT<T>, T>() {
            @Override
            public T call(HttpResultT<T> httpResult) {
                Log.d("RxManager","doSubscribeT is executed");
                Log.d("RxManager",httpResult.toString());
                if(httpResult.getCode() != 1) {    //手动抛出异常，执行onError方法
                    Log.d("RxManager",httpResult.getMsg() + "data:" + httpResult.getData());
                    throw new ApiException(httpResult.getMsg());
                }
                return httpResult.getData();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 在IO线程执行任务，在主线程发送通知，即改变UI等
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> Subscription doSubscribe(Observable<T> observable,
                                          Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 在IO线程执行任务和发送通知
     * @param observable
     * @param subscriber
     * @param <T>
     * @return
     */
    public <T> Subscription doIoSubscribe(Observable<T> observable,
                                          Subscriber<T> subscriber) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(subscriber);
    }

    /**
     * 得到Observable被观察者
     * @param observable
     * @param <T>
     * @return
     */
    public <T> Observable<T> doSubscribe(Observable<T> observable) {
        return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
