package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName

data class Center(
    @SerializedName("latitude") var latitude: Double,

    @SerializedName("longitude") var longitude: Double
)