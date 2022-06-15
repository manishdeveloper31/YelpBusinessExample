package com.example.yelpbusinessexample.data.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BusinessResponse{

	@SerializedName("total")
	private int total;

	@SerializedName("region")
	private Region region;

	@SerializedName("businesses")
	private List<Businesses> businesses;

	public void setTotal(int total){
		this.total = total;
	}

	public int getTotal(){
		return total;
	}

	public void setRegion(Region region){
		this.region = region;
	}

	public Region getRegion(){
		return region;
	}

	public void setBusinesses(List<Businesses> businesses){
		this.businesses = businesses;
	}

	public List<Businesses> getBusinesses(){
		return businesses;
	}
}