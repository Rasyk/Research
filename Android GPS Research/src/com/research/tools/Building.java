package com.research.tools;

import com.google.android.gms.maps.model.LatLng;

public class Building {
	private LatLng location;
	private String name;
	
	public Building(LatLng location , String name){
		this.location = location;
		this.name = name;
	}
	
	public LatLng getLocation(){
		return location;
	}
	
	public String getName(){
		return name;
	}
}
