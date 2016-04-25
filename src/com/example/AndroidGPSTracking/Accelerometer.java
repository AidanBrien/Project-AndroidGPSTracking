package com.example.AndroidGPSTracking;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Vibrator;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by g00289968 on 24/11/2015.
 */
public class Accelerometer extends Activity implements SensorEventListener{

    private float lastX, lastY, lastZ;

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private float deltaXMax = 0;
    private float deltaYMax = 0;
    private float deltaZMax = 0;

    private float deltaX = 0;
    private float deltaY = 0;
    private float deltaZ = 0;

    private float vibrateThreshold = 0;

    private TextView currentX, currentY, currentZ, maxX, maxY, maxZ;

    public Vibrator v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main1);
        initializeViews();

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            //Accelerometer Detected

            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            float sensitivity_val = readSensitivityValtoFile();
            vibrateThreshold = accelerometer.getMaximumRange() / sensitivity_val;
        } else {
            // No Accelerometer
        }

        //initialize vibration
        v = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void initializeViews() {
        currentX = (TextView) findViewById(R.id.currentX);
        currentY = (TextView) findViewById(R.id.currentY);
        currentZ = (TextView) findViewById(R.id.currentZ);

        maxX = (TextView) findViewById(R.id.maxX);
        maxY = (TextView) findViewById(R.id.maxY);
        maxZ = (TextView) findViewById(R.id.maxZ);
    }

    //onResume() register the accelerometer for listening the events
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    //onPause() unregister the accelerometer for stop listening the events
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        // clean current values
        displayCleanValues();
        // display the current x,y,z accelerometer values
        displayCurrentValues();
        // display the max x,y,z accelerometer values
        displayMaxValues();

        // get the change of the x,y,z values of the accelerometer
        deltaX = Math.abs(lastX - event.values[0]);
        deltaY = Math.abs(lastY - event.values[1]);
        deltaZ = Math.abs(lastZ - event.values[2]);

        // if the change is below 2, it is just plain noise
        if (deltaX < 2)
            deltaX = 0;
        if (deltaY < 2)
            deltaY = 0;
        if (deltaZ < 2)
            deltaZ = 0;

        // set the last know values of x,y,z
        lastX = event.values[0];
        lastY = event.values[1];
        lastZ = event.values[2];

        vibrate();
    }

    // if the change in the accelerometer value is big enough, then vibrate
    // our threshold is MaxValue/1
    public void vibrate() {
        if ((deltaX > vibrateThreshold) || (deltaY > vibrateThreshold) || (deltaZ > vibrateThreshold)) {
            v.vibrate(50);  //duration of vibration
            Toast.makeText(getApplicationContext(), "Fall Detected" , Toast.LENGTH_LONG).show();
            //****Check if Map is null******

            Map<String, Double> locationMap = getLocation();
            MySMS sms = new MySMS();
            if (null != locationMap){
                Double latitude = locationMap.get("latitude");
                Double longitude = locationMap.get("longitude");

                try {
                    String[] contactsArray = readNumbersFromFile();
                    String message = "User has fallen at location: \nLat: " + latitude + "\nLong: " + longitude;
                    for(int i =0; i < contactsArray.length; i++){
                        sms.sendSMS((contactsArray[i]), message);
                    }
                    System.out.println("Sending coordinates via http POST request");

                    GpsToServer(1, latitude, longitude);
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                String[] contactsArray = readNumbersFromFile();
                String message = "User has fallen at location unavailable";
                for(int i =0; i < contactsArray.length; i++){
                    sms.sendSMS((contactsArray[i]), message);
                }
            }
            //initialise resid to the sound file beep2.mp3
            int resId;
            resId = R.raw.beep2;
            //Create the media player
            MediaPlayer mp= MediaPlayer.create(this,resId);
            //start the media player
            mp.start();
        }

    }

    public void displayCleanValues() {
        currentX.setText("0.0");
        currentY.setText("0.0");
        currentZ.setText("0.0");
    }

    // display the current x,y,z accelerometer values
    public void displayCurrentValues() {
        currentX.setText(Float.toString(deltaX));
        currentY.setText(Float.toString(deltaY));
        currentZ.setText(Float.toString(deltaZ));
    }

    // display the max x,y,z accelerometer values
    public void displayMaxValues() {
        if (deltaX > deltaXMax) {
            deltaXMax = deltaX;
            maxX.setText(Float.toString(deltaXMax));
        }
        if (deltaY > deltaYMax) {
            deltaYMax = deltaY;
            maxY.setText(Float.toString(deltaYMax));
        }
        if (deltaZ > deltaZMax) {
            deltaZMax = deltaZ;
            maxZ.setText(Float.toString(deltaZMax));
        }
    }
    public Map getLocation(){
        Map <String, Double> gpsMap = new HashMap<String, Double>();

        GPSTracker gps = new GPSTracker(Accelerometer.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            gpsMap.put("latitude", latitude);
            gpsMap.put("longitude", longitude);
            return gpsMap;

        } else {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }
        return null;
    }
    private String[] readNumbersFromFile(){
        String[] contactsArray;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(getFilesDir() + File.separator + "MyFile.txt")));
            String read;
            read = bufferedReader.readLine();
            contactsArray = read.split(",");
            System.out.println("nums from file = " + read);
            bufferedReader.close();

            return contactsArray;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());

            return null;
        }
    }
    private float readSensitivityValtoFile(){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new
                    File(getFilesDir() + File.separator + "FileSensitivitySettings.txt")));
            String sensitivity_val;
            sensitivity_val = bufferedReader.readLine();
            System.out.println("val from file = " + sensitivity_val);
            bufferedReader.close();

            return Float.parseFloat(sensitivity_val);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());

            return -1;
        }
    }

    private void GpsToServer(int userid,double latitude,double longitude)throws Exception{

        String url = "http://140.203.204.78/niallcsit/InsertGPSToDB.php";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod("POST");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "USERID=" + userid + "&LATITUDE=" + latitude + "&LONGITUDE=" + longitude;

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("Sending 'POST' request to URL : " + url);
        System.out.println("Parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}