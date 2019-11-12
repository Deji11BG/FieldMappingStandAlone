package com.example.doreopartners.fieldmappingrevamp2;
//page you select the ik you want to map

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class startmapping extends AppCompatActivity {
    private static final String TAG="startmapping";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelclass1> memberList1=new ArrayList<>();
    private ArrayList<modelclass1> number_list=new ArrayList<>();
    private ArrayList<Map<String,String>> memberList2;
    private ArrayList firstname;
    private ArrayList lastname;
    private ArrayList number;
    private ArrayList phonenumber;
    String lga;
    RecyclerViewAdapter tfmAdapter;
    private RecyclerView.Adapter adapter;
    private RecyclerView mRecyclerView;
    String staff_name;
    String staff_role;
    String staff_id;
    String mem_id;
    //Spinner spinner_village;
    TextView staffid;
    String selectedlga;

    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;

    ArrayList<modelclass1> moreTGs, ikModel;

    //TextView staffid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        OnlineDBHelper db = new OnlineDBHelper(this);
        //spinner_village = findViewById(R.id.spinner_lga);
        staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");
        mem_id= member.getString("staff_id","bayo");
        staffid.setText(mem_id);

        recyclerView = findViewById(R.id.recyclerv_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        executeService();

        //initiknumbers();
        //mem = findViewById(R.id.editText);
//        try {
//            Intent intent = getIntent();
//            Bundle b = intent.getExtras();
//
//            staff_id = (String) b.get("staff_id");
//
////            prefsEdit.putString("MIN_WALKING_SPEED", intent.getStringExtra("max_walk_speed"));
////            prefsEdit.putString("MIN_BIKE_SPEED", intent.getStringExtra("max_bike_speed"));
//            memEdit.commit();
//
//            //sessionManagement = new SessionManagement(getApplicationContext());
//            //sessionManagement.CreateLoginSession(staff_name, staff_id, staff_role);
//
////            @SuppressLint("StaticFieldLeak") Asynctask.DownloadApplication x = new Asynctask.DownloadApplication(startmapping.this) {
////                protected void onPostExecute(String s) {
////                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
////                    Log.d("RESULT__NOW", s);
////                }
////            };
////            x.execute();
//
//
//        } catch (Exception e) {
//            staff_id = "T-1999999";
//            staff_role = "deji";
//            staff_name = "Adeniran Adebayo";
//            //sessionManagement = new SessionManagement(getApplicationContext());
//            //sessionManagement.CreateLoginSession(staff_name, staff_id, staff_role);
//
////            @SuppressLint("StaticFieldLeak") Asynctask.DownloadApplication x = new Asynctask.DownloadApplication(startmapping.this) {
////                protected void onPostExecute(String s) {
////                    Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
////                    Log.d("RESULT__NOW", s);
////                }
////            };
////            x.execute();
//
//
//        }

        /*String leader="Leader";
        List<String> list = db.getvillage(mem_id,leader);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);

        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_village.setAdapter(dataAdapter1);
        spinner_village.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedlga=spinner_village.getSelectedItem().toString().trim();

                loadRecyclerView(mem_id, "Leader");
                memEdit.putString("village",selectedlga);
                memEdit.commit();


            }



            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

        //String lgame=spinner_lga.getSelectedItem().toString().trim();
        //loadRecyclerView(mem_id,lgame,deji);
//        String LGA= member.getString("lga","deji");
//        Log.d("LGA",LGA);
////        Log.d("LGA",mem_id);
//        String me="T-1000000000001536";
//        String you="BIRNI-GWARI";


        //loadRecyclerView(staffID,LGA,deji);
        //startActivity(new Intent(startmapping.this, startmapping.class));

        loadRecyclerView(mem_id, "Leader");
    }

    public void loadRecyclerView(String staff_id, String leader){
        memberList2 = new ArrayList<>();
        OnlineDBHelper databaseOpenHelper = new OnlineDBHelper(getApplicationContext());
        //memberList2 = databaseOpenHelper.load_ik(staff_id,lga);
        memberList2 = databaseOpenHelper.load_ik(staff_id, leader);
        Log.d("--HELLO--1",memberList2+"");
        //recyclerController(memberList2);
        recyclerControllerAnother();
//        boolean isReady =recyclerController(memberList2).ge>1;
//        if (recyclerController(memberList2)=={
//
//        }
    }

//    }private void initiknumbers()
//    {final OnlineDBHelper db = new OnlineDBHelper(fieldselection.this);
//        final String staff_id="T-1999999";
//        //mem_id = getIntent().getStringExtra("staff_id");
//        //d=db.getstaffid();
//        //String staff_id= db.getstaffid();
//        //mNames=db.getiknumbers(staff_id);
//        //final ArrayList<String>
//               // String ik_number=getIntent().getStringExtra("ik_number");
//            String lga= member.getString("lga","deji");
//                //mNames=db.getmember_id(lga,mem_id);
//        String LGA="GWARZO";
//        String staffid="T-1000000000000465";
//
//        mNames=db.getmember_id(LGA,staffid);
//        firstname=new ArrayList();
//        lastname=new ArrayList();
//        phonenumber=new ArrayList();
//        number=new ArrayList();
//                for (int i =0;i<mNames.size();i++)
//                {
//                    String member=mNames.get(i);
//                    firstname.add(db.getfirstname(member));
////                    firstname=db.getfirstname(member);
//                    lastname.add(db.getlastname(member));
//                    phonenumber.add(db.getphonenumber(member));
//                    number.add(String.valueOf(0));
////                    initRecyclerView();
//
//
//
//                }
//                    //initRecyclerView();
////                firstname=db.getfirstname(mNames.toString());
////                lastname=db.getlastname(mNames.toString());
////                phonenumber=db.getphonenumber(mNames.toString());
////                prefsEdit.putString("first_name",firstname);
////                prefsEdit.putString("last_name",lastname);
////                prefsEdit.putString("phone_number",phonenumber);
////                String crop_type=db.getcroptype(mNames.toString());
////                prefsEdit.putString("crop_type",crop_type);
////                String mapping_date=db.getmapping_date(mNames.toString());
////                prefsEdit.putString("mapping_date",mapping_date);
////                String iknumbers=db.getiknumber(spinner_lga.getSelectedItem().toString(),mem_id).toString();
////                //prefsEdit.putString("ik_numbers",iknumbers);
////                String unique_id=db.getuniqueid(mNames.toString());
////                prefsEdit.putString("member_unique_id",unique_id);
////                String village=db.getvillage(mNames.toString());
////                prefsEdit.putString("village",village);
//
//
//               //String lga= member.getString("lga","deji");
//               // prefsEdit.commit();
//                //number=String.valueOf(db.membernumber(mem_id,member.getString("lga","lga")));
//                //mNames.add(staff_id);
//
//                //List<String> X = db.getmember_id(spinner_lga.getSelectedItem().toString());
//                //ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<>(startmapping.this, android.R.layout.simple_spinner_item, X);
//
//
//        Log.d("result", String.valueOf(mNames));
//        //mNames.add ("Deji");
//
//
//    }
//                mNames = db.getiknumbers(mem_id);
//                mNames.add(staff_id);
//                prefsEdit.putString("member_id",member_id);
    //mNames.add(list2);
    //RecyclerView recyclerView=findViewById(R.id.recyclerv_view);

//    private void initRecyclerView(){
//        Log.d(TAG,"initRecyclerView: init recyclerview.");
//        RecyclerView recyclerView=findViewById(R.id.recyclerv_view);
//        RecyclerViewAdapter2 adapter=new RecyclerViewAdapter2(mNames,firstname,lastname,number,phonenumber,this);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;

        Log.d("DDEEJJII",wordList.toString());
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelclass1(
                        jsonObject.getString("first_name"),
                        jsonObject.getString("last_name"),
                        jsonObject.getString("ik_number"),
                        jsonObject.getString("village")




                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
//        }   for(int i = 0; i<jsonArray.length();i++){
//            try {
//                jsonObject = jsonArray.getJSONObject(i);
//                number_list.add(new modelclass3(
//                        jsonObject.getString("number")
//
//
//
//                ));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }

//        }
        }

        Log.d("DDEEJJII",memberList1.toString());
        tfmAdapter = new RecyclerViewAdapter(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
    }

    private void recyclerControllerAnother(){
        //ikModel.clear();modelclass1
        OnlineDBHelper databaseOpenHelper = new OnlineDBHelper(getApplicationContext());
        ikModel = databaseOpenHelper.getIKArray();
        tfmAdapter = new RecyclerViewAdapter(ikModel,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                OnlineDBHelper databaseOpenHelper = new OnlineDBHelper(getApplicationContext());
                moreTGs = databaseOpenHelper.getIKs(10, page * 10);
                ikModel.addAll(moreTGs);
                view.post(new Runnable() {
                    @Override
                    public void run() {
                        int curSize = tfmAdapter.getItemCount();
                        tfmAdapter.notifyItemRangeInserted(curSize, ikModel.size() - 1);
                    }
                });
            }
        };
        recyclerView.addOnScrollListener(scrollListener);
    }

    public void onDestroy() {
        super.onDestroy();
        executeService();
    }

    public void executeService(){
        componentName = new ComponentName(this, BackgroundSync.class);
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
        builder.setPeriodic(1000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setPersisted(true);
        jobInfo = builder.build();
        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(jobInfo);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main3, menu);

        MenuItem search = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);
//        searchView.setOnQueryTextListener(Main2Activity.this);

        return true;
    }

    //This method is used by the search view side application
    private void search(SearchView searchView){

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                Log.d("CHECK", "Text being entered:" + newText);
                if (tfmAdapter != null){
                    tfmAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });
    }

}
