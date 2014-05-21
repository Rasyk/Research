package com.research.tools;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Building {
	private int id;
	private LatLng location;
	private String name;
	private String events;
	private MarkerOptions marker;
	
	public Building(int id,LatLng location , String name, String events){
		this.location = location;
		this.name = name;
		this.events = events;
		this.id = id;
		marker = new MarkerOptions();
		marker.position(location);
		marker.title(name);
		marker.snippet(events);
	}
	
	public Building (int id, LatLng location, String name){
		this.location = location;
		this.name  = name;
		this.id = id;
		marker = new MarkerOptions();
		marker.position(location);
		marker.title(name);
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
	
	public int getID (){
		return id;
	}
	
	public String getString(){
		return Integer.toString(id) + "\t" + Double.toString(location.latitude)+ "," +Double.toString(location.longitude) +"\t" +name+ "\t"+ events;
	}
}
