package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Intent;
import android.location.LocationManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Aidan on 19/04/2016.
 */
public class Settings extends Activity {

    WifiManager wm;
    Button btnWifiOn;
    Button btnGPSOn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        btnWifiOn = (Button) findViewById(R.id.btnWifiOn);
        btnWifiOn.setOnClickListener(new View.OnClickListener()
        {
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
    }
}


