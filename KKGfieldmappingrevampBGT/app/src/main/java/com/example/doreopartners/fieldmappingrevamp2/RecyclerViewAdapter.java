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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String>mImageNames=new ArrayList<>();
    private String firstname1;
    private String lastname1;
    private String number1;
    private Context mContext;
    SharedPreferences member;
    SharedPreferences prefs;
    SharedPreferences.Editor memEdit;
    modelclass1 model;
    private List<modelclass1> member_list;
    private List<modelclass1> mFilteredList;


    public RecyclerViewAdapter(ArrayList<modelclass1> memberList,Context context) {

        this.member_list=memberList;
        this.mFilteredList=memberList;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        model = mFilteredList.get(position);
        Log.d(TAG, "onBindViewHolder: called.");
        holder.imageName.setText(mFilteredList.get(position).getik_number());
        String first_name = mFilteredList.get(position).getFirst_name();
        String last_name = mFilteredList.get(position).getLast_name();
        String village = mFilteredList.get(position).getVillage();
        holder.firstname.setText(first_name+" "+last_name);
        holder.tv_village.setText("Village: "+village);

    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView imageName;
        TextView firstname;
        TextView leadername;
        TextView number;
        TextView tv_village;
        RelativeLayout parentLayout;

        public ViewHolder (View itemView) {
            super(itemView);
            imageName=itemView.findViewById(R.id.iknumber);
            firstname=itemView.findViewById(R.id.firstname);
            leadername=itemView.findViewById(R.id.leadername);
            parentLayout=itemView.findViewById(R.id.parent_layout);
            tv_village=itemView.findViewById(R.id.tv_village);


            parentLayout.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    final TextView ikTextView = view.findViewById(R.id.iknumber);

                    final String iknumber = ikTextView.getText().toString();

                    Intent intent= new Intent(mContext,fieldselection.class);
                    intent.putExtra("ik_number",iknumber);
                    Toast.makeText(mContext,iknumber, Toast.LENGTH_SHORT).show();

                    //prefsEdit.putString("MIN_LOC_UPDATE_DISTANCE",intent.getStringExtra("min_dist"));
                    intent.putExtra("MIN_WALKING_SPEED", intent.getStringExtra("max_walk_speed"));
                    intent.putExtra("MIN_BIKE_SPEED", intent.getStringExtra("max_bike_speed"));
                    mContext.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence charSequence){

                String charString = charSequence.toString().trim();

                Log.d("CHECK", "charString" + charString);
                if(charString.isEmpty()){

                    mFilteredList = member_list;
                }else{
                    List<modelclass1> filteredList = new ArrayList<>();

                    Log.d("CHECK", "charList" + member_list.toString());

                    for(modelclass1 modelClass : member_list){

                        if(modelClass.getVillage().toLowerCase().contains(charString) ||
                                modelClass.getik_number().toLowerCase().contains(charString)){

                            filteredList.add(modelClass);
                        }
                    }

                    mFilteredList = filteredList;
                    Log.d("CHECK", "filteredList" + mFilteredList.toString());
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults){

                mFilteredList = (List<modelclass1>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}

