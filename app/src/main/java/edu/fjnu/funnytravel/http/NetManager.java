package edu.sqchen.iubao.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 对请求过程进行封装
 */

public class NetManager {

    private static NetManager mNetManger = null;

    private static Retrofit retrofit = null;

    private static int DEFAULT_TIMEOUT = 15;

    /**
     * 创建实例
     */
    private NetManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create()) //表示返回的网络请求结果转换成JSON格式
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())   //结合RxJava时需要调用此方法
                .baseUrl(ApiUrl.TRAVEL_BASE_URL)
                .client(client)
                .build();
    }

    /**
     * 获取实例
     *
     * @return
     */
    public synchronized static NetManager getInstance() {
        if(mNetManger == null) {
            mNetManger = new NetManager();
        }
        return mNetManger;
    }

    /**
     * 创建Service实例
     * @param service
     * @param <S>
     * @return
     */
    public <S> S create(Class<S> service) {
        return retrofit.create(service);
    }

    /**
     * 当返回的数据不是JSON格式时，获取原始格式的值
     *
     * @param service
     * @param <S>
     * @return
     */
    public <S> S createString(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())  //表示返回原始的字符串数据
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(ApiUrl.TRAVEL_BASE_URL)
                .build();
        return retrofit.create(service);
    }

    /**
     * 当BASE_URL不一致时调用这个方法，解析接口中的BASE_URL
     *
     * @param service
     * @param <S>
     * @return
     */
    public <S> S createWithUrl(Class<S> service,String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(service);
    }

    /**
     * 解析接口中的BASE_URL,解决BASE_URL不一致的问题
     *
     * @param service
     * @param <S>
     * @return
     */
    private <S> String getBaseUrl(Class<S> service) {
        try {
            Field field = service.getField("TRAVEL_BASE_URL");
            return (String) field.get(service);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
