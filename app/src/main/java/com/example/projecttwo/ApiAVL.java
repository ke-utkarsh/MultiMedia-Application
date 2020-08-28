package com.example.projecttwo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiAVL {
    private static Retrofit retrofit = null;
    public static ApiInterfaceAVL getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vap.okli.in/api/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterfaceAVL apiavl = retrofit.create(ApiInterfaceAVL.class);
        return apiavl; // return the APIInterface object
    }
}
