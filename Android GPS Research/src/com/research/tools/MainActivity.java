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
import android.widget.CompoundButton;
import android.widget.ToggleButton;
import android.content.Intent;
import android.widget.CompoundButton.OnCheckedChangeListener;
import 	android.R.anim;

public class MainActivity extends Activity {
private static Context context;
private boolean gpsOn = false;
private boolean wifiOn = false;
private LocationManager locationManager;
private DeviceListener deviceListener;
private ToggleButton gpsButton;
private ToggleButton wifiButton;
private ToggleButton cellButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		MainActivity.context = getApplicationContext();
		
		gpsButton = (ToggleButton) findViewById(R.id.gps_button);
		wifiButton = (ToggleButton) findViewById(R.id.wifi_button);
		cellButton = (ToggleButton) findViewById(R.id.cell_button);
		
		gpsButton.setOnCheckedChangeListener(new GPSButtonListner());
		wifiButton.setOnCheckedChangeListener(new WifiButtonListner());
		cellButton.setOnCheckedChangeListener(new CellButtonListner());
		
	}
	public void onGPSButtonClick(){
		Log.i("b","gps");
		wifiButton.setChecked(false);
		cellButton.setChecked(false);
		if(!wifiOn){
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(this,0);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, deviceListener);
		} else {
			locationManager.removeUpdates(deviceListener);
		}
		gpsOn = !gpsOn;
		return;
	}
	public void onWifiButtonClick(){
		Log.i("b","wifi");
		gpsButton.setChecked(false);
		cellButton.setChecked(false);
		if(!wifiOn){
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(this,1);
		locationManager.removeUpdates(deviceListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, deviceListener);
		} else {
			locationManager.removeUpdates(deviceListener);
		}
		wifiOn = !wifiOn;
		return;
	}
	
	public void onAccelButtonClick(){
		return;
	}
	
	public void onCellButtonClick(){
		gpsButton.setChecked(false);
		wifiButton.setChecked(false);
	    Intent intent = new Intent(this, CellActivity.class);
	    startActivity(intent);
	}
	
	public static Context getContext(){
		return context;
	}
	
	class GPSButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			if(check){	
				onGPSButtonClick();
			}
			else {
				resetView();
			}
		}
	}
	
	class WifiButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			if(check)
			{	
				onWifiButtonClick();
			}
			else {
				resetView();
			}
		}
	}
	
	class CellButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			if(check)
			{	
				onCellButtonClick();
			}
			else {
				resetView();
			}
		}
	}

	public void resetView(){
		Intent intent = getIntent();
		finish();
		overridePendingTransition(17432576 , 17432579 );
		startActivity(intent);
	}
}
