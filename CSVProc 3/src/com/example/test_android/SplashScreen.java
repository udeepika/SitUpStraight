package com.example.test_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

 
//Activity for Splash screen

public class SplashScreen extends Activity 
{
 
    private boolean mIsBackButtonPressed;
    private static final int SPLASH_DURATION = 2500; //6 seconds
    private Handler myhandler;
 
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
 
        MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
		helper.getWritableDatabase();
		helper.addFlex();
    	Log.i("SplashScreen","Inside Splash");
    	
        myhandler = new Handler();
 
        // run a thread to start the home screen
        myhandler.postDelayed(new Runnable() {
 
            @Override
            public void run() 
            {
               if (!mIsBackButtonPressed)
               {
                    // start the home activity 
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    SplashScreen.this.startActivity(intent);
               }
                 
            }
 
        }, SPLASH_DURATION); 
    }
    
   
    //handle back button press
    @Override
    public void onBackPressed() 
    {
        mIsBackButtonPressed = true;
        super.onBackPressed();
    }
    
    
    
}