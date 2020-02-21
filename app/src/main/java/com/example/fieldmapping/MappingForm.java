package com.example.fieldmapping;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonIOException;
import com.shuhart.stepview.StepView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


//FILLING THE MAPPING FORM. 3 RECYCLER VIEWS ARE USED HERE
public class MappingForm extends AppCompatActivity {

    private int currentStep = 0;
    RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    TextView txtName;
    SharedPreferences.Editor memEdit;
    String memberName;
    String cropType;
    String tgId;
    String fieldId;
    double fieldSize;
    String latlong;
    Set1Adapter adapter1;
    Set2Adapter adapter2;
    Set3Adapter adapter3;
    ArrayList<Set1> tsns;
    ArrayList<Set1> tsns2;
    ArrayList<Set3> tsns3;
    double field;

    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_form);
        //OnlineDBHelper db=new OnlineDBHelper(this);

        txtName = findViewById(R.id.txtNameMappingForm);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();

        //txtName.setText(member.getString("first_name","David Bones"));
        String first_name=member.getString("first_name","koye");
        String last_name=member.getString("last_name","sodipo");



        //AlertDialog alertDialog = builder.create();
        //alertDialog.setCancelable(false);
        txtName.setText(first_name+" "+last_name);
        memberName = member.getString("first_name", "Koye Sodipo");
        cropType = member.getString("croptype", "Maize");
        tgId = member.getString("tgid", "IK000123");
        fieldId = member.getString("fieldid", "IK000123-01-01");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        mydate  = dateFormat.format(date);

        memEdit.putString("timestamp",mydate);
        memEdit.commit();

