package com.example.test_android;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Calendar;
import java.util.TreeMap;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.chart.*;
import org.achartengine.renderer.*;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import org.achartengine.GraphicalView;

public class MainActivity extends Activity implements OnClickListener {
	public static int[] pop, area;
	public static ArrayList<String> dates;
	private GraphicalView BarChartView;
	public static HashMap<String, Integer> beeps;
	public static HashMap<String, Integer> count_date;
	public static int[] beeps_count;
	public static int[] dates_count;
	public static String[] dates_set;
	Button btnCalendar_from, btnTimePicker_from, btnChart;
	EditText txtDate_from, txtTime_from;
	private int mYear_from, mMonth_from, mDay_from, mHour_from, mMinute_from;
	Button btnCalendar_to, btnTimePicker_to, score_btn;
	EditText txtDate_to, txtTime_to;
	private int mYear_to, mMonth_to, mDay_to, mHour_to, mMinute_to;
	String to_date, from_date, to_date_1, from_date_1;
	private Button btnCalendar_from_1;
	private EditText txtDate_from_1;
	private Button btnCalendar_to_1;
	private EditText txtDate_to_1;
	private int mYear_to_1;
	private int mMonth_to_1;
	private int mDay_to_1;
	private int mYear_from_1;
	private int mMonth_from_1;
	private int mDay_from_1;
	public static int max = 0;
	private Button btnChart2;
	public static Object[] angle_values_arr;
	public static ArrayList<Integer> angle_values;
	public static Object[] time_values_arr;
	public static ArrayList<String> time_values;
	public static String[] dates_set_line;
	public static int posture_score, beep_count, row_count;
	public static int score_angle;
	public StringBuffer sb,sb1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("MyApp", "Hello");

		
		MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
				dates = helper.getDates();
		int count = helper.getRowCount();
		
		ArrayList<FlexSensor> fs = helper.getValues();
		int i = 0;
		pop = new int[count];
		area = new int[count];

		int date_count = helper.getDateCount();
        beeps_count = new int[date_count + 1];
		dates_set = new String[date_count + 1];

		dates_count = new int[date_count + 1];
		dates_set_line = new String[date_count + 1];

		btnChart = (Button) findViewById(R.id.button1);
		btnChart2 = (Button) findViewById(R.id.button2);
		btnChart.setOnClickListener(this);
		btnChart2.setOnClickListener(this);
		// Defining click event listener for the button btn_chart
		btnCalendar_from = (Button) findViewById(R.id.btnCalendar);
		
		txtDate_from = (EditText) findViewById(R.id.txtDate);
		
		btnCalendar_from.setOnClickListener(this);
		
		btnCalendar_to = (Button) findViewById(R.id.btnCalendar1);
		

		txtDate_to = (EditText) findViewById(R.id.txtDate1);
        btnCalendar_to.setOnClickListener(this);
		
		score_btn = (Button) findViewById(R.id.score_btn);
		score_btn.setOnClickListener(this);

		btnCalendar_from_1 = (Button) findViewById(R.id.btnCalendar2);
		
		txtDate_from_1 = (EditText) findViewById(R.id.txtDate2);
		
		btnCalendar_from_1.setOnClickListener(this);
		
		btnCalendar_to_1 = (Button) findViewById(R.id.btnCalendar3);
        txtDate_to_1 = (EditText) findViewById(R.id.txtDate3);
		
