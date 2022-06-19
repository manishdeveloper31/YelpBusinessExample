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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.yelpbusinessexample.databinding.ActivityHomeBinding
import com.example.yelpbusinessexample.ui.extension.PaginationScrollListener
import com.example.yelpbusinessexample.ui.viewmodel.HomeViewModel
import com.example.yelpbusinessexample.ui.viewmodel.HomeViewModelFactory
import java.util.HashMap

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding
    private var adapter: RestaurantAdapter? = null
    private lateinit var viewModel : HomeViewModel

    companion object {
        private val TAG = HomeActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        viewModel = ViewModelProvider(this, HomeViewModelFactory(BusinessRepositoryImpl())).get(HomeViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = RestaurantAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager  = linearLayoutManager
        binding.recyclerView.adapter = adapter

        viewModel.businessList.observe(this, Observer {
            adapter!!.setRestaurants(it)
        })

        binding.radiusSlider.addOnChangeListener(Slider.OnChangeListener { slider: Slider?, value: Float, fromUser: Boolean ->
            viewModel.currentPage.value = 0;
            viewModel.radiusValue.value = value.toInt()
            adapter!!.clearList()
            viewModel.getBusiness()
        })

        binding.recyclerView.addOnScrollListener(object :
            PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                viewModel.currentPage.value = viewModel.currentPage.value?.inc()
                viewModel.getBusiness()
            }

            override fun isLastPage(): Boolean = viewModel.isLastPage.value == true

            override fun isLoading(): Boolean = viewModel.isLoading.value == true

        })
        viewModel.getBusiness()
    }
}