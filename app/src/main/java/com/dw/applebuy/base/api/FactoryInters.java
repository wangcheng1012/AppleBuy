package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.ui.home.hexiao.m.CouponOrder;
import com.dw.applebuy.ui.home.ordermanage.m.CouponOrderList;
import com.dw.applebuy.ui.home.scoremanage.m.ScoreListResult;
import com.dw.applebuy.ui.home.shoppingmanage.m.BusinessScope;
import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.shoppingmanage.m.ProvinceCityArea;
import com.dw.applebuy.ui.home.shoppingmanage.m.UploadCoverImg;
import com.dw.applebuy.ui.home.shoppingmanage.m.YouhuiQuanType;
import com.dw.applebuy.ui.home.shoppingmanage.m.Coupon;
import com.dw.applebuy.ui.home.usermanage.m.MemberIntegralLog;
import com.dw.applebuy.ui.home.usermanage.m.UserList;
import com.dw.applebuy.ui.message.MessageBean;
import com.dw.applebuy.ui.songjifen.m.VerifyUser;
import com.rxmvp.bean.HttpResult;

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
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 *
 */
public interface FactoryInters {

    @FormUrlEncoded
    @POST("app/common/getRegisterVerifyCode")
    Observable<HttpResult<String>> getRegisterVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/common/getForgetPasswordVerifyCode")
    Observable<HttpResult<List>> getForgetPasswordVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/common/getChangeMobileVerifyCode")
    Observable<HttpResult<List>> getChangeMobileVerifyCode(@Field("mobile") String phone);

    @FormUrlEncoded
    @POST("app/user/register")
    Observable<HttpResult<List>> register(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/forgetPassword")
    Observable<HttpResult<List>> forgetPassword(@FieldMap ArrayMap<String, String> arrayMap);

    @FormUrlEncoded
    @POST("app/user/login")
    Observable<HttpResult<LoginBack>> login(@Field("mobile") String phone, @Field("password") String psw);

    /**
     * 获取商家详情
     * @param sessionid
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/getInfo")
    Observable<HttpResult<Info>> getInfo(@Field("sessionid") String sessionid);

    /**
     * @param page      分页
     * @param sort_type 1-添加时间 2-销量 3-销量
     * @param status    2 -展示中 3-已下架
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCoupon")
    Observable<HttpResult<Coupon[]>> getCoupon(@Field("page") int page, @Field("sort_type") int sort_type, @Field("status") int status);

    /**
     * 获取优惠券类型
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/coupon/getCouponCategory")
    Observable<HttpResult<List<YouhuiQuanType>>> getCouponCategory(/*@Field("sessionid") String sessionid*/);

    /**
     * 添加和编辑优惠券
     *
     * @param arrayMap
     * @return
     */
    @Multipart
    @POST("app/coupon/save")
//    @POST("http://192.168.1.14:8080/ww/hello.jsp")
    Observable<HttpResult> addYouHui(@PartMap ArrayMap<String, RequestBody> arrayMap, @Part MultipartBody.Part photo);

    /**
     * 更换手机
     *
     * @param phone
     * @param verifyCode
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/changeMobile")
    Observable<HttpResult> changePhone(@Field("mobile") String phone, @Field("code") String verifyCode);

    /**
     * 获取关于我们
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/Common/AboutUs")
    Observable<HttpResult<String>> getAboutUs();

    /**
     * 省市区
     *
     * @return
     */
    @FormUrlEncoded
    @POST
    Observable<HttpResult<List<ProvinceCityArea>>> getProvinceCityArea(@Url String url, @Field("province_id") int province_id, @Field("city_id") int city_id);

    /**
     *
     * 获取商家经营范围-分类
     *
     * @return
     */
//    @FormUrlEncoded
    @POST("app/user/getAllCategory")
    Observable<HttpResult<List<BusinessScope>>> getAllCategory();

    /**
     *
     * @param code  加密后的优惠卷code
     * @param page  分页
     * @param status 0-全部 1-未消费 2-已消费 3-已完成
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCouponOrder")
    Observable<HttpResult<List<CouponOrderList>>> getCouponOrder(@Field("code")String code, @Field("page")Integer page, @Field("status") Integer status);

    @FormUrlEncoded
    @POST("app/coupon/getCouponOrder")
    Observable<HttpResult<CouponOrder>> getCouponOrder(@Field("code")String code);

    /**
     *  商家积分管理列表[消费记录]
      * @param page  分页
     * @param json   	[1]--表示赠送会员积分 [2,3]--表示充值和兑换
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/getIntegralDetails")
    Observable<HttpResult<ScoreListResult>> getScoreList(@Field("page")int page, @Field("type") String json);

    /**
     * 商家-充值时积分套餐
     * @return
     */
    @POST("app/Common/getMctIntegralPackage")
    Observable<HttpResult<ScorePackage>> getMctIntegralPackage( );

