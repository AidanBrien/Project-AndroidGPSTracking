package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AndroidGPSTrackingActivity extends Activity {

    Button btnShowAccelerometer;
    Button btnShowSMS;
    Button btnShowSettings;

    // GPSTracker class
    GPSTracker gps;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
                sendSMS("0857603133", "I have fallen, Please send help!");
                Toast.makeText(getApplicationContext(), "Your Message is sent", Toast.LENGTH_LONG).show();

            }
        });
        btnShowSettings = (Button) findViewById(R.id.btnShowSettings);
        btnShowSettings.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0) {
                Intent myIntentA1A2 = new Intent (AndroidGPSTrackingActivity.this,Settings.class);

                startActivity(myIntentA1A2);

            }
        });
    }
    public void sendSMS(String phoneNumber, String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
    }
}
