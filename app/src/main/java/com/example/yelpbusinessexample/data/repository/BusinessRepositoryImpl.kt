package com.example.yelpbusinessexample.data.repository

import com.example.yelpbusinessexample.data.api.NetworkService.Companion.instance
import com.example.yelpbusinessexample.data.repository.BusinessRepository.LoadBusinessesCallback
import com.example.yelpbusinessexample.data.api.YelpServiceApi
import com.example.yelpbusinessexample.data.model.BusinessResponse
import java.util.HashMap
import android.util.Log
import com.example.yelpbusinessexample.data.model.Businesses
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BusinessRepositoryImpl : BusinessRepository {

    private val serviceApi: YelpServiceApi by lazy {
        instance!!.yelpServiceApi
    }

    private var businessResponseCall: Call<BusinessResponse?>? = null

    override fun getBusinesses(queryMap: HashMap<String, String>, callback : LoadBusinessesCallback) {
        businessResponseCall = serviceApi.getBusinessesByDistance(
            "PUT YOUR HEADER HERE",
            queryMap
        )
        businessResponseCall!!.enqueue(object : Callback<BusinessResponse?> {
            override fun onResponse(call: Call<BusinessResponse?>, response: Response<BusinessResponse?>) {
                Log.d(TAG, "API Success")
                if (response.body() != null) {
                    val businesses: List<Businesses> = response.body()!!.businesses!!
                    callback.onBusinessesLoaded(businesses, response.body()!!.total)
                } else {
                    callback.onError(false)
                }
            }

            override fun onFailure(call: Call<BusinessResponse?>, t: Throwable) {
                Log.d(TAG, t.message!!)
                callback.onError(call.isCanceled)
            }
        })
    }

    override fun cancelCall() {
        businessResponseCall?.cancel()
    }

    companion object {
        private const val TAG = "Businesses Repository"
    }

}