/***********************************************
  Sit up Straight!

  Authors: Sanjana Kamath and Deepika Punyamurtula
  Date: Nov 15 2013

  --This code is for a posture corrector device.
  --The circuit has an Arduino Uno Rev3 connected to a flex sensor and a 
    Piezo buzzer element.
  --The flex sensor changes its resistance when flexed so we can measure
    that change using one of the Arduino’s analog pins. But to do that 
    we need a fixed resistor (not changing) that we can use for that 
    comparison (We are using a 10K resistor). This is called a voltage 
    divider and divides the 5v between the flex sensor and the resistor.
  --Thus the flex sensor detects a flex/bend in the user's posture.
  --When this happens, the buzzer goes off and alerts the user to change 
    his/her posture.
 *************************************************/
 
#include <SD.h>
#include <DS1307RTC.h>
#include <Time.h>
#include <Wire.h>
const int chipSelect = 10;

File dataFile;

void setup()
{
	//initialize serial communications and output pins
	Serial.begin(9600);
	pinMode(9, OUTPUT);

	Serial.print("Initializing SD card...");
	pinMode(10, OUTPUT);

	// see if the card is present and can be initialized:
	if (!SD.begin(chipSelect)) 
	{
		Serial.println("Card failed, or not present");
		return;
	}
	Serial.println("Card initialized.");

	// open the file. note that only one file can be open at a time,
	// so you have to close this one before opening another.

	char filename[] = "mylog.csv";

	if(!SD.exists(filename))
	{
		dataFile = SD.open(filename, FILE_WRITE);

		if(dataFile) {
			dataFile.print("Time");
			dataFile.print(",");
			dataFile.print("Date");
			dataFile.print(",");
			dataFile.print("Current Angle of the back(Degrees)");
			dataFile.print(",");
			dataFile.print("Is_Beeped");
			dataFile.print(",");
			dataFile.print("Intensity");
			dataFile.println();

			dataFile.close();
		}
		else {
			Serial.println("Error opening file");
		}
	}

}

void loop()
{
	int sensor, degrees;
	boolean beeped = false;
	String intensity = "Good";

        //Serial.println("inside loop");
	//read the voltage from the voltage divider (sensor plus resistor)
        //Serial.println("Before sensor");
	sensor = analogRead(0);
        //Serial.println("After sensor");
        //Serial.println(sensor);

	/*
    Convert the voltage reading to inches
    the first two numbers are the sensor values for straight (512) and bent (614)
    The second two numbers are the degree readings we'll map that to (0 to 90 deg)
	 */
	degrees = map(sensor, 512, 614, 0, 90);
        Serial.print("   Degree of bend: ");
        Serial.println(degrees,DEC);

	if(degrees >= 30) {

		if (degrees >= 30 && degrees <= 40) {
			beep(200);
                        beep(200);
			beeped = true;
			intensity = "Mild";
		}
		if (degrees >= 40 && degrees <= 50) {
			beep(100);
                        beep(100);
                        beep(100);
			beeped = true;
			intensity = "Severe";
		}
		if (degrees >= 50) {
			beep(50);
			beep(50);
			beep(50);
                        beep(50);
                        beep(50);
                        beep(50);
			beeped = true;
			intensity = "Very severe";
		} 
		//log the results to serial monitor for debugging purposes   
		Serial.println("Bad Posture!");
		Serial.print("Analog input: ");
		Serial.print(sensor,DEC);
		Serial.print("   Degree of bend: ");
		Serial.println(degrees,DEC);
	}

	// open the file. note that only one file can be open at a time,
	// so you have to close this one before opening another.
	//char filename[] = "datalogtest.csv";
	dataFile = SD.open("mylog.csv", FILE_WRITE);

	// if the file is available, write to it:
	if (dataFile) 
	{
		tmElements_t tm;
		if (RTC.read(tm)) 
		{
			//set field time
			if (tm.Hour >= 0 && tm.Hour < 10) {
				dataFile.print('0');
			}
			dataFile.print(tm.Hour);

			dataFile.print(':');

			if (tm.Minute >= 0 && tm.Minute < 10) {
				dataFile.print('0');
			}
			dataFile.print(tm.Minute);

			dataFile.print(':');

			if (tm.Second >= 0 && tm.Second < 10) {
				dataFile.print('0');
			}
			dataFile.print(tm.Second);

			//next field date
			dataFile.print(", "); 

			dataFile.print(tm.Day);
			dataFile.print('/');
			dataFile.print(tm.Month);
			dataFile.print('/');
			dataFile.print(tmYearToCalendar(tm.Year));
                        
                   
			Serial.println(tm.Day);
			Serial.println('/');
			Serial.println(tm.Month);
			Serial.println('/');
			Serial.println(tmYearToCalendar(tm.Year));

			//next field sensor degree
			dataFile.print(", "); 
			dataFile.print(degrees);

			//next field beeped value
			dataFile.print(", "); 
			dataFile.print(beeped);

			//next field intensity
			dataFile.print(", "); 
			dataFile.print(intensity);
		} 
		else 
		{
                        //Serial.println("Came here");
			if (RTC.chipPresent()) 
			{
				Serial.println("The DS1307 is stopped.  Please set the time first");
				Serial.println();
			} else 
			{
				Serial.println("DS1307 read error!  Please check the circuitry.");
				Serial.println();
			}
			delay(9000);
		}

		dataFile.println();
		dataFile.close();
	}  
	// if the file isn't open, pop up an error:
	else 
	{
		Serial.println("Error opening csv");
	}

	//pause before taking the next reading
	delay(1000);  
}

void beep(unsigned char delayms) 
{
	digitalWrite(9, HIGH);
	analogWrite(9, 100);      //Almost any value can be used except 0 and 255
	//experiment to get the best tone
	delay(delayms);          //wait for a delayms ms
	analogWrite(9, 0);       //0 turns it off
	delay(delayms);          //wait for a delayms ms   
} 
