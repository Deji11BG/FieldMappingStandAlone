package com.example.fieldmapping;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static android.Manifest.permission.CALL_PHONE;
import static android.content.Context.MODE_PRIVATE;

import static android.Manifest.permission.CALL_PHONE;
import static androidx.core.app.ActivityCompat.requestPermissions;

public class tghomerecycler extends RecyclerView.Adapter<tghomerecycler.ViewHolder> implements Filterable {
    private static final String TAG = "RecyclerViewAdapter";
    private Context mContext;
    modelmemberinfo model;
    private List<modelmemberinfo> member_list;
    private List<modelmemberinfo> mFilteredList;
    private List<modelmemberinfo> examplelistfull;
    // itemsCopy.addAll(items);
    private TFMHomePresenter tfmHomePresenter;

    public tghomerecycler(ArrayList<modelmemberinfo> memberList, Context context) {
        this.member_list = memberList;
        this.mFilteredList=memberList;
        examplelistfull = new ArrayList<>(memberList);
        mContext = context;
        //setHasStableIds(true);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tghomerecycler, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        tfmHomePresenter = new TFMHomePresenter();
        if (mFilteredList != null && position < mFilteredList.size()) {
            Log.d(TAG, "onBindViewHolder: called.");
            model = mFilteredList.get(position);
            holder.setLeader_image(holder.image, model.getMember_id());
            String full_name = holder.nameFormatter(model.getFirst_name(),model.getLast_name());
            holder.setTextController(holder.name, full_name);
            holder.ik_number.setText(model.getIk_number());
            holder.location.setText(model.getWard());
            holder.member_id.setText(model.getMember_id());
            holder.field_size.setText(model.getField_size());
            holder.phone_num.setText(model.getPhone_number());
            holder.callImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final String phone_numtxt = member_list.get(position).getPhone_number();
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse("tel:" + phone_numtxt));
                    if (ContextCompat.checkSelfPermission(mContext, CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        mContext.startActivity(i);
                    } else {
                        requestPermissions((Activity) mContext, new String[]{CALL_PHONE}, 1);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (mFilteredList != null){
            return mFilteredList.size();
        }else{
            return 0;
        }
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                filterResults.values = tfmHomePresenter.getSearchParameters(constraint,member_list);
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mFilteredList = (List<modelmemberinfo>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView ik_number;
        TextView location;
        TextView member_id;
        TextView field_size;
        ImageView image;
        TextView phone_num;
        ImageView callImage;
        LinearLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            ik_number = itemView.findViewById(R.id.ik_number);
            member_id = itemView.findViewById(R.id.member_id);
            location = itemView.findViewById(R.id.Locationtghome);
            field_size=itemView.findViewById(R.id.fieldmapped);
            callImage=itemView.findViewById(R.id.callImage);
            phone_num=itemView.findViewById(R.id.phone_num);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final TextView phone_number = view.findViewById(R.id.phone_num);
                    final String phone_numtxt = phone_number.getText().toString();
                    Log.d("phone_number11",phone_numtxt);

                    new AlertDialog.Builder(mContext)
                            .setTitle("Select Operation")
                            .setPositiveButton("Map Field", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    //TODO 2. This is where I send the data to your app, check the response function for the keys
                                    Intent intent= new Intent(mContext,MainActivity.class);
                                    mContext.startActivity(intent);

                                }
                            })

                            .setNeutralButton("Edit Field Size", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                //luxand verification to edit field_size
                                    //dialog with text to show input for new field size
                                    //update field size in the DB based on unique_id
                                }
                            })
                            .show();

                }
            });
        }

        @Override
        public void onClick(View v) {

        }
        void setTextController(TextView textView, String text) {
            textView.setText(text);
        }
        String nameFormatter(String first_name, String last_name){

            return first_name +" "+last_name;
        }
        void setLeader_image(ImageView iv_picture, String unique_id) {

            File ImgDirectory = new File(Environment.getExternalStorageDirectory().getPath(), "TestPictures");
            String image_name = File.separator + unique_id + ".jpg";
            //String imageFile = (ImgDirectory.getAbsolutePath() + image_name);

            File imgFile = new File(ImgDirectory.getAbsoluteFile(), image_name);

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                iv_picture.setImageBitmap(myBitmap);

            } else {
                iv_picture.setImageResource(R.drawable.bg_logo);
            }

            /*Bitmap mImageBitmap = null;
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(iv_picture.getContext().getContentResolver(), Uri.parse(imageFile));
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_picture.setImageBitmap(mImageBitmap);*/
        }



    }


}

