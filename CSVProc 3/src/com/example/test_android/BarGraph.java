package com.example.test_android;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class BarGraph {
	StringBuffer sb;
	public Intent getIntent(Context context) {

		
		CategorySeries series = new CategorySeries("Beep Count");
		System.out.println(MainActivity.beeps_count[1]);
		for (int i = 0; i < MainActivity.beeps_count.length; i++) {
			System.out.println(MainActivity.beeps_count[i]);
			series.add("Bar" + (i + 1), MainActivity.beeps_count[i]);
		}

		XYMultipleSeriesDataset dataSet = new XYMultipleSeriesDataset(); // collection
																			// of
																			// series
																			// under
																			// one
																			// object.,there
																			// could
																			// any
		dataSet.addSeries(series.toXYSeries()); // number of series

		// customization of the chart

		XYSeriesRenderer renderer = new XYSeriesRenderer(); // one renderer for
															// one series
		
		renderer.setDisplayChartValues(true);
		renderer.setChartValuesTextSize(18);
		renderer.setChartValuesSpacing((float) 2.5d);
		renderer.setLineWidth((float) 10d);
		renderer.setColor(Color.GREEN);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer(); // collection
																				// multiple
																				// values
																				// for
																				// one
																				// renderer
																				// or
																				// series
		mRenderer.addSeriesRenderer(renderer);
		mRenderer.setChartTitle("Posture Graph");
		mRenderer.setChartTitleTextSize(24);
		mRenderer.setXTitle("Dates");
		mRenderer.setYTitle("Count");
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setShowLegend(true);
		mRenderer.setShowGridX(true); // this will show the grid in graph
		mRenderer.setShowGridY(true);
		
		mRenderer.setBarSpacing(.1); // adding spacing between the line or
										// stacks
		mRenderer.setLegendTextSize(20);
		mRenderer.setBarWidth((float) 18.0d);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setXAxisMin(0);
		
		mRenderer.setXAxisMax(MainActivity.dates_set.length);
		mRenderer.setYAxisMax(MainActivity.max + 20);
		mRenderer.setAxisTitleTextSize(22);
		mRenderer.setAxesColor(Color.BLUE);
		mRenderer.setXLabels(0);
		mRenderer.setXLabelsAngle(45);
		for (int j = 0; j < MainActivity.dates_set.length; j++) {
			
			
			mRenderer.addXTextLabel(j + 1, MainActivity.dates_set[j]);
		}
		mRenderer.setMargins(new int[] { 10, 40, 60, 10 });
		mRenderer.setLabelsTextSize(20);
		mRenderer.setPanEnabled(true, true); // will fix the chart position
		Intent intent = ChartFactory.getBarChartIntent(context, dataSet,
				mRenderer, Type.DEFAULT);

		return intent;
	}

}
