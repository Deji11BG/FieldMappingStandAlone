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
    private ArrayList<modelmemberinfo> memberList1=new ArrayList<>();
    private ArrayList<modelmemberinfo> number_list=new ArrayList<>();
    private List<MembersTable> memberList2=new ArrayList<>();
    private List<fields> memberListFM;
    private ArrayList<Map<String,String>> memberList3;
    private ArrayList<Map<String,String>> memberList4;
    private ArrayList<Map<String,String>> memberList5;
    TextView staff_id;
    TextView lastSyncTime,version;
    Button next;
    ImageView mImageView;
    double fieldSizeSum=0;
    private RecyclerView recyclerView;
    SharedPreferences member;
    SharedPreferences.Editor memEdit;
    SharedPreferenceController sharedPreferenceController;
    String first_name;
    String last_name;
    Button complete,incomplete;
    Integer recyclerflag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tghome);
        ButterKnife.bind(TGhome.this);
        tfmHomePresenter = new TFMHomePresenter(TGhome.this);
        tfmHomePresenter.showFeature(toolbar_linear_layout);
        tfmHomePresenter.hideFeature(search_linear_layout);
        member = getSharedPreferences("member", MODE_PRIVATE);
        sharedPreferenceController = new SharedPreferenceController(getApplicationContext());
        memEdit = member.edit();
        staff_id=findViewById(R.id.tghomestaffid);
        lastSyncTime=findViewById(R.id.last_sync_date);
        SharedPreferenceController sharedPreferenceController = new SharedPreferenceController(this);
        lastSyncTime.setText(sharedPreferenceController.getDateUpdated());
        // TODO: 24/02/2020 get staff_id from access control 
        version=findViewById(R.id.version);
        incomplete=findViewById(R.id.incomplete);
        complete=findViewById(R.id.complete);
        incomplete.setBackgroundColor(getResources().getColor(R.color.view_fadewhite));
        Log.d(TAG, "onCreate: started.");
        recyclerView = findViewById(R.id.recyclerv_view);
        mImageView = findViewById(R.id.ImageActive);
        next=findViewById(R.id.next);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        @SuppressLint("StaticFieldLeak")
        RoomAsynctask.getMemberFieldSize x = new RoomAsynctask.getMemberFieldSize(TGhome.this) {

        };

        try {

            memberListFM = x.execute().get();
            Map<String,String > map = null;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadRecyclerView();

    }


    public void loadRecyclerView(){
        memberList2 = new ArrayList<>();
        @SuppressLint("StaticFieldLeak")
        RoomAsynctask.getMembers x = new RoomAsynctask.getMembers(TGhome.this) {

        };

        try {

            memberList2 = x.execute().get();
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
    }

    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(activefield.this, TGHome.class));
        finish();

    }

    public void incomplete(View v) {
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
