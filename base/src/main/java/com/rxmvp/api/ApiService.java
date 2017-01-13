//package com.rxmvp.api;
//
//import com.rxmvp.bean.HttpStateResult;
//
//import java.util.List;
//import java.util.Map;
//
//import retrofit2.http.Field;
//import retrofit2.http.FormUrlEncoded;
//import retrofit2.http.GET;
//import retrofit2.http.POST;
//import retrofit2.http.QueryMap;
//import rx.Observable;
//
///**
// * 请求示例：
// * http://v.juhe.cn/dream/query
// * q:梦境关键字，如：黄金 需要utf8 urlencode
// * cid:指定分类，默认全部
// * full: 是否显示详细信息，1:是 0:否，默认0
// */
//public interface ApiService {
//    @GET("dream/query")
//    Observable<HttpStateResult<List<String>>> getDreams(@QueryMap Map<String, Object> options);
//
//}