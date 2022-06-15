package com.example.yelpbusinessexample.data.model;

import com.google.gson.annotations.SerializedName;

public class CategoriesItem{

	@SerializedName("alias")
	private String alias;

	@SerializedName("title")
	private String title;

	public void setAlias(String alias){
		this.alias = alias;
	}

	public String getAlias(){
		return alias;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}