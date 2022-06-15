package com.example.yelpbusinessexample.data.api;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

   private static final String BASE_URL = " https://api.yelp.com/v3/";

   private final YelpServiceApi yelpServiceApi;
   private static NetworkService service;

   public NetworkService() {
      HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
      logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
      OkHttpClient client = new OkHttpClient.Builder()
              .addInterceptor(logging)
              .build();

      Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();

      yelpServiceApi = retrofit.create(YelpServiceApi.class);
   }

   public static NetworkService getInstance() {
      if (service == null) {
         synchronized (NetworkService.class) {
            if (service == null) {
               service = new NetworkService();
            }
         }
      }
      return service;
   }

   public YelpServiceApi getYelpServiceApi() {
      return yelpServiceApi;
   }

}
