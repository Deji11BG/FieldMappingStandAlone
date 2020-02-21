package com.example.fieldmapping;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class TGhome extends AppCompatActivity implements TFMHomeInterface {
    @BindView(R.id.et_search)
    TextView et_search;

    @BindView(R.id.search_edit_text)
    EditText search_edit_text;

    @BindView(R.id.back_to_toolbar)
    ImageView back_to_toolbar;

    @BindView(R.id.search_linear_layout)
    LinearLayout search_linear_layout;

    @BindView(R.id.toolbar_linear_layout)
    LinearLayout toolbar_linear_layout;
    TFMHomePresenter tfmHomePresenter;

    private static final String TAG="viewmapped_fields";
    private ArrayList<String> mNames=new ArrayList<>();
    private ArrayList<modelmemberinfo> memberList1=new ArrayList<>();
    //private ArrayList<modelmappedfield> memberList1=new ArrayList<>();
    private ArrayList<modelmemberinfo> number_list=new ArrayList<>();
    private List<MembersTable> memberList2=new ArrayList<>();
    private List<fields> memberListFM;
    private ArrayList<Map<String,String>> memberList3;
    private ArrayList<Map<String,String>> memberList4;
    private ArrayList<Map<String,String>> memberList5;

    private ArrayList firstname;
    private ArrayList lastname;
    private ArrayList number;
    private ArrayList phonenumber;
    String lga;
    private tghomerecycler adapter;
    private RecyclerView mRecyclerView;
    String staff_name;
    String staff_role;
    TextView staff_id;
    TextView mem_id;
    Spinner spinner_lga;
    TextView lastSyncTime,version;
    Button next;
    ImageView mImageView;
    double fieldSizeSum=0;

    //SessionManagement sessionManagement;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;

    //General Session Management
    SharedPreferenceController sharedPreferenceController;

    private JobScheduler jobScheduler;
    private JobInfo jobInfo;
    ComponentName componentName;
    private static final int JOB_ID =101;
    String first_name;
    String last_name;
    Button complete,incomplete;
    // TextView mem_id;
    Integer recyclerflag=0;
    tghomerecycler tfmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tghome);
        ButterKnife.bind(TGhome.this);
        tfmHomePresenter = new TFMHomePresenter(TGhome.this);
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);
        member = getSharedPreferences("member", MODE_PRIVATE);
        // prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        memEdit = member.edit();
        //OnlineDBHelper db = new OnlineDBHelper(this);
        //spinner_lga = findViewById(R.id.spinner_lga);
        staff_id=findViewById(R.id.tghomestaffid);
        lastSyncTime=findViewById(R.id.last_sync_date);
        version=findViewById(R.id.version);
        incomplete=findViewById(R.id.incomplete);
        complete=findViewById(R.id.complete);
        incomplete.setBackgroundColor(getResources().getColor(R.color.view_fadewhite));
        Log.d(TAG, "onCreate: started.");

        //Toast.makeText(this, mem_id, Toast.LENGTH_SHORT).show();

        recyclerView = findViewById(R.id.recyclerv_view);
        mImageView = findViewById(R.id.ImageActive);

        next=findViewById(R.id.next);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initiknumbers();
        //executeService();

        //String member_id=member.getString("member_id","R-20null1");
        //memEdit.putString("member_id3",member_id2);
        //memEdit.commit();
        @SuppressLint("StaticFieldLeak")
        RoomAsynctask.getMemberFieldSize x = new RoomAsynctask.getMemberFieldSize(TGhome.this) {

        };

        try {

            memberListFM = x.execute().get();

            //memberList3 = new ArrayList<>();
            Map<String,String > map = null;
//            Log.d("gotherrr",memberListFM.toString());
//            for (fields X: memberListFM){
//                double field_size=Double.valueOf(X.getFieldSize());
//                fieldSizeSum+=field_size;
//            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadRecyclerView();

    }


    public void loadRecyclerView(){
        memberList2 = new ArrayList<>();

        //memberList2 = databaseOpenHelper.load_ik(staff_id,lga);
        @SuppressLint("StaticFieldLeak")
        RoomAsynctask.getMembers x = new RoomAsynctask.getMembers(TGhome.this) {

        };

        try {

            memberList2 = x.execute().get();
            //Log.d("membersss",memberList2.toString());

            memberList3 = new ArrayList<>();
            memberList4 = new ArrayList<>();

            Map<String,String > completeMap= null;
            Map<String,String > incompleteMap= null;
                try {
                    for (MembersTable X : memberList2) {

                        String loanFieldSize = X.getLoan_field_size();
                        double fieldSizeFraction = Double.valueOf(fieldSizeSum) / Double.valueOf(loanFieldSize);
                        if (fieldSizeFraction < 1) {
                            incompleteMap = new HashMap<>();
                            incompleteMap.put("first_name", X.getFirst_name());
                            incompleteMap.put("last_name", X.getLast_name());
                            incompleteMap.put("ik_number", X.getIk_number());
                            incompleteMap.put("ward", X.getWard_id());
                            incompleteMap.put("member_id", String.valueOf(X.getUnique_member_id()));

                            incompleteMap.put("field_size", String.valueOf(fieldSizeSum + "/" + loanFieldSize));
                            incompleteMap.put("phone_number", X.getPhone_number());
                            memberList3.add(incompleteMap);
                        } else {
                            completeMap = new HashMap<>();

                            completeMap.put("first_name", X.getFirst_name());
                            completeMap.put("last_name", X.getLast_name());
                            completeMap.put("ik_number", X.getIk_number());
                            completeMap.put("ward", X.getWard_id());
                            completeMap.put("member_id", String.valueOf(X.getMember_id()));

                            completeMap.put("field_size", String.valueOf(fieldSizeSum + "/" + loanFieldSize));
                            completeMap.put("phone_number", X.getPhone_number());
                            memberList4.add(completeMap);

                        }


                    }
                }
         catch (Exception e) {
            e.printStackTrace();
        }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (recyclerflag==0){

            recyclerController(memberList3);
        }else{
            recyclerController(memberList4);

        }

        Log.d("--HELLO--1",memberList3+"");


    }

    private void recyclerController( ArrayList<Map<String,String>> wordList){
//        memberList1.clear();
        JSONArray jsonArray = new JSONArray(wordList);
        JSONObject jsonObject = null;
        for(int i = 0; i<jsonArray.length();i++) {
            try {
                jsonObject = jsonArray.getJSONObject(i);
                memberList1.add(new modelmemberinfo(
                        jsonObject.getString("first_name"),

                        jsonObject.getString("last_name"),
                        jsonObject.getString("ik_number"),
                        jsonObject.getString("ward"),
                        jsonObject.getString("member_id"),
                        jsonObject.getString("field_size"),
                        jsonObject.getString("phone_number")

                ));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        tghomerecycler tfmAdapter = new tghomerecycler(memberList1,this);
        tfmAdapter.notifyDataSetChanged();
        tfmHomePresenter.textWatcher(search_edit_text, tfmAdapter);

        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
    }


    public void onDestroy() {
        super.onDestroy();
        //executeService();
    }

//    public void executeService(){
//        componentName = new ComponentName(this, BackgroundSync.class);
//        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, componentName);
//        builder.setPeriodic(1000);
//        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
//        builder.setPersisted(true);
//        jobInfo = builder.build();
//        jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//        jobScheduler.schedule(jobInfo);
//
//
//    }


    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(activefield.this, TGHome.class));
        finish();

    }

    public void incomplete(View v) {
        // starting background task to update product
        incomplete.setBackgroundColor(getResources().getColor(R.color.view_fadewhite));
        complete.setBackgroundColor(getResources().getColor(R.color.white));
        tghomerecycler tfmAdapter = new tghomerecycler(memberList1,this);
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
        memberList1.clear(); // clear list
        tfmAdapter.notifyDataSetChanged();

        recyclerflag=0;
        loadRecyclerView();
    }
    public void complete(View v) {
        recyclerflag=1;
        complete.setBackgroundColor(getResources().getColor(R.color.view_fadewhite));
        incomplete.setBackgroundColor(getResources().getColor(R.color.white));
        tghomerecycler tfmAdapter = new tghomerecycler(memberList1,this);
        recyclerView.setAdapter(tfmAdapter);
        tfmAdapter.notifyDataSetChanged();
        memberList1.clear(); // clear list
        tfmAdapter.notifyDataSetChanged();
        loadRecyclerView();
    }
        public void syncTGHome(View v) {
            downloadTFMData download = new downloadTFMData(this);
            download.syncData();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recreate();
                }
            }, 10000);

        // starting background task to update product
        //finish();
    }
