package com.example.doreopartners.fieldmappingrevamp2;

//RECYCLER VIEW ADAPTER FOR DISPLAYING IKNUMBERS BASED ON STAFF LOGIN DETIALS
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class inactivefieldrecycler extends RecyclerView.Adapter<inactivefieldrecycler.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor memEdit;
    modelmappedfield model;

    private List<modelmappedfield> member_list;
    public inactivefieldrecycler(ArrayList<modelmappedfield> memberList,Context context) {

        this.member_list=memberList;
        mContext=context;
    }


    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.activefieldrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getField_id());
        holder.desciption.setText(model.getdescription());
        holder.field_size.setText(model.getField_size());
        holder.date.setText(model.getdate());
    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imageName;
        TextView desciption;
        //TextView lastname;
        TextView field_id;
        TextView field_size;

        TextView date;


        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.field_id);
            desciption=itemView.findViewById(R.id.description);
            field_size=itemView.findViewById(R.id.field_size);
            date=itemView.findViewById(R.id.date);
            parentLayout=itemView.findViewById(R.id.parent_layout);

            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView ikTextView = view.findViewById(R.id.field_id);

                    final String field_id = ikTextView.getText().toString();

                    final String status="active";
                    final String sync_status="0";

                    new AlertDialog.Builder(mContext)
                            .setTitle("Activate Field")
                            .setMessage("Do you want to make this field active?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //TODO 2. This is where I send the data to your app, check the response function for the keys
                                    Intent intent= new Intent(mContext,mappedfieldik.class);
                                    intent.putExtra("field_id",field_id);
                                    DbHelper databaseHelper= new DbHelper(mContext);
                                    databaseHelper.updatefieldStatus(field_id,status);
                                    databaseHelper.updateSyncStatus(field_id,sync_status);
                                    mContext.startActivity(intent);
                                }
                            })

                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            })
                            .show();
                    DbHelper db = new DbHelper(mContext);


                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }



}

