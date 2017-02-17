package com.rxmvp.api;

import com.rxmvp.api.interceptor.LoginInterceptor;
import com.rxmvp.api.interceptor.ParamsInterceptor;
import com.rxmvp.api.interceptor.ResponseInterceptor;
import com.wlj.base.util.AppConfig;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 注意 子类必须重写 getApiServiceClass
 */
public class RetrofitBase {

    public static final String BASE_URL = "http://supplier.pingguo24.com/index.php/merchant/";

    private static final int DEFAULT_TIMEOUT = 5;

    public Retrofit retrofit;

    //构造方法私有
    public RetrofitBase() {

        String cachePath = AppConfig.getAppConfig().getImagePath() + AppConfig.CONF_COOKIE + File.separator;
        Cache cache = new Cache(new File(cachePath), 1024 * 1024);

        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.cache(cache);//cache
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)).build();

        //添加请求的公共参数 动态的参数不能在这里加 因为单例模式
        ParamsInterceptor.Builder builder = new ParamsInterceptor.Builder();
//            builder.addParam("sessionid", sessionId);
        httpClientBuilder.addInterceptor(builder.build());

        //返回参数[]-> null ,{} -> null
        httpClientBuilder.addInterceptor(new ResponseInterceptor());
        httpClientBuilder.addInterceptor(new LoginInterceptor());

        //GsonConverterFactory_My 适配一个字段在状态码不同时返回数据类型不同的处理.eg;"成功返回时是消息数据列表，失败时是异常消息文本
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
//        apiService = retrofit.create(FactoryInters.class);
    }

    public <T> void toSubscribe(Observable<T> observable, Subscriber<T> subscriber) {
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


//
//    /**
//     * 用于获取聚合笑话的数据
//     * @param subscriber 由调用者传过来的观察者对象
//     * @param options 访问参数
//     */
//    public void getJokesByHttpResultMap(Subscriber<List<String>> subscriber, Map<String, Object> options){
//
////        apiService.getJokesByRxJavaHttpResult(options)
////                .map(new HttpResultFunc<JuHeDream>())
////                .subscribeOn(Schedulers.io())
////                .unsubscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(subscriber);
//        Observable<List<String>> observable = apiService.getDreams(options)
//                .map(new HttpResultFunc<List<String>>());
//        toSubscribe(observable,subscriber);
//    }

//    @FormUrlEncoded
//    @POST("common/getRegisterVerifyCode")
//    Observable<HttpStateResult<List>> getRegisterVerifyCode(@Field("mobile") String phone);


}