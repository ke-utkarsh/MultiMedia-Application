package com.example.projecttwo;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.projecttwo.ui.SearchAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    RecyclerView recyclerView2;
    List<Result> Data;


    public SearchFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View InputFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        Toast.makeText(getActivity(), "SET HO GYA", Toast.LENGTH_SHORT).show();

        recyclerView2 = (RecyclerView) InputFragmentView.findViewById(R.id.recyclerView2);

        getSearchDataList();
        return InputFragmentView;
    }
    public void getSearchDataList(){
        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog

        (ApiSVL.getClient().getVideosList()).enqueue(new Callback<VideoListResponse>() {
            @Override
            public void onResponse(Call<VideoListResponse> call, Response<VideoListResponse> response) {
                progressDialog.dismiss();
                if(response.body().getCount()==0){
                    Toast.makeText(getActivity(), "Sorry, No result :(", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(getActivity(), response.body().getCount().toString(), Toast.LENGTH_SHORT).show();
                    Data = response.body().getResults();
                    //Toast.makeText(getActivity(), response.body().getCount(), Toast.LENGTH_SHORT).show();
                    setDataInRecyclerView();
                }

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
        recyclerView2.setLayoutManager(linearLayoutManager);
        // call the constructor of UsersAdapter to send the reference and data to Adapter
        SearchAdapter searchAdapter = new SearchAdapter(getActivity(), Data);
        recyclerView2.setAdapter(searchAdapter); // set the Adapter to RecyclerView
    }
}
