package com.example.projecttwo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.AccessController;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

import static android.content.Context.MODE_PRIVATE;

public interface ApiInterfaceSVL {
    //SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(.getContext()); //Activity1.class
    //String ifsound = myPrefs.getString("sound","");
    @GET("search_video_database?search=ram")
        // API's endpoints
    Call<VideoListResponse> getVideosList();

// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray

}
