package com.example.fieldmapping;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;




public class SharedPreferenceController {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    public static final String KEY_APP_LANG = "app_lang";

    public static final String KEY_STAFF_NAME = "staff_name";
    public static final String KEY_STAFF_ID = "staff_id";
    public static final String KEY_STAFF_ROLE = "staff_role";
    public static final String KEY_STAFF_PROGRAM = "staff_program";
    public static final String KEY_IMPORT_FLAG = "import_flag";
    public static final String DATE_UPDATED="date_updated";






    // Constructor
    public SharedPreferenceController(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public SharedPreferenceController() {

    }





    public void setDateUpdated(String value) {
        editor.putString(DATE_UPDATED, value);
        editor.commit();
    }
//
//
//
//
//
//
    public String getDateUpdated() {
        String temp = pref.getString(DATE_UPDATED, "0");
        return temp;
    }



//    public HashMap<String, String> getUserDetails() {
//        HashMap<String, String> user = new HashMap<String, String>();
//        // user name
//        user.put(KEY_STAFF_NAME, pref.getString(KEY_STAFF_NAME, null));
//        user.put(KEY_STAFF_ID, pref.getString(KEY_STAFF_ID, null));
//        user.put(KEY_STAFF_PROGRAM, pref.getString(KEY_STAFF_PROGRAM, "BGD"));
//        user.put(KEY_IMPORT_FLAG, pref.getString(KEY_IMPORT_FLAG, "0"));
//        user.put(KEY_FIRST_NAME, pref.getString(KEY_FIRST_NAME, "0"));
//        user.put(KEY_LAST_NAME, pref.getString(KEY_LAST_NAME, "0"));
//        user.put(KEY_PHONE_NUMBER, pref.getString(KEY_PHONE_NUMBER, "0"));
//        user.put(KEY_AGE, pref.getString(KEY_AGE, "0"));
//        user.put(KEY_SEX, pref.getString(KEY_SEX, "0"));
//        user.put(KEY_MEMBER_ROLE, pref.getString(KEY_MEMBER_ROLE, "0"));
//        user.put(KEY_CROP_TYPE, pref.getString(KEY_CROP_TYPE, "0"));
//        user.put(KEY_TEST_ID, pref.getString(KEY_TEST_ID, "0"));
//        user.put(KEY_LATITUDE, pref.getString(KEY_LATITUDE, "0"));
//        user.put(KEY_LONGITUDE, pref.getString(KEY_LONGITUDE, "0"));
//        user.put(KEY_QUESTION_INDEX, pref.getString(KEY_QUESTION_INDEX, "0"));
//        user.put(KEY_HOLD_ANSWERS, pref.getString(KEY_HOLD_ANSWERS, "[]"));
//        user.put(KEY_STAFF_ROLE, pref.getString(KEY_STAFF_ROLE, ""));
//
//        return user;
//    }



}



