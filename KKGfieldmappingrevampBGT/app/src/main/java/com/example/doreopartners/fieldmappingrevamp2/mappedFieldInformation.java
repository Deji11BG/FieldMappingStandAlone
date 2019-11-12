package com.example.doreopartners.fieldmappingrevamp2;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.RectF;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.PointsGraphSeries;


import java.util.ArrayList;
import java.util.Random;
//this is where everything happens. not the most efficient though. but this was the only way available
public class mappedFieldInformation extends AppCompatActivity  {
    //add PointsGraphSeries of DataPoint type
    private static final String TAG = "mappedFieldInformation";

    PointsGraphSeries<DataPoint> xySeries;
    String minlat,maxlat,minlng,maxlng;
    double startx;
    double starty;
    double endx, endy;
    PointsGraphSeries<DataPoint> onClickSeries;
    LocationManager locationManager;
    //create graphview object
    GraphView mScatterPlot;
    //GraphView mScatterplot2;
    String provider;
    //make xyValueArray global
    ArrayList<XYValue> xyValueArray;

    //    lats = (ArrayList<Double>) getIntent().getSerializableExtra("lats");
//    longs = (ArrayList<Double>) getIntent().getSerializableExtra("lngs");
    Button take_picture, done;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferences prefs2;

    ArrayList<Double> lats1;
    ArrayList<Double> longs1;
    ArrayList<Double> time;
    ArrayList<Double> latlongs;
    ArrayList<Double> latsdouble;
    ArrayList<Double> longsdouble;
    final long MIN_LOC_UPDATE_TIME = 500;
    String walkOrBike;
    //these will be varied
    float MIN_LOC_UPDATE_DISTANCE;
    float MAX_WALKING_SPEED;
    float MAX_BIKE_SPEED;
    double latfirst;
    double longfirst;
    double field_size;
    Integer number;
    Integer count;
    ArrayList<Double>averagespacing1;
    ArrayList<Double>maximumspacing1;
    TextView fieldSize;
    TextView fieldName;
    TextView memberID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lats1 = new ArrayList<>();
        longs1 = new ArrayList<>();
        latsdouble=new ArrayList<>();
        longsdouble=new ArrayList<>();
        time = new ArrayList<>();
        latlongs = new ArrayList<>();
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = prefs2.edit();
        //declare graphview object
        mScatterPlot = (GraphView) findViewById(R.id.scatterplot);
        fieldSize = (TextView) findViewById(R.id.field_size);
        fieldName = (TextView) findViewById(R.id.field_name);
        memberID=findViewById(R.id.member_id);
        fieldSize.setText(member.getString("field_size","0.00"));
        fieldName.setText(member.getString("description","BG Field"));
        memberID.setText(member.getString("member_id","K100"));

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        provider = locationManager.getBestProvider(criteria, false);
        //take_picture.setEnabled(false);
        MIN_LOC_UPDATE_DISTANCE = Float.valueOf(member.getString("MIN_LOC_UPDATE_DISTANCE", "2"));
        MAX_WALKING_SPEED = Float.valueOf(member.getString("MAX_WALKING_SPEED", "10"));
        MAX_BIKE_SPEED = Float.valueOf(member.getString("MAX_BIKE_SPEED", "20"));
        walkOrBike = member.getString("walkorbike", "W");


        //a big problem here. the maximum photos that should be taken based on field size is 14. for this activity, you can't plot a point in the negative x axis.
        //and you can't clear a series. so I generated all the random points at once. not so efficient though
        xySeries = new PointsGraphSeries<>();

        take_picture = findViewById(R.id.take_picture);
        //  done=findViewById(R.id.done);

        //generate two lists of random values, one for x and one for y.
        xyValueArray = new ArrayList<>();

        String z = member.getString("latlongs", "NULL,NULL");

        String []latlongs2=z.split(",");
        Integer w=0;
        Log.d("latlongsss",latlongs2[1].toString());
        if (latlongs2.length>5) {
            for (w = 0; w < latlongs2.length; w++) {
                if (w == 0 || w % 2 == 0) {
                    latsdouble.add(Double.valueOf(latlongs2[w]));
                } else {
                    longsdouble.add(Double.valueOf(latlongs2[w]));

                }
            }
        }
        else {
            Toast.makeText(this,"field too small",Toast.LENGTH_LONG).show();
            Intent intent=new Intent (getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        }

        for (w=0;w<latsdouble.size();w++)
        {
            xyValueArray.add(new XYValue(longsdouble.get(w),latsdouble.get(w)));

        }

        //sort it in ASC order
        xyValueArray = sortArray(xyValueArray);
        startx =xyValueArray.get(0).getX();
        endx = xyValueArray.get(xyValueArray.size()-1).getX();
        Log.d("starttt",String.valueOf(startx));
        starty=min();
        endy=max();

        //add the data to the series
        for(int i = 0;i <xyValueArray.size(); i++){
            double x = xyValueArray.get(i).getX();
            double y = xyValueArray.get(i).getY();
            xySeries.appendData(new DataPoint(x,y),true, 1000);
        }double max=0;

        createScatterPlot();

    }public double max()

