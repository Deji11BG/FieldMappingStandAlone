package com.example.fieldmapping;
//RECYCLERVIEW FOR THE THIRD SET OF QUESTIONS

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class Set3Adapter extends RecyclerView.Adapter<Set3Adapter.VH> {
    final ArrayList<Set3> trans;
    private final OnItemClickListener listener; //this is the listener that is set on view click, declared final because  "                  "                  "
    Context context;
    SharedPreferences prefs2;
    SharedPreferences.Editor prefsEdit;
    int oner,twor, threer,fourr;
    int oned,twod,threed,fourd;

    //Constructor initializes all of the above, notice it is the same as the normal constructor except now listener is added
    public Set3Adapter(Context context, ArrayList<Set3> trans, OnItemClickListener listener) {
        this.trans = trans;
        this.context = context;
        this.listener = listener;
        this.prefs2 = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
    }

    //Gets the view holder i.e the layout to hold each transaction view
    @Override
    public Set3Adapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.set3row, parent, false);
        return new VH(v);       //returns the view holder class below with the textviews assigned to ids
    }

    @Override
    public void onBindViewHolder(final Set3Adapter.VH holder, final int position) {
        final Set3 trans = this.trans.get(position);   //gets current transaction object from array
        holder.tvQNo.setText(trans.getqNo() + "");
        holder.tvQ.setText(trans.getQ());
        holder.tvMem.setText(trans.getMem());
        String[] crops = {"--Select--","Maize", "Rice", "Soybean", "Groundnut", "Cowpea", "Sorghum","other"}; //the space in front is to make the spinner display a blank in default
         ArrayAdapter<String> adapter = new ArrayAdapter<String> (context,android.R.layout.simple_list_item_1,crops)
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
        holder.spDry.setAdapter(adapter);
        holder.spRainy.setAdapter(adapter);
        holder.spDry.setSelection(trans.getDry());
        holder.spRainy.setSelection(trans.getRainy());

        holder.spDry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                final String selected = holder.spDry.getSelectedItem().toString();
                trans.setDry(p);

                if (selected == "other") {
                    holder.other.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        if (s.length() > 0) {
                            switch (position){
                                case 0:
                                    oned=1;
                                case 1:
                                    twod=1;
                                case 2:
                                    threed=1;
                                case 3:
                                    fourd=1;
                            }
                            prefs2.edit().putString("ans" + String.valueOf(position + 1) + "d", s.toString()).commit();
                            prefs2.getString("ans1d", String.valueOf(s));
                            prefs2.getString("ans2d", String.valueOf(s));
                            prefs2.getString("ans3d", String.valueOf(s));
                            prefs2.getString("ans4d", String.valueOf(s));
                        } else {
                        }
                    }
                });
                    holder.other.setVisibility(View.VISIBLE);
                } else {
                    holder.other.setVisibility(View.GONE);
                    switch (position) {
                        case 0:
                            if (selected=="--Select--") {
                                oned=0;
                            } else {
                                oned = 1;
                            }
                            break;
                        case 1:
                            if (selected=="--Select--") {
                                twod = 0;

                            } else {
                                twod = 1;
                            }
                            break;
                        case 2:
                            if (selected=="--Select--") {
                                threed = 0;

                            } else {
                                threed = 1;
                            }
                            break;
                        case 3:
                            if (selected=="--Select--") {
                                fourd = 0;

                            } else {
                                fourd = 1;
                            }
                            break;
                    }
                    prefs2.edit().putString(("ans" + String.valueOf(position + 1) + "d").toString(), selected.toString()).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        holder.spRainy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                trans.setRainy(p);

                String selected2 = holder.spRainy.getSelectedItem().toString();
                Log.d("selectedttt",selected2);
                if (selected2 == "other") {

                    holder.other2.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                            if (s.length() > 0) {
                                switch (position){
                                    case 0:
                                        oner=1;
                                    case 1:
                                        twor=1;
                                    case 2:
                                        threer=1;
                                    case 3:
                                        fourr=1;

                                }
                                prefs2.edit().putString("ans" + (position + 1) + "r", s.toString()).commit();


                            } else {
                            }

                        }
                    });
                    holder.other2.setVisibility(View.VISIBLE);
                } else
                {
                    holder.other2.setVisibility(View.GONE);
                    switch (position) {
                        case 0:
                            if (selected2=="--Select--") {

                                oner=0;
                            } else {
                                oner = 1;
                            }
                            break;
                        case 1:
                            if (selected2=="--Select--") {
                                twor = 0;

                            } else {
                                twor = 1;
                            }
                            break;
                        case 2:
                            if (selected2=="--Select--") {
                                threer = 0;

                            } else {
                                threer = 1;
                            }
                            break;
                        case 3:
                            if (selected2=="--Select--") {
                                fourr = 0;

                            } else {
                                fourr = 1;
                            }
                            break;

                    }
                    prefs2.edit().putString("ans" + (position + 1) + "r", selected2).commit();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return trans.size();
    }

    public interface OnItemClickListener {      //this is the interface that enables the onclick to work
        void onClick(Set3 trans);

    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvQNo;
        TextView tvQ;
        TextView tvMem;
        EditText other;
        EditText other2;
        Spinner spDry;
        Spinner spRainy;

        public VH(View v) {
            super(v);
            tvQNo = v.findViewById(R.id.tvQNo);
            tvQ = v.findViewById(R.id.tvQ);
            tvMem = v.findViewById(R.id.tvMem);
            spDry = v.findViewById(R.id.spDry);
            spRainy = v.findViewById(R.id.spRainy);
            other=v.findViewById(R.id.other);
            other2=v.findViewById(R.id.otherrainy);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(trans.get(getLayoutPosition()));
                }
            });

        }
    }
    public boolean entrycheck(){
        if (oner==0 || twor==0 || threer==0 || fourr==0 || oned==0 || twod==0 || threed==0|| fourd==0)
            return false;
        else
            return true;

    }
}