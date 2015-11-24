package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by g00289968 on 24/11/2015.
 */
public class Accelerometer extends Activity implements SensorEventListener{

    Sensor accelerometer;
    SensorManager sm;
    TextView acceleration;
    Button btnGoHome;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);

//        btnGoHome = (Button) findViewById(R.id.btnGoHome);

        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        acceleration = (TextView) findViewById(R.id.acceleration);

        btnGoHome = (Button) findViewById(R.id.btnGoHome);
        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent myIntentA2 = new Intent(Accelerometer.this, AndroidGPSTrackingActivity.class);

                startActivity(myIntentA2);

            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        acceleration.setText("X: " + event.values[0] +
                "\nY: " + event.values[1] +
                "\nZ: " + event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}