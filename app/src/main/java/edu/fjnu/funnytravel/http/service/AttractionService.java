package edu.sqchen.iubao.http.service;

import java.util.List;

import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.HttpResultT;
import edu.sqchen.iubao.model.entity.AttractionComment;
import edu.sqchen.iubao.model.entity.AttractionDetail;
import edu.sqchen.iubao.model.entity.Collection;
import edu.sqchen.iubao.model.entity.HotelDetail;
import edu.sqchen.iubao.model.entity.ResultData;
import edu.sqchen.iubao.model.entity.Strategy;
import edu.sqchen.iubao.model.entity.Ticket;
import edu.sqchen.iubao.model.entity.Trip;
import edu.sqchen.iubao.model.entity.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/6/2.
 */

public interface AttractionService {

    /**
     * 获取城市列表
     * @param key
     * @return
     */
    @GET("cityList")
    Observable<ResultData> getCityInfo(@Query("key") String key);

    /**
     * 获取景点列表
     * @param cid
     * @param page
     * @param key
     * @return
     */
    @GET("scenery")
    Observable<ResultData> getAttractionsInfo(@Query("cid") int cid,
                                              @Query("page") int page,
                                              @Query("key") String key);

    /**
     * 获取景点详情
     * @param sid   景点ID
     * @param key
     * @return
     */
    @GET("GetScenery")
    Observable<AttractionDetail> getAttractionDetailResult(@Query("sid") String sid,
                                                           @Query("key") String key);

    /**
     * 获取酒店列表
     * @param cityId
     * @param page
     * @param ley
     * @return
     */
    @GET("HotelList")
    Observable<ResultData> getHotelData(@Query("cityId") int cityId,
                                        @Query("page") int page,
                                        @Query("key") String ley );

    /**
     * 获取酒店详情数据
     * @param hid
     * @param key
     * @return
     */
    @GET("GetHotel")
    Observable<HotelDetail> getHotelDetailData(@Query("hid") int hid,
                                               @Query("key") String key);

    /**
     * 获取景点门票信息
     * @param sid   景点ID
     * @param key   APPKEY
     * @return
     */
    @GET("TicketInfo")
    Observable<Ticket> getTicketInfo(@Query("sid") String sid,
                                     @Query("key") String key);

    /**
     * 保存行程信息
     * @param tripJson  行程对象JSON字符串
     * @return
     */
    @FormUrlEncoded
    @POST("trip")
    Observable<HttpResultT<Trip>> saveTripInfo( @Field("tripJson") String tripJson );

    /**
     * 对景点进行评价
     * @param commentJson   评价对象JSON字符串
     * @return
     */
    @FormUrlEncoded
    @POST("comment/addcomment")
    Observable<HttpResult> addComment(@Field("commentJson") String commentJson);

    /**
     * 获取景点评价列表数据
     * @param attractionId  根据景点ID获取所有评价
     * @return
     */
    @FormUrlEncoded
    @POST("comment/getcomment")
    Observable<HttpResultT<List<AttractionComment>>> getCommentList(@Field("attractionId") String attractionId);

    /**
     * 收藏景点
     * @param username  收藏者用户名
     * @param id        被收藏者id，包括酒店id和景点id
     * @return
     */
    @FormUrlEncoded
    @POST("collection/add")
    Observable<HttpResult> addCollection(@Field("username") String username,
                                         @Field("id") String id);

    @FormUrlEncoded
    @POST("collection/get")
    Observable<HttpResultT<List<Collection>>> getCollections(@Field("username") String username);

 }
