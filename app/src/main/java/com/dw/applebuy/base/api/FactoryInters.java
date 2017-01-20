package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrder;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityAreaRequest;
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.rxmvp.bean.ResultResponse;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 */
public interface FactoryInters {

    @FormUrlEncoded
    @POST("app/common/getRegisterVerifyCode")
    Observable<HttpStateResult<List>> getRegisterVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/common/getForgetPasswordVerifyCode")
    Observable<HttpStateResult<List>> getForgetPasswordVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/common/getChangeMobileVerifyCode")
    Observable<HttpStateResult<List>> getChangeMobileVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/user/register")
    Observable<HttpStateResult<List>> register(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/forgetPassword")
    Observable<HttpStateResult<List>> forgetPassword(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/login")
    Observable<HttpStateResult<LoginBack>> login(@Field("mobile") String phone, @Field("password") String psw);

    /**
     * @param page      分页
     * @param sort_type 1-添加时间 2-销量 3-销量
     * @param status    2 -展示中 3-已下架
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCoupon")
    Observable<HttpStateResult<Coupon[]>> getCoupon(@Field("page") int page, @Field("sort_type") int sort_type, @Field("status") int status);

    /**
     * 获取优惠券类型
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/coupon/getCouponCategory")
    Observable<HttpStateResult<List<YouhuiQuanType>>> getCouponCategory(/*@Field("sessionid") String sessionid*/);

    /**
     * 添加和编辑优惠券
     *
     * @param arrayMap
     * @return
     */
    @Multipart
    @POST("app/coupon/save")
    Observable<ResultResponse> addYouHui(@PartMap ArrayMap<String, RequestBody> arrayMap, @Part MultipartBody.Part photo);

    /**
     * 更换手机
     *
     * @param phone
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/changeMobile")
    Observable<ResultResponse> changePhone(@Field("mobile") String phone, @Field("code") String verifyCode);

    /**
     * 获取关于我们
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/Common/AboutUs")
    Observable<HttpStateResult<AboutUsModel>> getAboutUs();

    /**
     * 省市区
     *
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<HttpStateResult<List<ProvinceCityArea>>> getProvinceCityArea(@Url String url, @Field("province_id") int province_id, @Field("city_id") int city_id);

    /**
     *
     * 获取商家经营范围-分类
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/user/getAllCategory")
    Observable<HttpStateResult<List<BusinessScope>>> getAllCategory();

    /**
     *
     * @param code  加密后的优惠卷code
     * @param page  分页
     * @param status 0-全部 1-未消费 2-已消费 3-已完成
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCouponOrder")
    Observable<HttpStateResult<List<CouponOrder>>> getCouponOrder(@Field("code")int code,@Field("page")int page,@Field("status") int status);
}
