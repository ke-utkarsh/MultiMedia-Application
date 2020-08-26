package com.example.projecttwo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiVL {
    private static Retrofit retrofit = null;
    public static ApiInterfaceVL getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vap.okli.in/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterfaceVL apivl = retrofit.create(ApiInterfaceVL.class);
        return apivl; // return the APIInterface object
    }
}
