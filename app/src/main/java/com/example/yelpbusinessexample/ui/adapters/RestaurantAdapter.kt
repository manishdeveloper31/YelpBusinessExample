package com.example.yelpbusinessexample.ui.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.yelpbusinessexample.ui.adapters.RestaurantAdapter.RestaurantViewHolder
import com.example.yelpbusinessexample.data.model.Businesses
import android.view.ViewGroup
import android.view.View
import android.view.LayoutInflater
import com.example.yelpbusinessexample.R
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.widget.TextView
import java.util.ArrayList

class RestaurantAdapter(private val context: Context) : RecyclerView.Adapter<RestaurantViewHolder>() {

    private val restaurants: MutableList<Businesses> = ArrayList()

    fun setRestaurants(restaurants: List<Businesses>) {
        restaurants.let {
            this.restaurants.addAll(restaurants)
            notifyDataSetChanged()
        }
    }

    fun clearList() {
        restaurants.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_restaurant_item_row, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val businesses = restaurants[position]
        businesses.let {
            holder.tvName.text = it.name
            holder.tvAddr.text = it.location!!.address1
            holder.tvStatus.text = if (it.isIsClosed) "CLOSED" else "Currently OPEN"
            holder.tvRating.text = "${it.rating}"
            Glide.with(context).load(it.imageUrl).into(holder.iv_logo)
        }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    inner class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var iv_logo: ImageView
        var tvName: TextView
        var tvAddr: TextView
        var tvStatus: TextView
        var tvRating: TextView

        init {
            iv_logo = itemView.findViewById(R.id.iv_restaurant_dish)
            tvName = itemView.findViewById(R.id.tv_restaurant_name)
            tvAddr = itemView.findViewById(R.id.tv_restaurant_addr)
            tvStatus = itemView.findViewById(R.id.tv_status)
            tvRating = itemView.findViewById(R.id.tvRating)
        }
    }
}