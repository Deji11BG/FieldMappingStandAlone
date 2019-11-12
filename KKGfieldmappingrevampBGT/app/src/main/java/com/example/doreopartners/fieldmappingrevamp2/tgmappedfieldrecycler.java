package com.example.doreopartners.fieldmappingrevamp2;

//RECYCLER VIEW ADAPTER FOR DISPLAYING IKNUMBERS BASED ON STAFF LOGIN DETIALS
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class tgmappedfieldrecycler extends RecyclerView.Adapter<tgmappedfieldrecycler.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor memEdit;
    //private List<modelclass3>number_list;
    modelclassikmapped model;
    //modelclass3 model3;
    private List<modelclassikmapped> member_list;


    public tgmappedfieldrecycler(ArrayList<modelclassikmapped> memberList,Context context) {

        this.member_list=memberList;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.tgmappedfieldrecycler,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder( ViewHolder holder, final int position) {

        model = member_list.get(position);

        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(model.getik_number());

    }


    @Override
    public int getItemCount() {
        return member_list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView imageName;
        TextView firstname;
        //TextView lastname;
        TextView leadername;
        TextView numbertext;
        TextView number;


        RelativeLayout parentLayout;
        public ViewHolder (View itemView)
        {
            super(itemView);
            imageName=itemView.findViewById(R.id.iknumber);

            parentLayout=itemView.findViewById(R.id.parent_layout);


            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView ikTextView = view.findViewById(R.id.iknumber);

                    final String iknumber = ikTextView.getText().toString();


                    //Toast.makeText(mContext,mImageNames.get(position), Toast.LENGTH_SHORT).show();
                    Intent intent= new Intent(mContext,membermappedfield.class);
                    intent.putExtra("ik_number1",iknumber);
                    Toast.makeText(mContext,iknumber, Toast.LENGTH_SHORT).show();
                    OnlineDBHelper db = new OnlineDBHelper(mContext);


                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

}

