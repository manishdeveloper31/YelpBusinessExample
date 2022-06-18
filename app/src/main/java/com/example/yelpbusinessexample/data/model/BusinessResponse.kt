package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName
import com.example.yelpbusinessexample.data.model.Businesses

data class BusinessResponse(
    @SerializedName("total") var total : Int,

    @SerializedName("region") var region: Region? = null,

    @SerializedName("businesses") var businesses: List<Businesses>? = null
)