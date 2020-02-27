package com.example.fieldmapping;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.room.Room;

import static android.content.Context.MODE_PRIVATE;

public class RoomAsynctask {


    public static class UploadFields extends AsyncTask<Void, Void, String> {
//I UPLOAD DETAILS OF TRACKED FIELDS ONLINE

        NetworkInfo net;
        String field;
        @SuppressLint("StaticFieldLeak")
        Context context;
        ArrayList wordlist;
        String date;
        public static final String TAG = "AsyncTask2";
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;

        public UploadFields (Context mctx){
            this.context = mctx;

        }

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "onPreRequest");
        }

        @Override
        protected String doInBackground(Void... voids) {
                Log.d("asyncc","here");
                wordlist = new ArrayList<>();
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);
                    ArrayList<HashMap<String, String>> wordList = new ArrayList<>();
                    HashMap<String, String> map = new HashMap<>();
                    member = context.getSharedPreferences("member", MODE_PRIVATE);
                    prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);
                    memEdit = member.edit();
                    String firstname = member.getString("first_name", "Koye");
                    String lastname = member.getString("last_name", "Sodipo");
                    String cropType = member.getString("crop_type", "Maize");
                    String ik_number = member.getString("ik_number", "IK000123");
                    String z = member.getString("latlongs", "NULL,NULL");
                    String staff_id = member.getString("staff_id", "IK000123");
                    String member_id = member.getString("member_id", "10001");
                    String middle = member.getString("middle", "deji");
                    String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                    String minlat = member.getString("minlat", "notyet");
                    String maxlat = member.getString("maxlat", "notyet");
                    String minlng = member.getString("minlng", "notyet");
                    String maxlng = member.getString("maxlng", "notyet");
                    String TFMuniqueid=member.getString("TFMuniqueid", "notyet");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                    Date date = new Date();
                    mydate  = dateFormat.format(date);
                    String ID = staff_id + "-" + mydate + "-" + "F";
                    map.put("first_name", firstname);
                    map.put("last_name", lastname);
                    map.put("crop_type", cropType);
                    map.put("ik_number", ik_number);
                    field = member.getString("fieldsize", "1");
                    map.put("field_size", String.valueOf(field));
                    map.put("staff_id", staff_id);
                    map.put("member_id", member_id);
                    map.put("middle", middle);
                    map.put("minlat", minlat);
                    map.put("maxlat", maxlat);
                    map.put("minlng", minlng);
                    map.put("maxlng", maxlng);
                    map.put("TFMuniqueid",TFMuniqueid);
                    map.put("unique_id", ID);
                    map.put("timestamp", member.getString("timestamp", mydate));
                    map.put("q1", prefs2.getString("ans1", "no"));
                    map.put("q2", prefs2.getString("ans2", "no"));
                    map.put("q3", prefs2.getString("ans3", "no"));
                    map.put("q4", prefs2.getString("ans4", "no"));
                    map.put("q5", prefs2.getString("ans5", "no"));
                    SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
                    Date date3 = new Date();
                    String mydate3  = dateFormat3.format(date3);
                    map.put("date",member.getString("today",mydate3));
                    map.put("field_status",member.getString("field_status","active"));
                    map.put("upload_status","0");
                    map.put("latlongs", z);
                    map.put("q6", prefs2.getString("ans6", "no"));
                    map.put("q7", prefs2.getString("ans7", "no"));
                    map.put("q8", prefs2.getString("ans8", "no"));
                    map.put("q9", prefs2.getString("ans9", "no"));
                    map.put("q10", prefs2.getString("ans10", "no"));
                    map.put("q11", prefs2.getString("ans11", "no"));
                    map.put("q12", prefs2.getString("ans1a", "0"));
                    map.put("q13", prefs2.getString("ans2a", "0"));
                    map.put("q14", prefs2.getString("ans3a", "0"));
                    map.put("description", prefs2.getString("description", "good"));
                    map.put("village", member.getString("village", "village"));
                    map.put("q15ad", prefs2.getString("ans1d", "Maize"));
                    map.put("q15bd", prefs2.getString("ans2d", "Maize"));
                    map.put("q15cd", prefs2.getString("ans3d", "Maize"));
                    map.put("version", BuildConfig.VERSION_NAME);
                    map.put("remap_flag",prefs2.getString("remap_flag", "0"));

            try {
                        String action = prefs2.getString("ans4d", "Maize");
                        map.put("q15dd", action);
                        map.put("q15ar", prefs2.getString("ans1r", "Maize"));
                        map.put("q15br", prefs2.getString("ans2r", "Maize"));
                        map.put("q15cr", prefs2.getString("ans3r", "Maize"));
                        map.put("q15dr", prefs2.getString("ans4r", "Maize"));
                    }
                    catch(JsonIOException e)
                    {
                        Log.d("maperror",e.toString());
                       // Toast.makeText(this, "Please, go back and answer the questions ", Toast.LENGTH_SHORT).show();
                    }
                    wordList.add(map);
                    Gson gson = new GsonBuilder().create();
                    Log.d("Deji2", gson.toJson(wordList));
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray(gson.toJson(wordList));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    fd.saverecordsRoom(jsonArray);
                    Log.d("fieldmappresult", gson.toJson(jsonArray));
                    return gson.toJson(wordList);
        }
    }
    public static class getActiveFields extends AsyncTask<String, Void, List<fields>>{
        Context context;
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;



        public getActiveFields(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);
            String member_id=member.getString("member_id","R-20null1");
            //Log.d("member_idddd",member_id);
            String active="active";
            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().loadActiveFields(member_id,active);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class getMembers extends AsyncTask<Void, Void, List<MembersTable>>{
        Context context;
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;



        public getMembers(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<MembersTable> doInBackground(Void... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);

            try{

                Room.databaseBuilder(context,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();

                TFMDatabase td= TFMDatabase.getInstance(context);

                return td.getMembersTable().getmembers();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class getphonenumber extends AsyncTask<String, Void, String>{
        Context context;
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;



        public getphonenumber(Context mCtx, String member_id) {
            this.context = mCtx;

        }

        @Override
        protected String doInBackground(String... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);

            try{

                Room.databaseBuilder(context,
                        TFMDatabase.class, TFMDBContractClass.TFM_DATABASE_NAME).build();

                TFMDatabase td= TFMDatabase.getInstance(context);

                return td.getMembersTable().getphonenumber(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }


    public static class updateFieldStatus extends AsyncTask<String, Void, Void>{
        Context context;

        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;


        public updateFieldStatus(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Void doInBackground(String... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);

            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);
                final String status="inactive";
                final String sync_status="0";
                String field_id= member.getString("field_id_active", "Sodipo");


                 fd.fieldsdao().updateFieldStatusRoom(field_id,status);
                fd.fieldsdao().updateFieldStatusRoom(field_id,sync_status);

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class getMemberFieldSize extends AsyncTask<String, Void, List<fields>>{
        Context context;




        public getMemberFieldSize(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().getmemberfieldsize("100001");
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class loadMember extends AsyncTask<String, Void, List<fields>>{
        Context context;




        public loadMember(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().loadMemberRoom(params[0],params[1]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public static class getCountofMemberFields extends AsyncTask<String, Void, Integer>{
        Context context;




        public getCountofMemberFields(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Integer doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().memberfields(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class getMappedToday extends AsyncTask<String, Void, Integer>{
        Context context;




        public getMappedToday(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Integer doInBackground(String... params) {


            try{
                SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date();
                String mydate  = dateFormat3.format(date);
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);
                

                return fd.fieldsdao().mappedTodayRoom(mydate);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class getMappedTotal extends AsyncTask<Void, Void, Integer>{
        Context context;




        public getMappedTotal(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Integer doInBackground(Void... voids) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().mappedTotalRoom();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class getSyncedCount extends AsyncTask<String, Void, Integer>{
        Context context;




        public getSyncedCount(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Integer doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);
                String status="1";
                return fd.fieldsdao().syncedCountRoom(status);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }


    }
    public static class getVillage extends AsyncTask<String, Void, List<fields>>{
        Context context;




        public getVillage(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().getVillageRoom(params[0]);
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class getIk extends AsyncTask<String, Void, List<fields>>{
        Context context;

        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;


        public getIk(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);

            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                return fd.fieldsdao().loadIKRoom(member.getString("staff_id","0001"),member.getString("village1","0001"));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }

    @SuppressLint("StaticFieldLeak")
    public static class getUnsyncedCount extends AsyncTask<String, Void, Integer>{
        Context context;




        public getUnsyncedCount(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected Integer doInBackground(String... params) {


            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);

                //TODO---> Deji needs to check this out
                /*return fd.fieldsdao().unsyncedCount(params[0]);*/
                return fd.fieldsdao().unsyncedCount();

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
    public static class checkoverlap extends AsyncTask<String, Void, List<fields>>{
        Context context;
        TFMDatabase tfmDatabase;
        SharedPreferences member;
        SharedPreferences prefs2;
        SharedPreferences.Editor memEdit;


        public checkoverlap(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected List<fields> doInBackground(String... params) {
            member = context.getSharedPreferences("member", MODE_PRIVATE);
            prefs2 = context.getSharedPreferences("prefs", MODE_PRIVATE);

            try{
                String memberID,fieldstatus;
                //String<fields> areasSync = lists[0];
                // Room.databaseBuilder(context,
                //TFMDatabase.class, "DB").build();
                //tfmDatabase = TFMDatabase.getInstance(context);
                Room.databaseBuilder(context,
                        finalDatabase.class, "DB").build();
                finalDatabase fd= finalDatabase.getInstance(context);
                return fd.fieldsdao().checkoverlap(member.getString("middle","middle"));
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

    }
}

