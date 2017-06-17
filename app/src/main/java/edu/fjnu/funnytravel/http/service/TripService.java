package edu.sqchen.iubao.http.service;

import java.util.List;

import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.HttpResultT;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.model.entity.WeatherData;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/2.
 */

public interface TripService {

    /**
     * 获取天气信息
     * @param key       appkey
     * @param location  位置
     * @param days      天数，免费用户只能获取最近3天的数据
     * @return
     */
    @GET("weather/daily.json")
    Observable<WeatherData> getWeatherResult(@Query("key") String key,
                                             @Query("location") String location,
                                             @Query("days") int days);

    /**
     * 获取行程数据
     * @param username  用户名，根据用户名获取用户的所有行程数据
     * @return
     */
    @FormUrlEncoded
    @POST("trip/gettrip")
    Observable<HttpResultT<List<Trip>>> getTripList(@Field("username") String username);


    /**
     * 删除选中项行程
     * @param tripId    行程ID
     * @return
     */
    @FormUrlEncoded
    @POST("trip/deletetrip")
    Observable<HttpResult> deleteTrip(@Field("tripId") int tripId);

    /**
     * 修改行程
     * @param tripId    行程ID，确定修改的是哪个行程
     * @param tripJson  行程对象的JSON字符串，将修改后的整个对象发送到后台
     * @return
     */
    @FormUrlEncoded
    @POST("trip/updatetrip")
    Observable<HttpResultT<Trip>> updateTrip(@Field("tripId") int tripId,@Field("tripJson") String tripJson);
}
