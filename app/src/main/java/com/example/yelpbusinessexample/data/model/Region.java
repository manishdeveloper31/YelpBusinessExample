package com.example.yelpbusinessexample.data.model;

import com.google.gson.annotations.SerializedName;

public class Region{

	@SerializedName("center")
	private Center center;

	public void setCenter(Center center){
		this.center = center;
	}

	public Center getCenter(){
		return center;
	}
}