//        member = getSharedPreferences("member", MODE_PRIVATE);
//        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
//        memEdit = member.edit();

        //fieldSize = Double.valueOf(f);

        tsns = new ArrayList<>();
        tsns2 = new ArrayList<>();
        tsns3 = new ArrayList<>();
        tsns.add(new Set1(getString(R.string.numtxt), getString(R.string.q1), "Farmer", prefs2.getString("ans1", "")));
        tsns.add(new Set1(getString(R.string.num2), getString(R.string.q2), "MIK", prefs2.getString("ans2", "")));
        tsns.add(new Set1(getString(R.string.num3), getString(R.string.q3), "MIK", prefs2.getString("ans3", "")));
        tsns.add(new Set1(getString(R.string.num4), getString(R.string.q4), "MIK", prefs2.getString("ans4", "")));
        tsns.add(new Set1(getString(R.string.num5), getString(R.string.q5), "MIK", prefs2.getString("ans5", "")));
        tsns.add(new Set1(getString(R.string.num6), getString(R.string.q6), "Farmer", prefs2.getString("ans6", "")));
        tsns.add(new Set1(getString(R.string.num7), getString(R.string.q7), "Farmer", prefs2.getString("ans7", "")));
        tsns.add(new Set1(getString(R.string.num8), getString(R.string.q8), "Farmer", prefs2.getString("ans8", "")));
        //tsns.add(new Set1 ("","farmer",))
        tsns.add(new Set1(getString(R.string.num9), getString(R.string.q9), "Farmer", prefs2.getString("ans9", "")));
        tsns.add(new Set1(getString(R.string.num10), getString(R.string.q10), "Farmer", prefs2.getString("ans10", "")));
        tsns.add(new Set1(getString(R.string.num11), getString(R.string.q11), "Farmer", prefs2.getString("ans11", "")));

        tsns2.add(new Set1(getString(R.string.num12), getString(R.string.q12), "Farmer", prefs2.getString("ans1a", "")));
        tsns2.add(new Set1(getString(R.string.num13), getString(R.string.q13), "MIK", prefs2.getString("ans2a", "")));
        tsns2.add(new Set1(getString(R.string.num14), getString(R.string.q14), "MIK", prefs2.getString("ans3a", "")));



        //if()
        tsns3.add(new Set3(getString(R.string.num15a), getString(R.string.q15a), "Farmer")); //prefs.getInt("ans1d", 0), prefs.getInt("ans1r", 0)));

        tsns3.add(new Set3(getString(R.string.num15b), getString(R.string.q15b), "Farmer"));
        tsns3.add(new Set3(getString(R.string.num15c), getString(R.string.q15c), "Farmer"));
        tsns3.add(new Set3(getString(R.string.num15d), getString(R.string.q15d), "Farmer"));

        recyclerView = findViewById(R.id.rv);
        adapter1 = new Set1Adapter(this, tsns, new Set1Adapter.OnItemClickListener() {
            @Override
            public void onClick(Set1 trans) {
            }
        });
        adapter2 = new Set2Adapter(this, tsns2, new Set2Adapter.OnItemClickListener() {
            @Override
            public void onClick(Set1 trans) {

            }
        });
        adapter3 = new Set3Adapter(this, tsns3, new Set3Adapter.OnItemClickListener() {
            @Override
            public void onClick(Set3 trans) {



            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter1);

        final StepView stepView = findViewById(R.id.sv);
        stepView.setOnStepClickListener(new StepView.OnStepClickListener() {
            @Override
            public void onStepClick(int step) {
            }
        });
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep <= stepView.getStepCount() - 1) {
                    switch (currentStep) {
                        case 0:
                            if (adapter1.isFilledset1()) {
                                recyclerView.setAdapter(adapter2);
                                currentStep++;
                                stepView.go(currentStep, true);

                            }else {
                                Toast.makeText(MappingForm.this, getString(R.string.allInputCheck), Toast.LENGTH_SHORT).show();
                            }
                            break;
                        case 1:
                            if (adapter2.isFilled()) {
                                recyclerView.setAdapter(adapter3);
                                currentStep++;

                                stepView.go(currentStep, true);
                            } else {
                                Toast.makeText(MappingForm.this, getString(R.string.allInputCheck), Toast.LENGTH_SHORT).show();
                            }


                            break;
                        case 2:


                            if (adapter3.entrycheck()==false){
                                Toast.makeText(MappingForm.this, getString(R.string.allInputCheck), Toast.LENGTH_SHORT).show();
                            }else
                            {
                                new AlertDialog.Builder(MappingForm.this)
                                        .setTitle(R.string.cert)
                                        .setMessage(R.string.certText)
                                        .setPositiveButton(R.string.certYes, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                //TODO 2. This is where I send the data to your app, check the response function for the keys

                                                //Intent intent = new Intent(MappingForm.this, mappedFieldInformation.class);
                                                finish();
//                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                            intent.setComponent(new ComponentName("com.babbangona.tglapp","com.babbangona.tglapp.startmapping"));
                                                try {

                                                    // intent.putExtra("json", responses());
                                                    //intent.putExtra("module", "mapping");
                                                    //intent.putExtra("staff_id", member.getString("staff_id", "1234"));
                                                    Log.d("preffs",prefs2.getString("ans1","me"));
                                                    Log.d("preffs2",prefs2.getString("ans2","me"));

                                                    //memEdit.clear().commit();
                                                    //prefs2.clear().
                                                    //insert();



                                                /*try {
                                                    insertIntooAutoGerminationApp();
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                    Log.d("autoGermination", e.toString());
                                                    Toast.makeText(MappingForm.this, "Auto-Germination application missing in your phone. Contact Supervisor", Toast.LENGTH_LONG).show();
                                                }*/
                                                } catch (JsonIOException e) {
                                                    //Intent intent2 = new Intent(MappingForm.this,MappingForm.class);

                                                    //Toast.makeText(this, "Please, go back and answer the questions correctly", Toast.LENGTH_SHORT).show();

                                                }
                                                //databaseHelper.saverecords(jsonArray);


//                                            @SuppressLint("StaticFieldLeak") Asynctask2.UpploadTast x = new Asynctask2.UpploadTast(MappingForm.this) {
//                                                protected void onPostExecute(String s) {
//                                                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
//                                                    Log.d("RESULT__NOW", s);
//                                                }
//                                            };
//                                            x.execute();
                                                try {
                                                    //startActivity(intent);
                                                } catch (JsonIOException e) {
                                                    //Toast.makeText(this,"please go back and anser the questions correclty",Toast.LENGTH_SHORT).show();
                                                }
                                                //startActivity(intent2);

                                                //Log.d("deji","ans1");

                                            }
                                        })

                                        .setNegativeButton(R.string.certNo, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                            }
                                        })
                                        .show();
                                break;
                            }
                    }
                } else {
                    stepView.done(true);
                }


            }
        });
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentStep > 0) {
                    switch (currentStep) {
                        case 0:
                            break;
                        case 1:
                            recyclerView.setAdapter(adapter1);
                            break;
                        case 2:
                            recyclerView.setAdapter(adapter2);
                            break;
                    }
                    currentStep--;
                }
                stepView.done(false);
                stepView.go(currentStep, true);
            }
        });
        List<String> steps = new ArrayList<>();
        steps.add("Start");
        steps.add(" ");
        steps.add("Finish");
        stepView.setSteps(steps);

    }

    //this is the function that generates the json string, check the map for the keys and values
