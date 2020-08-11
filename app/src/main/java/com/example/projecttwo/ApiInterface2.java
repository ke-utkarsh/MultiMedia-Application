package com.example.projecttwo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface2 {
    @FormUrlEncoded // annotation used in POST type requests
    @POST("login")
    // API's endpoints
            Call<LogInResponse> register(@Field("username") String username,
                                         @Field("password") String password);
}
