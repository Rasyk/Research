package com.research.tools;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DeviceListener implements LocationListener{
	private Activity activity;
	private Location lastLoc = null;
	private long timeStarted = System.currentTimeMillis();
	private int updates = 0;
	private long timeTillFirstUpdate = -1;
	public DeviceListener(Activity a){
		this.activity = a;
	}
	@Override
	public void onLocationChanged(Location location) {
		this.lastLoc = location;
		updates++;
		TextView text = (TextView) activity.findViewById(R.id.latitudeTextView);
		TextView text2 = (TextView) activity.findViewById(R.id.longitudeTextView);
		TextView text3 = (TextView) activity.findViewById(R.id.gpsTimeTextView);
		text.setText(Double.toString(location.getLatitude()));
		text2.setText(Double.toString(location.getLongitude()));
		text3.setText(getTimeRunning());
		
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		return;
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	public Location getLastLocation(){
		
		return new Location(lastLoc);
	}
	public int getUpdateCount(){
		return updates;
	}
	public String getTimeRunning(){ 
		long temp = (System.currentTimeMillis() - timeStarted) / 1000;
		String ret = Long.toString(temp / (60 * 60)) + ":" + Long.toString((temp / 60) % 60) + ":" + Long.toString(temp % 60 );
		return ret;
	}
}