//    public String responses() {
//        ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
//        HashMap<String, String> map = new HashMap<>();
//
//        String firstname = member.getString("first_name", "Koye");
//        String lastname = member.getString("last_name", "Sodipo");
//        cropType = member.getString("crop_type", "Maize");
//        String ik_number = member.getString("ik_number", "IK000123");
//        //fieldId = member.getString("croptype", "IK000123-01-01");
//        //String f = member.getString("field_size", "");
//        String z = member.getString("latlongs", "NULL,NULL");
//        String staff_id = member.getString("staff_id", "IK000123");
//        final OnlineDBHelper db = new OnlineDBHelper(MappingForm.this);
//        String member_id = member.getString("member_id", "10001");
//        String middle = member.getString("middle", "deji");
//        String ID = staff_id + "-" + mydate + "-" + "F";
//        String minlat = member.getString("minlat", "notyet");
//        String maxlat = member.getString("maxlat", "notyet");
//        String minlng = member.getString("minlng", "notyet");
//        String maxlng = member.getString("maxlng", "notyet");
//        String TFMuniqueid=member.getString("TFMuniqueid", "notyet");
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//        Date date = new Date();
//        mydate  = dateFormat.format(date);
//
//        latlong = z;
//        //String[] x = z.split("_");
//        //latlong=x[0];
////        fieldSize = Float.parseFloat(x[0]);
//        //latlong=
//
//        //Log.d("HHEELLOO", String.valueOf(f));
//
//        map.put("first_name", firstname);
//        map.put("last_name", lastname);
//        map.put("crop_type", cropType);
//        map.put("ik_number", ik_number);
//        //map.put("test_Id", fieldId + "01");
//        String f = member.getString("fieldsize", "1");
////String f="0.18";
//        Log.d("FFLLOOAATT",f);
//        //String[] x = f.split(".");
//        String[] x = f.split("\\.");
//        Log.d("xmm",String.valueOf(x));
//
//        String y=x[1];
//        Log.d("ddddd",String.valueOf(y));
//
//        String charAtZero =  Character.toString(y.charAt(0));
//        Log.d("jwdnjdn",String.valueOf(charAtZero));
//        String w=x[0]+"."+charAtZero;
//        Log.d("jsjsjd",String.valueOf(w));
//        double round=Double.valueOf(w);
//        String charAtone = Character.toString(y.charAt(1));
//        Integer a=Integer.valueOf(charAtone);
//        Log.d("dkdkk",String.valueOf(charAtone));
//        Log.d("sjkssds",String.valueOf(a));
//        if (a>=7)
//        {
//            field=round+0.1;
//            map.put("field_size", String.valueOf(field));
//        }
//        else
//        {
//            field=round+0.0;
//            map.put("field_size", String.valueOf(field));
//        }
//
//        map.put("staff_id", staff_id);
//        map.put("member_id", member_id);
//        map.put("middle", middle);
//        map.put("minlat", minlat);
//        map.put("maxlat", maxlat);
//        map.put("minlng", minlng);
//        map.put("maxlng", maxlng);
//        map.put("TFMuniqueid",TFMuniqueid);
//        map.put("unique_id", prefs2.getString("unique_id","00001"));
//        map.put("timestamp", prefs2.getString("timestamp", mydate));
//
//        map.put("q1", prefs2.getString("ans1", "no"));
//        map.put("q2", prefs2.getString("ans2", "no"));
//        map.put("q3", prefs2.getString("ans3", "no"));
//        map.put("q4", prefs2.getString("ans4", "no"));
//        map.put("q5", prefs2.getString("ans5", "no"));
//        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date3 = new Date();
//        String mydate  = dateFormat3.format(date3);
//        map.put("date",member.getString("today",mydate));
//        map.put("field_status","active");
//        map.put("upload_status","0");
//
//        map.put("latlongs", z);
////        if (cropType.equals("Rice")) {
////            map.put("q6", prefs.getString("ans6", "no"));
////            map.put("q6", prefs.getString("ans6", "no"));
////
////        } else {
////            if (prefs.getString("ans6", "").equals("yes")) {
////                map.put("q6", "no");
////            } else {
////                map.put("q6", "yes");
////            }
////            map.put("10", prefs.getString("ans6", "no"));
////
////        }
//        map.put("q6", prefs2.getString("ans6", "no"));
//
//        map.put("q7", prefs2.getString("ans7", "no"));
//        map.put("q8", prefs2.getString("ans8", "no"));
//        map.put("q9", prefs2.getString("ans9", "no"));
//        map.put("q10", prefs2.getString("ans10", "no"));
//        map.put("q11", prefs2.getString("ans11", "no"));
//        map.put("q12", prefs2.getString("ans1a", "0"));
//        map.put("q13", prefs2.getString("ans2a", "0"));
//        map.put("q14", prefs2.getString("ans3a", "0"));
//        map.put("description", prefs2.getString("description", "good"));
//        map.put("village", member.getString("village", ""));
//
//        map.put("q15ad", prefs2.getString("ans1d", "Maize"));
//        map.put("q15bd", prefs2.getString("ans2d", "Maize"));
//        map.put("q15cd", prefs2.getString("ans3d", "Maize"));
//        map.put("version",BuildConfig.VERSION_NAME);
//        try {
//            String action = prefs2.getString("ans4d", "Maize");
//            map.put("q15dd", action);
//            map.put("q15ar", prefs2.getString("ans1r", "Maize"));
//            map.put("q15br", prefs2.getString("ans2r", "Maize"));
//            map.put("q15cr", prefs2.getString("ans3r", "Maize"));
//            map.put("q15dr", prefs2.getString("ans4r", "Maize"));
//        }
//        catch(JsonIOException e)
//        {
//            Toast.makeText(this, "Please, go back and answer the questions ", Toast.LENGTH_SHORT).show();
//        }
//
//
//        wordList.add(map);
//        DbHelper databaseHelper = new DbHelper(this);
//
//
//        Gson gson = new GsonBuilder().create();
//        Log.d("Deji2", gson.toJson(wordList));
//        JSONArray jsonArray = null;
//        try {
//            jsonArray = new JSONArray(gson.toJson(wordList));
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        //databaseHelper.saverecords(jsonArray);
//        //finalDatabase resultDB = Room.databaseBuilder(getApplicationContext(),
//          //      finalDatabase.class, "fields").build();
//        //MyApp.database =  Room.databaseBuilder(this, AppDatabase::class.java, "MyDatabase").allowMainThreadQueries().build();
//
//        databaseHelper.saverecords(jsonArray);
//        return gson.toJson(wordList);
//    }
// try {
//        jsonArray = new JSONArray(gson.toJson(wordList));

    public void insert(){
        try {
            String URL = "content://com.babbangona.shpincentiveapp/operations";
            Uri students = Uri.parse(URL);
            ContentValues contentValues = new ContentValues();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            String date_updated  = dateFormat.format(date);


            SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = new Date();
            String date_updated1  = dateFormat1.format(date1);

            contentValues.put("activity_name","Field Mapping");
            String staff_id=member.getString("staff_id","IK000123");
            contentValues.put("staff_id",staff_id);
            contentValues.put("upload_status","0");
            String ID =staff_id+ "-"+ mydate +"-"+"F";
            contentValues.put("unique_id",ID);
            contentValues.put("activity_flag",ID);


            contentValues.put("activity_date",date_updated);
            contentValues.put("statistics_date",date_updated1);
            Uri x = getContentResolver().insert(students,contentValues);
            long rawContactId = ContentUris.parseId(x);
            Log.d("ERROR__VAL",rawContactId+"");

        }catch (Exception e)
        {
            Toast.makeText(this, getString(R.string.incCheck),Toast.LENGTH_SHORT).show();
        }
    }

