package com.example.yelpbusinessexample.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.yelpbusinessexample.R;
import com.example.yelpbusinessexample.data.model.Businesses;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private Context context;
    private List<Businesses> restaurants;

    public RestaurantAdapter(Context context) {
        this.context = context;
        this.restaurants = new ArrayList<>();
    }

    public void setRestaurants(List<Businesses> restaurants) {
        this.restaurants.addAll(restaurants);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant_item_row,parent,false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Businesses businesses = restaurants.get(position);
        if (businesses != null) {
            holder.tvName.setText(businesses.getName());
            holder.tvAddr.setText(businesses.getLocation().getAddress1());
            holder.tvStatus.setText(businesses.isIsClosed() ? "CLOSED" : "Currently OPEN");
            holder.tvRating.setText("" + businesses.getRating());
            Glide.with(context).load(businesses.getImageUrl()).into(holder.iv_logo);
        }
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    class RestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_logo;
        TextView tvName, tvAddr, tvStatus, tvRating;

        public RestaurantViewHolder(View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv_restaurant_dish);
            tvName = itemView.findViewById(R.id.tv_restaurant_name);
            tvAddr = itemView.findViewById(R.id.tv_restaurant_addr);
            tvStatus = itemView.findViewById(R.id.tv_status);
            tvRating = itemView.findViewById(R.id.tvRating);
        }
    }
}
