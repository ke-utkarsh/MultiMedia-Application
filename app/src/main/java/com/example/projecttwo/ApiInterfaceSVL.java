package com.example.projecttwo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.security.AccessController;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface ApiInterfaceSVL {
    //SharedPreferences myPrefs = PreferenceManager.getDefaultSharedPreferences(.getContext()); //Activity1.class
    //String ifsound = myPrefs.getString("sound","");

    //String urli=SearchFragment.ExtraData.toString();



    //String url="search_video_database?search="+urli;
    @GET
        // API's endpoints
    Call<VideoListResponse> getVideosList(@Url String url);


// UserListResponse is POJO class to get the data from API, we use List<UserListResponse> in callback because the data in our API is starting from JSONArray

}
