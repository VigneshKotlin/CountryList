package com.zohotask.app.webengine;

import com.zohotask.app.countrylist.model.CountryListResponseModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServiceAPI {
    @GET("all")
    Observable<Response<ResponseBody>> getCountryList();
    @GET("weather")
    Observable<Response<ResponseBody>> getWeatherDetail(@Query("q") String city, @Query("lat") String lat,
                                                        @Query("lon") String lng, @Query("lang") String language);
}
