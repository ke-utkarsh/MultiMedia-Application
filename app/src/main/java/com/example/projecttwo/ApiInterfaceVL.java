package com.example.projecttwo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterfaceVL {
    @GET("video_database?page=1")
        // API's endpoints
    Call<VideoListResponse> getVideosList();

// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray

}
