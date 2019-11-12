package com.example.doreopartners.fieldmappingrevamp2;

public class modelclass1 {

//model class for tgselect

    private String first_name;
    private String last_name;
    private String ik_number;
    private String village;



    public modelclass1(String first_name, String last_name, String ik_number, String village ) {

        this.first_name = first_name;
        this.last_name = last_name;
        this.ik_number = ik_number;
        this.village = village;

    }



    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getik_number() {
        return ik_number;
    }

    public String getVillage() {
        return village;
    }

}