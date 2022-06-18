package com.example.yelpbusinessexample.data.api

import retrofit2.http.GET
import com.example.yelpbusinessexample.data.model.BusinessResponse
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.QueryMap

interface YelpServiceApi {

    @GET("businesses/search")
    fun getBusinessesByDistance(
        @Header("Authorization") authorization: String,
        @QueryMap searchQueries: Map<String, String>
    ): Call<BusinessResponse?>
}