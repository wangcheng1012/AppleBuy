package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.been.ImageBean;
import com.dw.applebuy.ui.home.shoppingmanage.m.UploadCoverImg;
import com.dw.applebuy.ui.home.shoppingmanage.m.Coupon;
import com.dw.applebuy.ui.message.MessageBean;
import com.dw.applebuy.ui.songjifen.m.VerifyUser;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.bean.HttpResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class AppHttpMethods {

    private static RetrofitBase retrofitBase;

    private static FactoryInters apiService;

    private static AppHttpMethods INSTANCE;

    public AppHttpMethods() {
        retrofitBase = new RetrofitBase();
        apiService = retrofitBase.retrofit.create(FactoryInters.class);
    }

    //获取单例
    public static AppHttpMethods getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new AppHttpMethods();
        }
        return INSTANCE;
    }

    public FactoryInters getApiService() {
        return apiService;
    }

    public RetrofitBase getRetrofitBase() {
        return retrofitBase;
    }

    private <T> void subscribe(Observable<T> observable, Subscriber<T> subscriber) {
        retrofitBase.toSubscribe(observable, subscriber);
    }


//    /**
//     * 这个方法 返回为 HttpStateResult，相当与返回了包含state 和code的所有参数
//     *
//     * @param subscriber 返回的观察者
//     * @param s          传入的String参数
//     */
//    public void getCode(Subscriber<HttpResult<List>> subscriber, String s) {
//        Observable<HttpResult<List>> verifyCode = apiService.getRegisterVerifyCode(s);
////                .map(new HttpResultFunc<String>());
//        subscribe(verifyCode, subscriber);
//    }

    /**
     * 忘记密码验证码
     *
     * @param subscriber
     * @param s
     */
    public void getForgetPasswordVerifyCode(Subscriber<HttpResult<List>> subscriber, String s) {
        Observable<HttpResult<List>> verifyCode = apiService.getForgetPasswordVerifyCode(s);
//                .map(new HttpResultFunc<String>());
        subscribe(verifyCode, subscriber);
    }
    /**
     * 更换手机验证码
     *
     * @param subscriber
     * @param phone
     */
    public void getChangeMobileVerifyCode(Subscriber<HttpResult<List>> subscriber, String phone) {
        Observable<HttpResult<List>> verifyCode = apiService.getChangeMobileVerifyCode(phone);
//                .map(new HttpResultFunc<String>());
        subscribe(verifyCode, subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber
     * @param arrayMap
     */
    public void register(Subscriber<HttpResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpResult<List>> observable = apiService.register(arrayMap);
        subscribe(observable, subscriber);
    }

    /**
     * 忘记密码
     *
     * @param subscriber
     * @param arrayMap
     */
    public void forgetPassword(Subscriber<HttpResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpResult<List>> observable = apiService.forgetPassword(arrayMap);
        subscribe(observable, subscriber);
    }

    /**
     * 登录
     *
     * @param subscriber
     * @param phone
     * @param psw
     */
    public void Login(Subscriber<HttpResult<LoginBack>> subscriber, String phone, String psw) {
        Observable<HttpResult<LoginBack>> observable = apiService.login(phone, psw);
        subscribe(observable, subscriber);
    }

    /**
     * 获取商家详情
     * @param subscriber
     */
    public void getInfo(Subscriber<Info> subscriber) {
        Observable<Info> observable = apiService.getInfo(null).map(new HttpResultFunc<Info>());
        subscribe(observable, subscriber);
    }

//    /**
//     * 添加编辑优惠券
//     *
//     * @param subscriber
//     * @param arrayMap
//     */
//    public void addYouHui(Subscriber<HttpResult<String>> subscriber, ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo) {
//        Observable<HttpResult<String>> observable = apiService.addYouHui(arrayMap, photo);
//        subscribe(observable, subscriber);
//    }

    /**
     * 更换手机号码
     *
     * @param subscriber
     * @param phone
     * @param verifyCode
     */
    public void changePhone(Subscriber<HttpResult> subscriber, String phone, String verifyCode) {
        Observable<HttpResult> changePhoneObservable = apiService.changePhone(phone, verifyCode);
        subscribe(changePhoneObservable, subscriber);
    }

    /**
     * 获取关于我们
     * @param subscriber
     */
    public void getAboutUs(Subscriber<String> subscriber) {
        Observable<String> aboutUs = apiService.getAboutUs()
                .map(new HttpResultFunc<String>() );
        subscribe(aboutUs,subscriber);
    }

    /**
     * 商家-充值时积分套餐
     * @param subscriber
     */
    public void getMctIntegralPackage(Subscriber< ScorePackage> subscriber) {
        Observable <ScorePackage> aboutUs = apiService.getMctIntegralPackage()
                .map(new HttpResultFunc<ScorePackage>());
        subscribe(aboutUs,subscriber);
    }


    /**
     *  商家-微信/支付宝 支付接kou
     * @param subscriber
     * @param id         套餐ID (传ID 可以不传积分)
     * @param integral   积分 (散积分 可以不传ID)
     */
    public void rechargeIntegral(Subscriber< RechageScoreOrder> subscriber,Integer id,Integer integral) {
        Observable <RechageScoreOrder> aboutUs = apiService.rechargeIntegral(id,integral)
                .map(new HttpResultFunc<RechageScoreOrder>());
        subscribe(aboutUs,subscriber);
    }

    /**
     * 赠送积分-检测用户是否存在
     * @param subscriber
     * @param phone
     */
    public void checkMember(Subscriber<VerifyUser> subscriber, String phone) {
        Observable<VerifyUser> observable = apiService.checkMember(phone)
                .map(new HttpResultFunc<VerifyUser>());
        subscribe(observable,subscriber);
    }

    /**
     * 赠送积分-给用户发短信验证
     * @param subscriber
     * @param phone
     */
    public void giveIntegralVerifyCode(Subscriber<HttpResult<String>> subscriber, String phone) {
        Observable<HttpResult<String>> observable = apiService.giveIntegralVerifyCode(phone);
        subscribe(observable,subscriber);
    }

    /**
     * 商家-赠送积分
     * @param subscriber
     * @param map
     */
    public void giveIntegral(Subscriber<HttpResult> subscriber, ArrayMap<String,Object> map) {
        Observable<HttpResult> observable = apiService.giveIntegral(map);
        subscribe(observable,subscriber);
    }

    public void edit(Subscriber<HttpResult> subscriber, ArrayMap<String,Object> map){
        Observable<HttpResult> observable  = apiService.edit(map);
        subscribe(observable,subscriber);

    }

    /**
     * 商家-申请认证
     *  @param subscriber
     * @param arrayMap
     * @param photo2
     */
    public void applyAuthentication(Subscriber<HttpResult> subscriber, ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo, MultipartBody.Part photo2) {
        Observable<HttpResult> observable = apiService.applyAuthentication(arrayMap, photo,photo2);
        subscribe(observable, subscriber);
    }

    /**
     * -提交  删除 优惠卷审核
     * @param sub
     * @param id
     */
    public void submitCoupon(Subscriber<HttpResult> sub, String path, String id) {
        Observable<HttpResult> observable = apiService.submitCoupon(path,id);
        subscribe(observable, sub);

    }

    /**
     * 获取优惠券详情
     * @param sub
     * @param id
     */
    public void getCouponInfo(Subscriber sub, String id) {
        Observable<Coupon> observable = apiService.getCouponInfo(id).map(new HttpResultFunc<Coupon>());
        subscribe(observable, sub);
    }

    /**
     * 获取优惠券详情
     * @param sub
     * @param code
     */
    public void useCoupon(Subscriber sub, String code) {
        Observable<HttpResult> observable = apiService.useCoupon(code);
        subscribe(observable, sub);
    }

    /**
     * 下架优惠券
     * @param sub
     * @param id
     */
    public void offShelfCoupon(Subscriber sub, String id) {
        Observable<HttpResult> observable = apiService.offShelfCoupon(id);
        subscribe(observable, sub);
    }

    /**
     * 上架优惠券
     * @param sub
     * @param id
     * @param end_time  2017-01-19
     */
    public void shelvesCoupon(Subscriber sub, String id,String end_time) {
        Observable<HttpResult> observable = apiService.shelvesCoupon(id,end_time);
        subscribe(observable, sub);
    }

    /**
     * 上传首图
     * @param sub
     * @param photo
     * @param sessionid
     */
    public void uploadCoverImg(Subscriber sub,MultipartBody.Part photo,RequestBody sessionid) {
        Observable<UploadCoverImg> observable = apiService.uploadCoverImg(sessionid,photo)
                .map(new HttpResultFunc<UploadCoverImg>());
        subscribe(observable, sub);
    }

    /**
     * 上传详情图
     * @param sub
     * @param photo
     * @param sessionid
     */
    public void uploadDetailsImgs(Subscriber sub, MultipartBody.Part photo, RequestBody sessionid) {
        Observable<List<ImageBean>> observable = apiService.uploadDetailsImgs(sessionid,photo)
                .map(new HttpResultFunc<List<ImageBean>>());
        subscribe(observable, sub);
    }

    /**
     * 会员备注修改
     * @param sub
     * @param member_id
     * @param remark
     */
    public void saveRemark(Subscriber<HttpResult> sub, String member_id, String remark) {
        Observable<HttpResult> observable = apiService.saveRemark(member_id,remark);
        subscribe(observable, sub);
    }

//    /**
//     * 获取消息详情 和 已浏览状态
//     * @param sub
//     * @param id
//     */
//    public void viewMessage(Subscriber<MessageBean> sub, String id) {
//        Observable<MessageBean> observable = apiService.viewMessage(id)
//                .map(new HttpResultFunc<MessageBean>());
//        subscribe(observable, sub);
//    }


}
