package com.example.projecttwo;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiSVL {
    private static Retrofit retrofit = null;
    public static ApiInterfaceSVL getClient() {

        // change your base URL
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://vap.okli.in/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        //Creating object for our interface
        ApiInterfaceSVL apisvl = retrofit.create(ApiInterfaceSVL.class);
        return apisvl; // return the APIInterface object
    }
}