//    public void insertIntoClearance(){
//
//            String URL = "content://com.example.bg10.clearancemodule/fields";
//            Uri fields = Uri.parse(URL);
//            ContentValues contentV = new ContentValues();
//
//            String f = member.getString("fieldsize", "1");
//            String[] x = f.split("\\.");
//            String y = x[1];
//            String charAtZero =  Character.toString(y.charAt(0));
//            Log.d("jwdnjdn",String.valueOf(charAtZero));
//            String w=x[0]+"."+charAtZero;
//            Log.d("jsjsjd",String.valueOf(w));
//            double round=Double.valueOf(w);
//            String charAtone = Character.toString(y.charAt(1));
//            Integer a=Integer.valueOf(charAtone);
//            if (a>=7)
//            {
//                field=round+0.1;
//            }
//            else
//            {
//                field=round+0.0;
//            }
//
//            String[] new_member_id = (Objects.requireNonNull(member.getString("member_id", "10001"))).split("-");
//            String[] new_field_id = (Objects.requireNonNull(member.getString("fieldid", "IK000123-01-01"))).split("-");
//
//            String season_id = new_member_id[0];
//            String member_id = new_member_id[2];
//            String field_id = new_field_id[2];
//
//
//            contentV.put("season_id", season_id);
//            contentV.put("ik_number", member.getString("ik_number", "IK000123"));
//            contentV.put("member_id", member_id);
//            contentV.put("field_id", field_id);
//            contentV.put("old_ik_number", member.getString("ik_number", "IK000123"));
//            contentV.put("old_member_id", member_id);
//            contentV.put("old_field_id", field_id);
//            contentV.put("size", String.valueOf(field));
//            contentV.put("old_field_size", String.valueOf(field));
//            contentV.put("crop", "Maize");
//            contentV.put("crop_type", member.getString("crop_type", "Maize"));
//            contentV.put("unique_member_id", member.getString("TFMuniqueid", "notyet"));
//            contentV.put("unique_field_id", prefs2.getString("unique_id","00001"));
//            contentV.put("status", "Active");
//            contentV.put("description", dami_description);
//            contentV.put("audit", "audit");
//            contentV.put("sync", "yes");
//
//        try {
//            Uri uri = getContentResolver().insert(fields, contentV);
//            long result = ContentUris.parseId(uri);
//            Log.d("Clearance_RESPONSE", result + "");
//        } catch (Exception e) {
//            Log.d("iClearField",e.toString());
//            //Toast.makeText(this, "Insertion not working, moving to update", Toast.LENGTH_SHORT).show();
//
//            try {
//                String where = "unique_field_id =  \""+ prefs2.getString("unique_id","00001") +"\"";
//
//                int update = getContentResolver().update(fields,contentV,where,null);
//                Log.d("Clearance_RESPONSE", update + "");
//            } catch (Exception e1) {
//                Log.d("iClearField",e.toString());
//                Toast.makeText(MappingForm.this, "Unable to insert into Clearance App - Fields.", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

