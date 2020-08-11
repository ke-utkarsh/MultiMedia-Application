package com.example.projecttwo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api2 {
    private static Retrofit retrofit = null;
    public static ApiInterface2 getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vap.okli.in/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterface2 api2 = retrofit.create(ApiInterface2.class);
        return api2; // return the APIInterface object
    }
}
