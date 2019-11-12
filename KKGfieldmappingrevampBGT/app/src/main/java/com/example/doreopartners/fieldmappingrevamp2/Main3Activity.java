package com.example.doreopartners.fieldmappingrevamp2;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
//THIS PAGE is redundant. you might delete it. but be sure
public class Main3Activity extends AppCompatActivity {

    SharedPreferences member;
    SharedPreferences prefs;
    TextView txtName;
    Button btnForm;
    SharedPreferences.Editor prefsEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = findViewById(R.id.txtName);

        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        btnForm = findViewById(R.id.btnForm);
        prefsEdit = member.edit();

        txtName.setText(member.getString("membername", "David Bones"));
        //TODO 1. your intent that launches my app starts here and initializes these shared preference variables
        Intent intent = getIntent();

        if (intent.getStringExtra("member_name") != null) {
            prefsEdit.putString("membername", intent.getStringExtra("member_name"));
            //prefsEdit.putString("memberid", intent.getStringExtra("staff_id"));
            //prefsEdit.putString("croptype", intent.getStringExtra("crop_type"));
            prefsEdit.putString("fieldid", intent.getStringExtra("test_id"));
            prefsEdit.putString("module", intent.getStringExtra("module"));

            //prefsEdit.putString("MIN_LOC_UPDATE_DISTANCE",intent.getStringExtra("min_dist"));
            prefsEdit.putString("MIN_WALKING_SPEED", intent.getStringExtra("max_walk_speed"));
            prefsEdit.putString("MIN_BIKE_SPEED", intent.getStringExtra("max_bike_speed"));

            prefsEdit.commit();
        }

    }

    public void form(View v) {
        if (prefs.getBoolean("mapped", false)) {
            startActivity(new Intent(Main3Activity.this, MappingForm.class));
        } else {
            Toast.makeText(getApplicationContext(), "Please map a field before filling the form", Toast.LENGTH_LONG).show();
        }

    }

    public void map(View v) {
        if (permissionGranted()) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;

            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch (Exception ex) {
            }

            try {
                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            } catch (Exception ex) {
            }

            if (!gps_enabled && !network_enabled) {
                // notify user
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Enable location");
                dialog.setPositiveButton("Turn on location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
                dialog.show();
            }
            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("Select Mapping mode");
                dialog.setPositiveButton("Walking", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        prefsEdit.putString("walkorbike", "W");
                        prefsEdit.commit();
                        startActivity(new Intent(Main3Activity.this, MappingActivity.class));
                    }
                });
                dialog.setNegativeButton("Bike", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        prefsEdit.putString("walkorbike", "B");
                        prefsEdit.commit();
                        startActivity(new Intent(Main3Activity.this,MappingActivity.class));
                    }
                });
                dialog.show();

            }
        } else {
            ActivityCompat.requestPermissions(Main3Activity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    public boolean permissionGranted() {
        return (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        startActivity(new Intent(Main3Activity.this, Main2Activity.class));
//        finish();
//
//    }

}
