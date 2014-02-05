package com.research.tools;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DeviceListener implements LocationListener{
	private Activity activity;
	
	private int updates = 0;
	
	private float initialBattery;
	
	private String timeTillFirstUpdate = "";
	private long timeStarted;
	public DeviceListener(Activity a){
		this.initialBattery = getCurrentBatteryLevel();
		this.activity = a;
		this.timeStarted = System.currentTimeMillis();
	}
	@Override
	public void onLocationChanged(Location location) {
		if(timeTillFirstUpdate.equals("")){
			TextView text1 = (TextView) activity.findViewById(R.id.timeTillFirstLockTextView);
			timeTillFirstUpdate = getTimeRunning();
			text1.setText(timeTillFirstUpdate + "s");
			
		}
		updates++;
		updateGUI(location);
		
	}
	public void updateGUI(Location location){
		Log.i("ping","ping");
		TextView lat = (TextView) activity.findViewById(R.id.latitudeTextView);
		TextView lon = (TextView) activity.findViewById(R.id.longitudeTextView);
		TextView gpsTime = (TextView) activity.findViewById(R.id.gpsTimeTextView);
		TextView batChange = (TextView) activity.findViewById(R.id.batteryChangeTextView);
		TextView updateCount = (TextView) activity.findViewById(R.id.updatesTextView);
		
		lat.setText(Double.toString(location.getLatitude()));
		lon.setText(Double.toString(location.getLongitude()));
		gpsTime.setText(getTimeRunning());
		batChange.setText(Integer.toString((int)getBatteryChange()) + "%");
		updateCount.setText(Integer.toString(updates));
		
	}
	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}
	public float getCurrentBatteryLevel(){
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = MainActivity.getContext().registerReceiver(null, ifilter);
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		return  level;
		
	}
	
	@Override
	public void onProviderEnabled(String provider) {
		return;
	}
	
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		
	}
	public int getUpdateCount(){
		return updates;
	}
	public String getTimeRunning(){ 
		long temp = (System.currentTimeMillis() - timeStarted) / 1000;
		String ret = Long.toString(temp / (60 * 60)) + ":" + Long.toString((temp / 60) % 60) + ":" + Long.toString(temp % 60 );
		return ret;
	}
	public float getBatteryChange(){
		return initialBattery - getCurrentBatteryLevel();
	}
}