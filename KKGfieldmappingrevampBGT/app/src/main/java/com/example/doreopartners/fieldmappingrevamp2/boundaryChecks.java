package com.example.doreopartners.fieldmappingrevamp2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class boundaryChecks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boundary_checks);
    }
    public void boundaryCheckButton(View v) {
        new AlertDialog.Builder(this)
                .setTitle("Start mapping session")
                .setMessage("Are you at the boundary of the field?")
                .setPositiveButton("Yes, start mapping", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(boundaryChecks.this, MappingActivity.class));
                    }
                })

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(boundaryChecks.this, boundaryChecks.class));
                    }
                })
                .show();
    }
}

