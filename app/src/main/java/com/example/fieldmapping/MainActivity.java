package com.example.fieldmapping;

        import android.Manifest;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.graphics.Color;
        import android.location.Criteria;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.ProgressBar;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.os.Handler;


        import java.util.ArrayList;
        import java.util.List;

        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.room.Room;

public class MainActivity extends AppCompatActivity implements LocationListener {

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
    TextView please;
    List<fields> overlapField;

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
        setContentView(R.layout.activity_main);
        pbPB = findViewById(R.id.pb);
        please=findViewById(R.id.please);

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
//        if (permissionGranted()) {
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            boolean gps_enabled = false;
//            boolean network_enabled = false;
//
//            try {
//                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//            } catch (Exception ex) {
//            }
//
//            try {
//                network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//            } catch (Exception ex) {
//            }
//
//            if (!gps_enabled && !network_enabled) {
//                // notify user
//                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
//                dialog.setMessage(R.string.enableLoc);
//                dialog.setPositiveButton(R.string.turnOnLoc, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(myIntent);
//
//                    }
//                });
//                dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
//
//                    }
//                });
//                dialog.show();
//            }
//            if (lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//
//            }
//            else
//            {
//                Toast.makeText(this,getString(R.string.locSettings),Toast.LENGTH_SHORT);
//                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                startActivity(myIntent);
//            }
//        } else {
//            ActivityCompat.requestPermissions(deviceSetup.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
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
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }


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
//        if (permissionGranted()) {
//            try {
//                Location location = locationManager.getLastKnownLocation(provider);
//                locationManager.requestLocationUpdates(provider, MIN_LOC_UPDATE_TIME, 0, this);
//
//                if (location != null) {
//                    onLocationChanged(location);
//                }
//
//            } catch (SecurityException e) {
//
//            }
//        } else {
//            ActivityCompat.requestPermissions(deviceSetup.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//        }
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
        Log.d("gothere","yes");
        //if it is the first point, add it
        if (size == 0) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);

            count.setText(String.valueOf(size));
            count.setText(String.valueOf(size));
            pbPB.incrementProgressBy(1);
            return;
        }


        double dist = pointsDist(lat, lng, lats.get(size - 1), longs.get(size - 1));

        if (walkOrBike.equals("W") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_WALKING_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);
            size=size+1;
            count.setText(String.valueOf(size));
            pbPB.incrementProgressBy(1);
            Log.i("TEST", String.valueOf(Double.valueOf(System.currentTimeMillis() / 1000)));

            return;
        }

        if (walkOrBike.equals("B") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_BIKE_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);
            size=size+1;
            pbPB.incrementProgressBy(1);
            count.setText(String.valueOf(size));

            return;
        }
//        if (size==1){
//
//
//        }
//        if (size==2){
//
//
//        }
//        if (size==3){
//            pbPB.incrementProgressBy(1);
//
//        }
        pbPB.setMax(4);
        if (pbPB.getProgress() == 4) {
            locationManager.removeUpdates(this);
            please.setText("Field Successfully Mapped");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Room.databaseBuilder(getApplicationContext(), finalDatabase.class, "DB").build();
                    final finalDatabase fd =  finalDatabase.getInstance(getApplicationContext());
                    overlapField = fd.fieldsdao().checkoverlap(lats.get(4) + "_" + longs.get(4));
                    Log.d("overlapped",String.valueOf(overlapField.size()));
                    int count = overlapField.size();
                    if (count==0) {
                        //Intent fp=new Intent(getApplicationContext(),MappingActivity.class);
                        //startActivity(fp);
                        finish();
                        //my_button.setBackgroundResource(R.drawable.defaultcard);
                    }
                    }
            }, 2000);
            //Intent fp=new Intent(getApplicationContext(),MappingActivity.class);
            //startActivity(fp);
            finish();
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
//    @Override
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        startActivity(new Intent(deviceSetup.this, locationSyncController.class));
//        finish();
//
//    }

    public void back_device_setup(View v) {
        // starting background task to update product
        finish();

    }
//    public void help_device_setup(View v) {
//        //Takes you to the Help center's Questions activity.
//        try {
//            startActivity(new Intent(MainActivity.this, ViewActivityIssues.class)
//                    .putExtra("activity_id", "field_mapping_1")
//                    .putExtra("app_id", "field_mapping")
//                    .putExtra("staff_id", "T-100000000000XXXX")
//                    .putExtra("user_location", "Lagos"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "Activity not found", Toast.LENGTH_LONG).show();
//        }
//
//    }



}
