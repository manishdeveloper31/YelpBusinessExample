package com.example.yelpbusinessexample.data.api;

import com.example.yelpbusinessexample.data.model.BusinessResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.QueryMap;

public interface YelpServiceApi {

    @GET("businesses/search")
    Call<BusinessResponse> getBusinessesByDistance(@Header("Authorization") String authorization, @QueryMap Map<String, String> searchQueries);

}
