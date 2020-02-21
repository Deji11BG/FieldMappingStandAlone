package com.example.fieldmapping;

import com.google.gson.annotations.SerializedName;

public class downloadMembersModel {
    @SerializedName("unique_member_id")
    private String unique_member_id;

    @SerializedName("unique_ik_number")
    private String unique_ik_number;

    @SerializedName("ik_number")
    private String ik_number;

    @SerializedName("member_id")
    private String member_id;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("middle_name")
    private String middle_name;

    @SerializedName("last_name")
    private String last_name;


    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("ward_id")
    private String ward_id;

    @SerializedName("village_name")
    private String village_name;

    @SerializedName("role")
    private String role;

    @SerializedName("template")
    private String template;
    @SerializedName("date_updated")
    private String date_updated;
//
//    @SerializedName("picture_name")
//    private String picture_name;
//
    @SerializedName("input_field_size")
    private String input_field_size;

    public String getUnique_member_id() {
        return unique_member_id;
    }

    public void setUnique_member_id(String unique_member_id) {
        this.unique_member_id = unique_member_id;
    }

    public String getWard_id() {
        return ward_id;
    }

    public void setWard_id(String ward_id) {
        this.ward_id = ward_id;
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

    public String getMiddle_name() {
        return middle_name;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
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


//    public String getPicture_name() {
//        return picture_name;
//    }
//
//    public void setPicture_name(String picture_name) {
//        this.picture_name = picture_name;
//    }

    public String getInput_field_size() {
        return input_field_size;
    }

    public void setInput_field_size(String input_field_size) {
        this.input_field_size = input_field_size;
    }

    public String getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(String date_updated) {
        this.date_updated = date_updated;
    }
}
