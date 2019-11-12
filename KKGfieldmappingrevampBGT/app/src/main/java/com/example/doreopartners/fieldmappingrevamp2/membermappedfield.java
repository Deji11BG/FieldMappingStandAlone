package com.example.doreopartners.fieldmappingrevamp2;
//this displays the members whose fields have been mapped. recycler is membermappedfieldrecycler. direclty under it.

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class membermappedfield extends AppCompatActivity {
    private static final String TAG="viewmapped_fields";
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
    Spinner spinner_lga;
    TextView staffid;
    Button next;

    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;

    //TextView staffid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membermappedfield);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        OnlineDBHelper db = new OnlineDBHelper(this);
        //spinner_lga = findViewById(R.id.spinner_lga);
        staffid=findViewById(R.id.txtName);
        //staffid=findViewById(R.id.txtName);
        Log.d(TAG, "onCreate: started.");
        // Intent intent = getIntent();
        mem_id = member.getString("staff_id","001");
        staffid.setText(mem_id);
//        staffid.setText(mem_id);
        memEdit.putString("staff_id",mem_id);
        //String member_id=getIntent().getStringExtra("member_id");
        // String lga = member.getString("lga", "deji");
        Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerv_view);
        next=findViewById(R.id.next);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //initiknumbers();
        executeService();
        //mem = findViewById(R.id.editText);
        String village=member.getString("village1","");
        String iknumber=getIntent().getStringExtra("ik_number1");

        loadRecyclerView(village,iknumber);
    }


    public void loadRecyclerView(String village,String iknumber){
        memberList2 = new ArrayList<>();
        DbHelper databaseOpenHelper = new DbHelper(getApplicationContext());
        memberList2 = databaseOpenHelper.load_member(village,iknumber);


        Log.d("--HELLO--1",memberList2+"");
        recyclerController(memberList2);
    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelclass3(
                        jsonObject.getString("first_name"),
                        jsonObject.getString("last_name"),
                        jsonObject.getString("member_id")

                        //jsonObject.getString("phone_number")



                ));
                ;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        membermappedfieldrecycler tfmAdapter = new membermappedfieldrecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(tfmAdapter);
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


    }   public void next(View v)
    {
        // starting background task to update product
        Intent fp=new Intent(getApplicationContext(),startmapping.class);
        startActivity(fp);
    }
//    public void onBackPressed()
//    {
//        super.onBackPressed();
//        startActivity(new Intent(membermappedfield.this, mappedfieldik.class));
//        finish();
//
//    }

}
