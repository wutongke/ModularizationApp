package com.linked.erfli.library.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by erfli on 6/14/16.
 */
public class AppClient {
    public interface HttpService {

    }
    public static Retrofit BaseRetrofit;
    static {
        initAppClient();
    }
    public static void initAppClient() {
        BaseRetrofit = new Retrofit.Builder()
                .baseUrl("http://news-at.zhihu.com/api/4/")
                .addCallAdapterFactory((RxJavaCallAdapterFactory.create()))
                .addConverterFactory(new Converter.Factory() {
                    Gson gson = new Gson();
                    @Override
                    public Converter<ResponseBody, ?> responseBodyConverter(final Type type, final Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<ResponseBody, Object>() {
                            @Override
                            public Object convert(ResponseBody value) throws IOException {
                                try {
                                    if (annotations.length > 1) {
                                        for (Annotation annotation : annotations) {
                                            if (annotation instanceof ConverterName && ((ConverterName) annotation).value().equals("string")) {
                                                return value.string();
                                            }
                                        }
                                    }
                                    return gson.getAdapter(TypeToken.get(type)).fromJson(value.charStream());
                                } finally {
                                    value.close();
                                }
                            }
                        };
                    }

                    @Override
                    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
                        return super.requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
                    }

                    @Override
                    public Converter<?, String> stringConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return super.stringConverter(type, annotations, retrofit);
                    }
                })
                .build();
    }

}
