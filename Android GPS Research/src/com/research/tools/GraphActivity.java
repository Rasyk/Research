package com.research.tools;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.content.Intent;

public class GraphActivity extends Activity {
	LineGraphView graphView;
	GraphViewSeries seriesSin;
	GraphViewSeries seriesCos;
	LinearLayout layout;
	ArrayList<Double> seated;
	ArrayList<Double> running;
	ArrayList<Double> vehicle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.graph);
		// first init data
		// sin curve
		int num = 150;
		GraphViewData[] data = new GraphViewData[num];
		double v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(v));
		}
		seriesSin = new GraphViewSeries("Sinus curve", new GraphViewSeriesStyle(Color.rgb(200, 50, 00),5), data);
		 
		// cos curve
		data = new GraphViewData[num];
		v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.cos(v));
		}
		seriesCos = new GraphViewSeries("Cosinus curve", new GraphViewSeriesStyle(Color.rgb(90, 250, 00),5), data);
		 
		// random curve
		num = 1000;
		data = new GraphViewData[num];
		v=0;
		for (int i=0; i<num; i++) {
		   v += 0.2;
		   data[i] = new GraphViewData(i, Math.sin(Math.random()*v));
		}
		GraphViewSeries seriesRnd = new GraphViewSeries("Random curve", null, data);
		 
		/*
		 * create graph
		 */
		graphView = new LineGraphView(
		      this
		      , "GraphViewDemo"
		);
		// add data
		graphView.addSeries(seriesCos);
		graphView.addSeries(seriesSin);
		//graphView.addSeries(seriesRnd);
		// optional - set view port, start=2, size=10
		graphView.setViewPort(2, 10);
		graphView.setScalable(true);
		// optional - legend
		graphView.setShowLegend(true);
		 
		layout = (LinearLayout) findViewById(R.id.graph1);
		layout.addView(graphView);
	}

	
	public void drawBatteryConsumption(View v) {
		graphView = new LineGraphView(
			      this
			      , "GraphViewDemo"
			);
		// add data
		graphView.addSeries(seriesCos);
		layout.removeAllViews();
		layout.addView(graphView);
	}
	
	public void drawVelocityActivity(View v)
	{
		graphView = new LineGraphView(
			      this
			      , "GraphViewDemo"
		);
		GraphViewSeries seatedVelocity;
		GraphViewSeries runningVelocity;
		GraphViewSeries vehicleVelocity;
		
		if((seated = createVelocityDataSet(1)) != null && (running = createVelocityDataSet(2)) != null && (vehicle = createVelocityDataSet(3)) != null ){
			seatedVelocity  = new GraphViewSeries("Seated Velocity",new GraphViewSeriesStyle(Color.rgb(200, 50, 00),5), createGraphViewData(seated) );
			graphView.addSeries(seatedVelocity);
			runningVelocity = new GraphViewSeries("Seated Velocity",new GraphViewSeriesStyle(Color.rgb(90, 250, 00),5), createGraphViewData(running) );
			graphView.addSeries(runningVelocity);
			vehicleVelocity = new GraphViewSeries("Seated Velocity",new GraphViewSeriesStyle(Color.rgb( 47, 17, 245),5), createGraphViewData(vehicle) );
			graphView.addSeries(vehicleVelocity);
		}
		
			
		graphView.setViewPort(2, 70);
		graphView.setScalable(true);
		// optional - legend
		graphView.setShowLegend(true);
			 
		layout.removeAllViews();
		layout.addView(graphView);
		
	}

	private GraphViewData[] createGraphViewData(ArrayList<Double> yCoor) {
		GraphViewData[] data = new GraphViewData[yCoor.size()];
		double xCoor=0;
		for (int i=0; i<yCoor.size(); i++) {
		   xCoor += 0.2;
		   data[i] = new GraphViewData(xCoor, yCoor.get(i));
		}
		return data;
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}
	
	public ArrayList<Double> createVelocityDataSet(int option)
	{
		FileReader fr = null;
		try{
			if(option ==1){
				fr = new FileReader("/mnt/sdcard/Download/Seateddata1111111.txt");
			}
			else if(option ==2){
				fr = new FileReader("/mnt/sdcard/Download/Runningdata1111111.txt");
			}else {
				fr = new FileReader("/mnt/sdcard/Download/Vehicledata1111111.txt");
			}
		}
		catch(FileNotFoundException e)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Not enough data to create the Graph")
			       .setCancelable(false)
			       .setPositiveButton("Go Back", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   Intent intent = new Intent(GraphActivity.this, MainActivity.class);
			       			startActivity(intent);
			           }
			       })
			       .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			                dialog.cancel();
			           }
			       });
			builder.show();
			return null;
		}
		
		BufferedReader br =null;
		
		try{
			String data;
			br = new BufferedReader(fr);
			ArrayList<Double> result = new ArrayList<Double>();
			while((data = br.readLine())!=null){
				result.add(Double.parseDouble(data));
			}
			return result;
		}catch(IOException e){
			e.printStackTrace();
		}
		finally{
			try{
				if(br != null){
					br.close();
				}
			}catch(IOException ex){
				ex.printStackTrace();
			}
			
		}
		return null;
		
	}

}
