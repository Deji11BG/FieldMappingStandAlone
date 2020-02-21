package com.example.fieldmapping;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("unique_id")
    @Expose
    private String unique_id;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("last_synced")
    @Expose
    private String last_synced;

    public DefaultResponse(String unique_id, String status, String last_synced) {
        this.unique_id = unique_id;
        this.status = status;
        this.last_synced = last_synced;
    }

    public String getField_id() {
        return unique_id;
    }

    public String getStatus() {
        return status;
    }

    public String getLast_synced() {
        return last_synced;
    }
}

