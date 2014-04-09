package com.research.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.location.Address;
import android.location.Geocoder;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.SnapshotReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;

public class MapMssgActivity extends FragmentActivity  {

    // Google Map
    private GoogleMap googleMap;
    private MarkerOptions markerOptions;
    private ArrayList<MarkerOptions> algorithmCoordinatesArrayList;
    private ArrayList<MarkerOptions> apiCoordinatesArrayList;
    private LatLng latLng;
    Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_mssg);

        try {
            // Loading map
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }
        button_listener();
        button_listener2();

        //getting btn_find_location
        Button btn_find = (Button) findViewById(R.id.btn_find_location);
        Button get_current_location = (Button) findViewById(R.id.get_current_location);

        //Define the listener to btn_find_location
        OnClickListener findClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting reference to EditText to get the user input location
                EditText etLocation = (EditText) findViewById(R.id.et_location);

                // Getting user input location
                String location = etLocation.getText().toString();

                if(location!=null && !location.equals("")){
                    new GeocoderTask().execute(location);
                }
            }
        };
        OnClickListener getCurrentListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
            	centerMapOnMyLocation();
            }
        };
        btn_find.setOnClickListener(findClickListener);
        get_current_location.setOnClickListener(getCurrentListener);
        
       if(getIntent().getStringExtra("location") != null){
    	   String[] locationStrings = getIntent().getStringExtra("location").split("M");
    	   LatLng lattlongg = new LatLng(Double.parseDouble(locationStrings[0]),Double.parseDouble(locationStrings[1]));
    	   
    	   markerOptions = new MarkerOptions();
           markerOptions.position(lattlongg);
           markerOptions.title("here");

           googleMap.addMarker(markerOptions);
    	   googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lattlongg,
                   (float) 17.0));
    	   
       }


    }

    /**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
        //centerMapOnMyLocation();
        googleMap.setMyLocationEnabled(false);
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    private void centerMapOnMyLocation() {

        googleMap.setMyLocationEnabled(false);
        Location location = getLocation();
        LatLng myLocation = null;
        if (location != null) {
            myLocation = new LatLng(location.getLatitude(),
                    location.getLongitude());
            markerOptions = new MarkerOptions();
            markerOptions.position(myLocation);
            markerOptions.title("you");

            googleMap.addMarker(markerOptions);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                    (float) 8.0));
        }
        else
        {
            //do nothing
        }

    }

    void button_listener() {
        Button button = (Button) findViewById(R.id.algorithm_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * Open the text file and put markers in there
                 */
            	createMarkers(1);
            	
            }
        });
    }
    
    void button_listener2() {
        Button button = (Button) findViewById(R.id.api_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * open the text file and put markers in there
                 */
            	createMarkers(2);

            }
        });
    }
    
    public void createMarkers(int option)
    {
    	FileReader fr = null;
    	try{
	    	if(option == 1){
	    		fr = new FileReader("/mnt/sdcard/Download/AlgorithmLocation1111.txt");
	    	}
	    	else {
	    		fr = new FileReader("/mnt/sdcard/Download/GPSlocation1111.txt");
			}
    	}catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
    	
    	BufferedReader br = null;
    	
    	try{
    		String dataString;
    		br = new BufferedReader(fr);
    		LatLng lattlongg = new LatLng(37.090240,-95.712891);
    		while((dataString = br.readLine()) !=null){
    			MarkerOptions coordinate = new MarkerOptions();
    			lattlongg = new LatLng(Double.parseDouble(dataString),Double.parseDouble(br.readLine()));
    	    	   
    			coordinate = new MarkerOptions();
    			coordinate.position(lattlongg);
    			coordinate.title("here");

    			googleMap.addMarker(coordinate);
    		}
    		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lattlongg,
                    (float) 8.0));
    	}catch(IOException e){
			e.printStackTrace();
		}finally{
			try{
				if(br != null){
					br.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
    }
    
    public Location getLocation(){
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Activity.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
               
                public void onLocationChanged(Location location){
                        makeUseOfNewLocation(location);
                        
                }

                @Override
                public void onProviderDisabled(String provider) {
                        // TODO Auto-generated method stub
                       
                }
                @Override
                public void onProviderEnabled(String provider) {
                        // TODO Auto-generated method stub
                       
                }
                @Override
                public void onStatusChanged(String provider, int status,
                                Bundle extras) {
                        // TODO Auto-generated method stub
                       
                }
        };
	    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,40*1000,2, locationListener);
	       
	    if(location != null){
	    	return location;
	    }
	    else {
	    	return locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
	    }
	}
	
	private void makeUseOfNewLocation(Location location){
	        this.location = location;
	        
	}

    private class GeocoderTask extends AsyncTask<String, Void, List<Address>>{

        @Override
        protected List<Address> doInBackground(String... locationName) {
            Geocoder geocoder = new Geocoder(getBaseContext());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocationName(locationName[0],3);
            }catch(IOException e){
                e.printStackTrace();
            }
            return addresses;

        }

        @Override
        protected void onPostExecute(List<Address> addresses)
        {
            if(addresses==null || addresses.size()==0){
                Toast.makeText(getBaseContext(), "No Location found", Toast.LENGTH_SHORT).show();
            }

            // Clears all the existing markers on the map

            // Adding Markers on Google Map for each matching address
            for(int i=0;i<addresses.size();i++){

                Address address = (Address) addresses.get(i);

                // Creating an instance of GeoPoint, to display in Google Map
                latLng = new LatLng(address.getLatitude(), address.getLongitude());

                String addressText = String.format("%s, %s",
                address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "",
                address.getCountryName());

                markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(addressText);

                googleMap.addMarker(markerOptions);

                // Locate the first location
                if(i==0)
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,
                            (float) 8.0));
            }

        }

    }


}