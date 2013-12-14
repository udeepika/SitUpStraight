package com.example.test_android;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;

public class Linegraph {

	public Intent getIntent(Context context) {
		XYSeries xSeries = new XYSeries("Change in the angle of the back");
		XYSeries xSeries1 = new XYSeries("Dates");
		XYSeries xSeries2 = new XYSeries("Bad Posture Threshold");

		for (int i = 0; i < MainActivity.angle_values_arr.length; i++) {
			
			xSeries.add(i + 1, (Integer) MainActivity.angle_values_arr[i]);

		}

		xSeries1.add(MainActivity.dates_count[0], 0);
		List temp = Arrays.asList(MainActivity.angle_values_arr);
		@SuppressWarnings("unchecked")
		int max = Collections.max(temp);
		xSeries1.add(MainActivity.dates_count[0], max);
		if (MainActivity.dates_count.length > 1) {
			int val = MainActivity.dates_count[0];
			
			for (int i = 1; i < MainActivity.dates_count.length - 1; i++) {
				xSeries1.add(MainActivity.dates_count[i - 1], 0);
				val += MainActivity.dates_count[i];
				xSeries1.add(val, 0);
				xSeries1.add(val, max);
			}
		}

		xSeries2.add(0, 15);
		xSeries2.add((Integer) MainActivity.angle_values_arr.length, 15);
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		dataset.addSeries(xSeries);
		dataset.addSeries(xSeries1);
		dataset.addSeries(xSeries2);

		XYSeriesRenderer Xrenderer = new XYSeriesRenderer();
		Xrenderer.setColor(Color.GREEN);
		Xrenderer.setPointStyle(PointStyle.DIAMOND);
		Xrenderer.setDisplayChartValues(true);
		Xrenderer.setChartValuesTextSize(18);
		Xrenderer.setLineWidth(4);
		Xrenderer.setFillPoints(true);

		XYSeriesRenderer Xrenderer1 = new XYSeriesRenderer();
		Xrenderer1.setColor(Color.MAGENTA);
		Xrenderer1.setPointStyle(PointStyle.DIAMOND);
		Xrenderer1.setDisplayChartValues(false);
		Xrenderer1.setLineWidth(4);
		Xrenderer1.setFillPoints(true);

		XYSeriesRenderer Xrenderer2 = new XYSeriesRenderer();
		Xrenderer2.setColor(Color.RED);
		Xrenderer2.setPointStyle(PointStyle.DIAMOND);
		Xrenderer2.setDisplayChartValues(false);
		Xrenderer2.setLineWidth(6);
		Xrenderer2.setFillPoints(true);

		XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

		
		mRenderer.setMargins(new int[] { 10, 40, 60, 10 });
		mRenderer.setLegendTextSize(24);
		mRenderer.setLabelsTextSize(22);
        mRenderer.setXTitle("Time");
		mRenderer.setXAxisMin(1);
		mRenderer.setXAxisMax(MainActivity.angle_values_arr.length);
		mRenderer.setYTitle("Back Angle");
		mRenderer.setAxisTitleTextSize(22);
		mRenderer.setZoomButtonsVisible(true);
		mRenderer.setXLabels(0);
		mRenderer.setPanEnabled(true);
		mRenderer.setXLabelsAlign(Align.RIGHT);
		mRenderer.setXLabelsAngle(45);
		mRenderer.setApplyBackgroundColor(true);
		mRenderer.setBackgroundColor(Color.BLACK);
		mRenderer.setShowGrid(true);
		mRenderer.setClickEnabled(false);
		mRenderer.setShowGridX(true);
		mRenderer.setShowGridY(true);

		mRenderer.addSeriesRenderer(Xrenderer);
		mRenderer.addSeriesRenderer(Xrenderer1);
		mRenderer.addSeriesRenderer(Xrenderer2);

		mRenderer.addXTextLabel(MainActivity.dates_count[0],
				MainActivity.dates_set_line[0]);
		if (MainActivity.dates_count.length > 1) {
			for (int j = 1; j < MainActivity.dates_set_line.length; j++) {
				mRenderer.addXTextLabel(MainActivity.dates_count[j]
						+ MainActivity.dates_count[j - 1],
						MainActivity.dates_set_line[j]);
			}
		}
		
		
		mRenderer.addXTextLabel(1,
				(String) MainActivity.time_values_arr[0]);
		int count = 1;
				while(count <= MainActivity.angle_values_arr.length)
		{
			count+=10;
			if(count <= MainActivity.angle_values_arr.length)
				mRenderer.addXTextLabel(count,
					(String) MainActivity.time_values_arr[count-1]);
			
			else
				mRenderer.addXTextLabel(count,
						(String) MainActivity.time_values_arr[MainActivity.angle_values_arr.length-1]);
		}
		Intent intent = ChartFactory.getLineChartIntent(context, dataset,
				mRenderer, "Posture Angle");
		return intent;

	}

}
