package com.rxmvp.api;

import com.rxmvp.api.GsonConverter.GsonConverterFactory_My;
import com.rxmvp.api.interceptor.ParamsInterceptor;
import com.rxmvp.api.interceptor.ResponseInterceptor;
import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.util.AppConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.InternalCache;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 注意 子类必须重写 getApiServiceClass
 */
public class RetrofitBase {

    public static final String BASE_URL = " http://supplier.pingguo24.com/index.php/merchant/";

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
        //添加请求的公共参数
        String sessionId = AppConfig.getAppConfig().get(AppConfig.CONF_KEY);
        if (!sessionId.isEmpty()) {
            ParamsInterceptor.Builder builder = new ParamsInterceptor.Builder();
            builder.addParam("sessionId", sessionId);
            httpClientBuilder.addInterceptor(builder.build());
        }
        //返回参数[]-> null ,{} -> null
        httpClientBuilder.addInterceptor(new ResponseInterceptor());

        //GsonConverterFactory_My 适配一个字段在状态码不同时返回数据类型不同的处理.eg;"成功返回时是消息数据列表，失败时是异常消息文本
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory_My.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
//        apiService = retrofit.create(FactoryInters.class);
    }

    /**
     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
     *
     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
     */
    public class HttpResultFunc<T> implements Func1<HttpStateResult<T>, T> {

        @Override
        public T call(HttpStateResult<T> httpResult) {
            if (httpResult.getStatus() != 0) {
                throw new ApiException(httpResult.getStatus());
            }
            return httpResult.getData();
        }
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