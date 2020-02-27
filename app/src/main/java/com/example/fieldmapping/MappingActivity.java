package com.example.fieldmapping;

import android.Manifest;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.pm.PackageManager;
        import android.location.Criteria;
        import android.location.Location;
        import android.location.LocationListener;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.TextView;
        import android.widget.Toast;
        import java.util.ArrayList;
        import java.util.Collections;
        import java.util.HashMap;
        import java.util.List;
        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.core.app.ActivityCompat;
        import androidx.core.content.ContextCompat;
        import androidx.room.Room;

//HAHA, MAPPING IS DONE HERE
public class MappingActivity extends AppCompatActivity implements LocationListener {
    TextView txtName;
    TextView txtNumPoints;
    TextView mem_id;
    LocationManager locationManager;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferences prefs2;
    String provider;
    ArrayList<Double> lats;
    ArrayList<Double> longs;
    ArrayList<Double> time;
    ArrayList<Double> latlongs;
    ArrayList latlongsPlot;
    Double minlat;
    Double maxlat;
    Double minlng;
    Double maxlng;
    Double midlats;
    Double midlng;
    SharedPreferenceController sharedPreferenceController;

    //this is constant
    final long MIN_LOC_UPDATE_TIME = 500;
    String walkOrBike;
    //these will be varied
    float MIN_LOC_UPDATE_DISTANCE;
    float MAX_WALKING_SPEED;
    float MAX_BIKE_SPEED;
    int size;
    double field;
    List<fields> overlapField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        txtName = findViewById(R.id.txtName);
        txtNumPoints = findViewById(R.id.txtNumPoints);
        mem_id = findViewById(R.id.mem_idmap);
        lats = new ArrayList<>();
        longs = new ArrayList<>();
        time = new ArrayList<>();
        latlongs = new ArrayList<>();
        latlongsPlot=new ArrayList();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //gets the parameters for the location request, on updates, the values in these variables will be changed
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        String first_name=member.getString("first_name","koye");
        String last_name=member.getString("last_name","sodipo");
        txtName.setText(first_name+" "+last_name);
        mem_id.setText(member.getString("member_id","001"));
        MIN_LOC_UPDATE_DISTANCE = Float.valueOf(member.getString("MIN_LOC_UPDATE_DISTANCE", "3"));
        MAX_WALKING_SPEED = Float.valueOf(member.getString("MAX_WALKING_SPEED", "10"));
        MAX_BIKE_SPEED = Float.valueOf(member.getString("MAX_BIKE_SPEED", "20"));
        walkOrBike = member.getString("walkorbike", "W");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        provider = locationManager.getBestProvider(criteria, false);
        Toast.makeText(this, "Start Mapping.", Toast.LENGTH_LONG).show();
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
            ActivityCompat.requestPermissions(MappingActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
    }

    private double distancecheck(double lat1, double lon1, double lat2, double lon2){  // generally used geo measurement function
        Integer R = 6371; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        double x=d * 1000;
        return (int)Math.round(x); // meters

    }

    public String getboundary(ArrayList<Double> latme,ArrayList<Double>lngme)
    {
        String boundary="";
        minlat= Collections.max(latme);
        maxlat=Collections.min(latme);
        minlng=Collections.min(lngme);
        maxlng=Collections.max(lngme);
        midlats=(minlat+maxlat)/2;
        midlng=(minlng+maxlng)/2;
        boundary +=minlat+"-"+maxlat+"-"+minlng+"-"+maxlng+"-"+midlats+"-"+midlng;
        return boundary;
    }

