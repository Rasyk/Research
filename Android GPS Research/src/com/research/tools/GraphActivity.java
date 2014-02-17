package com.research.tools;

import com.jjoe64.graphview.*;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;


import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;

public class GraphActivity extends Activity {
	LineGraphView graphView;
	GraphViewSeries seriesSin;
	GraphViewSeries seriesCos;
	LinearLayout layout;
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}

}
