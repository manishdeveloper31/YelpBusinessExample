package com.example.yelpbusinessexample.data.api

import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkService {
    val yelpServiceApi: YelpServiceApi

    companion object {
        private const val BASE_URL = " https://api.yelp.com/v3/"
        private var service: NetworkService? = null

        @JvmStatic
        val instance: NetworkService?
            get() {
                if (service == null) {
                    synchronized(NetworkService::class.java) {
                        if (service == null) {
                            service = NetworkService()
                        }
                    }
                }
                return service
            }
    }

    init {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client: OkHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(logging)
        }.build()

        val retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL)
            client(client)
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        yelpServiceApi = retrofit.create(YelpServiceApi::class.java)
    }
}