    public void end(View v) {
        int length = lats.size();
        Room.databaseBuilder(this, finalDatabase.class, "DB").build();
        final finalDatabase fd =  finalDatabase.getInstance(this);

        //if (length >=1 &&calculateArea(lats, longs)>0.001 ) {
        if (length >=30 &&calculateArea(lats, longs)>0.001 ) {
            final String x = getboundary(lats, longs);
            if (maxlat<12) {
                if (distancecheck(lats.get(2),longs.get(2),lats.get(lats.size()-1),longs.get(longs.size()-1))<50){

                    String[] d = x.split("-");
                    //check for overlap
                    overlapField = fd.fieldsdao().checkoverlap(d[4] + "_" + d[5]);
                    Log.d("overlapped",String.valueOf(overlapField.size()));
                    int count = overlapField.size();
                    if (count==0) {

//                pbPB.setVisibility(View.GONE);
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.endMapSess)
                                .setMessage(this.getResources().getString(R.string.theFieldA) + " " + String.valueOf(alertArea()) + " " + this.getResources().getString(R.string.theFieldA2))
                                .setPositiveButton(R.string.saveDet, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        memEdit.putString("fieldsize", String.valueOf(alertArea()));
                                        memEdit.putString("latlongs", getLatLongs());
                                        memEdit.putString("suggfieldsize", member.getString("suggfieldsize", "") + "_0");
                                        String[] a = x.split("-");
                                        memEdit.putString("minlat", a[0]);
                                        memEdit.putString("maxlat", a[1]);
                                        memEdit.putString("minlng", a[2]);
                                        memEdit.putString("maxlng", a[3]);
                                        memEdit.putString("middle", a[4] + "_" + a[5]);
                                        memEdit.putString("field_status","active");
                                        memEdit.putString("program","tge");
                                        memEdit.commit();
                                        prefs2.edit().putBoolean("mapped", true).commit();
                                        startActivity(new Intent(MappingActivity.this, MappingForm.class));
                                        finish();
                                    }
                                })

                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(MappingActivity.this, TGhome.class));
                                        finish();
                                    }
                                })
                                .show();
                    }
                    else{
                        new AlertDialog.Builder(this)
                                .setTitle(R.string.fieldOver)
                                .setMessage(this.getResources().getString(R.string.fieldOverlap))
                                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        memEdit.putString("fieldsize", String.valueOf(alertArea()));
                                        memEdit.putString("latlongs", getLatLongs());
                                        memEdit.putString("suggfieldsize", member.getString("suggfieldsize", "") + "_0");
                                        String[] a = x.split("-");
                                        memEdit.putString("minlat", a[0]);
                                        memEdit.putString("maxlat", a[1]);
                                        memEdit.putString("minlng", a[2]);
                                        memEdit.putString("maxlng", a[3]);
                                        memEdit.putString("middle", a[4] + "_" + a[5]+overlapField.toString());
                                        memEdit.putString("field_status","active");
                                        memEdit.commit();
                                        prefs2.edit().putBoolean("mapped", true).commit();
                                        startActivity(new Intent(MappingActivity.this, MappingForm.class));
                                        finish();
                                    }
                                })

                                .setNegativeButton(R.string.back, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        startActivity(new Intent(MappingActivity.this, TGhome.class));
                                        finish();
                                    }
                                })
                                .show();
                    }

                }else{
                    Toast.makeText(getApplicationContext(), getString(R.string.farFromStart), Toast.LENGTH_LONG).show();

                }
            }
            else{
                Toast.makeText(getApplicationContext(), getString(R.string.farNorth), Toast.LENGTH_LONG).show();

            }


        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.notEnoughPoints), Toast.LENGTH_LONG).show();
        }
    }

    public String getLatLongs() {
        String latnlongs = "";
        int size = latlongs.size();
        for (int i = 0; i < size - 1; i++) {
            latnlongs += String.valueOf(latlongs.get(i)) + ",";
        }
        latnlongs += String.valueOf(latlongs.get(size - 1));

        return latnlongs;
    }


    public String getLat() {
        String latnlongs = "";
        int size = latlongs.size();
        for (int i = 0; i < size - 1; i++) {
            latnlongs += String.valueOf(latlongs.get(i)) + ",";
        }
        latnlongs += String.valueOf(latlongs.get(size - 1));

        return latnlongs;
    }

    public String getmiddle() {
        String middle = midlng+"_"+midlats;


        return middle;
    }

    //check if fields overlap.
    //todo, I need to get all the values of min and max lat and lng from database
    //not used
    public boolean overlap(double minlat,double maxlat,double minlng, double maxlng,double resultlx,double resultly,double resultrx,double resultry)

    {
        if (minlng>resultrx || maxlng>resultrx) {
            if (maxlat < resultry || resultly < minlat)
            {
                return false;//no overlap
            }}

        else{
            return true;

        }return false;

    }

    public double pointsDist(double lat1, double lon1, double lat2, double lon2) {

        return Math.sqrt((lat2 - lat1) * (lat2 - lat1) + (lon2 - lon1) * (lon2 - lon1)) * 110000;
    }

    public double deg2rad(double deg) {
        return deg * (Math.PI / 180);
    }

    public static double calculateArea(final ArrayList<Double> lats, final ArrayList<Double>longs) {
        return calculateAreaOfGPSPolygonOnSphereInSquareMeters(lats,longs,6378137);
    }
    public double alertArea(){
        double area=calculateArea(lats, longs);
        Log.d("arrreaa",String.valueOf(area));
        String f = String.valueOf(area);
        String[] x = f.split("\\.");
        String y=x[1];
        String charAtZero =  Character.toString(y.charAt(0));
        String w=x[0]+"."+charAtZero;
        double round=Double.valueOf(w);
        String charAtone = Character.toString(y.charAt(1));
        Integer a=Integer.valueOf(charAtone);
        Log.d("dkdkk",String.valueOf(charAtone));
        Log.d("sjkssds",String.valueOf(a));
        if (a>=7)
        {
            field=round+0.1;

        }
        else
        {
            field=round+0.0;

        }
        return field;
    }

    private static double calculateAreaOfGPSPolygonOnSphereInSquareMeters(final ArrayList<Double>lats ,final ArrayList<Double>longs, final double radius) {
        if (lats.size() < 3) {
            return 0;
        }

        final double diameter = radius * 2;
        final double circumference = diameter * Math.PI;
        final ArrayList<Double> listY = new ArrayList<Double>();
        final ArrayList<Double> listX = new ArrayList<Double>();
        final ArrayList<Double> listArea = new ArrayList<Double>();
        // calculate segment x and y in degrees for each point
        final double latitudeRef = lats.get(3);
        final double longitudeRef = longs.get(3);
        for (int i = 4; i < lats.size(); i++) {
            final double latitude = lats.get(i);
            final double longitude = longs.get(i);
            listY.add(calculateYSegment(latitudeRef, latitude, circumference));
            listX.add(calculateXSegment(longitudeRef, longitude, latitude, circumference));
        }

        // calculate areas for each triangle segment
        for (int i = 2; i < listX.size(); i++) {
            final double x1 = listX.get(i - 1);
            final double y1 = listY.get(i - 1);
            final double x2 = listX.get(i);
            final double y2 = listY.get(i);
            listArea.add(calculateAreaInSquareMeters(x1, x2, y1, y2));
            //Log.d(LOG_TAG, String.format("area %s: %s", listArea.size() - 1, listArea.get(listArea.size() - 1)));
        }

        // sum areas of all triangle segments
        double areasSum = 0;
        for (final Double area : listArea) {
            areasSum = areasSum + area;
        }

        // get abolute value of area, it can't be negative
        return Math.abs(areasSum/10000);// Math.sqrt(areasSum * areasSum);
        //return Math.round(result*10)/10;//rounding to 1 decimal places
    }

    private static Double calculateAreaInSquareMeters(final double x1, final double x2, final double y1, final double y2) {
        Log.d("memme",String.valueOf((y1 * x2 - x1 * y2) / 2));
        return (y1 * x2 - x1 * y2) / 2;
    }

    private static double calculateYSegment(final double latitudeRef, final double latitude, final double circumference) {
        Log.d("memme",String.valueOf((latitude - latitudeRef) * circumference / 360.0));
        return (latitude - latitudeRef) * circumference / 360.0;
    }

    private static double calculateXSegment(final double longitudeRef, final double longitude, final double latitude,
                                            final double circumference) {
        return (longitude - longitudeRef) * circumference * Math.cos(Math.toRadians(latitude)) / 360.0;
    }

    private double area(ArrayList<Double> lats, ArrayList<Double> lons) {
        double sum = 0;
        double prevcolat = 0;
        double prevaz = 0;
        double colat0 = 0;
        double az0 = 0;
        for (int i = 0; i < lats.size(); i++) {
            double colat = 2 * Math.atan2(Math.sqrt(Math.pow(Math.sin(lats.get(i) * Math.PI / 180 / 2), 2) + Math.cos(lats.get(i) * Math.PI / 180) * Math.pow(Math.sin(lons.get(i) * Math.PI / 180 / 2), 2)), Math.sqrt(1 - Math.pow(Math.sin(lats.get(i) * Math.PI / 180 / 2), 2) - Math.cos(lats.get(i) * Math.PI / 180) * Math.pow(Math.sin(lons.get(i) * Math.PI / 180 / 2), 2)));
            double az = 0;
            if (lats.get(i) >= 90) {
                az = 0;
            } else if (lats.get(i) <= -90) {
                az = Math.PI;
            } else {
                az = Math.atan2(Math.cos(lats.get(i) * Math.PI / 180) * Math.sin(lons.get(i) * Math.PI / 180), Math.sin(lats.get(i) * Math.PI / 180)) % (2 * Math.PI);
            }
            if (i == 0) {
                colat0 = colat;
                az0 = az;
            }
            if (i > 0 && i < lats.size()) {
                sum = sum + (1 - Math.cos(prevcolat + (colat - prevcolat) / 2)) * Math.PI * ((Math.abs(az - prevaz) / Math.PI) - 2 * Math.ceil(((Math.abs(az - prevaz) / Math.PI) - 1) / 2)) * Math.signum(az - prevaz);
            }
            prevcolat = colat;
            prevaz = az;
        }
        sum = sum + (1 - Math.cos(prevcolat + (colat0 - prevcolat) / 2)) * (az0 - prevaz);
        return 5.10072E14 * Math.min(Math.abs(sum) / 4 / Math.PI, 1 - Math.abs(sum) / 4 / Math.PI);
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
//        txtLat.setText(String.valueOf(Math.round(lat * 100.0) / 100.0));
        //      txtLong.setText(String.valueOf(Math.round(lng * 100.0) / 100.0));
        size = lats.size();
        Log.d("got here", "yes");
        //if it is the first point, add it
        if (size == 0) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);
            latlongsPlot.add(lat);

            txtNumPoints.setText(String.valueOf(size + 1));

            return;
        }

        double dist = pointsDist(lat, lng, lats.get(size - 1), longs.get(size - 1));

        if (walkOrBike.equals("W") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_WALKING_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongs.add(lng);
            latlongsPlot.add(lng);
            latlongsPlot.add(lat);
            txtNumPoints.setText(String.valueOf(size + 1));

            Log.i("TEST", String.valueOf(Double.valueOf(System.currentTimeMillis() / 1000)));

            return;
        }

        if (walkOrBike.equals("B") && dist >= MIN_LOC_UPDATE_DISTANCE && dist/(Double.valueOf(System.currentTimeMillis()/1000) - time.get(size - 1)) <= MAX_BIKE_SPEED) {
            lats.add(lat);
            longs.add(lng);
            time.add(Double.valueOf(System.currentTimeMillis() / 1000));
            latlongs.add(lat);
            latlongsPlot.add(lat);
            latlongs.add(lng);
            latlongsPlot.add(lng);

            txtNumPoints.setText(String.valueOf(size + 1));

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MappingActivity.this, TGhome.class));
        finish();

    }

    public void helpMapping(View v) {
        //Takes you to the Help center's Questions activity.
//        HashMap<String,String> user = sharedPreferenceController.getUserDetails();
//        try {
//            startActivity(new Intent(this, ViewActivityIssues.class)
//                    .putExtra("activity_id", "field_mapping_2")
//                    .putExtra("app_id", "field_mapping")
//                    .putExtra("staff_id", user.get(SharedPreferenceController.KEY_STAFF_ID))
//                    .putExtra("app_language", user.get(SharedPreferenceController.KEY_APP_LANG))
//                    .putExtra("user_location", "Lagos"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Activity not found", Toast.LENGTH_LONG).show();
//        }
    }
}
