package com.example.yelpbusinessexample

import androidx.appcompat.app.AppCompatActivity
import com.example.yelpbusinessexample.data.repository.BusinessRepository
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.example.yelpbusinessexample.ui.adapters.RestaurantAdapter
import com.google.android.material.slider.Slider
import com.google.android.material.progressindicator.CircularProgressIndicator
import java.util.Timer
import java.util.TimerTask
import com.example.yelpbusinessexample.HomeActivity
import android.os.Bundle
import com.example.yelpbusinessexample.R
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yelpbusinessexample.data.repository.BusinessRepositoryImpl
import com.example.yelpbusinessexample.data.repository.BusinessRepository.LoadBusinessesCallback
import com.example.yelpbusinessexample.data.model.Businesses
import java.lang.Runnable
import android.view.View
import android.util.Log
import com.example.yelpbusinessexample.ui.extension.PaginationScrollListener
import java.util.HashMap

class HomeActivity : AppCompatActivity() {

    private lateinit var rvRestaurantList: RecyclerView
    private lateinit var tvRadius: TextView
    private lateinit var tvNoResult: TextView
    private lateinit var radiusSlider: Slider
    private lateinit var cpi_progress: CircularProgressIndicator

    private var businessRepository: BusinessRepository? = null
    private var adapter: RestaurantAdapter? = null
    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    private var distance = 100
    private var isLoading = false
    private var isLastPage = false
    private var TOTAL_PAGES = 5
    private var currentPage = PAGE_START

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        rvRestaurantList = findViewById(R.id.recyclerView)
        radiusSlider = findViewById(R.id.radius_slider)
        tvRadius = findViewById(R.id.tv_radius_selected)
        tvNoResult = findViewById(R.id.tv_no_result)
        cpi_progress = findViewById(R.id.cpi_progress)

        adapter = RestaurantAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        rvRestaurantList.setLayoutManager(linearLayoutManager)
        rvRestaurantList.setAdapter(adapter)

        businessRepository = BusinessRepositoryImpl(object : LoadBusinessesCallback {
            override fun onBusinessesLoaded(businesses: List<Businesses>, total: Int) {
                runOnUiThread {
                    isLoading = false
                    TOTAL_PAGES = total / 15
                    if (currentPage == TOTAL_PAGES) {
                        isLastPage = true
                    }
                    cpi_progress.setVisibility(View.GONE)
                    if (!businesses.isEmpty()) {
                        adapter!!.setRestaurants(businesses)
                        rvRestaurantList.setVisibility(View.VISIBLE)
                        tvNoResult.setVisibility(View.GONE)
                    } else {
                        rvRestaurantList.setVisibility(View.GONE)
                        tvNoResult.setVisibility(View.VISIBLE)
                    }
                }
            }

            override fun onDataNotAvailable() {
                Log.d("Activity", "API Data Not Available")
                cpi_progress.setVisibility(View.GONE)
                rvRestaurantList.setVisibility(View.GONE)
                tvNoResult.setVisibility(View.VISIBLE)
            }

            override fun onError(isCancel: Boolean) {
                if (!isCancel) {
                    cpi_progress.setVisibility(View.GONE)
                    rvRestaurantList.setVisibility(View.GONE)
                    tvNoResult.setVisibility(View.VISIBLE)
                }
            }
        })
        radiusSlider.addOnChangeListener(Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
            Log.d(TAG, "Radius : $value")
            if (value < 1000) {
                tvRadius.setText(String.format("%.0f M", value))
            } else {
                val km = (value / 1000).toDouble()
                tvRadius.setText(String.format("%.1f KM", km))
            }
            isLoading = false
            isLastPage = false
            distance = value.toInt()
            getBusinessByParameters(null, distance)
        })
        rvRestaurantList.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true
                currentPage += 1
                getBusinessByParameters(null, radiusSlider.getValue().toInt())
            }

            override fun isLastPage(): Boolean = isLastPage

            override fun isLoading(): Boolean = isLoading

        })
        getBusinessByParameters(null, distance)
    }

    private fun getBusinessByParameters(location: String?, radius: Int) {
        timer?.cancel()
        timer?.purge()

        timer = Timer()
        timerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val queryMap = HashMap<String, String>()
                    queryMap["term"] = "restaurants"
                    queryMap["location"] = "NYC"
                    queryMap["radius"] = "" + radius
                    queryMap["sort_by"] = "distance"
                    queryMap["limit"] = "15"
                    queryMap["offset"] = "" + currentPage * 15
                    cpi_progress.visibility = View.VISIBLE
                    tvNoResult.visibility = View.GONE
                    rvRestaurantList.visibility = View.GONE
                    businessRepository!!.getBusinesses(queryMap)
                }
            }
        }
        timer?.schedule(timerTask, 1000)
    }

    companion object {
        private val TAG = HomeActivity::class.java.name
        private const val PAGE_START = 0
    }
}