package com.example.test_android;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DialRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.DialRenderer.Type;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class DialGraph {
	public Intent execute(Context context) {
		CategorySeries category = new CategorySeries("Posture Score");
		category.add("Current Score", MainActivity.posture_score);
		DialRenderer renderer = new DialRenderer();
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(25);
		renderer.setLegendTextSize(30);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		if (MainActivity.posture_score < 50) {
			r.setColor(Color.RED);
		} else {
			r.setColor(Color.GREEN);
		}
		renderer.addSeriesRenderer(r);
				renderer.setLabelsTextSize(25);
		renderer.setLabelsColor(Color.BLUE);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.BLACK);
		renderer.setShowLabels(true);
		renderer.setVisualTypes(new DialRenderer.Type[] { Type.NEEDLE,
				Type.ARROW });
		renderer.setMinValue(0);
		renderer.setMaxValue(100);
		return ChartFactory.getDialChartIntent(context, category, renderer,
				"Posture Score");
	}

}
