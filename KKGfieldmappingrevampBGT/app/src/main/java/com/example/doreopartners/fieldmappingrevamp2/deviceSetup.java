package com.example.doreopartners.fieldmappingrevamp2;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class deviceSetup extends AppCompatActivity implements LocationListener {

    TextView count;
    ProgressBar pbPB;
    //ListView lv;
    LocationManager locationManager;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferences prefs2;
    String provider;
    ArrayList<Double> lats;
    ArrayList<Double> longs;
    ArrayList<Double> time;
    ArrayList<Double> latlongs;
    ArrayList<Double> latme;
    ArrayList<Double> longsme;
    Double minlat;
    Double maxlat;
    Double minlng;
    Double maxlng;
    Double midlats;
    Double midlng;

    //this is constant
    final long MIN_LOC_UPDATE_TIME = 500;
    String walkOrBike;
    //these will be varied
    float MIN_LOC_UPDATE_DISTANCE;
    float MAX_WALKING_SPEED;
    float MAX_BIKE_SPEED;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_setup);
        pbPB = findViewById(R.id.pb);
        pbPB.setMax(3);
       // pbPB.setProgress(1);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        count = findViewById(R.id.count);

        //lv = findViewById(R.id.listView);
        lats = new ArrayList<>();
        longs = new ArrayList<>();
        time = new ArrayList<>();
        latlongs = new ArrayList<>();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //gets the parameters for the location request, on updates, the values in these variables will be changed
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();

        //txtName.setText(member.getString("membername", "David Bones"));

        MIN_LOC_UPDATE_DISTANCE = Float.valueOf(member.getString("MIN_LOC_UPDATE_DISTANCE", "3"));
        MAX_WALKING_SPEED = Float.valueOf(member.getString("MAX_WALKING_SPEED", "10"));
        MAX_BIKE_SPEED = Float.valueOf(member.getString("MAX_BIKE_SPEED", "20"));
        walkOrBike = member.getString("walkorbike", "W");

        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        provider = locationManager.getBestProvider(criteria, false);




    }


    public double pointsDist(double lat1, double lon1, double lat2, double lon2) {
//        double R = 6.371; // Radius of the earth in m
//        double dLat = deg2rad(lat2 - lat1);  // deg2rad below
//        double dLon = deg2rad(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
//        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
//        double d = R * c; // Distance in m
//        return d;
        return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)) * 110000;
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (permissionGranted()) {
            try {
                Location location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, MIN_LOC_UPDATE_TIME, 0, this);

                if (location != null) {
                    onLocationChanged(location);
                }

            } catch (SecurityException e) {

            }
        } else {
            ActivityCompat.requestPermissions(deviceSetup.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lng = location.getLongitude();
        size = lats.size();

        //if it is the first point, add it
        if (size == 0) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);

            count.setText(String.valueOf(size + 1));

            return;
        }

        if (size==1){
            pbPB.incrementProgressBy(1);

        }
        if (size==2){
            pbPB.incrementProgressBy(1);

        }
        if (size==3){
            pbPB.incrementProgressBy(1);

        }
        if (pbPB.getProgress() == pbPB.getMax()) {
            locationManager.removeUpdates(this);

            Intent fp=new Intent(getApplicationContext(),MappingActivity.class);
            startActivity(fp);
        }
        double dist = pointsDist(lat, lng, lats.get(size - 1), longs.get(size - 1));

        if (walkOrBike.equals("W") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_WALKING_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);
            count.setText(String.valueOf(size + 1));

            Log.i("TEST", String.valueOf(Double.valueOf(System.currentTimeMillis() / 1000)));

            return;
        }

        if (walkOrBike.equals("B") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_BIKE_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);

            count.setText(String.valueOf(size + 1));

            return;
        }

        //lv.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, latlongs));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(deviceSetup.this, Main2Activity.class));
        finish();

    }





}
