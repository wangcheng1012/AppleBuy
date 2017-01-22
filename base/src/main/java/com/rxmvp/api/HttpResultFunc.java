package com.rxmvp.api;

import com.rxmvp.bean.HttpStateResult;
import com.wlj.base.BuildConfig;

import java.util.ArrayList;

import rx.functions.Func1;

/**
 * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
 *
 * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
 */
public class HttpResultFunc<T> implements Func1<HttpStateResult<T>, T> {

    @Override
    public T call(HttpStateResult<T> httpResult) {
        if (httpResult.getStatus() != 1) {
            throw new ApiException(httpResult.getStatus());
        }

        return httpResult.getData();
    }
}