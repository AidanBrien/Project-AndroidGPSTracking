package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AndroidGPSTrackingActivity extends Activity {

    Button btnShowLocation;
    Button btnShowAccelerometer;
    Button btnShowSMS;

    // GPSTracker class
    GPSTracker gps;

    //btnShowLocation.performClick();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

//        btnShowLocation = (Button) findViewById(R.id.btnShowLocation);
//
//        // show location button click event
//        btnShowLocation.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                // create class object
//                gps = new GPSTracker(AndroidGPSTrackingActivity.this);
//
//                // check if GPS enabled
//                if (gps.canGetLocation()) {
//
//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();
//
//                    // \n is for new line
//                    Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
//                } else {
//                    // can't get location
//                    // GPS or Network is not enabled
//                    // Ask user to enable GPS/network in settings
//                    gps.showSettingsAlert();
//                }
//
//            }
//        });
        btnShowAccelerometer = (Button) findViewById(R.id.btnShowAccelerometer);
        btnShowAccelerometer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                Intent myIntentA1A2 = new Intent (AndroidGPSTrackingActivity.this,Accelerometer.class);

                startActivity(myIntentA1A2);

            }
        });

        btnShowSMS = (Button) findViewById(R.id.btnShowSMS);
        btnShowSMS.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                Intent myIntentA1A2 = new Intent (AndroidGPSTrackingActivity.this,MySMS.class);

                startActivity(myIntentA1A2);

            }
        });

    }
}