    /**
     * 商家-微信/支付宝 支付接kou
     * @param id   	 套餐ID (传ID 可以不传积分)
     * @param integral  积分 (散积分 可以不传ID)
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/rechargeIntegral")
    Observable<HttpResult<RechageScoreOrder>> rechargeIntegral(@Field("id")Integer id, @Field("integral") Integer integral);

    /**
     *  赠送积分-检测用户是否存在
      * @param phone  	电话号码
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/checkMember")
    Observable<HttpResult<VerifyUser>>  checkMember(@Field("mobile") String phone);

    /**
     * 赠送积分- 创建用户验证短信
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("app/common/giveIntegralVerifyCode")
    Observable<HttpResult<String>> giveIntegralVerifyCode(@Field("mobile") String phone);

    /**
     * 商家-赠送积分
//     * @param code          验证码        N
//     * @param id            用户ID        N
//     * @param integral   	 积分         Y
//     * @param mobile        电话          N
//     * @param name          名称          N
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/giveIntegral")
    Observable<HttpResult> giveIntegral(@FieldMap ArrayMap<String,Object> map);

    /**
     * 编辑资料
     * @param map
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/edit")
    Observable<HttpResult> edit(@FieldMap ArrayMap<String, Object> map);

    @Multipart
    @POST("app/user/applyAuthentication")
    Observable<HttpResult> applyAuthentication(@PartMap ArrayMap<String, RequestBody> arrayMap, @Part MultipartBody.Part photo, @Part MultipartBody.Part photo2);

    /**
     * -提交 删除 优惠卷审核
     * @param path deleteCoupon/submitCoupon
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/{path}")
    Observable<HttpResult> submitCoupon(@Path("path")String path, @Field("id") String id);

    /**
     * 获取优惠券详情
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/getCouponInfo")
    Observable<HttpResult<Coupon>> getCouponInfo(@Field("id") String id);

    /**
     * 核销优惠卷
     * @param code
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/useCoupon")
    Observable<HttpResult> useCoupon(@Field("code") String code);

    /**
     * 优惠卷下架接口
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/offShelfCoupon")
    Observable<HttpResult> offShelfCoupon(@Field("id") String id);

    /**
     * 优惠卷上架接口
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/coupon/shelvesCoupon")
    Observable<HttpResult> shelvesCoupon(@Field("id") String id, @Field("end_time")String end_time);

    /**
     * 上传首图
     * @param photo
     * @return
     */
    @Multipart
    @POST("app/user/uploadCoverImg")
    Observable<HttpResult<UploadCoverImg>> uploadCoverImg(@Part("sessionid") RequestBody sessionid, @Part MultipartBody.Part photo);

    /**
     * 上传详情图片
     * @param photo
     * @return
     */
    @Multipart
    @POST("app/user/uploadDetailsImgs")
    Observable<HttpResult<List<ImageBean>>> uploadDetailsImgs(@Part("sessionid") RequestBody sessionid, @Part MultipartBody.Part photo);

    /**
     * 商家获取会员管理列表
     * @return
     */
    @FormUrlEncoded
    @POST("app/consumer/getMctMemberList")
    Observable<HttpResult<UserList>> getMctMemberList(@Field("page") int page, @Field("search") String search);

    /**
     * 会员备注修改
     * @param member_id
     * @param remark
     * @return
     */
    @FormUrlEncoded
    @POST("app/consumer/saveRemark")
    Observable<HttpResult> saveRemark(@Field("member_id") String member_id, @Field("remark") String remark);

    /**
     * 获取会员消费记录[积分兑换记录]
     * @param curPageStart
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/consumer/getMemberIntegralLog")
    Observable<HttpResult<List<MemberIntegralLog>>> getMemberIntegralLog(@Field("page") int curPageStart, @Field("id") String id);

    /**
     * 获取系统消息列表
     * @param curPageStart
     * @return
     */
    @FormUrlEncoded
    @POST("app/common/getMessage")
    Observable<HttpResult<List<MessageBean>>> getMessage(@Field("page") int curPageStart);

    /**
     * 获取消息详情 和 已浏览状态
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("app/common/viewMessage")
    Observable<HttpResult<MessageBean>> viewMessage(@Field("id") String id);

    /**
     * 通过扫码来验证用户是否存在
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("app/integral/checkMemberSao")
    Observable<HttpResult<VerifyUser>> checkMemberSao(@Field("mobile") String mobile);

    /**
     * 删除详情图片
     * @param uri
     * @return
     */
    @FormUrlEncoded
    @POST("app/user/removeDetailsImgs")
    Observable<HttpResult> removeDetailsImgs(@Field("uri") String uri);



}
