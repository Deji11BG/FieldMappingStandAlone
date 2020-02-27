package com.example.fieldmapping;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Arrays;

import androidx.recyclerview.widget.RecyclerView;
//RECYCLER VIEW ADAPTER FOR THE FIRST SET OF QUESTIONS
public class Set1Adapter extends RecyclerView.Adapter<Set1Adapter.VH> {
    final ArrayList<Set1> trans; //holds the list of all transactions in database as transaction objects, declared final because it is used in an inner class
    private final OnItemClickListener listener; //this is the listener that is set on view click, declared final because  "                  "                  "
    Context context;    //this is used to store the context where the adapter will be used.
    SharedPreferences prefs2;
    private SharedPreferences.Editor mEditor;
    static int IS_FILLED0 = 0;
    static int IS_FILLED1 = 0;
    static int IS_FILLED2 = 0;
    static int IS_FILLED3 = 0;
    static int IS_FILLED4 = 0;
    static int IS_FILLED5 = 0;
    static int IS_FILLED6 = 0;
    static int IS_FILLED7 = 0;
    static int IS_FILLED8 = 0;
    static int IS_FILLED9 = 0;
    static int IS_FILLED10 = 0;
    Integer[]check={0,0,0,0,0,0,0,0,0,0,0};
    //Constructor initializes all of the above, notice it is the same as the normal constructor except now listener is added
    public Set1Adapter(Context context, ArrayList<Set1> trans, OnItemClickListener listener) {
        this.trans = trans;
        this.context = context;
        this.listener = listener;
        this.prefs2 = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    //Gets the view holder i.e the layout to hold each transaction view
    @Override
    public Set1Adapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.set1row, parent, false);
        return new VH(v);       //returns the view holder class below with the textviews assigned to ids
    }

    @Override
    public void onBindViewHolder(final Set1Adapter.VH holder, final int position) {
        final Set1 trans = this.trans.get(position);   //gets current transaction object from array
        Log.d("array_logs1", Arrays.toString(check));
        holder.tvQNo.setText(trans.getqNo() + "");
        holder.tvQ.setText(trans.getQ());
        holder.tvMem.setText(trans.getMem());

        holder.rdbYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheck) {
                switch (position) {
                    case 0:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[0]==1){
                                IS_FILLED0=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 1:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[1]==1){
                                IS_FILLED1=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 2:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[2]==1){
                                IS_FILLED2=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 3:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[3]==1){
                                IS_FILLED3=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 4:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[4]==1){
                                IS_FILLED4=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 5:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[5]==1){
                                IS_FILLED5=1;
                            }
                            holder.rdbYes.setChecked(true);
                            holder.rdbNo.setChecked(false);
                        }
                    case 6:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[6]==1){
                                IS_FILLED6=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 7:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[7]==1){
                                IS_FILLED7=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 8:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[8]==1){
                                IS_FILLED8=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 9:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[9]==1){
                                IS_FILLED9=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                    case 10:
                        if (holder.rdbYes.isChecked()) {
                            check[position] = 1;
                            prefs2.edit().putString("ans" + (position + 1), "yes").commit();
                            if (check[10]==1){
                                IS_FILLED10=1;
                            }
                            holder.rdbNo.setChecked(false);
                        }
                }
            }
        });
        holder.rdbNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isCheck) {
                switch (position) {
                    case 0:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[0]==2){
                                IS_FILLED0=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 1:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[1]==2){
                                IS_FILLED1=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 2:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[2]==2){
                                IS_FILLED2=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 3:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[3]==2){
                                IS_FILLED3=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 4:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[4]==2){
                                IS_FILLED4=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 5:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[5]==2){
                                IS_FILLED5=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 6:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[6]==2){
                                IS_FILLED6=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 7:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[7]==2){
                                IS_FILLED7=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 8:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[8]==2){
                                IS_FILLED8=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 9:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[9]==2){
                                IS_FILLED9=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                    case 10:
                        if (holder.rdbNo.isChecked()) {
                            check[position] = 2;
                            prefs2.edit().putString("ans" + (position + 1), "no").commit();
                            if (check[10]==2){
                                IS_FILLED10=1;
                            }
                            holder.rdbYes.setChecked(false);
                        }
                }
                Log.d("array_logs", Arrays.toString(check));
            }
        });
        if (check[position] == 1){
            holder.rdbYes.setChecked(true);
            holder.rdbNo.setChecked(false);
        }else if (check[position] == 2){
            holder.rdbNo.setChecked(true);
            holder.rdbYes.setChecked(false);
        }else if (check[position]==0){
            holder.rdbNo.setChecked(false);
            holder.rdbYes.setChecked(false);
        }
    }
    public boolean isFilledset1() {
        if ((IS_FILLED0 == 1)&&(IS_FILLED1 == 1)&&(IS_FILLED2 == 1)&&(IS_FILLED3 == 1)&&(IS_FILLED4 == 1)&&(IS_FILLED5 == 1)&&(IS_FILLED6 == 1)
                &&(IS_FILLED7 == 1)&&(IS_FILLED8== 1)&&(IS_FILLED9 == 1)&&(IS_FILLED10 == 1)) {
            Log.d("isfilled",String.valueOf(IS_FILLED5));
            return true;
        }
        return false;
    }
    @Override
    public int getItemCount() {
        return trans.size();
    }

    public interface OnItemClickListener {      //this is the interface that enables the onclick to work
        void onClick(Set1 trans);
    }

    public class VH extends RecyclerView.ViewHolder {
        TextView tvQNo;
        TextView tvQ;
        TextView tvMem;
        RadioGroup rdg;
        RadioButton rdbYes;
        RadioButton rdbNo;

        public VH(View v) {
            super(v);
            tvQNo = v.findViewById(R.id.tvQNo);
            tvQ = v.findViewById(R.id.tvQ);
            tvMem = v.findViewById(R.id.tvMem);
            rdbYes = v.findViewById(R.id.rdbYes);
            rdbNo = v.findViewById(R.id.rdbNo);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(trans.get(getLayoutPosition()));
                }
            });

        }
    }
}