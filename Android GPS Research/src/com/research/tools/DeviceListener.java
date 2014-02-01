package com.research.tools;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class DeviceListener implements LocationListener{
	private Location lastLoc = null;
	private long timeStarted;
	private int updates = 0;
	private long timeTillFirstUpdate = -1;
	@Override
	public void onLocationChanged(Location location) {
		this.lastLoc = location;
		updates++;
		Log.i(Integer.toString(updates),Float.toString(lastLoc.getAccuracy()));
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		timeStarted = System.nanoTime();
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
	public long getTimeRunning(){
		return System.nanoTime() - timeStarted;
	}
}