//    public void insertIntooAutoGerminationApp() {
//
//        String f = member.getString("fieldsize", "1");
//        String[] x = f.split("\\.");
//        String y = x[1];
//        String charAtZero =  Character.toString(y.charAt(0));
//        Log.d("jwdnjdn",String.valueOf(charAtZero));
//        String w=x[0]+"."+charAtZero;
//        Log.d("jsjsjd",String.valueOf(w));
//        double round=Double.valueOf(w);
//        String charAtone = Character.toString(y.charAt(1));
//        Integer a=Integer.valueOf(charAtone);
//        if (a>=7)
//        {
//            field=round+0.1;
//        }
//        else
//        {
//            field=round+0.0;
//        }
//
//        OnlineDBHelper db = new OnlineDBHelper(this);
//        Map<String, String> wordList;
//        wordList = db.selectDetails(member.getString("member_id", "10001"));
//        Map<String, String> container = wordList;
//
//        String phone_number = container.get("phone_number");
//        String lga = container.get("lga");
//        String village = container.get("village");
//        String member_role = container.get("member_role");
//
//        String URL = "content://com.example.doreopartners.scatterplot/fields";
//        Uri fields = Uri.parse(URL);
//        ContentValues contentV = new ContentValues();
//
//        contentV.put("member_id", member.getString("member_id", "10001"));
//        contentV.put("ik_number", member.getString("ik_number", "IK000123"));
//        contentV.put("first_name", member.getString("first_name", "Koye"));
//        contentV.put("last_name", member.getString("last_name", "Sodipo"));
//        contentV.put("phone_number", phone_number);
//        contentV.put("lga", lga);
//        contentV.put("unique_id", prefs2.getString("unique_id","00001"));
//        contentV.put("village", village);
//        contentV.put("field_size", String.valueOf(field));
//        contentV.put("member_role", member_role);
//        contentV.put("staff_id", member.getString("staff_id", "IK000123"));
//        contentV.put("latlongs", member.getString("latlongs", "NULL,NULL"));
//        contentV.put("minlat",  member.getString("minlat", "notyet"));
//        contentV.put("maxlat", member.getString("maxlat", "notyet"));
//        contentV.put("minlng", member.getString("minlng", "notyet"));
//        contentV.put("maxlng", member.getString("maxlng", "notyet"));
//        contentV.put("description", dami_description);
//
//        try {
//            Uri uri = getContentResolver().insert(fields, contentV);
//            long result = ContentUris.parseId(uri);
//            Log.d("auto_RESPONSE", result + "");
//        } catch (Exception e) {
//            Log.d("iAutoField",e.toString());
//            Toast.makeText(MappingForm.this, "Insertion not working, moving to update", Toast.LENGTH_SHORT).show();
//
//            try {
//                String where = "unique_id =  \""+ prefs2.getString("unique_id","00001") +"\"";
//
//                int update = getContentResolver().update(fields,contentV,where,null);
//                Log.d("auto_RESPONSE", update + "");
//            } catch (Exception e1) {
//                Log.d("iAutoField",e.toString());
//                Toast.makeText(MappingForm.this, "Unable to insert into Auto-Germination App - Fields.", Toast.LENGTH_LONG).show();
//            }
//        }
//
//    }




    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        startActivity(new Intent(MappingForm.this, MainActivity.class));
        finish();

    }
//    public void mapping_form_back(View v) {
//        // starting background task to update product
//        startActivity(new Intent(MappingForm.this, MainActivity.class));
//        finish();    }
//    public void mapping_form_help(View v) {
//        // starting background task to update product
//        try {
//            startActivity(new Intent(MappingForm.this, ViewActivityIssues.class)
//                    .putExtra("activity_id", "mapping_form_1")
//                    .putExtra("app_id", "field_mapping")
//                    .putExtra("staff_id", "")
//                    .putExtra("user_location", ""));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(MappingForm.this, "Activity not found", Toast.LENGTH_LONG).show();
//        }
//        //finish();
//    }

}
