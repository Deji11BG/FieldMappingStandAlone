package com.example.doreopartners.fieldmappingrevamp2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
//landing page after access control
public class Main2Activity extends AppCompatActivity {
    private static final String TAG="mappedfieldik";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelclass3> memberList1=new ArrayList<>();
    private ArrayList<modelclass3> number_list=new ArrayList<>();
    private ArrayList<Map<String,String>> memberList2;
    private ArrayList firstname;
    private ArrayList lastname;
    private ArrayList number;
    private ArrayList phonenumber;
    String lga;
    private RecyclerView.Adapter adapter;
    private RecyclerView mRecyclerView;
    String staff_name;
    String staff_role;
    String staff_id;
    String mem_id;

    TextView staffid;
    Button map;
    Button sync_down;
    Button sync_up;
    Button show_mappedfields;
    Button stats;
    Button export;
    Button sendToClearance;

    SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_functions);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();

        staffid=findViewById(R.id.staff_id);
        map=findViewById(R.id.map);
        sync_down=findViewById(R.id.members);
        sync_up=findViewById(R.id.fields);
        show_mappedfields=findViewById(R.id.mapped_fields);
        stats=findViewById(R.id.statistics);
        export = (Button) findViewById(R.id.export);
        //sendToClearance = (Button) findViewById(R.id.sendToClearance);

        Log.d(TAG, "onCreate: started.");
        mem_id = getIntent().getStringExtra("staff_id");
        if (mem_id==null)
        {
            mem_id=member.getString("staff_id","001");
        }else
        {
            memEdit.putString("staff_id",mem_id);
            memEdit.commit();
        }
        staffid.setText(mem_id);
        Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        try {//EXTRACTING USER DETAILS
            Intent intent = getIntent();
            Bundle b = intent.getExtras();
            staff_name = (String) b.get("staff_name");
            staff_role = (String) b.get("staff_role");
            staff_id = (String) b.get("staff_id");

            sessionManagement = new SessionManagement(getApplicationContext());
            sessionManagement.CreateLoginSession(staff_name, staff_id, staff_role);


        } catch (Exception e) {

        }






    }
//Exporting to excel method

    public void export(View v) {

        String directory_path = Environment.getExternalStorageDirectory().getPath();
        Log.d("directory", "" + directory_path);
        SQLiteToExcel sqliteToExcel;
        final int[] count = {0};
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Do the file write
            sqliteToExcel = new SQLiteToExcel(getApplicationContext(), "FieldMappingrevamp.db", directory_path + "/Download");
            try {
                sqliteToExcel.exportAllTables("FieldMappingrevamp.xls", new SQLiteToExcel.ExportListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onCompleted(String filePath) {
                        count[0]++;
                        Log.d("count", "" + count[0]);
                        Toast.makeText(getApplicationContext(), "Exported Successfully", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("eeudhhdh", "" + e);
                        ;
                    }
                }, getApplicationContext());
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Export failed. Sync down all databases on first installation",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

        }

    }

    public void sendToClearance(View v) {

        String URL = "content://com.example.bg10.clearancemodule/fields";
        Uri fields = Uri.parse(URL);
        ContentValues contentV = new ContentValues();

        contentV.put("season_id", "1");
        contentV.put("ik_number", "2");
        contentV.put("member_id", "3");
        contentV.put("field_id", "4");
        contentV.put("old_ik_number", "5");
        contentV.put("old_member_id", "6");
        contentV.put("old_field_id", "7");
        contentV.put("size", "8");
        contentV.put("old_field_size", "9");
        contentV.put("crop", "10");
        contentV.put("crop_type", "11");
        contentV.put("unique_member_id", "12");
        contentV.put("unique_field_id", "13");
        contentV.put("status", "14");
        contentV.put("description", "15");
        contentV.put("audit", "16");
        contentV.put("sync", "17");

        try {
            Uri uri = getContentResolver().insert(fields, contentV);
            long result = ContentUris.parseId(uri);
            Log.d("Clearance_RESPONSE", result + "");
        } catch (Exception e) {
            Log.d("iClearField",e.toString());
            Toast.makeText(Main2Activity.this, "Insertion not working, moving to update", Toast.LENGTH_SHORT).show();

            try {
                String where = "unique_field_id =  \""+ member.getString("TFMuniqueid", "notyet")+"\"";

                int update = getContentResolver().update(fields,contentV,where,null);
                Log.d("Clearance_RESPONSE", update + "");
            } catch (Exception e1) {
                Log.d("iClearField",e.toString());
                Toast.makeText(Main2Activity.this, "Unable to insert into Clearance App - Fields.", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void sendToAutoGerm(View v) {

        String URL = "content://com.example.doreopartners.scatterplot/fields";
        Uri fields = Uri.parse(URL);
        ContentValues contentV = new ContentValues();

        contentV.put("member_id", "1");
        contentV.put("ik_number", "2");
        contentV.put("first_name", "3");
        contentV.put("last_name", "4");
        contentV.put("phone_number", "5");
        contentV.put("lga", "6");
        contentV.put("unique_id", "7");
        contentV.put("village", "8");
        contentV.put("field_size", "9");
        contentV.put("member_role", "10");
        contentV.put("staff_id", "11");
        contentV.put("latlongs", "12");
        contentV.put("minlat", "13");
        contentV.put("maxlat", "14");
        contentV.put("minlng", "15");
        contentV.put("maxlng", "16");
        contentV.put("description", "17");

        try {
            Uri uri = getContentResolver().insert(fields, contentV);
            long result = ContentUris.parseId(uri);
            Log.d("Clearance_RESPONSE", result + "");
        } catch (Exception e) {
            Log.d("iClearField",e.toString());
            Toast.makeText(Main2Activity.this, "Insertion not working, moving to update", Toast.LENGTH_SHORT).show();

            try {
                String where = "unique_id =  \""+ "7" +"\"";

                int update = getContentResolver().update(fields,contentV,where,null);
                Log.d("Clearance_RESPONSE", update + "");
            } catch (Exception e1) {
                Log.d("iClearField",e.toString());
                Toast.makeText(Main2Activity.this, "Unable to insert into Auto-Germination App - Fields.", Toast.LENGTH_LONG).show();
            }
        }

    }

// TglDB Method



    public void map_fields(View v) {
        // starting background task to update product
        //Intent fp=new Intent(getApplicationContext(),startmapping.class);
        Intent fp=new Intent(getApplicationContext(),boundaryChecks.class);
        startActivity(fp);
    }

    public void sync_members(View v) {Toast.makeText(this, "please wait as download is in progress.", Toast.LENGTH_SHORT).show();
        // starting background task to update product
        @SuppressLint("StaticFieldLeak") Asynctask.DownloadApplication x = new Asynctask.DownloadApplication(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        x.execute();
    }

    public void sync_fields(View v) {
        // starting background task to update product
        @SuppressLint("StaticFieldLeak") Asynctask2.UpploadTast x = new Asynctask2.UpploadTast(Main2Activity.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        x.execute();
    }
    public void show_statistics(View v)
    {
        // starting background task to update product
        Intent fp=new Intent(getApplicationContext(),statistics.class);
        startActivity(fp);
    }
    public void show_fields(View v)
    {
        // starting background task to update product
        Intent fp=new Intent(getApplicationContext(),mappedfieldik.class);
        startActivity(fp);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(Main2Activity.this, MainActivity.class));
        finish();

    }

}
