package com.example.projecttwo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterfaceSVL {
    @GET
        // API's endpoints
    Call<VideoListResponse> getVideosList(@Url String url);
// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray

}
