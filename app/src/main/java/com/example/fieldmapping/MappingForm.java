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
    Set1Adapter adapter1;
    Set2Adapter adapter2;
    Set3Adapter adapter3;
    ArrayList<Set1> tsns;
    ArrayList<Set1> tsns2;
    ArrayList<Set3> tsns3;
    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapping_form);

        txtName = findViewById(R.id.txtNameMappingForm);
        member = getSharedPreferences("member", MODE_PRIVATE);
        prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        String first_name=member.getString("first_name","koye");
        String last_name=member.getString("last_name","sodipo");
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
        tsns.add(new Set1(getString(R.string.num9), getString(R.string.q9), "Farmer", prefs2.getString("ans9", "")));
        tsns.add(new Set1(getString(R.string.num10), getString(R.string.q10), "Farmer", prefs2.getString("ans10", "")));
        tsns.add(new Set1(getString(R.string.num11), getString(R.string.q11), "Farmer", prefs2.getString("ans11", "")));
        tsns2.add(new Set1(getString(R.string.num12), getString(R.string.q12), "Farmer", prefs2.getString("ans1a", "")));
        tsns2.add(new Set1(getString(R.string.num13), getString(R.string.q13), "MIK", prefs2.getString("ans2a", "")));
        tsns2.add(new Set1(getString(R.string.num14), getString(R.string.q14), "MIK", prefs2.getString("ans3a", "")));
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
                                                Intent intent = new Intent(MappingForm.this, mappedFieldInformation.class);
                                                try {
                                                    startActivity(intent);
                                                    finish();
                                                } catch (JsonIOException e) {

                                                }

                                                try {
                                                } catch (JsonIOException e) {
                                                    //Toast.makeText(this,"please go back and anser the questions correclty",Toast.LENGTH_SHORT).show();
                                                }

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
}
