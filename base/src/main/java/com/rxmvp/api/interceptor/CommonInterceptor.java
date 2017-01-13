package com.rxmvp.api.interceptor;

import android.support.v4.util.ArrayMap;

import com.wlj.base.util.AppConfig;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 封装公共参数（Key和密码）
 * <p>
 */
public class CommonInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        ArrayMap<String, String> paramsMap = new ArrayMap<>();
        paramsMap.put("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));

        Request oldRequest = chain.request();
        Request.Builder requestBuilder = oldRequest.newBuilder();

//            // 添加新的get参数
//            HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
//                    .newBuilder()
//                    .scheme(oldRequest.url().scheme())
//                    .host(oldRequest.url().host())
//                    .addQueryParameter("sessionid", AppConfig.getAppConfig().get(AppConfig.CONF_KEY));

        // process post body inject
        if (oldRequest.method().equals("POST") && oldRequest.body().contentType().subtype().equals("x-www-form-urlencoded")) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (paramsMap.size() > 0) {
                Iterator iterator = paramsMap.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    formBodyBuilder.add((String) entry.getKey(), (String) entry.getValue());
                }
            }
            RequestBody formBody = formBodyBuilder.build();
            String postBodyString = bodyToString(oldRequest.body());
            postBodyString += ((postBodyString.length() > 0) ? "&" : "") + bodyToString(formBody);
            requestBuilder.post(RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postBodyString));
        } else {    // can't inject into body, then inject into url
            injectParamsIntoUrl(oldRequest, requestBuilder, paramsMap);
        }
        oldRequest = requestBuilder.build();
        return chain.proceed(oldRequest);
    }


    // func to inject params into url
    private void injectParamsIntoUrl(Request request, Request.Builder requestBuilder, Map<String, String> paramsMap) {
        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        if (paramsMap.size() > 0) {
            Iterator iterator = paramsMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                httpUrlBuilder.addQueryParameter((String) entry.getKey(), (String) entry.getValue());
            }
        }

        requestBuilder.url(httpUrlBuilder.build());
    }

    private static String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}