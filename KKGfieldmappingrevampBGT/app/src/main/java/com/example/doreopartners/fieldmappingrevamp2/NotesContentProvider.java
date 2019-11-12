package com.example.doreopartners.fieldmappingrevamp2;


import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Jason Wei
 *
 */
public class NotesContentProvider extends ContentProvider {

    /*
    * This activity contains all the methods that retrieves data from the SQlite database
    * also it has the content provider implementation that allows other application to access its database
    *
    * */

    private static final String TAG = "NotesContentProvider";

    private static final String DATABASE_NAME = "tfm.db";

    private static final String NOTES_TABLE_NAME = "tfmtable";


    private static final int DATABASE_VERSION = 1;





    public static final String AUTHORITY = "com.example.doreopartners.fieldmappingrevamp2";

    private static final UriMatcher sUriMatcher;

    // url provided to other applications
    static final String URL = "content://" + AUTHORITY + "/"+NOTES_TABLE_NAME;

    private static final int NOTES = 1;

    private static final int NOTES_ID = 2;

    private static HashMap<String, String> notesProjectionMap;



    private OnlineDBHelper dbHelper;

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (sUriMatcher.match(uri)) {
            case NOTES:
                break;
            case NOTES_ID:
                where = where + "_id = "+ uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        int count = db.delete(NOTES_TABLE_NAME, where, whereArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case NOTES:
                return Note.Notes.CONTENT_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
    }
// The insert method is called when other apps push data into the incentive app
    @Override
    public Uri insert(Uri uri, ContentValues initialValues) {
        if (sUriMatcher.match(uri) != NOTES) {
            throw new IllegalArgumentException("Unknown URI " + uri);
        }

        ContentValues values;
        if (initialValues != null) {
            values = new ContentValues(initialValues);
        } else {
            values = new ContentValues();
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long rowId = db.insert(NOTES_TABLE_NAME, Note.Notes.COLUMN_USER_MEMBERID, values);
        if (rowId > 0) {
            Uri noteUri = ContentUris.withAppendedId(Note.Notes.CONTENT_URI, rowId);
            getContext().getContentResolver().notifyChange(noteUri, null);
            return noteUri;
        }

        throw new SQLException("Failed to insert row into " + uri);
    }


    @Override
    public boolean onCreate() {
        dbHelper = new OnlineDBHelper(getContext());
        return true;
    }
// This query method is used to pull records from the incentive database based on some criteria

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(NOTES_TABLE_NAME);
        qb.setProjectionMap(notesProjectionMap);

        switch (sUriMatcher.match(uri)) {
            case NOTES:
                break;
            case NOTES_ID:
                selection = selection + "_id = "+ uri.getLastPathSegment();
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count;
        switch (sUriMatcher.match(uri)) {
            case NOTES:
                count = db.update(NOTES_TABLE_NAME, values, where, whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(AUTHORITY, NOTES_TABLE_NAME, NOTES);
        sUriMatcher.addURI(AUTHORITY, NOTES_TABLE_NAME + "/#", NOTES_ID);

        notesProjectionMap = new HashMap<String, String>();
        notesProjectionMap.put(Note.Notes.OPERATION_ID, Note.Notes.OPERATION_ID);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_MEMBERID, Note.Notes.COLUMN_USER_MEMBERID);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_IKNUMBER, Note.Notes.COLUMN_USER_IKNUMBER);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_FIRSTNAME, Note.Notes.COLUMN_USER_FIRSTNAME);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_LASTNAME, Note.Notes.COLUMN_USER_LASTNAME);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_PHONENUMBER, Note.Notes.COLUMN_USER_PHONENUMBER);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_CROPTYPE, Note.Notes.COLUMN_USER_CROPTYPE);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_STATE, Note.Notes.COLUMN_USER_STATE);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_LGA, Note.Notes.COLUMN_USER_LGA);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_UNIQUEID, Note.Notes.COLUMN_USER_UNIQUEID);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_VILLAGE, Note.Notes.COLUMN_USER_VILLAGE);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_MAPPINGDATE, Note.Notes.COLUMN_USER_MAPPINGDATE);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_MEMBERROLE, Note.Notes.COLUMN_USER_MEMBERROLE);
        notesProjectionMap.put(Note.Notes.COLUMN_USER_STAFFID, Note.Notes.COLUMN_USER_STAFFID);

    }
}