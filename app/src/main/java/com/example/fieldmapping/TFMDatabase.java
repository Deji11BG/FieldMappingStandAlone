package com.example.fieldmapping;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {MembersTable.class},

        version = TFMDBContractClass.TFM_DATABASE_VERSION, exportSchema = false)

public abstract  class TFMDatabase extends RoomDatabase {

    public abstract MembersDao getMembersTable();

    private static TFMDatabase tfmDatabase;

    /**
     * Return instance of database creation
     * */
    public static TFMDatabase getInstance(Context context) {
        if (null == tfmDatabase) {
            tfmDatabase = buildDatabaseInstance(context);
        }
        return tfmDatabase;
    }


    private static TFMDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                TFMDatabase.class,
                TFMDBContractClass.TFM_DATABASE_NAME)
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        tfmDatabase = null;
    }
}
