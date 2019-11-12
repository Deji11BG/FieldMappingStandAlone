package com.example.doreopartners.fieldmappingrevamp2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//WELL, THIS PARTICULAR ASYNCTASK IS USED FOR SYNCING DOWN TO GET DETAILS OF THE FIELD TO BE TRACKED
public class Asynctask {
    public static class DownloadApplication extends AsyncTask<Void, Void, String> {

        @SuppressLint("StaticFieldLeak")
        Context context;
        StringBuilder result = null;
        SessionManagement sessionManagement;
        OnlineDBHelper onlineDBHelper;
        String urlServer = "https://apps.babbangona.com/tgl_test/tgl_sync/field_mapping";

        public DownloadApplication(Context mCtx) {
            this.context = mCtx;
        }

        @Override
        protected String doInBackground(Void... voids) {
            OnlineDBHelper onlineDBHelper= new OnlineDBHelper(context);

            HttpURLConnection conn;
            URL url = null;
            JSONArray x=null;


            String staff_id="";
            String last_sync_time="";

            try {
                sessionManagement = new SessionManagement(context);
                HashMap<String, String> user = sessionManagement.getUserDetails();
                staff_id = user.get(SessionManagement.KEY_STAFF_ID);
                last_sync_time = user.get(SessionManagement.KEY_LAST_SYNC_TIME);
                Log.d("staffidme",staff_id);
                Log.d("last_sync_me",last_sync_time);
            } catch (Exception e) {
                System.out.println("Exception caught: Session Management didnt work");
            }

            if (staff_id == null) {
                return "Your members are up to date";
            }

            try {
                url = new URL(urlServer + "/fetch_memberTKKG.php?staff_id="+staff_id+"&latest_date="+URLEncoder.encode(last_sync_time));

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "exception 1";
            }

            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(20000);
                conn.setConnectTimeout(30000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                conn.setDoOutput(true);
                String latest_date = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("staff_id", staff_id).appendQueryParameter("latest_date", last_sync_time);
                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                e1.printStackTrace();
                return "Operation failed, kindly check your internet connection";
            }

            String output = "Operation failed, kindly check your internet connection";
            try {
                int response_code = conn.getResponseCode();
                if (response_code == HttpURLConnection.HTTP_OK) {
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    result = new StringBuilder();
                    String line;


                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    Map<String, String> map = null;
                    ArrayList<Map<String, String>> wordList;
                    wordList = new ArrayList();
                    Log.d("deji", result+"");
                    try {
                        JSONArray JA = new JSONArray(result.toString());
                        JSONObject json = null;
                        int j = 0;
                        for (int i = 0; i < JA.length(); i++) {

                            json = JA.getJSONObject(i);
                            map = new HashMap<String, String>();
                            map.put("member_id", json.getString("member_id"));
                            map.put("ik_number", json.getString("ik_number"));
                            map.put("first_name", json.getString("first_name"));
                            map.put("last_name", json.getString("last_name"));
                            map.put("phone_number", json.getString("phone_number"));
                            map.put("crop_type", json.getString("crop_type"));
                            map.put("state", json.getString("state"));
                            map.put("lga", json.getString("lga"));
                            //map.put("district", json.getString("district"));
                            map.put("village", json.getString("village"));
                            map.put("mapping_date", json.getString("mapping_date"));
                            map.put("member_role", json.getString("member_role"));
                            map.put("staff_id", json.getString("staff_id"));
                            map.put("unique_id",json.getString("unique_id"));

                            wordList.add(map);
                            j = 1;

                            if (i == JA.length()-1){
                                String last_sync_time1 = json.getString("last_sync_time");
                                SessionManagement sessionManagement = new SessionManagement(context);
                                sessionManagement.saveLastSyncTime(last_sync_time1);
                                Log.d("DamiLastSync",last_sync_time1);
                            }

                        }
                        if (j == 1) {
                            onlineDBHelper.getResults(wordList);
                            output = "Records downloaded successfully";

                        } else {
                            output = "No record found";
                        }

                    } catch (Exception e) {
                        Log.e("Fail 3", e.toString());
                    }

                    return output;
                } else {
                    return ("Connection error");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }
    }
}