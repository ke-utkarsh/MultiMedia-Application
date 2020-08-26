package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.projecttwo.ui.SearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends AppCompatActivity {

    String urli;
    RecyclerView recyclerView;
    List<Result> Data;
    String code;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.fragment_search);

        Intent intent=getIntent();
        urli=intent.getStringExtra("urli");
        code=intent.getStringExtra("code");
        /*SharedPreferences sP=getSharedPreferences("mykey",MODE_PRIVATE);
        SharedPreferences.Editor editor=sP.edit();
        editor.putString("value",code);
        editor.apply();*/

        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);

        if(code.equals("1")){
            url="search_video_database?search="+urli;
            getSearchDataList();
        }
        else{
            url="search_document_database?search="+urli;
            getPdfDataList();
        }


    }

    public void getPdfDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(SearchFragment.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiSVL.getClient().getVideosList(url).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                if(response.body().getCount()==0){
                    Toast.makeText(SearchFragment.this, "Sorry, No result :(", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(), response.body().getCount().toString(), Toast.LENGTH_SHORT).show();
                    Data = response.body().getResults();

                    setPdfDataInRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {
                Toast.makeText(SearchFragment.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });

    }

    public void getSearchDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(SearchFragment.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        ApiSVL.getClient().getVideosList(url).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                if(response.body().getCount()==0){
                    Toast.makeText(SearchFragment.this, "Sorry, No result :(", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(), response.body().getCount().toString(), Toast.LENGTH_SHORT).show();
                    Data = response.body().getResults();
                    //Toast.makeText(SearchFragment.this, Data.get(0).getUrl().toString(), Toast.LENGTH_SHORT).show();
                    setVideoDataInRecyclerView();
                }

            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {
                Toast.makeText(SearchFragment.this, t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }
    private void setVideoDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFragment.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        //int f=0;
        UsersAdapter usersAdapter = new UsersAdapter(SearchFragment.this, Data);
        recyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }
    private void setPdfDataInRecyclerView(){
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchFragment.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        PdfAdapter pdfAdapter = new PdfAdapter(SearchFragment.this, Data);
        recyclerView.setAdapter(pdfAdapter); // set the Adapter to RecyclerView
    }

}
