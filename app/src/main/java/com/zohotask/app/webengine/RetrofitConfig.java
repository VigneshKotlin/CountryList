package com.zohotask.app.webengine;


import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {
    private static OkHttpClient.Builder httpClient;

    private static RetrofitConfig single_instance = null;
    public static RetrofitConfig getInstance()
    {
        if (single_instance == null)
            single_instance = new RetrofitConfig();

        return single_instance;
    }

    public WebServiceAPI createService(String apiEndpoint) {
        httpClient = new OkHttpClient.Builder();
        // Timeout handling
        OkHttpClient client = httpClient.readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(WebServiceAPI.class);
    }

    public WebServiceAPI createServiceWithHeader(String apiEndpoint) {
        httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder().addHeader("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", "37bafc7adfmsh6ebb0df43b231dfp1b69aejsna49b2d45e204")
                        .build();
                return chain.proceed(request);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiEndpoint)
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(WebServiceAPI.class);
    }



}