		btnCalendar_to_1.setOnClickListener(this);
			}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

    //Caculate the Posture score
	public int evaluate_score(int beep_value, int row_count) {
		int score = 0;
		float score_val = (float) beep_value / row_count;
		score = (int) (score_val * 100);
		if (score == 0) {
			score = 100;

		} else if (score > 0 && score <= 20) {
			score = 80 + (20 - score);

		} else if (score > 20 && score <= 40)

			score = 60 + (40 - score);

		else if (score > 40 && score <= 60)

			score = 40 + (60 - score);

		else if (score > 60 && score <= 80) {

			score = 20 + (80 - score);
		} else if (score > 80 && score <= 100) {
			score = 0 + (100 - score);
		}
		return score;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnChart) {

			MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
			beeps = new HashMap<String, Integer>();
			beeps = helper.getBeepsBetweenDates(from_date, to_date);
			Map<String, Integer> treeMap = new TreeMap<String, Integer>(beeps);
			int i = 0;
			for (Entry<String, Integer> e : treeMap.entrySet()) {
				
				beeps_count[i] = e.getValue();
                dates_set[i] = e.getKey();
				sb = new StringBuffer(dates_set[i]);
				sb.insert(2,"/");
				sb.insert(5,"/");
				dates_set[i] = sb.toString();
				
				i++;
			}

			for (int i1 = 0; i1 < beeps_count.length; i1++) {
				if (beeps_count[i1] > max) {
					max = beeps_count[i1];
				}
			}
			BarGraph bar = new BarGraph();
			Intent barIntent = bar.getIntent(MainActivity.this);
			startActivity(barIntent);
		}
		if (v == btnChart2) {

			MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());

			count_date = new HashMap<String, Integer>();
			count_date = helper.getCountBetweenDates(from_date_1, to_date_1);

			angle_values = helper.getAngles(from_date_1, to_date_1);
			time_values = helper.getTimes(from_date_1, to_date_1);
			time_values_arr = time_values.toArray();
			angle_values_arr = angle_values.toArray();
            Map<String, Integer> treeMap = new TreeMap<String, Integer>(count_date);
			int i = 0;
			for (Entry<String, Integer> e : treeMap.entrySet()) {
				
				dates_count[i] = e.getValue();
				
				dates_set_line[i] = e.getKey();
				sb1 = new StringBuffer(dates_set_line[i]);
				sb1.insert(2,"/");
				sb1.insert(5,"/");
				dates_set_line[i] = sb1.toString();
				
				i++;
			}

			Linegraph line = new Linegraph();
			Intent lineIntent = line.getIntent(MainActivity.this);
			startActivity(lineIntent);
		}
		if (v == score_btn) {
			MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
			beep_count = helper.getBeepCountForDay(from_date_1);
            row_count = helper.getRowCountForDay(from_date_1);
			posture_score = evaluate_score(beep_count, row_count);

			DialGraph dg = new DialGraph();
			Intent dialIntent = dg.execute(MainActivity.this);
			startActivity(dialIntent);

		}
		if (v == btnCalendar_from) {

			final Calendar c = Calendar.getInstance();
			mYear_from = c.get(Calendar.YEAR);
			mMonth_from = c.get(Calendar.MONTH);
			mDay_from = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							if(dayOfMonth <= 9){
								if(monthOfYear <9){
								txtDate_from.setText("0"+dayOfMonth + "/"
										+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_from.setText("0"+dayOfMonth + "/"
											+(monthOfYear + 1) + "/" + year);
								}
								
							}
							else{
								if(monthOfYear <=9){
									txtDate_from.setText(dayOfMonth + "/"
											+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_from.setText(dayOfMonth + "/"
											+ (monthOfYear + 1) + "/" + year);
								}
							}
							
							
							from_date = txtDate_from.getText().toString()
									.replace("/", "");

						}
					}, mYear_from, mMonth_from, mDay_from);
			dpd.show();
		}
		if (v == btnCalendar_from_1) {

			final Calendar c = Calendar.getInstance();
			mYear_from_1 = c.get(Calendar.YEAR);
			mMonth_from_1 = c.get(Calendar.MONTH);
			mDay_from_1 = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							if(dayOfMonth <= 9){
								if(monthOfYear < 9){
								txtDate_from_1.setText("0"+dayOfMonth + "/"
										+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_from_1.setText("0"+dayOfMonth + "/"
											+(monthOfYear + 1) + "/" + year);
								}
								
								
							}
							else{
								if(monthOfYear < 9){
									txtDate_from_1.setText(dayOfMonth + "/"
											+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_from_1.setText(dayOfMonth + "/"
											+ (monthOfYear + 1) + "/" + year);
								}
							}

							
							from_date_1 = txtDate_from_1.getText().toString()
									.replace("/", "");

						}
					}, mYear_from_1, mMonth_from_1, mDay_from_1);
			dpd.show();
		}
		
		if (v == btnCalendar_to) {

			final Calendar c = Calendar.getInstance();
			mYear_to = c.get(Calendar.YEAR);
			mMonth_to = c.get(Calendar.MONTH);
			mDay_to = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							if(dayOfMonth <= 9){
								if(monthOfYear < 9){
								txtDate_to.setText("0"+dayOfMonth + "/"
										+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_to.setText("0"+dayOfMonth + "/"
											+(monthOfYear + 1) + "/" + year);
								}
								
							}
							else{
								if(monthOfYear < 9){
									txtDate_to.setText(dayOfMonth + "/"
											+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_to.setText(dayOfMonth + "/"
											+ (monthOfYear + 1) + "/" + year);
								}
							}

							
							to_date = txtDate_to.getText().toString()
									.replace("/", "");

						}
					}, mYear_to, mMonth_to, mDay_to);
			dpd.show();
		}
		if (v == btnCalendar_to_1) {

			final Calendar c = Calendar.getInstance();
			mYear_to_1 = c.get(Calendar.YEAR);
			mMonth_to_1 = c.get(Calendar.MONTH);
			mDay_to_1 = c.get(Calendar.DAY_OF_MONTH);

			DatePickerDialog dpd = new DatePickerDialog(this,
					new DatePickerDialog.OnDateSetListener() {

						@Override
						public void onDateSet(DatePicker view, int year,
								int monthOfYear, int dayOfMonth) {
							if(dayOfMonth <= 9){
								if(monthOfYear < 9){
								txtDate_to_1.setText("0"+dayOfMonth + "/"
										+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_to_1.setText("0"+dayOfMonth + "/"
											+(monthOfYear + 1) + "/" + year);
								}
								
							
							}
							else{
								if(monthOfYear < 9){
									txtDate_to_1.setText(dayOfMonth + "/"
											+"0"+ (monthOfYear + 1) + "/" + year);
								}
								else{
									txtDate_to.setText(dayOfMonth + "/"
											+ (monthOfYear + 1) + "/" + year);
								}
							}


							
							to_date_1 = txtDate_to_1.getText().toString()
									.replace("/", "");

						}
					}, mYear_to_1, mMonth_to_1, mDay_to_1);
			dpd.show();
		}
		
	}

}
