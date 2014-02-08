package com.research.tools;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity {
private static Context context;
private boolean gpsOn = false;
private LocationManager locationManager;
private DeviceListener deviceListener;
//Thread screenThread = new Thread(){
//	private boolean running = false;
//	public void run(){
//		running = true;
//		long currentTime = System.nanoTime();
//		long lastTime = System.nanoTime();
//		while(running){
//			if(currentTime - lastTime > 100000){
//				deviceListener.updateGUI();
//				lastTime = System.nanoTime();
//			}	
//			currentTime = System.nanoTime();
//		}
//	}
//};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		MainActivity.context = getApplicationContext();
	}
	public void onGPSButtonClick(View v){
		Log.i("b","gps");
		if(!gpsOn){
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, deviceListener);
//		screenThread.run();
		} else {
			locationManager.removeUpdates(deviceListener);
//			screenThread.interrupt();
		}
		gpsOn = !gpsOn;
		return;
	}
	
	public static Context getContext(){
		return context;
	}
	@SuppressLint("NewApi")
	public void x (View v){
		Log.i("b","cell");
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		int cid = 0;
		int lac = 0;
		double lat = 0;
		double lon = 0;
		
		switch( telephonyManager.getPhoneType() ) {

	    case TelephonyManager.PHONE_TYPE_GSM: 
	         // Handle GSM phone
	         GsmCellLocation gsmLocation = (GsmCellLocation) telephonyManager.getCellLocation();
	         cid = gsmLocation.getCid();
	         lac = gsmLocation.getLac();
	         break;
	    case TelephonyManager.PHONE_TYPE_CDMA: 
	         // Handle CDMA phone
	    	CdmaCellLocation.requestLocationUpdate();
	        CdmaCellLocation cdmaLocation = (CdmaCellLocation) telephonyManager.getCellLocation();
	        CdmaCellLocation.requestLocationUpdate();
	        cid = (90*cdmaLocation.getBaseStationLatitude())/1296000;
	        lac = (90*cdmaLocation.getBaseStationLongitude()) / 1296000;
	        break;
	    default: 
	        // can't do cell location
		}
		
	    Log.i(Integer.toString(cid), Integer.toString(lac));
		return;
	}
	public void onAccelButtonClick(View v){
		return;
	}
	public void onCellButtonClick(View view){
	    Intent intent = new Intent(this, CellActivity.class);
	    startActivity(intent);
	}
	
}
