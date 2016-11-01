package cn.easydone.modulea.Utils;

import com.linked.erfli.library.utils.AppClient;

import java.util.Map;

import cn.easydone.modulea.module.BookResponse;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by erfli on 11/1/16.
 */

public interface BookApiHttp {
    interface Http {
        @GET("https://api.douban.com/v2/book/search")
        Observable<BookResponse> getBooks(@QueryMap Map<String, String> options);
    }

    public static Http http = AppClient.BaseRetrofit.create(Http.class);
}
