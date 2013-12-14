package com.example.test_android;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {
	private static final String TABLE_FLEX = "flex";

		private static final String KEY_TIME = "time_val";
	private static final String KEY_ANGLE = "angle";
	private static final String KEY_BEEP = "is_beep";
	private static final String KEY_INTENSITY = "intensity";
	private static final String KEY_DATE = "date";
	private static final String KEY_ID = "id";
	private static final String[] COLUMNS = { KEY_ID, KEY_TIME, KEY_DATE,
			KEY_ANGLE, KEY_BEEP, KEY_INTENSITY };
	private HashMap<String, Integer> beeps = new HashMap<String, Integer>();
	private HashMap<String, Integer> count_date = new HashMap<String, Integer>();
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "Flex_database_6";
	private final Context myContext;

	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.myContext = context;
		System.out.println("Opened");
	}

	public void onCreate(SQLiteDatabase db) {
		
		String CREATE_FLEX_TABLE = "CREATE TABLE " + TABLE_FLEX + "(" + KEY_ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_TIME + " TEXT ,"
				+ KEY_DATE + " STRING, " + KEY_ANGLE + " INTEGER, " + KEY_BEEP
				+ " INTEGER, " + KEY_INTENSITY + " TEXT );";

		db.execSQL(CREATE_FLEX_TABLE);
		System.out.println("DB created");
	}

	public void addFlex() {
		ArrayList<String> cities = new ArrayList<String>();
		int size = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		try {
			// File files1=new File("/storage/extSdCard/mylog.csv");
			InputStream in_stream = this.myContext.getAssets()
					.open("mylog.csv");
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in_stream));
			
			String line, time_val = "";
			int angle_int, isbeep_int;
			int iteration = 0;
			while ((line = reader.readLine()) != null) {
				if (iteration != 0) {
					size += 1;

				}
				iteration++;
			}
			
			Log.d("MyApp", String.valueOf(size));
			reader.close();
			int i = 0;
			// File files=new File("/storage/extSdCard/mylog.csv");
			InputStream in_stream2 = this.myContext.getAssets().open(
					"mylog.csv");
			BufferedReader reader2 = new BufferedReader(new InputStreamReader(
					in_stream2));
			
			while ((line = reader2.readLine()) != null) {
				if (i != 0) {
					
					String[] rowData = line.split(",");
					time_val = rowData[0].trim();
					String date = rowData[1].trim().replace("/", "");

					
					String angle = rowData[2].trim();
					angle_int = Integer.parseInt(angle);
					String is_beep = rowData[3].trim();
					isbeep_int = Integer.parseInt(is_beep);
					String intensity = rowData[2].trim();
					
					ContentValues values = new ContentValues();

					values.put(KEY_INTENSITY, intensity);
					values.put(KEY_BEEP, isbeep_int);
					values.put(KEY_ANGLE, angle_int);
					values.put(KEY_DATE, date);
					values.put(KEY_TIME, time_val);
					values.put(KEY_ID, i);
					db.insert(TABLE_FLEX, null, values); // nullColumnHack
					

				}
				i++;
			}
			// 4. close
			reader2.close();
			db.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getBeepCountForDay(String date){
    	    	SQLiteDatabase db = this.getWritableDatabase();
    	int beep_count=0;
        Cursor cursor = db.rawQuery("SELECT SUM(is_beep) FROM " + TABLE_FLEX +" WHERE date='"+date+"' GROUP BY date ", null);
        if(cursor.getCount()!=0){
            if (cursor.moveToFirst()) {
                    do {
                    	
                    	beep_count = cursor.getInt(0);
                    	                    }while(cursor.moveToNext());

            }
        }
            return beep_count;
    	
    }
	public int getRowCountForDay(String date){
    	
    	SQLiteDatabase db = this.getWritableDatabase();
    	int row_count=0;
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_FLEX +" WHERE date='"+date+"' ", null);
        if(cursor.getCount()!=0){
            if (cursor.moveToFirst()) {
                    do {
                    	
                    	row_count = cursor.getInt(0);
                    	
                    }while(cursor.moveToNext());

            }
        }
            return row_count;
    	
    }

	public ArrayList<String> getDates() {
		ArrayList<String> cities = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT date FROM " + TABLE_FLEX, null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					cities.add(cursor.getString(0));
				} while (cursor.moveToNext());

			}
		}
		cursor.close();
		db.close();
		return cities;

	}

	public ArrayList<Integer> getAngles(String from, String to) {
		ArrayList<Integer> angles = new ArrayList<Integer>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT angle FROM " + TABLE_FLEX
				+ " WHERE date BETWEEN " + "'" + from + "' AND '" + to
				+ "' ORDER by date ", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					angles.add(cursor.getInt(0));
				} while (cursor.moveToNext());

			}
		}
		cursor.close();
		db.close();
		return angles;

	}
	
	public ArrayList<String> getTimes(String from, String to) {
		ArrayList<String> times = new ArrayList<String>();
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT time_val FROM " + TABLE_FLEX
				+ " WHERE date BETWEEN " + "'" + from + "' AND '" + to
				+ "' ORDER by date ", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					times.add(cursor.getString(0));
				} while (cursor.moveToNext());

			}
		}
		cursor.close();
		db.close();
		return times;

	}

	public HashMap<String, Integer> getBeepsForWeek() {
				SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT date,COUNT(is_beep) FROM "
				+ TABLE_FLEX + " GROUP BY date ", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					//System.out.println(cursor.getInt(1));
					beeps.put(cursor.getString(0), cursor.getInt(1));
				} while (cursor.moveToNext());

			}
		}
		return beeps;

	}

	public HashMap<String, Integer> getCountBetweenDates(String from, String to) {
		
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT date,COUNT(date) FROM "
				+ TABLE_FLEX + " WHERE date BETWEEN " + "'" + from + "' AND '"
				+ to + "'GROUP BY date ORDER by date ", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					
					count_date.put(cursor.getString(0), cursor.getInt(1));
				} while (cursor.moveToNext());

			}
		}
		return count_date;

	}

	public HashMap<String, Integer> getBeepsBetweenDates(String from, String to) {
				SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT date,SUM(is_beep) FROM "
				+ TABLE_FLEX + " WHERE date BETWEEN " + "'" + from + "' AND '"
				+ to + "' GROUP BY date ORDER by date ", null);
		if (cursor.getCount() != 0) {
			if (cursor.moveToFirst()) {
				do {
					
					beeps.put(cursor.getString(0), cursor.getInt(1));
				} while (cursor.moveToNext());

			}
		}
		return beeps;

	}

	public int getRowCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FLEX, null);
		int count = cursor.getCount();
		return count;

	}

	public int getDateCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery("SELECT DISTINCT date FROM " + TABLE_FLEX,
				null);
		int count = cursor.getCount();
		return count;
	}

	public ArrayList<FlexSensor> getValues() {
		ArrayList<FlexSensor> FlexValues = new ArrayList<FlexSensor>();
		SQLiteDatabase db1 = this.getWritableDatabase();
		Cursor cursor1 = db1.rawQuery("SELECT * FROM " + TABLE_FLEX, null);

		if (cursor1.getCount() != 0) {
			if (cursor1.moveToFirst()) {
				do {
					FlexSensor fs = new FlexSensor();
					fs.id = cursor1.getInt(cursor1.getColumnIndex(KEY_ID));
					fs.time_val = cursor1.getString(cursor1
							.getColumnIndex(KEY_TIME));
					fs.date = cursor1.getString(cursor1
							.getColumnIndex(KEY_DATE));
					fs.angle = cursor1
							.getInt(cursor1.getColumnIndex(KEY_ANGLE));
					fs.is_beep = cursor1.getInt(cursor1
							.getColumnIndex(KEY_BEEP));
					fs.intensity = cursor1.getString(cursor1
							.getColumnIndex(KEY_INTENSITY));
					
					FlexValues.add(fs);
				} while (cursor1.moveToNext());

			}
		}
		cursor1.close();
		db1.close();
		return FlexValues;
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		db.execSQL("DROP TABLE IF EXISTS flex");

		
		this.onCreate(db);
	}
}
