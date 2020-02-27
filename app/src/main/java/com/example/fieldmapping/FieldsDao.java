package com.example.fieldmapping;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface FieldsDao {
    @Query("SELECT * FROM fields")
    List<fields> getAll();

    @Query("UPDATE fields SET upload_status= :upload_status WHERE uniqueID =:uniqueID")
    void updateSyncStatusRoom(String upload_status, String uniqueID);

    @Query("UPDATE fields set field_status= :fieldStatus WHERE uniqueID =:uniqueID")
    void updateFieldStatusRoom(String fieldStatus, String uniqueID);

    @Query("SELECT count (*) FROM fields WHERE member_id =:memberID")
    Integer memberfields(String memberID);


    @Query("SELECT first_name,last_name,member_id,uniqueID FROM fields WHERE village =:village AND " +
            "ik_number =:IKNumber  ")
    List <fields> loadMemberRoom(String village, String IKNumber);

    @Query("SELECT field_size,uniqueID FROM fields WHERE member_id =:member_id")
    List <fields> getmemberfieldsize( String member_id);

    @Query("SELECT count (*) FROM fields WHERE date =:date")
    int mappedTodayRoom(String date);

    @Query("SELECT count (*) FROM fields")
    int mappedTotalRoom();

    @Query("SELECT count (*) FROM fields WHERE upload_status =:uploadStatus")
    int syncedCountRoom(String uploadStatus);

    @Query("SELECT village,uniqueID FROM fields WHERE staff_id =:staffID ")
    List <fields> getVillageRoom(String staffID);

    @Query("SELECT ik_number,uniqueID FROM fields WHERE member_id =:memberID AND " + "village =:village ")
    List <fields> loadIKRoom(String village,String memberID);

    @Query("SELECT description,uniqueID,field_size,date FROM fields WHERE member_id =:memberID AND " +
            "field_status =:fieldStatus ")
    List <fields> loadActiveFields(String memberID, String fieldStatus);

    @Query("SELECT description,uniqueID,field_size,date FROM fields WHERE member_id =:memberID AND " +
            "field_status =:fieldStatus ")
   List <fields> loadInactiveFields(String memberID, String fieldStatus);
    @Query("SELECT uniqueID FROM fields WHERE middle=:middle ")
    List <fields> checkoverlap(String middle);
//query to get from Dami's table
    //@Query("SELECT member_id,ik_number,first_name,last_name,phone_number,crop_type,state,lga,ward,village,mapping_date,member_role,staff_id FROM fields WHERE unique_id =:uniqueID")
    //List <fields> loadFieldInfo(String uniqueID);

    @Query("SELECT * FROM fields where upload_status = '0'")
    List<fields> getUnsynced();

    //TODO---> Deji has to check this out.
   /* @Query("SELECT count (*) FROM fields WHERE upload_status ='0'")
    Integer unsyncedCount(String memberID);*/

    @Query("SELECT count (*) FROM fields WHERE upload_status ='0'")
    Integer unsyncedCount();

    @Insert
    void insertAll(fields... fields);

    @Delete
    void delete(fields field);

}
