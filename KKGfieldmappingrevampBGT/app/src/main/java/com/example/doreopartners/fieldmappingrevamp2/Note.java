package com.example.doreopartners.fieldmappingrevamp2;


import android.net.Uri;
import android.provider.BaseColumns;

public class Note {

    /*
    * This class contains all the column names used in the database tables for this application
    *
    * */


        public Note() {
        }

        public static final class Notes implements BaseColumns {
            private Notes() {
            }

            public static final Uri CONTENT_URI = Uri.parse("content://"
                    + NotesContentProvider.AUTHORITY + "/tfmtable");

            public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.jwei512.tfmtable";

            public static final String OPERATION_ID = "_id";

            public static final String COLUMN_USER_MEMBERID = "member_id";

            public static final String COLUMN_USER_IKNUMBER = "ik_number";
            public static final String COLUMN_USER_FIRSTNAME = "first_name";
            public static final String COLUMN_USER_LASTNAME = "last_name";
            public static final String COLUMN_USER_PHONENUMBER = "phone_number";
            public static final String COLUMN_USER_CROPTYPE = "crop_type";
            public static final String COLUMN_USER_STATE = "state";
            public static final String COLUMN_USER_LGA = "lga";
            public static final String COLUMN_USER_UNIQUEID = "unique_id";
            public static final String COLUMN_USER_VILLAGE = "village";
            public static final String COLUMN_USER_MAPPINGDATE = "mapping_date";
            public static final String COLUMN_USER_MEMBERROLE = "member_role";
            public static final String COLUMN_USER_STAFFID = "staff_id";







        }

    }

