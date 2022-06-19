package com.example.yelpbusinessexample.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpbusinessexample.data.model.Businesses
import com.google.android.material.progressindicator.CircularProgressIndicator

@BindingAdapter("radiusValue")
fun TextView.setRadius(value : Int) {
    text = if (value < 1000) {
        String.format("%d M", value)
    } else {
        val km = value / 1000
        String.format("%d KM", km)
    }
}

@BindingAdapter("listValue")
fun TextView.setVisibility(value : List<Businesses>?) {
    visibility = if (value?.isNotEmpty() == true) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("isLoading")
fun CircularProgressIndicator.setVisibility(visible : Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}

@BindingAdapter("listValue")
fun RecyclerView.setVisibility(value : List<Businesses>?) {
    visibility = if (value?.isNotEmpty() == true) {
        View.VISIBLE
    } else {
        View.GONE
    }
}