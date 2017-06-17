package edu.sqchen.iubao.http.service;

import edu.sqchen.iubao.http.HttpResult;
import edu.sqchen.iubao.http.HttpResultT;
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

public interface UserService {

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Observable<HttpResultT<User>> userRegister(@Field("username") String username,
                                               @Field("password") String password);

    /**
     * 登录
     * @param userInfo  用户信息
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<HttpResultT<User>> userLogin(@Field("userInfo") String userInfo);

    /**
     * 取消收藏
     * @param username
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("collection/delete")
    Observable<HttpResult> deleteCollection(@Field("username") String username,
                                            @Field("id") String id);
}
