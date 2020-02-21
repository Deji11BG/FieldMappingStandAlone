package com.example.fieldmapping;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class fields {
//    @PrimaryKey
//    public int uid;

    @PrimaryKey
    @NonNull
    public String uniqueID;

    @ColumnInfo(name = "ik_number")
    public String ik_number;

    @ColumnInfo(name = "first_name")
    public String first_name;

//    public int getUid() {
//        return uid;
//    }

//    public void setUid(int uid) {
//        this.uid = uid;
//    }

    @ColumnInfo(name = "last_name")
    public String last_name;

    @ColumnInfo(name = "member_id")
    public String member_id;

    @ColumnInfo(name = "crop_type")
    public String crop_type;

    @ColumnInfo(name = "staff_id")
    public String staff_id;

    @ColumnInfo(name = "TFMuniqueid")
    public String TFMuniqueid;

    @ColumnInfo(name = "field_size")
    public String field_size;

    @ColumnInfo(name = "latlongs")
    public String latlongs;

    @ColumnInfo(name = "timestamp")
    public String timestamp;

    @ColumnInfo(name = "date")
    public String date;

    @ColumnInfo(name = "middle")
    public String middle;

    @ColumnInfo(name = "minlat")
    public String minlat;

    @ColumnInfo(name = "maxlat")
    public String maxlat;

    @ColumnInfo(name = "minlng")
    public String minlng;

    @ColumnInfo(name = "maxlng")
    public String maxlng;

    @ColumnInfo(name = "q1")
    public String q1;

    @ColumnInfo(name = "q2")
    public String q2;

    @ColumnInfo(name = "q3")
    public String q3;

    @ColumnInfo(name = "q4")
    public String q4;

    @ColumnInfo(name = "q5")
    public String q5;

    @ColumnInfo(name = "q6")
    public String q6;

    @ColumnInfo(name = "q7")
    public String q7;

    @ColumnInfo(name = "q8")
    public String q8;

    @ColumnInfo(name = "q9")
    public String q9;

    @ColumnInfo(name = "q10")
    public String q10;

    @ColumnInfo(name = "q11")
    public String q11;

    @ColumnInfo(name = "q12")
    public String q12;

    @ColumnInfo(name = "q13")
    public String q13;

    @ColumnInfo(name = "q14")
    public String q14;

    @ColumnInfo(name = "q15ad")
    public String q15ad;

    @ColumnInfo(name = "q15ar")
    public String q15ar;

    @ColumnInfo(name = "q15bd")
    public String q15bd;

    @ColumnInfo(name = "q15br")
    public String q15br;

    @ColumnInfo(name = "q15cd")
    public String q15cd;

    @ColumnInfo(name = "q15cr")
    public String q15cr;

    @ColumnInfo(name = "q15dd")
    public String q15dd;

    @ColumnInfo(name = "q15dr")
    public String q15dr;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "village")
    public String village;


    @ColumnInfo(name = "version")
    public String version;

    @ColumnInfo(name = "field_status")
    public String field_status;

    @ColumnInfo(name = "upload_status")
    public String upload_status;
    @ColumnInfo(name = "remap_flag")
    public String remap_flag;
    public void setRemap_flag(String remap_flag) {
        this.remap_flag = remap_flag;
    }
    public String getIkNumber() {
        return ik_number;
    }

    public void setIkNumber(String ikNumber) {
        this.ik_number = ikNumber;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getLastName() {
        return last_name;
    }

    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    public String getMemberID() {
        return member_id;
    }

    public void setMemberID(String memberID) {
        this.member_id = memberID;
    }

    public String getCropType() {
        return crop_type;
    }

    public void setCropType(String cropType) {
        this.crop_type = cropType;
    }

    public String getStaffID() {
        return staff_id;
    }

    public void setStaffID(String staffID) {
        this.staff_id = staffID;
    }

    public String getTFMUniqueID() {
        return TFMuniqueid;
    }

    public void setTFMUniqueID(String TFMUniqueID) {
        this.TFMuniqueid = TFMUniqueID;
    }

    public String getFieldSize() {
        return field_size;
    }

    public void setFieldSize(String fieldSize) {
        this.field_size = fieldSize;
    }

    public String getLatlongs() {
        return latlongs;
    }

    public void setLatlongs(String latlongs) {
        this.latlongs = latlongs;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getMinlat() {
        return minlat;
    }

    public void setMinlat(String minlat) {
        this.minlat = minlat;
    }

    public String getMaxlat() {
        return maxlat;
    }

    public void setMaxlat(String maxlat) {
        this.maxlat = maxlat;
    }

    public String getMinlng() {
        return minlng;
    }

    public void setMinlng(String minlng) {
        this.minlng = minlng;
    }

    public String getMaxlng() {
        return maxlng;
    }

    public void setMaxlng(String maxlng) {
        this.maxlng = maxlng;
    }

    public String getQuestion1() {
        return q1;
    }

    public void setQuestion1(String question1) {
        this.q1 = question1;
    }

    public String getQuestion2() {
        return q2;
    }

    public void setQuestion2(String question2) {
        this.q2 = question2;
    }

    public String getQuestion3() {
        return q3;
    }

    public void setQuestion3(String question3) {
        this.q3 = question3;
    }

    public String getQuestion4() {
        return q4;
    }

    public void setQuestion4(String question4) {
        this.q4 = question4;
    }

    public String getQuestion5() {
        return q5;
    }

    public void setQuestion5(String question5) {
        this.q5 = question5;
    }

    public String getQuestion6() {
        return q6;
    }

    public void setQuestion6(String question6) {
        this.q6 = question6;
    }

    public String getQuestion7() {
        return q7;
    }

    public void setQuestion7(String question7) {
        this.q7 = question7;
    }

    public String getQuestion8() {
        return q8;
    }

    public void setQuestion8(String question8) {
        this.q8 = question8;
    }

    public String getQuestion9() {
        return q9;
    }

    public void setQuestion9(String question9) {
        this.q9 = question9;
    }

    public String getQuestion10() {
        return q10;
    }

    public void setQuestion10(String question10) {
        this.q10 = question10;
    }

    public String getQuestion11() {
        return q11;
    }

    public void setQuestion11(String question11) {
        this.q11 = question11;
    }

    public String getQuestion12() {
        return q12;
    }

    public void setQuestion12(String question12) {
        this.q12 = question12;
    }

    public String getQuestion13() {
        return q13;
    }

    public void setQuestion13(String question13) {
        this.q13 = question13;
    }

    public String getQuestion14() {
        return q14;
    }

    public void setQuestion14(String question14) {
        this.q14 = question14;
    }

    public String getQuestion15ad() {
        return q15ad;
    }

    public void setQuestion15ad(String question15ad) {
        this.q15ad = question15ad;
    }

    public String getQuestion15ar() {
        return q15ar;
    }

    public void setQuestion15ar(String question15ar) {
        this.q15ar = question15ar;
    }

    public String getQuestion15bd() {
        return q15bd;
    }

    public void setQuestion15bd(String question15bd) {
        this.q15bd = question15bd;
    }

    public String getQuestion15br() {
        return q15br;
    }

    public void setQuestion15br(String question15br) {
        this.q15br = question15br;
    }

    public String getQuestion15cd() {
        return q15cd;
    }

    public void setQuestion15cd(String question15cd) {
        this.q15cd = question15cd;
    }

    public String getQuestion15cr() {
        return q15cr;
    }

    public void setQuestion15cr(String question15cr) {
        this.q15cr = question15cr;
    }

    public String getQuestion15dd() {
        return q15dd;
    }

    public void setQuestion15dd(String question15dd) {
        this.q15dd = question15dd;
    }

    public String getQuestion15dr() {
        return q15dr;
    }

    public void setQuestion15dr(String question15dr) {
        this.q15dr = question15dr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFieldStatus() {
        return field_status;
    }

    public void setFieldStatus(String fieldStatus) {
        this.field_status = fieldStatus;
    }

    public String getUpload_status() {
        return upload_status;
    }
    public String getRemap_flag() {
        return remap_flag;
    }
    public void setUpload_status(String upload_status) {
        this.upload_status = upload_status;
    }
}
