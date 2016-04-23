package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.*;

/**
 * Created by Aidan on 19/04/2016.
 */
public class Settings extends Activity {

    WifiManager wm;
    Button btnWifiOn;
    Button btnGPSOn;
    EditText phoneNumberEnter;
    EditText phoneNumberEnter2;
    Button btnSaveNumber1;
    //SharedPreferences prefs = getSharedPreferences("com.example.AndroidGPSTracking", Context.MODE_PRIVATE);
    //String phoneNumberKey = "com.example.AndroidGPSTracking.phoneNumber";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);


        btnWifiOn = (Button) findViewById(R.id.btnWifiOn);
        btnWifiOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                wm=(WifiManager)getSystemService(WIFI_SERVICE);
                //Check if wifi is enabled
                if(wm.isWifiEnabled())
                {
                    //if not enabled change the text on the button to say wifi on as a prompt
                    btnWifiOn.setText("Wifi ON");
                    wm.setWifiEnabled(false);
                }
                else
                {
                    //if it is enabled change the text on the button to say wifi off as a prompt
                    btnWifiOn.setText("Wifi OFF");
                    wm.setWifiEnabled(true);
                }
            }
        });
        phoneNumberEnter = (EditText) findViewById(R.id.phoneNumberEnter);
        phoneNumberEnter2 = (EditText) findViewById(R.id.phoneNumberEnter2);
        btnSaveNumber1 = (Button) findViewById(R.id.btnSaveNumber1);
        btnSaveNumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String textFieldContents = phoneNumberEnter.getText().toString();
                String textField2Contents = phoneNumberEnter2.getText().toString();
                int phoneNumber, phoneNumber2;
                StringBuilder fileContents = new StringBuilder();

                if(textFieldContents.length() > 0 )
                {
                    phoneNumber = Integer.parseInt(phoneNumberEnter.getText().toString());
                    fileContents.append(phoneNumber);

                }
                if(textField2Contents.length() > 0 )
                {
                    phoneNumber2 = Integer.parseInt(phoneNumberEnter2.getText().toString());
                    fileContents.append("," + phoneNumber2);
                }

                try {
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                            File(getFilesDir()+ File.separator+"MyFile.txt")));
                    bufferedWriter.write(fileContents.toString());
                    bufferedWriter.close();


//                    BufferedReader bufferedReader = new BufferedReader(new FileReader(new
//                            File(getFilesDir()+File.separator+"MyFile.txt")));
//                    String read;
//
//                    while((read = bufferedReader.readLine()) != null){
//                        System.out.println("nums from file = "  + read);
//                    }
//                    bufferedReader.close();
                }
                catch(Exception e){

                }
            }
        });

    }
}


