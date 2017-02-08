package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.Info;
import com.dw.applebuy.been.LoginBack;
import com.dw.applebuy.been.RechageScoreOrder;
import com.dw.applebuy.been.ScorePackage;
import com.dw.applebuy.ui.set.m.AboutUsModel;
import com.dw.applebuy.ui.songjifen.m.InputPhoneUser;
import com.rxmvp.api.ApiException;
import com.rxmvp.api.HttpResultFunc;
import com.rxmvp.bean.ResultResponse;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

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


    /**
     * 这个方法 返回为 HttpStateResult，相当与返回了包含state 和code的所有参数
     *
     * @param subscriber 返回的观察者
     * @param s          传入的String参数
     */
    public void getCode(Subscriber<HttpStateResult<List>> subscriber, String s) {
        Observable<HttpStateResult<List>> verifyCode = apiService.getRegisterVerifyCode(s);
//                .map(new HttpResultFunc<String>());
        subscribe(verifyCode, subscriber);
    }

    /**
     * 忘记密码验证码
     *
     * @param subscriber
     * @param s
     */
    public void getForgetPasswordVerifyCode(Subscriber<HttpStateResult<List>> subscriber, String s) {
        Observable<HttpStateResult<List>> verifyCode = apiService.getForgetPasswordVerifyCode(s);
//                .map(new HttpResultFunc<String>());
        subscribe(verifyCode, subscriber);
    }
    /**
     * 更换手机验证码
     *
     * @param subscriber
     * @param phone
     */
    public void getChangeMobileVerifyCode(Subscriber<HttpStateResult<List>> subscriber, String phone) {
        Observable<HttpStateResult<List>> verifyCode = apiService.getChangeMobileVerifyCode(phone);
//                .map(new HttpResultFunc<String>());
        subscribe(verifyCode, subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber
     * @param arrayMap
     */
    public void register(Subscriber<HttpStateResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpStateResult<List>> observable = apiService.register(arrayMap);
        subscribe(observable, subscriber);
    }

    /**
     * 忘记密码
     *
     * @param subscriber
     * @param arrayMap
     */
    public void forgetPassword(Subscriber<HttpStateResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpStateResult<List>> observable = apiService.forgetPassword(arrayMap);
        subscribe(observable, subscriber);
    }

    /**
     * 登录
     *
     * @param subscriber
     * @param phone
     * @param psw
     */
    public void Login(Subscriber<HttpStateResult<LoginBack>> subscriber, String phone, String psw) {
        Observable<HttpStateResult<LoginBack>> observable = apiService.login(phone, psw);
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

    /**
     * 添加编辑优惠券
     *
     * @param subscriber
     * @param arrayMap
     */
    public void addYouHui(Subscriber<ResultResponse> subscriber, ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo) {
        Observable<ResultResponse> observable = apiService.addYouHui(arrayMap, photo);
        subscribe(observable, subscriber);
    }

    /**
     * 更换手机号码
     *
     * @param subscriber
     * @param phone
     * @param verifyCode
     */
    public void changePhone(Subscriber<ResultResponse> subscriber, String phone, String verifyCode) {
        Observable<ResultResponse> changePhoneObservable = apiService.changePhone(phone, verifyCode);
        subscribe(changePhoneObservable, subscriber);
    }

    /**
     * 获取关于我们
     * @param subscriber
     */
    public void getAboutUs(Subscriber<AboutUsModel> subscriber) {
        Observable<AboutUsModel> aboutUs = apiService.getAboutUs()
                .map(new HttpResultFunc<AboutUsModel>() );
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
    public void checkMember(Subscriber<InputPhoneUser> subscriber, String phone) {
        Observable<InputPhoneUser> observable = apiService.checkMember(phone)
                .map(new HttpResultFunc<InputPhoneUser>());
        subscribe(observable,subscriber);
    }

    /**
     * 赠送积分-给用户发短信验证
     * @param subscriber
     * @param phone
     */
    public void giveIntegralVerifyCode(Subscriber<HttpStateResult<String>> subscriber, String phone) {
        Observable<HttpStateResult<String>> observable = apiService.giveIntegralVerifyCode(phone);
        subscribe(observable,subscriber);
    }

    /**
     * 商家-赠送积分
     * @param subscriber
     * @param map
     */
    public void giveIntegral(Subscriber<HttpStateResult> subscriber, ArrayMap<String,Object> map) {
        Observable<HttpStateResult> observable = apiService.giveIntegral(map);
        subscribe(observable,subscriber);
    }

    public void edit(Subscriber<HttpStateResult> subscriber,ArrayMap<String,Object> map){
        Observable<HttpStateResult> observable  = apiService.edit(map);
        subscribe(observable,subscriber);

    }

    /**
     * 商家-申请认证
     *  @param subscriber
     * @param arrayMap
     * @param photo2
     */
    public void applyAuthentication(Subscriber<HttpStateResult> subscriber, ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo, MultipartBody.Part photo2) {
        Observable<HttpStateResult> observable = apiService.applyAuthentication(arrayMap, photo,photo2);
        subscribe(observable, subscriber);
    }
}
