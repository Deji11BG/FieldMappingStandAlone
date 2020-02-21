package com.example.fieldmapping;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
@Dao
public interface MembersDao {

    @Query("SELECT * FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + "")
    List<MembersTable> getmembers();

    @Query("SELECT phone_number FROM " + TFMDBContractClass.TABLE_NEW_MEMBERS_DATA + " WHERE unique_member_id=:unique_member_id")
    String getphonenumber(String unique_member_id);


    /**
     * Insert the object in database
     * @param membersTable, object to be inserted
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MembersTable membersTable);

    /**
     * Insert the object in database
     * @param membersTableList, object to be inserted
     */


    /**
     * update the object in database
     * @param membersTable, object to be updated
     */
    @Update
    void update(MembersTable membersTable);

    /**
     * delete the object from database
     * @param membersTable, object to be deleted
     */
    @Delete
    void delete(MembersTable membersTable);

    /**
     * delete list of objects from database
     * @param data, array of objects to be deleted
     */
    @Delete
    void delete(MembersTable... data);      // data... is varargs, here data is an array
}
