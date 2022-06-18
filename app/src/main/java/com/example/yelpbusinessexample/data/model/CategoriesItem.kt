package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName

data class CategoriesItem(
    @SerializedName("alias") var alias: String? = null,

    @SerializedName("title") var title: String? = null
)