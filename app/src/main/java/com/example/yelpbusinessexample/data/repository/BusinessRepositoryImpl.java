package com.example.yelpbusinessexample.data.repository;

import android.util.Log;

import com.example.yelpbusinessexample.data.api.NetworkService;
import com.example.yelpbusinessexample.data.api.YelpServiceApi;
import com.example.yelpbusinessexample.data.model.BusinessResponse;
import com.example.yelpbusinessexample.data.model.Businesses;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessRepositoryImpl implements BusinessRepository {

    private static final String TAG = "Businesses Repository";

    private YelpServiceApi serviceApi;
    private LoadBusinessesCallback callback;

    Call<BusinessResponse> businessResponseCall;

    public BusinessRepositoryImpl(LoadBusinessesCallback callback) {
        serviceApi = NetworkService.getInstance().getYelpServiceApi();
        this.callback = callback;
    }

    @Override
    public void getBusinesses(HashMap<String, String> queryMap) {
        businessResponseCall = serviceApi.getBusinessesByDistance("PUT YOUR HEADER HERE", queryMap);
        businessResponseCall.enqueue(new Callback<BusinessResponse>() {
            @Override
            public void onResponse(Call<BusinessResponse> call, Response<BusinessResponse> response) {
                Log.d(TAG,"API Success");
                if (response.body() != null) {
                    List<Businesses> businesses = response.body().getBusinesses();
                    callback.onBusinessesLoaded(businesses, response.body().getTotal());
                } else {
                    callback.onDataNotAvailable();
                }
            }

            @Override
            public void onFailure(Call<BusinessResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                callback.onError(call.isCanceled());
            }
        });
    }

    @Override
    public void cancelCall() {
        if (businessResponseCall != null)
            businessResponseCall.cancel();
    }
}
