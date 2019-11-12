package com.example.doreopartners.fieldmappingrevamp2;

public class modelmappedfield {
//model class for mapped fields, both active and inative


    private String field_id;
    private String description;
    private String field_size;
    private String date;


    public modelmappedfield( String unique_id, String description,String field_size,String date ) {

        this.field_id = unique_id;
        this.description = description;
        this.field_size=field_size;
        this.date=date;

    }


    public String getField_id() {
        return field_id;
    }

    public String getdescription() {
        return description;
    }
    public String getField_size() {
        return field_size;
    }
    public String getdate() {
        return date;
    }

}