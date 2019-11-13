package com.example.doreopartners.fieldmappingrevamp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class boundaryChecks extends AppCompatActivity {
    Button yes1,no1,yes2,no2,yes3,no3;
    TextView histCheck1,histCheck2,histCheck3,confText1,confText2,confText3;
    ImageView confImage1,confImage2,confImage3;
    CardView question1,question2,question3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundary_checks);
        yes1=findViewById(R.id.yes1);
        yes2=findViewById(R.id.yes2);
        yes3=findViewById(R.id.yes3);


        no1=findViewById(R.id.no1);
        no2=findViewById(R.id.no2);
        no3=findViewById(R.id.no3);

        histCheck1=findViewById(R.id.histField1);
        histCheck2=findViewById(R.id.histField2);
        histCheck3=findViewById(R.id.histField3);
        confText1=findViewById(R.id.confText1);
        confText2=findViewById(R.id.confText2);
        confText3=findViewById(R.id.confText3);



        confImage1=findViewById(R.id.imageConf1);
        confImage2=findViewById(R.id.imageConf2);
        confImage3=findViewById(R.id.imageConf3);
        question1=findViewById(R.id.question1);
        question2=findViewById(R.id.question2);
        question3=findViewById(R.id.question3);
    }
    public void yes1(View v) {
        // starting background task to update product
        //Intent fp=new Intent(getApplicationContext(),startmapping.class);
        histCheck1.setVisibility(View.GONE);
        yes1.setVisibility(View.GONE);
        no1.setVisibility(View.GONE);
        question1.setVisibility(View.GONE);
        confImage1.setVisibility(View.VISIBLE);
        confText1.setVisibility(View.VISIBLE);
        histCheck2.setVisibility(View.VISIBLE);
        yes2.setVisibility(View.VISIBLE);
        no2.setVisibility(View.VISIBLE);
        question2.setVisibility(View.VISIBLE);


        //Intent fp=new Intent(getApplicationContext(),boundaryChecks.class);
        //startActivity(fp);
    }
    public void no1(View v) {
        Toast.makeText(this,"Please, go to the field",Toast.LENGTH_SHORT).show();
    }
    public void yes2(View v) {
        // starting background task to update product
        //Intent fp=new Intent(getApplicationContext(),startmapping.class);
        histCheck2.setVisibility(View.GONE);
        yes2.setVisibility(View.GONE);
        no2.setVisibility(View.GONE);
        question2.setVisibility(View.GONE);
        confImage2.setVisibility(View.VISIBLE);
        confText2.setVisibility(View.VISIBLE);
        histCheck3.setVisibility(View.VISIBLE);
        yes3.setVisibility(View.VISIBLE);
        no3.setVisibility(View.VISIBLE);
        question3.setVisibility(View.VISIBLE);

        //Intent fp=new Intent(getApplicationContext(),boundaryChecks.class);
        //startActivity(fp);
    }
    public void no2(View v) {
        Toast.makeText(this,"Please, go to the field",Toast.LENGTH_SHORT).show();
    }
    public void yes3(View v) {
        // starting background task to update product
        //Intent fp=new Intent(getApplicationContext(),startmapping.class);
        histCheck3.setVisibility(View.GONE);
        yes3.setVisibility(View.GONE);
        no3.setVisibility(View.GONE);
        question3.setVisibility(View.GONE);
        confImage3.setVisibility(View.VISIBLE);
        confText3.setVisibility(View.VISIBLE);

        Intent fp=new Intent(getApplicationContext(),deviceSetup.class);
        startActivity(fp);
    }
    public void no3(View v) {
        Toast.makeText(this,"Please, go to the field",Toast.LENGTH_SHORT).show();
    }

}

