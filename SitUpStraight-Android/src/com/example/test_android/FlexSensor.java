package com.example.test_android;

public class FlexSensor {
	public int id;
	public String time_val;
	public String date;
	public int angle;
	public int is_beep;
	public String intensity;
	
	public FlexSensor(){}
	public FlexSensor(int id, String time_val, String date, int angle,int is_beep,String intensity){
		this.id = id;
		this.time_val = time_val;
		this.date = date;
		this.angle = angle;
		this.is_beep = is_beep;
		this.intensity = intensity;
	}
   public String getTime(){
	   
	   return this.time_val;
   }
   public int getAngle(){
	   return this.angle;
   }
   public String getDate(){
	   return this.date;
   }
   public int getId(){
	   return this.id;
   }
   public int getIsBeep(){
	   return this.is_beep;
   }
   public String getIntensity(){
	   return this.intensity;
   }

}
