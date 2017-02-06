package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrder;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.youhui.showing.m.Coupon;
import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.google.gson.JsonArray;
import com.rxmvp.bean.HttpStateResult;
import com.rxmvp.bean.ResultResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
     * 获取商家详情
     * @param sessionid
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/getInfo")
    Observable<HttpStateResult<Info>> getInfo(@Field("sessionid") String sessionid);

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
//    @POST("http://192.168.1.14:8080/ww/hello.jsp")
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

    /**
     *  商家积分管理列表[消费记录]
      * @param page  分页
     * @param json   	[1]--表示赠送会员积分 [2,3]--表示充值和兑换
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/getIntegralDetails")
    Observable<HttpStateResult<ScoreListResult>> getScoreList(@Field("page")int page, @Field("type") String json);

    /**
     * 商家-充值时积分套餐
     * @return
     */
    @POST("app/Common/getMctIntegralPackage")
    Observable<HttpStateResult<ScorePackage>> getMctIntegralPackage( );

    /**
     * 商家-微信/支付宝 支付接kou
     * @param id   	 套餐ID (传ID 可以不传积分)
     * @param integral  积分 (散积分 可以不传ID)
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/rechargeIntegral")
    Observable<HttpStateResult<RechageScoreOrder>> rechargeIntegral(@Field("id")Integer id, @Field("integral") Integer integral);

    /**
     *  赠送积分-检测用户是否存在
      * @param phone  	电话号码
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/checkMember")
    Observable<HttpStateResult<InputPhoneUser>>  checkMember(@Field("mobile") String phone);

    /**
     * 赠送积分- 创建用户验证短信
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("app/common/giveIntegralVerifyCode")
    Observable<HttpStateResult<String>> giveIntegralVerifyCode(@Field("mobile") String phone);

    /**
     * 商家-赠送积分
     * @param code          验证码        N
     * @param id            用户ID        N
     * @param integral   	 积分         Y
     * @param mobile        电话          N
     * @param name          名称          N
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/giveIntegral")
    Observable<HttpStateResult> giveIntegral(@FieldMap ArrayMap<String,Object> map);

}
