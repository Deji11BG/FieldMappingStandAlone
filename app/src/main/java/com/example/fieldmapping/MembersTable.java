package com.example.fieldmapping;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;


@Entity(primaryKeys = {TFMDBContractClass.COL_UNIQUE_MEMBER_ID},
        indices = {@Index(value = TFMDBContractClass.COL_UNIQUE_MEMBER_ID, unique = true)},
        tableName = TFMDBContractClass.TABLE_NEW_MEMBERS_DATA)

public class MembersTable {

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_MEMBER_ID)
    @NonNull
    private String unique_member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_UNIQUE_IK_NUMBER)
    private String unique_ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_IK_NUMBER)
    private String ik_number;

    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_ID)
    private String member_id;

    @ColumnInfo(name = TFMDBContractClass.COL_FIRST_NAME)
    private String first_name;

    @ColumnInfo(name = TFMDBContractClass.COL_MIDDLE_NAME)
    private String middle_name;

    @ColumnInfo(name = TFMDBContractClass.COL_LAST_NAME)
    private String last_name;

    @ColumnInfo(name = TFMDBContractClass.COL_PHONE_NUMBER)
    private String phone_number;


    @ColumnInfo(name = TFMDBContractClass.COL_STATE_ID)
    private String state_id;

    @ColumnInfo(name = TFMDBContractClass.COL_LGA_ID)
    private String lga_id;

    @ColumnInfo(name = TFMDBContractClass.COL_WARD_ID)
    private String ward_id;

    @ColumnInfo(name = TFMDBContractClass.COL_VILLAGE_NAME)
    private String village_name;

    @ColumnInfo(name = TFMDBContractClass.COL_ROLE)
    private String role;

    @ColumnInfo(name = TFMDBContractClass.COL_TEMPLATE)
    private String template;

    @ColumnInfo(name = TFMDBContractClass.COL_STAFF_ID)
    private String staff_id;


    @ColumnInfo(name = TFMDBContractClass.COL_MEMBER_PROGRAM)
    private String member_program;

//    @ColumnInfo(name = TFMDBContractClass.SYNC_DATE)
//    private String sync_date;



    @ColumnInfo(name = TFMDBContractClass.COL_LOAN_FIELD_SIZE)
    private String loan_field_size;
    @ColumnInfo(name = TFMDBContractClass.TGE_ID)
    private String tge_id;
    @ColumnInfo(name = TFMDBContractClass.COL_IMEI)
    private String imei;

    public MembersTable(@NonNull String unique_member_id, String unique_ik_number, String ik_number,
                        String member_id, String first_name, String middle_name, String last_name,
                        String phone_number,
                        String state_id, String lga_id, String ward_id, String village_name, String role,
                        String template,String staff_id,String member_program, String tge_id, String imei,String field_size) {
        this.unique_member_id = unique_member_id;
        this.unique_ik_number = unique_ik_number;
        this.ik_number = ik_number;
        this.member_id = member_id;
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.phone_number = phone_number;

        this.state_id = state_id;
        this.lga_id = lga_id;
        this.ward_id = ward_id;
        this.village_name = village_name;
        this.role = role;
        this.template = template;
        this.staff_id = staff_id;
        this.member_program = member_program;
        this.tge_id = tge_id;
        this.imei = imei;
        this.loan_field_size=field_size;
        //this.sync_date=sync_date;
    }

    public MembersTable(){

    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getTge_id() {
        return tge_id;
    }

    public void setTge_id(String tge_id) {
        this.tge_id = tge_id;
    }

    public String getMember_program() {
        return member_program;
    }

    public void setMember_program(String member_program) {
        this.member_program = member_program;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }


    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getLga_id() {
        return lga_id;
    }

    public void setLga_id(String lga_id) {
        this.lga_id = lga_id;
    }

    @NonNull
    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(@NonNull String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getUnique_ik_number() {
        return unique_ik_number;
    }

    public void setUnique_ik_number(String unique_ik_number) {
        this.unique_ik_number = unique_ik_number;
    }

    public String getIk_number() {
        return ik_number;
    }

    public void setIk_number(String ik_number) {
        this.ik_number = ik_number;
    }

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }




    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getLoan_field_size() {
        return loan_field_size;
    }

    public void setLoan_field_size(String loan_field_size) {
        this.loan_field_size = loan_field_size;
    }
}
