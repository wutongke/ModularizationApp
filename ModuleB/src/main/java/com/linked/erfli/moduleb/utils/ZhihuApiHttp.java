package com.linked.erfli.moduleb.utils;

import com.linked.erfli.library.utils.AppClient;
import com.linked.erfli.library.utils.ConverterName;
import com.linked.erfli.moduleb.news.NewsDetailResponse;
import com.linked.erfli.moduleb.news.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by erfli on 11/1/16.
 */

public class ZhihuApiHttp {
    public interface Http{
        @GET("news/latest")
        Observable<NewsResponse> getLatestNews();

        @GET("news/before/{path}")
        Observable<NewsResponse> getHistoryNews(@Path("path") String path);

        @GET("news/{key}")
        Observable<NewsDetailResponse> getNewsDetail(@Path("key") String key);

        @GET("http://news.at.zhihu.com/css/news_qa.auto.css")
        @ConverterName("string")
        Observable<String> getCSS(@Query("v") String key);
    }

    public static Http http = AppClient.BaseRetrofit.create(Http.class);
}
