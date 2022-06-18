package com.example.yelpbusinessexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.yelpbusinessexample.data.model.Businesses;
import com.example.yelpbusinessexample.data.repository.BusinessRepository;
import com.example.yelpbusinessexample.data.repository.BusinessRepositoryImpl;
import com.example.yelpbusinessexample.ui.adapters.RestaurantAdapter;
import com.example.yelpbusinessexample.ui.extension.PaginationScrollListener;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.slider.Slider;

import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getName();
    private static final int PAGE_START = 0;
    private BusinessRepository businessRepository;
    private RecyclerView rvRestaurantList;
    private TextView tvRadius, tvNoResult;
    private RestaurantAdapter adapter;
    private Slider radiusSlider;
    private CircularProgressIndicator cpi_progress;
    private int distance = 100;
    private Timer timer;
    private TimerTask timerTask;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 5;
    private int currentPage = PAGE_START;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        rvRestaurantList = findViewById(R.id.recyclerView);
        radiusSlider = findViewById(R.id.radius_slider);
        tvRadius = findViewById(R.id.tv_radius_selected);
        tvNoResult = findViewById(R.id.tv_no_result);
        cpi_progress = findViewById(R.id.cpi_progress);

        adapter = new RestaurantAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvRestaurantList.setLayoutManager(linearLayoutManager);
        rvRestaurantList.setAdapter(adapter);

        businessRepository = new BusinessRepositoryImpl(new BusinessRepository.LoadBusinessesCallback() {
            @Override
            public void onBusinessesLoaded(List<Businesses> businesses,int total) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        isLoading = false;
                        TOTAL_PAGES = total / 15;
                        if (currentPage == TOTAL_PAGES) {
                            isLastPage = true;
                        }

                        cpi_progress.setVisibility(View.GONE);
                        if (!businesses.isEmpty()) {
                            adapter.setRestaurants(businesses);
                            rvRestaurantList.setVisibility(View.VISIBLE);
                            tvNoResult.setVisibility(View.GONE);
                        } else {
                            rvRestaurantList.setVisibility(View.GONE);
                            tvNoResult.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }

            @Override
            public void onDataNotAvailable() {
                Log.d("Activity", "API Data Not Available");
                cpi_progress.setVisibility(View.GONE);
                rvRestaurantList.setVisibility(View.GONE);
                tvNoResult.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(boolean isCancel) {
                if (!isCancel) {
                    cpi_progress.setVisibility(View.GONE);
                    rvRestaurantList.setVisibility(View.GONE);
                    tvNoResult.setVisibility(View.VISIBLE);
                }
            }
        });

        radiusSlider.addOnChangeListener((slider, value, fromUser) -> {
            Log.d(TAG, "Radius : " + value);
            if (value < 1000) {
                tvRadius.setText(String.format("%.0f M", value));
            } else {
                double km = value / 1000;
                tvRadius.setText(String.format("%.1f KM", km));
            }
            isLoading = false;
            isLastPage = false;
            distance = (int) value;
            getBusinessByParameters(null, distance);
        });

        rvRestaurantList.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                getBusinessByParameters(null, (int) radiusSlider.getValue());
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        getBusinessByParameters(null, distance);
    }

    private void getBusinessByParameters(String location, int radius) {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap<String, String> queryMap = new HashMap<>();
                        queryMap.put("term", "restaurants");
                        queryMap.put("location", "NYC");
                        queryMap.put("radius", "" + radius);
                        queryMap.put("sort_by", "distance");
                        queryMap.put("limit", "15");
                        queryMap.put("offset", "" + currentPage * 15);
                        cpi_progress.setVisibility(View.VISIBLE);
                        tvNoResult.setVisibility(View.GONE);
                        rvRestaurantList.setVisibility(View.GONE);
                        businessRepository.getBusinesses(queryMap);
                    }
                });
            }
        };

        timer.schedule(timerTask, 1000);
    }

}