    {
        double max = 0;

        for (int i = 0; i < xyValueArray.size(); i++) {
            if (xyValueArray.get(i).getY() > max) {
                max = xyValueArray.get(i).getY();
            }
        }
        return max;
    }
    public double min()

    {
        double max = xyValueArray.get(0).getY();

        for (int i = 0; i < xyValueArray.size(); i++) {
            if (xyValueArray.get(i).getY() < max) {
                max = xyValueArray.get(i).getY();
            }
        }
        return max;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // locationManager.removeUpdates(this);
    }

    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    private void createScatterPlot() {


        //set some properties
        xySeries.setShape(PointsGraphSeries.Shape.RECTANGLE);
        xySeries.setColor(Color.BLUE);
        xySeries.setSize(3f);

        //set Scrollable and Scaleable
        mScatterPlot.getViewport().setScalable(true);
        mScatterPlot.getViewport().setScalableY(true);
        mScatterPlot.getViewport().setScrollable(true);
        mScatterPlot.getViewport().setScrollableY(true);

        //set manual x bounds
        mScatterPlot.getViewport().setYAxisBoundsManual(true);
        Log.d("emini",String.valueOf(min()));
        Log.d("eminao",String.valueOf(max()));
        mScatterPlot.getViewport().setMinY(min());
        mScatterPlot.getViewport().setMaxY((max()));
//        mScatterPlot.getViewport().setMaxY(6);
//        mScatterPlot.getViewport().setMinY(7);

        //set manual y bounds
        mScatterPlot.getViewport().setXAxisBoundsManual(true);

        mScatterPlot.getViewport().setMinX(xyValueArray.get(0).getX());
        mScatterPlot.getViewport().setMaxX(xyValueArray.get(xyValueArray.size()-1).getX());
        //mScatterPlot.getViewport().setMinX(3);
        //mScatterPlot.getViewport().setMaxX(4);
        mScatterPlot.addSeries(xySeries);

//
    }

    /**
     * Sorts an ArrayList<XYValue> with respect to the x values.
     * @param array
     * @return
     */
    private ArrayList<XYValue> sortArray(ArrayList<XYValue> array){
        /*
        //Sorts the xyValues in Ascending order to prepare them for the PointsGraphSeries<DataSet>
         */
        int factor = Integer.parseInt(String.valueOf(Math.round(Math.pow(array.size(),2))));
        int m = array.size()-1;
        int count = 0;
        Log.d(TAG, "sortArray: Sorting the XYArray.");


        while(true){
            m--;
            if(m <= 0){
                m = array.size() - 1;
            }
            Log.d(TAG, "sortArray: m = " + m);
            try{
                //print out the y entrys so we know what the order looks like
                //Log.d(TAG, "sortArray: Order:");
                //for(int n = 0;n < array.size();n++){
                //Log.d(TAG, "sortArray: " + array.get(n).getY());
                //}
                double tempY = array.get(m-1).getY();
                double tempX = array.get(m-1).getX();
                if(tempX > array.get(m).getX() ){
                    array.get(m-1).setY(array.get(m).getY());
                    array.get(m).setY(tempY);
                    array.get(m-1).setX(array.get(m).getX());
                    array.get(m).setX(tempX);
                }
                else if(tempY == array.get(m).getY()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }

                else if(array.get(m).getX() > array.get(m-1).getX()){
                    count++;
                    Log.d(TAG, "sortArray: count = " + count);
                }
                //break when factorial is done
                if(count == factor ){
                    break;
                }
            }catch(ArrayIndexOutOfBoundsException e){
                Log.e(TAG, "sortArray: ArrayIndexOutOfBoundsException. Need more than 1 data point to create Plot." +
                        e.getMessage());
                break;
            }
        }
        return array;
    }


    /**
     * customizable toast
     * @param message
     */
    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }
    public void finish(View v) {


        startActivity(new Intent(mappedFieldInformation.this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));


    }




    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(mappedFieldInformation.this, Main2Activity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();

    }


}