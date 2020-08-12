package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Videos extends Fragment {
    RecyclerView recyclerView;
    List<Result> videoListResponseData;
    ImageButton ib;
    EditText et;
    String fet;
    public Videos() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.fragment_videos, container, false);
        recyclerView = (RecyclerView) InputFragmentView.findViewById(R.id.recyclerView);
        ib=(ImageButton) InputFragmentView.findViewById(R.id.imageButton);
        et=(EditText) InputFragmentView.findViewById(R.id.editText);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSearch();

            }
        });
        getVideoDataList();
        return InputFragmentView;
    }
    public void getVideoDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        (ApiVL.getClient().getVideosList()).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                videoListResponseData = response.body().getResults();
                //Toast.makeText(getActivity(), response.body().getCount(), Toast.LENGTH_SHORT).show();
                setDataInRecyclerView();
            }

            @Override
            public void onFailure(Call<VideoListResponse> call, Throwable t) {
                Toast.makeText(getActivity(), t.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss(); //dismiss progress dialog
            }
        });
    }
    private void setDataInRecyclerView() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        UsersAdapter usersAdapter = new UsersAdapter(getActivity(), videoListResponseData);
        recyclerView.setAdapter(usersAdapter); // set the Adapter to RecyclerView
    }

    public void goToSearch(){
        fet=et.getText().toString();
        if(fet.length()>0){
            Intent intent=new Intent(getActivity(),SearchFragment.class);
            intent.putExtra("urli",fet);
            intent.putExtra("code","1");
            startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "What do you wanna search?", Toast.LENGTH_SHORT).show();
        }
    }
}
