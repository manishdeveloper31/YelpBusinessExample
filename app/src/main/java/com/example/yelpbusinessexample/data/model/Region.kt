package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName
import com.example.yelpbusinessexample.data.model.Center

data class Region(
    @SerializedName("center") var center: Center? = null
)