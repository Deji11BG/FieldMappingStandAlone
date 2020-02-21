package com.example.fieldmapping;


import android.graphics.Bitmap;
import android.widget.ImageView;

public class modelmemberinfo {
//model class for mapped fields, both active and inative

    private String first_name;
    private String last_name;
    private String member_id;
    private String ward;
    private String ik_number;
    private String field_size;
    private String phone_number;



    public modelmemberinfo(String first_name, String last_name, String member_id, String ward, String ik_number, String field_size,String phone_number ) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.member_id=member_id;
        this.ward=ward;
        this.ik_number=ik_number;
        this.field_size=field_size;
        this.phone_number=phone_number;

    }
    public modelmemberinfo() {


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

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getIk_number() {
        return ik_number;
    }

    public void setIk_number(String ik_number) {
        this.ik_number = ik_number;
    }

    public String getField_size() {
        return field_size;
    }

    public void setField_size(String field_size) {
        this.field_size = field_size;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }
}