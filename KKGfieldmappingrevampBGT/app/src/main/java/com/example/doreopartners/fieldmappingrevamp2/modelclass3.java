package com.example.doreopartners.fieldmappingrevamp2;

public class modelclass3 {

//model class for member select

    private String first_name;
    private String last_name;

    private String member_id;


    public modelclass3(String first_name, String last_name, String member_id ) {

        this.first_name = first_name;
        this.last_name = last_name;

        this.member_id = member_id;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }


    public String getMember_id() {
        return member_id;
    }
}