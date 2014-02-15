package com.research.tools;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.wink.json4j.JSONException;
import org.apache.wink.json4j.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	
private static Context context;

private boolean gpsOn = false;
private boolean wifiOn = false;
private boolean testOn = false;

private LocationManager locationManager;
private DeviceListener deviceListener;

private Timer timer;
private TimerTask timerTaskOne;
private TimerTask timerTaskTwo;

private ToggleButton gpsButton;
private ToggleButton wifiButton;
private ToggleButton cellButton;
private Button testButton;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_main);
		MainActivity.context = getApplicationContext();
		
		gpsButton = (ToggleButton) findViewById(R.id.gps_button);
		wifiButton = (ToggleButton) findViewById(R.id.wifi_button);
		cellButton = (ToggleButton) findViewById(R.id.cell_button);
		testButton = (Button) findViewById(R.id.test_button);
		
		gpsButton.setOnCheckedChangeListener(new GPSButtonListner());
		wifiButton.setOnCheckedChangeListener(new WifiButtonListner());
		cellButton.setOnCheckedChangeListener(new CellButtonListner());
		testButton.setOnClickListener(new TestButtonListener());
		
		
	}
	public void onGPSButtonClick(){
		Log.i("b","gps");
		wifiButton.setChecked(false);
		cellButton.setChecked(false);
		if(!gpsOn){
			CharSequence text = "Test started, please wait " + getTimeToRun() +" minutes.";
			if(getTimeToRun() < 1){
				text = "Test started.";
			}
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(0);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, deviceListener);
		final Handler handler = new Handler();
		timer = new Timer(false);
		timerTaskOne = new TimerTask() {
		    @Override
		    public void run() {
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		            	TextView lat = (TextView) findViewById(R.id.latitudeTextView);
		        		TextView lon = (TextView) findViewById(R.id.longitudeTextView);
		        		TextView time = (TextView) findViewById(R.id.gpsTimeTextView);
		        		TextView batChange = (TextView) findViewById(R.id.gpsBatteryLossTextView);
		        		TextView updateCount = (TextView) findViewById(R.id.gpsUpdatesTextView);
		        		TextView firstLock = (TextView) findViewById(R.id.gpsFirstLockTextView);
		    			
		        		if(deviceListener.getLocation() != null){
		        		lat.setText(Double.toString(deviceListener.getLocation().getLatitude()));
		        		lon.setText(Double.toString(deviceListener.getLocation().getLongitude()));
		        		}
		        		if(!deviceListener.getTimeTillFirstUpdate().equals("")) firstLock.setText(deviceListener.getTimeTillFirstUpdate());
		        		time.setText(deviceListener.getTimeRunning());
		        		batChange.setText(Integer.toString((int)deviceListener.getBatteryChange()) + "%");
		        		updateCount.setText(Integer.toString(deviceListener.getUpdateCount()));
		        		if(getTimeToRun() > 0 && (int)deviceListener.getTimeRunningLong() >= getTimeToRun() * 60){
		        			timer.cancel();
		        		}
		            }
		        });
		    }
		};
		timer.scheduleAtFixedRate(timerTaskOne, 0, 1000);
		} else {
			timer.cancel();
			locationManager.removeUpdates(deviceListener);
		}
		gpsOn = !gpsOn;
		return;
	}
	public int getTimeToRun(){
		EditText editText = (EditText) findViewById(R.id.timeToRunTextField);
		int timeToRun = -1;
        if(!editText.getText().toString().equals("")){
        	timeToRun = Integer.parseInt(editText.getText().toString());
        }
		return timeToRun;
	}
	public void onWifiButtonClick(){
		Log.i("b","wifi");
		gpsButton.setChecked(false);
		cellButton.setChecked(false);
		if(!wifiOn){	
			CharSequence text = "Test started, please wait " + getTimeToRun() +" minutes.";
			if(getTimeToRun() < 1){
				text = "Test started.";
			}
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(1);
		locationManager.removeUpdates(deviceListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, deviceListener);
		final Handler handler = new Handler();
		timer = new Timer(false);
		timerTaskOne = new TimerTask() {
		    @Override
		    public void run() {
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		            	
		            	
		            	TextView lat = (TextView) findViewById(R.id.latitudeTextView);
		        		TextView lon = (TextView) findViewById(R.id.longitudeTextView);
		        		TextView time = (TextView) findViewById(R.id.wifiTimeTextView);
		        		TextView batChange = (TextView) findViewById(R.id.wifiBatteryLossTextView);
		        		TextView updateCount = (TextView) findViewById(R.id.wifiUpdatesTextView);
		        		TextView firstLock = (TextView) findViewById(R.id.wifiFirstLockTextView);
		    			
		        		if(deviceListener.getLocation() != null){
		        		lat.setText(Double.toString(deviceListener.getLocation().getLatitude()));
		        		lon.setText(Double.toString(deviceListener.getLocation().getLongitude()));
		        		}
		        		if(!deviceListener.getTimeTillFirstUpdate().equals("")) firstLock.setText(deviceListener.getTimeTillFirstUpdate());
		        		time.setText(deviceListener.getTimeRunning());
		        		batChange.setText(Integer.toString((int)deviceListener.getBatteryChange()) + "%");
		        		updateCount.setText(Integer.toString(deviceListener.getUpdateCount()));
		        		if(getTimeToRun() > 0 && (int)deviceListener.getTimeRunningLong() >= getTimeToRun() * 60){
		        			timer.cancel();
		        		}
		            }
		        });
		    }
		};
		timer.scheduleAtFixedRate(timerTaskOne, 0, 1000);
		} else {
			locationManager.removeUpdates(deviceListener);
		}
		wifiOn = !wifiOn;
		return;
	}
	public void onTestButtonClick(){
		if(!testOn){
		Context context = getApplicationContext();
		CharSequence text = "Test started, please wait " + 2 * getTimeToRun() +" minutes.";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		deviceListener = new DeviceListener(1);
		locationManager.removeUpdates(deviceListener);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, deviceListener);
		final Handler handler = new Handler();
		timer = new Timer(false);
		timerTaskOne = new TimerTask() {
		    @Override
		    public void run() {
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		            	TextView lat = (TextView) findViewById(R.id.latitudeTextView);
		        		TextView lon = (TextView) findViewById(R.id.longitudeTextView);
		        		TextView time = (TextView) findViewById(R.id.wifiTimeTextView);
		        		TextView batChange = (TextView) findViewById(R.id.wifiBatteryLossTextView);
		        		TextView updateCount = (TextView) findViewById(R.id.wifiUpdatesTextView);
		        		TextView firstLock = (TextView) findViewById(R.id.wifiFirstLockTextView);
		    			
		        		if(deviceListener.getLocation() != null){
		        		lat.setText(Double.toString(deviceListener.getLocation().getLatitude()));
		        		lon.setText(Double.toString(deviceListener.getLocation().getLongitude()));
		        		}
		        		if(!deviceListener.getTimeTillFirstUpdate().equals("")) firstLock.setText(deviceListener.getTimeTillFirstUpdate());
		        		time.setText(deviceListener.getTimeRunning());
		        		batChange.setText(Integer.toString((int)deviceListener.getBatteryChange()) + "%");
		        		updateCount.setText(Integer.toString(deviceListener.getUpdateCount()));
		        		Log.i("hi", Double.toString((double)(deviceListener.getTimeRunningLong())));
		        		if(getTimeToRun() > 0 && (int)deviceListener.getTimeRunningLong() >= getTimeToRun() * 60){
		        			timer.cancel();
		        			timer = new Timer(false);
		        			locationManager.removeUpdates(deviceListener);
		        			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, deviceListener);
		        			deviceListener.setTimeStarted();
		        			timer.scheduleAtFixedRate(timerTaskTwo, 0, 1000);
		        			
		        		}
		            }
		        });
		        
		    }
		};
		timerTaskTwo = new TimerTask() {
		    @Override
		    public void run() {
		        handler.post(new Runnable() {
		            @Override
		            public void run() {
		            	TextView lat = (TextView) findViewById(R.id.latitudeTextView);
		        		TextView lon = (TextView) findViewById(R.id.longitudeTextView);
		        		TextView time = (TextView) findViewById(R.id.gpsTimeTextView);
		        		TextView batChange = (TextView) findViewById(R.id.gpsBatteryLossTextView);
		        		TextView updateCount = (TextView) findViewById(R.id.gpsUpdatesTextView);
		        		TextView firstLock = (TextView) findViewById(R.id.gpsFirstLockTextView);
		    			
		        		if(deviceListener.getLocation() != null){
		        		lat.setText(Double.toString(deviceListener.getLocation().getLatitude()));
		        		lon.setText(Double.toString(deviceListener.getLocation().getLongitude()));
		        		}
		        		if(!deviceListener.getTimeTillFirstUpdate().equals("")) firstLock.setText(deviceListener.getTimeTillFirstUpdate());
		        		time.setText(deviceListener.getTimeRunning());
		        		batChange.setText(Integer.toString((int)deviceListener.getBatteryChange()) + "%");
		        		updateCount.setText(Integer.toString(deviceListener.getUpdateCount()));
		        		if(getTimeToRun() > 0 && (int)deviceListener.getTimeRunningLong() >= getTimeToRun() * 60){
		        			timer.cancel();
		        		}
		            }
		        });
		        
		    }
		};
		timer.scheduleAtFixedRate(timerTaskOne, 0,1000);
		} else {
			Context context = getApplicationContext();
			CharSequence text = "Test canceled.";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			timer.cancel();
		}
		
		testOn = !testOn;
	}
	
	public void onCellButtonClick() throws JSONException{
		gpsButton.setChecked(false);
		wifiButton.setChecked(false);
		String[] huy = new String[2];
		Postmethod postmethod = new Postmethod(this);
		long startTime = System.currentTimeMillis();
		postmethod.execute(huy);
		
		while(true){
			
			if(postmethod.getresponseText() != null){
				long endTime = System.currentTimeMillis();
				long duration = (endTime- startTime)/ 1000;;
				String JSONResponse = postmethod.getresponseText();
				JSONObject response = new JSONObject(JSONResponse);
				
				
				double lattitude =  response.getDouble("lat");
				double longtitude = response.getDouble("lon");
				
				TextView lat = (TextView) findViewById(R.id.latitudeTextView);
				TextView lon = (TextView) findViewById(R.id.longitudeTextView);
				TextView firstLockTextView = (TextView) findViewById(R.id.cellFirstLockTextView);
				String durationText = Long.toString(duration / (60 * 60)) + ":" + Long.toString((duration / 60) % 60) + ":" + Long.toString(duration % 60 );

				firstLockTextView.setText(durationText);
				lat.setText(Double.toString(lattitude));
				lon.setText(Double.toString(longtitude));
				
				break;
			}
		}
		
		
//		Intent intent = new Intent(this, CellActivity.class);
	    //startActivity(intent);
	}
	
	public static Context getContext(){
		return context;
	}
	
	class GPSButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			onGPSButtonClick();
			if(!check){	
				resetView();
			}
		}
	}
	
	class WifiButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			onWifiButtonClick();
			if(!check){
				resetView();
			}
		}
	}
	
	class CellButtonListner implements OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(CompoundButton button, boolean check) {
			if(check)
			{	
				try {
					onCellButtonClick();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				resetView();
			}
		}
	}
	class TestButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Log.i("b","test");
			onTestButtonClick();
			
		}
		
	}
	
	public void resetView(){
		Intent intent = getIntent();
		finish();
			overridePendingTransition(17432576 , 17432579 );
			startActivity(intent);
		}
}
