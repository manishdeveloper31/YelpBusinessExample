package com.example.yelpbusinessexample.data.model

import com.google.gson.annotations.SerializedName
import com.example.yelpbusinessexample.data.model.Coordinates
import com.example.yelpbusinessexample.data.model.CategoriesItem

data class Businesses(
    @SerializedName("distance") var distance: Double,
    @SerializedName("image_url") var imageUrl: String? = null,
    @SerializedName("rating") var rating: Double,
    @SerializedName("coordinates") var coordinates: Coordinates? = null,
    @SerializedName("review_count") var reviewCount: Double,
    @SerializedName("transactions") var transactions: List<String>? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("display_phone") var displayPhone: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("alias") var alias: String? = null,
    @SerializedName("location") var location: Location? = null,
    @SerializedName("id") var id: String? = null,
    @SerializedName("categories") var categories: List<CategoriesItem>? = null,
    @SerializedName("is_closed") var isIsClosed: Boolean
)