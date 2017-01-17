package com.dw.applebuy.base.api;

import android.support.v4.util.ArrayMap;

import com.dw.applebuy.been.LoginBack;
import com.rxmvp.api.GsonConverter.ResultResponse;
import com.rxmvp.api.RetrofitBase;
import com.rxmvp.bean.HttpStateResult;

import java.util.List;
import java.util.Map;

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

    public AppHttpMethods(){
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

    /**
     * 这个方法 返回为 HttpStateResult，相当与返回了包含state 和code的所有参数
     *
     * @param subscriber 返回的观察者
     * @param s          传入的String参数
     */
    public void getCode(Subscriber<HttpStateResult<List>> subscriber, String s) {
        Observable<HttpStateResult<List>> verifyCode = apiService.getRegisterVerifyCode(s);
//                .map(new HttpResultFunc<String>());
        retrofitBase.toSubscribe(verifyCode, subscriber);
    }

    /**
     * 忘记密码验证码
     * @param subscriber
     * @param s
     */
    public void getForgetPasswordVerifyCode(Subscriber<HttpStateResult<List>> subscriber, String s) {
        Observable<HttpStateResult<List>> verifyCode = apiService.getForgetPasswordVerifyCode(s);
//                .map(new HttpResultFunc<String>());
        retrofitBase.toSubscribe(verifyCode, subscriber);
    }
    /**
     * 注册
     * @param subscriber
     * @param arrayMap
     */
    public void register(Subscriber<HttpStateResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpStateResult<List>> observable = apiService.register(arrayMap);
        retrofitBase.toSubscribe(observable, subscriber);
    }

    /**
     * 忘记密码
     * @param subscriber
     * @param arrayMap
     */
    public void forgetPassword(Subscriber<HttpStateResult<List>> subscriber, ArrayMap<String, String> arrayMap) {
        Observable<HttpStateResult<List>> observable = apiService.forgetPassword(arrayMap);
        retrofitBase.toSubscribe(observable, subscriber);
    }
    /**
     * 登录
     * @param subscriber
     * @param phone
     * @param psw
     */
    public void Login(Subscriber<HttpStateResult<LoginBack>> subscriber, String phone, String psw) {
        Observable<HttpStateResult<LoginBack>> observable =  apiService.login(phone,psw);

        retrofitBase.toSubscribe(observable, subscriber);
    }

    /**
     * 添加编辑优惠券
     * @param subscriber
     * @param arrayMap
     */
    public void addYouHui(Subscriber<ResultResponse> subscriber, ArrayMap<String, RequestBody> arrayMap, MultipartBody.Part photo) {
        Observable<ResultResponse> observable =  apiService.addYouHui(arrayMap,photo);

        retrofitBase.toSubscribe(observable, subscriber);
    }
}
