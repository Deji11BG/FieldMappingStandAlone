package com.example.fieldmapping;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class fielddescription extends AppCompatActivity {
    Spinner guideSpinner,keywordSpinner;
    SharedPreferences member;
    SharedPreferences prefs2;
    SharedPreferences.Editor memEdit;
    String selectedGuide,selectedKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fielddescription);
        guideSpinner=findViewById(R.id.guide);
        keywordSpinner=findViewById(R.id.keyword);

        member = getSharedPreferences("member", MODE_PRIVATE);
        // prefs2 = getSharedPreferences("prefs", MODE_PRIVATE);
        memEdit = member.edit();
        String[] guide = {
                "select Preposition",
                "at",
                "towards",
                "in",
                "on",
                "by",
                "over",
                "before",
                "between",
                "after",
                "under",
                "within",
                "along",
                "across",
                "behind",
                "up",
                "out",
                "around",
                "down",
                "off",
                "above",
                "near",
                "North",
                "sourth",
                "West",
                "East"


        };
        String[] keyword = {
                "select keyword",
                "Rafi",

                "Gida",
                "Hanya",
                "Dutse",
                "Labi",
                "Kwari",
                "Dutse",
                "Rijiya",
                "rigar fulani",
                "Makaranta",
                "Daji",
                "kufai",
                "Mango",
                "Dorawa",
                "Kade",
                "Kuka",
                "Tsamiya",
                "Faru",
                "Dinya",
                "Gada",
                "Barbashi",
                "Dabaro",
                "Kamfa"
        };
        final ArrayAdapter<String> guideAdapter = new ArrayAdapter<String>(fielddescription.this, android.R.layout.simple_spinner_dropdown_item, guide)
        {
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        guideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        guideSpinner.setAdapter(guideAdapter);
        guideSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                selectedGuide=guideSpinner.getSelectedItem().toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final ArrayAdapter<String> keywordAdapter = new ArrayAdapter<String>(fielddescription.this, android.R.layout.simple_spinner_dropdown_item, keyword)
    {
        @Override
        public boolean isEnabled(int position){
            if(position == 0)
            {
                // Disable the first item from Spinner
                // First item will be use for hint
                return false;
            }
            else
            {
                return true;
            }
        }
        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            View view = super.getDropDownView(position, convertView, parent);
            TextView tv = (TextView) view;
            if(position == 0){
                // Set the hint text color gray
                tv.setTextColor(Color.GRAY);
            }
            else {
                tv.setTextColor(Color.BLACK);
            }
            return view;
        }
    };
        guideAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        keywordSpinner.setAdapter(keywordAdapter);
        keywordSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


            selectedKeyword=keywordSpinner.getSelectedItem().toString().trim();

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });
}
    public void nextDesc(View v) {
        // starting background task to update product
        memEdit.putString("description",selectedGuide+" "+selectedKeyword);
        memEdit.commit();
        @SuppressLint("StaticFieldLeak") RoomAsynctask.UploadFields x = new RoomAsynctask.UploadFields(fielddescription.this) {
            protected void onPostExecute(String s) {
                Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
                Log.d("RESULT__NOW", s);
            }
        };
        x.execute();
        memEdit.putString("remap_flag","0");
        memEdit.commit();
        //startActivity(new Intent(fielddescription.this, activefield.class));
        finish();
    }
}
