package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

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
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.yourRadioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                float sensitivity_val = 1.1f;


                    if(checkedId == R.id.radioButton1) {
                        sensitivity_val = 1;
                        Toast.makeText(getApplicationContext(), "Low Sensitivity Setting", Toast.LENGTH_SHORT).show();

                    } else if(checkedId == R.id.radioButton2) {
                        Toast.makeText(getApplicationContext(), "Medium Sensitivity Setting", Toast.LENGTH_SHORT).show();
                        sensitivity_val = 1.5f;

                    }
                    else if(checkedId == R.id.radioButton3) {
                        Toast.makeText(getApplicationContext(), "High Sensitivity Setting", Toast.LENGTH_SHORT).show();
                        sensitivity_val = 2;
                    }
                writeSensitivityValtoFile(sensitivity_val, "FileSensitivitySettings.txt");
                }
            });

    }
    private void writeSensitivityValtoFile( float sensitivity_val, String filename){
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new
                                    File(getFilesDir() + File.separator + filename)));
            bufferedWriter.write(""+sensitivity_val);
            bufferedWriter.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}


