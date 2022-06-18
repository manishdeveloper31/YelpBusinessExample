package com.example.yelpbusinessexample.data.repository

import com.example.yelpbusinessexample.data.model.Businesses
import java.util.HashMap

interface BusinessRepository {

    interface LoadBusinessesCallback {
        fun onBusinessesLoaded(businesses: List<Businesses>, total: Int)
        fun onDataNotAvailable()
        fun onError(isCancel: Boolean)
    }

    fun cancelCall()
    fun getBusinesses(queryMap: HashMap<String, String>)
}