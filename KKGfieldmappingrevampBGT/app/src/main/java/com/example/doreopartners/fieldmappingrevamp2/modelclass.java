package com.example.doreopartners.fieldmappingrevamp2;

public class modelclass {

//model class for memberrecycler

    private String first_name;
    private String last_name;
    private String phone_number;
    private String member_id;


    public modelclass(String first_name, String last_name, String member_id, String phone_number ) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = phone_number;
        this.member_id = member_id;

    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public String getMember_id() {
        return member_id;
    }
}