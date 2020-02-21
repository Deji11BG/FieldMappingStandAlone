package com.example.fieldmapping;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {fields.class}, version = 1)
public abstract class finalDatabase extends RoomDatabase {
    public abstract FieldsDao fieldsdao();
    public static final String DB_NAME = "DB";
    private static finalDatabase fd;



    public String saverecordsRoom(JSONArray jsonArray){
        JSONObject jsonObject = null;
        fields fields1 =new fields();
        for(int i = 0; i< jsonArray.length(); i++){
            try {
                //int Check  = 0;
                jsonObject = jsonArray.getJSONObject(i);
                fields1.setFirstName(jsonObject.getString("first_name"));
                fields1.setLastName(jsonObject.getString("last_name"));
                fields1.setCropType(jsonObject.getString("crop_type"));
                fields1.setIkNumber(jsonObject.getString("ik_number"));
                fields1.setFieldSize(jsonObject.getString("field_size"));
                fields1.setStaffID(jsonObject.getString("staff_id"));
                fields1.setMemberID(jsonObject.getString("member_id"));



                fields1.setMiddle(jsonObject.getString("middle"));
                fields1.setMinlat(jsonObject.getString("minlat"));
                fields1.setMaxlat(jsonObject.getString("maxlat"));
                fields1.setMinlng(jsonObject.getString("minlng"));
                fields1.setMaxlng(jsonObject.getString("maxlng"));

                fields1.setTFMUniqueID(jsonObject.getString("TFMuniqueid"));
                fields1.setUniqueID(jsonObject.getString("unique_id"));
                fields1.setTimestamp(jsonObject.getString("timestamp"));




                fields1.setQuestion1(jsonObject.getString("q1"));
                fields1.setQuestion2(jsonObject.getString("q2"));
                fields1.setQuestion3(jsonObject.getString("q3"));
                fields1.setQuestion4(jsonObject.getString("q4"));
                fields1.setQuestion5(jsonObject.getString("q5"));
                fields1.setDate(jsonObject.getString("date"));
                fields1.setFieldStatus(jsonObject.getString("field_status"));
                //fields1.setVersion(jsonObject.getString("upload_status"));

                fields1.setLatlongs(jsonObject.getString("latlongs"));

                fields1.setQuestion6(jsonObject.getString("q6"));
                fields1.setQuestion7(jsonObject.getString("q7"));
                fields1.setQuestion8(jsonObject.getString("q8"));
                fields1.setQuestion9(jsonObject.getString("q9"));
                fields1.setQuestion10(jsonObject.getString("q10"));
                fields1.setQuestion11(jsonObject.getString("q11"));
                fields1.setQuestion12(jsonObject.getString("q12"));
                fields1.setQuestion13(jsonObject.getString("q13"));
                fields1.setQuestion14(jsonObject.getString("q14"));
                fields1.setDescription(jsonObject.getString("description"));
                fields1.setVillage(jsonObject.getString("village"));


                fields1.setQuestion15ad(jsonObject.getString("q15ad"));
                fields1.setQuestion15bd(jsonObject.getString("q15bd"));
                fields1.setQuestion15cd(jsonObject.getString("q15cd"));
                fields1.setVersion(jsonObject.getString("version"));
                fields1.setUpload_status(jsonObject.getString("upload_status"));
                fields1.setRemap_flag(jsonObject.getString("remap_flag"));

                fields1.setQuestion15dd(jsonObject.getString("q15dd"));

                fields1.setQuestion15ar(jsonObject.getString("q15ar"));
                fields1.setQuestion15br(jsonObject.getString("q15br"));
                fields1.setQuestion15cr(jsonObject.getString("q15cr"));
                fields1.setQuestion15dr(jsonObject.getString("q15dr"));




                fieldsdao().insertAll(fields1);
                Log.d("field1",fields1.toString());
            } catch (JSONException e) {

                Log.d("deji4", e.toString());
                e.printStackTrace();
            }

        }





        return "";
    }

    public void cleanUp(){
        fd = null;
    }
    private static finalDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                finalDatabase.class,
                "DB")
                .allowMainThreadQueries().build();
    }
    public static finalDatabase getInstance(Context context) {
        if (null == fd) {
            fd = buildDatabaseInstance(context);
        }
        return fd;
    }


}
