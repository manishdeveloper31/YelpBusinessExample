package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName

data class Location(
    @SerializedName("country") var country: String? = null,

    @SerializedName("address3") var address3: String? = null,

    @SerializedName("address2") var address2: String? = null,

    @SerializedName("city") var city: String? = null,

    @SerializedName("address1") var address1: String? = null,

    @SerializedName("display_address") var displayAddress: List<String>? = null,

    @SerializedName("state") var state: String? = null,

    @SerializedName("zip_code") var zipCode: String? = null
)