package com.example.fieldmapping;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("sync_field_mapping.php")
    Call<List<DefaultResponse>> syncUpFields(@Field("wordlist") String sync_up_data);

    @GET("downloadMembersFM.php")
    Call<List<downloadMembersModel>> getInputRecords(@Query("staff_id") String staff_id, @Query("date_updated") String date_updatd);
}