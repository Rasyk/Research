package com.research.tools;

import com.google.android.gms.maps.model.LatLng;

public class Building {
	private LatLng location;
	private String name;
	private String events;
	
	public Building(LatLng location , String name, String events){
		this.location = location;
		this.name = name;
		this.events = events;
	}
	
	public Building (LatLng location, String name){
		this.location = location;
		this.name  = name;
	}
	
	public LatLng getLocation(){
		return location;
	}
	
	public String getName(){
		return name;
	}
	
	public String getEvent(){
		return events;
	}
	
	public void setEvent(String events){
		this.events = events;
	}
}
