package com.apppartner.androidprogrammertest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ShowMe on 7/30/16.
 */
public interface PostRequest {
    @FormUrlEncoded
    @POST("/AppPartnerDeveloperTest/scripts/login.php")
    Call<AppPartnerData> LoginToAppPartner(
            @Field("username") String username,
            @Field("password") String password
            );
}
