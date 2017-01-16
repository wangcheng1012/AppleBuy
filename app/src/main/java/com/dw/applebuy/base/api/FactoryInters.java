package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.ResultData;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.add.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.rxmvp.api.GsonConverter.ResultResponse;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 *
 */
public interface FactoryInters  {

    @FormUrlEncoded
    @POST("app/common/getRegisterVerifyCode")
    Observable<HttpStateResult<List>> getRegisterVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/common/getForgetPasswordVerifyCode")
    Observable<HttpStateResult<List>> getForgetPasswordVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/user/register")
    Observable<HttpStateResult<List>> register(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/forgetPassword")
    Observable<HttpStateResult<List>> forgetPassword(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/login")
    Observable<HttpStateResult<Object>> login(@Field("mobile") String phone, @Field("password") String psw);

    @FormUrlEncoded
    @POST("app/coupon/getCoupon")
    Observable<HttpStateResult<ResultData<Coupon>>> getCoupon(@Field("sessionid")String sessionid, @Field("page") int page, @Field("sort_type")int sort_type,@Field("status")int status);

    /**
     * 获取优惠券类型
     * @param sessionid
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCouponCategory")
    Observable<HttpStateResult<List<YouhuiQuanType>>> getCouponCategory(@Field("sessionid")String sessionid);

    /**
     * 添加和编辑优惠券
     * @param arrayMap
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/save")
    Observable<ResultResponse> addYouHui(@FieldMap ArrayMap<String, Object> arrayMap);



}
