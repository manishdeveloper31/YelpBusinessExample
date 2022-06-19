package com.example.yelpbusinessexample.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.yelpbusinessexample.data.model.Businesses
import com.example.yelpbusinessexample.data.repository.BusinessRepository
import java.util.*
import kotlin.collections.HashMap

class HomeViewModel(var repository: BusinessRepository) : ViewModel() {

    var radiusValue = MutableLiveData<Int>(100)
    var isLastPage = MutableLiveData<Boolean>()
    var isLoading = MutableLiveData<Boolean>()
    var TOTAL_PAGES = 0
    var currentPage = MutableLiveData<Int>()
    var businessList = MutableLiveData<List<Businesses>>()
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    fun getBusiness() {
        timer?.cancel()
        timer?.purge()

        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                val queryMap = HashMap<String, String>()
                queryMap["term"] = "restaurants"
                queryMap["location"] = "NYC"
                queryMap["radius"] = "" + radiusValue.value
                queryMap["sort_by"] = "distance"
                queryMap["limit"] = "15"
                queryMap["offset"] = "" + currentPage.value?.times(15)
                isLoading.postValue(true)
                repository.getBusinesses(queryMap, callback)
            }
        }
        timer?.schedule(timerTask, 1000)
    }

    fun setBusinessesLiveData(businesses: List<Businesses>, total : Int) {
        businessList.postValue(businesses)
        TOTAL_PAGES = total / 15
        isLastPage.value = currentPage.value == TOTAL_PAGES
    }

    var callback = object : BusinessRepository.LoadBusinessesCallback {

        override fun onBusinessesLoaded(businesses: List<Businesses>, total: Int) {
            setBusinessesLiveData(businesses, total)
            isLoading.value = false;
        }

        override fun onError(isCancel: Boolean) {
            isLoading.value = false;
        }
    }
}

class HomeViewModelFactory(private val repository: BusinessRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}