//    public void help_active_fields(View v) {
//        //Takes you to help center's home page.
//        HashMap<String,String> user = sharedPreferenceController.getUserDetails();
//        try {
//            startActivity(new Intent(this, SplashScreen.class)
//                    .putExtra("staff_id", user.get(SharedPreferenceController.KEY_STAFF_ID))
//                    .putExtra("app_language", user.get(SharedPreferenceController.KEY_APP_LANG))
//                    .putExtra("user_location", "Lagos"));
//        } catch (Exception e) {
//            e.printStackTrace();
//            Toast.makeText(this, "Activity not found", Toast.LENGTH_LONG).show();
//        }
//
//    }

    @OnClick(R.id.et_search)
    public void move_to_search(View view){
        tfmHomePresenter.hideFeature(toolbar_linear_layout);
        tfmHomePresenter.showFeature(search_linear_layout);
        search_edit_text.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).showSoftInput(search_edit_text, InputMethodManager.SHOW_IMPLICIT);
    }
    @OnClick(R.id.back_to_toolbar)
    public void move_away_from_search(View view){
        removeSearchTray();
    }
    void removeSearchTray(){
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);
        search_edit_text.setText(null);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        Objects.requireNonNull(imm).hideSoftInputFromWindow(et_search.getWindowToken(), 0);
    }
    @Override
    public void hideView(View view) {
        view.setVisibility(View.GONE);
    }

    @Override
    public void showView(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadPreviousActivity() {
        //startActivity(new Intent(TGhome.this, MainActivity.class));
    }
}
